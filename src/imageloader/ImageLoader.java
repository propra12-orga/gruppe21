package imageloader;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.Vector;

import javax.swing.ImageIcon;

/**
 * Class for loading the images and animationsets that are used in the game
 * 
 * @author eik
 * 
 */

public class ImageLoader {
	private Vector<GameImage> imageStack = new Vector<GameImage>();
	private Vector<AnimationSet> animationStack = new Vector<AnimationSet>();
	private BufferedImage placeholder;

	{
		ImageIcon icon = new ImageIcon("graphics/game/placeholder.png");
		Image temp = icon.getImage();
		BufferedImage placeholder = new BufferedImage(temp.getWidth(null),
				temp.getHeight(null), BufferedImage.TYPE_INT_ARGB);
		Graphics2D b = placeholder.createGraphics();

		b.drawImage(temp, 0, 0, null);
		b.dispose();

	}

	// TODO image/animation zusammenfassen
	// adds an Image to Imagestack except it does already exist

	/**
	 * 
	 * adds an image to the imagevector
	 * 
	 * @param imagePath
	 *            url of the image
	 */

	public void addImage(String imagePath, int rotation) {
		boolean gameimageexists = false;
		// is Image already in stack
		for (int i = 0; i < imageStack.size(); i++) {
			if (imageStack.get(i).pathEquals(imagePath)
					&& imageStack.get(i).getRotation() == rotation) {
				gameimageexists = true;
				break;
			}
		}
		// add if not
		if (!gameimageexists) {
			imageStack.add(new GameImage(imagePath, nameFromPath(imagePath),
					rotation));
		}
	}

	/**
	 * adds an animationset to the animationvector of the imageloader
	 * 
	 * @param setName
	 *            name of the set
	 * @param type
	 *            specifies type and defines the folder where the as is located
	 */

	public void addAnimationSet(String setName, String type) {
		boolean animationexists = false;
		for (int i = 0; i < animationStack.size(); i++) {
			if (animationStack.get(i).nameEquals(setName)) {
				animationexists = true;
				break;
			}

		}
		if (!animationexists) {
			animationStack.add(new AnimationSet(setName, type));
		}
	}

	/**
	 * gets an image from the imagevector
	 * 
	 * @param imagePath
	 *            url of the image
	 * @return Buffered Image
	 */

	public BufferedImage getImage(String imagePath) {
		for (int i = 0; i < imageStack.size(); i++) {
			if (imageStack.get(i).pathEquals(imagePath)) {
				return imageStack.get(i).getImage();
			}
		}
		return placeholder;
	}

	/**
	 * gets an image from the imagevector
	 * 
	 * @param imagePath
	 *            url of the image
	 * @param rotation
	 *            image rotation
	 * @return Buffered Image
	 */

	public BufferedImage getImage(String imagePath, int rotation) {
		for (int i = 0; i < imageStack.size(); i++) {
			if (imageStack.get(i).pathEquals(imagePath)
					&& imageStack.get(i).getRotation() == rotation) {
				return imageStack.get(i).getImage();
			}
		}
		return placeholder;
	}

	/**
	 * gets an AnimationSet
	 * 
	 * @param n
	 *            AnimationSet name
	 * @return AnimationSet
	 */
	public AnimationSet getAnimationSet(String n) {
		for (int i = 0; i < animationStack.size(); i++) {
			if (animationStack.get(i).nameEquals(n)) {
				return animationStack.get(i);
			}
		}
		return null;
	}

	// nur zum testen Später löschen

	public void printNames() {
		for (int i = 0; i < imageStack.size(); i++) {
			System.out.println(imageStack.get(i).getPath());
		}
		for (int i = 0; i < animationStack.size(); i++) {
			System.out.println(animationStack.get(i).getSetName());
		}

	}

	/**
	 * extracts a filename from an url without file extension
	 * 
	 * @param n
	 *            url
	 * @return filename as string
	 */
	public String nameFromPath(String n) {
		return n.split("/")[n.split("/").length - 1];
	}

}
