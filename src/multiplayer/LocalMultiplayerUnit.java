package multiplayer;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import main.GameConstants;
import main.GraphicalGameUnit;
import main.TransitionUnit;
import main.UnitNavigator;
import main.UnitState;
import map.Map;
import mapobjects.Player;

/**
 * Subclass of GraphicalGameUnit for simple "Two-Players-One-Keyboard"
 * multiplayer games.
 * 
 * @author tohei
 * 
 */
public class LocalMultiplayerUnit extends GraphicalGameUnit {

	private Player playerOne;
	private Player playerTwo;

	private Map multiplayerMap;
	private BufferedImage mapCanvas;

	/**
	 * Some win messages one can randomly choose from.
	 */
	private String[] winMessages = { " is bomb-happy!",
			" achieves a blasting victory!", " leaves nothing but a crater!" };

	public LocalMultiplayerUnit() {
		initComponent();
	}

	@Override
	public void updateComponent() {
		if (!multiplayerMap.isFinished()) {
			multiplayerMap.update();
		} else {
			/*
			 * A player died: Generate the appropriate image ...
			 */
			BufferedImage transitionMsg = createDrawMessage();
			if (playerOne.isAlive()) {
				transitionMsg = createWinMessage("Player One");
			} else if (playerTwo.isAlive()) {
				transitionMsg = createWinMessage("Player Two");
			}
			/*
			 * ... and pass it to a TransitionUnit
			 */
			TransitionUnit trans = new TransitionUnit(UnitState.BASE_MENU_UNIT,
					transitionMsg);
			UnitNavigator.getNavigator().addGameUnit(trans,
					UnitState.TEMPORARY_UNIT);
			UnitNavigator.getNavigator().set(UnitState.TEMPORARY_UNIT);
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
		/*
		 * load map and players
		 */
		multiplayerMap = new Map("MP-Woodwars");
		playerOne = multiplayerMap.getPlayerByNumber(1);
		playerTwo = multiplayerMap.getPlayerByNumber(2);
		/*
		 * create mapcanvas (used to depict and center the map)
		 */
		mapCanvas = new BufferedImage(multiplayerMap.getWidth(),
				multiplayerMap.getHeight(), BufferedImage.TYPE_INT_ARGB);
	}

	@Override
	public void drawComponent(Graphics g) {
		/*
		 * Black background color
		 */
		g.setColor(Color.black);
		g.fillRect(0, 0, GameConstants.FRAME_SIZE_X, GameConstants.FRAME_SIZE_Y);
		/*
		 * draw map onto the mapcanvas
		 */
		multiplayerMap.drawMap((Graphics2D) mapCanvas.getGraphics());
		/*
		 * center mapcanvas on panel by using the Graphics parameter
		 */
		g.drawImage(mapCanvas,
				(GameConstants.FRAME_SIZE_X - mapCanvas.getWidth()) / 2,
				(GameConstants.FRAME_SIZE_Y - mapCanvas.getHeight()) / 2,
				multiplayerMap.getWidth(), multiplayerMap.getHeight(), null);
	}

	/**
	 * Generates a message (i.e. a BufferedImage that displays a string)
	 * containing the name of the player that succeeded.
	 * 
	 * @param playerName
	 *            the player's name
	 * @return BufferedImage win message
	 */
	private BufferedImage createWinMessage(String playerName) {
		BufferedImage msg = new BufferedImage(GameConstants.FRAME_SIZE_X,
				GameConstants.FRAME_SIZE_Y, BufferedImage.TYPE_INT_ARGB);
		Graphics g = msg.getGraphics();
		g.setColor(Color.black);
		g.drawRect(0, 0, msg.getWidth(), msg.getHeight());
		g.setColor(Color.red);
		String output = playerName
				+ winMessages[(int) (Math.random() * winMessages.length)];
		g.drawString(output, GameConstants.FRAME_SIZE_X / 4,
				GameConstants.FRAME_SIZE_Y / 2);
		return msg;
	}

	/**
	 * Generates a BufferedImage indicating that the match has ended up in a
	 * tie.
	 * 
	 * @return BufferedImage draw message
	 */
	private BufferedImage createDrawMessage() {
		BufferedImage msg = new BufferedImage(GameConstants.FRAME_SIZE_X,
				GameConstants.FRAME_SIZE_Y, BufferedImage.TYPE_INT_ARGB);
		Graphics g = msg.getGraphics();
		g.setColor(Color.black);
		g.drawRect(0, 0, msg.getWidth(), msg.getHeight());
		g.setColor(Color.red);
		g.drawString("Unbelieveable! It's a draw!",
				GameConstants.FRAME_SIZE_X / 4, GameConstants.FRAME_SIZE_Y / 2);
		return msg;
	}
}
