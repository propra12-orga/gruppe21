package singleplayer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
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

	private PlayerData playerData;
	private CampaignData campaignData;

	public Savegame(PlayerData playerData, CampaignData campaignData) {
		this.playerData = playerData;
		this.campaignData = campaignData;
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
	 * Reads Savegame from text file.
	 * 
	 * @param filename
	 *            Filename and path of old savegame.
	 * @return Savegame object consisting of saved player and campaign data.
	 * @throws FileNotFoundException
	 */
	public static Savegame readSavegameFromFile(String filename)
			throws FileNotFoundException {
		File file = new File(filename);
		Scanner sc = new Scanner(file);

		PlayerData pData = null;
		CampaignData cData = null;

		while (sc.nextLine() != null) {
			String line = sc.nextLine();
			line = decipherSavegame(line);
			if (line.startsWith("player")) {
				pData = Player.PlayerData.extractDataFromString(line);
			} else if (line.startsWith("campaign")) {
				cData = Campaign.CampaignData.extractDataFromString(line);
			}
		}
		if (pData == null || cData == null) {
			System.err.println("CORRUPTED SAVEGAME: " + filename);
			return null;
		} else {
			return new Savegame(pData, cData);
		}
	}

	/**
	 * Stores Savegame to file.
	 * 
	 * @param filename
	 *            File used to store Savegame
	 */
	public void storeToFile(String filename) throws FileNotFoundException {
		File file = new File(filename);
		String encodedPlayerData = cipherSavegame(playerData
				.writeDataToString());
		String encodedCampaignData = cipherSavegame(campaignData
				.writeDataToString());
		PrintWriter writer = new PrintWriter(file);
		writer.println(encodedPlayerData);
		writer.println(encodedCampaignData);
		writer.flush();
		writer.close();
	}

}
