package enemies;

import imageloader.ImageLoader;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import mapobjects.Enemy;

/**
 * <b>public class Skeleton extends Enemy</b>
 * <p>
 * A Skeleton object displays an enemy which is able to move in a random
 * direction. Moreover it has the ability of shooting a skull into the moving
 * direction.
 * 
 * @author masto104
 */
public class Skeleton extends Enemy {

	/**
	 * Used to time the waits before the enemy starts moving.
	 */
	private int waitingCounter;

	/**
	 * Used to time the shoot interval.
	 */
	private int shootCounter;

	/**
	 * Used to control when shootSkull method shall be called.
	 */
	private boolean shooted = false;

	/**
	 * Skeleton constructor.
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
	public Skeleton(int x, int y, boolean v, boolean d, boolean c, String p,
			ImageLoader gr) {
		super(x, y, v, d, c, p, gr);
		speed = 2;
	}

	@Override
	public void move() {
		if (UP) {
			if (hasObjectCollision(posX, posY - speed, map.getCollisionMap())) {
				waitingCounter = 0;
				shootCounter = 0;
				shooted = false;
				findPath("enemyUp", "enemyDown", "enemyLeft", "enemyRight");
			} else {
				waitingCounter++;
				shootCounter++;
				if (!shooted) {
					shootSkull("up");
					shooted = true;
				}
				if (shootCounter > 80) {
					shootSkull("up");
					shootCounter = 0;
				}
				if (waitingCounter > 100) {
					posY -= speed;
				}
			}
		}

		if (DOWN) {
			if (hasObjectCollision(posX, posY + speed, map.getCollisionMap())) {
				waitingCounter = 0;
				shootCounter = 0;
				shooted = false;
				findPath("enemyUp", "enemyDown", "enemyLeft", "enemyRight");
			} else {
				waitingCounter++;
				shootCounter++;
				if (!shooted) {
					shootSkull("down");
					shooted = true;
				}
				if (shootCounter > 80) {
					shootSkull("down");
					shootCounter = 0;
				}
				if (waitingCounter > 100) {
					posY += speed;
				}
			}
		}

		if (LEFT) {
			if (hasObjectCollision(posX - speed, posY, map.getCollisionMap())) {
				waitingCounter = 0;
				shooted = false;
				shootCounter = 0;
				findPath("enemyUp", "enemyDown", "enemyLeft", "enemyRight");
			} else {
				waitingCounter++;
				shootCounter++;
				if (!shooted) {
					shootSkull("left");
					shooted = true;
				}
				if (shootCounter > 80) {
					shootSkull("left");
					shootCounter = 0;
				}
				if (waitingCounter > 100) {
					posX -= speed;
				}
			}
		}

		if (RIGHT) {
			if (hasObjectCollision(posX + speed, posY, map.getCollisionMap())) {
				waitingCounter = 0;
				shootCounter = 0;
				shooted = false;
				findPath("enemyUp", "enemyDown", "enemyLeft", "enemyRight");
			} else {
				waitingCounter++;
				shootCounter++;
				if (!shooted) {
					shootSkull("right");
					shooted = true;
				}
				if (shootCounter > 80) {
					shootSkull("right");
					shootCounter = 0;
				}
				if (waitingCounter > 100) {
					posX += speed;
				}
			}
		}
	}

	/**
	 * <b>public void shootSkull(String dir)</b>
	 * <p>
	 * Shoots a skull in the committed direction with a probability of 50%. The
	 * skull object will be added to the mapobjects vector of the map to be
	 * updated and draw.
	 * 
	 * @param dir
	 *            - direction the skull will move into.
	 */
	private void shootSkull(String dir) {
		int i = (int) (Math.random() * 2 + 1);

		if (i == 2) {
			Skull skull = new Skull(getPosX(), getPosY(), true, true, false,
					"skulls", map.getGraphics(), dir);
			skull.setMap(getMap());
			map.addEnemy(skull);
		}
	}

	@Override
	public void draw(Graphics2D g2d, ImageLoader gr, Graphics2D cm) {
		g2d.drawImage(animation.getCurrentImage(), posX, posY, null);
		cm.setPaint(Color.red);
		cm.fillRect(posX + 5, posY + 5, 40, 40);

	}

	@Override
	public void update(BufferedImage cm) {
		animation.animate();

		if (dying) {

			if (beforeTime + dyingTime <= System.nanoTime()) {
				setDestroyed(true);
			}
		} else {
			move();
		}
	}

}
