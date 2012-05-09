package main;

//	Gültige Spielzustände

public enum ComponentState {
	
	BASE_MENU_COMPONENT(0, "Hauptmenü"),
	LEVEL_MANAGER_COMPONENT(1, "Spiel-Manager"),
	TEMPORARY_COMPONENT(2, "Temporäre Spielanzeige");
		
	private final int value;
	private final String description;
	
//	Hinzufügen weiterer Spielzustände vermeiden
	private ComponentState(final int value, final String description) {
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
