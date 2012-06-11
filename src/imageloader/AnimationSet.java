package imageloader;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.Vector;

import main.GameConstants;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

/**
 * Class that saves all GameImages and GameAnimations as an AnimationSet
 * 
 * The AnimationSet is loaded from a XML file
 * 
 * @author eik
 * 
 */
public class AnimationSet {
	private Document setXML;
	private Element setRoot;
	private String setName;
	public GameImage defaultImage;
	public GameAnimation defaultAnimation;
	private Vector<GameAnimation> animationList = new Vector<GameAnimation>();
	private Vector<GameImage> imageList = new Vector<GameImage>();

	/**
	 * constructor
	 * 
	 * @param sn
	 *            Name of the Set
	 * @param type
	 *            specifies the type/folder (use the foldername)
	 */
	public AnimationSet(String sn, String type) {
		setName = sn;
		try {
			setXML = new SAXBuilder().build(GameConstants.ANIMATION_FILES_DIR
					+ type + "/" + sn + ".xml");
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		setRoot = setXML.getRootElement();
		// reads animation xml file , loads animations in animationlist
		// TODO optimieren , ist das mit den einzelbildern sinnvoll ?
		List<Element> animations = setRoot.getChildren("animation");
		List<Element> stills = setRoot.getChildren("still");

		defaultImage = new GameImage(GameConstants.ANIMATION_FILES_DIR + type
				+ "/" + setRoot.getAttributeValue("default"), "default");

		defaultAnimation = new GameAnimation("default", 1,
				GameConstants.ANIMATION_FILES_DIR + type + "/"
						+ setRoot.getAttributeValue("default"), 1);

		for (int i = 0; i < animations.size(); i++) {
			animationList.add(new GameAnimation(animations.get(i)
					.getAttributeValue("name"), Integer.parseInt(animations
					.get(i).getAttributeValue("frames")),
					GameConstants.ANIMATION_FILES_DIR + type + "/"
							+ animations.get(i).getText(), Integer
							.parseInt(animations.get(i).getAttributeValue(
									"stretch"))));
		}

		for (int i = 0; i < stills.size(); i++) {
			imageList.add(new GameImage(GameConstants.ANIMATION_FILES_DIR
					+ type + "/" + stills.get(i).getText(),
					GameConstants.ANIMATION_FILES_DIR + type + "/"
							+ stills.get(i).getAttributeValue("name")));
		}
	}

	/**
	 * returns an GameAnimation from the animationvector
	 * 
	 * @param n
	 *            animation-name
	 * @return GameAnimation Object/ default animation if the requested is not
	 *         available
	 */
	public GameAnimation getAnimation(String n) {
		for (int i = 0; i < animationList.size(); i++) {
			if (animationList.get(i).nameEquals(n)) {
				return animationList.get(i);

			}
		}

		return defaultAnimation;
	}

	/**
	 * returns the requested Image
	 * 
	 * @param n
	 *            name of the Image
	 * @return BufferedImage
	 */
	public BufferedImage getImage(String n) {
		for (int i = 0; i < imageList.size(); i++) {
			if (imageList.get(i).nameEquals(n)) {
				return imageList.get(i).getImage();
			}
		}
		return defaultImage.getImage();
	}

	/**
	 * 
	 * @return default image as BufferedImage
	 */
	public BufferedImage getDefault() {
		return defaultImage.getImage();
	}

	/**
	 * 
	 * @return name of the default image as string
	 */
	public String getDefaultName() {
		return defaultImage.getPath();
	}

	/**
	 * Compares a given string to the setName
	 * 
	 * @param n
	 *            string to compare
	 * @return true if equals else false
	 */
	public Boolean nameEquals(String n) {
		return n.equals(setName);
	}

	/**
	 * 
	 * @return name of the set
	 */
	public String getSetName() {
		return setName;
	}
}
