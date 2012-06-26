package mapobjects;

import imageloader.ImageLoader;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

/**
 * <b>public class Enemy extends MoveableObject</b>
 * <p>
 * The Enemy class defines the structure of an enemy object. It includes all the
 * methods which are called by the map, like update() an draw(), and some other
 * methods, e.g. those which are responsible for moving the Enemy object or
 * checking for collision.
 * 
 * @author masto104
 */
public abstract class Enemy extends MoveableObject {

	/**
	 * Direction variable.
	 */
	public boolean UP, DOWN, RIGHT, LEFT;

	/**
	 * Is set to true when Enemy gets struck by a bomb.
	 */
	public boolean dying;

	/**
	 * For the dying animation timer.
	 */
	public long beforeTime, dyingTime = 800000000;

	/**
	 * Enemy constructor.
	 * 
	 * @param x
	 *            - x-coordinate.
	 * @param y
	 *            - y-coordinate.
	 * @param v
	 *            - sets visibility.
	 * @param d
	 *            - sets 'destructible' flag.
	 * @param c
	 *            - sets 'collision' flag
	 * @param p
	 *            - AnimationSet filename
	 * @param gr
	 *            - ImageLoader
	 */
	public Enemy(int x, int y, boolean v, boolean d, boolean c, String p,
			ImageLoader gr) {
		super(x, y, v, d, c, p, gr);
		UP = false;
		DOWN = false;
		RIGHT = true; // start direction must be given
		LEFT = false;
		speed = 1;
	}

	/**
	 * Destroy or revive a MapObject. Call lookForUpgrades().
	 * 
	 * @param destroyed
	 *            new MapObject status.
	 */
	public void setDestroyed(boolean destroyed) {
		this.destroyed = destroyed;
		lookForUpgrades();
	}

	/**
	 * <b>public void stop()</b>
	 * <p>
	 * Sets the four moving variables to false.
	 */
	public void stop() {
		UP = false;
		DOWN = false;
		LEFT = false;
		RIGHT = false;
	}

	/**
	 * <b>public void move()</b>
	 * <p>
	 * Moves the Enemy object over the panel. Checks the ability of moving into
	 * the relevant direction. If ability is given the Enemy object will be
	 * moved for a fixed number of pixels, if not findPath() will be called.
	 */
	public abstract void move();

	/**
	 * <b>public void findPath()</b>
	 * <p>
	 * Chooses a random direction. Through a random number one of the four
	 * moving variables is set to true. Additionally the animation which matches
	 * to the moving direction is chosen.
	 */
	public void findPath(String up, String down, String left, String right) {
		int choice = (int) (Math.random() * 4 + 1);
		stop();

		switch (choice) {
		case 1:
			UP = true;
			animation.change(up);
			break;
		case 2:
			DOWN = true;
			animation.change(down);
			break;
		case 3:
			LEFT = true;
			animation.change(left);
			break;
		case 4:
			RIGHT = true;
			animation.change(right);
			break;
		default:
			findPath(up, down, left, right);
		}
	}

	@Override
	public abstract void draw(Graphics2D g2d, ImageLoader gr, Graphics2D cm);

	@Override
	public abstract void update(BufferedImage cm);

	/**
	 * <b>private void lookForUpgrades()</b>
	 * <p>
	 * Creates an random upgrade object with a probability of 50%. This happens
	 * by putting all possible upgrades into a list and choosing a random one of
	 * it.
	 */
	public void lookForUpgrades() {
		int i = (int) (Math.random() * 2) + 1;
		if (i == 1 && !map.hasReachedMaxUpgrades()) {
			Random rnd = new Random();
			ArrayList<Color> list = new ArrayList<Color>();

			list.add(Color.pink);
			list.add(Color.blue);
			list.add(Color.cyan);
			list.add(Color.magenta);
			list.add(Color.lightGray);

			Color upgradeColor = list.get(rnd.nextInt(list.size()));

			Upgrade upgrade = new Upgrade(posX, posY, true, true, true,
					"upgrades", map.getGraphics(), upgradeColor);
			upgrade.setMap(getMap());
			map.getMapObjects().get(1).add(upgrade);
			map.incrementUpgradeCounter();
		}
	}

	/**
	 * <b>public void die()</b>
	 * <p>
	 * This method is called when the Enemy object has been struck by a bomb.
	 * The animation changes to the dying animation. The starting point of the
	 * dying animation is set.
	 */
	public void die(String dyingAnimation) {
		dying = true;
		animation.change(dyingAnimation);
		beforeTime = System.nanoTime();
	}

	/**
	 * <b>public boolean hasObjectCollision(int x, int y, BufferedImage cm)</b>
	 * <p>
	 * Checks for frame border and object collision. Calls die() method if Enemy
	 * object hit the bomb explosion. Kills of the player and finishes the map
	 * if the player hits the Enemy object.
	 * 
	 * @param x
	 *            - x-coordinate
	 * @param y
	 *            - y-coordinate
	 * @param cm
	 *            - CollisionMap
	 * @return True, if the Enemy object collides with the frame borders or
	 *         fixed mapObjects. False, if there is no collision.
	 */
	public boolean hasObjectCollision(int x, int y, BufferedImage cm) {
		if (x < 0 || y < 0 || x > cm.getWidth() - 50 || y > cm.getHeight() - 50) {
			return true;
		}
		BufferedImage collTest = cm.getSubimage(x, y, 50, 50);
		for (int i = 0; i < collTest.getWidth(); i++) {
			for (int j = 0; j < collTest.getHeight(); j++) {
				Color test = new Color(collTest.getRGB(i, j));
				if (test.equals(Color.black) || test.equals(Color.gray)) {
					return true;
				} else if (test.equals(Color.orange)) {
					die("enemyDying");
				} else if (test.equals(Color.darkGray)) {
					die("enemyDying");
				}
			}
		}
		return false;
	}
}
