package mapobjects;

import imageloader.ImageLoader;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import main.GameConstants;

/**
 * the exit mapobject
 * 
 * @author eik
 * 
 */

public class Exit extends MapObject {
	private boolean activated = false;

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
	public Exit(int x, int y, int r, boolean v, boolean d, boolean c, String p) {
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
		if (collides()) {
			if (activated) {
				cm.setPaint(Color.yellow);
				cm.fillRect(posX + GameConstants.TILE_SIZE / 3, posY
						+ GameConstants.TILE_SIZE / 3,
						GameConstants.TILE_SIZE / 3,
						GameConstants.TILE_SIZE / 3);
			} else {
				cm.setPaint(Color.black);
				cm.fillRect(posX, posY, GameConstants.TILE_SIZE,
						GameConstants.TILE_SIZE);
			}

		} else {
			cm.setPaint(Color.white);
			cm.fillRect(posX, posY, 50, 50);
		}
	}

	/**
	 * overrides super method
	 * 
	 * 
	 * @cm collision map
	 */
	@Override
	public void update(BufferedImage cm) {
	}

	/**
	 * activates the exit and makes it visible
	 */
	public void activate() {
		imageUrl = GameConstants.MAP_GRAPHICS_DIR + "exit.png";
		activated = true;
	}

}
