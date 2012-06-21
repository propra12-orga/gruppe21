package enemies;

import imageloader.ImageLoader;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import mapobjects.Enemy;

public class Skeleton extends Enemy {

	// private int maxSkulls = 3;
	private int waitingCounter;

	private boolean shooted = false;

	public Skeleton(int x, int y, boolean v, boolean d, boolean c, String p,
			ImageLoader gr) {
		super(x, y, v, d, c, p, gr);
	}

	@Override
	public void move() {
		if (UP) {
			if (hasObjectCollision(posX, posY - speed, map.getCollisionMap())) {
				waitingCounter = 0;
				shooted = false;
				findPath("enemyUp", "enemyDown", "enemyLeft", "enemyRight");
			} else {
				waitingCounter++;
				if (!shooted) {
					shootSkull("up");
					shooted = true;
				}
				if (waitingCounter > 100) {
					posY -= speed;
				}
			}
		}

		if (DOWN) {
			if (hasObjectCollision(posX, posY + speed, map.getCollisionMap())) {
				waitingCounter = 0;
				shooted = false;
				findPath("enemyUp", "enemyDown", "enemyLeft", "enemyRight");
			} else {
				waitingCounter++;
				if (!shooted) {
					shootSkull("down");
					shooted = true;
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
				findPath("enemyUp", "enemyDown", "enemyLeft", "enemyRight");
			} else {
				waitingCounter++;
				if (!shooted) {
					shootSkull("left");
					shooted = true;
				}
				if (waitingCounter > 100) {
					posX -= speed;
				}
			}
		}

		if (RIGHT) {
			if (hasObjectCollision(posX + speed, posY, map.getCollisionMap())) {
				waitingCounter = 0;
				shooted = false;
				findPath("enemyUp", "enemyDown", "enemyLeft", "enemyRight");
			} else {
				waitingCounter++;
				if (!shooted) {
					shootSkull("right");
					shooted = true;
				}
				if (waitingCounter > 100) {
					posX += speed;
				}
			}
		}
	}

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

	private void shootSkull(String dir) {
		int i = (int) (Math.random() * 3 + 1);

		if (i == 3) {
			Skull skull = new Skull(getPosX(), getPosY(), true, true, false,
					"skulls", map.getGraphics(), dir);
			skull.setMap(getMap());
			map.getMapObjects().get(1).add(skull);
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

	@Override
	/* weg damit */
	public boolean hasObjectCollision(int x, int y, BufferedImage cm, String dir) {
		// TODO Auto-generated method stub
		return false;
	}

}
