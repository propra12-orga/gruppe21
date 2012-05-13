package imageloader;

import java.awt.Image;

public class Animation {
	private GameGraphic currentAnimation;
	private Image currentImage;
	boolean isrunning = false;
	boolean ispicture = false;
	String aSetName;
	AnimationSet animationSet;
	int animationCounter;
	
	public Animation(String n){
		aSetName=n;		
	}
	
	//starts an Animation with/without deelay
	public void start(){}
	
	public void start(int deelay){}
	
	//stops an animation with/without delay and/or specific picture
	public void stop(){}
	
	public void stop(int deelay){}
	
	public void stop(int deelay,String stillName){}
	
	// changes the current animation
	public void change(String aName){}
	
	public void change(String aName,int deelay){}
	
	//animates and returns the new picture
	public Image animate(){
		return currentImage;
	}
	
	public boolean isRunning(){
		return isrunning;
	}
}
