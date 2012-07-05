package mapeditor;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.util.Vector;

public class TilePanel {
	private Vector<Tile> tileList = new Vector<Tile>();

	private int posX;
	private int posY;
	private int width;
	private int height;
	private int tilecounter = 0;

	private int offset = 0;

	public TilePanel(int x, int y, int w, int h) {
		this.setPosX(x);
		this.setPosY(y);
		this.setWidth(w);
		this.setHeight(h);
	}

	public void draw(Graphics2D g2d) {
		g2d.setColor(Color.DARK_GRAY.brighter());
		g2d.fill(new Rectangle2D.Float(posX, posY, width, height));
		for (int i = 0; i < tileList.size(); i++) {
			tileList.get(i).draw(g2d, posX, posY);
		}
	}

	public void addTile(File file) {
		tileList.add(new Tile(file, tilecounter));
		tilecounter++;

	}

	public void addEnemy(File file) {
		tileList.add(new Tile(file, tilecounter, true));
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

	public void deselectAll() {
		for (int i = 0; i < tileList.size(); i++) {
			tileList.get(i).deselect();
		}

	}

	public Tile getTile(int mouseX, int mouseY) {
		File defaultF = new File("graphics/game/map/floorempty.png");
		System.out.println(defaultF.getAbsolutePath());
		Tile tile = new Tile(defaultF, 0);

		for (int i = 0; i < tileList.size(); i++) {
			System.out.println(mouseX + ", " + mouseY + ", "
					+ (tileList.get(i).getPosX() + posX) + ", "
					+ (tileList.get(i).getPosY() + posY - offset) + ", "
					+ (45 + posX) + ", " + (45 + posY - offset));
			if (Mouse.isInRegion(mouseX, mouseY, tileList.get(i).getPosX()
					+ posX, tileList.get(i).getPosY() + posY - offset, tileList
					.get(i).getPosX() + 45 + posX, tileList.get(i).getPosY()
					+ 45 + posY - offset)) {
				tile = tileList.get(i);
				System.out.println("mouse is here");
			}
		}
		return tile;
	}

}
