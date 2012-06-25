package multiplayer;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import main.GameConstants;
import main.GraphicalGameUnit;
import main.UnitNavigator;
import main.UnitState;
import map.Map;
import mapobjects.Player;

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
	private int myPlayerIndex;

	// Constructor
	public MultiplayerUnit() {
		// to do: hole dir die Adresse und evtl den Port vom Nutzer
		try {
			toHostSocket = new Socket(InetAddress.getLocalHost(), 5555);
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
		// to do: hole dir die Adresse und evtl den Port vom Nutzer
		try {
			toHostSocket = new Socket("127.0.0.1", 55555);
			os = new DataOutputStream(toHostSocket.getOutputStream());
			is = new DataInputStream(toHostSocket.getInputStream());
			fromHost = new ReadFromHost(toHostSocket, os, is);
		} catch (UnknownHostException e) {
			System.out.println("Could not reach the host");
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.mapName = mapName;
		initComponent();
	}

	@Override
	public void updateComponent() {
		// TODO Auto-generated method stub

	}

	@Override
	public void handleKeyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		/*
		 * Pause Game
		 */
		if (key == KeyEvent.VK_ESCAPE) {
			UnitNavigator.getNavigator().set(UnitState.BASE_MENU_UNIT);
		}
		/*
		 * playerOne KeyEvents
		 */
		if (key == KeyEvent.VK_W) {
			try {
				os.writeUTF("Player" + myPlayerIndex + ";" + "Up" + ";"
						+ "Pressed");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

		if (key == KeyEvent.VK_S) {
			try {
				os.writeUTF("Player" + myPlayerIndex + ";" + "Down" + ";"
						+ "Pressed");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

		if (key == KeyEvent.VK_A) {
			try {
				os.writeUTF("Player" + myPlayerIndex + ";" + "Left" + ";"
						+ "Pressed");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

		if (key == KeyEvent.VK_D) {
			try {
				os.writeUTF("Player" + myPlayerIndex + ";" + "Right" + ";"
						+ "Pressed");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

		if (key == KeyEvent.VK_SPACE) {
			try {
				os.writeUTF("Player" + myPlayerIndex + ";" + "Bomb");
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
				os.writeUTF("Player" + myPlayerIndex + ";" + "Up" + ";"
						+ "Released");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

		if (key == KeyEvent.VK_S) {
			try {
				os.writeUTF("Player" + myPlayerIndex + ";" + "Down" + ";"
						+ "Released");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

		if (key == KeyEvent.VK_A) {
			try {
				os.writeUTF("Player" + myPlayerIndex + ";" + "Left" + ";"
						+ "Released");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

		if (key == KeyEvent.VK_D) {
			try {
				os.writeUTF("Player" + myPlayerIndex + ";" + "Right" + ";"
						+ "Released");
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
		// TODO Auto-generated method stub

	}

	public void analizeIncoming(String incomingMsg) {
		if (incomingMsg.indexOf("Welcome Player ") != -1) {
			String[] parts = incomingMsg.split(":");
			myPlayerIndex = Integer.parseInt(parts[1]);
			return;
		}
		if (incomingMsg.indexOf("Start!") != -1)
			initComponent();
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
		}

		public void run() {
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
