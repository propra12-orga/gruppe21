package mapeditor;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.ImageIcon;

import main.GameConstants;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

/**
 * Class for loading and manipulating graphics
 * 
 * @author eik
 * 
 */
public class EditorGraphics {
	static Document mapXML;

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

	public static BufferedImage loadImageAni(String absolutePath) {
		String imagePath;
		Element mapRoot;

		try {
			mapXML = new SAXBuilder().build(absolutePath);
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		mapRoot = mapXML.getRootElement();
		imagePath = mapRoot.getAttributeValue("default");

		ImageIcon ii = new ImageIcon(GameConstants.MAP_GRAPHICS_DIR + imagePath);
		Image image = ii.getImage();

		BufferedImage buff = new BufferedImage(image.getWidth(null),
				image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
		Graphics2D b = buff.createGraphics();

		b.drawImage(image, 0, 0, null);
		b.dispose();

		return (buff);
	}
}
