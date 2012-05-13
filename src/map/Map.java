package map;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Vector;

import mapobjects.MapObject;
import mapobjects.Wall;

import imageloader.ImageLoader;

public class Map {
	public ImageLoader graphics = new ImageLoader();
	public Vector<Vector<MapObject>> mapObjects = new Vector<Vector<MapObject>>();

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
    	mr.getMap(mapObjects);
    	graphics.printNames();
    }
    
    public void loadNewMap(){}
	//TODO mapreader
	//TODO mapobjectarray mapreader.getMap
    
    public void drawMap(Graphics2D g2d){
    	for(int i=0; i<drawLevels; i++){
    		for(int j=0; j<mapObjects.get(i).size(); j++){
    			mapObjects.get(i).get(j).draw(g2d,graphics);
    			System.out.println("drawing: "+mapObjects.get(i).get(j).getImageUrl());
    		}
    	}
    }
}
