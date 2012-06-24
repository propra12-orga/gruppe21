package singleplayer;

import mapobjects.Player.PlayerData;
import singleplayer.Campaign.CampaignData;

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
		return null;
	}

	/**
	 * Decipher savegame to extract the data.
	 * 
	 * @param input
	 *            String to decipher.
	 * @return
	 */
	private static String decipherSavegame(String input) {
		return null;
	}

	/**
	 * Reads Savegame from text file.
	 * 
	 * @param filename
	 *            Filename and path of old savegame.
	 * @return Savegame object consisting of saved player and campaign data.
	 */
	public static Savegame readSavegameFromFile(String filename) {
		return null;
	}

}
