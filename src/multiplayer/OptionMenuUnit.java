package multiplayer;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
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
 * The OptionMenu class gives the user the oportunity to choose to play local
 * multiplayer or network-based multiplayer. It is extanded from the
 * GraphicalGameUnit class.
 * 
 * @author saber104
 * 
 */

public class OptionMenuUnit extends GraphicalGameUnit {

	private Image background = new ImageIcon(GameConstants.MENU_IMAGES_DIR
			+ "/MultiplayerMenuBG.png").getImage();
	private Image select = new ImageIcon(GameConstants.MENU_IMAGES_DIR
			+ "/Select.png").getImage();
	private Image activeLocal = new ImageIcon(GameConstants.MENU_IMAGES_DIR
			+ "/ActiveLocal.png").getImage();
	private Image inactiveLocal = new ImageIcon(GameConstants.MENU_IMAGES_DIR
			+ "/InactiveLocal.png").getImage();
	private Image activeNetwork = new ImageIcon(GameConstants.MENU_IMAGES_DIR
			+ "/ActiveNetwork.png").getImage();
	private Image inactiveNetwork = new ImageIcon(GameConstants.MENU_IMAGES_DIR
			+ "/InactiveNetwork.png").getImage();
	private Image activeBack = new ImageIcon(GameConstants.MENU_IMAGES_DIR
			+ "/ActiveBack.png").getImage();
	private Image inactiveBack = new ImageIcon(GameConstants.MENU_IMAGES_DIR
			+ "/InactiveBack.png").getImage();

	private int buttonSpace = 40;
	private int buttonWidth = activeLocal.getWidth(null);
	private int startYPos = GameConstants.FRAME_SIZE_Y / 2;
	private int startXPos = GameConstants.FRAME_SIZE_X / 2
			- (buttonWidth + buttonWidth / 2 + buttonSpace);
	private int button1XPos = startXPos;
	private int button2XPos = startXPos + 1 * (buttonWidth + buttonSpace);
	private int button3XPos = startXPos + 2 * (buttonWidth + buttonSpace);
	private Point selectorGhost = new Point(
			button1XPos - select.getWidth(null), startYPos);
	private int selectCounter;

	// to be replaced by FontManager
	private Font font;

	public OptionMenuUnit() {
		initComponent();
	}

	@Override
	public void updateComponent() {
		selectorGhost.setLocation(startXPos - select.getWidth(null)
				+ (buttonWidth + buttonSpace) * selectCounter, startYPos);
	}

	@Override
	public void handleKeyPressed(KeyEvent e) {
		int key = e.getKeyCode();

		if (key == KeyEvent.VK_LEFT) {
			selectCounter--;
		}
		if (key == KeyEvent.VK_RIGHT) {
			selectCounter++;
		}
		if (selectCounter > 2) {
			selectCounter = 0;
		}
		if (selectCounter < 0) {
			selectCounter = 2;
		}
		if (key == KeyEvent.VK_ENTER && selectCounter == 0) {
			MapMenuUnit mapMenu = new MapMenuUnit();
			UnitNavigator.getNavigator().addGameUnit(mapMenu,
					UnitState.TEMPORARY_UNIT);
			UnitNavigator.getNavigator().set(UnitState.TEMPORARY_UNIT);

		}
		if (key == KeyEvent.VK_ENTER && selectCounter == 1) {
			System.out.println("Network!!!");
		}
		if (key == KeyEvent.VK_ENTER && selectCounter == 2) {
			UnitNavigator.getNavigator().set(UnitState.BASE_MENU_UNIT);
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
		g.drawImage(inactiveLocal, button1XPos, startYPos, null);
		g.drawImage(inactiveNetwork, button2XPos, startYPos, null);
		g.drawImage(inactiveBack, button3XPos, startYPos, null);
		g.drawImage(select, (int) selectorGhost.getX(),
				(int) selectorGhost.getY(), null);

		/*
		 * load game font
		 */

		g.setFont(font);
		g.setColor(Color.white);

		/*
		 * center heading
		 */
		Graphics2D g2d = (Graphics2D) g;
		Rectangle2D rect = font.getStringBounds("Choose Multiplayer Mode:",
				g2d.getFontRenderContext());
		g2d.drawString("Choose Multiplayer Mode:",
				(int) (GameConstants.FRAME_SIZE_X - rect.getWidth()) / 2,
				(int) ((GameConstants.FRAME_SIZE_Y - rect.getHeight()) / 2)
						- font.getSize() / 2);

		if (selectorGhost.getX() == button1XPos - select.getWidth(null))
			g.drawImage(activeLocal, button1XPos, startYPos, null);

		if (selectorGhost.getX() == button2XPos - select.getWidth(null))
			g.drawImage(activeNetwork, button2XPos, startYPos, null);

		if (selectorGhost.getX() == button3XPos - select.getWidth(null))
			g.drawImage(activeBack, button3XPos, startYPos, null);

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
