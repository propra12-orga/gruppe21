package mapobjects;

import imageloader.ImageLoader;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * mapobject for effects like smoke etc.
 * 
 * @author eik
 * 
 */
public class Effect extends MapObject {

	/**
	 * constructor
	 * 
	 * @param x
	 *            x position
	 * @param y
	 *            y position
	 * @param r
	 *            rotation of the graphic (0,1,2,3)
	 * @param v
	 *            sets visibility
	 * @param d
	 *            sets destroyable flag
	 * @param c
	 *            sets collision
	 * @param p
	 *            image url
	 */
	public Effect(int x, int y, int r, boolean v, boolean d, boolean c, String p) {
		super(x, y, r, v, d, c, p);
	}

	/**
	 * overrides super method
	 * 
	 * draws the image on the game canvas draws the collision map
	 */
	@Override
	public void draw(Graphics2D g2d, ImageLoader gr, Graphics2D cm) {
		g2d.drawImage(gr.getImage(imageUrl), posX, posY, null);
	}

	/**
	 * overrides super method
	 * 
	 * @cm collision map
	 */
	@Override
	public void update(BufferedImage cm) {
	}
}
