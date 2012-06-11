package imageloader;

import java.awt.image.BufferedImage;

/**
 * Holds an image that will be used as animation
 * 
 * 
 * @author eik
 * 
 */
public class GameAnimation extends GameGraphic {
	String name;
	int frames;
	private int stretch = 1;

	/**
	 * constructor
	 * 
	 * 
	 * @param n
	 *            name of the animation
	 * @param f
	 *            amount of frames hold in the image
	 * @param p
	 *            url of the image
	 * @param st
	 *            stretchfactor of the animation
	 */
	public GameAnimation(String n, int f, String p, int st) {
		super(p);
		name = n;
		frames = f;
		stretch = st;
	}

	/**
	 * 
	 * @return amount of frames as integer
	 */
	public int getFrames() {
		return frames;
	}

	/**
	 * calculates the requested frame in from the image
	 * 
	 * @param frame
	 * @return a BufferedImage of the requested frame
	 */
	public BufferedImage getFrame(int frame) {
		return image.getSubimage(50 * frame, 0, 50, 50);
	}

	/**
	 * 
	 * @return the hole image as BufferedImage
	 */
	public BufferedImage getImage() {
		return image;
	}

	/**
	 * Compares a given string to the name
	 * 
	 * @param n
	 *            string to compare
	 * @return true if equals else not
	 */
	public boolean nameEquals(String n) {
		return name.equals(n);
	}

	/**
	 * checks if the given number is greater then frames-1
	 * 
	 * @param ac
	 *            number
	 * @return true if greater , else false
	 */
	public boolean end(int ac) {
		return ac >= frames - 1;
	}

	/**
	 * 
	 * @return animation stretch as int
	 */
	public int getStretch() {
		return stretch;
	}
}
