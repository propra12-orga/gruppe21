package multiplayer;

import imageloader.GameGraphic;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import main.GameConstants;
import main.GraphicalGameUnit;
import main.UnitNavigator;
import main.UnitState;

/**
 * The OptionMenu class gives the user the opportunity to choose between
 * different options represented by buttons.
 * 
 * @author saber104
 * 
 */

public class OptionMenuUnit extends GraphicalGameUnit {

	/**
	 * Contains all options that will be displayed.
	 */
	private MenuOption[] options;
	/**
	 * Standard background image.
	 */
	private GameGraphic background;
	/**
	 * Will be centered above the button bar.
	 */
	private String heading;
	/**
	 * Counter variable determining the selected button.
	 */
	private int selectCounter;

	/**
	 * The startX variable will be initialized to a value depending on the total
	 * number of buttons and their individual width.
	 */
	private int startX;

	private int startY = (GameConstants.FRAME_SIZE_Y) / 2;

	/**
	 * Space in between buttons.
	 */
	private int buttonSpace = 40;

	/**
	 * Create a new OptionMenuUnit out of a heading and a variable amount of
	 * buttons.
	 * 
	 * @param heading
	 * @param option
	 */
	public OptionMenuUnit(String heading, MenuOption... option) {
		this.options = option;
		this.heading = heading;
		initComponent();
	}

	@Override
	public void updateComponent() {

	}

	@Override
	public void handleKeyPressed(KeyEvent e) {
		int key = e.getKeyCode();

		if (key == KeyEvent.VK_LEFT) {
			selectCounter--;
		}
		if (key == KeyEvent.VK_RIGHT) {
			selectCounter++;
		}
		if (selectCounter >= options.length) {
			selectCounter = 0;
		}
		if (selectCounter < 0) {
			selectCounter = options.length - 1;
		}
		if (key == KeyEvent.VK_ENTER) {
			options[selectCounter].initOptionUnit();
		}

		if (key == KeyEvent.VK_ESCAPE) {
			UnitNavigator.getNavigator().set(UnitState.BASE_MENU_UNIT);
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
			unitFont = loadFont("font1.TTF").deriveFont(50f);
		} catch (Exception e) {
			System.err.println("ERROR LOADING FONT: font1.TTF");
			e.printStackTrace();
			unitFont = new Font("serif", Font.PLAIN, 24);
		}
		background = new GameGraphic(GameConstants.MENU_IMAGES_DIR
				+ "/MultiplayerMenuBG.png");

		int buttonBarLength = 0;
		for (MenuOption option : options) {
			buttonBarLength = buttonBarLength
					+ option.getInactiveButtonImage().getWidth() + 40;
		}
		buttonBarLength -= 40;
		startX = (GameConstants.FRAME_SIZE_X - buttonBarLength) / 2;

	}

	public void drawComponent(Graphics g) {
		g.drawImage(background.getImage(), 0, 0, GameConstants.FRAME_SIZE_X,
				GameConstants.FRAME_SIZE_Y, null);
		/*
		 * load game font
		 */
		g.setFont(unitFont);
		g.setColor(Color.white);
		/*
		 * center heading
		 */
		Graphics2D g2d = (Graphics2D) g;
		Rectangle2D rect = unitFont.getStringBounds(heading,
				g2d.getFontRenderContext());
		g2d.drawString(heading,
				(int) (GameConstants.FRAME_SIZE_X - rect.getWidth()) / 2,
				(int) ((GameConstants.FRAME_SIZE_Y - rect.getHeight()) / 2)
						- unitFont.getSize() / 2);
		/*
		 * based on select position, determine if button is active or inactive
		 */
		int buttonX = startX;
		for (int i = 0; i < options.length; i++) {
			BufferedImage button = null;
			if (i == selectCounter) {
				button = options[i].getActiveButtonImage();
			} else {
				button = options[i].getInactiveButtonImage();
			}
			g2d.drawImage(button, buttonX, startY, null);
			buttonX = buttonX + button.getWidth() + buttonSpace;
		}
	}

	/**
	 * Using a UnitCreator allows creating a particular unit when its needed.
	 * This avoids creating all possible subsequent units when using a new
	 * OptionMenuUnit. Instead, on can pass it a UnitCreator that has the power
	 * to create a new Unit on demand.
	 * 
	 * @author tohei
	 * 
	 */
	public interface UnitCreator {
		public GraphicalGameUnit createUnit();
	}

	/**
	 * Data container for the individual options.
	 * 
	 * @author tohei
	 * 
	 */
	public static class MenuOption {
		private UnitState nextUnitState;
		private UnitCreator nextUnit;
		private GameGraphic buttonActive;
		private GameGraphic buttonInactive;

		public MenuOption(UnitState nextUnitState, UnitCreator nextUnit,
				GameGraphic buttonActive, GameGraphic buttonInactive) {
			this.nextUnitState = nextUnitState;
			this.nextUnit = nextUnit;
			this.buttonActive = buttonActive;
			this.buttonInactive = buttonInactive;
		}

		public MenuOption(UnitState nextUnitState, GameGraphic buttonActive,
				GameGraphic buttonInactive) {
			this.nextUnitState = nextUnitState;
			this.buttonActive = buttonActive;
			this.buttonInactive = buttonInactive;
		}

		public BufferedImage getActiveButtonImage() {
			return buttonActive.getImage();
		}

		public BufferedImage getInactiveButtonImage() {
			return buttonInactive.getImage();
		}

		public void initOptionUnit() {
			if (nextUnit != null) {
				GraphicalGameUnit newUnit = nextUnit.createUnit();
				UnitNavigator.getNavigator()
						.addGameUnit(newUnit, nextUnitState);
			}
			UnitNavigator.getNavigator().set(nextUnitState);
		}

	}
}
