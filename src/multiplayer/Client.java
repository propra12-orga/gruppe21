package multiplayer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client extends Thread {

	private Socket tohostSocket;
	private DataOutputStream os;
	private DataInputStream is;

	public Client(InetAddress hostAddress, int port) throws IOException,
			UnknownHostException {
		tohostSocket = new Socket(hostAddress, port);
		os = new DataOutputStream(tohostSocket.getOutputStream());
		is = new DataInputStream(tohostSocket.getInputStream());
	}

	public void run() {
		try {
			os.writeUTF("hi!");
		} catch (IOException e) {
			System.out.println("Failed to send message");
		}
		String handshakemsg = null;
		try {
			handshakemsg = is.readUTF();
			if (handshakemsg.indexOf("ohai!") != -1)
				System.out.println("Handshake successfull!");
		} catch (IOException e) {
			System.out.println("Failed to read input");
		}
	}
}
