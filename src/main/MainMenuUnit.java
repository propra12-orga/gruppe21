package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class MainMenuUnit extends GraphicalGameUnit {

	// not final, loading of all pictures will be handled in Imageloader class
	private Image Background = new ImageIcon(GameConstants.MENU_IMAGES_DIR
			+ "/BombermanIsland.jpg").getImage();
	private Image select = new ImageIcon(GameConstants.MENU_IMAGES_DIR
			+ "/Select.png").getImage();
	private Image singlePlayerActive = new ImageIcon(
			GameConstants.MENU_IMAGES_DIR + "/SinglePlayerActive.png")
			.getImage();
	private Image multiplayerActive = new ImageIcon(
			GameConstants.MENU_IMAGES_DIR + "/MultiPlayerActive.png")
			.getImage();
	private Image quitActive = new ImageIcon(GameConstants.MENU_IMAGES_DIR
			+ "/QuitActive.png").getImage();
	private Image ccontinueActive = new ImageIcon(GameConstants.MENU_IMAGES_DIR
			+ "/ContinueActive.png").getImage();
	private Image singlePlayerInactive = new ImageIcon(
			GameConstants.MENU_IMAGES_DIR + "/SinglePlayerInactive.png")
			.getImage();
	private Image multiplayerInactive = new ImageIcon(
			GameConstants.MENU_IMAGES_DIR + "/MultiPlayerInactive.png")
			.getImage();
	private Image quitInactive = new ImageIcon(GameConstants.MENU_IMAGES_DIR
			+ "/QuitInactive.png").getImage();
	private Image ccontinueInactive = new ImageIcon(
			GameConstants.MENU_IMAGES_DIR + "/ContinueInactive.png").getImage();
	private Image toolTip = new ImageIcon(GameConstants.MENU_IMAGES_DIR
			+ "/Help.png").getImage();

	private int buttonSpace = 10;
	private int buttonHeight = singlePlayerActive.getHeight(null);
	private int buttonWidth = singlePlayerActive.getWidth(null);
	// using GameConstants for exact Buttonplacing on screen
	private int startXPos = GameConstants.FRAME_SIZE_X / 2 - buttonWidth / 2;
	private int startYPos = GameConstants.FRAME_SIZE_Y - 300;
	// sets the space between all buttons and their positions
	private int button1YPos = startYPos;
	private int button2YPos = startYPos + 1 * (buttonHeight + buttonSpace);
	private int button3YPos = startYPos + 2 * (buttonHeight + buttonSpace);
	private int button4YPos = startYPos + 3 * (buttonHeight + buttonSpace);
	private int selectCounter;
	// point connected with the select image for optimal positioning
	private Point selectorGhost = new Point(startXPos, startYPos);
	private Image selector;

	public MainMenuUnit() {
	}

	@Override
	public void drawComponent(Graphics g) {
		g.setColor(Color.blue);
		g.fillRect(0, 0, GameConstants.FRAME_SIZE_X, GameConstants.FRAME_SIZE_Y);
		g.drawImage(Background, 0, 0, null);
		g.drawImage(toolTip, 140, GameConstants.FRAME_SIZE_Y - 60, null);
		g.drawImage(singlePlayerInactive, startXPos, button1YPos, null);
		g.drawImage(multiplayerInactive, startXPos, button2YPos, null);
		g.drawImage(quitInactive, startXPos, button3YPos, null);
		g.drawImage(ccontinueInactive, startXPos, button4YPos, null);
		g.drawImage(select, (int) selectorGhost.getX() - buttonSpace,
				(int) selectorGhost.getY(), null);
		g.drawImage(selector, (int) selectorGhost.getX() - buttonSpace,
				(int) selectorGhost.getY(), null);
		/*
		 * determines either to use active or inactive button layout based on
		 * select position
		 */
		if (selectorGhost.getY() == button1YPos)
			g.drawImage(singlePlayerActive, startXPos, button1YPos, null);

		if (selectorGhost.getY() == button2YPos)
			g.drawImage(multiplayerActive, startXPos, button2YPos, null);

		if (selectorGhost.getY() == button3YPos)
			g.drawImage(quitActive, startXPos, button3YPos, null);

		if (selectorGhost.getY() == button4YPos)
			g.drawImage(ccontinueActive, startXPos, button4YPos, null);
	}

	// MainMenu Navigation
	@Override
	public void handleKeyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		// selectCounter is used to position the select image
		if (key == KeyEvent.VK_UP || key == KeyEvent.VK_LEFT) {
			selectCounter--;
		}
		if (key == KeyEvent.VK_DOWN || key == KeyEvent.VK_RIGHT) {
			selectCounter++;
		}
		if (selectCounter > 3) {
			selectCounter = 0;
		}
		if (selectCounter < 0) {
			selectCounter = 3;
		}
		// what happens if Enter is pressed
		if (key == KeyEvent.VK_ENTER && selectCounter == 0) {
			// create new game
			LevelManagerUnit levelmanager = new LevelManagerUnit();
			UnitNavigator.getNavigator().addGameUnit(levelmanager,
					UnitState.LEVEL_MANAGER_UNIT);
			UnitNavigator.getNavigator().set(UnitState.LEVEL_MANAGER_UNIT);
		}
		if (key == KeyEvent.VK_ENTER && selectCounter == 1) {
			System.out.println("Multiplayer");
		}
		if (key == KeyEvent.VK_ENTER && selectCounter == 2) {
			// end game
			UnitNavigator.getNavigator().terminateGame();
		}
		if (key == KeyEvent.VK_ENTER && selectCounter == 3) {
			// continue game
			if (UnitNavigator.getNavigator().getUnitAt(
					UnitState.LEVEL_MANAGER_UNIT) != null) {
				UnitNavigator.getNavigator().set(UnitState.LEVEL_MANAGER_UNIT);
			}
		}
	}

	@Override
	public void handleKeyReleased(KeyEvent e) {
	}

	@Override
	public void initComponent() {
		try {
			selector = ImageIO.read(new File(GameConstants.MENU_IMAGES_DIR
					+ "/SelectorPlaceholder.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateComponent() {
		// updates selectorPosition after keyevent
		selectorGhost.setLocation(startXPos - (select.getWidth(null)),
				startYPos + selectCounter * (buttonHeight + buttonSpace));
	}
}