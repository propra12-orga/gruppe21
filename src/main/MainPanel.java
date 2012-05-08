package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JPanel;

//	Spieloberfläche und Hauptthread für das
// 	Rendering der grafischen Darstellung und die Aktualisierung
//	der jeweils aktiven Spielkomponente. Nimmt außerdem KeyEvents
//	entgegen und leitet sie an die aktive Spielkomponente weiter.

public class MainPanel extends JPanel implements Runnable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3212988448230980893L;
	
//	Referenz auf Hauptspielklasse zum Zugriff auf die Verwaltungsoperationen der Spielzustände
	private final BMGame game;
	
//	Hauptthread zum Aktualisieren des Spielgeschehens und dem Rendern der aktiven Komponente
	private Thread gameThread;
	
	public MainPanel(BMGame bmGame) {
		this.game = bmGame;		
		setBackground(Color.white);
		setPreferredSize(new Dimension(GameConstants.FRAME_SIZE_X, GameConstants.FRAME_SIZE_Y));
		setFocusable(true);
		
//		Lausche auf KeyEvents zur Weiterleitung		
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				game.getActiveComponent().handleKeyPressed(e);
			}
			@Override
			public void keyReleased(KeyEvent e) {
				game.getActiveComponent().handleKeyReleased(e);
			}
		});
		
//		Füge Hauptmenu zur Liste der gespeicherten Spielkomponenten hinzu
		MainMenu mainMenu = new MainMenu();
		mainMenu.setGame(game);
		game.addGameComponent(mainMenu, ComponentState.BASE_MENU_COMPONENT);
		requestFocus();
		setDoubleBuffered(true);				
		activateThread();
	}

	private void activateThread() {
		if (gameThread == null) {
			gameThread = new Thread(this);
		}
		gameThread.start();		
	}
	
	public BMGame getGame() {
		return game;
	}

	@Override
	public void run() {
		game.setRunning(true);
		
//		Zeitmessung um FPS konstant zu halten
		long beforeTime, timeDiff, sleepTime;
		beforeTime = System.nanoTime();
			
//		"Endlosschleife" bis zur Beendigung des Spiels
		while (getGame().isRunning()) {
			game.getActiveComponent().updateComponent();
			repaint();
					
			timeDiff = System.nanoTime() - beforeTime;
			sleepTime = GameConstants.ITERATION_TIME - timeDiff;
			
			if (sleepTime < 0)
				sleepTime = 50;
			try {
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
				System.out.println("interrupted");
			}
			beforeTime = System.nanoTime();
		}
		game.quitGame();
	}
	
//	Biete der aktuellen Spielkomponente 'Graphics'-Objekt
//	für das Rendering seiner Oberfläche
	@Override
	public void paint(Graphics g) {
		if (getGame().isRunning()) {
			game.getActiveComponent().drawComponent(g);		
			Toolkit.getDefaultToolkit().sync();
			g.dispose();
		}
	}

}
