package multiplayer;

import imageloader.GameGraphic;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;

import main.GameConstants;
import main.GraphicalGameUnit;
import main.UnitNavigator;
import main.UnitState;

/**
 * This unit lets the user choose a port number and (if he wants join an
 * existing game) enter the desired ip address.
 * 
 * @author tohei
 * 
 */
public class IPChooserUnit extends GraphicalGameUnit {

	private int selectionCounter = 0;
	private final String heading1 = "Enter Port and IP adress:";
	private final String heading2 = "Enter Port number:";
	private String heading;
	private int numOfElements;
	private boolean asHost;
	private GameGraphic background;
	private GameGraphic selector;
	private GameGraphic proceedActive;
	private GameGraphic proceedInactive;
	private GameGraphic backActive;
	private GameGraphic backInactive;
	private String port;
	private String ip;
	private int elementSpace = 40;
	private int inputFieldX = GameConstants.FRAME_SIZE_X / 2 - 200;
	private int inputFieldY = GameConstants.FRAME_SIZE_Y / 2 - 20;
	private int button1X;
	private int button2X;

	public IPChooserUnit(boolean asHost) {
		this.asHost = asHost;
		initComponent();
	}

	@Override
	public void updateComponent() {
		// TODO Auto-generated method stub

	}

	@Override
	public void handleKeyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_UP) {
			if (selectionCounter > 0)
				selectionCounter--;
		}
		if (key == KeyEvent.VK_DOWN) {
			if (selectionCounter < numOfElements - 1)
				selectionCounter++;
		}
		if (key == KeyEvent.VK_RIGHT && selectionCounter == numOfElements - 2) {
			selectionCounter++;
		}
		if (key == KeyEvent.VK_LEFT && selectionCounter == numOfElements - 1) {
			selectionCounter--;
		}
		if (key == KeyEvent.VK_ENTER) {
			if (selectionCounter == numOfElements - 1) {
				UnitNavigator.getNavigator().addGameUnit(
						OptionMenuUnit.loadHostClientOptionMenu(),
						UnitState.TEMPORARY_UNIT);
				UnitNavigator.getNavigator().set(UnitState.TEMPORARY_UNIT);
			} else if (selectionCounter == numOfElements - 2) {
				if (asHost) {
					MapMenuUnit mapMenuUnit = new MapMenuUnit(parsePort());
					UnitNavigator.getNavigator().addGameUnit(mapMenuUnit,
							UnitState.TEMPORARY_UNIT);
					UnitNavigator.getNavigator().set(UnitState.TEMPORARY_UNIT);
				} else {
					// NetworkConnectorUnit networkConnector = new
					// NetworkConnectorUnit(
					// ip, parsePort());
					// UnitNavigator.getNavigator().addGameUnit(networkConnector,
					// UnitState.TEMPORARY_UNIT);
					// UnitNavigator.getNavigator().set(UnitState.TEMPORARY_UNIT);
				}
			}
		}

		if (key == KeyEvent.VK_ESCAPE) {
			UnitNavigator.getNavigator().set(UnitState.BASE_MENU_UNIT);
			System.gc();
			/*
			 * suggest running the garbage collector (might be useful if a lot
			 * of different sub menus have been used)
			 */
		}

		if (selectionCounter == 0) {
			handlePortInput(e);
		} else {
			if (!asHost) {
				if (selectionCounter == 1) {
					handleIPInput(e);
				}

			}
		}
	}

	/*
	 * to be changed!
	 */
	private int parsePort() {
		return Integer.parseInt(port);
	}

	/**
	 * Filter KeyEvents to allow only numbers and '.' to be accepted as elements
	 * of an ip address.
	 * 
	 * @param e
	 */
	private void handleIPInput(KeyEvent e) {
		if ((e.getKeyCode() >= '0' && e.getKeyCode() <= '9')
				|| e.getKeyChar() == '.') {
			if (ip == null)
				ip = "";
			if (ip.length() < 15) {
				ip = ip + e.getKeyChar();
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_DELETE) {
			ip = null;
		}
		if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
			if (ip != null && ip.length() > 1) {
				ip = ip.substring(0, ip.length() - 1);
			} else {
				ip = null;
			}
		}
	}

	/**
	 * Filter KeyEvents to allow only numbers to be accepted as elements of a
	 * port number.
	 * 
	 * @param e
	 */
	private void handlePortInput(KeyEvent e) {
		if (e.getKeyCode() >= '0' && e.getKeyCode() <= '9') {
			if (port == null)
				port = "";
			if (port.length() < 5) {
				port = port + e.getKeyChar();
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_DELETE) {
			port = null;
		}
		if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
			if (port != null && port.length() > 1) {
				port = port.substring(0, port.length() - 1);
			} else {
				port = null;
			}
		}
	}

	@Override
	public void handleKeyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void initComponent() {
		/*
		 * load font
		 */
		try {
			unitFont = loadFont("font1.TTF").deriveFont(40f);
		} catch (Exception e) {
			System.err.println("ERROR LOADING FONT: font1.TTF");
			e.printStackTrace();
			unitFont = new Font("serif", Font.PLAIN, 24);
		}
		/*
		 * load images
		 */
		background = new GameGraphic(GameConstants.MENU_IMAGES_DIR
				+ "MultiplayerMenuBG.png");
		selector = new GameGraphic(GameConstants.MENU_IMAGES_DIR + "Select.png");
		if (asHost) {
			heading = heading2;
			numOfElements = 3;
			proceedActive = new GameGraphic(GameConstants.MENU_IMAGES_DIR
					+ "HostGameActive.png");
			proceedInactive = new GameGraphic(GameConstants.MENU_IMAGES_DIR
					+ "HostGameInactive.png");
		} else {
			heading = heading1;
			numOfElements = 4;
			proceedActive = new GameGraphic(GameConstants.MENU_IMAGES_DIR
					+ "JoinGameActive.png");
			proceedInactive = new GameGraphic(GameConstants.MENU_IMAGES_DIR
					+ "JoinGameInactive.png");
		}
		backActive = new GameGraphic(GameConstants.MENU_IMAGES_DIR
				+ "ActiveBack.png");
		backInactive = new GameGraphic(GameConstants.MENU_IMAGES_DIR
				+ "InactiveBack.png");
		button1X = (GameConstants.FRAME_SIZE_X
				- proceedActive.getImage().getWidth()
				- backActive.getImage().getWidth() - elementSpace) / 2;
		button2X = button1X + proceedActive.getImage().getWidth()
				+ elementSpace;
	}

	@Override
	public void drawComponent(Graphics g) {
		g.drawImage(background.getImage(), 0, 0, GameConstants.FRAME_SIZE_X,
				GameConstants.FRAME_SIZE_Y, null);
		/*
		 * load game font
		 */
		g.setFont(unitFont);
		g.setColor(Color.white);
		/*
		 * center heading
		 */
		Graphics2D g2d = (Graphics2D) g;
		Rectangle2D rect = unitFont.getStringBounds(heading,
				g2d.getFontRenderContext());
		g2d.drawString(heading,
				(int) (GameConstants.FRAME_SIZE_X - rect.getWidth()) / 2,
				(int) (inputFieldY - (10 + unitFont.getSize())));
		String tmp = null;
		if (port == null) {
			tmp = "Port: ...Enter Port...";
		} else {
			tmp = "Port: " + port;
		}
		g2d.drawString(tmp, inputFieldX, inputFieldY + elementSpace);
		if (!asHost) {
			if (ip == null) {
				tmp = "IP-address: ...Enter IP...";
			} else {
				tmp = "IP-address: " + ip;
			}
			g2d.drawString(tmp, inputFieldX, inputFieldY + 2 * elementSpace);
		}
		/*
		 * Draw selector
		 */
		if (selectionCounter < numOfElements - 2)
			g2d.drawImage(selector.getImage(),
					GameConstants.FRAME_SIZE_X / 2 - 260, inputFieldY
							+ selectionCounter * elementSpace, null);
		/*
		 * draw buttons
		 */
		GameGraphic tmpButton = proceedInactive;
		if (selectionCounter == numOfElements - 2) {
			tmpButton = proceedActive;
		}
		g2d.drawImage(tmpButton.getImage(), button1X, inputFieldY + 3
				* elementSpace, null);

		tmpButton = backInactive;
		if (selectionCounter == numOfElements - 1) {
			tmpButton = backActive;
		}
		g2d.drawImage(tmpButton.getImage(), button2X, inputFieldY + 3
				* elementSpace, null);
	}

}
