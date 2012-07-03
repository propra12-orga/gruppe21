package mapeditor;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class MapCanvas extends JPanel implements MouseListener {

	private String mode = "pointer";
	public EditorMap editorMap = null;
	private boolean xOverflow;
	private boolean yOverflow;
	private Editor editor;

	public MapCanvas(Editor ed) {
		this.setBackground(Color.gray);
		this.setSize(550, 600);
		this.setDoubleBuffered(true);
		this.addMouseListener(this);

		this.editor = ed;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;

		if (editorMap != null) {
			editorMap.draw(g2d);
		}

	}

	public void setMode(String mo) {
		this.mode = mo;
		System.out.println(mode);
	}

	public String getMode() {
		return this.mode;
	}

	public void openNew() {
		int newx = Integer.parseInt(JOptionPane
				.showInputDialog("Horizontal tiles ?"));
		int newy = Integer.parseInt(JOptionPane
				.showInputDialog("Vertical tiles ?"));
		if (newx * 50 > 600) {
			xOverflow = true;
		}
		if (newy * 50 > 550) {
			yOverflow = true;
		}
		editorMap = new EditorMap(newx, newy);
		repaint();
	}

	@Override
	public void mouseClicked(MouseEvent e) {

		if (e.getButton() == MouseEvent.BUTTON1) {
			if (mode.equals("small") || mode.equals("medium")
					|| mode.equals("large")) {
				editorMap.addTile(e.getX(), e.getY(),
						editor.tileViewer.getDrawTile());
			}
			if (mode.equals("exit")) {
				editorMap.setExit(e.getX(), e.getY());
			}
			if (mode.equals("start")) {
				editorMap.setStart(e.getX(), e.getY());
			}
			repaint();
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
