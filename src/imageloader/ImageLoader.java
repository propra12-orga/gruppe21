package imageloader;

import java.awt.Image;
import java.util.Vector;
import javax.swing.ImageIcon;

public class ImageLoader {
	private Vector<GameImage> imageStack = new Vector<GameImage>();
	private Vector<AnimationSet> animationStack = new Vector<AnimationSet>();
	private Image placeholder;
	
	{
	ImageIcon icon = new ImageIcon("graphics/game/placeholder.png");
	placeholder = icon.getImage();
	}
	
	
    // adds an Image to Imagestack except it does already exist
	
	public void addImage(String imagePath){
		imageStack.add(new GameImage(imagePath));
	}
	
//returns image if existing, else returns placeholder-image
	
	public Image getImage(String imagePath){
		Image rImage = placeholder;
		for(int i=0; i < imageStack.size();i++){
			if(imageStack.get(i).pathEquals(imagePath)){
				return imageStack.get(i).getImage();
			}
		}
		return rImage;
	
	}
	
// nur zum testen Später löschen
	
	public void printNames(){
		for(int i=0; i < imageStack.size();i++){
			System.out.println(imageStack.get(i).getPath());
		}
		
	}
}
