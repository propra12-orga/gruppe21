package main;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Abstract superclass of all GameUnits. A GameUnit acts as an interchangeable
 * module that is capable of handling KeyEvents, drawing itself and updating its
 * components.
 * 
 * @author tohei
 * @see main.UnitState
 */
public abstract class GraphicalGameUnit {

	/**
	 * Can be used to define a unit's font.
	 */
	protected Font unitFont;

	/**
	 * A more general way of updating a GraphicalGameUnit's components.
	 */
	public abstract void updateComponent();

	/**
	 * Offers a way of reacting to user interaction by passing KeyEvents
	 * received from a Listener to the GraphicalGameUnit
	 * 
	 * @param e
	 *            Java.awt.event.KeyEvent
	 */
	public abstract void handleKeyPressed(KeyEvent e);

	/**
	 * Like handleKeyPressed(), this method can be used to communicate with the
	 * GraphicalGameUnit.
	 * 
	 * @param e
	 *            Java.awt.event.KeyEvent
	 */
	public abstract void handleKeyReleased(KeyEvent e);

	/**
	 * Initialize a GraphicalGameUnit's components.
	 */
	public abstract void initComponent();

	/**
	 * Allows delegation of the drawing process.
	 * 
	 * @param g
	 *            Graphics object to be used for drawing.
	 */
	public abstract void drawComponent(Graphics g);

	/**
	 * May be used to load non-java fonts from file (assuming it is located in
	 * the 'fonts' directory).
	 * 
	 * @param filename
	 *            font name
	 * @return loaded font
	 * @throws FontFormatException
	 * @throws IOException
	 */
	public static Font loadFont(String filename) throws FontFormatException,
			IOException {
		InputStream is = new FileInputStream(new File(GameConstants.FONTS_DIR
				+ filename));
		return Font.createFont(Font.TRUETYPE_FONT, is);
	}
}
