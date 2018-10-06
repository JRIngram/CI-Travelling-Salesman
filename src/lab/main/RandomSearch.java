package lab.main;

import java.util.Random;

/**
* Creates the TravellingSalesman problem representation, and then allows for RandomSearch of optimal routes to take place.
* @see TravellingSalesman
* @author JRIngram
*/
public class RandomSearch extends TravellingSalesman {
	
	public RandomSearch(){
		super();
	}
	
	public RandomSearch(String filePath){
		super(filePath);
	}
	
	protected Tuple<String, Double> randomSearch(int numberOfSearches){
		Tuple<String, Double> bestRoute = testRandomRoute(numberOfCities);
		int searchesComplete;
		System.out.println("New best route found: " + bestRoute.getItemOne() + " with a distance of " + bestRoute.getItemTwo());
		for (searchesComplete = 1; searchesComplete < numberOfSearches; searchesComplete++) {
			Tuple<String, Double> randomRoute = testRandomRoute(numberOfCities);
			if (randomRoute.getItemTwo() < bestRoute.getItemTwo()) {
				bestRoute = randomRoute;
				System.out.println("New best route found: " + bestRoute.getItemOne() + " with a distance of " + bestRoute.getItemTwo());
			}
		}
		return bestRoute;
	}
	
	/**
	 * Creates a random route which includes all cities.
	 * @param numberOfCities The number of cities in the graph.
	 * @return The cost of the entire route.
	 */
	protected Tuple<String, Double> testRandomRoute(int numberOfCities) {
		Random rng = new Random();
		double routeCost = 0;
		
		Tuple<String, Double> routeCostTuple = new Tuple<String, Double>("", 0.0);
		while (routeCost == 0) {
			String route = "";
			for (int i = 0; i < numberOfCities; i++) {
				String newCityCharacter = getCityCharacter(rng.nextInt(numberOfCities));
				while (route.contains(newCityCharacter)) {
					newCityCharacter = getCityCharacter(rng.nextInt(numberOfCities));
				}
				route += newCityCharacter;
			}
			routeCostTuple.setItemOne(route);
			routeCostTuple.setItemTwo(getCostOfRoute(route));
			if (routeCostTuple.getItemTwo() > 0) {
				return routeCostTuple;
			}
		}
		return routeCostTuple;
	}
	
	/**
	 * Ensure that if this is being ran that a generic random search hasn't been ran using the same object prior to this search.
	 * @param timeRestraint
	 * @return
	 */
	//TODO Need to create break out if algorithm gets stuck.
	protected Tuple<String,Double> timeLimitedRandomSearch(double timeRestraint){
		//Sets timer.
		long start = System.currentTimeMillis();
		long now = System.currentTimeMillis();
		long timeDifference = (now - start) / 1000;
		
		Tuple<String, Double> bestRoute = new Tuple("", 0.0);
		while(timeDifference < timeRestraint ) {
			Tuple<String, Double> randomRoute = testRandomRoute(numberOfCities);
			now = System.currentTimeMillis();
			timeDifference = (now - start) / 1000;
			if (((randomRoute.getItemTwo() < bestRoute.getItemTwo()) || bestRoute.getItemTwo() == 0.0) && (timeDifference < timeRestraint)) {
				bestRoute = randomRoute;
				System.out.println("[" + timeDifference + "] New best route found: " + bestRoute.getItemOne() + " with a distance of " + bestRoute.getItemTwo());
			}
		}
		
		return bestRoute;
	}

}
