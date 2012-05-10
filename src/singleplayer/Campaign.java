package singleplayer;

import java.util.ArrayList;

import map.Map;
import map.MapReader;

/*
 * stores sequences of map names ( = level) 
 */
public class Campaign {

	private ArrayList<MapSequence> levels;
	private WorldMap worldMap;
	private boolean campaignFinished;
	private int mapCounter;
	private int levelProgress;
	private int currentLevel;
	private MapReader mapReader;
	
	public Campaign() {
		levels = new ArrayList<MapSequence>();
		mapReader = new MapReader();
		worldMap = new WorldMap();
		campaignFinished = false;
		mapCounter = 0;
		levelProgress = 0;
		currentLevel = 0;
	}
	
	/*
	 * read current map using the mapReader 
	 */
	public Map getCurrentMap() {
		//return mapReader.readMap(levels.get(currentLevel).getMap(mapCounter));
		return null;
	}
	
	/*
	 * called by the LevelManager
	 */
	public void finishedMap(boolean successful) {
		// TODO Auto-generated method stub
	}
	
	
	/*
	 * update level progress and map / level counter
	 */
	
	public void setLevelProgress(int newLevelProgress) {
		if (newLevelProgress < levels.size() && newLevelProgress >= 0) {
			levelProgress = newLevelProgress;
		} else {
			throw new IllegalArgumentException("Unknown Level");
		}
	}
	
	public void setCurrentLevel(int newCurrentLevel) {
		if (newCurrentLevel <= levelProgress && newCurrentLevel >= 0) {
			currentLevel = newCurrentLevel;
		} else {
			throw new IllegalArgumentException("Unknown Level");
		}
	}
	
	public void setMapCounter(int newMapCounter) {
		if (levels.get(currentLevel)!=null && newMapCounter < levels.get(currentLevel).getSize()) {
			mapCounter = newMapCounter;
		}
	}
		
	/*
	 * read campaign from text file
	 */	
	public static Campaign readCampaignFromFile(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	
	/*
	 * inner class MapSequence stores a sequence of map names
	 * in an ArrayList
	 */
	public class MapSequence {
		
		ArrayList<String> maps;
		
		public MapSequence() {
			maps = new ArrayList<String>();
		}
		
		public int getSize() {
			return maps.size();
		}
		
		public String getMap(int index) {
			return maps.get(index);
		}
		
		public void addMap(String newMap) {
			if (newMap != null) {
				maps.add(newMap);
			}
		}
		public void removeMap(String mapToRemove) {
			if (mapToRemove != null) {
				maps.remove(mapToRemove);
			}
		}
	}
}
