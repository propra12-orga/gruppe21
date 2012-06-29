package multiplayer;

import java.io.IOException;

public class Start_MP_Host {

	private static Thread hostThread;

	public static void main(String[] args) {
		try {
			hostThread = new Server(2, "MP-Woodwars", 5555);
		} catch (IOException e) {
			System.out.println("Sry, couldn't establish server");
		}
	}
}
