package mapeditor;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.Vector;

public class TilePanel {
	private Vector<Tile> tileList = new Vector<Tile>();

	private int posX;
	private int posY;
	private int width;
	private int height;
	private int tilecounter = 0;

	public TilePanel(int x, int y, int w, int h) {
		this.setPosX(x);
		this.setPosY(y);
		this.setWidth(w);
		this.setHeight(h);
	}

	public void draw(Graphics2D g2d) {
		g2d.setColor(Color.DARK_GRAY.brighter());
		g2d.fill(new Rectangle2D.Float(posX, posY, width, height));
	}

	public void addTile(Tile tile) {
		tileList.add(tile);
		tilecounter++;
	}

	/**
	 * Getter and Setter
	 * 
	 */

	public int getPosX() {
		return posX;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public int getPosY() {
		return posY;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
}
