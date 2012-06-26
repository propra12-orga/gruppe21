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

	private boolean helpShown = false;

	private String[] infoMsg = {
			"Use Up and Down Keys to switch the selected level",
			"Press 'Enter' to play, or 's' to save!" };
	private String[] helpMsg = { "This is the WorldMap of Bomberman Island.",
			"Here you can plan your journey and save your progress:",
			"Use Up and Down Keys to switch the selected level",
			"Press 'Enter' to play, or 'S' to save!" };

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

		/*
		 * draw info message
		 */
		Graphics2D g2d = (Graphics2D) g;
		g2d.setFont(unitFont);
		String[] infoTxt;
		if (helpShown) {
			infoTxt = infoMsg;
		} else {
			infoTxt = helpMsg;
		}
		for (int i = 0; i < infoTxt.length; i++) {
			Rectangle2D rect = unitFont.getStringBounds(infoTxt[i],
					g2d.getFontRenderContext());
			g2d.drawString(infoTxt[i],
					(int) (GameConstants.FRAME_SIZE_X - rect.getWidth()) / 2,
					(int) ((80 + rect.getHeight() * 2 * i)));
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
				GameConstants.FRAME_SIZE_Y - 60);
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
			unitFont = loadFont("font1.TTF").deriveFont(30f);
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
		updatePlayerPosition();
	}

}
