package mapobjects;

import imageloader.Animation;
import imageloader.ImageLoader;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * A MapObject representing an explosive that may be dropped by the player. A
 * bombs properties include countdown and explosion periods as well as a
 * playerID To associate a Bomb object with its player (as it is important for
 * multiplayer matches). <br>
 * To dynamically calculate the explosion, a bomb checks for every cardinal
 * direction. Solely indestructible objects will impede the expansion. All this
 * is done in the calcArms() method. <br>
 * The explosion itself consists of two individual animation objects, one
 * representing the 'head' of the advancing detonation and one serving as a
 * connecting segment in between center and head.
 * 
 * @author eik
 */

public class Bomb extends MapObject {

	/**
	 * Necessary to identify the corresponding Player object.
	 */
	private int playerID;
	/**
	 * This variable determines how far the explosion will advance in every
	 * direction.
	 */
	private int radius = 1;
	/**
	 * This animation will connect the center of an explosion with all four
	 * heads, respectively.
	 */
	Animation armanimation;
	/**
	 * An animation object representing the 'head' of an explosion.
	 */
	Animation endanimation;
	/**
	 * This array will be used by calcArms(). Its values state how far an
	 * explosion may expand in every cardinal direction.
	 */
	int arms[] = new int[4];
	/**
	 * This will be set to true as soon as the player moves away from the bomb.
	 * Subsequently, a bomb's collision flag will be set to true as well.
	 */
	boolean playerleft = false;
	/**
	 * As soon as the coundown period is over, the bomb will explode (in
	 * nanoseconds).
	 */
	private long countdownTime = 4000000000L;
	/**
	 * This variable determines for how long the explosion will pose a threat to
	 * other MapObjects (in nanoseconds).
	 */
	private long explosionTime = 500000000;
	/**
	 * Will be initialized on creating a new bomb object (System time in
	 * nanoseconds).
	 */
	private long beforeTime;
	/**
	 * Will be set to true as soon as the coundown time is over.
	 */
	private boolean exploding = false;
	/**
	 * Will be set to true on firing the bomb.
	 */
	private boolean activated = false;

	/**
	 * constructor
	 * 
	 * @param x
	 *            x coordinate
	 * @param y
	 *            y coordinate
	 * @param v
	 *            sets visibility
	 * @param d
	 *            sets destroyable
	 * @param c
	 *            sets collision
	 * @param p
	 *            image url for default
	 * @param gr
	 *            the ImageLoader of the map
	 * @param r
	 *            sets rotation of the image (0,1,2,3)
	 * @param cm
	 *            collision map of the current map
	 * @param playerID
	 *            id of the player who layed the bomb
	 */

	public Bomb(int x, int y, boolean v, boolean d, boolean c, String p,
			ImageLoader gr, int r, BufferedImage cm, int playerID) {
		super(x, y, v, d, c, p, gr);
		posX = (x + 25) / 50 * 50;
		posY = (y + 25) / 50 * 50;
		radius = r;
		animation.start("simplebomb");
		armanimation = new Animation("simplebomb", gr);
		endanimation = new Animation("simplebomb", gr);
		armanimation.start("gerade");
		endanimation.start("up");
		this.playerID = playerID;
	}

	/**
	 * <<<<<<< HEAD sets the radius of the bomb
	 * 
	 * @param radius
	 *            bomb radius ======= A bomb's explosion radius may be changed
	 *            due to player upgrades.
	 * 
	 * @param radius
	 *            new explosion radius. >>>>>>>
	 *            ae0e39d559abbfefcd1bc0ce6b0fb70f201b5052
	 */
	public void setRadius(int radius) {
		this.radius = radius;
	}

	/**
	 * <b>draw</b>
	 * <p>
	 * Method which draws the bomb's current images. Also draws specific parts
	 * of the collision map. Called by the map.
	 * 
	 * @param g2d
	 *            - graphics2D object which draws the images
	 * @param gr
	 *            - ImageLoader
	 * @param cm
	 *            - graphics2D objects which draws parts of the collision map
	 * @see mapobjects.MapObject#draw(java.awt.Graphics2D,
	 *      imageloader.ImageLoader, java.awt.Graphics2D)
	 * @see imageloader.ImageLoader
	 */
	@Override
	public void draw(Graphics2D g2d, ImageLoader gr, Graphics2D cm) {
		g2d.drawImage(animation.getCurrentImage(), posX, posY, null);
		if (exploding) {
			cm.setPaint(Color.orange);
		} else if (!(playerleft && collides())) {
			cm.setPaint(Color.white);
		} else if (collides()) {
			cm.setPaint(Color.gray);
		}
		cm.fillRect(posX + 5, posY + 5, 40, 40);

		if (exploding) {

			for (int i = 0; i < arms.length; i++) {
				for (int j = 0; j < arms[i]; j++) {
					if (i == 0) {
						if (j == arms[i] - 1) {
							g2d.drawImage(
									rotate(endanimation.getCurrentImage(), i),
									posX, posY - (j + 1) * 50, null);
						} else {
							g2d.drawImage(
									rotate(armanimation.getCurrentImage(), i),
									posX, posY - (j + 1) * 50, null);
						}
						cm.setPaint(Color.orange);
						cm.fillRect(posX + 5, posY - (j + 1) * 50 + 5, 40, 40);
					}
					if (i == 1) {
						if (j == arms[i] - 1) {
							g2d.drawImage(
									rotate(endanimation.getCurrentImage(), i),
									posX + (j + 1) * 50, posY, null);
						} else {
							g2d.drawImage(
									rotate(armanimation.getCurrentImage(), i),
									posX + (j + 1) * 50, posY, null);
						}
						cm.setPaint(Color.orange);
						cm.fillRect(posX + (j + 1) * 50 + 5, posY + 5, 40, 40);
					}
					if (i == 2) {
						if (j == arms[i] - 1) {
							g2d.drawImage(
									rotate(endanimation.getCurrentImage(), i),
									posX, posY + (j + 1) * 50, null);
						} else {
							g2d.drawImage(
									rotate(armanimation.getCurrentImage(), i),
									posX, posY + (j + 1) * 50, null);
						}
						cm.setPaint(Color.orange);
						cm.fillRect(posX + 5, posY + (j + 1) * 50 + 5, 40, 40);
					}
					if (i == 3) {
						if (j == arms[i] - 1) {
							g2d.drawImage(
									rotate(endanimation.getCurrentImage(), i),
									posX - (j + 1) * 50, posY, null);
						} else {
							g2d.drawImage(
									rotate(armanimation.getCurrentImage(), i),
									posX - (j + 1) * 50, posY, null);
						}
						cm.setPaint(Color.orange);
						cm.fillRect(posX - (j + 1) * 50 + 5, posY + 5, 40, 40);
					}

				}
			}
		}
	}

	/**
	 * Calculates detonation expansion using the collision map.
	 * 
	 * @param cm
	 *            collision map
	 */
	private void calcArms(BufferedImage cm) {
		for (int i = 0; i < arms.length; i++) {
			for (int j = 0; j < radius; j++) {
				arms[i] = radius;
				if (i == 0) {
					if (simpleHasColl(posX, posY - ((j + 1) * 50), cm,
							Color.black)) {
						arms[i] = j;
						break;
					}
				}
				if (i == 1) {
					if (simpleHasColl(posX + ((j + 1) * 50), posY, cm,
							Color.black)) {
						arms[i] = j;
						break;
					}
				}
				if (i == 2) {
					if (simpleHasColl(posX, posY + ((j + 1) * 50), cm,
							Color.black)) {
						arms[i] = j;
						break;
					}
				}
				if (i == 3) {
					if (simpleHasColl(posX - ((j + 1) * 50), posY, cm,
							Color.black)) {
						arms[i] = j;
						break;
					}
				}
			}
		}
	}

	/**
	 * <b>update</b>
	 * <p>
	 * public void update(BufferedImage cm)
	 * <p>
	 * Called by the Map before drawing the images.
	 * 
	 * @param cm
	 *            - image used for collision detection
	 * @see mapobjects.MapObject#update(java.awt.image.BufferedImage)
	 * @see map.Map#update()
	 */
	@Override
	public void update(BufferedImage cm) {
		armanimation.animate();
		endanimation.animate();

		if (visible && !exploding) {
			animation.animate();

			if (activated && beforeTime + countdownTime <= System.nanoTime()
					|| simpleHasColl(posX, posY, cm, Color.orange)) {
				this.explode(cm);
			}
		}

		if (visible && exploding) {
			animation.animate();
			collision = false;
			if (beforeTime + explosionTime <= System.nanoTime()) {
				animation.stop();
				visible = false;
				destroyed = true;

			}
		}

		if (!playerleft) {
			if (!simpleHasColl(this.posX, this.posY, cm, Color.green)) {
				playerleft = true;
				collision = true;
			}
		}
	}

	/**
	 * Changes the bomb's animation to an animation of the explosion. Sets the
	 * start point the of explosion time. Calculates the arms of the explosion.
	 * Shifts the object into a higher layer, so that the explosion will be
	 * painted over the other objects.
	 * 
	 * @param cm
	 *            collision map
	 */
	public void explode(BufferedImage cm) {
		animation.stop();
		animation.change("center");
		exploding = true;
		beforeTime = System.nanoTime();
		animation.start("center");

		// verschiebe Bombe bei explosion in höhere ebene, damit
		// bombengrafik über player
		this.map.getMapObjects().get(1).remove(this);
		this.map.getMapObjects().get(3).add(this);
		// armlängen berechnen
		calcArms(cm);
	}

	/**
	 * <<<<<<< HEAD
	 * 
	 * @return playerID as int ======= Check for playerID.
	 * 
	 * @return ID of the player who planted the bomb. >>>>>>>
	 *         ae0e39d559abbfefcd1bc0ce6b0fb70f201b5052
	 */
	public int getPlayerID() {
		return playerID;
	}

	/**
	 * Fire the bomb.
	 */
	public void activateBomb() {
		activated = true;
		beforeTime = System.nanoTime();
	}

}
