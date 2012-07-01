package mapeditor;

/**
 * Class for the Tiles in the Tilenavigator saves, position, type and other
 * prosperties of a tile
 * 
 * @author eik
 * 
 */
public class Tile {

	private int posX;
	private int PosY;
	private String name;
	private String type;
	private boolean collision;

	public Tile(int x, int y, String name) {
		this.setPosX(x);
		this.setPosY(y);
		this.setName(name);
	}

	public int getPosX() {
		return posX;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public int getPosY() {
		return PosY;
	}

	public void setPosY(int posY) {
		PosY = posY;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean hasCollision() {
		return collision;
	}

	public void setCollision(boolean collision) {
		this.collision = collision;
	}

}
