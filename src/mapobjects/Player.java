package mapobjects;

import imageloader.ImageLoader;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

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
	private int maxbombs = 3;
	/**
	 * number of active bombs on the playground
	 */
	private int actualbombs = 0;
	/**
	 * radius of the bomb
	 */
	private int bombradius = 2;
	/**
	 * set if player is alive
	 */
	private boolean alive;

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
	 * adds bomb to the mapobject
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
			bomb.activateBomb();
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
					this.die();
					map.finishMap();
				} else if (test.equals(Color.red)) {
					this.die();
					map.finishMap();
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