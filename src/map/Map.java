package map;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Vector;

import mapobjects.Bomb;
import mapobjects.MapObject;
import mapobjects.Player;

import imageloader.ImageLoader;

public class Map {
	private ImageLoader graphics = new ImageLoader();
	private Vector<Vector<MapObject>> mapObjects = new Vector<Vector<MapObject>>();

	private String mapName;
	private int mapSizeX;
	private int mapSizeY;
	private int drawLevels;
	private Player mapplayer;

	private boolean mapFinished = false;

	BufferedImage collisionMap; 
	MapReader mr;

	public Map(String mn){
		mr = new MapReader(mn);

		//read Mapheader
		mapName = mr.getHeader("mapname");
		mapSizeX = Integer.parseInt(mr.getHeader("sizex"));
		mapSizeY = Integer.parseInt(mr.getHeader("sizey"));
		drawLevels = mr.getDrawLevels();

		mr.loadGraphics(graphics);
		mr.getMap(mapObjects,graphics);

		for(int i=0; i<drawLevels; i++){
			for(int j=0; j<mapObjects.get(i).size(); j++){
				mapObjects.get(i).get(j).setMap(this);
				if(mapObjects.get(i).get(j) instanceof Player){
					mapplayer = (Player) mapObjects.get(i).get(j);	
				}
			}
		}
		
		collisionMap = new BufferedImage(mapSizeX,mapSizeY,BufferedImage.TYPE_INT_ARGB);
		Graphics2D gtemp = collisionMap.createGraphics();
		gtemp.setPaint(Color.white);
	    gtemp.fillRect(mapSizeX, mapSizeY, 50, 50);
	    gtemp.dispose();
		//TODO make enemies
	}

	//TODO public void loadNewMap(){}

	public void drawMap(Graphics2D g2d){
		BufferedImage collisionMaptemp = new BufferedImage(mapSizeX,mapSizeY,BufferedImage.TYPE_INT_ARGB);
		Graphics2D b = collisionMaptemp.createGraphics();
		for(int i=0; i<drawLevels; i++){
			for(int j=0; j<mapObjects.get(i).size(); j++){
				if (mapObjects.get(i).get(j).isVisible()) {		
					mapObjects.get(i).get(j).draw(g2d,graphics,b);
				}	
			}
		}
		b.dispose();
		collisionMap = collisionMaptemp;
	}

	public void update() {
		for(int i=0; i<drawLevels; i++){
			for(int j=0; j<mapObjects.get(i).size(); j++){
				if (mapObjects.get(i).get(j).isDestroyed()) {		//destroyed mapObjects will be removed from the list
					if (mapObjects.get(i).get(j) instanceof Bomb) {
						mapplayer.removeBomb();
					}
					mapObjects.get(i).remove(j);
				} else {
					mapObjects.get(i).get(j).update(collisionMap);
				}
			}
		}
	}



	public BufferedImage getCollisionMap() {
		return collisionMap;
	}

	public void setCollisionMap(BufferedImage collisionMap) {
		this.collisionMap = collisionMap;
	}

	public ImageLoader getGraphics() {
		return graphics;
	}

	public void setGraphics(ImageLoader graphics) {
		this.graphics = graphics;
	}

	public Vector<Vector<MapObject>> getMapObjects() {
		return mapObjects;
	}

	public void setMapObjects(Vector<Vector<MapObject>> mapObjects) {
		this.mapObjects = mapObjects;
	}

	public boolean isFinished() {
		return mapFinished;
	}

	public void finishMap() {
		mapFinished = true;
	}

	public Player getMapPlayer() {
		return mapplayer;
	}

	public int getWidth() {
		return this.mapSizeX;
	}
	
	public int getHeight() {
		return this.mapSizeY;
	}
}
