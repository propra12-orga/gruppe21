package mapobjects;

import imageloader.ImageLoader;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Vector;

/**
 * Player object is the player controlled by the player
 * 
 * @author eik
 * 
 */
public class Player extends MoveableObject {
	/**
	 * player id , for multiplayer
	 */
	private int ID;
	/**
	 * maximal number of bombs that the player could use
	 */
	private int maxbombs = 1;
	/**
	 * number of active bombs on the playground
	 */
	private int actualbombs = 0;
	/**
	 * radius of the bomb
	 */
	private int bombradius = 1;
	/**
	 * set if player is alive
	 */
	private boolean alive;
	/**
	 * Set true if upgrade bomb remote has been collected.
	 */
	private boolean bombRemote = false;

	private boolean immortal = false;
	/**
	 * List of the actual planted bombs
	 */
	private Vector<Bomb> remoteBombs = new Vector<Bomb>();

	private boolean maxBombs_used = false, bombRadius_used = false,
			playerSpeed_used = false;

	/**
	 * constructor
	 * 
	 * @param x
	 *            x-position
	 * @param y
	 *            y-position
	 * @param v
	 *            set visibility
	 * @param d
	 *            set destroyable
	 * @param c
	 *            set collisionable
	 * @param p
	 *            image url
	 * @param gr
	 *            imageloader of the map
	 */
	public Player(int x, int y, boolean v, boolean d, boolean c, String p,
			ImageLoader gr) {
		super(x, y, v, d, c, p, gr);
		alive = true;
	}

	// TODO UseWeapon or LayBomB,ChangeWeapon

	@Override
	public void move() {
		if (direction.isUp()) {
			if (hasObjectCollision(posX, posY - speed, map.getCollisionMap(),
					"UP")) {
			} else {
				posY -= speed;
			}
			animation.change("playerUp");
		}

		if (direction.isDown()) {
			if (hasObjectCollision(posX, posY + speed, map.getCollisionMap(),
					"DOWN")) {
			} else {
				posY += speed;
			}
			animation.change("playerDown");
		}

		if (direction.isLeft()) {
			if (hasObjectCollision(posX - speed, posY, map.getCollisionMap(),
					"LEFT")) {
			} else {
				posX -= speed;
			}
			animation.change("playerLeft");
		}

		if (direction.isRight()) {
			if (hasObjectCollision(posX + speed, posY, map.getCollisionMap(),
					"RIGHT")) {
			} else {
				posX += speed;
			}
			animation.change("playerRight");
		}
	}

	/**
	 * Adds bomb to the mapObjects. If Upgrade bombRemote has been collected the
	 * bomb object will be added to a list of actual planted bombs.
	 * 
	 * @param cm
	 *            collision map
	 */
	public void plantBomb(BufferedImage cm) {
		if (!reachedMaxBombs()) {
			Bomb bomb = new Bomb(getPosX(), getPosY(), true, false, false,
					"simplebomb", map.getGraphics(), bombradius, cm, ID);
			bomb.setMap(getMap());
			map.getMapObjects().get(1).add(bomb);
			addBomb();

			if (!bombRemote) {
				bomb.activateBomb();
			} else {
				remoteBombs.add(bomb);
			}
		}
	}

	@Override
	public boolean hasObjectCollision(int x, int y, BufferedImage cm, String dir) {
		if (x < 0 || y < 0 || x > cm.getWidth() - 50 || y > cm.getHeight() - 50) {
			return true;
		}
		int t = 20;
		boolean upleft = false;
		boolean upright = false;
		boolean downleft = false;
		boolean downright = false;
		BufferedImage collTest = cm.getSubimage(x, y, 50, 50);
		for (int i = 0; i < collTest.getWidth(); i++) {
			for (int j = 0; j < collTest.getHeight(); j++) {
				Color test = new Color(collTest.getRGB(i, j));
				if (test.equals(Color.yellow)) {
					map.finishMap();
				} else if (test.equals(Color.orange)) {
					if (!immortal) {
						this.die();
						map.finishMap();
					} else {
						return true;
					}
				} else if (test.equals(Color.red)) {
					if (!immortal) {
						this.die();
						map.finishMap();
					} else {
						return true;
					}
				} else if (test.equals(Color.black) || test.equals(Color.gray)) {
					if (i < t && j < t) {
						upleft = true;
					} else if (i > 50 - t && j < t) {
						upright = true;
					} else if (i < t && j > 50 - t) {
						downleft = true;
					} else if (i > 50 - t && j > 50 - t) {
						downright = true;
					} else {
						return true;
					}
				}
			}
		}

		if (upleft ^ upright ^ downleft ^ downright) {
			if (upleft) {
				if (dir.equals("UP")) {
					if (simpleHasColl(posX + 1, posY, cm, Color.black,
							Color.gray)) {
						return true;
					} else {
						this.posX += 1;
						return true;
					}
				}

				if (dir.equals("LEFT")) {
					if (simpleHasColl(posX, posY + 1, cm, Color.black,
							Color.gray)) {
						return true;
					} else {
						this.posY += 1;
						return true;
					}
				}
			}

			if (upright) {
				if (dir.equals("UP")) {
					if (simpleHasColl(posX - 1, posY, cm, Color.black,
							Color.gray)) {
						return true;
					} else {
						this.posX -= 1;
						return true;
					}

				}

				if (dir.equals("RIGHT")) {
					if (simpleHasColl(posX, posY + 1, cm, Color.black,
							Color.gray)) {
						return true;
					} else {
						this.posY += 1;
						return true;
					}
				}
			}

			if (downleft) {
				if (dir.equals("DOWN")) {
					if (simpleHasColl(posX + 1, posY, cm, Color.black,
							Color.gray)) {
						return true;
					} else {
						this.posX += 1;
						return true;
					}
				}

				if (dir.equals("LEFT")) {
					if (simpleHasColl(posX, posY - 1, cm, Color.black,
							Color.gray)) {
						return true;
					} else {
						this.posY -= 1;
						return true;
					}
				}
			}

			if (downright) {
				if (dir.equals("DOWN")) {
					if (simpleHasColl(posX - 1, posY, cm, Color.black,
							Color.gray)) {
						return true;
					} else {
						this.posX -= 1;
						return true;
					}
				}

				if (dir.equals("RIGHT")) {
					if (simpleHasColl(posX, posY - 1, cm, Color.black,
							Color.gray)) {
						return true;
					} else {
						this.posY -= 1;
						return true;
					}

				}
			}

		}// else{return true;}

		return false;
	}

	/**
	 * checks for enemy/bomb collison , kills player if true
	 * 
	 * @cm collision map
	 */
	@Override
	public void update(BufferedImage cm) {
		if (simpleHasColl(posX, posY, map.getCollisionMap(), Color.orange,
				Color.red)) {
			this.die();
			map.finishMap();
		}
		animation.animate();
		move();
		checkUpgradeCollision(cm);
	}

	/**
	 * Causes all planted bombs to explode. Method is called by pressing a key,
	 * but only if the Upgrade bombRemote has been collected.
	 */
	public void bombExplode() {
		for (Bomb bomb : remoteBombs) {
			bomb.explode(map.getCollisionMap());
		}
	}

	/**
	 * Checks for collision with Upgrades which are on the map. Changes the
	 * corresponding attribute or activates the special effect if there is a
	 * collision.
	 */
	private void checkUpgradeCollision(BufferedImage cm) {
		if (simpleHasColl(posX, posY, cm, Color.pink) && !maxBombs_used) {
			if (maxbombs < 4) {
				maxbombs++;
				maxBombs_used = true;
				System.out.printf("maxbombs: %d\n", maxbombs);
			}
		}
		if (simpleHasColl(posX, posY, cm, Color.blue) && !bombRadius_used) {
			if (bombradius < 3) {
				bombradius++;
				bombRadius_used = true;
				System.out.printf("Radius: %d\n", bombradius);
			}
		}
		if (simpleHasColl(posX, posY, cm, Color.cyan) && !playerSpeed_used) {
			if (speed < 5) {
				speed++;
				playerSpeed_used = true;
				System.out.printf("Speed: %d\n", speed);
			}
		}
		if (simpleHasColl(posX, posY, cm, Color.magenta)) {
			bombRemote = true;
		}

		if (maxBombs_used) {
			if (!simpleHasColl(posX, posY, cm, Color.pink)) {
				maxBombs_used = false;
			}
		}

		if (bombRadius_used) {
			if (!simpleHasColl(posX, posY, cm, Color.blue)) {
				bombRadius_used = false;
			}
		}

		if (playerSpeed_used) {
			if (!simpleHasColl(posX, posY, cm, Color.cyan)) {
				playerSpeed_used = false;
			}
		}
	}

	@Override
	public void draw(Graphics2D g2d, ImageLoader gr, Graphics2D cm) {
		g2d.drawImage(animation.getCurrentImage(), posX, posY, null);
		cm.setPaint(Color.green);
		cm.fillRect(posX, posY, 50, 50);

	}

	/**
	 * 
	 * @return true if max bomb level is reached else false
	 */
	public boolean reachedMaxBombs() {
		return (actualbombs + 1 > maxbombs);
	}

	/**
	 * increase bombcounter
	 */
	public void addBomb() {
		actualbombs++;
	}

	/**
	 * decrease bombcounter
	 */
	public void removeBomb() {
		actualbombs--;
	}

	/**
	 * Returns a player's identification number.
	 * 
	 * @return current ID
	 */
	public int getID() {
		return ID;
	}

	/**
	 * Sets a player's identification number.
	 * 
	 * @param iD
	 *            new ID
	 */
	public void setID(int iD) {
		ID = iD;
	}

	/**
	 * Allows checking for a player's 'alive'-status.
	 * 
	 * @return alive flag
	 */
	public boolean isAlive() {
		return alive;
	}

	/**
	 * Kills player object (i.e. sets its 'alive' flag to false).
	 */
	public void die() {
		alive = false;
	}
}