package enemies;

import imageloader.ImageLoader;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import mapobjects.Enemy;

public class Ghost extends Enemy {

	private int walldelay;
	private int walkdelay;
	private boolean wallWalking = false;
	private boolean comingOutOfWall = false;

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
