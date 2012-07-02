package multiplayer;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import main.GameConstants;
import main.GraphicalGameUnit;
import main.UnitNavigator;
import main.UnitState;
import map.Map;
import mapobjects.MapObject;
import mapobjects.Player;
import mapobjects.Upgrade;
import unitTransitions.TransitionUnit;

/**
 * Subclass of GraphicalGameUnit for network multiplayer games.
 * 
 * @author Dorian
 * 
 */
public class MultiplayerUnit extends GraphicalGameUnit implements
		multiplayer.ReadFromHost.SocketListener, UpgradeListener {

	private Map multiplayerMap;
	private BufferedImage mapCanvas;

	private int mapCanvasPosX = 0;
	private int mapCanvasPosY = 0;

	private String mapName = "MP-Woodwars";

	private ReadFromHost fromHost = null;
	private DataOutputStream os = null;
	private DataInputStream is = null;
	private Player myPlayer;
	private int myPlayerIndex;

	private Server server;
	private Socket toHostSocket;
	private boolean asHost;

	private boolean playerDiedMsgSend = false;

	private int[] attendingPLayers;
	/**
	 * Some win messages one can randomly choose from.
	 */
	private final String[] winMessages = { " is bomb-happy!",
			" achieves a blasting victory!", " leaves nothing but a crater!" };
	/**
	 * Message to be shown in case of a draw.
	 */
	private final String drawMessage = "Unbelieveable! It's a draw!";
	private int playersRemaining;

	/**
	 * Create a multiplayer unit
	 * 
	 * @param fromHost
	 * @param myPlayerIndex
	 * @param mapName
	 * @param toHostSocket
	 * @param attendingPLayers
	 */
	public MultiplayerUnit(ReadFromHost fromHost, int myPlayerIndex,
			String mapName, Socket toHostSocket, int[] attendingPLayers) {
		this.toHostSocket = toHostSocket;
		this.fromHost = fromHost;
		this.attendingPLayers = attendingPLayers;
		fromHost.setListener(this);
		try {
			os = new DataOutputStream(toHostSocket.getOutputStream());
			is = new DataInputStream(toHostSocket.getInputStream());
		} catch (IOException e) {
			System.err.println("Failed to fetch Stream from socket.");
			e.printStackTrace();
		}
		this.myPlayerIndex = myPlayerIndex;
		this.mapName = mapName;
		initComponent();
	}

	@Override
	public void updateComponent() {
		if (playersRemaining <= 1)
			multiplayerMap.finishMap();

		if (!myPlayer.isAlive() && !playerDiedMsgSend) {
			writeToHost("Player:" + myPlayerIndex + ";died");
			playersRemaining--;
			playerDiedMsgSend = true;
		}

		if (!multiplayerMap.isFinished())
			multiplayerMap.update();
		else {
			BufferedImage msg = null;
			if (playersRemaining == 0) {
				msg = createTransitionMessage(drawMessage);
			} else {
				for (int i = 0; i < multiplayerMap.getPlayers().size(); i++) {
					if (multiplayerMap.getPlayerByNumber(i + 1).isAlive()) {
						msg = createTransitionMessage("Player "
								+ (i + 1)
								+ winMessages[(int) (Math.random() * winMessages.length)]);
						break;
					}
				}
			}
			TransitionUnit trans = new TransitionUnit(UnitState.BASE_MENU_UNIT,
					msg, false);
			UnitNavigator.getNavigator().addGameUnit(trans,
					UnitState.TEMPORARY_UNIT);
			UnitNavigator.getNavigator().set(UnitState.TEMPORARY_UNIT);
		}

	}

	@Override
	public void handleKeyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		/*
		 * Pause Game
		 */
		if (key == KeyEvent.VK_ESCAPE) {
			UnitNavigator.getNavigator().set(UnitState.BASE_MENU_UNIT);
			// schicke dem server eine stop-nachricht. dieser echot die
			// nachricht zur�ck und meldet den entsprechenden socket/thread ab
			return;
		}
		/*
		 * Player KeyEvents
		 */
		if (myPlayer.isAlive()) {
			if (key == KeyEvent.VK_UP) {
				if (!myPlayer.direction.isUp()) {
					int tmpPosX = myPlayer.getPosX();
					int tmpPosY = myPlayer.getPosY();
					writeToHost("Player:" + myPlayerIndex + ";" + "Up" + ";"
							+ "Pressed" + "/" + tmpPosX + "/" + tmpPosY);
					myPlayer.direction.setUp(true);
				}
				return;
			}

			if (key == KeyEvent.VK_DOWN) {
				if (!myPlayer.direction.isDown()) {
					int tmpPosX = myPlayer.getPosX();
					int tmpPosY = myPlayer.getPosY();
					writeToHost("Player:" + myPlayerIndex + ";" + "Down" + ";"
							+ "Pressed" + "/" + tmpPosX + "/" + tmpPosY);
					myPlayer.direction.setDown(true);

				}
				return;
			}

			if (key == KeyEvent.VK_LEFT) {
				if (!myPlayer.direction.isLeft()) {
					int tmpPosX = myPlayer.getPosX();
					int tmpPosY = myPlayer.getPosY();
					writeToHost("Player:" + myPlayerIndex + ";" + "Left" + ";"
							+ "Pressed" + "/" + tmpPosX + "/" + tmpPosY);
					myPlayer.direction.setLeft(true);

				}
				return;
			}

			if (key == KeyEvent.VK_RIGHT) {
				if (!myPlayer.direction.isRight()) {
					int tmpPosX = myPlayer.getPosX();
					int tmpPosY = myPlayer.getPosY();
					writeToHost("Player:" + myPlayerIndex + ";" + "Right" + ";"
							+ "Pressed" + "/" + tmpPosX + "/" + tmpPosY);
					myPlayer.direction.setRight(true);

				}
				return;
			}

			if (key == KeyEvent.VK_SPACE) {
				if (!(myPlayer.getCurrentBombs() == myPlayer.getMaxBombs())) {
					int tmpPosX = myPlayer.getPosX();
					int tmpPosY = myPlayer.getPosY();
					writeToHost("Player:" + myPlayerIndex + ";" + "Bomb" + ";"
							+ "Pressed" + "/" + tmpPosX + "/" + tmpPosY);
					myPlayer.plantBomb(multiplayerMap.getCollisionMap());
				}
			}

			if (key == KeyEvent.VK_C) {
				if (myPlayer.hasRemoteBombs()) {
					writeToHost("Player:" + myPlayerIndex + ";" + "Remote"
							+ ";" + "Pressed");
					myPlayer.bombExplode();
				}
			}

			if (key == KeyEvent.VK_S) {
				if (myPlayer.activateShield()) {
					int tmpPosX = myPlayer.getPosX();
					int tmpPosY = myPlayer.getPosY();
					writeToHost("Player:" + myPlayerIndex + ";" + "Shield"
							+ ";" + "Pressed" + "/" + tmpPosX + "/" + tmpPosY);
				}
			}
		}
	}

	@Override
	public void handleKeyReleased(KeyEvent e) {
		int key = e.getKeyCode();

		if (key == KeyEvent.VK_UP) {
			int tmpPosX = myPlayer.getPosX();
			int tmpPosY = myPlayer.getPosY();
			writeToHost("Player:" + myPlayerIndex + ";" + "Up" + ";"
					+ "Released" + "/" + tmpPosX + "/" + tmpPosY);
			myPlayer.direction.setUp(false);
			return;
		}

		if (key == KeyEvent.VK_DOWN) {
			int tmpPosX = myPlayer.getPosX();
			int tmpPosY = myPlayer.getPosY();
			writeToHost("Player:" + myPlayerIndex + ";" + "Down" + ";"
					+ "Released" + "/" + tmpPosX + "/" + tmpPosY);
			myPlayer.direction.setDown(false);
			return;
		}

		if (key == KeyEvent.VK_LEFT) {
			int tmpPosX = myPlayer.getPosX();
			int tmpPosY = myPlayer.getPosY();
			writeToHost("Player:" + myPlayerIndex + ";" + "Left" + ";"
					+ "Released" + "/" + tmpPosX + "/" + tmpPosY);
			myPlayer.direction.setLeft(false);
			return;
		}

		if (key == KeyEvent.VK_RIGHT) {
			int tmpPosX = myPlayer.getPosX();
			int tmpPosY = myPlayer.getPosY();
			writeToHost("Player:" + myPlayerIndex + ";" + "Right" + ";"
					+ "Released" + "/" + tmpPosX + "/" + tmpPosY);
			myPlayer.direction.setRight(false);
		}
	}

	@Override
	public void initComponent() {
		/*
		 * load map and players
		 */
		multiplayerMap = new Map(mapName);
		multiplayerMap.removeUnattendedPlayers(attendingPLayers);
		myPlayer = multiplayerMap.getPlayerByNumber(myPlayerIndex);
		System.out.println("PLAYER COUNT: "
				+ multiplayerMap.getPlayers().size());
		for (int i = 0; i < multiplayerMap.getPlayers().size(); i++) {
			multiplayerMap.getPlayerByNumber(i + 1).setMultiplayerModeTo(true);
			if (!(i + 1 == myPlayerIndex))
				multiplayerMap.getPlayerByNumber(i + 1).makeRemote();
		}
		playersRemaining = multiplayerMap.getPlayers().size();
		/*
		 * initializing the upgrade system
		 */
		multiplayerMap.setCMListener(myPlayer);
		multiplayerMap.setUpgradeListener(this);
		if (myPlayerIndex != 1)
			multiplayerMap.setMaxUpgrades(0);
		/*
		 * calculate mapCanvas position on panel
		 */
		mapCanvasPosX = (GameConstants.FRAME_SIZE_X - multiplayerMap.getWidth()) / 2;
		mapCanvasPosY = (GameConstants.FRAME_SIZE_Y - multiplayerMap
				.getHeight()) / 2;
		/*
		 * create mapCanvas (used to depict and center the map)
		 */
		mapCanvas = new BufferedImage(multiplayerMap.getWidth(),
				multiplayerMap.getHeight(), BufferedImage.TYPE_INT_ARGB);
		/*
		 * load font
		 */
		try {
			unitFont = loadFont("font1.TTF").deriveFont(50f);
		} catch (Exception e) {
			System.err.println("ERROR LOADING FONT: font1.TTF");
			e.printStackTrace();
			unitFont = new Font("serif", Font.PLAIN, 24);
		}
	}

	@Override
	public void drawComponent(Graphics g) {
		/*
		 * Black background color
		 */
		g.setColor(Color.black);
		g.fillRect(0, 0, GameConstants.FRAME_SIZE_X, GameConstants.FRAME_SIZE_Y);
		/*
		 * draw map onto the mapcanvas
		 */
		multiplayerMap.drawMap((Graphics2D) mapCanvas.getGraphics());
		/*
		 * center mapcanvas on panel by using the Graphics parameter
		 */
		g.drawImage(mapCanvas, mapCanvasPosX, mapCanvasPosY,
				mapCanvas.getWidth(), mapCanvas.getHeight(), null);
	}

	/*
	 * The following Method handles any toHost communication
	 */

	public void writeToHost(String outgoing) {
		try {
			os.writeUTF(outgoing);
		} catch (IOException e) {
			System.out.println("Failed to write Message!");
		}
	}

	/*
	 * The following section deals with incoming server-messages
	 */

	public void analizeIncoming(String incomingMsg) {
		if (incomingMsg.indexOf("Player:") != -1) {
			String[] parts = incomingMsg.split(":");
			int playerIndex = Integer.parseInt(parts[1].substring(0, 1));
			handlePlayerEvents(playerIndex, parts[1].substring(2));
			return;
		}
		if (incomingMsg.indexOf("Upgrade:") != -1) {
			String[] parts = incomingMsg.split(":");
			handleUpgradeEvents(parts[1]);
		}
	}

	private void handlePlayerEvents(int playerIndex, String incoming) {
		if (incoming.indexOf("Pressed") != -1) {
			handlePlayerMovementOnPress(playerIndex, incoming);
			return;
		}
		if (incoming.indexOf("Released") != -1) {
			handlePlayerMovementOnRelease(playerIndex, incoming);
			return;
		}
		if (incoming.indexOf("died") != -1) {
			Player player = multiplayerMap.getPlayerByNumber(playerIndex);
			if (player.isAlive()) {
				playersRemaining--;
				multiplayerMap.getMapObjects().get(2).remove(player);
				multiplayerMap.getPlayerByNumber(playerIndex).die();
			}
		}
	}

	private void handlePlayerMovementOnPress(int playerIndex, String incoming) {
		Player tmpPlayer = multiplayerMap.getPlayerByNumber(playerIndex);
		String[] parts = incoming.split("/");
		if (incoming.indexOf("Up") != -1) {
			tmpPlayer.setPosX(Integer.parseInt(parts[1]));
			tmpPlayer.setPosY(Integer.parseInt(parts[2]));
			tmpPlayer.direction.setUp(true);
			return;
		}
		if (incoming.indexOf("Down") != -1) {
			tmpPlayer.setPosX(Integer.parseInt(parts[1]));
			tmpPlayer.setPosY(Integer.parseInt(parts[2]));
			tmpPlayer.direction.setDown(true);
			return;
		}
		if (incoming.indexOf("Left") != -1) {
			tmpPlayer.setPosX(Integer.parseInt(parts[1]));
			tmpPlayer.setPosY(Integer.parseInt(parts[2]));
			tmpPlayer.direction.setLeft(true);
			return;
		}
		if (incoming.indexOf("Right") != -1) {
			tmpPlayer.setPosX(Integer.parseInt(parts[1]));
			tmpPlayer.setPosY(Integer.parseInt(parts[2]));
			tmpPlayer.direction.setRight(true);
			return;
		}
		if (incoming.indexOf("Bomb") != -1) {
			tmpPlayer.setPosX(Integer.parseInt(parts[1]));
			tmpPlayer.setPosY(Integer.parseInt(parts[2]));
			tmpPlayer.plantBomb(multiplayerMap.getCollisionMap());
			return;
		}
		if (incoming.indexOf("Remote") != -1) {
			tmpPlayer.bombExplode();
			return;
		}
		if (incoming.indexOf("Shield") != -1) {
			tmpPlayer.setPosX(Integer.parseInt(parts[1]));
			tmpPlayer.setPosY(Integer.parseInt(parts[2]));
			tmpPlayer.activateShield();
		}
	}

	private void handlePlayerMovementOnRelease(int playerIndex, String incoming) {
		Player tmpPlayer = multiplayerMap.getPlayerByNumber(playerIndex);
		String[] parts = incoming.split("/");
		if (incoming.indexOf("Up") != -1) {
			tmpPlayer.setPosX(Integer.parseInt(parts[1]));
			tmpPlayer.setPosY(Integer.parseInt(parts[2]));
			tmpPlayer.direction.setUp(false);
			return;
		}
		if (incoming.indexOf("Down") != -1) {
			tmpPlayer.setPosX(Integer.parseInt(parts[1]));
			tmpPlayer.setPosY(Integer.parseInt(parts[2]));
			tmpPlayer.direction.setDown(false);
			return;
		}
		if (incoming.indexOf("Left") != -1) {
			tmpPlayer.setPosX(Integer.parseInt(parts[1]));
			tmpPlayer.setPosY(Integer.parseInt(parts[2]));
			tmpPlayer.direction.setLeft(false);
			return;
		}
		if (incoming.indexOf("Right") != -1) {
			tmpPlayer.setPosX(Integer.parseInt(parts[1]));
			tmpPlayer.setPosY(Integer.parseInt(parts[2]));
			tmpPlayer.direction.setRight(false);
		}
	}

	private void handleUpgradeEvents(String incoming) {
		if (incoming.indexOf("PickUp") != -1) {
			System.out.println(incoming);
			String[] parts = incoming.split(";");
			int tmpPlayerIndex = Integer.parseInt(parts[0].substring(6));
			for (MapObject obj : multiplayerMap.getMapObjects().get(1)) {
				if (obj instanceof Upgrade) {
					Upgrade tmpUpgrade = (Upgrade) obj;
					if (tmpUpgrade.getMPID().equals(parts[2])) {
						multiplayerMap.getPlayerByNumber(tmpPlayerIndex)
								.giveUpgrade(tmpUpgrade);
						break;
					}
				}
			}
		} else {
			System.out.println(incoming);
			String[] parts = incoming.split("/");
			Color tmpColor = stringToColor(parts[0]);
			int tmpPosX = Integer.parseInt(parts[1]);
			int tmpPosY = Integer.parseInt(parts[2]);
			String MPID = parts[3];
			Upgrade upgrade = new Upgrade(tmpPosX, tmpPosY, true, true, true,
					"upgrades", multiplayerMap.getGraphics(), tmpColor, MPID);
			multiplayerMap.addUpgradeRemotely(upgrade);
			upgrade.setMap(multiplayerMap);

		}
	}

	private Color stringToColor(String colName) {
		Color c = null;
		if (colName.equals("pink"))
			return Color.pink;
		if (colName.equals("blue"))
			return Color.blue;
		if (colName.equals("cyan"))
			return Color.cyan;
		if (colName.equals("magenta"))
			return Color.magenta;
		if (colName.equals("lightGray"))
			return Color.lightGray;
		return c;
	}

	/**
	 * Generates a BufferedImage to be passed to a TransitionUnit.
	 * 
	 * @return BufferedImage message
	 */
	private BufferedImage createTransitionMessage(String message) {
		/*
		 * create BufferedImage and paint map using its Graphics2D object
		 */
		multiplayerMap.drawMap(mapCanvas.createGraphics());
		BufferedImage msg = new BufferedImage(GameConstants.FRAME_SIZE_X,
				GameConstants.FRAME_SIZE_Y, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = msg.createGraphics();
		g2d.setColor(Color.black);
		g2d.drawRect(0, 0, msg.getWidth(), msg.getHeight());
		g2d.drawImage(mapCanvas, mapCanvasPosX, mapCanvasPosY,
				mapCanvas.getWidth(), mapCanvas.getHeight(), null);
		g2d.setFont(unitFont);
		g2d.setColor(new Color(0, 0, 0, 200));
		g2d.fillRect(mapCanvasPosX, mapCanvasPosY, mapCanvas.getWidth(),
				mapCanvas.getHeight());
		g2d.setColor(Color.white);
		/*
		 * center text message
		 */
		Rectangle2D rect = unitFont.getStringBounds(message,
				g2d.getFontRenderContext());
		g2d.drawString(message,
				(int) (GameConstants.FRAME_SIZE_X - rect.getWidth()) / 2,
				(int) (GameConstants.FRAME_SIZE_Y - rect.getHeight()) / 2);
		return msg;
	}

	@Override
	public void upgradeSpawned(int x, int y, String color, String MPID) {
		writeToHost("!Upgrade:" + color + "/" + x + "/" + y + "/" + MPID);
	}

	@Override
	public void upgradePickedUp(int PosAtList, String MPID) {
		writeToHost("!Upgrade:" + "Player" + myPlayerIndex + ";" + "PickUp"
				+ ";" + MPID);

	}
}
