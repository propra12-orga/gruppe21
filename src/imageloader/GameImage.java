package imageloader;

import java.awt.Image;


import javax.swing.ImageIcon;

public class GameImage {
	private String path;
	private Image image;
	
	public GameImage(String p){
		path = p;
		ImageIcon ii = new ImageIcon(path);
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
