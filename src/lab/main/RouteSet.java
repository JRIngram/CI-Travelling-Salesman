package lab.main;

/**
 * A set which contains the searched route.
 * It will not allow multiple of the same routes to be stored in it.
 * @author JRIngram
 */
public class RouteSet {

	/**
	 * Contains a set of searched routes.
	 */
	private String[] set;

	/**
	 * The current size of the set.
	 */
	private int size;

	/**
	 * Calculates what the size of the set should be to store all roots, cityNumber!.
	 * 
	 * @param cityNumber The number of cities in the travelling salesman problem.
	 */
	public RouteSet(int cityNumber) {
		//TODO change the name of the parameter to be more representational.
		int factorial = 1;
		size = 0;
		for (int i = 1; i <= cityNumber; i++) {
			factorial *= i;
		}
		set = new String[factorial];

	}
	
	/**
	 * Takes the String representation of a route, such as ABCD, and inserts it into the set.
	 * @param route The String representation of the route, such as ABCD.
	 * @return True if the route is not already in the set and no cities are duplicates. False if there set is already present in the set or a city would be visited twice in the route.
	 */
	public boolean enterRoute(String route) {
		String[] characters = route.split("");

		// Checks if the proposed route contains any duplicate characters
		for (int i = 0; i < characters.length; i++) {
			String testingCharacter = characters[i];
			for (int j = i + 1; j < characters.length; j++) {
				if (testingCharacter.equals(characters[j])) {
					return false;
				}
			}
		}

		// Checks if the route is already in the set.
		if (searchForRoute(route) != null) {
			return false;
		}
		set[size] = route;
		size++;
		return true;
	}
	
	/**
	 * Returns the String representation of the route, such as "ABCD".
	 * @param routeNum The index of the route being searched for.
	 * @return The String representation of the route, such as "ABCD".
	 */
	public String getRoute(int routeNum) {
		return set[routeNum];
	}
	
	/**
	 * Searches the set for the string representation of a route.
	 * @param route The string representation of a route, such as "ABCD".
	 * @return Null if the route is not present in the set, or the string representation of the route, such as "ABCD".
	 *
	 */
	public String searchForRoute(String route) {
		//TODO change method to be a boolean, return true if present and false if not.
		for (int i = 0; i < set.length; i++) {
			if (route.equals(set[i])) {
				return set[i];
			}
		}
		return null;
	}

}