package map;

import java.io.IOException;
import java.util.List;
import java.util.Vector;
import main.GameConstants;
import mapobjects.Exit;
import mapobjects.Floor;
import mapobjects.MapObject;
import mapobjects.Wall;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import imageloader.ImageLoader;

public class MapReader {
	private Document mapXML;
	private Element mapRoot;
	private boolean graphicisloaded = false;
	private String[] moList = {"floor","animatetfloor","bomb","effect","enemy","exit","player","wall"};
	
	
	public MapReader(String mn) {
		
		//read XML map with given name
		try {
			mapXML = new SAXBuilder().build(GameConstants.MAP_FILES_DIR+mn+".xml");
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		mapRoot = mapXML.getRootElement();
	}
	
	// returns text of an headerelement with given name
	public String getHeader(String v){
		return mapRoot.getChild("header").getChildText(v);
	}
	
	//loads all graphic elements of the map into the given imageloader
	public void loadGraphics(ImageLoader il){
		// kann liste nicht nach vector casten ...
		List<Element> levels = mapRoot.getChild("mapobjects").getChildren("level");
		
		//TODO animation
		for(int i=0; i<levels.size(); i++){
			List<Element> tile = levels.get(i).getChildren("tile");
			for(int j=0; j<tile.size(); j++){
			il.addImage(GameConstants.MAP_GRAPHICS_DIR+tile.get(j).getChildText("image"));
			}
		}
		
		//TODO animationtile eventuell animated true/false in xml einfüg
		//TODO Movingobjects
		graphicisloaded = true;
	}
	
	// TODO ReadMap
			// levels1
			
	public void getMap(Vector<Vector<MapObject>> mo){
		//until graphics have been loaded you can't get the map;
		if(!graphicisloaded){/*TODO exception*/}
		else{
			List<Element> levels = mapRoot.getChild("mapobjects").getChildren("level");
			//run through draw levels
			for(int i=0; i<levels.size(); i++){
				mo.add(new Vector<MapObject>());
				//look for diffobjecttypes
				for(int j=0; j<moList.length; j++){
				    Vector<Element> templist = new Vector<Element>();
					List<Element> tile = levels.get(i).getChildren("tile");
					for(int c=0; c<tile.size(); c++){
						if(tile.get(c).getAttributeValue("type").equals(moList[j])){
							templist.add(tile.get(c));
						}
					}
					for(int c=0; c<templist.size(); c++){
						//TODO das ist kacka muss noch anders gemacht werden
						if(moList[j].equals("wall")){
							mo.get(i).add(new Wall(Integer.parseInt(templist.get(c).getChildText("posx")),
								    Integer.parseInt(templist.get(c).getChildText("posy")),
									Boolean.parseBoolean(templist.get(c).getChildText("visible")),
									Boolean.parseBoolean(templist.get(c).getChildText("destroyable")),
									Boolean.parseBoolean(templist.get(c).getChildText("collision")),
									templist.get(c).getChildText("image")
							));
							System.out.println("wall added");
						}
						if(moList[j].equals("floor")){
							mo.get(i).add(new Floor(Integer.parseInt(templist.get(c).getChildText("posx")),
								    Integer.parseInt(templist.get(c).getChildText("posy")),
									Boolean.parseBoolean(templist.get(c).getChildText("visible")),
									Boolean.parseBoolean(templist.get(c).getChildText("destroyable")),
									Boolean.parseBoolean(templist.get(c).getChildText("collision")),
									templist.get(c).getChildText("image")
							));
						  System.out.println("floor added");
						}
						if(moList[j].equals("exit")){
							mo.get(i).add(new Exit(Integer.parseInt(templist.get(c).getChildText("posx")),
								    Integer.parseInt(templist.get(c).getChildText("posy")),
									Boolean.parseBoolean(templist.get(c).getChildText("visible")),
									Boolean.parseBoolean(templist.get(c).getChildText("destroyable")),
									Boolean.parseBoolean(templist.get(c).getChildText("collision")),
									templist.get(c).getChildText("image")
							));
						}
					}
					
					//List<Element> type = tile.getChildren(moList[j]);
				}
			}
			
			// wir gehen VORERST davon aus das die tiles in der richtigen reihenfolge geliefert werden
			// beim adden 
		}
	}


    // returns number of levels to be drawn
	public int getDrawLevels() {
		return mapRoot.getChild("mapobjects").getChildren().size();
	}

}
