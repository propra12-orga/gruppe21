package enemies;

import imageloader.ImageLoader;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import mapobjects.Enemy;

public class Monster extends Enemy {

	private int waitingCounter;
	private int walkdelay;
	private int x, y;

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
					y += speed;

					if (y >= 5) {
						Slime slime = new Slime(posX, posY, true, true, false,
								"slime", map.getGraphics(), "up");
						slime.setMap(getMap());
						map.getMapObjects().get(1).add(slime);
						y = 0;
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
					y += speed;

					if (y >= 5) {
						Slime slime = new Slime(posX, posY, true, true, false,
								"slime", map.getGraphics(), "down");
						slime.setMap(getMap());
						map.getMapObjects().get(1).add(slime);
						y = 0;
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
					x += speed;

					if (x >= 5) {
						Slime slime = new Slime(posX, posY, true, true, false,
								"slime", map.getGraphics(), "left");
						slime.setMap(getMap());
						map.getMapObjects().get(1).add(slime);
						x = 0;
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
					x += speed;

					if (x >= 5) {
						Slime slime = new Slime(posX, posY, true, true, false,
								"slime", map.getGraphics(), "right");
						slime.setMap(getMap());
						map.getMapObjects().get(1).add(slime);
						x = 0;
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
			// if (walkdelay > 1) {
			// walkdelay = 0;
			// }
			// if (walkdelay == 0) {
			// move();
			// }
			// walkdelay++;
			move();
		}
	}

}
