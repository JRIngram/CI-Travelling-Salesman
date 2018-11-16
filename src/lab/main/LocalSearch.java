package lab.main;

import java.util.ArrayList;
import lab.main.Tuple;

public class LocalSearch {


	public LocalSearch(){

	}
	
	/**
	 * Creates a neighbourhood of two-opt swaps, e.g. swapping to location of two cities in a route.
	 * This performs this swap and creates list of all possible two-opt swaps from the route passed.
	 * @param routeTuple The route to perform two-opt swaps on.
	 * @return A list of two-opt routes.
	 */
	public ArrayList<String> createNeighbourhood(Tuple<String, Double> routeTuple){
		ArrayList<String> routeList = new ArrayList<String>();
		String[] splitRoute = routeTuple.getItemOne().split("->");
		
		//Sequentially performs a two-opt swap for all possible configurations of the current route
		//Position i and position j swap places and the route is saved until all routes formed.
		for(int i = 0; i < splitRoute.length; i++) {
			for(int j = i + 1; j < splitRoute.length; j++) {
				String[] newRoute = splitRoute.clone();
				String tempI = splitRoute[i];
				newRoute[i] = splitRoute[j];
				newRoute[j] = tempI;
				String newRouteString = "";
				for(int k = 0; k < splitRoute.length; k++) {
					if(k == 0) {
						newRouteString = newRouteString + newRoute[k];
					}
					else {
						newRouteString = newRouteString + "->" + newRoute[k];	
					}

				}
				routeList.add(newRouteString);
			}
		}
		return routeList;
	}
	
	/**
	 * Calculates the cost of all routes within the passed routeList.
	 * This should be the routelist passed from createNeighbourhood
	 * @see createNeighbourhood
	 * @param routeList A list of all possible two-opt configurations
	 * @return the route with the lowest cost.
	 */
	public Tuple<String, Double> twoOpt(ArrayList<String> routeList) {
		Tuple<String, Double> bestRoute = new Tuple<String, Double>(routeList.get(0), TravellingSalesman.getCostOfRoute(routeList.get(0)));
		for(int i = 1; i < routeList.size(); i++){
			double routeCost = TravellingSalesman.getCostOfRoute(routeList.get(i));
			if(routeCost < bestRoute.getItemTwo()) {
				bestRoute = new Tuple<String, Double>(routeList.get(i), routeCost);
				System.out.println("New best route: " + routeList.get(i) + " with a distance of " + routeCost);
			}
		}
		return bestRoute;	
	}

	/**
	 * Performs two opt local searches in random neighbourhoods until time restraint is passed.
	 * @param filePath File path to generate cities from
	 * @param timeRestraint Time restraint of algorithm
	 * @return The best route found.
	 */
	public Tuple<String, Double> twoOptLocalSearch(String filePath, long timeRestraint){
		RandomSearch rs;
		//Sets timer.
		long start = System.currentTimeMillis();
		long now = System.currentTimeMillis();
		long timeDifference = (now - start) / 1000;

		//Generates a random search
		if(!filePath.equals("")) {
			rs = new RandomSearch(filePath);
		}
		else {
			rs = new RandomSearch();
		}

		//Creates bestRoute tuple from the best result of a one second random search and calculates local optima from this.
		Tuple<String, Double> bestRoute = rs.timeLimitedRandomSearch(1, false);
		bestRoute = findLocalOptima(start, now, timeRestraint, bestRoute);
		timeDifference = (now - start) / 1000;
		System.out.println("[" + timeDifference + "] New best route found from local optima: " + bestRoute.getItemTwo());
		now = System.currentTimeMillis();
		timeDifference = (now - start) / 1000;
		//Searches random neighbourhoods until time restraint is passed.
		while(timeDifference < timeRestraint){
			//Creates bestRoute tuple from the best result of a one second random search and calculates local optima from this.
			Tuple<String, Double> randomLocalOptima = findLocalOptima(start, now, timeRestraint, rs.timeLimitedRandomSearch(1, false));
			now = System.currentTimeMillis();
			timeDifference = (now - start) / 1000;
			System.out.println("[" + timeDifference + "] Local optima: " + randomLocalOptima.getItemTwo() + " vs. " + bestRoute.getItemTwo());
			if(randomLocalOptima.getItemTwo() < bestRoute.getItemTwo()) {
				bestRoute = randomLocalOptima;
				System.out.println("[" + timeDifference + "] New best route found from local optima: " + bestRoute.getItemTwo());
			}
			now = System.currentTimeMillis();
			timeDifference = (now - start) / 1000;
		}
		timeDifference = (now - start) / 1000;
		System.out.println("Completed in " + timeDifference + " seconds!");
		return bestRoute;
	}
	
	/**
	 * Finds the local optima from a given route
	 * @param start Time the function this was called in was originally called
	 * @param now Time this function was called
	 * @param timeRestraint Time restraint for algorithm
	 * @param route Route to find local optima for
	 * @return Local optima route.
	 * @see twoOptLocalSearch
	 */
	private Tuple<String, Double> findLocalOptima(long start, long now, long timeRestraint, Tuple<String, Double> route){
		/*Sets the time difference from start of when function this was called in began running */
		/*and when this function was called.*/ 
		long timeDifference = (now - start) / 1000;
		
		//Creates a two-opt neighbourhood from current best route.
		Tuple<String,Double> bestLocalRoute = route;
		ArrayList<String> bestNeighbourhood = createNeighbourhood(bestLocalRoute);
		
		//Runs until either the best route doesn't change from the entire neighbourhood (local optima found) or algorithm runs out of time
		int unchangedBestRouteCounter = 0;
		while(unchangedBestRouteCounter < bestNeighbourhood.size() && timeDifference < timeRestraint) {
			unchangedBestRouteCounter = 0;
			//Creates a two-opts neighbourhood from current best local route.
			bestNeighbourhood = createNeighbourhood(bestLocalRoute);
			System.out.println("[" + timeDifference + "] NEW NEIGHBOURHOOD!");
			//Calculates routes for entire neighbourhood, and checks for new best route.
			for(int i = 0; i < bestNeighbourhood.size(); i++) {
				Tuple<String, Double> newRoute = new Tuple<String, Double>(bestNeighbourhood.get(i), 0.0);
				newRoute.setItemTwo(TravellingSalesman.getCostOfRoute(bestNeighbourhood.get(i)));
				
				//Calculates time difference.
				now = System.currentTimeMillis();
				timeDifference = (now - start) / 1000;
				if(timeDifference < timeRestraint) {
					if(newRoute.getItemTwo() < bestLocalRoute.getItemTwo() && newRoute.getItemTwo() != 0) {
						bestLocalRoute = newRoute;
					}
					else {
						unchangedBestRouteCounter++;
					}
				}
				else{
					break;
				}
			}
			//Calculates time difference.
			now = System.currentTimeMillis();
			timeDifference = (now - start) / 1000;
			System.out.println("[" + timeDifference + "] Current best route in local optima: " + bestLocalRoute.getItemTwo());
		}
		return bestLocalRoute;
	}

}