package mapeditor;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
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

	public static BufferedImage resize(int x, int y, BufferedImage image) {
		BufferedImage resImage = new BufferedImage(x, y,
				BufferedImage.TYPE_INT_ARGB);

		Graphics2D g2d = resImage.createGraphics();
		g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
				RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2d.drawImage(image, 0, 0, x, y, null);

		g2d.dispose();
		return resImage;
	}
}
