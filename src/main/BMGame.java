package main;

import javax.swing.JFrame;

/*
 * launcher with main()
 */

public class BMGame {

	/*
	 * main frame for the application
	 */
	private final JFrame mainFrame;
	
	/*
	 * initialize frame and add main panel
	 */
	public BMGame() {
		mainFrame = new JFrame("Gruppe 21 - Bomberman");
		mainFrame.setSize(GameConstants.FRAME_SIZE_X, GameConstants.FRAME_SIZE_Y);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setResizable(false);
		mainFrame.setVisible(true);
		MainPanel mainPanel = new MainPanel();
		mainPanel.initGame();
		mainFrame.add(mainPanel);		
	}	

	public static void main(String[] args) {    	
		new BMGame();
	}

}
