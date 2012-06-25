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
 * The MapMenu class let users choose what map they want to play on.
 * 
 * @author saber104
 * 
 */
public class MapMenuUnit extends GraphicalGameUnit {
	/*
	 * loading all images needed
	 */
	private Image background = new ImageIcon(GameConstants.MENU_IMAGES_DIR
			+ "/MapChooserBG.png").getImage();
	private Image map1 = new ImageIcon(GameConstants.MENU_IMAGES_DIR
			+ "/WoodwarsMenuPic.png").getImage();
	private Image select = new ImageIcon(GameConstants.MENU_IMAGES_DIR
			+ "/SelectFrame.png").getImage();
	private Image activeBack = new ImageIcon(GameConstants.MENU_IMAGES_DIR
			+ "/ActiveBack.png").getImage();
	private Image map2 = new ImageIcon(GameConstants.MENU_IMAGES_DIR
			+ "MultMapMenuPic.png").getImage();
	private Image inactiveBack = new ImageIcon(GameConstants.MENU_IMAGES_DIR
			+ "/InactiveBack.png").getImage();
	private Image currentImage = inactiveBack;
	/*
	 * class variables for object positioning
	 */
	private int startYPos = GameConstants.FRAME_SIZE_Y / 2;
	private int startXPos = GameConstants.FRAME_SIZE_X / 2;
	private int frameXPos = startXPos - (map1.getWidth(null) + 24);
	private int frameXPosition1 = frameXPos;
	private int frameXPosition2 = startXPos + 16;
	private int frameOutOfRange = -2000;
	private int buttonWidth = activeBack.getWidth(null);
	/*
	 * point for select positioning
	 */
	private Point selectorGhost = new Point(frameXPosition2, startYPos
			- (map1.getHeight(null) / 2) - 3);

	boolean local = true;

	public MapMenuUnit() {
		initComponent();
	}

	public MapMenuUnit(boolean local) {
		this.local = local;
		initComponent();
	}

	/*
	 * sets the new position of select based on current position (non-Javadoc)
	 * 
	 * @see main.GraphicalGameUnit#updateComponent()
	 */
	@Override
	public void updateComponent() {
		selectorGhost.setLocation(frameXPosition1,
				startYPos - (map1.getHeight(null) / 2) - 3);

	}

	@Override
	public void handleKeyPressed(KeyEvent e) {
		int key = e.getKeyCode();

		if (key == KeyEvent.VK_ENTER && frameXPosition1 == frameXPos) {
			GraphicalGameUnit levelmanager;
			if (local) {
				levelmanager = new LocalMultiplayerUnit("MP-Woodwars");
			} else {
				levelmanager = new MultiplayerUnit("MP-Woodwars");
			}
			UnitNavigator.getNavigator().addGameUnit(levelmanager,
					UnitState.LEVEL_MANAGER_UNIT);
			UnitNavigator.getNavigator().set(UnitState.LEVEL_MANAGER_UNIT);
		}
		if (key == KeyEvent.VK_ENTER && frameXPosition1 == frameXPosition2) {
			GraphicalGameUnit levelmanager;
			if (local) {
				levelmanager = new LocalMultiplayerUnit("multMap");
			} else {
				levelmanager = new MultiplayerUnit("multMap");
			}
			UnitNavigator.getNavigator().addGameUnit(levelmanager,
					UnitState.LEVEL_MANAGER_UNIT);
			UnitNavigator.getNavigator().set(UnitState.LEVEL_MANAGER_UNIT);
		}
		if (key == KeyEvent.VK_ENTER && frameXPosition1 == frameOutOfRange) {
			UnitNavigator.getNavigator().set(UnitState.BASE_MENU_UNIT);
		}
		if (key == KeyEvent.VK_ESCAPE) {
			UnitNavigator.getNavigator().set(UnitState.BASE_MENU_UNIT);
		}
		if (key == KeyEvent.VK_RIGHT) {
			if (frameXPosition1 != frameOutOfRange) {
				frameXPosition1 = frameXPosition2;
			}
		}
		if (key == KeyEvent.VK_LEFT) {
			if (frameXPosition1 != frameOutOfRange) {
				frameXPosition1 = frameXPos;
			}
		}
		if (key == KeyEvent.VK_UP) {
			frameXPosition1 = frameXPos;
			currentImage = inactiveBack;
		}
		if (key == KeyEvent.VK_DOWN) {
			frameXPosition1 = frameOutOfRange;
			currentImage = activeBack;
		}
	}

	@Override
	public void handleKeyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void initComponent() {
		try {
			unitFont = loadFont("font1.TTF").deriveFont(50f);
		} catch (Exception e) {
			System.err.println("ERROR LOADING FONT: font1.TTF");
			e.printStackTrace();
			unitFont = new Font("serif", Font.PLAIN, 24);
		}
	}

	@Override
	public void drawComponent(Graphics g) {

		g.drawImage(background, 0, 0, GameConstants.FRAME_SIZE_X,
				GameConstants.FRAME_SIZE_Y, null);
		g.drawImage(select, (int) selectorGhost.getX(),
				(int) selectorGhost.getY(), null);
		g.drawImage(map1, startXPos - (map1.getWidth(null) + 20), startYPos
				- (map1.getHeight(null) / 2), null);
		g.drawImage(map2, startXPos + 20, startYPos
				- (map1.getHeight(null) / 2), null);
		g.drawImage(currentImage, startXPos - buttonWidth / 2, startYPos + 250,
				null);

		g.setFont(unitFont);
		g.setColor(Color.white);

		/*
		 * center heading
		 */
		Graphics2D g2d = (Graphics2D) g;
		Rectangle2D rect = unitFont.getStringBounds("Choose Multiplayer Map:",
				g2d.getFontRenderContext());
		g2d.drawString("Choose Multiplayer Map:",
				(int) (GameConstants.FRAME_SIZE_X - rect.getWidth()) / 2,
				(int) ((GameConstants.FRAME_SIZE_Y - rect.getHeight()) / 2) - 4
						* unitFont.getSize());

	}
}
