package enemies;

import imageloader.ImageLoader;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import mapobjects.Enemy;

/**
 * <b>public class sillyEnemy extends Enemy</b>
 * <p>
 * A sillyEnemy object displays an enemy which is just able to move with a
 * constant speed into a random direction.
 * 
 * @author masto104
 */
public class sillyEnemy extends Enemy {

	/**
	 * sillyEnemy constructor.
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
	public sillyEnemy(int x, int y, boolean v, boolean d, boolean c, String p,
			ImageLoader gr) {
		super(x, y, v, d, c, p, gr);
	}

	@Override
	public void move() {
		if (UP) {
			if (simpleHasColl(posX, posY - speed, map.getCollisionMap(),
					Color.black, Color.gray)) {
				findPath("enemyUp", "enemyDown", "enemyLeft", "enemyRight");
			} else {
				posY -= speed;
			}
		}

		if (DOWN) {
			if (simpleHasColl(posX, posY + speed, map.getCollisionMap(),
					Color.black, Color.gray)) {
				findPath("enemyUp", "enemyDown", "enemyLeft", "enemyRight");
			} else {
				posY += speed;
			}
		}

		if (LEFT) {
			if (simpleHasColl(posX - speed, posY, map.getCollisionMap(),
					Color.black, Color.gray)) {
				findPath("enemyUp", "enemyDown", "enemyLeft", "enemyRight");
			} else {
				posX -= speed;
			}
		}

		if (RIGHT) {
			if (simpleHasColl(posX + speed, posY, map.getCollisionMap(),
					Color.black, Color.gray)) {
				findPath("enemyUp", "enemyDown", "enemyLeft", "enemyRight");
			} else {
				posX += speed;
			}
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

		if (simpleHasColl(posX, posY, cm, Color.orange, Color.darkGray)) {
			die("enemyDying");
		}

		if (dying) {

			if (beforeTime + dyingTime <= System.nanoTime()) {
				setDestroyed(true);
			}
		} else {
			move();
		}
	}

}
