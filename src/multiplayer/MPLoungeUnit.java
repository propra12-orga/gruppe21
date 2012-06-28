package multiplayer;

import imageloader.GameGraphic;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;

import main.GameConstants;
import main.GraphicalGameUnit;

/**
 * When starting a new multiplayer game, all players meet in a MPLoungeUnit
 * before the game can be started by the host. Every player has to set his
 * status to 'ready' in order to start the game.
 * 
 * @author tohei
 * 
 */
public class MPLoungeUnit extends GraphicalGameUnit {

	private GameGraphic background;
	private GameGraphic[] playerStatusImages;
	private static final String[] STATUS_MESSAGES = { "Unavailable",
			"Connected", "Ready" };
	private int[] playerStatus;
	private String heading = "Multiplayer Lounge";
	private int elementSpace = 40;
	private int elementStartX = GameConstants.FRAME_SIZE_X / 4 - 100;
	private int elementStartY = GameConstants.FRAME_SIZE_Y / 3;

	private int elementImageStartX = GameConstants.FRAME_SIZE_X / 4 + 10;
	private int elementImageStartY = GameConstants.FRAME_SIZE_Y / 3 - 30;

	private int mapX = GameConstants.FRAME_SIZE_X / 2;

	private int buttonStartX;
	private int buttonStartY = 3 * GameConstants.FRAME_SIZE_Y / 4;

	private static final int PLAYER_UNAVAILABLE = 0;
	private static final int PLAYER_CONNECTED = 1;
	private static final int PLAYER_READY = 2;

	GameGraphic selectedMap;

	GameGraphic backActive;
	GameGraphic backInactive;

	GameGraphic readyActive;
	GameGraphic readyInactive;

	GameGraphic startActive;
	GameGraphic startInactive;

	private int selectionCounter = 0;

	private int playerNumber;
	boolean asHost;

	public MPLoungeUnit(int numOfPlayers, GameGraphic selectedMap,
			boolean asHost, int playerNumber) {
		this.asHost = asHost;
		this.selectedMap = selectedMap;
		this.playerNumber = playerNumber - 1;

		playerStatus = new int[numOfPlayers];
		playerStatus[0] = PLAYER_CONNECTED;
		playerStatusImages = new GameGraphic[3];
		initComponent();
	}

	@Override
	public void updateComponent() {
		// TODO Auto-generated method stub

	}

	@Override
	public void handleKeyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_LEFT) {
			if (!(selectionCounter == 0)) {
				selectionCounter--;
			}
		}
		if (key == KeyEvent.VK_RIGHT) {
			if (!(selectionCounter == 2)) {
				selectionCounter++;
			}
		}
		if (key == KeyEvent.VK_ENTER && selectionCounter == 0) {
			if (playerStatus[playerNumber] == PLAYER_READY) {
				playerStatus[playerNumber] = PLAYER_CONNECTED;
			} else {
				playerStatus[playerNumber] = PLAYER_READY;
			}
		}

	}

	@Override
	public void handleKeyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void initComponent() {
		/*
		 * load font
		 */
		try {
			unitFont = loadFont("font1.TTF").deriveFont(25f);
		} catch (Exception e) {
			System.err.println("ERROR LOADING FONT: font1.TTF");
			e.printStackTrace();
			unitFont = new Font("serif", Font.PLAIN, 24);
		}
		/*
		 * load images
		 */
		background = new GameGraphic(GameConstants.MENU_IMAGES_DIR
				+ "MapChooserBG.png");
		playerStatusImages[0] = new GameGraphic(GameConstants.MENU_IMAGES_DIR
				+ "unavailable.png");
		playerStatusImages[1] = new GameGraphic(GameConstants.MENU_IMAGES_DIR
				+ "connected.png");
		playerStatusImages[2] = new GameGraphic(GameConstants.MENU_IMAGES_DIR
				+ "ready.png");
		backActive = new GameGraphic(GameConstants.MENU_IMAGES_DIR
				+ "ActiveBack.png");
		backInactive = new GameGraphic(GameConstants.MENU_IMAGES_DIR
				+ "InactiveBack.png");
		readyActive = new GameGraphic(GameConstants.MENU_IMAGES_DIR
				+ "ReadyActive.png");
		readyInactive = new GameGraphic(GameConstants.MENU_IMAGES_DIR
				+ "ReadyInactive.png");
		if (asHost) {
			startActive = new GameGraphic(GameConstants.MENU_IMAGES_DIR
					+ "StartGameActive.png");
			startInactive = new GameGraphic(GameConstants.MENU_IMAGES_DIR
					+ "StartGameInactive.png");
			buttonStartX = (GameConstants.FRAME_SIZE_X
					- readyActive.getImage().getWidth() - elementSpace
					- startActive.getImage().getWidth() - elementSpace - backActive
					.getImage().getWidth()) / 2;
		} else {
			buttonStartX = (GameConstants.FRAME_SIZE_X
					- readyActive.getImage().getWidth() - elementSpace - backActive
					.getImage().getWidth()) / 2;
		}
	}

	@Override
	public void drawComponent(Graphics g) {
		g.drawImage(background.getImage(), 0, 0, GameConstants.FRAME_SIZE_X,
				GameConstants.FRAME_SIZE_Y, null);
		/*
		 * load game font
		 */
		g.setFont(unitFont);
		g.setColor(Color.white);
		/*
		 * draw heading
		 */
		Graphics2D g2d = (Graphics2D) g;
		Rectangle2D rect = unitFont.getStringBounds(heading,
				g2d.getFontRenderContext());
		g2d.drawString(heading,
				(int) (GameConstants.FRAME_SIZE_X - rect.getWidth()) / 2, 150);

		/*
		 * draw player connection info
		 */
		for (int i = 0; i < playerStatus.length; i++) {
			g2d.drawString("Player " + (i + 1), elementStartX, elementStartY
					+ 2 * i * elementSpace);
			g2d.drawImage(playerStatusImages[playerStatus[i]].getImage(),
					elementImageStartX, elementImageStartY + 2 * i
							* elementSpace, null);
			g2d.drawString(STATUS_MESSAGES[playerStatus[i]],
					elementImageStartX + 50, elementStartY + 2 * i
							* elementSpace);
		}
		g2d.drawImage(selectedMap.getImage(), mapX, elementStartY, null);

		/*
		 * draw ready button
		 */
		int tmpX = buttonStartX;
		GameGraphic tmpButton = readyInactive;
		if (selectionCounter == 0) {
			tmpButton = readyActive;
		}
		g2d.drawImage(tmpButton.getImage(), tmpX, buttonStartY, null);

		/*
		 * if host, draw start game button
		 */
		if (asHost) {
			tmpX = tmpX + tmpButton.getImage().getWidth() + elementSpace;
			tmpButton = startInactive;
			if (selectionCounter == 1) {
				tmpButton = startActive;
			}
			g2d.drawImage(tmpButton.getImage(), tmpX, buttonStartY, null);
		}
		/*
		 * draw back button
		 */
		tmpX = tmpX + tmpButton.getImage().getWidth() + elementSpace;
		tmpButton = backInactive;
		if (selectionCounter == 2) {
			tmpButton = backActive;
		}
		g2d.drawImage(tmpButton.getImage(), tmpX, buttonStartY, null);

	}
}
