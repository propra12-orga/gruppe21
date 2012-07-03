package mapeditor;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

import javax.swing.Action;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

public class TileViewer extends JPanel implements MouseListener {
	private Editor editor;
	public Tile currentDrawTile;
	public Tile currentSelectedTile;

	private String mode = "paint";

	private JCheckBox destroyBox, collideBox, visibleBox;

	public TileViewer(Editor e) {
		this.setBackground(Color.GRAY);
		this.setSize(200, 300);
		this.setDoubleBuffered(true);
		this.setLayout(null);
		addMouseListener(this);

		editor = e;

		File defaultF = new File("graphics/game/map/floorempty.png");
		System.out.println(defaultF.getAbsolutePath());
		currentDrawTile = new Tile(defaultF, 0);
		currentSelectedTile = new Tile(defaultF, 0);

		this.add(destroyBox = new JCheckBox("is destroyable", false));
		this.add(collideBox = new JCheckBox("has collision", false));
		this.add(visibleBox = new JCheckBox("is visible", false));
		Action toogleDestroy = toogleDestroy();
		destroyBox.setBounds(5, 200, 150, 20);
		destroyBox.setBackground(Color.gray);
		destroyBox.addActionListener(null);
		destroyBox.setAction(toogleDestroy);
		collideBox.setBounds(5, 220, 150, 20);
		collideBox.setBackground(Color.gray);
		visibleBox.setBounds(5, 240, 150, 20);
		visibleBox.setBackground(Color.gray);

	}

	private Action toogleDestroy() {
		currentDrawTile.setDestroyable(!currentDrawTile.hasCollision());
		return null;
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
		this.visibleBox.setSelected(currentTile.isVisible());
		this.collideBox.setSelected(currentTile.hasCollision());
		this.destroyBox.setSelected(currentTile.isDestroyable());
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
