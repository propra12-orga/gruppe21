package main;

// 	Verwaltung der GameUnits

public class UnitNavigator {
	
	private int activeUnit;
	private GraphicalGameUnit[] gameUnits;

	public MainPanel mainPanel;
	
	public UnitNavigator(MainPanel mainPanel) {
		gameUnits = new GraphicalGameUnit[GameConstants.NUM_OF_UNIT_STATES];
		activeUnit = UnitState.BASE_MENU_UNIT.getValue();		
		this.mainPanel = mainPanel;
	}

//	Liefert aktive Einheit 
	public GraphicalGameUnit getActiveUnit() {
		return gameUnits[activeUnit];		
	}
	
//	Ändert bzw fügt neue Einheit am angegebenen Index (state) in gameUnits ein
	public void addGameUnit(GraphicalGameUnit newComponent, UnitState state) {
		gameUnits[state.getValue()] = newComponent;
	}
	
//	Setzt den aktiven Spielzustand (und damit die aktive Einheit)
	public void set(UnitState state) {
		activeUnit = state.getValue();
	}
	
//	Ermoeglicht die Beendigung des Spiels	
	public void terminateGame() {
		mainPanel.stop();
	}
	
}
