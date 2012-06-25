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
	public Object[] toClientSockets = new Object[3];

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
				toClientSockets[playerCount] = new toClientSocket(playerCount,
						hostSocket.accept());
				playerCount += 1;
				if (playerCount == 2)
					gamestarted = true;
				// Initialisiere den Krieg!
			} catch (SocketTimeoutException s) {
				System.out.println("Socket T/O");
				break;
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

	public class toClientSocket extends Thread implements Runnable {
		private int playerIndex;
		private Socket clientSocket;
		private String incoming = null;
		private Lock writeLock = new ReentrantLock();

		// Constructor
		public toClientSocket(int playerIndex, Socket clientSocket) {
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
				os.writeUTF("Welcome Player:" + playerIndex);
				writeLock.unlock();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		public void run() {
			while (gamestarted == true) {
				try {
					incoming = is.readUTF();
					for (int i = 1; i < 3; i++) {
						toClientSocket tmp = (toClientSocket) toClientSockets[i];
						Lock tmpLock = tmp.getToClientWriteLock();
						tmpLock.lock();
						tmp.getToClientSocketOS().writeUTF(incoming);
						tmpLock.unlock();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
