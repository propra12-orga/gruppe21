package mapeditor;

/**
 * Class for mouse analytics
 * 
 * @author eik
 * 
 */
public class Mouse {
	/**
	 * returns if mousepointer is in an rectangular region or not
	 * 
	 * @param x
	 *            mouse x-position
	 * @param y
	 *            mouse y-position
	 * @param startX
	 *            x coordinate of the start point of the rectangular
	 * @param startY
	 *            y coordinate of the start point of the rectangular
	 * @param endX
	 *            x coordinate of the end point of the rectangular
	 * @param endY
	 *            y coordinate of the end point of the rectangular
	 * @return boolean true if mouse is in region else false
	 */
	public static boolean isInRegion(int x, int y, int startX, int startY,
			int endX, int endY) {
		boolean status = false;
		if ((x > startX && x < endX) && (y > startY && y < endY)) {
			status = true;
		}
		return status;
	}
}
