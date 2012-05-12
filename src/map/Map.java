package map;

import java.util.ArrayList;
import java.util.Vector;

import mapobjects.Wall;

import imageloader.ImageLoader;

public class Map {
	public ImageLoader graphics = new ImageLoader();
	public Vector<MapObject> mapObjects = new Vector<MapObject>();
	
	private String mapName;
    private String mapSizeX;
    private String mapSizeY;
    private int drawLevels;
    
	MapReader mr;
	
    public Map(String mn){
    	mr = new MapReader(mn);
    	
    	//read Mapheader
    	mapName = mr.getHeader("mapname");
    	mapSizeX = mr.getHeader("sizex");
    	mapSizeY = mr.getHeader("sizey");
    	drawLevels = mr.getDrawLevels();
    	
    	mr.loadGraphics(graphics);
    	graphics.printNames();
    }
    
    public void loadNewMap(){}
	//TODO mapreader
	//TODO mapobjectarray mapreader.getMap
}
