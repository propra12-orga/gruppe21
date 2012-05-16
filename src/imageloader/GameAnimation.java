package imageloader;

import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

// saves a picture with amount of frames hold in it

public class GameAnimation extends GameGraphic{
	String name;
	int frames;
	
	
	public GameAnimation(String n,int f,String p){
		super(p);
		name = n;
		frames = f;
	}
	
	public int getFrames(){
		return frames;
	}
	
	public BufferedImage getFrame(int frame){
		return image.getSubimage(50*frame, 0, 50, 50);
	}
	
	public BufferedImage getImage(){
		return image;
	}
	
	public boolean nameEquals(String n){
		return name.equals(n);
	}

	public boolean end(int ac) {
		return ac>=frames-1;
	}
}
