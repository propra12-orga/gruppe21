package enemies;

import imageloader.ImageLoader;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import mapobjects.Enemy;

/**
 * <b>public class Monster extends Enemy</b>
 * <p>
 * A Monster object displays an enemy which is able to move into a random
 * direction. While moving it leaves a trace of poisonous slime.
 * 
 * @author masto104
 */
public class Monster extends Enemy {

	/**
	 * Used to time the waits before the enemy starts running.
	 */
	private int waitingCounter;

	/**
	 * Used to decrease the speed.
	 */
	private int walkdelay;

	/**
	 * Used for
	 */
	private int distance;

	/**
	 * Monster constructor.
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
	public Monster(int x, int y, boolean v, boolean d, boolean c, String p,
			ImageLoader gr) {
		super(x, y, v, d, c, p, gr);
	}

	/**
	 * <b>public void move()</b>
	 * <p>
	 * Moves the enemy object over the panel. Checks the ability of moving into
	 * the relevant direction. If ability is given the enemy object will be
	 * moved for a fixed number of pixels, if not findPath() will be called.
	 * Also creates a Slime object whenever a distance of a constant value of
	 * pixels is exceeded.
	 */
	@Override
	public void move() {
		if (UP) {
			if (hasObjectCollision(posX, posY - speed, map.getCollisionMap())) {
				waitingCounter = 0;
				findPath("enemyUp", "enemyDown", "enemyLeft", "enemyRight");
			} else {
				waitingCounter++;

				if (waitingCounter > 50) {
					posY -= speed;
					distance += speed;

					if (distance >= 15) {
						Slime slime = new Slime(posX, posY, true, true, false,
								"slime", map.getGraphics(), "up");
						slime.setMap(getMap());
						map.getMapObjects().get(1).add(slime);
						distance = 0;
					}
				}
			}
		}

		if (DOWN) {
			if (hasObjectCollision(posX, posY + speed, map.getCollisionMap())) {
				waitingCounter = 0;
				findPath("enemyUp", "enemyDown", "enemyLeft", "enemyRight");
			} else {
				waitingCounter++;
				if (waitingCounter > 50) {
					posY += speed;
					distance += speed;

					if (distance >= 15) {
						Slime slime = new Slime(posX, posY, true, true, false,
								"slime", map.getGraphics(), "down");
						slime.setMap(getMap());
						map.getMapObjects().get(1).add(slime);
						distance = 0;
					}
				}
			}
		}

		if (LEFT) {
			if (hasObjectCollision(posX - speed, posY, map.getCollisionMap())) {
				waitingCounter = 0;
				findPath("enemyUp", "enemyDown", "enemyLeft", "enemyRight");
			} else {
				waitingCounter++;
				if (waitingCounter > 50) {
					posX -= speed;
					distance += speed;

					if (distance >= 15) {
						Slime slime = new Slime(posX, posY, true, true, false,
								"slime", map.getGraphics(), "left");
						slime.setMap(getMap());
						map.getMapObjects().get(1).add(slime);
						distance = 0;
					}
				}
			}
		}

		if (RIGHT) {
			if (hasObjectCollision(posX + speed, posY, map.getCollisionMap())) {
				waitingCounter = 0;
				findPath("enemyUp", "enemyDown", "enemyLeft", "enemyRight");
			} else {
				waitingCounter++;
				if (waitingCounter > 50) {
					posX += speed;
					distance += speed;

					if (distance >= 15) {
						Slime slime = new Slime(posX, posY, true, true, false,
								"slime", map.getGraphics(), "right");
						slime.setMap(getMap());
						map.getMapObjects().get(1).add(slime);
						distance = 0;
					}
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
