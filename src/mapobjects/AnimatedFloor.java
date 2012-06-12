package mapobjects;

import imageloader.ImageLoader;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * mapobject for floortiles that use animated textures
 * 
 * @author eik
 * 
 */

public class AnimatedFloor extends MapObject {

	/**
	 * constructor
	 * 
	 * @param x
	 *            x coordinate
	 * @param y
	 *            y coordinate
	 * @param v
	 *            sets visibility
	 * @param d
	 *            sets destroyable
	 * @param c
	 *            sets collision
	 * @param p
	 *            image url for default
	 * @param gr
	 *            the ImageLoader of the map
	 */
	public AnimatedFloor(int x, int y, boolean v, boolean d, boolean c,
			String p, ImageLoader gr) {
		super(x, y, v, d, c, p, gr);
		animation.start("animation");
	}

	@Override
	public void draw(Graphics2D g2d, ImageLoader gr, Graphics2D cm) {
		g2d.drawImage(animation.getCurrentImage(), posX, posY, null);
		if (collides()) {
			cm.setPaint(Color.black);
			cm.fillRect(posX, posY, 50, 50);
		} else {
			cm.setPaint(Color.white);
			cm.fillRect(posX, posY, 50, 50);
		}
	}

	@Override
	public void update(BufferedImage cm) {
		animation.animate();
	}
}
