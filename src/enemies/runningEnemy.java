package enemies;

import imageloader.ImageLoader;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import mapobjects.Enemy;

/**
 * <b>public class runningEnemy extends Enemy</b>
 * <p>
 * A runningEnemy object displays an enemy which is able to move into a random
 * direction. When it collides with a wall, it waits a few moments and then runs
 * into the chosen direction.
 * 
 * @author masto104
 */
public class runningEnemy extends Enemy {

	/**
	 * Used to time the waits before the enemy starts running.
	 */
	private int waitingCounter;

	/**
	 * runningEnemy constructor.
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
	public runningEnemy(int x, int y, boolean v, boolean d, boolean c,
			String p, ImageLoader gr) {
		super(x, y, v, d, c, p, gr);
	}

	@Override
	public void move() {
		if (UP) {
			if (hasObjectCollision(posX, posY - speed, map.getCollisionMap())) {
				waitingCounter = 0;
				findPath("enemyUp", "enemyDown", "enemyLeft", "enemyRight");
			} else {
				waitingCounter++;

				if (waitingCounter > 100) {
					posY -= speed * 5;
				}
			}
		}

		if (DOWN) {
			if (hasObjectCollision(posX, posY + speed, map.getCollisionMap())) {
				waitingCounter = 0;
				findPath("enemyUp", "enemyDown", "enemyLeft", "enemyRight");
			} else {
				waitingCounter++;
				if (waitingCounter > 100) {
					posY += speed * 5;
				}
			}
		}

		if (LEFT) {
			if (hasObjectCollision(posX - speed, posY, map.getCollisionMap())) {
				waitingCounter = 0;
				findPath("enemyUp", "enemyDown", "enemyLeft", "enemyRight");
			} else {
				waitingCounter++;
				if (waitingCounter > 100) {
					posX -= speed * 5;
				}
			}
		}

		if (RIGHT) {
			if (hasObjectCollision(posX + speed, posY, map.getCollisionMap())) {
				waitingCounter = 0;
				findPath("enemyUp", "enemyDown", "enemyLeft", "enemyRight");
			} else {
				waitingCounter++;
				if (waitingCounter > 100) {
					posX += speed * 5;
				}
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

		if (dying) {

			if (beforeTime + dyingTime <= System.nanoTime()) {
				setDestroyed(true);
			}
		} else {
			move();
		}
	}

}
