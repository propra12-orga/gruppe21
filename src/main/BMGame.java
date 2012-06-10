package main;

import javax.swing.JFrame;

/**
 * Contains main frame for the application and the main method.
 * 
 * @author tohei
 * 
 */
public class BMGame {

	/*
	 * main frame for the application
	 */
	private final JFrame mainFrame;

	/**
	 * Initializes Game-Frame and adds main panel.
	 */
	public BMGame() {
		mainFrame = new JFrame("Gruppe 21 - Bomberman");
		mainFrame.setSize(GameConstants.FRAME_SIZE_X,
				GameConstants.FRAME_SIZE_Y);
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
