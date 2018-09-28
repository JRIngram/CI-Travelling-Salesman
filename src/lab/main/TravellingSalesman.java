/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab.pkg1;

import java.util.Random;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *
 * @author jamie
 */
public class TravellingSalesman {
        
        private static double[][] graph;
	private static RouteSet routes;
	
	public TravellingSalesman(){
		// TODO Auto-generated method stub
		graph = new double[4][4];

		
		//a routes
		graph[0][0] = 0; //a --> a
		graph[0][1] = 20;//a --> b
		graph[0][2] = 42;//a --> c
		graph[0][3] = 35;//a --> d
		
		//b routes
		graph[1][0] = 20;
		graph[1][1] = 0;
		graph[1][2] = 30;
		graph[1][3] = 34;
		
		//c routes
		graph[2][0] = 42;
		graph[2][1] = 30;
		graph[2][2] = 0;
		graph[2][3] = 12;
		
		//d routes
		graph[3][0] = 35;
		graph[3][1] = 34;
		graph[3][2] = 12;
		graph[3][3] = 0;
		
		routes = new RouteSet(4);
        System.out.println("Completed");
        getCostOfRoute("ABCD");
        for(int i = 0; i < 23; i++){
        	testRandomRoute(4);
        }

	}
        
        public TravellingSalesman(String filePath){
            File csv = new File(filePath);
            try{
                Scanner validLineScan = new Scanner(csv);
                int validLineCounter = 0;
                
                while(validLineScan.hasNext()){
                    String line = validLineScan.nextLine();
                    String[] splitLine = line.split(",");
                    //Detects if line starts with the city ID
                    if(splitLine[0].matches("[0-9]+")){
                        validLineCounter++;
                    }
                } 
                System.out.println("Number of valid lines = " + validLineCounter);
                graph = new double[validLineCounter][validLineCounter];
                validLineScan.close();
                
                Scanner cityValuesScan = new Scanner(csv);
                String[] cityValues = new String[validLineCounter];
                int index = 0;
                while(cityValuesScan.hasNext()){
                    String line = cityValuesScan.nextLine();
                    String[] splitLine = line.split(",");

                    //Detects if line starts with the city ID
                    if(splitLine[0].matches("[0-9]+")){
                        cityValues[index] = splitLine[1] + "," +splitLine[2];
                        index++;
                    }
                }
                
                for(int fromIndex = 0; fromIndex < cityValues.length; fromIndex++){
                    for(int toIndex = 0; toIndex < cityValues.length; toIndex++){
                        graph[fromIndex][toIndex] = calculateDistance(cityValues[fromIndex], cityValues[toIndex]);
                    }
                }
                routes = new RouteSet(11); //11 is the maximum amount before heapspace error
                getCostOfRoute("ABCD");
                double bestRoute = 0;
                for(int i = 0; i < 5000; i++){
                	double randomRoute = testRandomRoute(16);
                	if(randomRoute < bestRoute || bestRoute == 0) {
                		bestRoute = randomRoute;
                	}
                }
                System.out.println("Best route found is: " + bestRoute);

            }
            catch(FileNotFoundException e){
                System.out.println(e);
            }
            
        }
	
	public static double getCostOfRoute(String route){
		//Search for routes or enter route into the route set.
		//current city + next city location
		//set current city to next city
		//set route length
                
                //If route not in set, enter the route.
		if(routes.enterRoute(route) != true){
                    return 0;
		}
                
                String[] splitRoute = route.split("");
                int from = 0;
                int to = 0;
                double routeCost = 0;
                //for each entry, check the distance between that and the next entry
                for(int i = 0; i < splitRoute.length; i++){
                    int nextCity = i + 1;
                    from = getCity(splitRoute[i]);
                    to = 0;
                    if(nextCity < splitRoute.length){
                        to = getCity(splitRoute[nextCity]);
                    }
                    else{
                        to = getCity(splitRoute[0]);
                    }
                    routeCost += graph[from][to];
                }
		return routeCost;
	}
        
        private static int getCity(String city){
            int cityNumber = 0;
            switch(city){
                case "A": cityNumber = 0;
                break;
                
                case "B": cityNumber = 1;
                break;
                
                case "C": cityNumber = 2;
                break;
                
                case "D": cityNumber = 3;
                break;
                
                case "E": cityNumber = 4;
                break;
                
                case "F": cityNumber = 5;
                break;
                
                case "G": cityNumber = 6;
                break;
                
                case "H": cityNumber = 7;
                break;
                
                case "I": cityNumber = 8;
                break;
                
                case "J": cityNumber = 9;
                break;
                
                case "K": cityNumber = 10;
                break;
                
                case "L": cityNumber = 11;
                break;
                
                case "M": cityNumber = 12;
                break;
                
                case "N": cityNumber = 13;
                break;
                
                case "O": cityNumber = 14;
                break;
                
                case "P": cityNumber = 15;
                break;
                
                default: cityNumber = 0;
                break;
            }
            return cityNumber;
        }
        
        private static String getCityCharacter(int cityNumber){
            String cityCharacter;
            //Switch statement to determine cityCharacter
            switch(cityNumber){
                case 0: cityCharacter = "A";
                break;
                
                case 1: cityCharacter = "B";
                break;
                
                case 2: cityCharacter = "C";
                break;
                
                case 3: cityCharacter = "D";
                break;
                
                case 4: cityCharacter = "E";
                break;
                
                case 5: cityCharacter = "F";
                break;
                
                case 6: cityCharacter = "G";
                break;
                
                case 7: cityCharacter = "H";
                break;
                
                case 8: cityCharacter = "I";
                break;
                
                case 9: cityCharacter = "J";
                break;
                
                case 10: cityCharacter = "K";
                break;
                
                case 11: cityCharacter = "L";
                break;
                
                case 12: cityCharacter = "M";
                break;
                
                case 13: cityCharacter = "N";
                break;
                
                case 14: cityCharacter = "O";
                break;
                
                case 15: cityCharacter = "P";
                break;
                
                default: cityCharacter = "A";
                break;
                
            }
            return cityCharacter;
        }
        
        public double testRandomRoute(int cityNumbers){
            Random rng = new Random();
            double routeCost = 0;
            while(routeCost == 0){
                String route = "";
                for(int i = 0; i < cityNumbers; i++){
                    String newCityCharacter = getCityCharacter(rng.nextInt(cityNumbers));
                    while(route.contains(newCityCharacter)){
                        newCityCharacter = getCityCharacter(rng.nextInt(cityNumbers));
                    }
                    route += newCityCharacter;
                }
                routeCost = getCostOfRoute(route);
                if(routeCost > 0){
                    System.out.println("Cost of route " + route + " is " + routeCost);    
                    return routeCost;
                }
            }
            return routeCost;
        }
        
        public double calculateDistance(String a, String b){
            float xa = 0;
            float xb = 0;
            float ya = 0;
            float yb = 0;
            
            String[] splitA = a.split(",");
            xa = Float.parseFloat(splitA[0]);
            ya = Float.parseFloat(splitA[1]);
            
            String[] splitB = b.split(",");
            xb = Float.parseFloat(splitB[0]);
            yb = Float.parseFloat(splitB[1]);
            
            double xDistance = Math.pow((xb-xa), 2);
            double yDistance = Math.pow((yb - ya), 2);
            double totalDistance = Math.sqrt(Math.abs(xDistance - yDistance));
            
            return totalDistance;
        }
    
}
