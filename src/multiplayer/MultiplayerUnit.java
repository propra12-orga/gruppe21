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
import java.net.UnknownHostException;

import main.GameConstants;
import main.GraphicalGameUnit;
import main.UnitNavigator;
import main.UnitState;
import map.Map;
import mapobjects.Player;
import unitTransitions.TransitionUnit;

public class MultiplayerUnit extends GraphicalGameUnit {

	private Map multiplayerMap;
	private BufferedImage mapCanvas;

	private int mapCanvasPosX = 0;
	private int mapCanvasPosY = 0;

	private String mapName = "MP-Woodwars";

	private Thread fromHost = null;
	private Socket toHostSocket = null;
	private DataOutputStream os = null;
	private DataInputStream is = null;
	private Player playerOne;
	private Player playerTwo;
	private Player myPlayer;
	private int myPlayerIndex;

	/**
	 * Some win messages one can randomly choose from.
	 */
	private final String[] winMessages = { " is bomb-happy!",
			" achieves a blasting victory!", " leaves nothing but a crater!" };
	/**
	 * Message to be shown in case of a draw.
	 */
	private final String drawMessage = "Unbelieveable! It's a draw!";

	// Constructor
	public MultiplayerUnit() {
		initComponent();
		// to do: hole dir die Adresse und evtl den Port vom Nutzer
		try {
			toHostSocket = new Socket("127.0.0.1", 5555);
			os = new DataOutputStream(toHostSocket.getOutputStream());
			is = new DataInputStream(toHostSocket.getInputStream());
			fromHost = new ReadFromHost(toHostSocket, os, is);
		} catch (UnknownHostException e) {
			System.out.println("Could not reach the host");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public MultiplayerUnit(String mapName) {
		this.mapName = mapName;
		initComponent();
		// to do: hole dir die Adresse und evtl den Port vom Nutzer
		try {
			toHostSocket = new Socket("127.0.0.1", 5555);
			os = new DataOutputStream(toHostSocket.getOutputStream());
			is = new DataInputStream(toHostSocket.getInputStream());
			fromHost = new ReadFromHost(toHostSocket, os, is);
		} catch (UnknownHostException e) {
			System.out.println("Could not reach the host");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateComponent() {
		if (!multiplayerMap.isFinished()) {
			multiplayerMap.update();
		} else {
			/*
			 * A player died: Generate the appropriate image ...
			 */
			BufferedImage transitionMsg = createTransitionMessage(drawMessage);
			if (playerOne.isAlive()) {
				transitionMsg = createTransitionMessage("Player One"
						+ winMessages[(int) (Math.random() * 3)]);
			} else if (playerTwo.isAlive()) {
				transitionMsg = createTransitionMessage("Player Two"
						+ winMessages[(int) (Math.random() * 3)]);
			}
			/*
			 * ... and pass it to a TransitionUnit
			 */
			TransitionUnit trans = new TransitionUnit(UnitState.BASE_MENU_UNIT,
					transitionMsg, false);
			UnitNavigator.getNavigator().removeGameUnit(
					UnitState.LEVEL_MANAGER_UNIT);
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
			return;
		}
		/*
		 * Player KeyEvents
		 */
		if (key == KeyEvent.VK_W) {
			if (!myPlayer.direction.isUp()) {
				try {
					int tmpPosX = myPlayer.getPosX();
					int tmpPosY = myPlayer.getPosY();
					os.writeUTF("Player:" + myPlayerIndex + ";" + "Up" + ";"
							+ "Pressed" + "/" + tmpPosX + "/" + tmpPosY);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			return;
		}

		if (key == KeyEvent.VK_S) {
			if (!myPlayer.direction.isDown())
				try {
					int tmpPosX = myPlayer.getPosX();
					int tmpPosY = myPlayer.getPosY();
					os.writeUTF("Player:" + myPlayerIndex + ";" + "Down" + ";"
							+ "Pressed" + "/" + tmpPosX + "/" + tmpPosY);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			return;
		}

		if (key == KeyEvent.VK_A) {
			if (!myPlayer.direction.isLeft())
				try {
					int tmpPosX = myPlayer.getPosX();
					int tmpPosY = myPlayer.getPosY();
					os.writeUTF("Player:" + myPlayerIndex + ";" + "Left" + ";"
							+ "Pressed" + "/" + tmpPosX + "/" + tmpPosY);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			return;
		}

		if (key == KeyEvent.VK_D) {
			if (!myPlayer.direction.isRight())
				try {
					int tmpPosX = myPlayer.getPosX();
					int tmpPosY = myPlayer.getPosY();
					os.writeUTF("Player:" + myPlayerIndex + ";" + "Right" + ";"
							+ "Pressed" + "/" + tmpPosX + "/" + tmpPosY);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			return;
		}

		if (key == KeyEvent.VK_SPACE) {
			// Prüfe, ob die Anzahl maximaler Bomben erreicht ist
			try {
				os.writeUTF("Player:" + myPlayerIndex + ";" + "Bomb" + ";"
						+ "Pressed");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	@Override
	public void handleKeyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		/*
		 * playerOne KeyEvents
		 */
		if (key == KeyEvent.VK_W) {
			try {
				int tmpPosX = myPlayer.getPosX();
				int tmpPosY = myPlayer.getPosY();
				os.writeUTF("Player:" + myPlayerIndex + ";" + "Up" + ";"
						+ "Released" + "/" + tmpPosX + "/" + tmpPosY);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			return;
		}

		if (key == KeyEvent.VK_S) {
			try {
				int tmpPosX = myPlayer.getPosX();
				int tmpPosY = myPlayer.getPosY();
				os.writeUTF("Player:" + myPlayerIndex + ";" + "Down" + ";"
						+ "Released" + "/" + tmpPosX + "/" + tmpPosY);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			return;
		}

		if (key == KeyEvent.VK_A) {
			try {
				int tmpPosX = myPlayer.getPosX();
				int tmpPosY = myPlayer.getPosY();
				os.writeUTF("Player:" + myPlayerIndex + ";" + "Left" + ";"
						+ "Released" + "/" + tmpPosX + "/" + tmpPosY);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			return;
		}

		if (key == KeyEvent.VK_D) {
			try {
				int tmpPosX = myPlayer.getPosX();
				int tmpPosY = myPlayer.getPosY();
				os.writeUTF("Player:" + myPlayerIndex + ";" + "Right" + ";"
						+ "Released" + "/" + tmpPosX + "/" + tmpPosY);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

	}

	@Override
	public void initComponent() {
		/*
		 * load map and players
		 */
		multiplayerMap = new Map(mapName);
		playerOne = multiplayerMap.getPlayerByNumber(1);
		playerTwo = multiplayerMap.getPlayerByNumber(2);
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

	public void analizeIncoming(String incomingMsg) {
		if (incomingMsg.indexOf("Player:") != -1) {
			String[] parts = incomingMsg.split(":");
			int playerIndex = Integer.parseInt(parts[1].substring(0, 1));
			handlePlayerEvents(playerIndex, parts[1].substring(2));
			return;
		}
		if (incomingMsg.indexOf("Welcome Player ") != -1) {
			myPlayerIndex = Integer.parseInt(incomingMsg.substring(15, 16));
			myPlayer = multiplayerMap.getPlayerByNumber(myPlayerIndex);
			System.out.println(myPlayer);
			return;
		}
		if (incomingMsg.indexOf("Start!") != -1) {
			System.out.println("Success!");
			// initComponent();
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
			tmpPlayer.plantBomb(multiplayerMap.getCollisionMap());
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

	public class ReadFromHost extends Thread implements Runnable {

		private Socket toHostSocket;
		private DataOutputStream os;
		private DataInputStream is;
		private String incomingMsg = null;

		public ReadFromHost(Socket toHostSocket, DataOutputStream os,
				DataInputStream is) throws IOException, UnknownHostException {
			this.toHostSocket = toHostSocket;
			this.os = os;
			this.is = is;
			this.start();
		}

		public void run() {
			while (true) {
				try {
					incomingMsg = is.readUTF();
					System.out.println(incomingMsg);
					analizeIncoming(incomingMsg);
				} catch (IOException e) {
					System.out.println("Failed to read message");
				}
			}
		}
	}

}
