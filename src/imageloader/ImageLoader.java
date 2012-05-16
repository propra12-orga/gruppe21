package imageloader;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.Vector;
import javax.swing.ImageIcon;

public class ImageLoader {
	private Vector<GameImage> imageStack = new Vector<GameImage>();
	private Vector<AnimationSet> animationStack = new Vector<AnimationSet>();
	private BufferedImage placeholder;
	
	{
	ImageIcon icon = new ImageIcon("graphics/game/placeholder.png");
	Image temp = icon.getImage();
	BufferedImage placeholder = new BufferedImage(temp.getWidth(null),temp.getHeight(null),BufferedImage.TYPE_INT_ARGB);
	Graphics2D b = placeholder.createGraphics();
	
	b.drawImage(temp,0,0,null);
	b.dispose();
	
	}
	
	//TODO image/animation zusammenfassen
    // adds an Image to Imagestack except it does already exist
	
	public void addImage(String imagePath){
		boolean gameimageexists = false;
		// is Image already in stack
		for(int i=0; i < imageStack.size();i++){
			if(imageStack.get(i).pathEquals(imagePath)){
				gameimageexists = true;
				break;
			}
		}
		// add if not
		if(!gameimageexists){imageStack.add(new GameImage(imagePath,nameFromPath(imagePath)));}
	}
											  // type für pfadspezifikation
	public void addAnimationSet(String setName,String type){
		boolean animationexists = false;
		for(int i=0; i< animationStack.size(); i++){
			if(animationStack.get(i).nameEquals(setName)){
				animationexists = true;
				break;
			}
			
		}
		if(!animationexists){animationStack.add(new AnimationSet(setName,type));}
	}
	
//returns image if existing, else returns placeholder-image
	
	public BufferedImage getImage(String imagePath){
		for(int i=0; i < imageStack.size();i++){
			if(imageStack.get(i).pathEquals(imagePath)){
				return imageStack.get(i).getImage();
			}
		}
		return placeholder;
	}

//returns animationSet
	public AnimationSet getAnimationSet(String n) {
		for(int i=0; i<animationStack.size(); i++){
			if(animationStack.get(i).nameEquals(n)){
				return animationStack.get(i);
			}
		}
		return null;
	}	
	
// nur zum testen Später löschen
	
	public void printNames(){
		for(int i=0; i < imageStack.size();i++){
			System.out.println(imageStack.get(i).getPath());
		}
		for(int i=0; i < animationStack.size();i++){
			System.out.println(animationStack.get(i).getSetName());
		}
		
	}

	public String nameFromPath(String n){
		return n.split("/")[n.split("/").length-1];
	}

}


