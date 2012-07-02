package enemies;

import imageloader.ImageLoader;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import mapobjects.Enemy;

public class StoneOrb extends Enemy {

	public StoneOrb(int x, int y, boolean v, boolean d, boolean c, String p,
			ImageLoader gr, String dir) {
		super(x, y, v, d, c, p, gr);
		stop();
		speed = 4;
		initDirection(dir);
	}

	private void initDirection(String dir) {

		if (dir.equals("right")) {
			RIGHT = true;
		} else {
			System.out.println("Error in Orb#initDirection");
		}
	}

	@Override
	public void move() {

		if (RIGHT) {
			if (hasObjectCollision(posX + speed, posY, map.getCollisionMap())) {
				die("enemyDying");
			} else {
				posX += speed;
				animation.change("enemyRight");
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
				destroyed = true;
			}
		} else {
			move();
		}
	}

}
