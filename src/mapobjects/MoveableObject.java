package mapobjects;

import imageloader.ImageLoader;

/**
 * abstract super class of all moving mapobjects (player,enemies) extends the
 * standart MapObject adds direction
 * 
 * @author eik
 * 
 */
public abstract class MoveableObject extends MapObject {
	/**
	 * object that has information about the current direction of the object
	 */
	public Direction direction;
	/**
	 * the speed of the object
	 */
	protected int speed = 3;

	/**
	 * standard constructor
	 * 
	 * @param x
	 *            x-position on the map
	 * @param y
	 *            y-position on the map
	 * @param v
	 *            boolean visibility , true if visible else false
	 * @param d
	 *            boolean destroyable , true if destroyable else false
	 * @param c
	 *            sets 'collision flag'
	 * @param p
	 *            path for the defaultimage
	 * @param gr
	 *            iamgeloader of the map
	 */
	public MoveableObject(int x, int y, boolean v, boolean d, boolean c,
			String p, ImageLoader gr) {
		super(x, y, v, d, c, p, gr);
		direction = new Direction();
	}

	/**
	 * abstract method move that all moving objects have to implement defines
	 * movement of the object
	 */
	public abstract void move();

	/**
	 * checks for enemy collision
	 * 
	 * @param x
	 *            current x position
	 * @param y
	 *            current y position
	 * @return returns if enemy collision is true or false
	 */
	public boolean hasEnemyCollision(int x, int y) {
		return false;
	}

	/**
	 * 
	 * @return returns the current speed of the moving object
	 */
	public int getSpeed() {
		return speed;
	}

	/**
	 * Sets moving speed value.
	 * 
	 * @param speed
	 *            new speed value.
	 */
	public void setSpeed(int speed) {
		this.speed = speed;
	}
}
