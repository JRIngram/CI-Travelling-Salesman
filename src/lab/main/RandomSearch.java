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
	
	public Tuple<String, Double> randomSearch(int numberOfSearches){
		Tuple<String, Double> bestRoute = testRandomRoute(numberOfCities);
		int searchesComplete;
		for (searchesComplete = 1; searchesComplete < numberOfSearches; searchesComplete++) {
			Tuple<String, Double> randomRoute = testRandomRoute(numberOfCities);
			if (randomRoute.getItemTwo() < bestRoute.getItemTwo()) {
				bestRoute = randomRoute;
			}
		}
		return bestRoute;
	}
	
	/**
	 * Creates a random route which includes all cities.
	 * @param numberOfCities The number of cities in the graph.
	 * @return The cost of the entire route.
	 */
	public Tuple<String, Double> testRandomRoute(int numberOfCities) {
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
				System.out.println("Cost of route " + routeCostTuple.getItemOne() + " is " + routeCostTuple.getItemTwo());
				return routeCostTuple;
			}
		}
		return routeCostTuple;
	}

}
