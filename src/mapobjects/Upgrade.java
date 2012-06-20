package mapobjects;

import imageloader.Animation;
import imageloader.ImageLoader;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Upgrade extends MapObject {

	private Color color, colorCache;

	public Upgrade(int x, int y, boolean v, boolean d, boolean c, String p,
			ImageLoader gr, Color col) {
		super(x, y, v, d, c, p, gr);
		color = col;
		animation = new Animation(p, gr);
		initAnimation(col);
		colorCache = color;
	}

	private void initAnimation(Color c) {
		String upgradeAnimation;

		if (c.equals(Color.pink)) {
			upgradeAnimation = "BombPlus";
		} else if (c.equals(Color.blue)) {
			upgradeAnimation = "BombRange";
		} else if (c.equals(Color.cyan)) {
			upgradeAnimation = "PlayerSpeed";
		} else if (c.equals(Color.magenta)) {
			upgradeAnimation = "BombRemote";
		} else if (c.equals(Color.lightGray)) {
			upgradeAnimation = "Bulletproof";
		} else {
			upgradeAnimation = "Bulletproof";
		}

		animation.start(upgradeAnimation);
	}

	@Override
	public void update(BufferedImage collisionMap) {
		if (!simpleHasColl(posX, posY, collisionMap, Color.green)) {
			animation.animate();
		} else {
			setDestroyed(true);
			setVisible(false);
			color = Color.white;
		}
	}

	@Override
	public void draw(Graphics2D g2d, ImageLoader gr, Graphics2D cmg) {
		cmg.setPaint(color);

		if (destroyed) {
			color = Color.white;
			System.out.println("draw white");
		}
		g2d.drawImage(animation.getCurrentImage(), posX, posY, null);
		cmg.fillRect(posX, posY, 50, 50);

	}
}
