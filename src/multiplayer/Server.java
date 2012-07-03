package multiplayer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * The Server class listens for incoming connections and holds the method for
 * sending messages to the clients
 * 
 * @author Dorian
 * 
 */
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

	/**
	 * Creates a Server Thread to listen for incoming connections
	 * 
	 * @param maxPlayers
	 * @param selectedMap
	 * @param port
	 * @throws IOException
	 */
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
		try {
			hostSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Sends the given string object to all sockets
	 * 
	 * @param incoming
	 */
	private void distributeMessage(String incoming) {
		for (int i = 1; i < playerCount; i++) {
			try {
				Lock tmpLock = toClientSockets[i].getWriteLock();
				tmpLock.lock();
				toClientSockets[i].getOS().writeUTF(incoming);
				tmpLock.unlock();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Sends the given string object to all sockets but the socket with the
	 * "sendingPlayer" index
	 * 
	 * @param sendingPlayer
	 * @param incoming
	 */
	private void distributeMessage(int sendingPlayer, String incoming) {
		for (int i = 1; i < playerCount; i++) {
			if (!(i == sendingPlayer)) {
				try {
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

	/**
	 * Determines if the incoming message needs a reaction from the server
	 * 
	 * @param sendingPlayer
	 * @param incoming
	 */
	public void checkRelevance(int sendingPlayer, String incoming) {
		if (incoming.startsWith("!"))
			handleRelevantMsg(sendingPlayer, incoming);
		else {
			if (incoming.contains("starting"))
				gamestarted = true;

			distributeMessage(sendingPlayer, incoming);
		}
	}

	/**
	 * Handles messages which contain upgrade-information and if necessary
	 * supresses the message
	 * 
	 * @param sendingPlayer
	 * @param incoming
	 */
	private void handleRelevantMsg(int sendingPlayer, String incoming) {
		if (incoming.indexOf("Upgrade:") != -1) {
			handleUpgradeMsg(sendingPlayer, incoming);
			return;
		}
		if (incoming.contains("stop")) {
			distributeMessage("stop");
			return;
		}
		if (incoming.contains("close remote")) {
			toClientSockets[sendingPlayer].terminate();
		}
	}

	private synchronized void handleUpgradeMsg(int sendingPlayer,
			String incoming) {
		if (incoming.indexOf("PickUp") != -1) {
			String[] parts = incoming.split(";");
			int listIndex = MPIDList.indexOf(parts[2]);
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

	/**
	 * This thread holds the socket to a client and reads on it
	 * 
	 * @author Dorian
	 * 
	 */
	public class ToClientSocket extends Thread implements Runnable {
		private DataOutputStream os = null;
		private DataInputStream is = null;
		private int playerIndex;
		private Socket clientSocket;
		private Lock writeLock = new ReentrantLock();
		private boolean hasRemoved = true;
		private boolean stopped = false;

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

		public void terminate() {
			stopped = true;
			try {
				clientSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		public void run() {
			while (!stopped) {
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
