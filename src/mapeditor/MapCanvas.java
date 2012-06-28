package mapeditor;

import java.awt.Color;

import javax.swing.JPanel;

public class MapCanvas extends JPanel {

	private String mode = "pointer";

	public MapCanvas() {
		this.setBackground(Color.gray);
		this.setSize(550, 600);
		this.setDoubleBuffered(true);
	}

	public void setMode(String mo) {
		this.mode = mo;
	}

	public String getMode() {
		return this.mode;
	}
}
