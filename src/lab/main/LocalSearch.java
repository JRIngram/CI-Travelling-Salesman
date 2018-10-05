package lab.main;

import java.util.ArrayList;
import lab.main.Tuple;

public class LocalSearch {
	
	
	public LocalSearch(){
		
	}
	
	public void twoOpt(Tuple<String, Double> routeTuple) {
		String[] splitRoute = routeTuple.getItemOne().split("");
		System.out.println("Route: " + routeTuple.getItemOne());
		ArrayList<String> routeList = new ArrayList();
		routeList.add(routeTuple.getItemOne());
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
				System.out.println("New route:" + newRouteString);
				routeList.add(newRouteString);
			}
		}
	}
}
