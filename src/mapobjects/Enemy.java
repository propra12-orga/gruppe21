package mapobjects;

import imageloader.ImageLoader;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Enemy extends MoveableObject {
	private boolean hiddenObject = false;
	private boolean UP, DOWN, RIGHT, LEFT;
	private boolean dying;
	private long beforeTime, dyingTime = 800000000;

	public Enemy(int x, int y, boolean v, boolean d, boolean c, String p,
			ImageLoader gr) {
		super(x, y, v, d, c, p, gr);
		UP = false;
		DOWN = false;
		RIGHT = true;
		LEFT = false;
		speed = 1;
	}

	private void stop() {
		UP = false;
		DOWN = false;
		LEFT = false;
		RIGHT = false;
	}

	@Override
	public void move() {
		// also move hidden Object
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

	public void die() {
		dying = true;
		animation.stop();
		animation.change("enemyDying");
		animation.start("enemyDying");
		beforeTime = System.nanoTime();
	}

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
