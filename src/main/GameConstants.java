package main;

/*
 * constants
 */

public class GameConstants {

	public static final int FRAME_SIZE_X = 1024;
	public static final int FRAME_SIZE_Y = 768;

	public static final int TILE_SIZE = 50;

	public static final int ITERATION_TIME = 10;

	public static final int NUM_OF_UNIT_STATES = 3;
	/*
	 * directory constants
	 */
	public static final String CAMPAIGNS_DIR = "campaigns/";
	public static final String MENU_IMAGES_DIR = "graphics/MainMenu/";
	public static final String MAP_FILES_DIR = "maps/";
	public static final String MAP_GRAPHICS_DIR = "graphics/game/map/";
	public static final String SOUNDS_DIR = "sounds";

	private GameConstants() {
		/*
		 * avoid instantiation
		 */
	}
}
