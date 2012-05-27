package multiplayer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class Server extends Thread {

	private boolean gamestarted = false;
	// Socket Classvars
	private ServerSocket hostSocket = null;
	private Socket toclientSocket = null;
	private DataOutputStream os = null;
	private DataInputStream is = null;
	// Player Array
	private int[] Player = new int[2]; // not needed yet since I'm trying to
										// make 2 Player MP work to begin
										// with...

	// Constructor
	public Server(int port) throws IOException {
		hostSocket = new ServerSocket(port);
		/* hostSocket.setSoTimeout(10000); */// not sure if needed
	}

	@Override
	public void run() {
		while (gamestarted == false) {
			try {
				toclientSocket = hostSocket.accept();
				is = new DataInputStream(toclientSocket.getInputStream());
				os = new DataOutputStream(toclientSocket.getOutputStream());
				System.out.println(is.readUTF());
				os.writeUTF("ohai!");
			} catch (SocketTimeoutException s) {
				System.out.println("Socket T/O");
				break;
			} catch (IOException e) {
				System.out.println("IOException");
				break;
			}
		}
	}

}
