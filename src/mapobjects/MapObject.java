package mapobjects;

import imageloader.Animation;
import imageloader.ImageLoader;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import main.GameConstants;
import map.Map;

/**
 * Abstract superclass of all MapObjects. Includes various boolean variables
 * that describe basic attributes such as visibility and collision behavior as
 * well as a reference to the enclosing Map object and integer values
 * representing its x and y coordinates on the map.
 * 
 * @author xmrn
 * 
 */
public abstract class MapObject {
	/**
	 * The x-coordinate.
	 */
	protected int posX;
	/**
	 * The y-coordinate.
	 */
	protected int posY;
	/**
	 * May be used to rotate the visual representation of a MapObject.
	 */
	protected int rotation = 0;
	/**
	 * Determines if a MapObject is going to be drawn to the screen.
	 */
	protected boolean visible;
	/**
	 * Tags a MapObject as capable of being destroyed.
	 */
	protected boolean destroyable;
	/**
	 * May be used to identify a destroyed MapObject.
	 */
	protected boolean destroyed = false;
	/**
	 * Determines if this MapObject acts as a barrier to other moving
	 * MapObjects.
	 */
	protected boolean collision;
	/**
	 * The filename of an image visually representing the MapObject.
	 */
	protected String imageUrl;
	/**
	 * An animation consists of a series of pictures. May be used to animate
	 * moving MapObjects or the like.
	 */
	protected Animation animation;

	/**
	 * Reference to the enclosing Map object.
	 */
	protected Map map;

	/**
	 * Standard constructor.
	 * 
	 * @param x
	 *            x-coordinate.
	 * @param y
	 *            y-coordinate.
	 * @param v
	 *            sets visibility.
	 * @param d
	 *            sets 'destroyable' flag.
	 * @param c
	 *            sets 'collision' flag.
	 */
	public MapObject(int x, int y, boolean v, boolean d, boolean c) {
		posX = x;
		posY = y;
		visible = v;
		destroyable = d;
		collision = c;
	}

	/**
	 * Create a MapObject using the standard constructor but additionally
	 * passing a string representing a simple image file.
	 * 
	 * @param x
	 *            x-coordinate.
	 * @param y
	 *            y-coordinate.
	 * @param r
	 *            an integer value describing the image rotation.
	 * @param v
	 *            sets visibility.
	 * @param d
	 *            sets 'destroyable' flag.
	 * @param c
	 *            sets 'collision' flag.
	 * @param img
	 *            image filename
	 */
	public MapObject(int x, int y, int r, boolean v, boolean d, boolean c,
			String img) {
		this(x, y, v, d, c);
		rotation = r;
		imageUrl = GameConstants.MAP_GRAPHICS_DIR + img;
	}

	/**
	 * Create a MapObject using the standard constructor but passing an xml file
	 * that references a series of pictures as well as an ImageLoader object to
	 * form an animation.
	 * 
	 * @param x
	 *            x-coordinate.
	 * @param y
	 *            y-coordinate.
	 * @param v
	 *            sets visibility.
	 * @param d
	 *            sets 'destroyable' flag.
	 * @param c
	 *            sets 'collision' flag.
	 * @param img
	 *            xml filename
	 * @param gr
	 *            ImageLoader to manage animation
	 */
	public MapObject(int x, int y, boolean v, boolean d, boolean c, String img,
			ImageLoader gr) {
		this(x, y, v, d, c);
		animation = new Animation(img, gr);
	}

	/**
	 * Updates a MapObject's status. May be used to move or animate a MapObject.
	 * 
	 * @param collisionMap
	 */
	public abstract void update(BufferedImage collisionMap);

	/**
	 * Delegates the drawing process to an individual MapObject.
	 * 
	 * @param g2d
	 *            a Graphics object of the actual drawing canvas.
	 * @param gr
	 *            an ImageLoader to request images if necessary.
	 * @param cmg
	 *            a Graphics object to draw onto the collision map.
	 */
	public abstract void draw(Graphics2D g2d, ImageLoader gr, Graphics2D cmg);

	/**
	 * Get the filename of the MapObject's image.
	 * 
	 * @return image filename
	 */
	public String getImageUrl() {
		return imageUrl;
	}

	/**
	 * Get the x position of the MapObject.
	 * 
	 * @return x-coordinate.
	 */
	public int getPosX() {
		return posX;
	}

	/**
	 * Set the x position of the MapObject
	 * 
	 * @param x
	 *            new x-coordinate.
	 */
	public void setPosX(int x) {
		posX = x;
	}

	/**
	 * Get the y position of the MapObject.
	 * 
	 * @return y-coordinate.
	 */
	public int getPosY() {
		return posY;
	}

	/**
	 * Set the y position of the MapObject
	 * 
	 * @param y
	 *            new y-coordinate.
	 */
	public void setPosY(int y) {
		posY = y;
	}

	/**
	 * Request the status of the 'destroyable' flag.
	 * 
	 * @return destroyable.
	 */
	public boolean isDestroyable() {
		return destroyable;
	}

	/**
	 * Set the 'destroyable' flag to the given argument.
	 * 
	 * @param b
	 */
	public void setDestroyable(boolean b) {
		destroyable = b;
	}

	/**
	 * Check for a MapObject's 'destroyed' flag.
	 * 
	 * @return 'destroyed' flag.
	 */
	public boolean isDestroyed() {
		return destroyed;
	}

	/**
	 * Destroy or revive a MapObject.
	 * 
	 * @param destroyed
	 *            new MapObject status.
	 */
	public void setDestroyed(boolean destroyed) {
		this.destroyed = destroyed;
	}

	/**
	 * Check for a MapObject's visibility.
	 * 
	 * @return 'visible flag.
	 */
	public boolean isVisible() {
		return visible;
	}

	/**
	 * Set 'visible' flag to the given argument.
	 * 
	 * @param b
	 *            new value for 'visible'.
	 */
	public void setVisible(boolean b) {
		visible = b;
	}

	/**
	 * Check for an MapObject's collision behavior.
	 * 
	 * @return 'collision' flag.
	 */
	public boolean collides() {
		return collision;
	}

	/**
	 * Set an MapObject's 'collision' flag to the given argument.
	 * 
	 * @param b
	 *            new 'collision' behavior.
	 */
	public void setCollision(boolean b) {
		collision = b;
	}

	/**
	 * Request the enclosing Map object.
	 * 
	 * @return map.
	 */
	public Map getMap() {
		return map;
	}

	/**
	 * Set the 'map' variable to the given argument.
	 * 
	 * @param map
	 *            new map.
	 */
	public void setMap(Map map) {
		this.map = map;
	}

	/**
	 * Allows checking for different colors (representing MapObjects) within a
	 * rectangular segment (length: GameConstants.TILE_SIZE) of the given
	 * collision map.
	 * 
	 * @param x
	 *            x-coordinate of the segment
	 * @param y
	 *            y-coordinate of the segment
	 * @param cm
	 *            collision map
	 * @param ca
	 *            colors
	 * @return true, if colors were found, false otherwise.
	 */
	public boolean simpleHasColl(int x, int y, BufferedImage cm, Color... ca) {
		if (x < 0 || y < 0 || x > cm.getWidth() - GameConstants.TILE_SIZE
				|| y > cm.getHeight() - GameConstants.TILE_SIZE) {
			return true;
		}
		BufferedImage collTest = cm.getSubimage(x, y, GameConstants.TILE_SIZE,
				GameConstants.TILE_SIZE);
		for (int i = 0; i < collTest.getWidth(); i++) {
			for (int j = 0; j < collTest.getHeight(); j++) {
				Color test = new Color(collTest.getRGB(i, j));
				for (Color color : ca) {
					if (test.equals(color)) {
						return true;
					}
				}
			}
		}
		return false;
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
	public BufferedImage rotate(BufferedImage original, int degc) {
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
}
