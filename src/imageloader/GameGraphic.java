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
	protected int rotation;

	/**
	 * constructor
	 * 
	 * @param p
	 *            url for default image
	 */
	public GameGraphic(String p) {
		rotation = 0;
		image = toBuff(p, rotation);
	}

	public GameGraphic(String p, int rotation) {
		this.rotation = rotation;
		image = toBuff(p, rotation);
	}

	/**
	 * gets image from path and writes a BufferedImage
	 * 
	 * @param p
	 *            url
	 * @param rotation
	 *            image rotation
	 * @return BufferedImage
	 * 
	 */
	public BufferedImage toBuff(String p, int rotation) {
		this.rotation = rotation;
		ImageIcon ii = new ImageIcon(p);
		Image image = ii.getImage();

		BufferedImage buff = new BufferedImage(image.getWidth(null),
				image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
		Graphics2D b = buff.createGraphics();

		b.drawImage(image, 0, 0, null);
		b.dispose();
		if (rotation != 0)
			buff = rotate(buff, rotation);
		return toCompatibleImage(buff);
		// return buff;
	}

	/**
	 * Try optimizing image.
	 * 
	 * @param image
	 * @return
	 */
	public static BufferedImage toCompatibleImage(BufferedImage image) {
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

	/**
	 * Rotate a BufferedImage using Math.sin() and Math.cos().
	 * 
	 * @param original
	 *            the image to rotate
	 * @param degc
	 *            the amount of degrees.
	 * @return copy of original image rotated by 'degc' degrees.
	 */
	public static BufferedImage rotate(BufferedImage original, int degc) {
		if (degc != 0) {
			BufferedImage rotated = new BufferedImage(50, 50,
					BufferedImage.TYPE_INT_ARGB);
			for (int i = 0; i < original.getWidth(); i++) {
				for (int j = 0; j < original.getHeight(); j++) {
					int cx = (i
							* (int) (Math.cos(Math
									.toRadians((double) degc * 90.00))) - j
							* (int) Math.sin(Math
									.toRadians((double) degc * 90.00)));
					int cy = (i
							* (int) Math.sin(Math
									.toRadians((double) degc * 90.00)) + j
							* (int) Math.cos(Math
									.toRadians((double) degc * 90.00)));
					if (degc == 1) {
						if (cx <= 0) {
							cx += 49;
						}
					} else if (degc == 3) {
						if (cy <= 0) {
							cy += 49;
						}
					} else {
						if (cx <= 0) {
							cx += 49;
						}
						if (cy <= 0) {
							cy += 49;
						}
					}
					rotated.setRGB((int) Math.floor(cx), (int) Math.floor(cy),
							original.getRGB(i, j));
				}

			}
			return rotated;
		} else
			return original;
	}

	public int getRotation() {
		return rotation;
	}

}