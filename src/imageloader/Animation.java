package imageloader;

import java.awt.image.BufferedImage;

/**
 * calculates Animations from an given AnimationSet
 * 
 * @author eik
 * 
 */

public class Animation {
	private GameAnimation currentAnimation;
	private BufferedImage currentImage;
	private boolean isrunning = false;
	private boolean isimage = false;
	private String aSetName;
	private AnimationSet animationSet;
	private int animationCounter = 0;
	private int startdelay = -1;
	private int stopdelay = -1;
	private int changedelay = -1;
	private String waitingAnimation;
	private BufferedImage waitingStopImage = null;
	private int stretch = 1;
	private int stretchcounter = 0;

	/**
	 * constructor
	 * 
	 * @param n
	 *            Name of the AnimationSet
	 * @param gr
	 *            the ImageLoader , that is used and where the AnimationSet is
	 *            stored
	 */
	public Animation(String n, ImageLoader gr) {
		aSetName = n;
		animationSet = gr.getAnimationSet(n);
		currentImage = animationSet.getDefault();
	}

	/**
	 * switches the current Animation to the given
	 * 
	 * @param an
	 *            name of the new Animation
	 */
	public void setCurrentAnimation(String an) {
		currentAnimation = animationSet.getAnimation(an);
		animationCounter = 0;
		stretchcounter = 0;
		stretch = currentAnimation.getStretch();
		isimage = false;
		isrunning = true;
	}

	/**
	 * starts an animation
	 * 
	 * @param an
	 *            name of the animation
	 */

	public void start(String an) {
		isrunning = true;
		setCurrentAnimation(an);
	}

	/**
	 * starts an animation with delay
	 * 
	 * @param an
	 *            name of the animation
	 * @param delay
	 *            startdelay
	 */
	public void start(String an, int delay) {
		startdelay = delay;
		waitingAnimation = an;
	}

	/**
	 * stops the animation
	 */
	public void stop() {
		isrunning = false;
		currentImage = new BufferedImage(50, 50, BufferedImage.TYPE_INT_ARGB);
	}

	/**
	 * stops the animation and displays a picture
	 * 
	 * @param stillName
	 *            name of the image to show
	 */
	public void stop(String stillName) {
		isrunning = false;
		currentImage = animationSet.getImage(stillName);
		isimage = true;
	}

	/**
	 * stops the animation with delay
	 * 
	 * @param delay
	 *            stop-delay
	 */
	public void stop(int delay) {
		stopdelay = delay;
		currentImage = new BufferedImage(50, 50, BufferedImage.TYPE_INT_ARGB);
	}

	/**
	 * stops animation with delay an display a given image
	 * 
	 * @param delay
	 *            stop-delay
	 * @param stillName
	 *            picture to display
	 */
	public void stop(int delay, String stillName) {
		stopdelay = delay;
		waitingStopImage = animationSet.getImage(stillName);
	}

	/**
	 * change animation
	 * 
	 * @param aName
	 *            new animation name
	 */
	public void change(String aName) {
		setCurrentAnimation(aName);
	}

	/**
	 * change animation with delay
	 * 
	 * @param delay
	 *            change delay
	 * @param aName
	 *            new animation name
	 */
	public void change(int delay, String aName) {
		changedelay = delay;
		waitingAnimation = aName;
	}

	/**
	 * animates the current animation frame and stretch counter are increased
	 * current image of the animation is set by fetching the frame from the
	 * GameAnimation
	 */
	public void animate() {
		if (stopdelay >= 0) {
			if (stopdelay == 0) {
				if (waitingStopImage != null) {
					currentImage = waitingStopImage;
					isimage = true;
					waitingStopImage = null;
				}
				isrunning = false;
				stopdelay = -1;
			} else {
				stopdelay -= 1;
			}
		}
		if (startdelay >= 0) {
			if (startdelay == 0) {
				setCurrentAnimation(waitingAnimation);
				isrunning = true;
				startdelay = -1;
			} else {
				startdelay -= 1;
			}
		}
		if (changedelay >= 0) {
			if (startdelay == 0) {
				setCurrentAnimation(waitingAnimation);
				changedelay = -1;
			} else {
				changedelay -= 1;
			}
		}
		if (isrunning) {
			if (currentAnimation.end(animationCounter)) {
				currentImage = currentAnimation.getFrame(animationCounter);
				if (stretchcounter < stretch) {
					stretchcounter++;
				} else {
					stretchcounter = 0;
					animationCounter = 0;
				}

			} else {
				currentImage = currentAnimation.getFrame(animationCounter);
				if (stretchcounter < stretch) {
					stretchcounter++;
				} else {
					animationCounter = animationCounter + 1;
					stretchcounter = 0;
				}
			}
		}
	}

	/**
	 * 
	 * @return the current image of the animation
	 */
	public BufferedImage getCurrentImage() {
		return currentImage;
	}

	/**
	 * 
	 * @return the name of the current image
	 */
	public String getCurrentImagePath() {
		return animationSet.getDefaultName();
	}

	/**
	 * 
	 * @return true if animation is running - else false
	 */
	public boolean isRunning() {
		return isrunning;
	}
}
