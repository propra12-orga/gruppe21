package mapeditor;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import main.GameConstants;

public class Button extends JPanel {
	BufferedImage image;
	int width;
	int height;

	public Button(int w, int h, String imageName) {
		this.setSize(w, h);
		width = w;
		height = h;
		image = EditorGraphics.loadImage(GameConstants.EDITOR_BUTTONS
				+ imageName);
	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(image, 0, 0, width, height, null);
		System.out.print("button draw");
	}
}
