package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

/**
 * This subclass of GraphicalGameUnit displays an image (message) that can be
 * used to provide some sort of transition in between two (different)
 * GraphicalGameUnits.<br>
 * This may include:
 * <ul>
 * <LI>Loading screens</LI>
 * <LI>Win/Lose messages</LI>
 * </ul>
 * etc.<br>
 * In order to determine the next GraphicalGameUnit to move to, one has to pass
 * its UnitState (and optionally the unit itself) to the TransitionUnit's
 * constructor.<br>
 * Note that pressing the 'Escape' key will lead to a transition to the
 * GraphicalGameUnit located at UnitState.BASE_MENU_UNIT (normally this will be
 * the MainMenuUnit).
 * 
 * @author tohei
 * 
 * @see main.UnitState
 * @see main.GraphicalGameUnit
 */
public class TransitionUnit extends GraphicalGameUnit {

	/**
	 * The image to display.
	 */
	private BufferedImage message;
	/**
	 * This UnitState will be set active on pressing the 'Enter' key.
	 */
	private UnitState nextUnitState;
	/**
	 * May be used to add the next unit to the UnitNavigator's array of
	 * GraphicalGameUnits. This is necessary if the next unit is to be placed at
	 * the same UnitState as the TransitionUnit itself.
	 */
	private GraphicalGameUnit nextUnit;

	/**
	 * Used to center the message.
	 */
	private int messagePosX;
	/**
	 * Used to center the message.
	 */
	private int messagePosY;

	/**
	 * Constructs a TransitionUnit.
	 * 
	 * @param nextUnitState
	 *            the UnitState of the succeeding GraphicalGameUnit.
	 * @param message
	 *            the message to display.
	 */
	public TransitionUnit(UnitState nextUnitState, BufferedImage message) {
		this.message = message;
		this.nextUnitState = nextUnitState;
		initComponent();
	}

	/**
	 * Constructs a TransitionUnit and takes the successor as an argument so it
	 * can be installed from the TransitionUnit.
	 * 
	 * @param nextUnitState
	 * @param message
	 * @param nextUnit
	 */
	public TransitionUnit(UnitState nextUnitState, BufferedImage message,
			GraphicalGameUnit nextUnit) {
		this(nextUnitState, message);
		this.nextUnit = nextUnit;
		initComponent();
	}

	@Override
	public void updateComponent() {
	}

	@Override
	public void handleKeyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_ESCAPE) {
			/*
			 * return to MainMenu
			 */
			UnitNavigator.getNavigator().set(UnitState.BASE_MENU_UNIT);
		}
		if (key == KeyEvent.VK_ENTER) {
			/*
			 * proceed to next unit
			 */
			if (nextUnit != null) {
				UnitNavigator.getNavigator().addGameUnit(nextUnit,
						nextUnitState);
			}
			UnitNavigator.getNavigator().set(nextUnitState);
		}
	}

	@Override
	public void handleKeyReleased(KeyEvent e) {
	}

	@Override
	public void initComponent() {
		messagePosX = (GameConstants.FRAME_SIZE_X - message.getWidth()) / 2;
		messagePosY = (GameConstants.FRAME_SIZE_Y - message.getHeight()) / 2;
	}

	@Override
	public void drawComponent(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(0, 0, GameConstants.FRAME_SIZE_X, GameConstants.FRAME_SIZE_Y);
		g.drawImage(message, messagePosX, messagePosY, message.getWidth(),
				message.getHeight(), null);
	}

}
