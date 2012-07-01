package mapeditor;

import imageloader.ImageLoader;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;

/**
 * Main Class for the Level Editor
 * 
 * 
 * @author eik
 * 
 */
public class Editor extends JFrame implements KeyListener, MouseListener {
	/**
	 * Editor Components
	 */
	public ImageLoader imageStack = new ImageLoader();
	private MapCanvas canvas = new MapCanvas();
	private FileToolBar fileBar = new FileToolBar(48, 1);
	private ToolBar toolBar = new ToolBar(48, 1, canvas);
	private TileNavigator tileNavigator = new TileNavigator();
	/**
	 * status of the map changes
	 */
	Boolean mapChanged = false;

	/**
	 * Constructor
	 * 
	 * sets up the window adds Components
	 */

	public Editor() {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		setSize(800, 600);
		setFocusable(true);
		setResizable(false);
		setLocationRelativeTo(null);
		setTitle("Mapeditor Bomberman Island");
		setVisible(true);
		addKeyListener(this);
		addMouseListener(this);

		fileBar.setBounds(0, 0, 200, 50);
		toolBar.setBounds(200, 0, 400, 50);
		canvas.setBounds(0, 50, 600, 550);
		tileNavigator.setBounds(600, 0, 200, 300);
		add(fileBar);
		add(toolBar);
		add(canvas);
		add(tileNavigator);
	}

	public static void main(String args[]) {
		new Editor();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}
}
