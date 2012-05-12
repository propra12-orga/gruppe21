package imageloader;

import java.awt.Image;
import java.awt.image.BufferedImage;


import javax.swing.ImageIcon;

public class GameImage extends GameGraphic{
	private String path;
	private Image image;
	
	public GameImage(String p){
		path = p;
		ImageIcon ii = new ImageIcon(p);
        image = ii.getImage();
	}
	
	public Image getImage(){
		return image;
	}

	public boolean pathEquals(String inS) {
		return(path.equals(inS));
	}

	public String getPath() {
		return path;
	}
	
}
