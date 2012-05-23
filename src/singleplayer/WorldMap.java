package singleplayer;

import imageloader.GameImage;

import java.awt.Point;

import map.Map;

public class WorldMap {

	private int selectedLevel;
	private Point[] levelCoords;
	private int maxLevelAccessible;
	private Map worldmap;

	public WorldMap(Point[] levelCoords, Map worldmap) {
		this.levelCoords = levelCoords;
		this.worldmap = worldmap;
		maxLevelAccessible = 0;
		selectedLevel = 0;
	}
	
	

	public Map getMap() {
		return worldmap;
	}

	public void setWorldmap(Map worldmap) {
		this.worldmap = worldmap;
	}

	public int getSelectedLevel() {
		return selectedLevel;
	}

	public int getNumOfLevels() {
		return levelCoords.length;
	}

	public Point getPlayerCoord() {
		return levelCoords[selectedLevel];
	}

	public Point[] getLevelCoords() {
		return levelCoords;
	}

	public void setSelectedLevel(int newSelectedLevel) {
		if (newSelectedLevel <= maxLevelAccessible && newSelectedLevel >= 0) {
			selectedLevel = newSelectedLevel;
		}
	}

	public void setMaxLevelAccessible(int newMaxLevel) {
		if (newMaxLevel >= 0 && newMaxLevel <= levelCoords.length) {
			maxLevelAccessible = newMaxLevel;
		}
	}

	public int getMaxLevelAccessible() {
		return maxLevelAccessible;
	}

}
