package mapobjects;

public class Direction {

	private boolean up = false;
	private boolean down = false;
	private boolean left = false;
	private boolean right = false;

	public boolean isUp() {
		return up;
	}

	public boolean isDown() {
		return down;
	}

	public boolean isLeft() {
		return left;
	}

	public boolean isRight() {
		return right;
	}

	public void setUp(Boolean s) {
		up = s;
	}

	public void setDown(Boolean s) {
		down = s;
	}

	public void setLeft(Boolean s) {
		left = s;
	}

	public void setRight(Boolean s) {
		right = s;
	}

}
