package enemies;

import imageloader.ImageLoader;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import mapobjects.AnimatedFloor;
import mapobjects.MapObject;

public class BlackHole extends MapObject {

	private double spawnTime, spawnCountdown = 7400000000L;
	private AnimatedFloor finishHole;

	public BlackHole(int x, int y, boolean v, boolean d, boolean c, String p,
			ImageLoader gr) {
		super(x, y, v, d, c, p, gr);
		animation.start("animation");
	}

	private void spawnOrb() {
		StoneOrb newOrb = new StoneOrb(50, 300, true, true, true, "stoneOrbs",
				map.getGraphics());
		newOrb.setMap(getMap());
		map.getMapObjects().get(2).add(newOrb);

		spawnTime = System.nanoTime();
	}

	@Override
	public void draw(Graphics2D g2d, ImageLoader gr, Graphics2D cm) {
		g2d.drawImage(animation.getCurrentImage(), posX, posY, null);
		cm.setPaint(Color.gray);
		cm.fillRect(posX + 5, posY + 5, 40, 40);
	}

	@Override
	public void update(BufferedImage cm) {
		animation.animate();

		if (spawnTime + spawnCountdown <= System.nanoTime()) {
			spawnOrb();
		}
	}
}
