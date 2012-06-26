package enemies;

import imageloader.ImageLoader;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import mapobjects.Enemy;

public class Ghost extends Enemy {

	private int walkdelay;
	private boolean wallWalking = false;

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
		}

		if (DOWN) {
			if (simpleHasColl(posX, posY + speed, map.getCollisionMap(),
					Color.black)) {
				findPath("enemyUp", "enemyDown", "enemyLeft", "enemyRight");
			} else {
				posY += speed;
			}
		}

		if (LEFT) {
			if (simpleHasColl(posX - speed, posY, map.getCollisionMap(),
					Color.black)) {
				findPath("enemyUp", "enemyDown", "enemyLeft", "enemyRight");
			} else {
				posX -= speed;
			}
		}

		if (RIGHT) {
			if (simpleHasColl(posX + speed, posY, map.getCollisionMap(),
					Color.black)) {
				findPath("enemyUp", "enemyDown", "enemyLeft", "enemyRight");
			} else {
				posX += speed;
			}
		}
	}

	@Override
	public void findPath(String up, String down, String left, String right) {
		int choice = (int) (Math.random() * 4 + 1);
		stop();

		switch (choice) {
		case 1:
			UP = true;
			animation.change(up);
			if (simpleHasColl(posX, posY - speed, map.getCollisionMap(),
					Color.gray)) {
				wallWalking = true;
			}
			break;
		case 2:
			DOWN = true;
			animation.change(down);
			if (simpleHasColl(posX, posY + speed, map.getCollisionMap(),
					Color.gray)) {
				wallWalking = true;
			}
			break;
		case 3:
			LEFT = true;
			animation.change(left);
			if (simpleHasColl(posX - speed, posY, map.getCollisionMap(),
					Color.gray)) {
				wallWalking = true;
			}
			break;
		case 4:
			RIGHT = true;
			animation.change(right);
			if (simpleHasColl(posX + speed, posY, map.getCollisionMap(),
					Color.gray)) {
				wallWalking = true;
			}
			break;
		default:
			findPath(up, down, left, right);
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
		if (simpleHasColl(posX, posY, cm, Color.gray)) {
			wallWalking = true;
		} else {
			wallWalking = false;
		}

		if (dying) {

			if (beforeTime + dyingTime <= System.nanoTime()) {
				setDestroyed(true);
			}
		} else if (wallWalking) {
			if (walkdelay > 2) {
				walkdelay = 0;
			}
			if (walkdelay == 0) {
				move();
			}
			walkdelay++;
		} else {
			move();
		}

	}

}
