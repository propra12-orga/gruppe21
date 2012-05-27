package multiplayer;

import java.io.IOException;

public class Start_MP_Host {
	
	private Thread hostThread;

	public void main(String[] args) {
		try {
			hostThread = new Server(5555);
			hostThread.start();
		} catch(IOException e)
		{
			System.out.println("Sry, couldn't establish server");
		}
	}
}
