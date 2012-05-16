package imageloader;

import java.awt.Image;
import java.awt.image.BufferedImage;


import javax.swing.ImageIcon;

public class GameImage extends GameGraphic{
	private String path;
	String name;
	
	public GameImage(String p,String n){
		super(p);
		path = p;
		name = n;
	}
	
	public BufferedImage getImage(){
		return image;
	}

	public boolean pathEquals(String inS) {
		return(path.equals(inS));
	}

	public String getPath() {
		return path;
	}

	public boolean nameEquals(String n) {
		return name.equals(n);
	}
	
}
