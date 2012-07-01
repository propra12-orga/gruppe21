package mapeditor;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;

public class Tab {

	private int posX;
	private int posY;
	private int width;
	private int height;
	private Shape tabGraphic;
	private String mode;

	public Tab(int x, int y, int w, int h, String mode) {
		this.setPosX(x);
		this.setPosY(y);
		this.setWidth(w);
		this.setHeight(h);
		this.setMode(mode);

		tabGraphic = new Rectangle2D.Float(x, y, w, h);
	}

	public void draw(Graphics2D g2d, boolean active) {
		if (active) {
			g2d.setColor(Color.GRAY);
			g2d.draw(tabGraphic);
			g2d.fill(tabGraphic);

			g2d.setColor(Color.white);
			g2d.setFont(new Font("Arial", Font.BOLD, 14));
			g2d.drawString(this.mode, posX + 5, posY + 15);

		} else {
			g2d.setColor(Color.DARK_GRAY.brighter());
			g2d.draw(tabGraphic);
			g2d.fill(tabGraphic);

			g2d.setColor(Color.white.darker());
			g2d.setFont(new Font("Arial", Font.BOLD, 14));
			g2d.drawString(this.mode, posX + 5, posY + 15);
		}
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

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}
}
