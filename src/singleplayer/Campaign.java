package singleplayer;

import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import main.GameConstants;
import map.Map;

/**
 * Stores sequences of map names ( = level) and the corresponding world map.
 * Offers static method readCampaignFromFile to load previously stored campaign
 * objects from a text file.
 * 
 * @author tohei
 * @see singleplayer.WorldMap
 */
public class Campaign {

	/**
	 * Constant needed for the parsing process.
	 */
	private static final String SEQUENCE_INDICATOR = "seq";
	/**
	 * Constant needed for the parsing process.
	 */
	private static final String WORLDMAP_INDICATOR = "wm";

	/**
	 * Stores MapSequences.
	 */
	private ArrayList<MapSequence> levels;
	/**
	 * Flag indicating the status of a particular campaign object. If every
	 * level has been activated and completed successfully, this flag will be
	 * set to true.
	 */
	private boolean campaignFinished;
	/**
	 * Keeps track of the maps in a MapSequence.
	 */
	private int mapCounter;
	/**
	 * Every campaign needs a WorldMap to visualize the player's progress.
	 */
	private WorldMap worldMap;

	/**
	 * Construct a new Campaign object from a series of MapSequences and a
	 * WorldMap.
	 * 
	 * @param levels
	 *            list of MapSequences (sequences of map names).
	 * @param worldMap
	 *            a WorldMap object.
	 */
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

	/**
	 * Creates a new MapObject from a string found in the current MapSequence
	 * (level) at the index position represented by mapCounter.
	 * 
	 * @return current Map.
	 */
	public Map getCurrentMap() {
		return new Map(levels.get(worldMap.getSelectedLevel()).getMap(
				mapCounter));
	}

	public WorldMap getWorldMap() {
		return worldMap;
	}

	/**
	 * Called by the LevelManager. Returns boolean value: <br>
	 * <ul>
	 * <LI>true - map counter has been successfully incremented</LI>
	 * <LI>false - map counter could not be incremented => there were no more
	 * maps left in this particular level</LI>
	 * </ul>
	 * 
	 * As long as there is a level remaining, update level progress, otherwise
	 * turn campaignFinished to true.<br>
	 * <br>
	 * 
	 * Providing these return values, the LevelManager is able to decide whether
	 * to ask for the next map or to initiate the WorldMap.
	 */
	public boolean updateCounters() {
		if (mapCounter < levels.get(worldMap.getSelectedLevel()).getSize() - 1) {
			mapCounter++;
			return true;
		} else {
			if (worldMap.getSelectedLevel() == worldMap.getMaxLevelAccessible()) {
				if (worldMap.getMaxLevelAccessible() == levels.size() - 1) {
					campaignFinished = true;
				} else {
					worldMap.setMaxLevelAccessible(worldMap
							.getMaxLevelAccessible() + 1);
				}
			}
			return false;
		}
	}

	/**
	 * Check for campaign status.
	 * 
	 * @return campaignFinished flag
	 */
	public boolean isFinished() {
		return campaignFinished;
	}

	/**
	 * Reads a Campaign from a text file.
	 * 
	 * It is of vital importance to stick to the right format (as specified in
	 * the standard 'campaign1.txt' file)
	 * 
	 * @param filename
	 *            name of a text file to be used for loading the Campaign
	 *            object.
	 * @return new Campaign object consisting of all the maps that were listed
	 *         in the corresponding text file.
	 */
	public static Campaign readCampaignFromFile(String filename)
			throws IOException, FileNotFoundException {
		ArrayList<MapSequence> mapSequences = new ArrayList<MapSequence>(10);
		WorldMap worldMap = null;
		File campaignFile = new File(GameConstants.CAMPAIGNS_DIR + filename);
		Scanner sc = new Scanner(campaignFile);
		while (sc.hasNext()) {
			String line = sc.nextLine();
			if (line.startsWith("#"))
				continue;
			String[] data = line.split(":");
			if (data[0].startsWith(SEQUENCE_INDICATOR)) {
				String[] prefix = data[0].split("\\.");
				int index = Integer.parseInt(prefix[1]);
				if (mapSequences.size() <= index) {
					for (int i = mapSequences.size() - 1; i < index; i++) {
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
					coords[i] = new Point(Integer.parseInt(splitCoord[0]),
							Integer.parseInt(splitCoord[1]));
				}
				String worldmap = mapData[1];

				worldMap = new WorldMap(coords, new Map(worldmap));
			}
		}
		return new Campaign(mapSequences, worldMap);
	}

	/**
	 * Inner container class MapSequence semantically groups a sequence of map
	 * names (strings).
	 * 
	 * @author tohei
	 */
	public static class MapSequence {

		private ArrayList<String> maps;

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
