package imageloader;

import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

/**
 * super class for Gamegraphics
 * 
 * @author eik
 * 
 */
public class GameGraphic {
	protected BufferedImage image;

	/**
	 * constructor
	 * 
	 * @param p
	 *            url for default image
	 */
	public GameGraphic(String p) {
		image = toBuff(p);
	}

	/**
	 * gets image from path and writes a BufferedImage
	 * 
	 * @param p
	 *            url
	 * @return BufferedImage
	 */
	// returns bufferd image from given imagepath
	public BufferedImage toBuff(String p) {
		ImageIcon ii = new ImageIcon(p);
		Image image = ii.getImage();

		BufferedImage buff = new BufferedImage(image.getWidth(null),
				image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
		Graphics2D b = buff.createGraphics();

		b.drawImage(image, 0, 0, null);
		b.dispose();

		return toCompatibleImage(buff);
		// return buff;
	}

	/**
	 * Try optimizing image.
	 * 
	 * @param image
	 * @return
	 */
	private BufferedImage toCompatibleImage(BufferedImage image) {
		GraphicsConfiguration graphicsConfig = GraphicsEnvironment
				.getLocalGraphicsEnvironment().getDefaultScreenDevice()
				.getDefaultConfiguration();

		if (image.getColorModel().equals(graphicsConfig.getColorModel()))
			return image;

		BufferedImage new_image = graphicsConfig.createCompatibleImage(
				image.getWidth(), image.getHeight(), image.getTransparency());
		Graphics2D g2d = (Graphics2D) new_image.getGraphics();

		g2d.drawImage(image, 0, 0, null);
		g2d.dispose();
		return new_image;
	}

	public BufferedImage getImage() {
		return image;
	}

}