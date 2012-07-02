package enemies;

import imageloader.ImageLoader;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import mapobjects.AnimatedFloor;
import mapobjects.Enemy;

public class StoneOrbSpawn extends Enemy {

	private double startDelay = 5000000000L, spawnTime,
			spawnCountdown = 3000000000L;
	AnimatedFloor spawnpoint;

	public StoneOrbSpawn(int x, int y, boolean v, boolean d, boolean c,
			String p, ImageLoader gr) {
		super(x, y, v, d, c, p, gr);

		spawnpoint = new AnimatedFloor(50, 300, true, false, false, "portals",
				map.getGraphics());
		spawnpoint.setMap(getMap());
		map.getMapObjects().get(1).add(spawnpoint);

	}

	private void spawnOrb() {

		if (spawnTime + spawnCountdown <= System.nanoTime()) {
			int x, y = 200, i = (int) (Math.random() * 2 + 1);

			if (i == 1) {
				x = 150;
			} else {
				x = 700;
			}

			StoneOrb newOrb = new StoneOrb(x, y, true, true, true, "stoneOrbs",
					map.getGraphics(), "right");
			newOrb.setMap(getMap());
			map.getMapObjects().get(1).add(newOrb);

			spawnTime = System.nanoTime();
		}
	}

	@Override
	public void move() {

	}

	@Override
	public void draw(Graphics2D g2d, ImageLoader gr, Graphics2D cm) {

	}

	@Override
	public void update(BufferedImage cm) {

	}
}
