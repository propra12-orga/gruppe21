package main;

/**
 * Contains ease-of-use constants that need to be accessible to all classes
 * (directory paths, frame and tile size).
 * 
 * @author tohei
 * 
 */
public class GameConstants {

	public static final int FRAME_SIZE_X = 1024;
	public static final int FRAME_SIZE_Y = 768;

	/**
	 * Most MapObjects rely on a fixed tile size for drawing and collision
	 * detection.
	 */
	public static final int TILE_SIZE = 50;

	/*
	 * Storing path names as constants for easy access and a more flexible file
	 * hierarchy
	 */
	public static final String CAMPAIGNS_DIR = "campaigns/";
	public static final String MENU_IMAGES_DIR = "graphics/gui/MainMenu/";
	public static final String MAP_FILES_DIR = "maps/";
	public static final String MAP_GRAPHICS_DIR = "graphics/game/map/";
	public static final String ANIMATION_FILES_DIR = "graphics/game/";
	public static final String SOUNDS_DIR = "sounds/";
	public static final String FONTS_DIR = "fonts/";

	public static final String[] TOOL_TIPS = {
			"Tipp: You may always return to the World Map by pressing 'Escape'",
			"Tipp: Immortal Bomberman destroys his enemies at a touch!",
			"Tipp: Saving your progress will also save your characters upgrades.",
			"Tipp: You may pause the game by pressing 'p'." };

	/**
	 * Private Constructor prevents other classes from instantiating the
	 * GameConstans class
	 */
	private GameConstants() {
	}
}
