package unitTransitions;

import java.awt.Graphics2D;

/**
 * A TransitionEffect may be passed to a TransitionUnit to change the transition
 * animation.
 * 
 * @author tohei
 * 
 */
public interface TransitionEffect {
	/**
	 * Uses a TransitionUnit's Graphics2D object to draw its own image to the
	 * canvas
	 * 
	 * @param g2d
	 */
	public void drawEffect(Graphics2D g2d);

	/**
	 * May be used to update a TransitionEffect's variables.
	 */
	public void updateEffectState();
}
