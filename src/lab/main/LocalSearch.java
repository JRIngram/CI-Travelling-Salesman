package lab.main;

import java.util.ArrayList;
import lab.main.Tuple;

public class LocalSearch {
	
	
	public LocalSearch(){
		
	}
	
	public Tuple<String, Double> twoOpt(Tuple<String, Double> routeTuple) {
		String[] splitRoute = routeTuple.getItemOne().split("");
		System.out.println("Route: " + routeTuple.getItemOne());
		ArrayList<String> routeList = new ArrayList<String>();
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
		Tuple<String, Double> bestRoute = new Tuple<String, Double>(routeTuple.getItemOne(), routeTuple.getItemTwo());
		for(int i = 0; i < routeList.size(); i++){
			double routeCost = TravellingSalesman.getCostOfRoute(routeList.get(i));
			if(routeCost < bestRoute.getItemTwo()) {
				bestRoute = new Tuple<String, Double>(routeList.get(i), routeCost);
				System.out.println("New best route: " + routeList.get(i) + " with a distance of " + routeCost);
			}
		}
		return bestRoute;	
	}
}
