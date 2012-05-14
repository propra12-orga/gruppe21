package main;

/*
 * responsible for managing the game units
 */

public class UnitNavigator {
	
	private int activeUnit;
	private GraphicalGameUnit[] gameUnits;

	public MainPanel mainPanel;
	
	public UnitNavigator(MainPanel mainPanel) {
		gameUnits = new GraphicalGameUnit[GameConstants.NUM_OF_UNIT_STATES];
		activeUnit = UnitState.BASE_MENU_UNIT.getValue();		
		this.mainPanel = mainPanel;
	}

	/*
	 * returns active unit
	 */
	public GraphicalGameUnit getActiveUnit() {
		return gameUnits[activeUnit];		
	}
	
	/*
	 * returns unit at given unit state
	 */
	public GraphicalGameUnit getUnitAt(UnitState state) {
		return gameUnits[state.getValue()];
	}
	
	/*
	 * adds new game unit to the array
	 */
	public void addGameUnit(GraphicalGameUnit newComponent, UnitState state) {
		gameUnits[state.getValue()] = newComponent;
	}
	
	/*
	 * sets the active state/game unit
	 */
	public void set(UnitState state) {
		activeUnit = state.getValue();
	}
	
	/*
	 * allows to terminate the infinite loop in mainPanel
	 * which will lead to System.exit(0)
	 */
	public void terminateGame() {
		mainPanel.stop();
	}
	
}
