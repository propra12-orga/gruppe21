package mapobjects;

import imageloader.ImageLoader;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * <b>public class FireBowl extends MapObject</b>
 * <p>
 * FireBowl objects are used in the boss fight. It just pictures a burning fire
 * bowl which has collision and is destructible.
 * 
 * @author masto104
 */
public class FireBowl extends MapObject {

	/**
	 * <b>public FireBowl</b>
	 * <p>
	 * FireBowl constructor.
	 * 
	 * @param x
	 *            - x-coordinate.
	 * @param y
	 *            - y-coordinate.
	 * @param v
	 *            - visibility
	 * @param d
	 *            - 'destructible-flag'
	 * @param c
	 *            - 'collision-flag'
	 * @param p
	 *            - AnimationSet filename
	 * @param gr
	 *            - ImageLoader
	 */
	public FireBowl(int x, int y, boolean v, boolean d, boolean c, String p,
			ImageLoader gr) {
		super(x, y, v, d, c, p, gr);
		animation.start("burning");
	}

	@Override
	public void update(BufferedImage collisionMap) {
		animation.animate();

		if (simpleHasColl(posX, posY, collisionMap, Color.orange)) {
			destroyed = true;
		}
	}

	@Override
	public void draw(Graphics2D g2d, ImageLoader gr, Graphics2D cmg) {
		g2d.drawImage(animation.getCurrentImage(), posX, posY, null);
		cmg.setPaint(Color.gray);
		cmg.fillRect(posX, posY, 50, 50);
	}

}
