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
 * <b>public class Boss extends Enemy</b>
 * <p>
 * The final boss. A Boss object displays an enemy which is able to move into a
 * random direction. The enemy has got five lives. The fight is divided into
 * four phases:
 * <p>
 * Phase 1: The boss is placed at the top of the level, invoking two portals.
 * There are eight fire bowls in the room. They must be bombed to get to phase
 * 2. During they are not bombed the boss is immortal and the portals spawn
 * skeletons in a constant interval.
 * <p>
 * Phase 2: The portals have closed. The boss is moving now. While he moves into
 * random directions he inflames the ground in a constant interval. The fight
 * gets to phase 3 if the boss has been hit by a bomb.
 * <p>
 * Phase 3: The boss does additionally spit fire in a constant interval. The
 * fight gets to phase 4 if the boss has been hit by a bomb.
 * <p>
 * Phase 4: The boss does additionally cast an aoe attack. The cast inflames the
 * ground around the boss in a range of 2 tiles.
 * 
 * @author masto104
 */
public class Boss extends Enemy {

	/**
	 * Used to initialize the fire bowls and the portal in the first call of the
	 * upgrade method.
	 */
	private boolean startInit = true;

	/**
	 * The current lives of the boss.
	 */
	private int lives = 5;

	/**
	 * Used to time the interval of planting flames.
	 */
	private int flameDelay;

	/**
	 * Used to time the interval of casting aoe attack.
	 */
	private int aoeDelay;

	/**
	 * Used to time the interval of spitting fire.
	 */
	private int fireSpitCounter;

	/**
	 * Array of the x- and y-coordinates of the tiles on which the aoe attack is
	 * damaging
	 */
	private int[] xPos, yPos;

	/**
	 * List of current fire bowls.
	 */
	public ArrayList<FireBowl> fireBowls;

	/**
	 * True, if the boss is currently immortal.
	 */
	private boolean immortal;

	/**
	 * True, if boss is casting aoe attack.
	 */
	private boolean aoe;

	/**
	 * True, if the matching phase is active.
	 */
	private boolean phase1 = true, phase2 = false, phase3 = false,
			phase4 = false, phase5 = false;

	/**
	 * Used to time the temporary immortality.
	 */
	private double immortalStart, immortalTime = 5000000000L;

	/**
	 * Used to time the enemy spawn interval.
	 */
	private double spawnTime, spawnCountdown = 3000000000L;

	/**
	 * Used to time the aoe attack interval.
	 */
	private double aoeStart, aoeTime = 2000000000L;

	/**
	 * The time of the dying animation.
	 */
	private double dyingTime = 5000000000L;

	/**
	 * The aoe animation.
	 */
	private Animation aoeAnim;

	/**
	 * The portals of phase 1.
	 */
	AnimatedFloor portal1, portal2;

	/**
	 * Boss constructor.
	 * 
	 * @param x
	 *            - x-coordinate.
	 * @param y
	 *            - y-coordinate.
	 * @param v
	 *            - sets visibility.
	 * @param d
	 *            - sets 'destructible' flag.
	 * @param c
	 *            - sets 'collision' flag
	 * @param p
	 *            - AnimationSet filename
	 * @param gr
	 *            - ImageLoader
	 */
	public Boss(int x, int y, boolean v, boolean d, boolean c, String p,
			ImageLoader gr) {
		super(x, y, v, d, c, p, gr);
		stop();
		speed = 2;
		immortal = true;
		animation.start("casting");

		spawnTime = System.nanoTime();

		xPos = new int[25];
		yPos = new int[25];

		aoeAnim = new Animation("aoeFlames", gr);
		aoeAnim.start("aoe");
	}

	/**
	 * <b>private void initPhase1()</b>
	 * <p>
	 * Initializes phase1. Creates the fireBowls and the portals.
	 */
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

	/**
	 * <b>private void closeEntrance()</b>
	 * <p>
	 * Sets the accessibility of the entrance of the room to false.
	 */
	private void closeEntrance() {
		Wall gridLeft = new Wall(400, 650, 0, true, false, true, "PrisonUL");
		gridLeft.setMap(getMap());
		map.getMapObjects().get(1).add(gridLeft);
		Wall gridRight = new Wall(450, 650, 0, true, false, true, "PrisonUR");
		gridRight.setMap(getMap());
		map.getMapObjects().get(1).add(gridRight);
	}

	/**
	 * <b>private void plantFlame()</b>
	 * <p>
	 * Creates a new Flame object.
	 */
	private void plantFlame() {
		Flame flame = new Flame(posX, posY, true, true, false, "flames",
				map.getGraphics());
		flame.setMap(getMap());
		map.getMapObjects().get(1).add(flame);
	}

	/**
	 * <b>private void spitFire(String dir)</b>
	 * <p>
	 * Creates a new FireBreathe object moving into the committed direction.
	 * 
	 * @param dir
	 *            - direction.
	 */
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

	/**
	 * <b>private void aoeAttack()</b>
	 * <p>
	 * Initializes the aoe arrays. Only the coordinates of the tiles on which
	 * there is no wall.
	 */
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

	/**
	 * <b>private void clearAoeArr()</b>
	 * <p>
	 * Clears the aoe array.
	 */
	private void clearAoeArr() {
		for (int i = 0; i < xPos.length; i++) {
			xPos[i] = 0;
			yPos[i] = 0;
		}
	}

	/**
	 * <b>private void phase1()</b>
	 * <p>
	 * Checks if all fire bowls are destroyed. While not, skeletons are spawned.
	 * If all fire bowls are destroyed the portals disappear, the boss's
	 * x-coordinate is changed, closeEntrance() is called and phase2 is set to
	 * true.
	 */
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

	/**
	 * The move() and the plantFlame() method are called.
	 */
	private void phase2() {
		if (lives == 4) {
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

	/**
	 * Same as phase2().
	 */
	private void phase3() {
		if (lives == 3) {
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

	/**
	 * Same as phase2(). Additionally aoeAttack() is called.
	 */
	private void phase4() {
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

	/**
	 * <b>public void move()</b>
	 * <p>
	 * Moves the Enemy object over the panel. Checks the ability of moving into
	 * the relevant direction. If ability is given the Enemy object will be
	 * moved for a fixed number of pixels, if not findPath() will be called.
	 * Also calls spitFire() if the fight is in a higher phase than phase 2.
	 */
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

	/**
	 * <b>public void draw(Graphics2D g2d, ImageLoader gr, Graphics2D cm)</b>
	 * <p>
	 * Draws the current Images and the collision map.
	 * 
	 * @param g2d
	 *            a Graphics object of the actual drawing canvas.
	 * @param gr
	 *            an ImageLoader to request images if necessary.
	 * @param cm
	 *            a Graphics object to draw onto the collision map.
	 * 
	 */
	@Override
	public void draw(Graphics2D g2d, ImageLoader gr, Graphics2D cm) {
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

		g2d.drawImage(animation.getCurrentImage(), posX, posY, null);

		if (phase1) {
			cm.setPaint(Color.black); // bubble
		} else if (dying) {
			cm.setPaint(Color.black);
		} else {
			cm.setPaint(Color.red);
		}
		cm.fillRect(posX + 5, posY + 5, 40, 40);
	}

	/**
	 * <b>public void update(BufferedImage cm)</b>
	 * <p>
	 * Calls the phase method for the currently active phase. Also checks
	 * collision with bombs, if the boss have to die, the aoe animation and
	 * animates the boss himself.
	 */
	@Override
	public void update(BufferedImage cm) {
		if (startInit) {
			initPhase1();
			startInit = false;
		}

		if (lives == 0 && !dying) {
			phase4 = false;
			stop();
			die("enemyDying");
		}

		if (dying) {
			if (beforeTime + dyingTime <= System.nanoTime()) {
				destroyed = true;
				map.finishMap();
			}
		}

		animation.animate();

		if (simpleHasColl(posX, posY, cm, Color.orange)) {
			if (!immortal) {
				lives--;
				immortal = true;
				immortalStart = System.nanoTime();
			}
		}

		if (!phase1 && immortal) {
			if (immortalStart + immortalTime <= System.nanoTime()) {
				immortal = false;
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

	/**
	 * <b>public class Flame extends MapObject</b>
	 * <p>
	 * A Flame object displays a burning floor tile.
	 * 
	 * @author masto104
	 */
	static class Flame extends MapObject {

		/**
		 * Used to time the life time of the object.
		 */
		private double beforeTime, disappearTime = 1000000000L,
				lifeTime = 5000000000L;

		/**
		 * Used to time the disappearing animation.
		 */
		private boolean disappearing = false;

		/**
		 * Flame constructor.
		 * 
		 * @param x
		 *            - x-coordinate.
		 * @param y
		 *            - y-coordinate.
		 * @param v
		 *            - sets visibility.
		 * @param d
		 *            - sets 'destructible' flag.
		 * @param c
		 *            - sets 'collision' flag
		 * @param p
		 *            - AnimationSet filename
		 * @param gr
		 *            - ImageLoader
		 */
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

		/**
		 * <b>public void die(String animation)</b>
		 * <p>
		 * This method is called when the Enemy object has been struck by a
		 * bomb. The animation changes to the dying animation. The starting
		 * point of the dying animation is set.
		 */
		private void die(String animation) {
			beforeTime = System.nanoTime();
			disappearing = true;
			this.animation.change(animation);
		}
	}

	/**
	 * <b>public class FireBreathe extends Skull</b>
	 * <p>
	 * A FireBreath object displays a flame spit out from the boss.
	 * 
	 * @author masto104
	 */
	static class FireBreathe extends Skull {

		/**
		 * FireBreath constructor.
		 * 
		 * @param x
		 *            - x-coordinate.
		 * @param y
		 *            - y-coordinate.
		 * @param v
		 *            - sets visibility.
		 * @param d
		 *            - sets 'destructible' flag.
		 * @param c
		 *            - sets 'collision' flag
		 * @param p
		 *            - AnimationSet filename
		 * @param gr
		 *            - ImageLoader
		 */
		public FireBreathe(int x, int y, boolean v, boolean d, boolean c,
				String p, ImageLoader gr, String dir) {
			super(x, y, v, d, c, p, gr, dir);
		}
	}

}
