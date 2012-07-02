package enemies;

import imageloader.ImageLoader;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import mapobjects.Enemy;

public class StoneOrb extends Enemy {

	private double animationStart, appearTime = 500000000,
			disappearTime = 500000000L;
	private int distance;

	public StoneOrb(int x, int y, boolean v, boolean d, boolean c, String p,
			ImageLoader gr) {
		super(x, y, v, d, c, p, gr);
		stop();
		speed = 3;
		RIGHT = true;
		animation.start("move");
		animationStart = System.nanoTime();
	}

	@Override
	public void move() {
		if (simpleHasColl(posX + 50, posY, map.getCollisionMap(), Color.gray)) {
			if (distance > 100) {
				stop();
				die("disappear");
			} else {
				posX += speed;
				distance += speed;
			}
		} else {
			posX += speed;
			distance += speed;
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
