package multiplayer;

import java.awt.Graphics;
import java.awt.event.KeyEvent;

import main.GraphicalGameUnit;
import main.UnitNavigator;
import main.UnitState;
import map.Map;
import mapobjects.Player;

public class LocalMultiplayerUnit extends GraphicalGameUnit {

	Player playerOne;
	Player playerTwo;

	Map multiplayerMap;

	@Override
	public void updateComponent() {
		// TODO Auto-generated method stub

	}

	@Override
	public void handleKeyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		/*
		 * Pause Game
		 */
		if (key == KeyEvent.VK_ESCAPE) {
			UnitNavigator.getNavigator().set(UnitState.BASE_MENU_UNIT);
		}
		/*
		 * playerOne KeyEvents
		 */
		if (key == KeyEvent.VK_UP) {
			playerOne.direction.UP.set(true);
		}

		if (key == KeyEvent.VK_DOWN) {
			playerOne.direction.DOWN.set(true);
		}

		if (key == KeyEvent.VK_LEFT) {
			playerOne.direction.LEFT.set(true);
		}

		if (key == KeyEvent.VK_RIGHT) {
			playerOne.direction.RIGHT.set(true);
		}

		if (key == KeyEvent.VK_INSERT) {
			playerOne.layBomb(multiplayerMap.getCollisionMap());
		}

		/*
		 * playerTwo KeyEvents
		 */
		if (key == KeyEvent.VK_W) {
			playerTwo.direction.UP.set(true);
		}

		if (key == KeyEvent.VK_S) {
			playerTwo.direction.DOWN.set(true);
		}

		if (key == KeyEvent.VK_A) {
			playerTwo.direction.LEFT.set(true);
		}

		if (key == KeyEvent.VK_D) {
			playerTwo.direction.RIGHT.set(true);
		}

		if (key == KeyEvent.VK_SPACE) {
			playerTwo.layBomb(multiplayerMap.getCollisionMap());
		}
	}

	@Override
	public void handleKeyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		/*
		 * playerOne KeyEvents
		 */
		if (key == KeyEvent.VK_UP) {
			playerOne.direction.UP.set(false);
		}

		if (key == KeyEvent.VK_DOWN) {
			playerOne.direction.DOWN.set(false);
		}

		if (key == KeyEvent.VK_LEFT) {
			playerOne.direction.LEFT.set(false);
		}

		if (key == KeyEvent.VK_RIGHT) {
			playerOne.direction.RIGHT.set(false);
		}

		/*
		 * playerTwo KeyEvents
		 */
		if (key == KeyEvent.VK_W) {
			playerOne.direction.UP.set(false);
		}

		if (key == KeyEvent.VK_S) {
			playerOne.direction.DOWN.set(false);
		}

		if (key == KeyEvent.VK_A) {
			playerOne.direction.LEFT.set(false);
		}

		if (key == KeyEvent.VK_D) {
			playerOne.direction.RIGHT.set(false);
		}
	}

	@Override
	public void initComponent() {
		multiplayerMap = new Map("testmap");

	}

	@Override
	public void drawComponent(Graphics g) {
		// TODO Auto-generated method stub

	}

}
