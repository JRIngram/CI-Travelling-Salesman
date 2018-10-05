package lab.main;

import java.util.ArrayList;

public class LocalSearch {
	
	
	public LocalSearch(){
		
	}
	
	public void twoOpt(String route) {
		String[] splitRoute = route.split("");
		System.out.println("Route: " + route);
		ArrayList<String> routeList = new ArrayList();
		routeList.add(route);
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
