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
	Button newButton, saveButton, openButton;
	/**
	 * a Vector list for the buttons to manage drawing etc.
	 */
	Vector<Button> buttonList = new Vector<Button>();

	/**
	 * FileToolBar constructor
	 * 
	 * sets up the Jpanel, adds Buttons
	 */
	public FileToolBar() {
		this.setBackground(Color.gray);
		this.setSize(200, 50);
		this.setDoubleBuffered(true);
		setFocusable(true);
		addMouseListener(this);

		buttonList.add(newButton = new Button(50, 50, "new.png"));
		buttonList.add(saveButton = new Button(50, 50, "save.png"));
		buttonList.add(openButton = new Button(50, 50, "open.png"));
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (int i = 0; i < buttonList.size(); i++) {
			buttonList.get(i).draw((i * 50), 0, g);
		}

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		System.out.print("FileToolbar clicked");
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
