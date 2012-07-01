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

	private String[] infoMsg = {
			"Use Up and Down Keys to switch the selected level",
			"Press 'Enter' to play, or 's' to save!" };
	private String[] helpMsg = { "This is the WorldMap of Bomberman Island.",
			"Here you can plan your journey and save your progress:",
			"Use Up and Down Keys to switch the selected level",
			"Press 'Enter' to play, or 'S' to save!" };
	private int textMaxWidth;
	int[] lineWidth;

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
		if (helpShown) {
			drawTextInfo(g2d, infoMsg);
		} else {
			drawTextInfo(g2d, helpMsg);
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

		g.setColor(Color.black);
		g.fillRect(0, 0, GameConstants.FRAME_SIZE_X, GameConstants.FRAME_SIZE_Y);
		g.drawImage(mapCanvas, mapCanvasX, mapCanvasY, null);
	}

	private void drawTextInfo(Graphics2D g2d, String[] text) {
		g2d.setColor(Color.white);
		for (int i = 0; i < text.length; i++) {
			g2d.drawString(text[i],
					(GameConstants.FRAME_SIZE_X - lineWidth[i]) / 2, 50 + i * 2
							* unitFont.getSize());
		}

	}

	public void setHelpShown(boolean helpShown) {
		this.helpShown = helpShown;
		lineWidth = new int[infoMsg.length];
		for (int i = 0; i < infoMsg.length; i++) {
			Rectangle2D rect = unitFont.getStringBounds(infoMsg[i], mapCanvas
					.createGraphics().getFontRenderContext());
			if (rect.getWidth() > textMaxWidth) {
				textMaxWidth = (int) rect.getWidth();
			}
			lineWidth[i] = (int) rect.getWidth();
		}
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
	}

	@Override
	public void updateComponent() {

	}

}
