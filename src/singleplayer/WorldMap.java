package singleplayer;

import imageloader.GameGraphic;

import java.awt.Point;

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
	private String[] coordLabels;
	private GameGraphic[] progressIndicator;
	private GameGraphic backgroundImg;
	private GameGraphic playerImg;
	/**
	 * The maximum level accessible by the player. On successfully completing
	 * the current max level, this counter will be increased, allowing the user
	 * to advance.
	 */
	private int maxLevelAccessible;
	private String worldMapName;

	/**
	 * Construct a WorldMap.
	 * 
	 * @param levelCoords
	 *            level locations.
	 * @param worldMapName
	 * @param worldmap
	 *            map object representing the WorldMap.
	 */
	public WorldMap(Point[] levelCoords, String[] coordLabels,
			GameGraphic[] progressIndicator, GameGraphic backgroundImg,
			GameGraphic playerImg, String worldMapName) {
		this.levelCoords = levelCoords;
		this.coordLabels = coordLabels;
		this.progressIndicator = progressIndicator;
		this.playerImg = playerImg;
		this.backgroundImg = backgroundImg;
		this.worldMapName = worldMapName;
		maxLevelAccessible = 0;
		selectedLevel = 0;
	}

	public String getName() {
		return worldMapName;
	}

	public GameGraphic getPlayerImg() {
		return playerImg;
	}

	/**
	 * Request background image.
	 * 
	 * @return
	 */
	public GameGraphic getBackgroundImg() {
		return backgroundImg;
	}

	/**
	 * Get an array of images that may used to depict a player's progress on the
	 * worldmap.
	 * 
	 * @return
	 */
	public GameGraphic[] getProgressIndicator() {
		return progressIndicator;
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
	 * Returns description of selected level
	 * 
	 * @return coord label of selected level
	 */
	public String getSelectedCoordLabel() {
		return coordLabels[selectedLevel];
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
