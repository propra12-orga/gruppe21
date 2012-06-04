package main;

import java.awt.Graphics;
import java.awt.event.KeyEvent;

/**
 * Abstract superclass of all GameUnits. A GameUnit acts as an interchangeable
 * module that is capable of handling KeyEvents, drawing itself and updating its
 * components.
 * 
 * @author tohei
 * 
 */
public abstract class GraphicalGameUnit {

	private UnitNavigator unitNavigator;

	public abstract void updateComponent();

	public abstract void handleKeyPressed(KeyEvent e);

	public abstract void handleKeyReleased(KeyEvent e);

	public abstract void initComponent();

	public abstract void drawComponent(Graphics g);
}
