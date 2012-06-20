package singleplayer;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.ImageIcon;

import main.GameConstants;
import main.GraphicalGameUnit;
import main.TransitionUnit;
import main.UnitNavigator;
import main.UnitState;
import map.Map;
import mapobjects.Player;

/**
 * The LevelManagerUnit controls all single-player related game content. It
 * starts by loading a particular campaign object from file and requesting a map
 * object to display. The LevelManagerUnit extends GraphicalGameUnit and thus
 * offers a common interface by overriding methods that allow updating and
 * displaying its components.
 * 
 * @author tohei
 * 
 */
public class LevelManagerUnit extends GraphicalGameUnit {

	private Campaign campaign;
	private String campaignFile;
	private Map currentMap;
	/**
	 * The WorldMapUnit is used to display the WorldMap in between different
	 * levels.
	 */
	private WorldMapUnit worldMapUnit;

	/**
	 * Store main Player object so it can be handed to the active Map object
	 * once a new map is loaded in order to maintain its upgrades and stats
	 */
	private Player player;

	/**
	 * X coordinate determining the location of the map canvas on screen.
	 */
	private int mapOffsetX = 0;
	/**
	 * Y coordinate determining the location of the map canvas on screen.
	 */
	private int mapOffsetY = 0;
	/**
	 * If a map's width is smaller than the panel, this flag will be set to true
	 * by initOffset(). If neither mapXSmaller nor mapYSmaller are set, no
	 * further calculation concerning the positioning of the mapCanvas is
	 * needed.
	 */
	private boolean mapXSmaller = false;
	/**
	 * Like mapXSmaller; will be set if a map is shorter in height than the
	 * surrounding panel.
	 */
	private boolean mapYSmaller = false;
	/**
	 * Maps are going to be drawn to this BufferedImage, thus allowing map
	 * positioning to be achieved by just moving this image to the desired
	 * location.
	 */
	private BufferedImage mapCanvas;

	/**
	 * Might be necessary to protect unit from KeyEvent inferno
	 */
	private boolean unitRunning = false;

	public LevelManagerUnit() {
		initComponent();
	}

	/**
	 * This constructor takes a campaign file (a .txt. file that can be used to
	 * construct a campaign object) as an argument.
	 * 
	 * @param campaignFile
	 */
	public LevelManagerUnit(String campaignFile) {
		this.campaignFile = campaignFile;
		initComponent();
	}

	@Override
	public void drawComponent(Graphics g) {
		if (unitRunning) {
			g.setColor(Color.black);
			g.fillRect(0, 0, GameConstants.FRAME_SIZE_X,
					GameConstants.FRAME_SIZE_Y);
			currentMap.drawMap((Graphics2D) mapCanvas.getGraphics());
			g.drawImage(mapCanvas, this.mapOffsetX, this.mapOffsetY,
					currentMap.getWidth(), currentMap.getHeight(), null);
		}
	}

	@Override
	public void handleKeyPressed(KeyEvent e) {
		if (unitRunning) {
			int key = e.getKeyCode();
			if (key == KeyEvent.VK_ESCAPE) {
				UnitNavigator.getNavigator().set(UnitState.BASE_MENU_UNIT);
			}
			if (key == KeyEvent.VK_UP) {
				player.direction.setUp(true);
			}

			if (key == KeyEvent.VK_DOWN) {
				player.direction.setDown(true);
			}

			if (key == KeyEvent.VK_LEFT) {
				player.direction.setLeft(true);
			}

			if (key == KeyEvent.VK_RIGHT) {
				player.direction.setRight(true);
			}

			if (key == KeyEvent.VK_SPACE) {
				player.plantBomb(currentMap.getCollisionMap());
			}

			if (key == KeyEvent.VK_C) {
				player.bombExplode();
			}
		}
	}

	@Override
	public void handleKeyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_UP) {
			player.direction.setUp(false);
		}

		if (key == KeyEvent.VK_DOWN) {
			player.direction.setDown(false);
		}

		if (key == KeyEvent.VK_LEFT) {
			player.direction.setLeft(false);
		}

		if (key == KeyEvent.VK_RIGHT) {
			player.direction.setRight(false);
		}

	}

	@Override
	public void initComponent() {
		try {
			campaign = Campaign.readCampaignFromFile(campaignFile);
			worldMapUnit = new WorldMapUnit(campaign.getWorldMap());

		} catch (FileNotFoundException e) {
			System.err.println("Error loading Campaign: Campaign not found!");
			e.printStackTrace();
			terminateLevelManager();
		} catch (IOException e) {
			System.err.println("Error loading Campaign: IOException!");
			e.printStackTrace();
			terminateLevelManager();
		}
	}

	/**
	 * Remove LevelManagerUnit and return to MainMenu.
	 */
	private void terminateLevelManager() {
		UnitNavigator.getNavigator().set(UnitState.BASE_MENU_UNIT);
		UnitNavigator.getNavigator().removeGameUnit(
				UnitState.LEVEL_MANAGER_UNIT);
	}

	@Override
	public void updateComponent() {
		if (unitRunning) {
			if (!currentMap.isFinished()) {
				currentMap.update();
				updateOffset();
			} else {
				unitRunning = false;

				if (currentMap.playerSucced()) {

					BufferedImage message = createTransitionMessage("graphics/gui/YouWin.png");
					/*
					 * update campaign counters to see if there's a level
					 * remaining
					 */
					if (!campaign.updateCounters()) {
						if (campaign.isFinished()) {
							/*
							 * campaign finished, just show a win message
							 */
							TransitionUnit trans = new TransitionUnit(
									UnitState.BASE_MENU_UNIT, message);
							UnitNavigator.getNavigator().removeGameUnit(
									UnitState.LEVEL_MANAGER_UNIT);
							UnitNavigator.getNavigator().addGameUnit(trans,
									UnitState.TEMPORARY_UNIT);
							UnitNavigator.getNavigator().set(
									UnitState.TEMPORARY_UNIT);
						} else {
							/*
							 * level completed, show world map
							 */
							TransitionUnit trans = new TransitionUnit(
									UnitState.TEMPORARY_UNIT, message,
									worldMapUnit);
							UnitNavigator.getNavigator().addGameUnit(trans,
									UnitState.TEMPORARY_UNIT);
							UnitNavigator.getNavigator().set(
									UnitState.TEMPORARY_UNIT);
						}
					}
				} else {
					/*
					 * player died, show lose message
					 */
					BufferedImage message = createTransitionMessage("graphics/gui/YouLose.png");
					TransitionUnit trans = new TransitionUnit(
							UnitState.LEVEL_MANAGER_UNIT, message);
					UnitNavigator.getNavigator().addGameUnit(trans,
							UnitState.TEMPORARY_UNIT);
					UnitNavigator.getNavigator().set(UnitState.TEMPORARY_UNIT);
				}
			}
		} else {
			/*
			 * in any case: get a new map object
			 */
			changeCurrentMap();
			unitRunning = true;
		}
	}

	/**
	 * Used to request a new map object from the campaign and to update the
	 * mapCanvas to its size.
	 */
	private void changeCurrentMap() {
		currentMap = campaign.getCurrentMap();
		mapCanvas = new BufferedImage(currentMap.getWidth(),
				currentMap.getHeight(), BufferedImage.TYPE_INT_ARGB);
		player = currentMap.getMapPlayer();
		player.direction.setUp(false);
		player.direction.setDown(false);
		player.direction.setLeft(false);
		player.direction.setRight(false);
		initOffset();
	}

	/**
	 * Calculates positioning of the mapCanvas (i.e. the part of the map to be
	 * displayed) depending on the player's position and the size of the map. To
	 * be called on a map change.
	 */
	private void initOffset() {
		if (currentMap.getWidth() < GameConstants.FRAME_SIZE_X) {
			mapOffsetX = (GameConstants.FRAME_SIZE_X - currentMap.getWidth()) / 2;
			mapXSmaller = true;
		} else {
			if (player.getPosX() - GameConstants.TILE_SIZE >= GameConstants.FRAME_SIZE_X) {
				if (player.getPosX() >= currentMap.getWidth()
						- GameConstants.FRAME_SIZE_X) {
					mapOffsetX = -(currentMap.getWidth() - GameConstants.FRAME_SIZE_X);
				} else {
					mapOffsetX = -(player.getPosX() - GameConstants.FRAME_SIZE_X / 2);
				}
			}
		}

		if (currentMap.getHeight() < GameConstants.FRAME_SIZE_Y) {
			mapOffsetY = (GameConstants.FRAME_SIZE_Y - currentMap.getHeight()) / 2; // wenn
																					// Map
																					// kleiner
																					// offset
																					// auf
																					// halben
																					// leerbereich
																					// setzen
			mapYSmaller = true;
		} else {
			if (player.getPosY() - GameConstants.TILE_SIZE >= GameConstants.FRAME_SIZE_Y) {
				if (player.getPosY() >= currentMap.getWidth()
						- GameConstants.FRAME_SIZE_Y) {
					mapOffsetY = -(currentMap.getWidth() - GameConstants.FRAME_SIZE_Y);
				} else {
					mapOffsetY = -(player.getPosY() - GameConstants.FRAME_SIZE_Y / 2);
				}
			}
		}
	}

	/**
	 * Updates positioning of the current map object. Very similar to
	 * initOffset, but to be called on updateComponent().
	 */
	private void updateOffset() {
		if (!mapXSmaller) {
			if (player.getPosX() > GameConstants.FRAME_SIZE_X / 2 - 25) {
				if (player.getPosX() - GameConstants.FRAME_SIZE_X / 2 + 25 < currentMap
						.getWidth() - GameConstants.FRAME_SIZE_X) {
					this.mapOffsetX = -(player.getPosX() - GameConstants.FRAME_SIZE_X / 2)
							- GameConstants.TILE_SIZE / 2;
				} else {
					this.mapOffsetX = -(currentMap.getWidth() - GameConstants.FRAME_SIZE_X);
				}
			} else {
				this.mapOffsetX = 0;
			}
		}

		if (!mapYSmaller) {
			if (player.getPosY() > GameConstants.FRAME_SIZE_Y / 2 - 25) {
				if (player.getPosY() - GameConstants.FRAME_SIZE_Y / 2 + 25 < currentMap
						.getHeight() - GameConstants.FRAME_SIZE_Y) {
					this.mapOffsetY = -(player.getPosY() - GameConstants.FRAME_SIZE_Y / 2)
							- GameConstants.TILE_SIZE / 2;
				} else {
					this.mapOffsetY = -(currentMap.getHeight() - GameConstants.FRAME_SIZE_Y);
				}
			} else {
				this.mapOffsetY = 0;
			}
		}
	}

	/**
	 * Creates a BufferedImage to be passed to a TransitionUnit. The image will
	 * consist of the mapCanvas (background) and a message (foreground).
	 * 
	 * @param filename
	 *            of the message image
	 * @return BufferedImage that shall be displayed by a TransitionUnit
	 */
	private BufferedImage createTransitionMessage(String filename) {
		currentMap.drawMap(mapCanvas.createGraphics());
		Image tmp = new ImageIcon(filename).getImage();

		BufferedImage transitionImage = new BufferedImage(
				GameConstants.FRAME_SIZE_X, GameConstants.FRAME_SIZE_Y,
				BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = transitionImage.createGraphics();
		g2d.setColor(Color.black);
		g2d.drawImage(mapCanvas, mapOffsetX, mapOffsetY, null);
		g2d.drawImage(tmp, 0, transitionImage.getHeight() / 4,
				transitionImage.getWidth(), transitionImage.getHeight() / 2,
				null);
		return transitionImage;
	}

}
