package main;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;

public class MainMenuUnit extends GraphicalGameUnit {

	// not final, loading of all pictures will be handled in Imageloader class
	private Image Background = new ImageIcon(GameConstants.MENU_IMAGES_DIR
			+ "/MMBGPlaceholder.png").getImage();
	private Image select = new ImageIcon(GameConstants.MENU_IMAGES_DIR
			+ "/SelectorPlaceholder.png").getImage();
	private Image singlePlayerActive = new ImageIcon(
			GameConstants.MENU_IMAGES_DIR
					+ "/ActiveSingleplayerPlaceholder.png").getImage();
	private Image multiplayerActive = new ImageIcon(
			GameConstants.MENU_IMAGES_DIR + "/ActiveMultiplayerPlaceholder.png")
			.getImage();
	private Image quitActive = new ImageIcon(GameConstants.MENU_IMAGES_DIR
			+ "/ActiveQuitgamePlaceholder.png").getImage();
	private Image ccontinueActive = new ImageIcon(GameConstants.MENU_IMAGES_DIR
			+ "/ActiveContinueGamePlaceholder.png").getImage();
	private Image singlePlayerInactive = new ImageIcon(
			GameConstants.MENU_IMAGES_DIR
					+ "/InactiveSingleplayerPlaceholder.png").getImage();
	private Image multiplayerInactive = new ImageIcon(
			GameConstants.MENU_IMAGES_DIR
					+ "/InactiveMultiplayerPlaceholder.png").getImage();
	private Image quitInactive = new ImageIcon(GameConstants.MENU_IMAGES_DIR
			+ "/InactiveQuitgamePlaceholder.png").getImage();
	private Image ccontinueInactive = new ImageIcon(
			GameConstants.MENU_IMAGES_DIR
					+ "/InactiveContinueGamePlaceholder.png").getImage();

	private int buttonSpace = 10;
	private int buttonHeight = singlePlayerActive.getHeight(null);
	private int buttonWidth = singlePlayerActive.getWidth(null);
	// using GameConstants for exact Buttonplacing on screen
	private int startXPos = GameConstants.FRAME_SIZE_X / 2 - buttonWidth / 2;
	private int startYPos = GameConstants.FRAME_SIZE_Y - 300;
	// sets the space between all buttons and their positions
	private int button1YPos = startYPos;
	private int button2YPos = startYPos + 1 * (buttonHeight + buttonSpace);
	private int button3YPos = startYPos + 2 * (buttonHeight + buttonSpace);
	private int button4YPos = startYPos + 3 * (buttonHeight + buttonSpace);
	private int selectCounter;
	// point connected with the select image for optimal positioning
	private Point selectorGhost = new Point(startXPos, startYPos);

	public MainMenuUnit() {
		playSounds();
	}

	@Override
	public void drawComponent(Graphics g) {
		g.drawImage(Background, 0, 0, null);
		g.drawImage(singlePlayerInactive, startXPos, button1YPos, null);
		g.drawImage(multiplayerInactive, startXPos, button2YPos, null);
		g.drawImage(quitInactive, startXPos, button3YPos, null);
		g.drawImage(ccontinueInactive, startXPos, button4YPos, null);
		g.drawImage(select, (int) selectorGhost.getX() - buttonSpace,
				(int) selectorGhost.getY(), null);
		/*
		 * determines either to use active or inactive button layout based on
		 * select position
		 */
		if (selectorGhost.getY() == button1YPos)
			g.drawImage(singlePlayerActive, startXPos, button1YPos, null);

		if (selectorGhost.getY() == button2YPos)
			g.drawImage(multiplayerActive, startXPos, button2YPos, null);

		if (selectorGhost.getY() == button3YPos)
			g.drawImage(quitActive, startXPos, button3YPos, null);

		if (selectorGhost.getY() == button4YPos)
			g.drawImage(ccontinueActive, startXPos, button4YPos, null);
	}

	// MainMenu Navigation
	@Override
	public void handleKeyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		// selectCounter is used to position the select image
		if (key == KeyEvent.VK_UP || key == KeyEvent.VK_LEFT) {
			selectCounter--;
		}
		if (key == KeyEvent.VK_DOWN || key == KeyEvent.VK_RIGHT) {
			selectCounter++;
		}
		if (selectCounter > 3) {
			selectCounter = 0;
		}
		if (selectCounter < 0) {
			selectCounter = 3;
		}
		// what happens if Enter is pressed
		if (key == KeyEvent.VK_ENTER && selectCounter == 0) {
			// create new game
			LevelManagerUnit levelmanager = new LevelManagerUnit();
			levelmanager.setNavigator(getNavigator());
			getNavigator().addGameUnit(levelmanager,
					UnitState.LEVEL_MANAGER_UNIT);
			getNavigator().set(UnitState.LEVEL_MANAGER_UNIT);
		}
		if (key == KeyEvent.VK_ENTER && selectCounter == 1) {
			System.out.println("Multiplayer");
		}
		if (key == KeyEvent.VK_ENTER && selectCounter == 2) {
			// end game
			getNavigator().terminateGame();
		}
		if (key == KeyEvent.VK_ENTER && selectCounter == 3) {
			System.out.println("Continue");
		}
	}

	@Override
	public void handleKeyReleased(KeyEvent e) {
	}

	@Override
	public void initComponent() {
	}

	@Override
	public void updateComponent() {
		// updates selectorPosition after keyevent
		selectorGhost.setLocation(startXPos - (select.getWidth(null)),
				startYPos + selectCounter * (buttonHeight + buttonSpace));
	}

	/*
	 * added a playSounds method for testing purpose & to check for performance
	 * issues not exactly sure how this works in detail if you have problems
	 * with other parts of the game, just put the method contained in
	 * constructor into comment lines
	 */
	public void playSounds() {
		try {
			AudioInputStream menuSoundA = AudioSystem
					.getAudioInputStream(new File(GameConstants.SOUNDS_DIR+"/MainMenuMIDI.mid"));
			BufferedInputStream menuSoundB = new BufferedInputStream(menuSoundA);
			AudioFormat af = menuSoundA.getFormat();
			int size = (int) (af.getFrameSize() * menuSoundA.getFrameLength());
			byte[] audio = new byte[size];
			DataLine.Info info = new DataLine.Info(Clip.class, af, size);
			menuSoundB.read(audio, 0, size);
			Clip bgSound = (Clip) AudioSystem.getLine(info);
			bgSound.open(af, audio, 0, size);
			bgSound.start();
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}