package imageloader;

import java.awt.Image;

// saves a picture with amount of frames hold in it

public class GameAnimation extends GameGraphic{
	String name;
	int frames;
	Image image;
	
	public GameAnimation(String n,int f,Image i){
		name = n;
		frames = f;
		image = i;
	}
	
	public int getFrames(){
		return frames;
	}
	
	public Image getImage(){
		return image;
	}
	
	public boolean nameEquals(String n){
		return name.equals(n);
	}
}
