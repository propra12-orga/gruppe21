package mapeditor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Vector;

import javax.swing.JOptionPane;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class FileActions {

	public static void perform(String action, Editor ed) {
		if (action.equals("saveFile")) {
			saveFile(ed);
		}
		if (action.equals("openFile")) {
			openFile();
		}
		if (action.equals("newFile")) {
			newFile();
			ed.canvas.openNew();
		}

	}

	public static void saveFile(Editor editor) {
		String mapname = (JOptionPane.showInputDialog("Give the map a name!"));
		File outfile = new File("maps/" + mapname + ".xml");
		Element root = new Element("map");
		Document doc = new Document(root);
		// header schreiben
		root.addContent(new Element("header")
				.addContent(new Element("mapname").addContent(mapname))
				.addContent(
						new Element("sizex").addContent(String
								.valueOf(editor.canvas.editorMap.getWidth())))
				.addContent(
						new Element("sizey").addContent(String
								.valueOf(editor.canvas.editorMap.getHeight())))
				.addContent(
						new Element("maxUpgrades").addContent(String
								.valueOf(editor.canvas.editorMap.countTiles() / 30)))
				.addContent(new Element("bombsActivated").addContent("true")));

		// map schreiben
		root.addContent(new Element("mapobjects")
				.addContent(new Element("level"))
				.addContent(new Element("level"))
				.addContent(new Element("level"))
				.addContent(new Element("level"))
				.addContent(new Element("level")));

		Vector<Tile> currentList = editor.canvas.editorMap.getTileListL1();
		for (int i = 0; i < currentList.size(); i++) {
			if (currentList.get(i).getType().equals("floor")
					|| currentList.get(i).getType().equals("animatedfloor")
					|| currentList.get(i).getType().equals("wall")) {
				addNewTile(root.getChild("mapobjects").getChildren().get(0),
						currentList.get(i));
			}
		}

		currentList = editor.canvas.editorMap.getTileListL2();
		for (int i = 0; i < currentList.size(); i++) {
			if (currentList.get(i).getType().equals("floor")
					|| currentList.get(i).getType().equals("animatedfloor")
					|| currentList.get(i).getType().equals("wall")) {
				addNewTile(root.getChild("mapobjects").getChildren().get(1),
						currentList.get(i));
			}
			if (currentList.get(i).getType().equals("exit")) {
				root.getChild("mapobjects")
						.getChildren()
						.get(1)
						.addContent(
								new Element("tile")
										.setAttribute("type", "exit")
										.setAttribute("animated", "false")
										.addContent(
												new Element("visible")
														.addContent("true"))
										.addContent(
												new Element("collision")
														.addContent("true"))
										.addContent(
												new Element("destroyable")
														.addContent("false"))
										.addContent(
												new Element("image")
														.setAttribute(
																"rotation", "0")
														.addContent("exit.png"))
										.addContent(
												new Element("posx").addContent(String
														.valueOf(editor.canvas.editorMap.exitTile
																.getPosX())))
										.addContent(
												new Element("posy").addContent(String
														.valueOf(editor.canvas.editorMap.exitTile
																.getPosY()))));

			}
		}

		// Items section schreiben
		root.addContent(new Element("items")
				.addContent(
						new Element("animationset").addContent("simplebomb"))
				.addContent(new Element("animationset").addContent("upgrades"))
				.addContent(new Element("image").addContent("exit.png")));

		// XML-Dokument in Datei speichern
		XMLOutputter out = new XMLOutputter(Format.getPrettyFormat());
		try {
			out.output(doc, new FileOutputStream(outfile.getAbsolutePath()));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("file saved");
	}

	public static void openFile() {
		System.out.println("file open");
	}

	public static void newFile() {
		System.out.println("new file");
	}

	public static void addNewTile(Element el, Tile tile) {
		Element graph;
		if (tile.isAnimated()) {
			String[] namecut = tile.getName().split(".xml");
			graph = new Element("animationset").addContent(namecut[0]);
		} else {
			graph = new Element("image").setAttribute("rotation",
					String.valueOf(tile.getRotation())).addContent(
					tile.getName());
		}
		el.addContent(new Element("tile")
				.setAttribute("type", "floor")
				.setAttribute("animated", String.valueOf(tile.isAnimated()))
				.addContent(
						new Element("visible").addContent(String.valueOf(tile
								.isVisible())))
				.addContent(
						new Element("collision").addContent(String.valueOf(tile
								.hasCollision())))
				.addContent(
						new Element("destroyable").addContent(String
								.valueOf(tile.isDestroyable())))
				.addContent(graph)
				.addContent(
						new Element("posx").addContent(String.valueOf(tile
								.getPosX())))
				.addContent(
						new Element("posy").addContent(String.valueOf(tile
								.getPosY()))));

	}

}
