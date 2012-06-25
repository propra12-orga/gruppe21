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
	private BufferedImage background;
	/**
	 * Will be shown on effect completion.
	 */
	private BufferedImage foreground;
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
		circleDiameter = GameConstants.FRAME_SIZE_X;
	}

	public CircularZoomEffect(int zoomCenterX, int zoomCenterY, int zoomSpeed,
			BufferedImage background, BufferedImage foreground) {
		this(zoomCenterX, zoomCenterY, zoomSpeed, background);
		this.foreground = foreground;
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
					2 * circleDiameter, 2 * circleDiameter);
			area.subtract(new Area(circle));
			g2d.fill(area);
		} else if (foreground != null) {
			g2d.drawImage(foreground,
					(GameConstants.FRAME_SIZE_X - foreground.getWidth()) / 2,
					(GameConstants.FRAME_SIZE_Y - foreground.getHeight()) / 2,
					foreground.getWidth(), foreground.getHeight(), null);
		}
	}

	@Override
	public void updateEffectState() {
		if (!effectFinished) {
			circleDiameter = circleDiameter - zoomSpeed;
			if (circleDiameter <= 0) {
				effectFinished = true;
			}
		}
	}

}
