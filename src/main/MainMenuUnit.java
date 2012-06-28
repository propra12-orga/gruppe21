package main;

import imageloader.GameGraphic;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

import multiplayer.OptionMenuUnit;
import singleplayer.LevelManagerUnit;
import singleplayer.SavegameManagerUnit;
import unitTransitions.TransitionUnit;

/**
 * This class represents the main menu. It is used as the main hub, from where
 * you can choose what to do. Including singleplayer, multiplayer or exit the
 * game.
 * 
 * @author Saber104
 * 
 */

public class MainMenuUnit extends GraphicalGameUnit {

	// not final, loading of all pictures will be handled in Imageloader class

	private GameGraphic background;
	private GameGraphic select;
	private GameGraphic singlePlayerActive;
	private GameGraphic singlePlayerInactive;
	private GameGraphic multiplayerActive;
	private GameGraphic multiplayerInactive;
	private GameGraphic quitActive;
	private GameGraphic quitInactive;
	private GameGraphic continueActive;
	private GameGraphic continueInactive;
	private GameGraphic loadGameActive;
	private GameGraphic loadGameInactive;
	private GameGraphic toolTip;

	private int buttonSpace = 10;
	private int buttonHeight;
	private int buttonWidth;
	// using GameConstants for exact Buttonplacing on screen
	private int startXPos;
	private int startYPos;
	// sets the space between all buttons and their positions
	private int button1YPos;
	private int button2YPos;
	private int button3YPos;
	private int button4YPos;
	private int button5YPos;
	private int selectCounter;
	// point connected with the select image for optimal positioning
	private Point selectorGhost;

	private String campaign = "campaign1";

	public MainMenuUnit() {
		initComponent();
	}

	@Override
	public void drawComponent(Graphics g) {

		g.drawImage(background.getImage(), 0, 0, null);
		g.drawImage(toolTip.getImage(), 140, GameConstants.FRAME_SIZE_Y - 60,
				null);
		g.drawImage(singlePlayerInactive.getImage(), startXPos, button1YPos,
				null);
		g.drawImage(multiplayerInactive.getImage(), startXPos, button2YPos,
				null);
		g.drawImage(loadGameInactive.getImage(), startXPos, button3YPos, null);
		g.drawImage(quitInactive.getImage(), startXPos, button4YPos, null);
		g.drawImage(continueInactive.getImage(), startXPos, button5YPos, null);
		g.drawImage(select.getImage(),
				(int) selectorGhost.getX() - buttonSpace,
				(int) selectorGhost.getY(), null);
		/*
		 * determines whether to use active or inactive button layout based on
		 * select position
		 */
		if (selectorGhost.getY() == button1YPos)
			g.drawImage(singlePlayerActive.getImage(), startXPos, button1YPos,
					null);

		if (selectorGhost.getY() == button2YPos)
			g.drawImage(multiplayerActive.getImage(), startXPos, button2YPos,
					null);

		if (selectorGhost.getY() == button3YPos)
			g.drawImage(loadGameActive.getImage(), startXPos, button3YPos, null);

		if (selectorGhost.getY() == button4YPos)
			g.drawImage(quitActive.getImage(), startXPos, button4YPos, null);

		if (selectorGhost.getY() == button5YPos)
			g.drawImage(continueActive.getImage(), startXPos, button5YPos, null);
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
		if (selectCounter > 4) {
			selectCounter = 0;
		}
		if (selectCounter < 0) {
			selectCounter = 4;
		}
		// what happens if Enter is pressed
		if (key == KeyEvent.VK_ENTER && selectCounter == 0) {
			final TransitionUnit toolTipUnit = new TransitionUnit(
					UnitState.LEVEL_MANAGER_UNIT,
					createLoadingScreen("loading ..."), true);
			toolTipUnit.disableKeyEventControlledProgression();
			toolTipUnit.setWaitForNotification(true);
			toolTipUnit.setTransitionPeriod(100);
			new Thread(new Runnable() {
				@Override
				public void run() {
					LevelManagerUnit levelmanager = new LevelManagerUnit(
							campaign);
					UnitNavigator.getNavigator().addGameUnit(levelmanager,
							UnitState.LEVEL_MANAGER_UNIT);
					toolTipUnit.authorizeProgression();
				}
			}).start();
			UnitNavigator.getNavigator().addGameUnit(toolTipUnit,
					UnitState.TEMPORARY_UNIT);
			UnitNavigator.getNavigator().set(UnitState.TEMPORARY_UNIT);
		}
		if (key == KeyEvent.VK_ENTER && selectCounter == 1) {
			UnitNavigator.getNavigator().addGameUnit(
					OptionMenuUnit.loadLocalNetworkOptionMenu(),
					UnitState.TEMPORARY_UNIT);
			UnitNavigator.getNavigator().set(UnitState.TEMPORARY_UNIT);

		}
		if (key == KeyEvent.VK_ENTER && selectCounter == 2) {
			UnitNavigator.getNavigator().addGameUnit(
					new SavegameManagerUnit(true), UnitState.TEMPORARY_UNIT);
			UnitNavigator.getNavigator().set(UnitState.TEMPORARY_UNIT);
		}
		if (key == KeyEvent.VK_ENTER && selectCounter == 3) {
			// end game
			UnitNavigator.getNavigator().terminateGame();
		}
		if (key == KeyEvent.VK_ENTER && selectCounter == 4) {
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
		/*
		 * load font
		 */
		try {
			unitFont = loadFont("font1.TTF").deriveFont(30f);
		} catch (Exception e) {
			System.err.println("ERROR LOADING FONT: font1.TTF");
			e.printStackTrace();
			unitFont = new Font("serif", Font.PLAIN, 24);
		}

		background = new GameGraphic(GameConstants.MENU_IMAGES_DIR
				+ "/BombermanIsland.jpg");
		select = new GameGraphic(GameConstants.MENU_IMAGES_DIR + "/Select.png");
		singlePlayerActive = new GameGraphic(GameConstants.MENU_IMAGES_DIR
				+ "/SinglePlayerActive.png");
		singlePlayerInactive = new GameGraphic(GameConstants.MENU_IMAGES_DIR
				+ "/SinglePlayerInactive.png");
		multiplayerActive = new GameGraphic(GameConstants.MENU_IMAGES_DIR
				+ "/MultiPlayerActive.png");
		multiplayerInactive = new GameGraphic(GameConstants.MENU_IMAGES_DIR
				+ "/MultiPlayerInactive.png");
		quitActive = new GameGraphic(GameConstants.MENU_IMAGES_DIR
				+ "/QuitActive.png");
		quitInactive = new GameGraphic(GameConstants.MENU_IMAGES_DIR
				+ "/QuitInactive.png");
		continueActive = new GameGraphic(GameConstants.MENU_IMAGES_DIR
				+ "/ContinueActive.png");
		continueInactive = new GameGraphic(GameConstants.MENU_IMAGES_DIR
				+ "/ContinueInactive.png");
		loadGameActive = new GameGraphic(GameConstants.MENU_IMAGES_DIR
				+ "/LoadGameActive.png");
		loadGameInactive = new GameGraphic(GameConstants.MENU_IMAGES_DIR
				+ "/LoadGameInactive.png");
		toolTip = new GameGraphic(GameConstants.MENU_IMAGES_DIR + "/Help.png");

		buttonHeight = singlePlayerActive.getImage().getHeight();
		buttonWidth = singlePlayerActive.getImage().getWidth();

		startXPos = GameConstants.FRAME_SIZE_X / 2 - buttonWidth / 2;
		startYPos = GameConstants.FRAME_SIZE_Y - 350;

		button1YPos = startYPos;
		button2YPos = startYPos + 1 * (buttonHeight + buttonSpace);
		button3YPos = startYPos + 2 * (buttonHeight + buttonSpace);
		button4YPos = startYPos + 3 * (buttonHeight + buttonSpace);
		button5YPos = startYPos + 4 * (buttonHeight + buttonSpace);

		selectorGhost = new Point(startXPos, startYPos);
	}

	@Override
	public void updateComponent() {
		// updates selectorPosition after keyevent
		selectorGhost.setLocation(startXPos - (select.getImage().getWidth()),
				startYPos + selectCounter * (buttonHeight + buttonSpace));
	}

	private BufferedImage createLoadingScreen(String msg) {
		Image tmp = new ImageIcon(GameConstants.MENU_IMAGES_DIR
				+ "MultiplayerMenuBG.png").getImage();
		BufferedImage transitionImage = new BufferedImage(
				GameConstants.FRAME_SIZE_X, GameConstants.FRAME_SIZE_Y,
				BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = transitionImage.createGraphics();
		g2d.drawImage(tmp, 0, 0, transitionImage.getWidth(),
				transitionImage.getHeight(), null);
		g2d.setFont(unitFont);
		Rectangle2D rect = unitFont.getStringBounds(msg,
				g2d.getFontRenderContext());
		g2d.drawString(msg,
				(int) (GameConstants.FRAME_SIZE_X - rect.getWidth()) / 2,
				(int) (GameConstants.FRAME_SIZE_Y / 2 - rect.getHeight()));
		return transitionImage;
	}
}