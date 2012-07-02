package multiplayer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class ReadFromHost extends Thread implements Runnable {

	private SocketListener listener;
	private Socket toHostSocket;
	private DataOutputStream os;
	private DataInputStream is;
	private String incomingMsg = null;

	public ReadFromHost(Socket toHostSocket, DataOutputStream os,
			DataInputStream is, SocketListener listener) throws IOException,
			UnknownHostException {
		this.toHostSocket = toHostSocket;
		this.listener = listener;
		this.os = os;
		this.is = is;
		this.start();
	}

	public void run() {
		while (true) {
			try {
				incomingMsg = is.readUTF();
				listener.analizeIncoming(incomingMsg);
			} catch (IOException e) {
				System.out.println("Connection to host lost!");
				try {
					toHostSocket.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				break;
			}
		}
	}

	public void setListener(SocketListener socketListener) {
		this.listener = socketListener;
	}

	public interface SocketListener {
		public void analizeIncoming(String input);
	}

}