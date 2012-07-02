package singleplayer;

import imageloader.GameGraphic;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import main.GameConstants;
import main.GraphicalGameUnit;
import main.UnitNavigator;
import main.UnitState;
import unitTransitions.TransitionUnit;

/**
 * This subclass of GraphicalGameUnit is used to depict and interact with a
 * WorldMap object. This unit is to be used as a TEMPORARY_UNIT.
 * 
 * @author tohei
 * @see singleplayer.WorldMap
 */
public class WorldMapUnit extends GraphicalGameUnit {

	/**
	 * The corresponding WorldMap object manages all related data.
	 */
	private WorldMap worldMap;

	/**
	 * Create a new WorldMapUnit from a WorldMap object.
	 * 
	 * @param worldMap
	 */

	private BufferedImage mapCanvas;
	private int mapCanvasX;
	private int mapCanvasY;

	public WorldMapUnit(WorldMap worldMap) {
		this.worldMap = worldMap;
		initComponent();
	}

	private boolean helpShown = false;
	private BufferedImage helpScreen;
	private String[] helpMsg = {
			"Here you can plan your journey and save your progress:",
			"Use Up and Down Keys to switch the selected level",
			"Press 'Enter' to play, or 'S' to save!",
			"You may hide or display this message by pressing 'F1'" };
	private int textMaxWidth;
	private int[] lineWidth;
	private String heading;

	@Override
	public void drawComponent(Graphics g) {
		Graphics2D g2d = mapCanvas.createGraphics();
		g2d.setFont(unitFont);
		g2d.setColor(Color.black);
		g2d.fillRect(0, 0, mapCanvas.getWidth(), mapCanvas.getHeight());
		g2d.setColor(Color.white);
		g2d.drawImage(worldMap.getBackgroundImg().getImage(), 0, 0, null);
		GameGraphic[] tmp = worldMap.getProgressIndicator();
		Point[] coords = worldMap.getLevelCoords();
		for (int i = 0; i < worldMap.getMaxLevelAccessible(); i++) {
			g2d.drawImage(tmp[i].getImage(), coords[i + 1].x, coords[i + 1].y,
					null);
		}
		g2d.drawImage(worldMap.getPlayerImg().getImage(),
				worldMap.getPlayerCoord().x
						- worldMap.getPlayerImg().getImage().getWidth() / 2,
				worldMap.getPlayerCoord().y
						- worldMap.getPlayerImg().getImage().getHeight(), null);

		/*
		 * draw info message
		 */
		if (!helpShown) {
			helpShown = true;
			initHelpscreen();
		}
		/*
		 * draw level info
		 */
		String s = worldMap.getSelectedCoordLabel();
		if (worldMap.getMaxLevelAccessible() > worldMap.getSelectedLevel())
			s = s + " - Completed";
		Rectangle2D rect = unitFont.getStringBounds(s,
				g2d.getFontRenderContext());
		g2d.drawString(s,
				(int) (GameConstants.FRAME_SIZE_X - rect.getWidth()) / 2,
				GameConstants.FRAME_SIZE_Y - 40);

		/*
		 * draw heading
		 */
		rect = unitFont.getStringBounds(heading, g2d.getFontRenderContext());
		g2d.drawString(heading,
				(int) (GameConstants.FRAME_SIZE_X - rect.getWidth()) / 2, 30);

		g.setColor(Color.black);
		g.fillRect(0, 0, GameConstants.FRAME_SIZE_X, GameConstants.FRAME_SIZE_Y);
		g.drawImage(mapCanvas, mapCanvasX, mapCanvasY, null);
	}

	public void setHelpShown(boolean helpShown) {
		this.helpShown = helpShown;
	}

	@Override
	public void handleKeyPressed(KeyEvent e) {
		/*
		 * User input will change the selectedLevel variable of the worldMap.
		 */
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_UP) {
			if (worldMap.getSelectedLevel() + 1 <= worldMap
					.getMaxLevelAccessible()) {
				worldMap.setSelectedLevel(worldMap.getSelectedLevel() + 1);
			}
		}
		if (key == KeyEvent.VK_DOWN) {
			if (worldMap.getSelectedLevel() - 1 >= 0) {
				worldMap.setSelectedLevel(worldMap.getSelectedLevel() - 1);
			}
		}
		if (key == KeyEvent.VK_S) {
			UnitNavigator.getNavigator().addGameUnit(
					new SavegameManagerUnit(this, false),
					UnitState.TEMPORARY_UNIT);
			UnitNavigator.getNavigator().set(UnitState.TEMPORARY_UNIT);
			helpShown = true;
		}

		/*
		 * Return to the LevelManagerUnit and start a new map.
		 */
		if (key == KeyEvent.VK_ENTER) {
			UnitNavigator.getNavigator().set(UnitState.LEVEL_MANAGER_UNIT);
			LevelManagerUnit tmp = (LevelManagerUnit) UnitNavigator
					.getNavigator().getActiveUnit();
			tmp.activateUnit();
			helpShown = true;
		}

		if (key == KeyEvent.VK_ESCAPE) {
			UnitNavigator.getNavigator().set(UnitState.BASE_MENU_UNIT);
		}
		if (key == KeyEvent.VK_F1) {
			initHelpscreen();
			helpShown = true;
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
			unitFont = loadFont("font1.TTF").deriveFont(28f);
		} catch (Exception e) {
			System.err.println("ERROR LOADING FONT: font1.TTF");
			e.printStackTrace();
			unitFont = new Font("serif", Font.PLAIN, 24);
		}
		mapCanvas = new BufferedImage(worldMap.getBackgroundImg().getImage()
				.getWidth(),
				worldMap.getBackgroundImg().getImage().getHeight(),
				BufferedImage.TYPE_INT_ARGB);
		mapCanvasX = (GameConstants.FRAME_SIZE_X - mapCanvas.getWidth()) / 2;
		mapCanvasY = (GameConstants.FRAME_SIZE_Y - mapCanvas.getHeight()) / 2;

		heading = worldMap.getName() + " - Worldmap";

		mapCanvas.createGraphics().setFont(unitFont);
		lineWidth = new int[helpMsg.length];
		for (int i = 0; i < helpMsg.length; i++) {
			Rectangle2D rect = unitFont.getStringBounds(helpMsg[i], mapCanvas
					.createGraphics().getFontRenderContext());
			if (rect.getWidth() > textMaxWidth) {
				textMaxWidth = (int) rect.getWidth();
			}
			lineWidth[i] = (int) rect.getWidth();
		}
		loadHelpscreen();
	}

	@Override
	public void updateComponent() {

	}

	/**
	 * Loads helpscreen image.
	 */
	private void loadHelpscreen() {
		helpScreen = new BufferedImage(textMaxWidth + 40, 40 + 2
				* unitFont.getSize() * helpMsg.length,
				BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = helpScreen.createGraphics();
		Color color = new Color(0, 0, 0, 200);
		g2d.setColor(color);
		g2d.fillRect(0, 0, helpScreen.getWidth(), helpScreen.getHeight());

		g2d.setColor(Color.white);
		g2d.setFont(unitFont);

		for (int i = 0; i < helpMsg.length; i++) {
			g2d.drawString(helpMsg[i],
					(helpScreen.getWidth() - lineWidth[i]) / 2, 50 + i * 2
							* unitFont.getSize());
		}

	}

	/**
	 * Loads helpscreen image and initializes a TransitionUnit.
	 */
	private void initHelpscreen() {
		BufferedImage helpscreenImage = createGameSceenshot();
		helpscreenImage
				.createGraphics()
				.drawImage(
						helpScreen,
						(GameConstants.FRAME_SIZE_X - helpScreen.getWidth()) / 2,
						(GameConstants.FRAME_SIZE_Y - helpScreen.getHeight()) / 2,
						null);

		TransitionUnit trans = new TransitionUnit(UnitState.TEMPORARY_UNIT,
				helpscreenImage, this, false);
		trans.setProgressionKey(KeyEvent.VK_F1);
		UnitNavigator.getNavigator().addGameUnit(trans,
				UnitState.TEMPORARY_UNIT);
		UnitNavigator.getNavigator().set(UnitState.TEMPORARY_UNIT);
	}

	/**
	 * Creates BufferedImage out of the mapCanvas and a black background.
	 * 
	 * @return BufferedImage depicting the current game screen.
	 */
	private BufferedImage createGameSceenshot() {
		BufferedImage screenshot = new BufferedImage(
				GameConstants.FRAME_SIZE_X, GameConstants.FRAME_SIZE_Y,
				BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = screenshot.createGraphics();
		g2d.setColor(Color.black);
		g2d.drawImage(mapCanvas, mapCanvasX, mapCanvasY, null);
		return screenshot;
	}
}
