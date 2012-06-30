package multiplayer;

import java.awt.Color;

public interface UpgradeListener {
	public void upgradeSpawned(int x, int y, Color color);

	public void upgradePickedUp(int PosAtList);
}
