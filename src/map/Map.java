package map;

import imageloader.ImageLoader;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Vector;

import mapobjects.Bomb;
import mapobjects.Enemy;
import mapobjects.Exit;
import mapobjects.MapObject;
import mapobjects.Player;

/**
 * the map class holds all objects of the map in a vector (MapObjects) the map
 * is loaded by the MapReader
 * 
 * @author eik
 * 
 */
public class Map {
	private ImageLoader graphics = new ImageLoader();
	private Vector<Vector<MapObject>> mapObjects = new Vector<Vector<MapObject>>();

	private String mapName;
	private int mapSizeX;
	private int mapSizeY;
	private int drawLevels;
	private Vector<Player> players;
	private int playerIDCnt = 0;
	private boolean mapFinished = false;

	private int enemies = 0;
	private Exit exit;

	BufferedImage collisionMap;
	MapReader mr;

	/**
	 * constructor
	 * 
	 * @param mn
	 *            the name of the map
	 */
	public Map(String mn) {
		mr = new MapReader(mn);

		// read Mapheader
		mapName = mr.getHeader("mapname");
		mapSizeX = Integer.parseInt(mr.getHeader("sizex"));
		mapSizeY = Integer.parseInt(mr.getHeader("sizey"));
		drawLevels = mr.getDrawLevels();

		mr.loadGraphics(graphics);
		mr.getMap(mapObjects, graphics);
		enemies = mr.getEnemies();

		collisionMap = new BufferedImage(mapSizeX, mapSizeY,
				BufferedImage.TYPE_INT_ARGB);
		Graphics2D gtemp = collisionMap.createGraphics();
		gtemp.setPaint(Color.white);
		gtemp.fillRect(mapSizeX, mapSizeY, 50, 50);
		gtemp.dispose();

		players = new Vector<Player>();
		for (int i = 0; i < drawLevels; i++) {
			for (int j = 0; j < mapObjects.get(i).size(); j++) {
				mapObjects.get(i).get(j).setMap(this);
				if (mapObjects.get(i).get(j) instanceof Player) {
					Player player = (Player) mapObjects.get(i).get(j);
					players.add(player);
					player.setID(playerIDCnt);
					playerIDCnt++;
				}
				if (mapObjects.get(i).get(j) instanceof Exit) {
					exit = (Exit) mapObjects.get(i).get(j);
				}
			}
		}
	}

	/**
	 * iterates over the MapObjects and calls their draw methods
	 * 
	 * @param g2d
	 *            graphics2D object to draw the map
	 */
	public void drawMap(Graphics2D g2d) {
		BufferedImage collisionMaptemp = new BufferedImage(mapSizeX, mapSizeY,
				BufferedImage.TYPE_INT_ARGB);
		Graphics2D b = collisionMaptemp.createGraphics();
		for (int i = 0; i < drawLevels; i++) {
			for (int j = 0; j < mapObjects.get(i).size(); j++) {
				if (mapObjects.get(i).get(j).isVisible()) {
					mapObjects.get(i).get(j).draw(g2d, graphics, b);
				}
			}
		}
		b.dispose();
		collisionMap = collisionMaptemp;
		// g2d.drawImage(collisionMap, 0, 0, mapSizeX, mapSizeY, null);

	}

	/**
	 * iterates over the MapObjects and calls their update methods removes
	 * destroyed objects/enemies
	 */
	public void update() {
		if (enemies <= 0 && exit != null) {
			this.exit.activate();
		}
		for (int i = 0; i < drawLevels; i++) {
			for (int j = 0; j < mapObjects.get(i).size(); j++) {
				mapObjects.get(i).get(j);
				if (mapObjects.get(i).get(j).isDestroyed()) { // destroyed
					// mapObjects
					// will be
					// removed from
					// the list
					if (mapObjects.get(i).get(j) instanceof Bomb) {
						for (Player player : players) {
							if (player.getID() == ((Bomb) mapObjects.get(i)
									.get(j)).getPlayerID())
								player.removeBomb();
						}// TODO Auto-generated constructor stub
					}
					if (mapObjects.get(i).get(j) instanceof Enemy) {
						this.decreaseEnemies();
					}
					mapObjects.get(i).remove(j);

				} else {
					mapObjects.get(i).get(j).update(collisionMap);
				}
			}
		}
	}

	/**
	 * 
	 * @return BufferedImage of the collision map
	 */
	public BufferedImage getCollisionMap() {
		return collisionMap;
	}

	public void setCollisionMap(BufferedImage collisionMap) {
		this.collisionMap = collisionMap;
	}

	/**
	 * 
	 * @return the ImageLoader of the map
	 */
	public ImageLoader getGraphics() {
		return graphics;
	}

	/**
	 * sets the ImageLoader of the map
	 * 
	 * @param graphics
	 *            the new ImageLoader object
	 */
	public void setGraphics(ImageLoader graphics) {
		this.graphics = graphics;
	}

	/**
	 * 
	 * @return the MapObject vector
	 */
	public Vector<Vector<MapObject>> getMapObjects() {
		return mapObjects;
	}

	/**
	 * sets the mapObjects
	 * 
	 * @param mapObjects
	 *            the new MapObject vector
	 */
	public void setMapObjects(Vector<Vector<MapObject>> mapObjects) {
		this.mapObjects = mapObjects;
	}

	/**
	 * returns if map is finished
	 * 
	 * @return true if map is finished else false
	 */
	public boolean isFinished() {
		return mapFinished;
	}

	/**
	 * 
	 * @return true if player is alive else false
	 */
	public boolean playerSucced() {
		if (players.size() > 0) {
			return players.get(0).isAlive();
		}
		return false;
	}

	/**
	 * sets mapFinished true
	 */
	public void finishMap() {
		mapFinished = true;
	}

	/**
	 * 
	 * @return the Player object off the map
	 */
	public Player getMapPlayer() {
		if (players.size() > 0)
			return players.get(0);
		return null;
	}

	/**
	 * returns player by given number
	 * 
	 * @param number
	 *            number of the player
	 * @return Player object
	 */
	public Player getPlayerByNumber(int number) {
		if (number <= players.size()) {
			return players.get(number - 1);
		}
		return null;
	}

	/**
	 * 
	 * @return width of the map
	 */
	public int getWidth() {
		return this.mapSizeX;
	}

	/**
	 * 
	 * @return heigth of the map
	 */
	public int getHeight() {
		return this.mapSizeY;
	}

	/**
	 * decreases the enemy counter
	 */
	public void decreaseEnemies() {
		if (enemies != 0)
			this.enemies--;
		System.out.println(enemies);
	}

	/**
	 * 
	 * @return a list of the players
	 */
	public List<Player> getPlayers() {
		return players;
	}

}
