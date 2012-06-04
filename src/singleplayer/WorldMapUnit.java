package singleplayer;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import main.GameConstants;
import main.GraphicalGameUnit;
import main.UnitNavigator;
import main.UnitState;
import mapobjects.Player;

/*
 * campaign worldmap 
 * (to be used as a temporary game unit)
 */

public class WorldMapUnit extends GraphicalGameUnit {

	private WorldMap worldMap;
	private BufferedImage mapCanvas;

	public WorldMapUnit(WorldMap worldMap) {
		this.worldMap = worldMap;
	}

	@Override
	public void drawComponent(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(0, 0, GameConstants.FRAME_SIZE_X, GameConstants.FRAME_SIZE_Y);
		mapCanvas = new BufferedImage(worldMap.getMap().getWidth(), worldMap
				.getMap().getHeight(), BufferedImage.TYPE_INT_ARGB);
		worldMap.getMap().drawMap((Graphics2D) mapCanvas.getGraphics());
		g.drawImage(mapCanvas, 0, 0, GameConstants.FRAME_SIZE_X,
				GameConstants.FRAME_SIZE_Y, null);
	}

	@Override
	public void handleKeyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_LEFT) {
			if (worldMap.getSelectedLevel() + 1 <= worldMap
					.getMaxLevelAccessible()) {
				worldMap.setSelectedLevel(worldMap.getSelectedLevel() + 1);
				updatePlayerPosition();
			}
		}
		if (key == KeyEvent.VK_RIGHT) {
			if (worldMap.getSelectedLevel() - 1 >= 0) {
				worldMap.setSelectedLevel(worldMap.getSelectedLevel() - 1);
				updatePlayerPosition();

			}
		}
		if (key == KeyEvent.VK_ENTER) {
			UnitNavigator.getNavigator().set(UnitState.LEVEL_MANAGER_UNIT);
		}
	}

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
	}

	@Override
	public void updateComponent() {
		Player player = worldMap.getMap().getMapPlayer();
		player.direction.UP.set(false);
		player.direction.DOWN.set(false);
		player.direction.LEFT.set(false);
		player.direction.RIGHT.set(false);
		worldMap.getMap().update();
	}

}
