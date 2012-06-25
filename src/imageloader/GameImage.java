package imageloader;

/**
 * Holds an Image
 * 
 * @author eik
 * 
 */

public class GameImage extends GameGraphic {
	private String path;
	String name;

	/**
	 * constructor
	 * 
	 * @param p
	 *            url of the image
	 * @param n
	 *            name of the image
	 */
	public GameImage(String p, String n) {
		super(p);
		path = p;
		name = n;
	}

	public GameImage(String p, String n, int rotation) {
		super(p, rotation);
		path = p;
		name = n;
	}

	/**
	 * compares a given path with the actual path , returns true if they are
	 * equal
	 * 
	 * @param inS
	 *            string to compare
	 * @return boolean , true if equals - false if not
	 */
	public boolean pathEquals(String inS) {
		return (path.equals(inS));
	}

	/**
	 * returns the url of the image
	 * 
	 * @return path as String
	 */
	public String getPath() {
		return path;
	}

	/**
	 * compares a given name with the GameImage name , returns true if they are
	 * equal
	 * 
	 * @param n
	 *            name to compare
	 * @return boolean, true if equals - false if not
	 */
	public boolean nameEquals(String n) {
		return name.equals(n);
	}

}
