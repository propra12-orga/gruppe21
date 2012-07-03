package mapeditor;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Vector;

public class EditorMap {
	private int width;
	private int height;
	private int tilesX;
	private int tilesY;

	private Vector<Tile> tileListL1 = new Vector<Tile>();
	private Vector<Tile> tileListL2 = new Vector<Tile>();

	private Vector<Tile> currentTileList = tileListL1;

	private int currentDrawLevel = 1;

	public EditorMap(int w, int h) {
		tilesX = w;
		tilesY = h;
		width = w * 50;
		height = h * 50;
	}

	public void draw(Graphics2D g2d) {
		g2d.setColor(Color.white);
		g2d.fillRect(0, 0, width, height);

		for (int i = 0; i < tileListL1.size(); i++) {
			tileListL1.get(i).drawOnMap(g2d, 0, 0);
		}

		if (currentDrawLevel == 2) {
			g2d.setColor(new Color(0, 0, 0, 100));
			g2d.fillRect(0, 0, width, height);

			for (int i = 0; i < tileListL2.size(); i++) {
				tileListL2.get(i).drawOnMap(g2d, 0, 0);
			}
		}

		g2d.setColor(Color.black);
		g2d.drawLine(0, 0, 0, height);
		g2d.drawLine(0, 0, width, 0);
		g2d.drawLine(width, 0, width, height);
		g2d.drawLine(0, height, width, height);

		for (int i = 1; i < tilesX; i++) {
			g2d.drawLine(i * 50, 0, i * 50, height);
			g2d.drawLine(i * 50 - 1, 0, i * 50 - 1, height);
		}
		for (int i = 1; i < tilesY; i++) {
			g2d.drawLine(0, i * 50, width, i * 50);
			g2d.drawLine(0, i * 50 - 1, width, i * 50 - 1);
		}
	}

	public void addTile(int x, int y, Tile drawTile) {
		Tile newTile = (Tile) drawTile.clone();
		int newPosX = (x / 50) * 50;
		int newPosY = (y / 50) * 50;
		newTile.setPosX(newPosX);
		newTile.setPosY(newPosY);

		for (int i = 0; i < currentTileList.size(); i++) {
			if (currentTileList.get(i).getPosX() == newPosX
					&& currentTileList.get(i).getPosY() == newPosY) {
				currentTileList.remove(i);
			}

		}
		currentTileList.add(newTile);
		System.out.println(currentTileList.size());
	}

	public void switchLevel() {
		if (currentDrawLevel == 1) {
			currentDrawLevel = 2;
			currentTileList = tileListL2;
		} else if (currentDrawLevel == 2) {
			currentDrawLevel = 1;
			currentTileList = tileListL1;
		}
		System.out.println(currentDrawLevel);
	}
}
