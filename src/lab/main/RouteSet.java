package lab.main;

/**
 * 
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
         * Calculates what the size of the set should 
         * @param cityNumber 
         */
	public RouteSet(int cityNumber){
		int factorial = 1;
		int size = 0;
		for(int i = 1; i <= cityNumber; i++){
			factorial *= i;
		}
		set = new String[factorial];
		
	}
	
	public boolean enterRoute(String route){
		String[] characters = route.split("");
		
		//Checks if the proposed route contains any duplicate characters
		for(int i = 0; i < characters.length; i++){
			String testingCharacter = characters[i];
			for(int j = i+1; j < characters.length; j++){
				if(testingCharacter.equals(characters[j])){
					return false;
				}
			}
		}
		
		//Checks if the route is already in the set.
		if(searchForRoute(route) != null){
                    return false;
                }
		set[size] = route;
		size++;
		return true;
	}
	
	public String getRoute(int routeNum){
		return set[routeNum];
	}
	
	public String searchForRoute(String route){
		for(int i = 0; i < set.length;i++){
			if(route.equals(set[i])){
				return set[i];
			}
		}
		return null;
	}
	
}