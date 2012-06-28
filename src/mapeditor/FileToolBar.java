package mapeditor;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

import javax.swing.JPanel;

/**
 * Toolbar for file actions
 * 
 * displays all buttons for managing file actions
 * 
 * @author eik
 * 
 */
public class FileToolBar extends JPanel implements MouseListener {
	/**
	 * Buttons
	 */
	private Button newButton, saveButton, openButton;
	/**
	 * a Vector list for the buttons to manage drawing etc.
	 */
	private Vector<Button> buttonList = new Vector<Button>();

	private int buttonSize;
	private int padding;

	/**
	 * FileToolBar constructor
	 * 
	 * sets up the Jpanel, adds Buttons
	 */
	public FileToolBar(int bSize, int padd) {
		this.setBackground(Color.gray);
		this.setSize(200, 50);
		this.setDoubleBuffered(true);
		setFocusable(true);
		addMouseListener(this);
		buttonSize = bSize;
		padding = padd;

		buttonList.add(newButton = new Button(bSize, bSize, "new.png",
				"newFile"));
		buttonList.add(saveButton = new Button(bSize, bSize, "save.png",
				"saveFile"));
		buttonList.add(openButton = new Button(bSize, bSize, "open.png",
				"openFile"));
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
		// TODO Auto-generated method stub
		if (e.getButton() == MouseEvent.BUTTON1) {
			for (int i = 0; i < buttonList.size(); i++) {
				if (Mouse.isInRegion(e.getX(), e.getY(), buttonList.get(i)
						.getPosX(), buttonList.get(i).getPosY(), buttonList
						.get(i).getPosX() + buttonList.get(i).getWidth(),
						buttonList.get(i).getPosY()
								+ buttonList.get(i).getHeight())) {
					FileActions.perform(buttonList.get(i).getAction());
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
