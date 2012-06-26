package unitTransitions;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import main.GameConstants;
import main.GraphicalGameUnit;
import main.UnitNavigator;
import main.UnitState;

/**
 * This subclass of GraphicalGameUnit displays either an image (message) or a
 * TransitionEffect that can be used to provide some sort of transition in
 * between two (different) GraphicalGameUnits.<br>
 * This may include:
 * <ul>
 * <LI>Loading screens</LI>
 * <LI>Win/Lose messages</LI>
 * </ul>
 * etc.<br>
 * In order to determine the next GraphicalGameUnit to move to, one has to pass
 * its UnitState (and optionally the unit itself) to the TransitionUnit's
 * constructor.<br>
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
	 * If set to true, there will be no need for pressing a key to proceed to
	 * the next unit: The TransitionUnit will automatically call
	 * initTransition() after as many frames have passed as specified in
	 * transitionPeriod.
	 */
	private boolean proceedAutomatically = false;
	/**
	 * Number of frames to wait until transition (only used if
	 * proceedAutomatically is set). Standard value is 500.
	 */
	private int transitionPeriod = 500;

	private TransitionEffect transitionEffect;

	/**
	 * Prevents the user from initializing the transition by himself
	 */
	private boolean disableKeys = false;
	/**
	 * Tells the unit to wait for notification before proceeding
	 */
	private boolean waitForNotification = false;
	/**
	 * Standard key for initializing the transition
	 */
	private int progressionKey = KeyEvent.VK_ENTER;

	/**
	 * Constructs a TransitionUnit.
	 * 
	 * @param nextUnitState
	 *            the UnitState of the succeeding GraphicalGameUnit.
	 * @param message
	 *            the message to display.
	 */
	public TransitionUnit(UnitState nextUnitState, BufferedImage message,
			boolean proceedAutomatically) {
		this.message = message;
		this.nextUnitState = nextUnitState;
		this.proceedAutomatically = proceedAutomatically;
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
			GraphicalGameUnit nextUnit, boolean proceedAutomatically) {
		this(nextUnitState, message, proceedAutomatically);
		this.nextUnit = nextUnit;
		initComponent();
	}

	/**
	 * Constructs a TransitionUnit. Takes a TransitionEffect (to be used for the
	 * transition animation) as an argument.
	 * 
	 * @param nextUnitState
	 *            the UnitState of the succeeding GraphicalGameUnit.
	 * @param transitionEffect
	 *            transition effect used to animate the transition
	 */
	public TransitionUnit(UnitState nextUnitState,
			TransitionEffect transitionEffect, boolean proceedAutomatically) {
		this.transitionEffect = transitionEffect;
		this.nextUnitState = nextUnitState;
		this.proceedAutomatically = proceedAutomatically;
		initComponent();
	}

	/**
	 * Constructs a TransitionUnit and takes the successor as an argument so it
	 * can be installed from the TransitionUnit. Takes a TransitionEffect (to be
	 * used for the transition animation) as an argument.
	 * 
	 * @param nextUnitState
	 * @param transitionEffect
	 *            transition effect used to animate the transition
	 * @param nextUnit
	 */
	public TransitionUnit(UnitState nextUnitState,
			TransitionEffect transitionEffect, GraphicalGameUnit nextUnit,
			boolean proceedAutomatically) {
		this(nextUnitState, transitionEffect, proceedAutomatically);
		this.nextUnit = nextUnit;
		initComponent();
	}

	/**
	 * Changes the duration of the transition. This is only necessary if
	 * 'proceedAutomatically' was set to true in a constructor.
	 * 
	 * @param transitionPeriod
	 */
	public void setTransitionPeriod(int transitionPeriod) {
		this.transitionPeriod = transitionPeriod;
	}

	@Override
	public void updateComponent() {
		if (proceedAutomatically) {
			transitionPeriod--;
			if (transitionPeriod <= 0) {
				if (!waitForNotification)
					initTransition();
			}
		}
		if (transitionEffect != null) {
			transitionEffect.updateEffectState();
		}
	}

	@Override
	public void handleKeyPressed(KeyEvent e) {
		if (disableKeys)
			return;
		int key = e.getKeyCode();

		if (key == progressionKey) {
			/*
			 * proceed to next unit
			 */
			initTransition();
		}
	}

	/**
	 * Proceed to nextUnit.
	 */
	private void initTransition() {
		if (nextUnit != null) {
			UnitNavigator.getNavigator().addGameUnit(nextUnit, nextUnitState);
		}
		UnitNavigator.getNavigator().set(nextUnitState);
	}

	@Override
	public void handleKeyReleased(KeyEvent e) {
	}

	@Override
	public void initComponent() {
		if (transitionEffect == null) {
			messagePosX = (GameConstants.FRAME_SIZE_X - message.getWidth()) / 2;
			messagePosY = (GameConstants.FRAME_SIZE_Y - message.getHeight()) / 2;
		}
	}

	@Override
	public void drawComponent(Graphics g) {
		if (transitionEffect != null) {
			transitionEffect.drawEffect((Graphics2D) g);
		} else {
			g.setColor(Color.black);
			g.fillRect(0, 0, GameConstants.FRAME_SIZE_X,
					GameConstants.FRAME_SIZE_Y);
			g.drawImage(message, messagePosX, messagePosY, null);
		}
	}

	/**
	 * Disable KeyEvent control.
	 */
	public void disableKeyEventControlledProgression() {
		disableKeys = true;
	}

	/**
	 * If set to true, the thread will wait for 'authorizeProgression() to be
	 * called in order to proceed to the next unit.
	 * 
	 * @param waitForNotification
	 */
	public void setWaitForNotification(boolean waitForNotification) {
		this.waitForNotification = waitForNotification;
	}

	/**
	 * Notify TransitionUnit; enables transition process. Important for laoding
	 * screens.
	 */
	public void authorizeProgression() {
		disableKeys = false;
		waitForNotification = false;
	}

	/**
	 * Sets KeyEvent to listen for to initialize transition
	 * 
	 * @param keyEvent
	 */
	public void setProgressionKey(int keyEvent) {
		this.progressionKey = keyEvent;
	}

}
