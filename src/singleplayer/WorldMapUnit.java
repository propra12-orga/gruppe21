package singleplayer;

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
import mapobjects.Player;

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
	 * Used to center and draw the worldMap's map object.
	 */
	private BufferedImage mapCanvas;

	/**
	 * Create a new WorldMapUnit from a WorldMap object.
	 * 
	 * @param worldMap
	 */
	public WorldMapUnit(WorldMap worldMap) {
		this.worldMap = worldMap;
		initComponent();
	}

	private String[] infoMsg = {
			"Use Up and Down Keys to switch the selected level",
			"Press Enter to play!" };

	@Override
	public void drawComponent(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(0, 0, GameConstants.FRAME_SIZE_X, GameConstants.FRAME_SIZE_Y);
		mapCanvas = new BufferedImage(worldMap.getMap().getWidth(), worldMap
				.getMap().getHeight(), BufferedImage.TYPE_INT_ARGB);
		worldMap.getMap().drawMap((Graphics2D) mapCanvas.getGraphics());
		/*
		 * Center and draw mapCanvas
		 */
		g.drawImage(mapCanvas,
				(GameConstants.FRAME_SIZE_X - mapCanvas.getWidth()) / 2,
				(GameConstants.FRAME_SIZE_Y - mapCanvas.getHeight()) / 2,
				mapCanvas.getWidth(), mapCanvas.getHeight(), null);

		Graphics2D g2d = (Graphics2D) g;
		g2d.setFont(unitFont);
		for (int i = 0; i < infoMsg.length; i++) {
			Rectangle2D rect = unitFont.getStringBounds(infoMsg[i],
					g2d.getFontRenderContext());
			g2d.drawString(infoMsg[i],
					(int) (GameConstants.FRAME_SIZE_X - rect.getWidth()) / 2,
					(int) (((GameConstants.FRAME_SIZE_Y - rect.getHeight()
							* infoMsg.length) / 4) + rect.getHeight() * 2 * i));
		}

		for (int i = 0; i < infoMsg.length; i++) {

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
				updatePlayerPosition();
			}
		}
		if (key == KeyEvent.VK_DOWN) {
			if (worldMap.getSelectedLevel() - 1 >= 0) {
				worldMap.setSelectedLevel(worldMap.getSelectedLevel() - 1);
				updatePlayerPosition();

			}
		}
		// if (key == KeyEvent.VK_S) {
		// GraphicalGameUnit tmp = UnitNavigator.getNavigator().getUnitAt(
		// UnitState.LEVEL_MANAGER_UNIT);
		// if (tmp != null && tmp instanceof LevelManagerUnit) {
		// LevelManagerUnit levelManager = (LevelManagerUnit) tmp;
		// try {
		// levelManager.createSavegame().storeToFile("SAVEGAME.txt");
		// } catch (FileNotFoundException e1) {
		// e1.printStackTrace();
		// }
		// }
		//
		// }
		/*
		 * Return to the LevelManagerUnit and start a new map.
		 */
		if (key == KeyEvent.VK_ENTER) {
			UnitNavigator.getNavigator().set(UnitState.LEVEL_MANAGER_UNIT);
		}
	}

	/**
	 * Set player position according to the worldMap's selectedLevel variable.
	 */
	private void updatePlayerPosition() {
		Point playerCoord = worldMap.getPlayerCoord();
		worldMap.getMap().getMapPlayer()
				.setPosX(GameConstants.TILE_SIZE * playerCoord.x);
		worldMap.getMap().getMapPlayer()
				.setPosY(GameConstants.TILE_SIZE * playerCoord.y);
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
			unitFont = loadFont("font1.TTF").deriveFont(25f);
		} catch (Exception e) {
			System.err.println("ERROR LOADING FONT: font1.TTF");
			e.printStackTrace();
			unitFont = new Font("serif", Font.PLAIN, 24);
		}
	}

	@Override
	public void updateComponent() {
		Player player = worldMap.getMap().getMapPlayer();
		player.direction.setUp(false);
		player.direction.setDown(false);
		player.direction.setLeft(false);
		player.direction.setRight(false);
		worldMap.getMap().update();
	}

}
