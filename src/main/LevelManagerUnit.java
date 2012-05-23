
package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;

import map.Map;
import mapobjects.Player;
import singleplayer.Campaign;
import singleplayer.WorldMapUnit;

/*
 * loads single-player campaign and updates the current map object
 */
public class LevelManagerUnit extends GraphicalGameUnit {

	private Campaign campaign;
	private Map currentMap;
	private WorldMapUnit worldMapUnit;
	private Player player;

	// OffsetVariablen für die zu Zeichnende Map
	private int mapOffsetX=0;
	private int mapOffsetY=0;
	//Booleans wenn map kleiner als bereich bei initalisierung auf true
	private boolean mapXSmaller = false;
	private boolean mapYSmaller = false;
	BufferedImage mapCanvas;


	public LevelManagerUnit() {
		initComponent();
	}

	@Override
	public void drawComponent(Graphics g) {	
		g.setColor(Color.black);
		g.fillRect(0, 0, GameConstants.FRAME_SIZE_X, GameConstants.FRAME_SIZE_Y);
		mapCanvas = new BufferedImage(currentMap.getWidth(), currentMap.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics gMap = mapCanvas.getGraphics();
		currentMap.drawMap((Graphics2D) gMap);
		g.drawImage(mapCanvas, this.mapOffsetX, this.mapOffsetY, currentMap.getWidth(), currentMap.getHeight(), null);
	}

	@Override
	public void handleKeyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_ESCAPE) {
			getNavigator().set(UnitState.BASE_MENU_UNIT);
		}
		if (key == KeyEvent.VK_UP) {
			player.direction.UP.set(true);
		}

		if (key == KeyEvent.VK_DOWN) {
			player.direction.DOWN.set(true);
		}

		if (key == KeyEvent.VK_LEFT) {
			player.direction.LEFT.set(true);
		}

		if (key == KeyEvent.VK_RIGHT) {
			player.direction.RIGHT.set(true);
		}

		if (key == KeyEvent.VK_SPACE) {
			player.layBomb();
		}
	}

	@Override
	public void handleKeyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_UP) {
			player.direction.UP.set(false);
		}

		if (key == KeyEvent.VK_DOWN) {
			player.direction.DOWN.set(false);
		}

		if (key == KeyEvent.VK_LEFT) {
			player.direction.LEFT.set(false);
		}

		if (key == KeyEvent.VK_RIGHT) {
			player.direction.RIGHT.set(false);
		}

	}

	@Override
	public void initComponent() {
		
		try {
			campaign = Campaign.readCampaignFromFile("campaign1.txt");
			changeCurrentMap();
			worldMapUnit = new WorldMapUnit(campaign.getWorldMap());
			
			player.direction.UP.set(false);
			player.direction.DOWN.set(false);
			player.direction.LEFT.set(false);
			player.direction.RIGHT.set(false);
		} catch (FileNotFoundException e) {
			System.err.println("Error loading Campaign: Campaign not found!");
			e.printStackTrace();
			terminateLevelManager();
		} catch (IOException e) {
			System.err.println("Error loading Campaign: IOException!");
			e.printStackTrace();
			terminateLevelManager();
		}
	}

	private void terminateLevelManager() {
		getNavigator().set(UnitState.BASE_MENU_UNIT);
		getNavigator().removeGameUnit(UnitState.LEVEL_MANAGER_UNIT);		
	}

	@Override
	public void updateComponent() {
		if (!currentMap.isFinished()) {
			currentMap.update();
			updateOffset();
		} else {
			if (campaign.updateCounters()) {
				changeCurrentMap();			
			} else {
				if (campaign.isFinished()) {
					// campaign completed, show credits
					terminateLevelManager();
				} else {
					// level completed, show world map
					getNavigator().addGameUnit(worldMapUnit, UnitState.TEMPORARY_UNIT);
					getNavigator().set(UnitState.TEMPORARY_UNIT);
				}				
			}
		}	
	}

	private void changeCurrentMap() {
		currentMap = campaign.getCurrentMap();
		player = currentMap.getMapPlayer();
		initOffset();
	}

	/*
	 * initializes offset
	 */

	public void initOffset() {
		if(currentMap.getWidth()<GameConstants.FRAME_SIZE_X){
			mapOffsetX = (GameConstants.FRAME_SIZE_X-currentMap.getWidth())/2;
			mapXSmaller = true;
		}else{
			if(player.getPosX()-GameConstants.TILE_SIZE>= GameConstants.FRAME_SIZE_X){
				if(player.getPosX()>=currentMap.getWidth()-GameConstants.FRAME_SIZE_X){
					mapOffsetX = -(currentMap.getWidth()-GameConstants.FRAME_SIZE_X);
				}else{
					mapOffsetX = -(player.getPosX()-GameConstants.FRAME_SIZE_X/2);
				}
			}
		}

		if(currentMap.getHeight()<GameConstants.FRAME_SIZE_Y){
			mapOffsetY = (GameConstants.FRAME_SIZE_Y-currentMap.getHeight())/2;  //wenn Map kleiner offset auf halben leerbereich setzen
			mapYSmaller = true;
		}else{
			if(player.getPosY()- GameConstants.TILE_SIZE >= GameConstants.FRAME_SIZE_Y){
				if(player.getPosY()>=currentMap.getWidth()-GameConstants.FRAME_SIZE_Y){
					mapOffsetY = -(currentMap.getWidth()-GameConstants.FRAME_SIZE_Y);
				}else{
					mapOffsetY = -(player.getPosY()-GameConstants.FRAME_SIZE_Y/2);
				}
			}
		}
	}

	/*
	 * updates the current Offset
	 */
	public void updateOffset(){
		if(!mapXSmaller){
			if(player.getPosX()>GameConstants.FRAME_SIZE_X/2-25){
				if(player.getPosX()-GameConstants.FRAME_SIZE_X/2+25<currentMap.getWidth()-GameConstants.FRAME_SIZE_X){
					this.mapOffsetX = -(player.getPosX()-GameConstants.FRAME_SIZE_X/2)-GameConstants.TILE_SIZE/2;
				}else{
					this.mapOffsetX= -(currentMap.getWidth()-GameConstants.FRAME_SIZE_X);
				}
			}else{
				this.mapOffsetX=0;
			}
		}

		if(!mapYSmaller){
			if(player.getPosY()>GameConstants.FRAME_SIZE_Y/2-25){
				if(player.getPosY()-GameConstants.FRAME_SIZE_Y/2+25<currentMap.getHeight()-GameConstants.FRAME_SIZE_Y){
					this.mapOffsetY = -(player.getPosY()-GameConstants.FRAME_SIZE_Y/2)-GameConstants.TILE_SIZE/2;
				}else{
					this.mapOffsetY= -(currentMap.getHeight()-GameConstants.FRAME_SIZE_Y);
				}
			}else{
				this.mapOffsetY=0;
			}
		}
	}

}
