package lab.main;

import java.util.ArrayList;
import lab.main.Tuple;

public class LocalSearch {
	
	
	public LocalSearch(){
		
	}
	
	public ArrayList<String> createNeighbourhood(Tuple<String, Double> routeTuple){
		ArrayList<String> routeList = new ArrayList<String>();
		String[] splitRoute = routeTuple.getItemOne().split("");
		for(int i = 0; i < splitRoute.length; i++) {
			for(int j = i + 1; j < splitRoute.length; j++) {
				String[] newRoute = splitRoute.clone();
				String tempI = splitRoute[i];
				newRoute[i] = splitRoute[j];
				newRoute[j] = tempI;
				String newRouteString = "";
				for(int k = 0; k < splitRoute.length; k++) {
					newRouteString = newRouteString + newRoute[k];
				}
				routeList.add(newRouteString);
			}
		}
		return routeList;
	}
	
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
	
	//TODO Add a break if local optima is found.
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
		
		//Creates bestRoute tuple from the best result of the random search.
		Tuple<String, Double> bestRoute = rs.timeLimitedRandomSearch(1);
		bestRoute = findLocalOptima(start, now, timeRestraint, bestRoute);
		now = System.currentTimeMillis();
		timeDifference = (now - start) / 1000;
		System.out.println("Completed in " + timeDifference + " seconds!");
		return bestRoute;
	}
	
	private Tuple<String, Double> findLocalOptima(long start, long now, long timeRestraint, Tuple<String, Double> route){
		long timeDifference = (now - start) / 1000;
		Tuple<String,Double> bestLocalRoute = route;
		while(timeDifference < timeRestraint) {
			ArrayList<String> bestNeighbourhood = createNeighbourhood(bestLocalRoute);
			System.out.println("NEW NEIGHBOURHOOD!");
			for(int i = 0; i < bestNeighbourhood.size(); i++) {
				Tuple<String, Double> newRoute = new Tuple<String, Double>(bestNeighbourhood.get(i), 0.0);
				newRoute.setItemTwo(TravellingSalesman.getCostOfRoute(bestNeighbourhood.get(i)));
				//Calculates time difference.
				now = System.currentTimeMillis();
				timeDifference = (now - start) / 1000;
				if(timeDifference < timeRestraint) {
					if(newRoute.getItemTwo() < bestLocalRoute.getItemTwo() && newRoute.getItemTwo() != 0) {
						bestLocalRoute = newRoute;
						System.out.println("[" + timeDifference + "] NEW BEST ROUTE LOCAL: " + bestLocalRoute.getItemOne() + " : " + bestLocalRoute.getItemTwo());
					}
				}
				else{
					break;
				}
			}
		}
		return bestLocalRoute;
	}
}
