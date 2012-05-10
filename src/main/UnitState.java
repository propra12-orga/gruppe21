package main;

/*
 * valid unit states
 */

public enum UnitState {

	BASE_MENU_UNIT(0, "Hauptmenu"),
	LEVEL_MANAGER_UNIT(1, "Spiel-Manager"),	
	TEMPORARY_UNIT(2, "Temporäre Spielanzeige");
		
	private final int value;
	private final String description;
	
	/*
	 * avoid the creation of additional states
	 */
	private UnitState(final int value, final String description) {
		this.value = value;
		this.description = description;
	}
	
	public int getValue() {
		return value;
	}
	
	public String getDescription() {
		return description;
	}
}
