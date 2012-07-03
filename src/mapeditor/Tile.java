package mapeditor;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Class for the Tiles in the Tilenavigator saves, position, type and other
 * prosperties of a tile
 * 
 * @author eik
 * 
 */
public class Tile implements Cloneable {

	private int posX;
	private int posY;
	private boolean selected = false;
	private String name;
	private String type;
	private boolean collision;
	private boolean visible = true;
	private boolean destroyable;

	private BufferedImage image;
	private int rotation = 0;
	private boolean animated = false;

	public Tile(File file, int tilecounter) {
		this.setPosX(((tilecounter % 4) * 45) + (tilecounter % 4 + 1) * 2);
		this.setPosY(((tilecounter / 4) * 45) + (tilecounter / 4 + 1) * 4);
		this.setName(file.getName());
		if (file.getName().endsWith(".xml")) {
			animated = true;
			image = EditorGraphics.loadImageAni(file.getAbsolutePath());

		} else if (file.getName().endsWith(".png")) {

			image = EditorGraphics.loadImage(file.getAbsolutePath());
		}
		selected = true;
	}

	public Tile(String string) {
		this.setPosX(0);
		this.setPosY(0);
		System.out.println("this is the url" + string);
		image = EditorGraphics.loadImage(string);

	}

	@Override
	public Object clone() {
		try {
			return super.clone();
		} catch (Exception e) {
			return null;
		}
	}

	public void draw(Graphics2D g2d, int offX, int offY) {
		int x = posX + offX;
		int y = posY + offY;

		g2d.drawImage(image, x, y, 45, 45, null);
		if (animated) {
			g2d.setColor(Color.white);
			g2d.fillOval(x + 3, y + 3, 8, 8);
			g2d.setColor(Color.black);
			g2d.drawOval(x + 3, y + 3, 8, 8);
		}
		if (selected) {
			g2d.setColor(Color.red);
			g2d.drawRect(x - 1, y - 1, 46, 46);
		}
	}

	public void drawOnMap(Graphics2D g2d, int offX, int offY) {
		int x = posX + offX;
		int y = posY + offY;

		g2d.drawImage(image, x, y, 50, 50, null);
	}

	public int getPosX() {
		return posX;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public int getPosY() {
		return posY;
	}

	public void setPosY(int y) {
		this.posY = y;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean hasCollision() {
		return collision;
	}

	public void setCollision(boolean collision) {
		this.collision = collision;
	}

	public void deselect() {
		selected = false;
	}

	public BufferedImage getImage() {
		return this.image;
	}

	public void select() {
		selected = true;

	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public boolean isDestroyable() {
		return destroyable;
	}

	public void setDestroyable(boolean destroyable) {
		this.destroyable = destroyable;
	}

	public int getRotation() {
		return rotation;
	}

	public void setRotation(int rotation) {
		this.rotation = rotation;
	}

}
