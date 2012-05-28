package multiplayer;

import java.io.IOException;

public class Start_MP_Host {

	private static Thread hostThread;

	public static void main(String[] args) {
		try {
			hostThread = new Server(5555);
			hostThread.start();
		} catch (IOException e) {
			System.out.println("Sry, couldn't establish server");
		}
	}
}
