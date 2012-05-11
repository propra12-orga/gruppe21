package main;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;

public class MainMenu extends GraphicalGameUnit {

	// using GameConstants for exact Buttonplacing on screen
	private int startXPos = GameConstants.FRAME_SIZE_X / 2 - 100;
	private int startYPos = (GameConstants.FRAME_SIZE_Y / 3) * 2;

	// sets the space between all buttons and thier positions
	private int ButtonSpace = 20;
	private int ButtonHeight = 50;
	private int selectorMoveStep = ButtonSpace + ButtonHeight;
	private int button1YPos = startYPos + selectorMoveStep;
	private int button2YPos = startYPos + 2 * selectorMoveStep;
	private int button3YPos = startYPos + 3 * selectorMoveStep;
	private int button4YPos = startYPos + 4 * selectorMoveStep;

	// not final, loading of all pictures will be handled in Imageloader class
	private Image Background = new ImageIcon("/MMBGPlaceholder.png").getImage();
	private Image select = new ImageIcon("/SelectorPlaceholder.png").getImage();
	private Image singlePlayerActive = new ImageIcon(
			"/ActiveSingleplayerPlaceholder.png").getImage();
	private Image multiplayerActive = new ImageIcon(
			"/ActiveMultiplayerPlaceholder.png").getImage();
	private Image quitActive = new ImageIcon("/ActiveQuitgamePlaceholder.png")
			.getImage();
	private Image ccontinueActive = new ImageIcon(
			"/ActiveContinueGamePlaceholder.png").getImage();

	private Image singlePlayerInactive = new ImageIcon(
			"/InactiveSingleplayerPlaceholder.png").getImage();
	private Image multiplayerInactive = new ImageIcon(
			"/InactiveMultiplayerPlaceholder.png").getImage();
	private Image quitInactive = new ImageIcon(
			"/InactiveQuitgamePlaceholder.png").getImage();
	private Image ccontinueInactive = new ImageIcon(
			"/InactiveContinueGamePlaceholder.png").getImage();

	@Override
	public void drawComponent(Graphics g) {
		g.drawImage(Background, 0, 0, null);
		g.drawImage(singlePlayerInactive, startXPos, button1YPos, null);
		g.drawImage(multiplayerInactive, startXPos, button2YPos + ButtonSpace
				+ ButtonHeight, null);
		g.drawImage(quitInactive, startXPos, button3YPos + ButtonSpace
				+ ButtonHeight, null);
		g.drawImage(ccontinueInactive, startXPos, button4YPos + ButtonSpace
				+ ButtonHeight, null);
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

	public void test() {

	}

}
