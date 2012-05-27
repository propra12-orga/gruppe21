package multiplayer;

import java.io.IOException;
import java.net.InetAddress;

public class Start_MP_Client {
	private Thread clientThread;

	public void main(String[] args) {
		try {
			clientThread = new Client(InetAddress.getLocalHost(), 5555);
			clientThread.start();
		} catch (IOException e) {
			e.printStackTrace(); // wofür auch immer^^
		}
	}
}
