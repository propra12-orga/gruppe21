package multiplayer;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.ImageIcon;

import main.GameConstants;
import main.GraphicalGameUnit;
import main.UnitNavigator;
import main.UnitState;

/**
 * The MapMenu class let users choose what map they want to play on.
 * 
 * @author saber104
 * 
 */
public class MapMenuUnit extends GraphicalGameUnit {

	private Image background = new ImageIcon(GameConstants.MENU_IMAGES_DIR
			+ "/MapChooserBG.png").getImage();
	private Image map1 = new ImageIcon(GameConstants.MENU_IMAGES_DIR
			+ "/WoodwarsMenuPic.png").getImage();
	private Image select = new ImageIcon(GameConstants.MENU_IMAGES_DIR
			+ "/SelectFrame.png").getImage();
	private Image activeBack = new ImageIcon(GameConstants.MENU_IMAGES_DIR
			+ "/ActiveBack.png").getImage();
	private Image map2 = new ImageIcon(GameConstants.MENU_IMAGES_DIR
			+ "MultMapMenuPic.png").getImage();
	private int startYPos = GameConstants.FRAME_SIZE_Y / 2;
	private int startXPos = GameConstants.FRAME_SIZE_X / 2;

	private int buttonWidth = activeBack.getWidth(null);

	// to be replaced by FontManager
	private Font font;

	public MapMenuUnit() {
		initComponent();
	}

	@Override
	public void updateComponent() {
		// TODO Auto-generated method stub

	}

	@Override
	public void handleKeyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_ENTER) {
			LocalMultiplayerUnit levelmanager = new LocalMultiplayerUnit();
			UnitNavigator.getNavigator().addGameUnit(levelmanager,
					UnitState.LEVEL_MANAGER_UNIT);
			UnitNavigator.getNavigator().set(UnitState.LEVEL_MANAGER_UNIT);
		}
		if (key == KeyEvent.VK_ESCAPE) {
			UnitNavigator.getNavigator().set(UnitState.BASE_MENU_UNIT);
		}
	}

	@Override
	public void handleKeyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void initComponent() {
		try {
			font = loadFont("font1.TTF").deriveFont(50f);
		} catch (Exception e) {
			System.err.println("ERROR LOADING FONT: font1.TTF");
			e.printStackTrace();
			font = new Font("serif", Font.PLAIN, 24);
		}
	}

	@Override
	public void drawComponent(Graphics g) {
		g.drawImage(background, 0, 0, GameConstants.FRAME_SIZE_X,
				GameConstants.FRAME_SIZE_Y, null);
		g.drawImage(select, startXPos - (select.getWidth(null) + 16), startYPos
				- (select.getHeight(null) / 2), null);
		g.drawImage(map1, startXPos - (map1.getWidth(null) + 20), startYPos
				- (map1.getHeight(null) / 2), null);
		g.drawImage(map2, startXPos + 20, startYPos
				- (map1.getHeight(null) / 2), null);
		g.drawImage(activeBack, startXPos - buttonWidth / 2, startYPos + 250,
				null);

		g.setFont(font);
		g.setColor(Color.white);

		/*
		 * center heading
		 */
		Graphics2D g2d = (Graphics2D) g;
		Rectangle2D rect = font.getStringBounds("Choose Multiplayer Map:",
				g2d.getFontRenderContext());
		g2d.drawString("Choose Multiplayer Map:",
				(int) (GameConstants.FRAME_SIZE_X - rect.getWidth()) / 2,
				(int) ((GameConstants.FRAME_SIZE_Y - rect.getHeight()) / 2) - 4
						* font.getSize());

	}

	// to be replaced by FontManager
	/**
	 * Loads font from file (assuming it is located in the 'fonts' directory).
	 * 
	 * @param filename
	 *            font name
	 * @return loaded font
	 * @throws FontFormatException
	 * @throws IOException
	 */
	private Font loadFont(String filename) throws FontFormatException,
			IOException {
		InputStream is = new FileInputStream(new File(GameConstants.FONTS_DIR
				+ filename));
		return Font.createFont(Font.TRUETYPE_FONT, is);
	}

}
