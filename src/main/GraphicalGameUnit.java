package main;

import java.awt.Graphics;
import java.awt.event.KeyEvent;

/*
 * abstract superclass of all game units. 
 */
public abstract class GraphicalGameUnit {
	
	private UnitNavigator unitNavigator;
	
	public abstract void updateComponent();
	public abstract void handleKeyPressed(KeyEvent e);
	public abstract void handleKeyReleased(KeyEvent e);
	public abstract void initComponent();
	public abstract void drawComponent(Graphics g);
	
	public UnitNavigator getNavigator() {
		return unitNavigator;
	}
	public void setNavigator(UnitNavigator navigator) {
		this.unitNavigator = navigator;
	}
	
	
}
