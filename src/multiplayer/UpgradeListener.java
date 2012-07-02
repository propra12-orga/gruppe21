package multiplayer;

/**
 * This interface implements Events for multiplayer purposes
 * 
 * @author Dorian
 * 
 */
public interface UpgradeListener {
	public void upgradeSpawned(int x, int y, String color, String MPID);

	public void upgradePickedUp(int PosAtList, String MPID);
}
