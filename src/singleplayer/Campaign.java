package singleplayer;

import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import main.GameConstants;
import map.Map;


/*
 * stores sequences of map names ( = level) and 
 * the corresponding world map. Offers static
 * method readCampaignFromFile to load previously
 * stored campaign objects from a text file.
 */
public class Campaign {

	private static final String SEQUENCE_INDICATOR = "seq";
	private static final String WORLDMAP_INDICATOR = "wm";
	
	private ArrayList<MapSequence> levels;
	private boolean campaignFinished;
	private int mapCounter;
	private WorldMap worldMap;

	
	public Campaign(ArrayList<MapSequence> levels, WorldMap worldMap) {
		if (levels == null || worldMap == null) {
			throw new IllegalArgumentException();
		} else {
			this.levels = levels;
			this.worldMap = worldMap;
			campaignFinished = false;
			mapCounter = 0;
		}
	}
	
	/*
	 * return current map
	 */
	public Map getCurrentMap() {
		return new Map(levels.get(worldMap.getSelectedLevel()).getMap(mapCounter));
	}
	
	public WorldMap getWorldMap() {
		return worldMap;
	}
	
	/*
	 * called by the LevelManager. Returns boolean value:
	 * 	true - map counter has been successfully incremented
	 * 	false - map counter could not be incremented =>
	 * 			there were no more maps left in this particular 
	 * 			level. 
	 * 			As long as there is a level remaining, 
	 * 			update level progress, otherwise turn campaignFinished to
	 * 			true. 			
	 * 
	 * Providing these return values, the LevelManager is able to decide 
	 * whether to ask for the next map or to initiate the WorldMap.
	 */
	public boolean updateCounters() {
		if (mapCounter < levels.get(worldMap.getSelectedLevel()).getSize()-1) {	
			mapCounter++;
			return true;
		} else {
			if (worldMap.getSelectedLevel() == worldMap.getMaxLevelAccessible()) {
				if (worldMap.getMaxLevelAccessible() == levels.size()-1) {
					campaignFinished = true;
				} else {
					worldMap.setMaxLevelAccessible(worldMap.getMaxLevelAccessible()+1);
				}
			}
			return false;
		}
	}

	public boolean isFinished() {
		return campaignFinished;
	}
	
	/*
	 * read campaign from text file. 
	 * 
	 * Currently, it is of vital importance to stick to 
	 * the right format (as specified in 
	 * the standard 'campaign1.txt' file)
	 * 
	 * Advanced error detection yet to be implemented.
	 */	
	public static Campaign readCampaignFromFile(String filename) throws IOException, FileNotFoundException {
		ArrayList<MapSequence> mapSequences = new ArrayList<MapSequence>(10);
		WorldMap worldMap = null;
		File campaignFile = new File(GameConstants.CAMPAIGNS_DIR + filename);
		Scanner sc = new Scanner(campaignFile);
		while (sc.hasNext()) {
			String line = sc.nextLine();
			if (line.startsWith("#")) continue;
			String[] data = line.split(":");
			if (data[0].startsWith(SEQUENCE_INDICATOR)) {
				String[] prefix = data[0].split("\\.");
				int index = Integer.parseInt(prefix[1]);				
				if (mapSequences.size() <= index) {
					for (int i = mapSequences.size()-1; i < index; i++) {
						mapSequences.add(new MapSequence());
					}
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
				String worldmap = mapData[1];
				
				worldMap = new WorldMap(coords, new Map(worldmap));				
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
		Campaign campaign = Campaign.readCampaignFromFile("campaign1.txt");
		System.out.println(Arrays.toString(campaign.getWorldMap().getLevelCoords()));
	}
}
