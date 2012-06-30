package multiplayer;

import imageloader.GameGraphic;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import main.GameConstants;
import main.GraphicalGameUnit;
import main.UnitNavigator;
import main.UnitState;

public class NetworkConnectorUnit extends GraphicalGameUnit implements
		multiplayer.ReadFromHost.SocketListener {

	private String ip;
	private int port;
	private Socket clientSocket;
	private GameGraphic background;
	private String msg;
	private boolean asHost;
	private final String errorMsg1 = "Failed to set up Server! Press Enter";
	private final String errorMsg2 = "Failed to connect to Server! Press Enter";
	private final String statusMsg1 = "loading ...";
	private final String statusMsg2 = "connecting ...";

	private boolean connectionFailed = false;

	private ReadFromHost fromHost = null;
	private Socket toHostSocket = null;
	private DataOutputStream os = null;
	private DataInputStream is = null;

	private long timeOutPeriod = 10000000000L;
	private long startTime;

	private int playerIndex;
	private String mapName;
	private boolean gotMapName = false;
	private boolean gotPlayerIndex = false;

	public NetworkConnectorUnit(String ip, int port, boolean asHost) {
		this.asHost = asHost;
		this.ip = ip;
		this.port = port;
		startTime = System.nanoTime();
		if (asHost)
			msg = statusMsg1;
		else
			msg = statusMsg2;
		initComponent();
	}

	@Override
	public void analizeIncoming(String incomingMsg) {
		if (incomingMsg.contains("Map")) {
			String[] parts = incomingMsg.split(":");
			mapName = parts[1];
			gotPlayerIndex = true;
			return;
		}
		if (incomingMsg.contains("Welcome Player")) {
			playerIndex = Integer.parseInt(incomingMsg.substring(15, 16));
			gotMapName = true;
			return;
		}
	}

	@Override
	public void updateComponent() {
		if (gotPlayerIndex && gotMapName) {
			if (asHost) {
				MPLoungeUnit lounge = new MPLoungeUnit(fromHost, toHostSocket,
						playerIndex, mapName, true);
				UnitNavigator.getNavigator().addGameUnit(lounge,
						UnitState.TEMPORARY_UNIT);
				UnitNavigator.getNavigator().set(UnitState.TEMPORARY_UNIT);
			} else {
				MPLoungeUnit lounge = new MPLoungeUnit(fromHost, toHostSocket,
						playerIndex, mapName, false);
				UnitNavigator.getNavigator().addGameUnit(lounge,
						UnitState.TEMPORARY_UNIT);
				UnitNavigator.getNavigator().set(UnitState.TEMPORARY_UNIT);
			}
		}
		if (startTime + timeOutPeriod < System.nanoTime()) {
			connectionFailed = true;
		}
		if (connectionFailed) {
			if (asHost) {
				msg = errorMsg1;
			} else {
				msg = errorMsg2;
			}
		}

	}

	@Override
	public void handleKeyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_ENTER && connectionFailed) {
			UnitNavigator.getNavigator().set(UnitState.BASE_MENU_UNIT);
		}
		if (key == KeyEvent.VK_ESCAPE) {
			UnitNavigator.getNavigator().set(UnitState.BASE_MENU_UNIT);
		}

	}

	@Override
	public void handleKeyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void initComponent() {
		try {
			unitFont = loadFont("font1.TTF").deriveFont(30f);
		} catch (Exception e) {
			System.err.println("ERROR LOADING FONT: font1.TTF");
			e.printStackTrace();
			unitFont = new Font("serif", Font.PLAIN, 24);
		}
		background = new GameGraphic(GameConstants.MENU_IMAGES_DIR
				+ "/MultiplayerMenuBG.png");

		try {
			toHostSocket = new Socket(ip, port);
			os = new DataOutputStream(toHostSocket.getOutputStream());
			is = new DataInputStream(toHostSocket.getInputStream());
			fromHost = new ReadFromHost(toHostSocket, os, is, this);
		} catch (UnknownHostException e) {
			System.err.println("Could not reach the host");
			connectionFailed = true;
		} catch (IOException e) {
			System.err
					.println("Received IOException trying to connect to Server.");
			connectionFailed = true;
		}

	}

	@Override
	public void drawComponent(Graphics g) {
		g.drawImage(background.getImage(), 0, 0, GameConstants.FRAME_SIZE_X,
				GameConstants.FRAME_SIZE_Y, null);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.white);
		g2d.setFont(unitFont);

		Rectangle2D rect = unitFont.getStringBounds(msg,
				g2d.getFontRenderContext());
		g2d.drawString(msg,
				(int) (GameConstants.FRAME_SIZE_X - rect.getWidth()) / 2,
				(int) (GameConstants.FRAME_SIZE_Y / 2));

	}

}
