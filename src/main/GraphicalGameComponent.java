package main;

import java.awt.Graphics;
import java.awt.event.KeyEvent;

public abstract class GraphicalGameComponent {
	
	private BMGame game;
	
	public abstract void updateComponent();
	public abstract void handleKeyPressed(KeyEvent e);
	public abstract void handleKeyReleased(KeyEvent e);
	public abstract void initComponent();
	public abstract void drawComponent(Graphics g);
	
	public BMGame getGame() {
		return game;
	}
	public void setGame(BMGame game) {
		this.game = game;
	}
	
	
}
