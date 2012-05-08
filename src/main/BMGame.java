package main;

import javax.swing.JFrame;

//	Hauptklasse mit main(). Enthält Methoden für den
//	Zugriff auf die Spielzustände und zum Beenden des Spiels.

public class BMGame {

	private volatile boolean running = false;
	
//	Basisframe der Applikation
	private final JFrame mainFrame;
	
//	Array für die momentan gespeicherten Spielkomponenten (Menü/LevelManager/Temporäre Komponenten)
	private GraphicalGameComponent[] gameComponents = new GraphicalGameComponent[GameConstants.NUM_OF_GAME_STATES];
	
//	Zeiger auf das jeweils aktive Feld von 'gameComponents'
	private int activeComponent;
	
//	Konstruktor - Initialisierung des Basisframes und Hinzufügen des MainPanels
	public BMGame() {
		mainFrame = new JFrame("Gruppe 21 - Bomberman");
		mainFrame.setSize(GameConstants.FRAME_SIZE_X, GameConstants.FRAME_SIZE_Y);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setResizable(false);
		mainFrame.setVisible(true);
		mainFrame.add(new MainPanel(this));
	}	

//	Spiel beenden
	public void quitGame() {
		mainFrame.dispose();
	}

//	Liefert aktive Spielkomponente 
	public GraphicalGameComponent getActiveComponent() {
		return gameComponents[activeComponent];		
	}
	
//	Ändert bzw fügt neue Spielkomponente am angegebenen Index (state) in gameComponents ein
	public void addGameComponent(GraphicalGameComponent newComponent, ComponentState state) {
		gameComponents[state.getValue()] = newComponent;
	}
	
//	Setzt den aktiven Spielzustand (und damit die aktive Spielkomponente)
	public void setGameState(ComponentState newGameState) {
		activeComponent = newGameState.getValue();
	}
	
//	Startet das Spiel
	public static void main(String[] args) {    	
		new BMGame();
	}
	
	public void setRunning(boolean running) {
		this.running = running;
	}
	
	public boolean isRunning() {
		if (running) {
			return true;
		}
		return false;
	}
	
}
