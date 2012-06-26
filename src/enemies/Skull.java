package enemies;

import imageloader.ImageLoader;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import mapobjects.Enemy;

public class Skull extends Enemy {

	public Skull(int x, int y, boolean v, boolean d, boolean c, String p,
			ImageLoader gr, String dir) {
		super(x, y, v, d, c, p, gr);
		stop();
		speed = 3;
		initDirection(dir);
	}

	private void initDirection(String dir) {
		if (dir.equals("up")) {
			UP = true;
		} else if (dir.equals("down")) {
			DOWN = true;
		} else if (dir.equals("left")) {
			LEFT = true;
		} else if (dir.equals("right")) {
			RIGHT = true;
		} else {
			System.out.println("Error in Skull#initDirection");
		}
	}

	@Override
	public void move() {
		if (UP) {
			if (hasObjectCollision(posX, posY - speed, map.getCollisionMap())) {
				die("enemyDying");
			} else {
				posY -= speed;
			}
			animation.change("enemyUp");
		}

		if (DOWN) {
			if (hasObjectCollision(posX, posY + speed, map.getCollisionMap())) {
				die("enemyDying");
			} else {
				posY += speed;
			}
			animation.change("enemyDown");
		}

		if (LEFT) {
			if (hasObjectCollision(posX - speed, posY, map.getCollisionMap())) {
				die("enemyDying");
			} else {
				posX -= speed;
			}
			animation.change("enemyLeft");
		}

		if (RIGHT) {
			if (hasObjectCollision(posX + speed, posY, map.getCollisionMap())) {
				die("enemyDying");
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
	public void setDestroyed(boolean destroyed) {
		this.destroyed = destroyed;
	}

}
