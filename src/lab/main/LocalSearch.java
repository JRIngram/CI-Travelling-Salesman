package lab.main;

public class LocalSearch {
	
	
	public LocalSearch(){
		
	}
	
	public void twoOpt(String route) {
		String[] splitRoute = route.split("");
		System.out.println("Route: " + route);
		for(int i = 0; i < splitRoute.length; i++) {
			System.out.println(i);
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
			}
		}
	}
}
