package mapeditor;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;

import main.GameConstants;

public class Button extends JComponent implements MouseListener {
	BufferedImage image;
	private int width;
	private int height;
	private int posX;
	private int posY;
	private String action;

	public Button(int w, int h, String imageName, String act) {
		width = w;
		height = h;
		image = EditorGraphics.loadImage(GameConstants.EDITOR_BUTTONS
				+ imageName);
		action = act;

		setFocusable(true);
		setSize(w, h);
		addMouseListener(this);
	}

	/**
	 * Draw method for the Button
	 * 
	 * draws the button at the given x,y coordinate
	 * 
	 * @param x
	 *            x-coordinate
	 * @param y
	 *            y-coordinate
	 * @param g
	 *            Graphics object to draw on
	 */
	protected void draw(int x, int y, Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
				RenderingHints.VALUE_INTERPOLATION_BILINEAR);

		g2d.drawImage(image, x, y, width, height, null);
		this.posX = x;
		this.posY = y;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		System.out.println("Button clicked");
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

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getPosX() {
		return posX;
	}

	public int getPosY() {
		return posY;
	}

	public String getAction() {
		return this.action;
	}
}
