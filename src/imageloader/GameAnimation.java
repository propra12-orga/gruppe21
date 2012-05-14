package imageloader;

import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

// saves a picture with amount of frames hold in it

public class GameAnimation extends GameGraphic{
	String name;
	int frames;
	Image image;
	
	public GameAnimation(String n,int f,String p){
		name = n;
		frames = f;
		ImageIcon ii = new ImageIcon(p);
        image = ii.getImage();
	}
	
	public int getFrames(){
		return frames;
	}
	
	//TODO bild in frames zerlegen
	public Image getImage(int frame){
		return image;
	}
	
	public boolean nameEquals(String n){
		return name.equals(n);
	}
}
