package enemies;

import imageloader.Animation;
import imageloader.ImageLoader;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import mapobjects.AnimatedFloor;
import mapobjects.Enemy;
import mapobjects.FireBowl;
import mapobjects.MapObject;
import mapobjects.Wall;

/**
 * phase 1: casten, immortal, fire bowls zu zerstören phase 2: nicht mehr
 * immortal, flames legen phase 3: zusätzlich noch schießen phase 4: aoe (3
 * leben noch)
 * 
 * 
 * @author masto
 * 
 */

public class Boss extends Enemy {

	private boolean startInit = true;
	private int lifes = 5;
	private int flameDelay;
	private int aoeDelay;
	private int fireSpitCounter;
	private int[] xPos;
	private int[] yPos;
	public ArrayList<FireBowl> fireBowls;
	private boolean immortal;
	private boolean aoe;
	private boolean phase1 = true, phase2 = false, phase3 = false,
			phase4 = false, phase5 = false;
	private double immortalStart, immortalTime = 5000000000L;
	private double startDelay = 5000000000L, spawnTime,
			spawnCountdown = 3000000000L;
	private double aoeStart, aoeTime = 2000000000L;
	private Animation aoeAnim;
	AnimatedFloor portal1, portal2;

	public Boss(int x, int y, boolean v, boolean d, boolean c, String p,
			ImageLoader gr) {
		super(x, y, v, d, c, p, gr);
		stop();
		speed = 2;
		immortal = true;
		animation.start("casting"); // Anfangsanimation (casten/beschwören
									// oder so)
		spawnTime = System.nanoTime();

		xPos = new int[25];
		yPos = new int[25];

		aoeAnim = new Animation("aoeFlames", gr);
		aoeAnim.start("aoe");
	}

	private void initPhase1() {
		fireBowls = new ArrayList<FireBowl>();

		fireBowls.add(new FireBowl(350, 450, true, true, true, "fireBowls", map
				.getGraphics()));

		fireBowls.add(new FireBowl(500, 450, true, true, true, "fireBowls", map
				.getGraphics()));

		fireBowls.add(new FireBowl(350, 350, true, true, true, "fireBowls", map
				.getGraphics()));

		fireBowls.add(new FireBowl(500, 350, true, true, true, "fireBowls", map
				.getGraphics()));

		fireBowls.add(new FireBowl(350, 250, true, true, true, "fireBowls", map
				.getGraphics()));

		fireBowls.add(new FireBowl(500, 250, true, true, true, "fireBowls", map
				.getGraphics()));

		fireBowls.add(new FireBowl(350, 150, true, true, true, "fireBowls", map
				.getGraphics()));

		fireBowls.add(new FireBowl(500, 150, true, true, true, "fireBowls", map
				.getGraphics()));

		for (FireBowl bowl : fireBowls) {
			bowl.setMap(getMap());
			map.getMapObjects().get(1).add(bowl);
		}

		portal1 = new AnimatedFloor(150, 200, true, false, false, "portals",
				map.getGraphics());
		portal1.setMap(getMap());
		map.getMapObjects().get(1).add(portal1);

		portal2 = new AnimatedFloor(700, 200, true, false, false, "portals",
				map.getGraphics());
		portal2.setMap(getMap());
		map.getMapObjects().get(1).add(portal2);
	}

	private void closeEntrance() {
		Wall gridLeft = new Wall(400, 650, 0, true, false, true, "PrisonUL");
		gridLeft.setMap(getMap());
		map.getMapObjects().get(1).add(gridLeft);
		Wall gridRight = new Wall(450, 650, 0, true, false, true, "PrisonUR");
		gridRight.setMap(getMap());
		map.getMapObjects().get(1).add(gridRight);
	}

	private void plantFlame() {
		Flame flame = new Flame(posX, posY, true, true, false, "flames",
				map.getGraphics());
		flame.setMap(getMap());
		map.getMapObjects().get(1).add(flame);
	}

	private void spitFire(String dir) {
		fireSpitCounter++;

		if (fireSpitCounter == 100) {
			FireBreathe fire = new FireBreathe(posX, posY, true, true, true,
					"fireBreath", map.getGraphics(), dir);
			fire.setMap(getMap());
			map.getMapObjects().get(1).add(fire);

			fireSpitCounter = 0;
		}
	}

	private void aoeAttack() {
		if (!aoe) {
			int index = 0;

			int x = (posX + 25) / 50 * 50;
			int y = (posY + 25) / 50 * 50;

			for (int i = -2; i < 3; i++) {
				for (int j = -2; j < 3; j++) {
					if (!simpleHasColl(x + i * 50, y + j * 50,
							map.getCollisionMap(), Color.black, Color.gray)) {
						xPos[index] = x + i * 50;
						yPos[index] = y + j * 50;

						index++;
					}
				}
			}
			aoe = true;
			aoeStart = System.nanoTime();
		}
	}

	private void clearAoeArr() {
		for (int i = 0; i < xPos.length; i++) {
			xPos[i] = 0;
			yPos[i] = 0;
		}
	}

	private void phase1() {
		if (!fireBowls.isEmpty()) {
			if (spawnTime + spawnCountdown <= System.nanoTime()) {
				int x, y = 200, i = (int) (Math.random() * 2 + 1);

				if (i == 1) {
					x = 150;
				} else {
					x = 700;
				}

				Skeleton skel = new Skeleton(x, y, true, true, true,
						"skeletons", map.getGraphics());
				skel.setMap(getMap());
				map.getMapObjects().get(1).add(skel);

				spawnTime = System.nanoTime();
			}
		} else {
			closeEntrance();
			portal1.setDestroyed(true);
			portal2.setDestroyed(true);
			immortal = false;
			phase1 = false;
			phase2 = true;
			posY = 150;
			findPath("enemyUp", "enemyDown", "enemyLeft", "enemyRight");
			System.out.println("Phase 1 geschafft.");
		}
	}

	private void phase2() {
		if (lifes == 4) {
			phase2 = false;
			phase3 = true;
			System.out.println("Phase 2 geschafft.");
		}

		move();

		flameDelay++;
		if (flameDelay > 80) {
			plantFlame();
			flameDelay = 0;
		}
	}

	private void phase3() {
		if (lifes == 3) {
			phase3 = false;
			phase4 = true;
			System.out.println("Phase 3 geschafft.");
		}

		move();

		flameDelay++;
		if (flameDelay > 80) {
			plantFlame();
			flameDelay = 0;
		}
	}

	private void phase4() {
		// if (lifes == 2) {
		// phase4 = false;
		// phase5 = true;
		// }

		move();

		flameDelay++;
		if (flameDelay > 80) {
			plantFlame();
			flameDelay = 0;
		}

		aoeDelay++;
		if (aoeDelay > 100) {
			aoeAttack();
			aoeDelay = 0;
		}
	}

	@Override
	public void move() {
		if (UP) {
			if (simpleHasColl(posX, posY - speed, map.getCollisionMap(),
					Color.black, Color.gray)) {
				findPath("enemyUp", "enemyDown", "enemyLeft", "enemyRight");
			} else {
				posY -= speed;
			}
			if (phase3 || phase4 || phase5) {
				spitFire("up");
			}
		}

		if (DOWN) {
			if (simpleHasColl(posX, posY + speed, map.getCollisionMap(),
					Color.black, Color.gray)) {
				findPath("enemyUp", "enemyDown", "enemyLeft", "enemyRight");
			} else {
				posY += speed;
			}
			if (phase3 || phase4 || phase5) {
				spitFire("down");
			}
		}

		if (LEFT) {
			if (simpleHasColl(posX - speed, posY, map.getCollisionMap(),
					Color.black, Color.gray)) {
				findPath("enemyUp", "enemyDown", "enemyLeft", "enemyRight");
			} else {
				posX -= speed;
			}
			if (phase3 || phase4 || phase5) {
				spitFire("left");
			}
		}

		if (RIGHT) {
			if (simpleHasColl(posX + speed, posY, map.getCollisionMap(),
					Color.black, Color.gray)) {
				findPath("enemyUp", "enemyDown", "enemyLeft", "enemyRight");
			} else {
				posX += speed;
			}
			if (phase3 || phase4 || phase5) {
				spitFire("right");
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
			if (immortal) {
				animation.change("enemyUp_hit");
			} else {
				animation.change(up);
			}
			break;
		case 2:
			DOWN = true;
			if (immortal) {
				animation.change("enemyDown_hit");
			} else {
				animation.change(down);
			}
			break;
		case 3:
			LEFT = true;
			if (immortal) {
				animation.change("enemyLeft_hit");
			} else {
				animation.change(left);
			}
			break;
		case 4:
			RIGHT = true;
			if (immortal) {
				animation.change("enemyRight_hit");
			} else {
				animation.change(right);
			}
			break;
		default:
			findPath(up, down, left, right);
		}
	}

	@Override
	public void draw(Graphics2D g2d, ImageLoader gr, Graphics2D cm) {
		g2d.drawImage(animation.getCurrentImage(), posX, posY, null);

		if (phase1) {
			cm.setPaint(Color.black); // bubble
		} else {
			cm.setPaint(Color.red);
		}
		cm.fillRect(posX + 5, posY + 5, 40, 40);

		if (aoe) {
			for (int i = 0; i < xPos.length; i++) {
				if (xPos[i] != 0 && yPos[i] != 0) {
					g2d.drawImage(aoeAnim.getCurrentImage(), xPos[i], yPos[i],
							null);
					cm.setPaint(Color.red);
					cm.fillRect(xPos[i] + 5, yPos[i] + 5, 40, 40);
				}
			}
		}
	}

	@Override
	public void update(BufferedImage cm) {
		if (startInit) {
			initPhase1();
			startInit = false;
		}

		animation.animate();

		if (simpleHasColl(posX, posY, cm, Color.orange)) {
			if (!immortal) {
				lifes--;
				immortal = true;
				immortalStart = System.nanoTime();
			}
		}
		if (!phase1 && immortal) {
			if (immortalStart + immortalTime <= System.nanoTime()) {
				immortal = false;
			}
		}
		if (lifes == 0) {
			phase4 = false;
			stop();
			die("enemyDying");
		}
		if (dying) {
			if (beforeTime + dyingTime <= System.nanoTime()) {
				destroyed = true;
			}
		}

		if (aoe) {
			aoeAnim.animate();
			if (aoeStart + aoeTime <= System.nanoTime()) {
				aoe = false;
				clearAoeArr();
			}
		}

		if (phase1) {
			phase1();
		} else if (phase2) {
			phase2();
		} else if (phase3) {
			phase3();
		} else if (phase4) {
			phase4();
		}

	}

	static class Flame extends MapObject {

		private double beforeTime, disappearTime = 1000000000L,
				lifeTime = 5000000000L;
		private boolean disappearing = false;

		public Flame(int x, int y, boolean v, boolean d, boolean c, String p,
				ImageLoader gr) {
			super(x, y, v, d, c, p, gr);
			beforeTime = System.nanoTime();
			posX = (x + 25) / 50 * 50;
			posY = (y + 25) / 50 * 50;
			animation.start("flame");
		}

		@Override
		public void update(BufferedImage collisionMap) {
			animation.animate();

			if (simpleHasColl(posX, posY, map.getCollisionMap(), Color.orange,
					Color.darkGray)) {
				destroyed = true;
			}

			if (disappearing) {
				if (beforeTime + disappearTime <= System.nanoTime()) {
					destroyed = true;
				}
			} else {
				if (beforeTime + lifeTime <= System.nanoTime()) {
					die("disappear");
				}
			}

		}

		@Override
		public void draw(Graphics2D g2d, ImageLoader gr, Graphics2D cmg) {
			g2d.drawImage(animation.getCurrentImage(), posX, posY, null);
			if (!disappearing) {
				cmg.setPaint(Color.red);
			} else {
				cmg.setPaint(Color.white);
			}
			cmg.fillRect(posX + 5, posY + 5, 40, 40);
		}

		private void die(String animation) {
			beforeTime = System.nanoTime();
			disappearing = true;
			this.animation.change(animation);
		}
	}

	static class FireBreathe extends Skull {

		public FireBreathe(int x, int y, boolean v, boolean d, boolean c,
				String p, ImageLoader gr, String dir) {
			super(x, y, v, d, c, p, gr, dir);
		}
	}

}
