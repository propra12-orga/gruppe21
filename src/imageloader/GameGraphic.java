package imageloader;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

public class GameGraphic {
	protected BufferedImage image;
	
	public GameGraphic(String p){
		image = toBuff(p);
	}

	// returns bufferd image from given imagepath
	public BufferedImage toBuff(String p){
		ImageIcon ii = new ImageIcon(p);
		Image image = ii.getImage();
		
		BufferedImage buff = new BufferedImage(image.getWidth(null),image.getHeight(null),BufferedImage.TYPE_INT_ARGB);
		Graphics2D b = buff.createGraphics();
		
		b.drawImage(image,0,0,null);
		b.dispose();
		
		return buff;
	}
}