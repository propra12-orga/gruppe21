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

public class CampaignReader {
	private Document campaignXML;
	private Element campaignRoot;
	private String filename;

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

	public String getHeader(String v) {
		return campaignRoot.getChild("header").getChildText(v);
	}

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
				progressIndicator, background, player);
		Campaign campaign = new Campaign(levels, worldMap, filename);
		return campaign;
	}
}
