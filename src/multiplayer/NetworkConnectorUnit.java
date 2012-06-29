package multiplayer;

import imageloader.GameGraphic;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.net.Socket;

import main.GameConstants;
import main.GraphicalGameUnit;
import main.UnitNavigator;
import main.UnitState;
import multiplayer.MultiplayerUnit.SocketListener;

public class NetworkConnectorUnit extends GraphicalGameUnit implements
		SocketListener {

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

	private boolean failed;
	private boolean connectionFailed = false;

	public NetworkConnectorUnit(String ip, int port, boolean asHost) {
		this.asHost = asHost;
		this.ip = ip;
		this.port = port;
		if (asHost)
			msg = statusMsg1;
		else
			msg = statusMsg2;
	}

	@Override
	public void analizeIncoming(String input) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateComponent() {
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
			unitFont = loadFont("font1.TTF").deriveFont(50f);
		} catch (Exception e) {
			System.err.println("ERROR LOADING FONT: font1.TTF");
			e.printStackTrace();
			unitFont = new Font("serif", Font.PLAIN, 24);
		}
	}

	@Override
	public void drawComponent(Graphics g) {
		g.setColor(Color.black);
		g.drawImage(background.getImage(), 0, 0, GameConstants.FRAME_SIZE_X,
				GameConstants.FRAME_SIZE_Y, null);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setFont(unitFont);

		Rectangle2D rect = unitFont.getStringBounds(msg,
				g2d.getFontRenderContext());
		g2d.drawString(msg,
				(int) (GameConstants.FRAME_SIZE_X - rect.getWidth()) / 2,
				(int) (GameConstants.FRAME_SIZE_Y / 2 - rect.getHeight()));

	}

}
