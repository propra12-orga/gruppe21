package main;

import javax.swing.JFrame;

//	Launcher mit main()

public class BMGame {

//	Basisframe der Applikation
	private final JFrame mainFrame;
	
//	Konstruktor - Initialisierung des Basisframes und Hinzufügen des MainPanels
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

//	Startet das Spiel
	public static void main(String[] args) {    	
		new BMGame();
	}

}
