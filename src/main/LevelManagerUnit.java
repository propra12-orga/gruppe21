
package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import map.Map;
import singleplayer.Campaign;
import singleplayer.WorldMapUnit;

/*
 * loads single-player campaign and updates the current map object
 */
public class LevelManagerUnit extends GraphicalGameUnit {

//	private Campaign campaign;
	private Map currentMap;
//	private WorldMapUnit worldMapUnit;
	//private Player player;
	
	public LevelManagerUnit() {
		initComponent();
	}
	
	@Override
	public void drawComponent(Graphics g) {	
		g.setColor(Color.white);
		g.fillRect(0, 0, GameConstants.FRAME_SIZE_X, GameConstants.FRAME_SIZE_Y);
		currentMap.drawMap((Graphics2D) g);
	}

	@Override
	public void handleKeyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_ESCAPE) {
			getNavigator().set(UnitState.BASE_MENU_UNIT);
		}
		// TODO Auto-generated method stub		
	}

	@Override
	public void handleKeyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initComponent() {
		//player = new Player();
		//campaign = Campaign.readCampaignFromFile("campaign1.txt");
		//currentMap = campaign.getCurrentMap();	
		//worldMapUnit = new WorldMapUnit(campaign.getWorldMap());
		currentMap = new Map("testmap");
	}

	@Override
	public void updateComponent() {
		//updateEnemies;
		//updatePlayer;
		//updateBombs;		
	}

}
