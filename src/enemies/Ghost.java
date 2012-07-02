package enemies;

import imageloader.ImageLoader;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import mapobjects.Enemy;

/**
 * <b>public class Ghost extends Enemy</b>
 * <p>
 * A Ghost object displays an enemy which is able to move through destructible
 * walls. The enemy chooses a random direction when it has come out of a wall.
 * 
 * @author masto104
 */
public class Ghost extends Enemy {

	/**
	 * Used to decrease the speed while moving inside walls.
	 */
	private int walldelay;

	/**
	 * Used to decrease the speed.
	 */
	private int walkdelay;

	/**
	 * True, if the enemy is inside a wall.
	 */
	private boolean wallWalking = false;

	/**
	 * True, if enemy has completely left a wall.
	 */
	private boolean comingOutOfWall = false;

	/**
	 * Ghost constructor.
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
	public Ghost(int x, int y, boolean v, boolean d, boolean c, String p,
			ImageLoader gr) {
		super(x, y, v, d, c, p, gr);
	}

	@Override
	public void move() {
		if (UP) {
			if (simpleHasColl(posX, posY - speed, map.getCollisionMap(),
					Color.black)) {
				findPath("enemyUp", "enemyDown", "enemyLeft", "enemyRight");
			} else {
				posY -= speed;
			}
			if (wallWalking) {
				animation.change("enemyUp_wall");
			} else {
				animation.change("enemyUp");
			}
		}

		if (DOWN) {
			if (simpleHasColl(posX, posY + speed, map.getCollisionMap(),
					Color.black)) {
				findPath("enemyUp", "enemyDown", "enemyLeft", "enemyRight");
			} else {
				posY += speed;
			}
			if (wallWalking) {
				animation.change("enemyDown_wall");
			} else {
				animation.change("enemyDown");
			}
		}

		if (LEFT) {
			if (simpleHasColl(posX - speed, posY, map.getCollisionMap(),
					Color.black)) {
				findPath("enemyUp", "enemyDown", "enemyLeft", "enemyRight");
			} else {
				posX -= speed;
			}
			if (wallWalking) {
				animation.change("enemyLeft_wall");
			} else {
				animation.change("enemyLeft");
			}
		}

		if (RIGHT) {
			if (simpleHasColl(posX + speed, posY, map.getCollisionMap(),
					Color.black)) {
				findPath("enemyUp", "enemyDown", "enemyLeft", "enemyRight");
			} else {
				posX += speed;
			}
			if (wallWalking) {
				animation.change("enemyRight_wall");
			} else {
				animation.change("enemyRight");
			}
		}
	}

	@Override
	public void draw(Graphics2D g2d, ImageLoader gr, Graphics2D cm) {
		g2d.drawImage(animation.getCurrentImage(), posX, posY, null);
		cm.setPaint(Color.red);
		cm.fillRect(posX + 5, posY + 5, 40, 40);
	}

	/**
	 * <b>public void update(BufferedImage cm)</b>
	 * <p>
	 * Updates a Ghost object's status. May be used to move and animate a Ghost
	 * object. Commands the Ghost object to call findPath() after leaving a
	 * wall.
	 * 
	 * @param cm
	 *            - collisionMap.
	 */
	@Override
	public void update(BufferedImage cm) {
		animation.animate();

		if (simpleHasColl(posX, posY, cm, Color.orange, Color.darkGray)) {
			die("enemyDying");
		}
		if (simpleHasColl(posX, posY, cm, Color.gray)) {
			wallWalking = true;
			comingOutOfWall = true;
		} else {
			wallWalking = false;
			if (comingOutOfWall) {
				findPath("enemyUp", "enemyDown", "enemyLeft", "enemyRight");
				comingOutOfWall = false;
			}
		}

		if (dying) {

			if (beforeTime + dyingTime <= System.nanoTime()) {
				setDestroyed(true);
			}
		} else if (wallWalking) {
			if (walldelay > 1) {
				walldelay = 0;
			}
			if (walldelay == 0) {
				move();
			}
			walldelay++;
		} else {
			if (walkdelay > 1) {
				walkdelay = 0;
			}
			if (walkdelay == 0) {
				move();
			}
			walkdelay++;
		}

	}

}
