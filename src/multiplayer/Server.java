package multiplayer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Server extends Thread {

	public static boolean gamestarted = false;
	// Socket Classvars
	private ServerSocket hostSocket = null;
	private DataOutputStream os = null;
	private DataInputStream is = null;
	// Player Array
	private int playerCount = 1;
	// Socket Management
	private ToClientSocket[] toClientSockets = new ToClientSocket[2]; // Change

	// Constructor
	public Server(int port) throws IOException {
		hostSocket = new ServerSocket(port);
		/* hostSocket.setSoTimeout(10000); */// not sure if needed
		this.start();
	}

	@Override
	public void run() {
		while (gamestarted == false) {
			try {
				toClientSockets[playerCount] = new ToClientSocket(playerCount,
						hostSocket.accept());
				playerCount += 1;
				if (playerCount == 2) { // Change
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
		while (true) {
			try {
				Thread.sleep(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void distributeMessage(String incoming) {
		for (int i = 1; i < 2; i++) { // Change
			try {
				ToClientSocket tmp = toClientSockets[i];
				Lock tmpLock = tmp.getToClientWriteLock();
				tmpLock.lock();
				tmp.getToClientSocketOS().writeUTF(incoming);
				tmpLock.unlock();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		incoming = null;
	}

	// evtl stoppe den Thread, nachdem das Spiel startet

	public class ToClientSocket extends Thread implements Runnable {
		private int playerIndex;
		private Socket clientSocket;
		protected String incoming = null;
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

		public DataOutputStream getToClientSocketOS() {
			return os;
		}

		public Lock getToClientWriteLock() {
			return writeLock;
		}

		private void initializePlayerSlot() {
			try {
				writeLock.lock();
				os.writeUTF("Welcome Player " + playerIndex);
				writeLock.unlock();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		public void run() {
			while (gamestarted == true) {
				try {
					incoming = is.readUTF();
					distributeMessage(incoming);
				} catch (IOException e) {
					e.printStackTrace();
				}
				try {
					Thread.sleep(2);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
