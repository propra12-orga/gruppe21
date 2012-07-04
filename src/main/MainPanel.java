package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;

/**
 * Serves as a canvas for the active GraphicalGameUnit. All KeyEvents are
 * forwarded to the game unit in use.
 * 
 * @author tohei
 */
public class MainPanel extends JPanel implements Runnable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3212988448230980893L;

	/**
	 * As long as this flag remains true, the MainPanel keeps updating and
	 * redrawing the active GraphicalGameUnit
	 */
	private boolean running = false;

	/**
	 * Minimum amount of time of one iteration of the game-update-loop (in
	 * milliseconds). If the duration of an update process surpasses
	 * ITERATION_TIME, the game-update-thread will be put to sleep for the
	 * duration specified in STANDARD_SLEEP, otherwise the sleep time is
	 * computed by subtracting the update period from ITERATION_TIME.
	 */
	public static final int ITERATION_TIME = 11;
	/**
	 * If it takes longer to draw and update the active GraphicalGameUnit than
	 * specified in ITERATION_TIME, the update-thread is being put to sleep for
	 * as long as determined in STANDARD_SLEEP (in milliseconds).
	 */
	public static final int STANDARD_SLEEP = 4;

	/**
	 * Main thread for updating and rendering the active GraphicalGameUnit.
	 */
	private Thread gameThread;

	private boolean timeMeasurementActivated = false;;
	private int timeCounter = 0;
	private long timeMeasured = 0;

	private final int MAX_TIME_COUNT = 1000;

	/**
	 * Set up MainPanel and add a KeyListener.
	 */
	public MainPanel() {
		setBackground(Color.white);
		setSize(GameConstants.FRAME_SIZE_X, GameConstants.FRAME_SIZE_Y);
		setFocusable(true);
		/*
		 * wait for KeyEvents to be propagated to the active game unit
		 */
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_F5) {
					timeMeasurementActivated = true;
				}
				if (e.getKeyCode() == KeyEvent.VK_UP)
					System.out.print(""); // this somehow helps avoiding nasty
											// keyListener bugs
				UnitNavigator.getNavigator().getActiveUnit()
						.handleKeyPressed(e);
			}

			@Override
			public void keyReleased(KeyEvent e) {
				UnitNavigator.getNavigator().getActiveUnit()
						.handleKeyReleased(e);
			}
		});
		requestFocusInWindow();
		setDoubleBuffered(true);
	}

	/**
	 * Creates a new MainMenuUnit and hands it to the UnitNavigator.
	 */
	public void initGame() {
		/*
		 * add main menu
		 */
		MainMenuUnit mainMenu = new MainMenuUnit();
		UnitNavigator.getNavigator().addGameUnit(mainMenu,
				UnitState.BASE_MENU_UNIT);
		activateThread();
	}

	/**
	 * Starts a new thread for the rendering and update process.
	 */
	private void activateThread() {
		if (gameThread == null) {
			gameThread = new Thread(this);
		}
		running = true;
		gameThread.start();
	}

	/**
	 * Contains "infinite" loop that updates and renders the active
	 * GraphicalGameUnit determined by the UnitNavigator.
	 * 
	 * To maintain relatively constant frames per second, it captures the amount
	 * of time required and proceeds by dynamically calculating the sleeping
	 * time. Checks if a GraphicalGameUnit has requested termination of the
	 * update-loop (i.e. the user selected 'quit' in the MainMenu) and if so,
	 * sets running to false.
	 */
	@Override
	public void run() {
		/*
		 * some time measurement to maintain constant fps
		 */
		long beforeTime, timeDiff, sleepTime;
		beforeTime = System.nanoTime();

		/*
		 * infinite loop for updating and drawing the selected game unit
		 */
		while (running) {
			if (UnitNavigator.getNavigator().terminationRequested()) {
				running = false;
			}
			requestFocusInWindow();

			long time = System.nanoTime();
			UnitNavigator.getNavigator().getActiveUnit().updateComponent();
			if (timeMeasurementActivated) {
				timeMeasured += (System.nanoTime() - time) / 1000;
				timeCounter++;
				if (timeCounter == MAX_TIME_COUNT) {
					timeMeasurementActivated = false;
					System.out
							.println("Durchschnitt der benötigten Zeit für die letzten "
									+ MAX_TIME_COUNT
									+ " Updatezyklen: "
									+ (timeMeasured / MAX_TIME_COUNT)
									+ " Mikrosekunden.");
					timeMeasured = 0;
					timeCounter = 0;
				}
			}
			repaint();
			timeDiff = System.nanoTime() - beforeTime;
			sleepTime = ITERATION_TIME - timeDiff / 1000000L;
			if (sleepTime < 0)
				sleepTime = STANDARD_SLEEP;
			try {
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
				System.out.println("interrupted");
			}
			// System.out.println(sleepTime);
			beforeTime = System.nanoTime();
		}
		quitGame();
	}

	/**
	 * Will be called periodically by the update thread. Redraws the active
	 * GraphicalGameUnit.
	 */
	@Override
	public void paint(Graphics g) {
		if (running) {
			UnitNavigator.getNavigator().getActiveUnit().drawComponent(g);
			Toolkit.getDefaultToolkit().sync();
			g.dispose();
		}
	}

	/**
	 * Leads to System.exit(0).
	 */
	private void quitGame() {
		System.exit(0);
	}
}
