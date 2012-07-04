package multiplayer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

/**
 * This is the Thread attached to the Client which listens for incoming messages
 * from the server
 * 
 * @author Dorian
 * 
 */
public class ReadFromHost extends Thread implements Runnable {

	private SocketListener listener;
	private Socket toHostSocket;
	private DataOutputStream os;
	private DataInputStream is;
	private String incomingMsg = null;
	private boolean stopped = false;

	/**
	 * Create a Thread which reads on a DataInputStream and passes the incoming
	 * Message to a specified listener
	 * 
	 * @param toHostSocket
	 * @param attached
	 *            DataOutputStream
	 * @param attached
	 *            DataInputStream
	 * @param listener
	 * @throws IOException
	 * @throws UnknownHostException
	 */
	public ReadFromHost(Socket toHostSocket, DataOutputStream os,
			DataInputStream is, SocketListener listener) throws IOException,
			UnknownHostException {
		this.toHostSocket = toHostSocket;
		toHostSocket.setSoTimeout(7000);
		this.listener = listener;
		this.os = os;
		this.is = is;
		this.start();
	}

	public void run() {
		while (!stopped) {
			try {
				incomingMsg = is.readUTF();
				listener.analizeIncoming(incomingMsg);
			} catch (SocketTimeoutException e1) {
			} catch (IOException e) {
				System.out.println("Connection to host lost!");
				listener.disconnectRecognized();
				terminate();
			}
		}
	}

	public void terminate() {
		stopped = true;
		try {
			toHostSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setListener(SocketListener socketListener) {
		this.listener = socketListener;
	}

	public interface SocketListener {
		public void analizeIncoming(String input);

		public void disconnectRecognized();
	}

}