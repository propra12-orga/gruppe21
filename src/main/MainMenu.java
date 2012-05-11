package main;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;

public class MainMenu extends GraphicalGameUnit {

	// using GameConstants for exact Buttonplacing on screen
	private int startXPos = GameConstants.FRAME_SIZE_X / 2 - 100;
	private int startYPos = (GameConstants.FRAME_SIZE_Y / 3) * 2 - 50;

	// sets the space between all buttons and their positions
	private int ButtonSpace = 10;
	private int ButtonHeight = 50;
	private int button1YPos = startYPos;
	private int button2YPos = button1YPos + ButtonHeight + ButtonSpace;
	private int button3YPos = button2YPos + ButtonHeight + ButtonSpace;
	private int button4YPos = button3YPos + ButtonHeight + ButtonSpace;

	// not final, loading of all pictures will be handled in Imageloader class
	private Image Background = new ImageIcon(GameConstants.MENU_IMAGES_DIR
			+ "/MMBGPlaceholder.png").getImage();
	private Image select = new ImageIcon(GameConstants.MENU_IMAGES_DIR
			+ "/SelectorPlaceholder.png").getImage();
	private Image singlePlayerActive = new ImageIcon(
			GameConstants.MENU_IMAGES_DIR
					+ "/ActiveSingleplayerPlaceholder.png").getImage();
	private Image multiplayerActive = new ImageIcon(
			GameConstants.MENU_IMAGES_DIR + "/ActiveMultiplayerPlaceholder.png")
			.getImage();
	private Image quitActive = new ImageIcon(GameConstants.MENU_IMAGES_DIR
			+ "/ActiveQuitgamePlaceholder.png").getImage();
	private Image ccontinueActive = new ImageIcon(GameConstants.MENU_IMAGES_DIR
			+ "/ActiveContinueGamePlaceholder.png").getImage();

	private Image singlePlayerInactive = new ImageIcon(
			GameConstants.MENU_IMAGES_DIR
					+ "/InactiveSingleplayerPlaceholder.png").getImage();
	private Image multiplayerInactive = new ImageIcon(
			GameConstants.MENU_IMAGES_DIR
					+ "/InactiveMultiplayerPlaceholder.png").getImage();
	private Image quitInactive = new ImageIcon(GameConstants.MENU_IMAGES_DIR
			+ "/InactiveQuitgamePlaceholder.png").getImage();
	private Image ccontinueInactive = new ImageIcon(
			GameConstants.MENU_IMAGES_DIR
					+ "/InactiveContinueGamePlaceholder.png").getImage();

	@Override
	public void drawComponent(Graphics g) {
		g.drawImage(Background, 0, 0, null);
		g.drawImage(singlePlayerInactive, startXPos, button1YPos, null);
		g.drawImage(multiplayerInactive, startXPos, button2YPos, null);
		g.drawImage(quitInactive, startXPos, button3YPos, null);
		g.drawImage(ccontinueInactive, startXPos, button4YPos, null);
		g.drawImage(select, startXPos - (select.getWidth(null) + ButtonSpace),
				button1YPos, null);

	}

	public int getX() {
		return 0;
	}

	// MainMenu Navigation
	@Override
	public void handleKeyPressed(KeyEvent e) {
		int key = e.getKeyCode();

		if (key == KeyEvent.VK_UP || key == KeyEvent.VK_LEFT) {

		}
		if (key == KeyEvent.VK_DOWN || key == KeyEvent.VK_RIGHT) {

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

	}
}
