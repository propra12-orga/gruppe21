package enemies;

import imageloader.ImageLoader;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import mapobjects.MapObject;

/**
 * <b>public class Slime extends MapObject</b>
 * <p>
 * A Slime object displays some slime which is left by a Monster object. It is
 * very poisonous and should not be touched by the player.
 * 
 * @author masto104
 */
public class Slime extends MapObject {

	/**
	 * The name of the dying animation.
	 */
	private String dyingAnimation;

	/**
	 * Variables for timing the animation.
	 */
	private double beforeTime, lifeTime = 2000000000L,
			disappearTime = 1000000000L;

	/**
	 * True, if lifeTime is over.
	 */
	private boolean disappearing = false;

	/**
	 * Slime constructor.
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
	 * @param dir
	 *            - moving direction of the Monster object who left this object.
	 */
	public Slime(int x, int y, boolean v, boolean d, boolean c, String p,
			ImageLoader gr, String dir) {
		super(x, y, v, d, c, p, gr);
		beforeTime = System.nanoTime();
		initAnimation(dir);
	}

	/**
	 * <b>public void initDirection(String dir)</b>
	 * <p>
	 * Initializes the matching animations for the committed direction.
	 * 
	 * @param dir
	 *            - direction.
	 */
	private void initAnimation(String dir) {
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
			System.out.println("Slime#initAnimation(): Fehlerhafte Richtung");
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

	/**
	 * <b>public void die(String animation)</b>
	 * <p>
	 * This method is called when the Enemy object has been struck by a bomb.
	 * The animation changes to the dying animation. The starting point of the
	 * dying animation is set.
	 */
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
