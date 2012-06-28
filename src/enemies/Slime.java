package enemies;

import imageloader.ImageLoader;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import mapobjects.MapObject;

public class Slime extends MapObject {

	private String dyingAnimation;

	private double beforeTime, lifeTime = 2000000000L,
			disappearTime = 1000000000L;
	private boolean disappearing = false;

	public Slime(int x, int y, boolean v, boolean d, boolean c, String p,
			ImageLoader gr, String dir) {
		super(x, y, v, d, c, p, gr);
		beforeTime = System.nanoTime();
		animation.start("slime");
		initDirection(dir);
	}

	private void initDirection(String dir) {
		if (dir.equals("up")) {
			animation.start("up");
			dyingAnimation = "disappear_up";
		} else if (dir.equals("down")) {
			animation.start("down");
			dyingAnimation = "disappear_down";
		} else if (dir.equals("left")) {
			animation.start("left");
			dyingAnimation = "disappear_left";
		} else if (dir.equals("right")) {
			animation.start("right");
			dyingAnimation = "disappear_right";
		} else {
			System.out.println("Slime#initDirection(): Fehlerhafte Richtung");
		}
	}

	@Override
	public void update(BufferedImage collisionMap) {
		animation.animate();

		if (simpleHasColl(posX, posY, collisionMap, Color.orange,
				Color.darkGray)) {
			destroyed = true;
		}

		if (disappearing) {
			if (beforeTime + disappearTime <= System.nanoTime()) {
				destroyed = true;
			}
		} else {
			if (beforeTime + lifeTime <= System.nanoTime()) {
				die(dyingAnimation);
			}
		}
	}

	public void die(String animation) {
		disappearing = true;
		this.animation.change(animation);
		beforeTime = System.nanoTime();
	}

	@Override
	public void draw(Graphics2D g2d, ImageLoader gr, Graphics2D cmg) {
		g2d.drawImage(animation.getCurrentImage(), posX, posY, null);
		cmg.setPaint(Color.red);
		cmg.fillRect(posX + 15, posY + 15, 20, 20);
	}
}
