package multiplayer;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
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

	public NetworkConnectorUnit(String ip, int port) {
		this.ip = ip;
		this.port = port;
	}

	@Override
	public void analizeIncoming(String input) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateComponent() {
		// TODO Auto-generated method stub

	}

	@Override
	public void handleKeyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_ESCAPE) {
			System.out.println("TERMINATED");
			UnitNavigator.getNavigator().set(UnitState.BASE_MENU_UNIT);
		}

	}

	@Override
	public void handleKeyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void initComponent() {
		// TODO Auto-generated method stub

	}

	@Override
	public void drawComponent(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(0, 0, GameConstants.FRAME_SIZE_X, GameConstants.FRAME_SIZE_Y);

	}

}
