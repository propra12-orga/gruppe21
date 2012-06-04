package main;

/*
 * responsible for managing the game units
 */

public class UnitNavigator {

	public static final int NUM_OF_UNIT_STATES = 3;
	private int activeUnit;
	private GraphicalGameUnit[] gameUnits;
	private boolean requestTermination = false;
	private static UnitNavigator navigator;

	private UnitNavigator() {
		gameUnits = new GraphicalGameUnit[NUM_OF_UNIT_STATES];
		activeUnit = UnitState.BASE_MENU_UNIT.getValue();
	}

	public static UnitNavigator getNavigator() {
		if (navigator == null) {
			navigator = new UnitNavigator();
		}
		return navigator;
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
	 * allows to terminate the infinite loop in mainPanel which will lead to
	 * System.exit(0)
	 */
	public void terminateGame() {
		requestTermination = true;
	}

	public boolean terminationRequested() {
		return requestTermination;
	}

	public void removeGameUnit(UnitState state) {
		gameUnits[state.getValue()] = null;
	}

}
