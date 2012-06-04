package main;

/**
 * Defines valid UnitStates that represent slots (index positions) in the
 * UnitNavigator's gameUnits array. There are three general UnitStates: <br>
 * <ul>
 * <LI>BASE_MENU_UNIT - the main game menu.
 * <LI>LEVEL_MANAGER_UNIT - this slot is to be used for the LevelManagerUnit
 * (manages the single-player campaign) or a multiplayer equivalent.
 * <LI>TEMPORARY_UNIT - for temporary GraphicalGameUnits such as TransitionUnit
 * or WorldMapUnit.
 * </ul>
 * 
 * @author tohei
 * 
 */
public enum UnitState {

	BASE_MENU_UNIT(0, "Hauptmenu"), LEVEL_MANAGER_UNIT(1, "Spiel-Manager"), TEMPORARY_UNIT(
			2, "Temporäre Spielanzeige");

	/**
	 * Every UnitState represents an index position.
	 */
	private final int value;
	/**
	 * Description for readability.
	 */
	private final String description;

	/**
	 * Private constructor prevents other classes from defining their own
	 * UnitStates
	 */
	private UnitState(final int value, final String description) {
		this.value = value;
		this.description = description;
	}

	/**
	 * Returns id of the respective UnitState.
	 * 
	 * @return UnitState value
	 */
	public int getValue() {
		return value;
	}

	/**
	 * Returns description of a particular UnitState
	 * 
	 * @return description
	 */
	public String getDescription() {
		return description;
	}
}
