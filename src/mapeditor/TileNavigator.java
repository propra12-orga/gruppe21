package mapeditor;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.util.Vector;

import javax.swing.JPanel;

public class TileNavigator extends JPanel implements MouseListener {
	private Button newButton, navUp, navDown;
	private Vector<Button> buttonList = new Vector<Button>();
	private String mode = "Tiles";
	private boolean tileOverflow = false;
	private boolean enemyOverflow = false;
	private Editor editor;

	/**
	 * Tabelements elements
	 */
	private Tab tile, enemy;
	Vector<Tab> tabList = new Vector<Tab>();
	TilePanel tilePanelTiles = new TilePanel(5, 30, 190, 200);
	TilePanel tilePanelEnemies = new TilePanel(5, 30, 190, 200);

	public TileNavigator(Editor e) {
		this.setBackground(Color.DARK_GRAY);
		this.setSize(200, 300);
		this.setDoubleBuffered(true);
		addMouseListener(this);

		editor = e;

		/**
		 * setup tabs
		 */
		tabList.add(tile = new Tab(1, 0, 70, 20, "Tiles"));
		tabList.add(enemy = new Tab(73, 0, 70, 20, "Enemies"));
		buttonList.add(newButton = new Button(5, 270, 25, 25, "newred.png",
				"newtile"));
		buttonList.add(navUp = new Button(10, 235, 80, 20, "up.png", "navUp"));
		buttonList.add(navDown = new Button(110, 235, 80, 20, "down.png",
				"navDown"));
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;

		if (mode.equals("Tiles")) {
			tile.draw(g2d, true);
			enemy.draw(g2d, false);
		} else {
			tile.draw(g2d, false);
			enemy.draw(g2d, true);
		}

		g2d.setColor(Color.GRAY);
		g2d.fill(new Rectangle2D.Float(0, 20, 200, 280));

		if (mode.equals("Tiles")) {
			tilePanelTiles.draw(g2d);
		} else {
			tilePanelEnemies.draw(g2d);
		}

		for (int i = 0; i < buttonList.size(); i++) {
			buttonList.get(i).draw(g2d);
		}

		g2d.setColor(Color.DARK_GRAY);
		g2d.drawLine(0, 0, 0, 299);
	}

	public void addTile(File file) {
		tilePanelTiles.deselectAll();
		tilePanelTiles.addTile(file);

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {

			/**
			 * go through tabs and check if one was clicked
			 */
			for (int i = 0; i < tabList.size(); i++) {
				if (Mouse.isInRegion(e.getX(), e.getY(), tabList.get(i)
						.getPosX(), tabList.get(i).getPosY(), tabList.get(i)
						.getPosX() + tabList.get(i).getWidth(), tabList.get(i)
						.getPosY() + tabList.get(i).getHeight())) {
					this.setMode(tabList.get(i).getMode());
					this.repaint();
				}
			}

			/**
			 * go through buttons if one was clicked
			 */

			for (int i = 0; i < buttonList.size(); i++) {
				if (Mouse.isInRegion(e.getX(), e.getY(), buttonList.get(i)
						.getPosX(), buttonList.get(i).getPosY(), buttonList
						.get(i).getPosX() + buttonList.get(i).getWidth(),
						buttonList.get(i).getPosY()
								+ buttonList.get(i).getHeight())) {
					if (buttonList.get(i).getAction().equals("newtile")) {
						editor.loadNewFile(mode);
					}
					this.repaint();
				}
			}

			if (Mouse.isInRegion(e.getX(), e.getY(), 5, 30, 190, 200)) {
				if (mode.equals("Tiles")) {
					Tile cTile = tilePanelTiles.getTile(e.getX(), e.getY());
					editor.updateTileViewer(cTile, "paint");
					tilePanelTiles.deselectAll();
					cTile.select();
					this.repaint();
				}
				if (mode.equals("Enemies")) {

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

	/**
	 * Getter and Setter
	 */

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}
}
