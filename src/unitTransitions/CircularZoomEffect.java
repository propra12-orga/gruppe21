package unitTransitions;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;

import main.GameConstants;

/**
 * Creates some sort of zoom effect, targeting a particular coordinate on the
 * screen.
 * 
 * @author tohei
 * 
 */
public class CircularZoomEffect implements TransitionEffect {

	/**
	 * X coordinate used to center the zoom effect.
	 */
	private int zoomCenterX;
	/**
	 * Y coordinate used to center the zoom effect.
	 */
	private int zoomCenterY;
	/**
	 * Diameter of the circular zoom effect.
	 */
	private int circleDiameter;
	/**
	 * Regulates the speed of the zoom.
	 */
	private int zoomSpeed;
	/**
	 * Background image.
	 */
	BufferedImage background;
	/**
	 * Indicates if the zoom finished.
	 */
	private boolean effectFinished = false;

	public CircularZoomEffect(int zoomCenterX, int zoomCenterY, int zoomSpeed,
			BufferedImage background) {
		if (zoomCenterX < 0 || zoomCenterY < 0)
			throw new IllegalArgumentException(
					"X and Y coordinates must be positive!");
		this.zoomCenterX = zoomCenterX;
		this.zoomCenterY = zoomCenterY;
		this.zoomSpeed = zoomSpeed;
		this.background = background;
	}

	@Override
	public void drawEffect(Graphics2D g2d) {
		g2d.setColor(Color.black);
		g2d.fillRect(0, 0, GameConstants.FRAME_SIZE_X,
				GameConstants.FRAME_SIZE_Y);
		if (!effectFinished) {
			g2d.drawImage(background,
					(GameConstants.FRAME_SIZE_X - background.getWidth()) / 2,
					(GameConstants.FRAME_SIZE_Y - background.getHeight()) / 2,
					background.getWidth(), background.getHeight(), null);
			Area area = new Area(new Rectangle(GameConstants.FRAME_SIZE_X,
					GameConstants.FRAME_SIZE_Y));
			Ellipse2D circle = new Ellipse2D.Float(
					zoomCenterX - circleDiameter, zoomCenterY - circleDiameter,
					circleDiameter, circleDiameter);
			area.subtract(new Area(circle));
			g2d.fill(area);
		}
	}

	@Override
	public void updateEffectState() {
		if (!effectFinished) {
			circleDiameter -= zoomSpeed;
			if (circleDiameter <= 0) {
				effectFinished = true;
			}
		}
	}

}
