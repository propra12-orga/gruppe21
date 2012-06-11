package multiplayer;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.KeyEvent;

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

public class OptionMenu extends GraphicalGameUnit {

	private Image background = new ImageIcon(GameConstants.MENU_IMAGES_DIR
			+ "/MultiplayerMenuBG").getImage();
	private Image select = new ImageIcon(GameConstants.MENU_IMAGES_DIR
			+ "/Select.png").getImage();
	private Image activeLocal = new ImageIcon(GameConstants.MENU_IMAGES_DIR
			+ "/ActiveLocal").getImage();
	private Image inactiveLocal = new ImageIcon(GameConstants.MENU_IMAGES_DIR
			+ "/InactiveLocal").getImage();
	private Image activeNetwork = new ImageIcon(GameConstants.MENU_IMAGES_DIR
			+ "/ActiveNetwork").getImage();
	private Image inactiveNetwork = new ImageIcon(GameConstants.MENU_IMAGES_DIR
			+ "/InactiveNetwork").getImage();
	private Image activeBack = new ImageIcon(GameConstants.MENU_IMAGES_DIR
			+ "/ActiveBack").getImage();
	private Image inactiveBack = new ImageIcon(GameConstants.MENU_IMAGES_DIR
			+ "/InactiveBack").getImage();

	private int buttonSpace = 20;
	private int buttonWidth = activeLocal.getWidth(null);
	private int startYPos = GameConstants.FRAME_SIZE_Y / 2 - buttonWidth / 2;
	private int startXPos = GameConstants.FRAME_SIZE_X / 2
			- (buttonWidth + buttonWidth / 2 + buttonSpace);
	private int button1XPos = startXPos;
	private int button2XPos = startXPos + 1 * (buttonWidth + buttonSpace);
	private int button3XPos = startXPos + 2 * (buttonWidth + buttonSpace);
	private Point selectorGhost = new Point(startXPos, startYPos);
	private int selectCounter;

	@Override
	public void updateComponent() {
		selectorGhost.setLocation(startXPos - (select.getWidth(null))
				* selectCounter, startYPos);

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
			// create new game
			LocalMultiplayerUnit levelmanager = new LocalMultiplayerUnit();
			UnitNavigator.getNavigator().addGameUnit(levelmanager,
					UnitState.LEVEL_MANAGER_UNIT);
			UnitNavigator.getNavigator().set(UnitState.LEVEL_MANAGER_UNIT);

		}
		if (key == KeyEvent.VK_ENTER && selectCounter == 1) {
			System.out.println("Network!!!");
		}
		if (key == KeyEvent.VK_ENTER && selectCounter == 2) {
			UnitNavigator.getNavigator().set(UnitState.BASE_MENU_UNIT);
		}

	}

	@Override
	public void handleKeyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void initComponent() {
		// TODO Auto-generated method stub

	}

	@Override
	public void drawComponent(Graphics g) {
		g.drawImage(background, 0, 0, null);
		g.drawImage(inactiveLocal, button1XPos, startYPos, null);
		g.drawImage(inactiveNetwork, button2XPos, startYPos, null);
		g.drawImage(inactiveBack, button3XPos, startYPos, null);
		g.drawImage(select, (int) selectorGhost.getX() - buttonSpace,
				(int) selectorGhost.getY(), null);

		if (selectorGhost.getX() == button1XPos)
			g.drawImage(activeLocal, button1XPos, startYPos, null);

		if (selectorGhost.getX() == button2XPos)
			g.drawImage(activeNetwork, button2XPos, startYPos, null);

		if (selectorGhost.getX() == button3XPos)
			g.drawImage(activeBack, button3XPos, startYPos, null);

	}

}
