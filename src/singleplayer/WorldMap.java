package singleplayer;

import imageloader.GameImage;

import java.awt.Point;

public class WorldMap {

	private int selectedLevel;
	private Point[] levelCoords;
	private int maxLevelAccessible;
	private GameImage background;
	private GameImage player;

	public WorldMap(Point[] levelCoords, String background, String player) {
		this.levelCoords = levelCoords;
		this.background = new GameImage(background);
		this.player = new GameImage(player);
		maxLevelAccessible = 0;
		selectedLevel = 0;
	}

	public GameImage getBackground() {
		return background;
	}

	public GameImage getPlayer() {
		return player;
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
