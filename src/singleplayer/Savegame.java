package singleplayer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import mapobjects.Player;
import mapobjects.Player.PlayerData;
import singleplayer.Campaign.CampaignData;

/**
 * Used to store and reload a user's game progress.
 * 
 * @author tohei
 * 
 */
public class Savegame {

	private static final String SAVEGAME_FILE = "savegames.txt";

	private PlayerData playerData;
	private CampaignData campaignData;

	public Savegame(PlayerData playerData, CampaignData campaignData) {
		this.playerData = playerData;
		this.campaignData = campaignData;
	}

	public PlayerData getPlayerData() {
		return playerData;
	}

	public CampaignData getCampaignData() {
		return campaignData;
	}

	/**
	 * Cipher a savegame to prevent the user from editing his savegames
	 * manually.
	 * 
	 * @param input
	 *            String to cipher.
	 * @return
	 */
	private static String cipherSavegame(String input) {
		return input;
	}

	/**
	 * Decipher savegame to extract the data.
	 * 
	 * @param input
	 *            String to decipher.
	 * @return
	 */
	private static String decipherSavegame(String input) {
		return input;
	}

	/**
	 * Reads Savegame slot from savegame text file.
	 * 
	 * @param slot
	 *            Savegame slot (0 - 3).
	 * @return Savegame object consisting of saved player and campaign data.
	 * @throws FileNotFoundException
	 */
	public static Savegame readSavegameSlot(int slot)
			throws FileNotFoundException {
		if (slot < 0 || slot > 3)
			throw new IllegalArgumentException("Unknown Savegame Slot:" + slot);
		File file = new File(SAVEGAME_FILE);
		Scanner sc = new Scanner(file);

		PlayerData pData = null;
		CampaignData cData = null;

		while (sc.hasNextLine()) {
			String line = sc.nextLine();
			line = decipherSavegame(line);
			if (line.startsWith("slot-" + slot) && line.contains("player")) {
				pData = Player.PlayerData
						.extractDataFromString(line.split(":")[1]);
			} else if (line.startsWith("slot-" + slot)
					&& line.contains("campaign")) {
				cData = Campaign.CampaignData.extractDataFromString(line
						.split(":")[1]);
			}
		}
		if (pData == null || cData == null) {
			return null;
		} else {
			return new Savegame(pData, cData);
		}
	}

	/**
	 * Generates strings that can be used to display some sort of information
	 * about every Savegame slot. Faster than analyzing each slot individually.
	 * 
	 * @return Array containing two lines per slot, one containing information
	 *         about the selected level, and the other representing the level
	 *         progress. If a Savegame slot has not been used yet, its array
	 *         slots will remain null.
	 * @throws FileNotFoundException
	 */
	public static String[] readSavegameInfo() throws FileNotFoundException {
		File file = new File(SAVEGAME_FILE);
		String[] slotStatus = new String[8];

		if (file.exists()) {
			Scanner sc = new Scanner(file);
			while (sc.hasNextLine()) {
				String line = sc.nextLine();
				// System.out.println(line);
				line = decipherSavegame(line);
				if (line.contains("campaign")) {
					String[] lineData = line.split(":");
					String[] slotData = lineData[0].split("-");
					CampaignData cData = CampaignData
							.extractDataFromString(lineData[1]);
					int slot = 2 * Integer.parseInt(slotData[1]);
					slotStatus[slot] = "Selected Level: "
							+ (cData.getSelectedLevel() + 1);
					slotStatus[slot + 1] = "Level Progress: "
							+ cData.getMaxLevelAccessible() + " / "
							+ cData.getNumOfLevels();
				}
			}
		}
		return slotStatus;
	}

	/**
	 * Stores Savegame to savegame file.
	 * 
	 * @param slot
	 *            Savegame slot (0 - 3)
	 * @throws IOException
	 */
	public void storeToFile(int slot) throws IOException {
		if (slot < 0 || slot > 3)
			throw new IllegalArgumentException("Unknown Savegame Slot:" + slot);
		File file = new File(SAVEGAME_FILE);

		if (!file.exists())
			file.createNewFile();

		String encodedPlayerData = cipherSavegame(playerData
				.writeDataToString());
		String encodedCampaignData = cipherSavegame(campaignData
				.writeDataToString());
		/*
		 * Used to "clean file": All other slots will be kept, old input slot
		 * will be overwritten
		 */
		List<String> fileContent = new ArrayList<String>();
		Scanner sc = new Scanner(file);
		while (sc.hasNextLine()) {
			String line = sc.nextLine();
			if (!line.isEmpty() && !line.startsWith("slot-" + slot))
				fileContent.add(line);
		}
		file.delete();
		file = new File(SAVEGAME_FILE);
		PrintWriter writer = new PrintWriter(file);
		for (String s : fileContent) {
			writer.println(s);
		}
		writer.print("slot-" + slot + ":");
		writer.println(encodedPlayerData);
		writer.print("slot-" + slot + ":");
		writer.println(encodedCampaignData);
		writer.flush();
		writer.close();
	}
}
