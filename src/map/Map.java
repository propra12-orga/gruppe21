package map;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Vector;

import mapobjects.MapObject;
import mapobjects.Player;
import mapobjects.Wall;

import imageloader.ImageLoader;

public class Map {
	public ImageLoader graphics = new ImageLoader();
	public Vector<Vector<MapObject>> mapObjects = new Vector<Vector<MapObject>>();

	private String mapName;
    private String mapSizeX;
    private String mapSizeY;
    private int drawLevels;
    private Player mapplayer;
    
    BufferedImage CollisionMap;
    
	MapReader mr;
	
    public Map(String mn){
    	mr = new MapReader(mn);
    	
    	//read Mapheader
    	mapName = mr.getHeader("mapname");
    	mapSizeX = mr.getHeader("sizex");
    	mapSizeY = mr.getHeader("sizey");
    	drawLevels = mr.getDrawLevels();
    	
    	mr.loadGraphics(graphics);
    	mr.getMap(mapObjects,graphics);
    	//make player
    	for(int i=0; i<drawLevels; i++){
    		for(int j=0; j<mapObjects.get(i).size(); j++){
    			if(mapObjects.get(i).get(j) instanceof Player){
    				mapplayer = (Player) mapObjects.get(i).get(j);	
    			}
    		}
    	}
    	//make enemies
    	graphics.printNames();
    }
    
    public void loadNewMap(){}
	//TODO mapreader
	//TODO mapobjectarray mapreader.getMap
    // Vorsicht noch nciht impl
    public void drawMap(Graphics2D g2d){
    	for(int i=0; i<drawLevels; i++){
    		for(int j=0; j<mapObjects.get(i).size(); j++){
    			mapObjects.get(i).get(j).draw(g2d,graphics);
    		}
    	}
    }

	public void update() {
		for(int i=0; i<drawLevels; i++){
    		for(int j=0; j<mapObjects.get(i).size(); j++){
    			mapObjects.get(i).get(j).update();
    		}
    	}
        //enemies move
		//draw collision map
		drawCollisionMap();
	}
	
	public void drawCollisionMap(){
		CollisionMap = new BufferedImage(Integer.parseInt(mapSizeX),Integer.parseInt(mapSizeY),BufferedImage.TYPE_INT_ARGB);
		Graphics2D b = CollisionMap.createGraphics();
		for(int i=0; i<drawLevels; i++){
    		for(int j=0; j<mapObjects.get(i).size(); j++){
    			mapObjects.get(i).get(j).drawCollision(b,graphics);
    		}
    	}
		//b.drawImage(image,0,0,null);
		
		b.dispose();
	}
	
	public void movePlayer(String dir){
		//if collision false

		int newposX = mapplayer.getPosX();
		int newposY = mapplayer.getPosY();
		
		
		if (dir.equals("UP")) {
		    newposY = mapplayer.getPosY()-mapplayer.getSpeed();
		}
		
		if (dir.equals("DOWN")) {
			newposY = mapplayer.getPosY()+mapplayer.getSpeed();
		}
		
		if (dir.equals("LEFT")) {
			newposX = mapplayer.getPosX()-mapplayer.getSpeed();
		}
		
		if (dir.equals("RIGHT")) {
			newposX = mapplayer.getPosX()+mapplayer.getSpeed();
		}
		if(hasObjectCollision(newposX,newposY)){return;}
		else if(hasEnemyCollision(newposX,newposY)){
			//die
		}else{
			if (dir.equals("UP")) {
				mapplayer.direction.UP.set(true);
				 
			}
			
			if (dir.equals("DOWN")) {
				mapplayer.direction.DOWN.set(true);
			}
			
			if (dir.equals("LEFT")) {
				mapplayer.direction.LEFT.set(true);
			}
			
			if (dir.equals("RIGHT")) {
				mapplayer.direction.RIGHT.set(true);
			}
		}
	}
	//moveplayer methode
	public void stopPlayer(String dir){
		//if collision false

		int newposX = mapplayer.getPosX();
		int newposY = mapplayer.getPosY();

			if (dir.equals("UP")) {
				mapplayer.direction.UP.set(false);
				 
			}
			
			if (dir.equals("DOWN")) {
				mapplayer.direction.DOWN.set(false);
			}
			
			if (dir.equals("LEFT")) {
				mapplayer.direction.LEFT.set(false);
			}
			
			if (dir.equals("RIGHT")) {
				mapplayer.direction.RIGHT.set(false);
			}
		}
	

	
	
	
	
	//playeraction methode
	
	public boolean hasObjectCollision(int x, int y){
		BufferedImage collTest = CollisionMap.getSubimage(x, y, 50, 50);
		for(int i=0; i<collTest.getWidth(); i++){
			for(int j=0; j<collTest.getHeight(); j++){
				Color test = new Color(collTest.getRGB(i, j));
				if(test.equals(Color.black)){return true;}
			}
		}
		return false;
	}
	
	public boolean hasEnemyCollision(int x, int y){
		return false;
	}
}
