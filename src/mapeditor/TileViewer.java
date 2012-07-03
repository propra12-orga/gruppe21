package mapeditor;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

import javax.swing.JPanel;

public class TileViewer extends JPanel implements MouseListener {
	private Editor editor;
	public Tile currentDrawTile;
	public Tile currentSelectedTile;

	private String mode = "paint";

	public TileViewer(Editor e) {
		this.setBackground(Color.GRAY);
		this.setSize(200, 300);
		this.setDoubleBuffered(true);
		addMouseListener(this);

		editor = e;

		File defaultF = new File("graphics/game/map/floorempty.png");
		System.out.println(defaultF.getAbsolutePath());
		currentDrawTile = new Tile(defaultF, 0);
		currentSelectedTile = new Tile(defaultF, 0);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;

		g2d.drawImage(currentDrawTile.getImage(), 10, 10, 180, 180, null);

		g2d.setColor(Color.DARK_GRAY);
		g2d.drawLine(0, 0, 0, 299);
		g2d.drawLine(0, 0, 200, 0);
	}

	public Tile getDrawTile() {
		return currentDrawTile;
	}

	public void setDrawTile(Tile currentTile) {
		this.currentDrawTile = currentTile;
		repaint();
	}

	public Tile getSelectedTile() {
		return currentSelectedTile;
	}

	public void setSelectedTile(Tile currentTile) {
		this.currentSelectedTile = currentTile;
		repaint();
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

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

}
