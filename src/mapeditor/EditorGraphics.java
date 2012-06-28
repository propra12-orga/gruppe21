package mapeditor;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

/**
 * Class for loading and manipulating graphics
 * 
 * @author eik
 * 
 */
public class EditorGraphics {

	/**
	 * Loads an image from the given path and returns as BufferedImage.
	 * 
	 * @param imagePath
	 *            the path of the image
	 * @return image as BufferedImage
	 */
	public static BufferedImage loadImage(String imagePath) {
		ImageIcon ii = new ImageIcon(imagePath);
		Image image = ii.getImage();

		BufferedImage buff = new BufferedImage(image.getWidth(null),
				image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
		Graphics2D b = buff.createGraphics();

		b.drawImage(image, 0, 0, null);
		b.dispose();

		return (buff);
	}
}
