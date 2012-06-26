package singleplayer;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.ImageIcon;

import main.GameConstants;
import main.GraphicalGameUnit;
import main.UnitNavigator;
import main.UnitState;
import unitTransitions.TransitionUnit;

public class SavegameManagerUnit extends GraphicalGameUnit {

	String[] saveGameInfo;
	private Image background;
	private Image selectorPic;
	private int selectorPos;
	private GraphicalGameUnit prevTmpUnit;
	private String title;
	private String helpMsg1 = "Press \"UP\"/\"Down\" to select - press \"Enter\" to ";
	private String helpMsg2;
	private String helpMsg3 = "Press \"Escape\" to cancel";
	private boolean loadGame;
	private final float slotFontSize = 32f;
	private final float titleFontSize = 50f;
	private final float helpMsgSize = 22f;
	private final int slotInfoAreaX = GameConstants.FRAME_SIZE_X / 2;
	private final int slotInfoAreaY = 240;
	private final int titleAreaY = 150;
	private final int helpAreaY = GameConstants.FRAME_SIZE_Y - 120;

	public SavegameManagerUnit(boolean loadGame) {
		this.loadGame = loadGame;
		initComponent();
	}

	public SavegameManagerUnit(GraphicalGameUnit prevTmpUnit, boolean loadGame) {
		this.prevTmpUnit = prevTmpUnit;
		this.loadGame = loadGame;
		initComponent();
	}

	@Override
	public void updateComponent() {
	}

	@Override
	public void handleKeyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_UP) {
			selectorPos--;
			if (selectorPos < 0) {
				selectorPos = 3;
			}
		}
		if (key == KeyEvent.VK_DOWN) {
			selectorPos++;
			if (selectorPos > 3) {
				selectorPos = 0;
			}
		}
		if (key == KeyEvent.VK_ENTER) {
			if (loadGame)
				handleLoading();
			else
				handleSaving();

		}
		if (key == KeyEvent.VK_ESCAPE) {
			if (prevTmpUnit != null) {
				UnitNavigator.getNavigator().addGameUnit(prevTmpUnit,
						UnitState.TEMPORARY_UNIT);
				UnitNavigator.getNavigator().set(UnitState.TEMPORARY_UNIT);
			} else {
				UnitNavigator.getNavigator().set(UnitState.BASE_MENU_UNIT);
			}
		}

	}

	/**
	 * Load a game
	 */
	private void handleLoading() {
		try {
			final Savegame save = Savegame.readSavegameSlot(selectorPos);
			if (save != null) {
				final TransitionUnit toolTipUnit = new TransitionUnit(
						UnitState.LEVEL_MANAGER_UNIT, createTooltip(), true);
				toolTipUnit.disableKeyEventControlledProgression();
				toolTipUnit.setWaitForNotification(true);
				toolTipUnit.setTransitionPeriod(300);
				new Thread(new Runnable() {
					@Override
					public void run() {
						LevelManagerUnit levelmanager = new LevelManagerUnit(
								save);
						UnitNavigator.getNavigator().addGameUnit(levelmanager,
								UnitState.LEVEL_MANAGER_UNIT);
						toolTipUnit.authorizeProgression();
					}
				}).start();
				UnitNavigator.getNavigator().addGameUnit(toolTipUnit,
						UnitState.TEMPORARY_UNIT);
				UnitNavigator.getNavigator().set(UnitState.TEMPORARY_UNIT);
			}
		} catch (FileNotFoundException e1) {
			System.err.println("Error loading Savegame slot " + selectorPos
					+ "!");
		}
	}

	private BufferedImage createTooltip() {
		Image tmp = new ImageIcon(GameConstants.MENU_IMAGES_DIR
				+ "MultiplayerMenuBG.png").getImage();
		BufferedImage transitionImage = new BufferedImage(
				GameConstants.FRAME_SIZE_X, GameConstants.FRAME_SIZE_Y,
				BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = transitionImage.createGraphics();
		g2d.drawImage(tmp, 0, 0, transitionImage.getWidth(),
				transitionImage.getHeight(), null);
		g2d.setFont(unitFont);

		String toolTip = GameConstants.TOOL_TIPS[(int) (Math.random() * GameConstants.TOOL_TIPS.length)];
		Rectangle2D rect = unitFont.getStringBounds(toolTip,
				g2d.getFontRenderContext());
		g2d.drawString("loading...",
				(int) (GameConstants.FRAME_SIZE_X - rect.getWidth()) / 2,
				(int) (GameConstants.FRAME_SIZE_Y / 2 - rect.getHeight()));

		g2d.drawString(toolTip,
				(int) (GameConstants.FRAME_SIZE_X - rect.getWidth()) / 2,
				(int) (GameConstants.FRAME_SIZE_Y / 2 + rect.getHeight()));

		return transitionImage;
	}

	/**
	 * Save current game
	 */
	private void handleSaving() {
		GraphicalGameUnit tmp = UnitNavigator.getNavigator().getUnitAt(
				UnitState.LEVEL_MANAGER_UNIT);
		if (tmp instanceof LevelManagerUnit) {
			LevelManagerUnit levelmanager = (LevelManagerUnit) tmp;
			try {
				levelmanager.createSavegame().storeToFile(selectorPos);
				saveGameInfo = Savegame.readSavegameInfo();
			} catch (IOException e) {
				System.err.println("Error storing savegame to file!");
			}
		}
	}

	@Override
	public void handleKeyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void initComponent() {
		background = new ImageIcon(GameConstants.MENU_IMAGES_DIR
				+ "/MapChooserBG.png").getImage();
		selectorPic = new ImageIcon(GameConstants.MENU_IMAGES_DIR
				+ "/Select.png").getImage();
		selectorPos = 0;
		if (loadGame) {
			title = "Load savegame:";
			helpMsg2 = "load savegame";
		} else {
			title = "Save game:";
			helpMsg2 = "save game";
		}
		/*
		 * load font
		 */
		try {
			unitFont = loadFont("font1.TTF").deriveFont(titleFontSize);
		} catch (Exception e) {
			System.err.println("ERROR LOADING FONT: font1.TTF");
			e.printStackTrace();
			unitFont = new Font("serif", Font.PLAIN, 24);
		}
		try {
			saveGameInfo = Savegame.readSavegameInfo();
		} catch (FileNotFoundException e) {
			System.err.println("Error loading Savegame file");
		}
	}

	@Override
	public void drawComponent(Graphics g) {

		/*
		 * Draw selector and background
		 */
		Graphics2D g2d = (Graphics2D) g;
		g2d.setFont(unitFont);
		g2d.setColor(Color.white);
		g2d.drawImage(background, 0, 0, GameConstants.FRAME_SIZE_X,
				GameConstants.FRAME_SIZE_Y, null);
		g2d.drawImage(
				selectorPic,
				slotInfoAreaX - selectorPic.getWidth(null) * 2,
				(int) (slotInfoAreaY - 3 * selectorPic.getHeight(null) / 4 + selectorPos
						* 3 * (slotFontSize)), null);

		/*
		 * Draw title
		 */
		unitFont = unitFont.deriveFont(titleFontSize);
		g2d.setFont(unitFont.deriveFont(titleFontSize));
		Rectangle2D rect = unitFont.getStringBounds(title,
				g2d.getFontRenderContext());
		g2d.drawString(title,
				(int) ((GameConstants.FRAME_SIZE_X - rect.getWidth()) / 2),
				titleAreaY);

		/*
		 * Draw slot info
		 */
		unitFont = unitFont.deriveFont(slotFontSize);
		g2d.setFont(unitFont);
		int extraSlotDistance = 0;
		for (int i = 0; i < saveGameInfo.length; i += 2) {
			if (saveGameInfo[i] == null || saveGameInfo[i + 1] == null) {
				g2d.drawString("UNUSED", slotInfoAreaX, slotInfoAreaY
						+ (i + extraSlotDistance) * (slotFontSize));
				extraSlotDistance++;

			} else {
				g2d.drawString(saveGameInfo[i], slotInfoAreaX, slotInfoAreaY
						+ (i + extraSlotDistance) * (slotFontSize));

				g2d.drawString(saveGameInfo[i + 1], slotInfoAreaX,
						slotInfoAreaY + (i + 1 + extraSlotDistance)
								* (slotFontSize));
				extraSlotDistance++;

			}
		}
		/*
		 * Draw help message
		 */
		unitFont = unitFont.deriveFont(helpMsgSize);
		g2d.setFont(unitFont);
		rect = unitFont.getStringBounds(helpMsg1 + helpMsg2,
				g2d.getFontRenderContext());
		g2d.drawString(helpMsg1 + helpMsg2,
				(int) ((GameConstants.FRAME_SIZE_X - rect.getWidth()) / 2),
				helpAreaY);

		rect = unitFont.getStringBounds(helpMsg3, g2d.getFontRenderContext());
		g2d.drawString(helpMsg3,
				(int) ((GameConstants.FRAME_SIZE_X - rect.getWidth()) / 2),
				helpAreaY + slotFontSize);
	}
}
