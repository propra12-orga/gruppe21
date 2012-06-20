package mapobjects;

import imageloader.ImageLoader;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

/**
 * mapobject for simple walls
 * 
 * 
 * @author eik
 * 
 */

public class Wall extends MapObject {
	private boolean hiddenObject;

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
	public Wall(int x, int y, int r, boolean v, boolean d, boolean c, String p) {
		super(x, y, r, v, d, c, p);
	}

	/**
	 * Destroy or revive a MapObject. Call lookForUgrades().
	 * 
	 * @param destroyed
	 *            new MapObject status.
	 */
	public void setDestroyed(boolean destroyed) {
		this.destroyed = destroyed;
		lookForUpgrades();
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
			if (destroyable) {
				cm.setPaint(Color.gray);
			} else {
				cm.setPaint(Color.black);
			}
			cm.fillRect(posX, posY, 50, 50);
		} else {
			cm.setPaint(Color.white);
			cm.fillRect(posX, posY, 50, 50);
		}
	}

	/**
	 * Creates an random upgrade object with a probability of 50%. This happens
	 * by putting all possible upgrades into a list and choosing a random one of
	 * it.
	 */
	private void lookForUpgrades() {
		// int i = (int) (Math.random() * 2 + 1);
		int i = 1;
		if (i == 1) {
			Random rnd = new Random();
			ArrayList<Color> list = new ArrayList<Color>();

			list.add(Color.pink);
			list.add(Color.blue);
			list.add(Color.cyan);
			list.add(Color.magenta);

			Color upgradeColor = list.get(rnd.nextInt(list.size()));

			Upgrade upgrade = new Upgrade(getPosX(), getPosY(), true, true,
					true, "upgrades", map.getGraphics(), upgradeColor);
			upgrade.setMap(getMap());
			map.getMapObjects().get(1).add(upgrade);
		}
	}

	/**
	 * overrides super method
	 * 
	 * if the wall is destroyable , wall is checked for collision with bomb
	 * 
	 * @param cm
	 *            collision map
	 */
	@Override
	public void update(BufferedImage cm) {
		if (isDestroyable()) {
			if (simpleHasColl(posX, posY, cm, Color.orange)) {

				visible = false;
				setDestroyed(true);
			}
		}
	}

	public void addHiddenObject() {
	}

	public void destroy() {
	}
}
