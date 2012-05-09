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
	
	private volatile boolean running = false;
	
//	Referenz auf Navigator zum Zugriff auf die Verwaltungsoperationen für GraphicalGameUnits
	private UnitNavigator unitNavigator;

	
//	Hauptthread zum Aktualisieren des Spielgeschehens und dem Rendern der aktiven Komponente
	private Thread gameThread;
	
	public MainPanel() {		
		setBackground(Color.white);
		setPreferredSize(new Dimension(GameConstants.FRAME_SIZE_X, GameConstants.FRAME_SIZE_Y));
		setFocusable(true);		
//		Lausche auf KeyEvents zur Weiterleitung		
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				unitNavigator.getActiveUnit().handleKeyPressed(e);
			}
			@Override
			public void keyReleased(KeyEvent e) {
				unitNavigator.getActiveUnit().handleKeyReleased(e);
			}
		});		
		requestFocus();
		setDoubleBuffered(true);				
	}
	public void initGame() {
		unitNavigator = new UnitNavigator(this);
//		Füge Hauptmenu zur Liste der gespeicherten Einheiten hinzu
		MainMenu mainMenu = new MainMenu();
		mainMenu.setNavigator(unitNavigator);
		unitNavigator.addGameUnit(mainMenu, UnitState.BASE_MENU_UNIT);
		activateThread();
	}

	private void activateThread() {
		if (gameThread == null) {
			gameThread = new Thread(this);
		}
		this.start();		
		gameThread.start();		
	}

	public UnitNavigator getGameStateNavigator() {
		return unitNavigator;
	}

	@Override
	public void run() {		
//		Zeitmessung um FPS konstant zu halten
		long beforeTime, timeDiff, sleepTime;
		beforeTime = System.nanoTime();
			
//		"Endlosschleife" bis zur Beendigung des Spiels
		while (this.isRunning()) {
			unitNavigator.getActiveUnit().updateComponent();
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
		quitGame();
	}
	
//	Biete der aktuellen Spielkomponente 'Graphics'-Objekt
//	für das Rendering seiner Oberfläche
	@Override
	public void paint(Graphics g) {
		if (this.isRunning()) {
			unitNavigator.getActiveUnit().drawComponent(g);		
			Toolkit.getDefaultToolkit().sync();
			g.dispose();
		}
	}

//	Spiel beenden
	private void quitGame() {
		System.exit(0);
	}
		
	public void stop() {
		this.running = false;
	}
	
	public void start() {
		this.running = true;
	}
	
	public boolean isRunning() {
		if (running) {
			return true;
		}
		return false;
	}

	
	
}
