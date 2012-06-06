package mapobjects;

import imageloader.ImageLoader;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Enemy extends MoveableObject {
	private boolean hiddenObject = false;
	private boolean UP, DOWN, RIGHT, LEFT;

	public Enemy(int x, int y, boolean v, boolean d, boolean c, String p,
			ImageLoader gr) {
		super(x, y, v, d, c, p, gr);
		UP = false;
		DOWN = false;
		RIGHT = true;
		LEFT = false;
		speed = 2;
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

			} else {
				posY -= speed;
			}
			animation.change("enemyUp");
		}

		if (DOWN) {
			if (hasObjectCollision(posX, posY + speed, map.getCollisionMap(),
					"DOWN")) {

			} else {
				posY += speed;
			}
			animation.change("enemyDown");
		}

		if (LEFT) {
			if (hasObjectCollision(posX - speed, posY, map.getCollisionMap(),
					"LEFT")) {
				LEFT = false;
				RIGHT = true;
			} else {
				posX -= speed;
			}
			animation.change("enemyLeft");
		}

		if (RIGHT) {
			if (hasObjectCollision(posX + speed, posY, map.getCollisionMap(),
					"RIGHT")) {
				RIGHT = false;
				LEFT = true;
			} else {
				posX += speed;
			}
			animation.change("enemyRight");
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
		move();
	}

	// hidden object released on die
	public void addHiddenObject() {
	}

	public void die() {
		stop();

		this.setDestroyed(true);
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
					map.finishMap(true);
				}
			}
		}
		return false;
	}
}
