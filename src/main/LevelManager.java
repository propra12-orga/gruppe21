
package main;

import java.awt.Graphics;
import java.awt.event.KeyEvent;

import map.Map;

import singleplayer.Campaign;

/*
 * loads single-player campaign and updates the current map object
 */
public class LevelManager extends GraphicalGameUnit {

	private Campaign campaign;
	private Map currentMap;
	//private Player player;
	
	@Override
	public void drawComponent(Graphics g) {		
		//map.draw(g);
	}

	@Override
	public void handleKeyPressed(KeyEvent e) {
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
	}

	@Override
	public void updateComponent() {
		//updateEnemies;
		//updatePlayer;
		//updateBombs;		
	}

}
