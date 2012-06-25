package multiplayer;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;

import javax.swing.ImageIcon;

import main.GameConstants;
import main.GraphicalGameUnit;
import main.UnitNavigator;
import main.UnitState;

/**
 * The OptionMenu class gives the user the oportunity to choose to play local
 * multiplayer or network-based multiplayer. It is extanded from the
 * GraphicalGameUnit class.
 * 
 * @author saber104
 * 
 */

public class OptionMenuUnit extends GraphicalGameUnit {
	/*
	 * loading all images needed
	 */
	private Image background = new ImageIcon(GameConstants.MENU_IMAGES_DIR
			+ "/MultiplayerMenuBG.png").getImage();
	private Image select = new ImageIcon(GameConstants.MENU_IMAGES_DIR
			+ "/Select.png").getImage();
	private Image activeLocal = new ImageIcon(GameConstants.MENU_IMAGES_DIR
			+ "/ActiveLocal.png").getImage();
	private Image inactiveLocal = new ImageIcon(GameConstants.MENU_IMAGES_DIR
			+ "/InactiveLocal.png").getImage();
	private Image activeNetwork = new ImageIcon(GameConstants.MENU_IMAGES_DIR
			+ "/ActiveNetwork.png").getImage();
	private Image inactiveNetwork = new ImageIcon(GameConstants.MENU_IMAGES_DIR
			+ "/InactiveNetwork.png").getImage();
	private Image activeBack = new ImageIcon(GameConstants.MENU_IMAGES_DIR
			+ "/ActiveBack.png").getImage();
	private Image inactiveBack = new ImageIcon(GameConstants.MENU_IMAGES_DIR
			+ "/InactiveBack.png").getImage();
	/*
	 * class variables for button positioning
	 */
	private int buttonSpace = 40;
	private int buttonWidth = activeLocal.getWidth(null);
	private int startYPos = GameConstants.FRAME_SIZE_Y / 2;
	private int startXPos = GameConstants.FRAME_SIZE_X / 2
			- (buttonWidth + buttonWidth / 2 + buttonSpace);
	private int button1XPos = startXPos;
	private int button2XPos = startXPos + 1 * (buttonWidth + buttonSpace);
	private int button3XPos = startXPos + 2 * (buttonWidth + buttonSpace);
	/*
	 * point for select positioning
	 */
	private Point selectorGhost = new Point(
			button1XPos - select.getWidth(null), startYPos);
	private int selectCounter;

	public OptionMenuUnit() {
		initComponent();
	}

	/*
	 * sets the new position of select based on selectCounter value
	 * (non-Javadoc)
	 * 
	 * @see main.GraphicalGameUnit#updateComponent()
	 */
	@Override
	public void updateComponent() {
		selectorGhost.setLocation(startXPos - select.getWidth(null)
				+ (buttonWidth + buttonSpace) * selectCounter, startYPos);
	}

	/*
	 * Left, RIght for switching, enter to accept map & escape to leave menu
	 * (non-Javadoc)
	 * 
	 * @see main.GraphicalGameUnit#handleKeyPressed(java.awt.event.KeyEvent)
	 */
	@Override
	public void handleKeyPressed(KeyEvent e) {
		int key = e.getKeyCode();

		if (key == KeyEvent.VK_LEFT) {
			selectCounter--;
		}
		if (key == KeyEvent.VK_RIGHT) {
			selectCounter++;
		}
		if (selectCounter > 2) {
			selectCounter = 0;
		}
		if (selectCounter < 0) {
			selectCounter = 2;
		}
		if (key == KeyEvent.VK_ENTER && selectCounter == 0) {
			MapMenuUnit mapMenu = new MapMenuUnit();
			UnitNavigator.getNavigator().addGameUnit(mapMenu,
					UnitState.TEMPORARY_UNIT);
			UnitNavigator.getNavigator().set(UnitState.TEMPORARY_UNIT);
		}
		if (key == KeyEvent.VK_ENTER && selectCounter == 1) {
			MapMenuUnit mapMenu = new MapMenuUnit(false);
			UnitNavigator.getNavigator().addGameUnit(mapMenu,
					UnitState.TEMPORARY_UNIT);
			UnitNavigator.getNavigator().set(UnitState.TEMPORARY_UNIT);
		}
		if (key == KeyEvent.VK_ENTER && selectCounter == 2) {
			UnitNavigator.getNavigator().set(UnitState.BASE_MENU_UNIT);
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
	}

	@Override
	/*
	 * all opjects are drawn here (non-Javadoc)
	 * 
	 * @see main.GraphicalGameUnit#drawComponent(java.awt.Graphics)
	 */
	public void drawComponent(Graphics g) {
		g.drawImage(background, 0, 0, GameConstants.FRAME_SIZE_X,
				GameConstants.FRAME_SIZE_Y, null);
		g.drawImage(inactiveLocal, button1XPos, startYPos, null);
		g.drawImage(inactiveNetwork, button2XPos, startYPos, null);
		g.drawImage(inactiveBack, button3XPos, startYPos, null);
		g.drawImage(select, (int) selectorGhost.getX(),
				(int) selectorGhost.getY(), null);

		/*
		 * load game font
		 */
		g.setFont(unitFont);
		g.setColor(Color.white);
		/*
		 * center heading
		 */
		Graphics2D g2d = (Graphics2D) g;
		Rectangle2D rect = unitFont.getStringBounds("Choose Multiplayer Mode:",
				g2d.getFontRenderContext());
		g2d.drawString("Choose Multiplayer Mode:",
				(int) (GameConstants.FRAME_SIZE_X - rect.getWidth()) / 2,
				(int) ((GameConstants.FRAME_SIZE_Y - rect.getHeight()) / 2)
						- unitFont.getSize() / 2);
		/*
		 * based on select position, determine if button is activ e or inactive
		 */
		if (selectorGhost.getX() == button1XPos - select.getWidth(null))
			g.drawImage(activeLocal, button1XPos, startYPos, null);

		if (selectorGhost.getX() == button2XPos - select.getWidth(null))
			g.drawImage(activeNetwork, button2XPos, startYPos, null);

		if (selectorGhost.getX() == button3XPos - select.getWidth(null))
			g.drawImage(activeBack, button3XPos, startYPos, null);

	}
}
