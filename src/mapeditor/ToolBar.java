package mapeditor;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

import javax.swing.JPanel;

public class ToolBar extends JPanel implements MouseListener {
	/**
	 * Buttons
	 */
	private Button pointerButton, smallButton, mediumButton, bigButton;
	/**
	 * a Vector list for the buttons to manage drawing etc.
	 */
	private Vector<Button> buttonList = new Vector<Button>();
	/**
	 * Variables for button size and spacing
	 */
	private int buttonSize;
	private int padding;

	MapCanvas mapCanvas = new MapCanvas();

	/**
	 * Constructor for Toolbar
	 * 
	 * @param bSize
	 *            sets the buttonsize
	 * @param padd
	 *            sets the button spacing
	 * @param can
	 *            a reference to the map canvas
	 */
	public ToolBar(int bSize, int padd, MapCanvas can) {
		this.setBackground(Color.gray);
		this.setSize(200, 50);
		this.setDoubleBuffered(true);
		setFocusable(true);
		addMouseListener(this);
		buttonSize = bSize;
		padding = padd;
		mapCanvas = can;

		buttonList.add(pointerButton = new Button(bSize, bSize, "pointer.png",
				"pointer"));
		buttonList.add(smallButton = new Button(bSize, bSize, "bsmall.png",
				"small"));
		buttonList.add(mediumButton = new Button(bSize, bSize, "bmedium.png",
				"medium"));
		buttonList.add(mediumButton = new Button(bSize, bSize, "bbig.png",
				"big"));
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (int i = 0; i < buttonList.size(); i++) {
			buttonList.get(i).draw((i * 50) + padding, 0 + padding, g);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {

		if (e.getButton() == MouseEvent.BUTTON1) {
			for (int i = 0; i < buttonList.size(); i++) {
				if (Mouse.isInRegion(e.getX(), e.getY(), buttonList.get(i)
						.getPosX(), buttonList.get(i).getPosY(), buttonList
						.get(i).getPosX() + buttonList.get(i).getWidth(),
						buttonList.get(i).getPosY()
								+ buttonList.get(i).getHeight())) {
				}
			}
		}

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
