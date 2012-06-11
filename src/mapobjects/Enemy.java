package mapobjects;

import imageloader.ImageLoader;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

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
public class Enemy extends MoveableObject {

	/**
	 * True, if Enemy drops some item after dying.
	 */
	private boolean hiddenObject = false;

	/**
	 * Direction variable.
	 */
	private boolean UP, DOWN, RIGHT, LEFT;

	/**
	 * It set to true when Enemy gets struck by a bomb.
	 */
	private boolean dying;

	/**
	 * For the dying animation timer.
	 */
	private long beforeTime, dyingTime = 800000000;

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
	 * <b>public void stop()</b>
	 * <p>
	 * Sets the four moving variables to false.
	 */
	private void stop() {
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
	@Override
	public void move() {
		// TODO also move hidden Object
		if (UP) {
			if (hasObjectCollision(posX, posY - speed, map.getCollisionMap(),
					"UP")) {
				findPath();

			} else {
				posY -= speed;
			}
		}

		if (DOWN) {
			if (hasObjectCollision(posX, posY + speed, map.getCollisionMap(),
					"DOWN")) {
				findPath();
			} else {
				posY += speed;
			}
		}

		if (LEFT) {
			if (hasObjectCollision(posX - speed, posY, map.getCollisionMap(),
					"LEFT")) {
				findPath();
			} else {
				posX -= speed;
			}
		}

		if (RIGHT) {
			if (hasObjectCollision(posX + speed, posY, map.getCollisionMap(),
					"RIGHT")) {
				findPath();
			} else {
				posX += speed;
			}
		}
	}

	/**
	 * <b>public void findPath()</b>
	 * <p>
	 * Chooses a random direction. Through a random number one of the four
	 * moving variables is set to true. Additionally the animation which matches
	 * to the moving direction is chosen.
	 */
	private void findPath() {
		int choice = (int) (Math.random() * 4 + 1);
		stop();

		switch (choice) {
		case 1:
			UP = true;
			animation.change("enemyUp");
			break;
		case 2:
			DOWN = true;
			animation.change("enemyDown");
			break;
		case 3:
			LEFT = true;
			animation.change("enemyLeft");
			break;
		case 4:
			RIGHT = true;
			animation.change("enemyRight");
			break;
		default:
			findPath();
		}
	}

	@Override
	public void draw(Graphics2D g2d, ImageLoader gr, Graphics2D cm) {
		g2d.drawImage(animation.getCurrentImage(), posX, posY, null);
		cm.setPaint(Color.red);
		cm.fillRect(posX, posY, 50, 50);

	}

	@Override
	public void update(BufferedImage cm) {
		animation.animate();

		if (dying) {

			if (beforeTime + dyingTime <= System.nanoTime()) {
				this.destroyed = true;
				// this.map.decreaseEnemies();
			}
		} else {
			move();
		}
	}

	// hidden object released on die
	public void addHiddenObject() {
	}

	/**
	 * <b>public void die()</b>
	 * <p>
	 * This method is called when the Enemy object has been struck by a bomb.
	 * The animation changes to the dying animation. The starting point of the
	 * dying animation is set.
	 */
	public void die() {
		dying = true;
		animation.change("enemyDying");
		beforeTime = System.nanoTime();
	}

	/**
	 * <b>public boolean hasObjectCollision(int x, int y, BufferedImage cm,
	 * String dir)</b>
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
	 * @param dir
	 *            - Moving direction
	 * @return True, if the Enemy object collides with the frame borders or
	 *         fixed mapObjects. False, if there is no collision.
	 */
	@Override
	public boolean hasObjectCollision(int x, int y, BufferedImage cm, String dir) {
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
					die();
				} else if (test.equals(Color.green)) {
					map.getMapPlayer().die();
					map.finishMap();
				}
			}
		}
		return false;
	}
}
