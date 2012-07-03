package mapobjects;

import imageloader.ImageLoader;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Upgrade extends MapObject {

	private Color color;
	private String MPID;

	public Upgrade(int x, int y, boolean v, boolean d, boolean c, String p,
			ImageLoader gr, Color col) {
		super(x, y, v, d, c, p, gr);
		color = col;
		initAnimation(col);
	}

	public Upgrade(int x, int y, boolean v, boolean d, boolean c, String p,
			ImageLoader gr, Color col, String MPID) {
		super(x, y, v, d, c, p, gr);
		color = col;
		this.MPID = MPID;
		initAnimation(col);
	}

	private void initAnimation(Color c) {
		String upgradeAnimation;

		if (c.equals(Color.pink)) {
			upgradeAnimation = "BombPlus";
		} else if (c.equals(Color.blue)) {
			upgradeAnimation = "BombRange";
		} else if (c.equals(Color.cyan)) {
			upgradeAnimation = "Shield";
		} else if (c.equals(Color.magenta)) {
			upgradeAnimation = "BombRemote";
		} else if (c.equals(Color.lightGray)) {
			upgradeAnimation = "Bulletproof";
		} else {
			upgradeAnimation = "BombPlus";
		}

		animation.start(upgradeAnimation);
	}

	@Override
	public void update(BufferedImage collisionMap) {
		if (!simpleHasColl(posX, posY, collisionMap, Color.green,
				Color.darkGray)) {
			animation.animate();
		} else {
			map.pickUpEvent(this);
		}
	}

	@Override
	public void draw(Graphics2D g2d, ImageLoader gr, Graphics2D cmg) {
		if (!destroyed) {
			g2d.drawImage(animation.getCurrentImage(), posX, posY, null);
			cmg.setPaint(color);
			cmg.fillRect(posX + 10, posY + 10, 30, 30);
		} else {
			cmg.setPaint(Color.white);
			cmg.fillRect(posX + 10, posY + 10, 30, 30);
		}
	}

	public Color getColor() {
		return color;
	}

	public String getMPID() {
		return MPID;
	}

	public void setMPID(String MPID) {
		this.MPID = MPID;
	}

	public String getStringOfColor() {
		if (color.equals(Color.pink))
			return "pink";
		if (color.equals(Color.blue))
			return "blue";
		if (color.equals(Color.cyan))
			return "cyan";
		if (color.equals(Color.magenta))
			return "magenta";
		if (color.equals(Color.lightGray))
			return "lightGray";
		return "";
	}

	/**
	 * Listener interface for upgrade collision with players
	 * 
	 * @author Dorian
	 * 
	 */
	public interface CMListener {
		public void giveUpgrade(Upgrade upgrade);

		public boolean collidesWithListener(Upgrade upgrade);
	}
}
