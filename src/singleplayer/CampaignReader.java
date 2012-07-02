package singleplayer;

import imageloader.GameGraphic;

import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import main.GameConstants;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

/**
 * The CampaignReader can be used to load a campaign object from XML files.
 * Unlike the Map class, one cannot automatically load a Campaign object by
 * passing its constructor the XML file, but instead needs to create a
 * CampaignReader and call its readCampaignFromFile() method explicitly.
 * 
 * @author tohei
 * 
 */
public class CampaignReader {
	private Document campaignXML;
	private Element campaignRoot;
	private String filename;

	/**
	 * Create a new CampaignReader by passing its constructor an XML filename.
	 * The file is assumed to be located in the campaigns directory; its suffix
	 * (.xml) has to be omitted!
	 * 
	 * @param filename
	 */
	public CampaignReader(String filename) {
		this.filename = filename;
		try {
			campaignXML = new SAXBuilder().build(GameConstants.CAMPAIGNS_DIR
					+ filename + ".xml");
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		campaignRoot = campaignXML.getRootElement();
	}

	/**
	 * Read file header.
	 * 
	 * @param v
	 * @return
	 */
	public String getHeader(String v) {
		return campaignRoot.getChild("header").getChildText(v);
	}

	/**
	 * To be called in order to read a campaign object from file.
	 * 
	 * @return
	 */
	public Campaign readCampaignFromFile() {

		int levelNumber = Integer.parseInt(campaignRoot.getChild("header")
				.getChildText("numOfLevels"));

		ArrayList<ArrayList<Campaign.StoryMapContainer>> levels = new ArrayList<ArrayList<Campaign.StoryMapContainer>>();
		for (int i = 0; i < levelNumber; i++) {
			levels.add(new ArrayList<Campaign.StoryMapContainer>());
		}
		List<Element> maps = campaignRoot.getChild("maps").getChildren();

		for (Element el : maps) {
			String intro = el.getChildText("intro");
			String[] mapIntro = null;
			if (!intro.trim().isEmpty()) {
				mapIntro = intro.split("#");
			}
			String mapName = el.getChildText("name");
			int index = Integer.parseInt(el.getChildText("sequence"));
			levels.get(index).add(
					new Campaign.StoryMapContainer(mapName, mapIntro));
		}

		Element worldMapElement = campaignRoot.getChild("worldmap");
		String worldMapName = worldMapElement.getChildText("name");
		GameGraphic background = new GameGraphic(GameConstants.WM_GRAPHICS_DIR
				+ worldMapElement.getChildText("background"));
		GameGraphic player = new GameGraphic(GameConstants.CHAR_DIR
				+ worldMapElement.getChildText("player"));
		List<Element> coords = campaignRoot.getChild("worldmap")
				.getChild("coordinates").getChildren();
		Point[] coordinates = new Point[coords.size()];
		String[] coordLabel = new String[coords.size()];
		GameGraphic[] progressIndicator = new GameGraphic[coords.size() - 1];
		for (int i = 0; i < coords.size(); i++) {
			Element el = coords.get(i);
			int x = Integer.parseInt(el.getChildText("cx"));
			int y = Integer.parseInt(el.getChildText("cy"));
			coordLabel[i] = el.getChildText("label");
			coordinates[i] = new Point(x, y);
			if (i != coords.size() - 1) {
				progressIndicator[i] = new GameGraphic(
						GameConstants.WM_GRAPHICS_DIR
								+ el.getChildText("progInd"));
			}
		}

		WorldMap worldMap = new WorldMap(coordinates, coordLabel,
				progressIndicator, background, player, worldMapName);
		Campaign campaign = new Campaign(levels, worldMap, filename);
		return campaign;
	}
}
