package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.swing.ImageIcon;

import multiplayer.OptionMenuUnit;
import singleplayer.Campaign;
import singleplayer.LevelManagerUnit;

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

	private String campaign = "campaign1.txt";

	@Override
	public void drawComponent(Graphics g) {

		g.drawImage(Background, 0, 0, null);
		g.drawImage(toolTip, 140, GameConstants.FRAME_SIZE_Y - 60, null);
		g.drawImage(singlePlayerInactive, startXPos, button1YPos, null);
		g.drawImage(multiplayerInactive, startXPos, button2YPos, null);
		g.drawImage(quitInactive, startXPos, button3YPos, null);
		g.drawImage(ccontinueInactive, startXPos, button4YPos, null);
		g.drawImage(select, (int) selectorGhost.getX() - buttonSpace,
				(int) selectorGhost.getY(), null);
		/*
		 * determines whether to use active or inactive button layout based on
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
			startNewSingeplayerCampaign(campaign);
		}
		if (key == KeyEvent.VK_ENTER && selectCounter == 1) {
			// create new game
			OptionMenuUnit optionMenu = new OptionMenuUnit();
			UnitNavigator.getNavigator().addGameUnit(optionMenu,
					UnitState.TEMPORARY_UNIT);
			UnitNavigator.getNavigator().set(UnitState.TEMPORARY_UNIT);
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

	}

	@Override
	public void updateComponent() {
		// updates selectorPosition after keyevent
		selectorGhost.setLocation(startXPos - (select.getWidth(null)),
				startYPos + selectCounter * (buttonHeight + buttonSpace));
	}

	/**
	 * Constructs BufferedImage containing an introduction to the given campaign
	 * and creates a new TransitionUnit to display that message.
	 * 
	 * @param campaignFile
	 *            file containing campaign introduction
	 */
	private void startNewSingeplayerCampaign(String campaignFile) {
		/*
		 * Create new LevelManager
		 */
		LevelManagerUnit levelmanager = new LevelManagerUnit(campaign);
		UnitNavigator.getNavigator().addGameUnit(levelmanager,
				UnitState.LEVEL_MANAGER_UNIT);
		/*
		 * If campaign starts with an introduction, start by creating a
		 * TransitionUnit
		 */
		List<String> message = null;
		try {
			message = Campaign.readMapIntro(campaignFile);
		} catch (FileNotFoundException e1) {
			System.err.println("CAMPAIGN NOT FOUND: " + campaignFile);
			e1.printStackTrace();
		}
		if (message.size() != 0) {
			BufferedImage transitionImage = new BufferedImage(
					GameConstants.FRAME_SIZE_X, GameConstants.FRAME_SIZE_Y,
					BufferedImage.TYPE_INT_ARGB);
			Image tmp = new ImageIcon(GameConstants.MENU_IMAGES_DIR
					+ "MultiplayerMenuBG.png").getImage();
			Graphics2D g2d = transitionImage.createGraphics();
			g2d.drawImage(tmp, 0, 0, transitionImage.getWidth(),
					transitionImage.getHeight(), null);
			/*
			 * load game font
			 */
			Font font;
			try {
				font = loadFont("font1.TTF").deriveFont(30f);
			} catch (Exception e) {
				System.err.println("ERROR LOADING FONT: font1.TTF");
				e.printStackTrace();
				font = new Font("serif", Font.PLAIN, 24);
			}
			g2d.setFont(font);
			g2d.setColor(Color.white);
			/*
			 * center text message
			 */
			final int lineDistance = 30;
			for (int i = 0; i < message.size(); i++) {
				g2d.drawString(message.get(i),
						(int) (GameConstants.FRAME_SIZE_X / 6),
						(int) (GameConstants.FRAME_SIZE_Y / 2) + (i - 1)
								* lineDistance);
			}
			g2d.drawString((" PRESS ENTER"),
					(int) (GameConstants.FRAME_SIZE_X / 6),
					(int) (GameConstants.FRAME_SIZE_Y / 2) + message.size()
							* lineDistance);

			TransitionUnit transUnit = new TransitionUnit(
					UnitState.LEVEL_MANAGER_UNIT, transitionImage);
			UnitNavigator.getNavigator().addGameUnit(transUnit,
					UnitState.TEMPORARY_UNIT);
			UnitNavigator.getNavigator().set(UnitState.TEMPORARY_UNIT);
		} else {
			/*
			 * do not use TransitionUnit, proceed to LevelManagerUnit
			 */
			UnitNavigator.getNavigator().set(UnitState.LEVEL_MANAGER_UNIT);
		}
	}

	// to be replaced by FontManager
	/**
	 * Loads font from file (assuming it is located in the 'fonts' directory).
	 * 
	 * @param filename
	 *            font name
	 * @return loaded font
	 * @throws FontFormatException
	 * @throws IOException
	 */
	private Font loadFont(String filename) throws FontFormatException,
			IOException {
		InputStream is = new FileInputStream(new File(GameConstants.FONTS_DIR
				+ filename));
		return Font.createFont(Font.TRUETYPE_FONT, is);
	}
}