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
		g2d.drawImage(collisionMap, 0, 0, mapSizeX, mapSizeY, null);

	}

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
						}
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

	public BufferedImage getCollisionMap() {
		return collisionMap;
	}

	public void setCollisionMap(BufferedImage collisionMap) {
		this.collisionMap = collisionMap;
	}

	public ImageLoader getGraphics() {
		return graphics;
	}

	public void setGraphics(ImageLoader graphics) {
		this.graphics = graphics;
	}

	public Vector<Vector<MapObject>> getMapObjects() {
		return mapObjects;
	}

	public void setMapObjects(Vector<Vector<MapObject>> mapObjects) {
		this.mapObjects = mapObjects;
	}

	public boolean isFinished() {
		return mapFinished;
	}

	public boolean playerSucced() {
		if (players.size() > 0) {
			return players.get(0).isAlive();
		}
		return false;
	}

	public void finishMap() {
		mapFinished = true;
	}

	public Player getMapPlayer() {
		if (players.size() > 0)
			return players.get(0);
		return null;
	}

	public Player getPlayerByNumber(int number) {
		if (number <= players.size()) {
			return players.get(number - 1);
		}
		return null;
	}

	public int getWidth() {
		return this.mapSizeX;
	}

	public int getHeight() {
		return this.mapSizeY;
	}

	public void decreaseEnemies() {
		if (enemies != 0)
			this.enemies--;
		System.out.println(enemies);
	}

	public List<Player> getPlayers() {
		return players;
	}

}
