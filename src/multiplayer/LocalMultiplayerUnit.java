package multiplayer;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import main.GameConstants;
import main.GraphicalGameUnit;
import main.UnitNavigator;
import main.UnitState;
import map.Map;
import mapobjects.Player;

public class LocalMultiplayerUnit extends GraphicalGameUnit {

	Player playerOne;
	Player playerTwo;

	Map multiplayerMap;
	BufferedImage mapCanvas;

	public LocalMultiplayerUnit() {
		initComponent();
	}

	@Override
	public void updateComponent() {
		if (!multiplayerMap.isFinished()) {
			multiplayerMap.update();
		} else {
			UnitNavigator.getNavigator().set(UnitState.BASE_MENU_UNIT);
			UnitNavigator.getNavigator().removeGameUnit(
					UnitState.LEVEL_MANAGER_UNIT);
		}

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
			playerOne.direction.setUp(true);
		}

		if (key == KeyEvent.VK_DOWN) {
			playerOne.direction.setDown(true);
		}

		if (key == KeyEvent.VK_LEFT) {
			playerOne.direction.setLeft(true);
		}

		if (key == KeyEvent.VK_RIGHT) {
			playerOne.direction.setRight(true);
		}

		if (key == KeyEvent.VK_PLUS) {
			playerOne.layBomb(multiplayerMap.getCollisionMap());
		}

		/*
		 * playerTwo KeyEvents
		 */
		if (key == KeyEvent.VK_W) {
			playerTwo.direction.setUp(true);
		}

		if (key == KeyEvent.VK_S) {
			playerTwo.direction.setDown(true);
		}

		if (key == KeyEvent.VK_A) {
			playerTwo.direction.setLeft(true);
		}

		if (key == KeyEvent.VK_D) {
			playerTwo.direction.setRight(true);
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
			playerOne.direction.setUp(false);
		}

		if (key == KeyEvent.VK_DOWN) {
			playerOne.direction.setDown(false);
		}

		if (key == KeyEvent.VK_LEFT) {
			playerOne.direction.setLeft(false);
		}

		if (key == KeyEvent.VK_RIGHT) {
			playerOne.direction.setRight(false);
		}

		/*
		 * playerTwo KeyEvents
		 */
		if (key == KeyEvent.VK_W) {
			playerTwo.direction.setUp(false);
		}

		if (key == KeyEvent.VK_S) {
			playerTwo.direction.setDown(false);
		}

		if (key == KeyEvent.VK_A) {
			playerTwo.direction.setLeft(false);
		}

		if (key == KeyEvent.VK_D) {
			playerTwo.direction.setRight(false);
		}
	}

	@Override
	public void initComponent() {
		multiplayerMap = new Map("multMap");
		playerOne = multiplayerMap.getPlayerByNumber(1);
		playerTwo = multiplayerMap.getPlayerByNumber(2);
	}

	@Override
	public void drawComponent(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(0, 0, GameConstants.FRAME_SIZE_X, GameConstants.FRAME_SIZE_Y);
		mapCanvas = new BufferedImage(multiplayerMap.getWidth(),
				multiplayerMap.getHeight(), BufferedImage.TYPE_INT_ARGB);
		multiplayerMap.drawMap((Graphics2D) mapCanvas.getGraphics());
		g.drawImage(mapCanvas, 10, 10, multiplayerMap.getWidth(),
				multiplayerMap.getHeight(), null);
	}

}
