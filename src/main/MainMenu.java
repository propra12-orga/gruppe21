package main;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;

public class MainMenu extends GraphicalGameUnit {
	// using GameConstants for exact Buttonplacing on screen
	private int ButtonX = GameConstants.FRAME_SIZE_X / 2 - 100;
	private int ButtonY = (GameConstants.FRAME_SIZE_Y / 3) * 2;
	// sets the space between all buttons
	private int ButtonSpace = 20;
	/*
	 * picturetitles as strings for loading purposes not final, loading of all
	 * pictures will be handled in Imageloader class
	 */
	private String menuBGImage = "/MMBGPlaceholder.png";
	private String singleplayerActive = "/ActiveSingleplayerPlaceholder.png";
	private String multiplayerActive = "/ActiveMultiplayerPlaceholder.png";
	private String quitActive = "/ActiveQuitgamePlaceholder.png";
	private String ccontinueActive = "/ActiveContinueGamePlaceholder.png";
	private String singleplayerInactive = "/InactiveSingleplayerPlaceholder.png";
	private String multiplayerInactive = "/InactiveMultiplayerPlaceholder.png";
	private String quitInactive = "/InactiveQuitgamePlaceholder.png";
	private String ccontinueInactive = "/InactiveContinueGamePlaceholder.png";
	private String selector = "/SelectorPlaceholder.png";
	private ImageIcon iiOne = new ImageIcon(menuBGImage);
	private Image Background = iiOne.getImage();

	// loads all images needed

	@Override
	public void drawComponent(Graphics g) {
		g.drawImage(Background, 0, 0, null);
	}

	@Override
	public void handleKeyPressed(KeyEvent e) {

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
