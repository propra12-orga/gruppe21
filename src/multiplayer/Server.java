package multiplayer;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Scanner;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Server extends Thread {

	public static boolean gamestarted = false;
	// Host Socket
	private ServerSocket hostSocket = null;
	// Player Management
	private int maxPlayers;
	private int playerCount = 1;
	private String selectedMap;
	// Socket Management
	private ToClientSocket[] toClientSockets;
	// Console Input
	private Scanner scanner = new Scanner(new BufferedInputStream(System.in),
			"UTF-8");

	// Constructor
	public Server(int maxPlayers, String selectedMap, int port)
			throws IOException {
		this.maxPlayers = maxPlayers;
		this.selectedMap = selectedMap;
		toClientSockets = new ToClientSocket[maxPlayers + 1];
		hostSocket = new ServerSocket(port);
		/* hostSocket.setSoTimeout(10000); */// not sure if needed
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
				if (playerCount == maxPlayers + 1) {
					gamestarted = true;
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					distributeMessage("Start!");
				}
			} catch (SocketTimeoutException s) {
				System.out.println("Socket T/O");
				break;
			} catch (IOException e) {
				System.out.println("IOException");
				break;
			}
		}
		System.out.println("Bomberman Server v1.0:");
		String consoleInput = null;
		while (true) {
			consoleInput = scanner.next();
			if (consoleInput.equals("stop")) // schliesse Sockets
				break;
		}
	}

	public void distributeMessage(String incoming) {
		for (int i = 1; i <= maxPlayers; i++) {
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

	public void distributeMessage(int sendingPlayer, String incoming) {
		for (int i = 1; i <= maxPlayers; i++) {
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

	// evtl stoppe den Thread, nachdem das Spiel startet

	public class ToClientSocket extends Thread implements Runnable {
		private DataOutputStream os = null;
		private DataInputStream is = null;
		private int playerIndex;
		private Socket clientSocket;
		private Lock writeLock = new ReentrantLock();

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

		private void initializePlayerSlot() {
			try {
				writeLock.lock();
				os.writeUTF("Welcome Player " + playerIndex);
				os.writeUTF("Map: " + getMapName());
				writeLock.unlock();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		public void run() {
			while (true) {
				try {
					String incoming = is.readUTF();
					distributeMessage(playerIndex, incoming);
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
