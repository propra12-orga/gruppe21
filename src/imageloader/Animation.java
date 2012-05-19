package imageloader;

import java.awt.image.BufferedImage;

public class Animation {
	private GameAnimation currentAnimation;
	private BufferedImage currentImage;
	private boolean isrunning = false;
	private boolean isimage = false;
	private String aSetName;
	private AnimationSet animationSet;
	private int animationCounter = 0;
	private int startdelay = -1;
	private int stopdelay = -1;
	private int changedelay = -1;
	private String waitingAnimation;
	private BufferedImage waitingStopImage = null;
	
	//TODO Verzehrung ausführen am besten im Konstruktur oder gleich beim auslesen
	
	public Animation(String n,ImageLoader gr){
		aSetName=n;	
		animationSet = gr.getAnimationSet(n);
		currentImage = animationSet.getDefault();
	}
	
	//sets animation
	public void setCurrentAnimation(String an){
		currentAnimation = animationSet.getAnimation(an);
		animationCounter = 0;
		isimage = false;
		isrunning = true;
	}
	
	//starts an Animation with/without delay
	public void start(String an){
		isrunning=true;
		setCurrentAnimation(an);
	}
	
	public void start(String an,int delay){
		startdelay = delay;
		waitingAnimation = an;
	}
	
	//stops an animation with/without delay and/or specific picture
	public void stop(){
		isrunning=false;
		currentImage= new BufferedImage(50,50,BufferedImage.TYPE_INT_ARGB);
	}
	
	public void stop(String stillName){
		isrunning = false;
		currentImage = animationSet.getImage(stillName);
		isimage = true;
	}
	
	public void stop(int delay){
		stopdelay = delay;
		currentImage= new BufferedImage(50,50,BufferedImage.TYPE_INT_ARGB);
	}
	
	public void stop(int delay,String stillName){
		stopdelay = delay;
		waitingStopImage = animationSet.getImage(stillName);
	}
	
	// changes the current animation
	public void change(String aName){
		setCurrentAnimation(aName);
	}
	
	public void change(int delay,String aName){
		changedelay = delay;
		waitingAnimation = aName;
	}
	
	//animates and returns the new picture
	public void animate(){
		if(stopdelay>=0){
			if(stopdelay==0){
				if(waitingStopImage!=null){
					currentImage = waitingStopImage;
					isimage =true;
					waitingStopImage = null;
				}
				isrunning=false;
				stopdelay=-1;
			} else{stopdelay-=1;}
		}
		if(startdelay>=0){
			if(startdelay==0){
				setCurrentAnimation(waitingAnimation);
				isrunning=true;
				startdelay=-1;
			}else{startdelay-=1;}
		}
		if(changedelay>=0){
			if(startdelay==0){
				setCurrentAnimation(waitingAnimation);
				changedelay=-1;
			}else{changedelay-=1;}
		}
		if(isrunning){
			if(currentAnimation.end(animationCounter)){
				currentImage = currentAnimation.getFrame(animationCounter);
				animationCounter=0;
			}else{
				currentImage = currentAnimation.getFrame(animationCounter);
				animationCounter=animationCounter+1;
			}
			
		}
	}
	
	public BufferedImage getCurrentImage(){
	return currentImage;
	}
	
	public String getCurrentImagePath(){
		return animationSet.getDefaultName();
	}
	
	public boolean isRunning(){
		return isrunning;
	}
}
