package singleplayer;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import main.GameConstants;
import main.GraphicalGameUnit;
import main.UnitNavigator;
import main.UnitState;

public class TransitionUnit extends GraphicalGameUnit {

	private BufferedImage message;
	private UnitState nextUnitState;
	private GraphicalGameUnit nextUnit;

	public TransitionUnit(UnitState nextUnitState, BufferedImage message) {
		this.message = message;
		this.nextUnitState = nextUnitState;
	}

	public TransitionUnit(UnitState nextUnitState, BufferedImage message,
			GraphicalGameUnit nextUnit) {
		this(nextUnitState, message);
		this.nextUnit = nextUnit;

	}

	@Override
	public void updateComponent() {
		// TODO Auto-generated method stub

	}

	@Override
	public void handleKeyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_ESCAPE) {
			UnitNavigator.getNavigator().set(UnitState.BASE_MENU_UNIT);
		}
		if (key == KeyEvent.VK_ENTER) {
			if (nextUnit != null) {
				UnitNavigator.getNavigator().addGameUnit(nextUnit,
						nextUnitState);
			}
			System.out.println(nextUnitState);
			UnitNavigator.getNavigator().set(nextUnitState);
		}
	}

	@Override
	public void handleKeyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void initComponent() {
		// TODO Auto-generated method stub

	}

	@Override
	public void drawComponent(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(0, 0, GameConstants.FRAME_SIZE_X, GameConstants.FRAME_SIZE_Y);
		g.drawImage(message, 0, 0, message.getWidth(), message.getHeight(),
				null);
	}

}
