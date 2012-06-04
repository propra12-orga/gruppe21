package main;

/**
 * Manages all GraphicalGameUnits. <br>
 * <br>
 * Because there has to be only one UnitNavigator per Game (that needs to be
 * easily accessible to all GraphicalGameUnits), this class employs the
 * Singleton pattern as can be seen from the use of a private constructor and
 * the static UnitNavigator class variable. <br>
 * <br>
 * The UnitNavigator uses an array to hold all GraphicalGameUnits that need to
 * coexist (like the MainMenuUnit and the LevelManagerUnit) and offers methods
 * to add and to remove GraphicalGameUnits respectively. To access a particular
 * slot or to change the active unit, the UnitNavigator makes use of the so
 * called UnitStates to prevent other classes from provoking index out of bounds
 * exceptions.
 * 
 * @author tohei
 * 
 */
public class UnitNavigator {

	/**
	 * Constant defining the number of slots to store GraphicalGameUnits.
	 */
	public static final int NUM_OF_UNIT_STATES = 3;
	/**
	 * Index of the active GraphicalGameUnit.
	 */
	private int activeUnit;
	/**
	 * Container for GraphicalGameUnits.
	 */
	private GraphicalGameUnit[] gameUnits;
	/**
	 * Allows to any GraphicalGameUnit to request termination of the update-loop
	 * in MainPanel.
	 */
	private boolean requestTermination = false;
	/**
	 * Class variable according to the Singleton pattern
	 */
	private static UnitNavigator navigator;

	/**
	 * Private constructor to prevent other classes from creating more than one
	 * UnitNavigator.
	 */
	private UnitNavigator() {
		gameUnits = new GraphicalGameUnit[NUM_OF_UNIT_STATES];
		activeUnit = UnitState.BASE_MENU_UNIT.getValue();
	}

	/**
	 * Returns navigator or creates a new UnitNavigator if no one has been used
	 * yet.
	 * 
	 * @return UnitNavigator.
	 */
	public static UnitNavigator getNavigator() {
		if (navigator == null) {
			navigator = new UnitNavigator();
		}
		return navigator;
	}

	/**
	 * Grants access to the active GraphicalGameUnit.
	 * 
	 * @return active GraphicalGameUnit.
	 */
	public GraphicalGameUnit getActiveUnit() {
		return gameUnits[activeUnit];
	}

	/**
	 * Returns unit at given UnitState
	 * 
	 * @param state
	 *            Index
	 * @return GraphicalGameUnit at state
	 */
	public GraphicalGameUnit getUnitAt(UnitState state) {
		return gameUnits[state.getValue()];
	}

	/**
	 * Adds new GraphicalGameUnit to the array.
	 * 
	 * @param newComponent
	 *            GraphicalGameUnit to add.
	 * @param state
	 *            Index
	 */
	public void addGameUnit(GraphicalGameUnit newComponent, UnitState state) {
		gameUnits[state.getValue()] = newComponent;
	}

	/**
	 * Sets the active state/game unit
	 * 
	 * @param state
	 *            Index
	 */
	public void set(UnitState state) {
		activeUnit = state.getValue();
	}

	/**
	 * Allows to request termination of the game.
	 */
	public void terminateGame() {
		requestTermination = true;
	}

	/**
	 * Called by MainPanel to determine whether a GraphicalGameUnit has
	 * requested termination of the update loop.
	 * 
	 * @return value of requestTermination
	 */
	public boolean terminationRequested() {
		return requestTermination;
	}

	/**
	 * Removes GraphicalGameUnit at given UnitState.
	 * 
	 * @param state
	 *            Index
	 */
	public void removeGameUnit(UnitState state) {
		gameUnits[state.getValue()] = null;
	}

}
