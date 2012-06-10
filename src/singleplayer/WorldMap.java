package singleplayer;

import java.awt.Point;

import map.Map;

/**
 * The WorldMap class contains all data required to manage a WorldMap. This
 * includes a Map object and integer counter variables indicating the player's
 * progress as well as an array storing coordinates that represent locations of
 * all the levels in a campaign. To display and interact with a WorldMap object,
 * one can use the WorldMapUnit class.
 * 
 * @author tohei
 * @see singleplayer.Campaign
 * @see singleplayer.WorldMapUnit
 */
public class WorldMap {

	/**
	 * The level selected by the user.
	 */
	private int selectedLevel;
	/**
	 * Coordinates of locations (levels) on the map object.
	 */
	private Point[] levelCoords;
	/**
	 * The maximum level accessible by the player. On successfully completing
	 * the current max level, this counter will be increased, allowing the user
	 * to advance.
	 */
	private int maxLevelAccessible;
	/**
	 * A map object representing the WorldMap.
	 */
	private Map worldmap;

	/**
	 * Construct a WorldMap.
	 * 
	 * @param levelCoords
	 *            level locations.
	 * @param worldmap
	 *            map object representing the WorldMap.
	 */
	public WorldMap(Point[] levelCoords, Map worldmap) {
		this.levelCoords = levelCoords;
		this.worldmap = worldmap;
		maxLevelAccessible = 0;
		selectedLevel = 0;
	}

	/**
	 * Request the map object.
	 * 
	 * @return world map
	 */
	public Map getMap() {
		return worldmap;
	}

	/**
	 * Sets the worldMap variable to the given map object.
	 * 
	 * @param worldmap
	 *            new world map
	 */
	public void setWorldmap(Map worldmap) {
		this.worldmap = worldmap;
	}

	/**
	 * Returns the level represented by the selectedLevel counter.
	 * 
	 * @return selectedLevel counter variable.
	 */
	public int getSelectedLevel() {
		return selectedLevel;
	}

	/**
	 * The total number of levels available is determined by the size of the
	 * levelCoords array.
	 * 
	 * @return total number of levels available.
	 */
	public int getNumOfLevels() {
		return levelCoords.length;
	}

	/**
	 * Get the coordinates of the selected level's location.
	 * 
	 * @return coordinates of selected level.
	 */
	public Point getPlayerCoord() {
		return levelCoords[selectedLevel];
	}

	/**
	 * Get all the level coordinates of a WorldMap.
	 * 
	 * @return levelCoords array.
	 */
	public Point[] getLevelCoords() {
		return levelCoords;
	}

	/**
	 * Sets the selectedLevel counter variable to the given integer value.
	 * 
	 * @param newSelectedLevel
	 */
	public void setSelectedLevel(int newSelectedLevel) {
		if (newSelectedLevel <= maxLevelAccessible && newSelectedLevel >= 0) {
			selectedLevel = newSelectedLevel;
		}
	}

	/**
	 * Sets the maxLevelAccessible counter variable to the given integer value.
	 * 
	 * @param newMaxLevel
	 */
	public void setMaxLevelAccessible(int newMaxLevel) {
		if (newMaxLevel >= 0 && newMaxLevel <= levelCoords.length) {
			maxLevelAccessible = newMaxLevel;
		}
	}

	/**
	 * Returns the integer value representing the maximum level accessible to
	 * the player.
	 * 
	 * @return maxLevelAccessible
	 */
	public int getMaxLevelAccessible() {
		return maxLevelAccessible;
	}

}
