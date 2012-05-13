
package singleplayer;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import main.GraphicalGameUnit;
import main.UnitState;

/*
 * campaign worldmap 
 * (to be used as a temporary game unit)
 */

public class WorldMapUnit extends GraphicalGameUnit{

	private WorldMap worldMap;
	
	public WorldMapUnit(WorldMap worldMap) {
		this.worldMap = worldMap;
	}
	
	@Override
	public void drawComponent(Graphics g) {
		g.drawImage(worldMap.getBackground().getImage(), 0, 0, null);
		g.drawImage(worldMap.getPlayer().getImage(), worldMap.getPlayerCoord().x,  worldMap.getPlayerCoord().y, null);
	}

	@Override
	public void handleKeyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_LEFT) {
			if (worldMap.getSelectedLevel()-1 >= 0 ) {
				worldMap.setSelectedLevel(worldMap.getSelectedLevel()-1);
			}
		}
		if (key == KeyEvent.VK_RIGHT) {
			if (worldMap.getSelectedLevel()+1 <= worldMap.getMaxLevelAccessible()) {
				worldMap.setSelectedLevel(worldMap.getSelectedLevel()+1);
			}
		}
		if (key == KeyEvent.VK_ENTER) {
			getNavigator().set(UnitState.LEVEL_MANAGER_UNIT);
		}
	}

	@Override
	public void handleKeyReleased(KeyEvent e) {}

	@Override
	public void initComponent() {}

	@Override
	public void updateComponent() {}

}
