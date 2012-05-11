package singleplayer;

import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import main.GameConstants;
import map.Map;
import map.MapReader;

/*
 * stores sequences of map names ( = level) 
 */
public class Campaign {

	private static final String SEQUENCE_INDICATOR = "seq";
	private static final String WORLDMAP_INDICATOR = "wm";
	
	private ArrayList<MapSequence> levels;
	private boolean campaignFinished;
	private int mapCounter;
	private int levelProgress;
	private int currentLevel;
	private MapReader mapReader;
	private WorldMap worldMap;
	
	public Campaign(ArrayList<MapSequence> levels, WorldMap worldMap) {
		if (levels == null || worldMap == null) {
			throw new IllegalArgumentException();
		} else {
			this.levels = levels;
			this.worldMap = worldMap;
			mapReader = new MapReader();
			campaignFinished = false;
			mapCounter = 0;
			levelProgress = 0;
			currentLevel = 0;
		}
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
	public static Campaign readCampaignFromFile(String filename) throws IOException, FileNotFoundException {
		ArrayList<MapSequence> mapSequences = new ArrayList<MapSequence>();
		WorldMap worldMap = null;
		File campaignFile = new File(GameConstants.CAMPAIGNS_DIR + filename);
		Scanner sc = new Scanner(campaignFile);
		while (sc.hasNext()) {
			String[] data = sc.nextLine().split(":");
			if (data[0].startsWith(SEQUENCE_INDICATOR)) {
				String[] prefix = data[0].split(".");
				System.out.println("seq found");
				int index = Integer.parseInt(prefix[1]);
				if (mapSequences.get(index) == null) {
					MapSequence mapSeq = new MapSequence();
					mapSequences.add(index, mapSeq);
				}
				mapSequences.get(index).addMap(data[1]);
			}
			if (data[0].startsWith(WORLDMAP_INDICATOR)) {
				String[] mapData = data[1].split("-");
				
				String[] coordsData = mapData[0].split(";");
				Point[] coords = new Point[coordsData.length];
				for (int i = 0; i < coordsData.length; i++) {
					String[] splitCoord = coordsData[i].split(",");
					coords[i] = new Point(Integer.parseInt(splitCoord[0]), Integer.parseInt(splitCoord[1]));
				}
				String[] imageData = mapData[2].split(";");
				
				worldMap = new WorldMap(coords, imageData[1], imageData[0]);
				
			}
		}
		return new Campaign(mapSequences, worldMap);
	}

	
	/*
	 * inner class MapSequence stores a sequence of map names
	 * in an ArrayList
	 */
	public static class MapSequence {
		
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
	
	public static void main(String[] args) throws IOException {
		//Campaign campaign = Campaign.readCampaignFromFile("campaign1.txt");
	}
}
