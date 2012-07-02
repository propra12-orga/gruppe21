package multiplayer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Server extends Thread {

	public boolean gamestarted = false;
	// Host Socket
	private ServerSocket hostSocket = null;
	// Player Management
	private int maxPlayers;
	private int playerCount = 1;
	private String selectedMap;
	// Socket Management
	private ToClientSocket[] toClientSockets;
	// Upgrade Management
	private ArrayList<String> MPIDList = new ArrayList<String>();

	// Constructor
	public Server(int maxPlayers, String selectedMap, int port)
			throws IOException {
		this.maxPlayers = maxPlayers;
		this.selectedMap = selectedMap;
		toClientSockets = new ToClientSocket[maxPlayers + 1];
		hostSocket = new ServerSocket(port);
		this.start();
	}

	public int getPort() {
		return hostSocket.getLocalPort();
	}

	public String getMapName() {
		return selectedMap;
	}

	@Override
	public void run() {
		while (gamestarted == false) {
			try {
				toClientSockets[playerCount] = new ToClientSocket(playerCount,
						hostSocket.accept());
				playerCount += 1;
				if (playerCount == maxPlayers + 1)
					gamestarted = true;
			} catch (IOException e) {
				System.out.println("IOException");
				break;
			}
		}
	}

	private void distributeMessage(String incoming) {
		for (int i = 1; i < playerCount; i++) {
			try {
				System.out.println(incoming);
				Lock tmpLock = toClientSockets[i].getWriteLock();
				tmpLock.lock();
				toClientSockets[i].getOS().writeUTF(incoming);
				tmpLock.unlock();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void distributeMessage(int sendingPlayer, String incoming) {
		for (int i = 1; i < playerCount; i++) {
			if (!(i == sendingPlayer)) {
				try {
					System.out.println(incoming);
					Lock tmpLock = toClientSockets[i].getWriteLock();
					tmpLock.lock();
					toClientSockets[i].getOS().writeUTF(incoming);
					tmpLock.unlock();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void checkRelevance(int sendingPlayer, String incoming) {
		if (incoming.startsWith("!"))
			handleRelevantMsg(sendingPlayer, incoming);
		else {
			if (incoming.contains("starting"))
				gamestarted = true;

			distributeMessage(sendingPlayer, incoming);
		}
	}

	private synchronized void handleRelevantMsg(int sendingPlayer,
			String incoming) {
		if (incoming.indexOf("Upgrade:") != -1)
			if (incoming.indexOf("PickUp") != -1) {
				String[] parts = incoming.split(";");
				int listIndex = MPIDList.indexOf(parts[3]);
				if (listIndex != -1) {
					MPIDList.remove(listIndex);
					distributeMessage(incoming);
				}
			} else {
				String[] parts = incoming.split("/");
				MPIDList.add(parts[3]);
				distributeMessage(sendingPlayer, incoming);
			}
	}

	public class ToClientSocket extends Thread implements Runnable {
		private DataOutputStream os = null;
		private DataInputStream is = null;
		private int playerIndex;
		private Socket clientSocket;
		private Lock writeLock = new ReentrantLock();
		private boolean hasRemoved = true;

		// Constructor
		public ToClientSocket(int playerIndex, Socket clientSocket) {
			this.playerIndex = playerIndex;
			this.clientSocket = clientSocket;
			try {
				is = new DataInputStream(clientSocket.getInputStream());
				os = new DataOutputStream(clientSocket.getOutputStream());
			} catch (IOException e) {
				e.printStackTrace();
			}
			this.start();
			initializePlayerSlot();
		}

		public DataOutputStream getOS() {
			return os;
		}

		public Lock getWriteLock() {
			return writeLock;
		}

		public boolean isRemoved() {
			return hasRemoved;
		}

		public void setRemoved(boolean b) {
			hasRemoved = b;
		}

		private void initializePlayerSlot() {
			try {
				writeLock.lock();
				os.writeUTF("Welcome Player " + playerIndex);
				os.writeUTF("Map:" + getMapName());
				writeLock.unlock();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		public void run() {
			while (true) {
				try {
					String incoming = is.readUTF();
					checkRelevance(playerIndex, incoming);
				} catch (IOException e) {
					e.printStackTrace();
					try {
						Thread.sleep(10);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
				}
			}
		}
	}
}
