package main;

//	Gültige Spielzustände

public enum ComponentState {
<<<<<<< HEAD
	
	BASE_MENU_COMPONENT(0, "Hauptmenü"),
=======

	BASE_MENU_COMPONENT(0, "Hauptmenu"),
>>>>>>> 12aaa99fa1817303473e6465f1832c7d63ebee89
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
