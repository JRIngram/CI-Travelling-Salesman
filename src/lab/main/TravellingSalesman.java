/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab.main;

import java.util.Random;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *
 * @author JRIngram
 */
public class TravellingSalesman {
	
	/**
	 * An array representing the graph used in the Travelling Salesman Problem.
	 */
	private static double[][] graph;
	
	/**
	 * A set containing the searched routes.
	 * @see RouteSet
	 */
	private static RouteSet routes;
	
	private int numberOfCities;

	/**
	 * Hardcoded representation of the travelling salesman problem.
	 * Creates a 4 x 4 2D Array which contains the values of a route between two nodes.
	 * 	e.g. graph[0][1] would contain the value needed to travel from node 0 to node 1.
	 */
	public TravellingSalesman() {
		graph = new double[4][4];

		// a routes
		graph[0][0] = 0; // a --> a
		graph[0][1] = 20;// a --> b
		graph[0][2] = 42;// a --> c
		graph[0][3] = 35;// a --> d

		// b routes
		graph[1][0] = 20;
		graph[1][1] = 0;
		graph[1][2] = 30;
		graph[1][3] = 34;

		// c routes
		graph[2][0] = 42;
		graph[2][1] = 30;
		graph[2][2] = 0;
		graph[2][3] = 12;

		// d routes
		graph[3][0] = 35;
		graph[3][1] = 34;
		graph[3][2] = 12;
		graph[3][3] = 0;

		routes = new RouteSet(4);
		numberOfCities = 4;

	}

	/**
	 * Loads in a CSV file containing x and y coordinates - each entry will be a "city" in the travelling salesman problem.
	 * Once this has been completed, 5000 of the routes are tests, and a print statement shows the best route found.
	 * @param filePath The file path for the csv file being loaded in.
	 */
	public TravellingSalesman(String filePath) {
		File csv = new File(filePath);
		try {
			Scanner validLineScan = new Scanner(csv);
			int validLineCounter = 0;

			while (validLineScan.hasNext()) {
				String line = validLineScan.nextLine();
				String[] splitLine = line.split(",");
				
				// Detects if line starts with the city ID - counter increases if it does.
				if (splitLine[0].matches("[0-9]+")) {
					validLineCounter++;
				}
			}
			
			System.out.println("Number of valid lines = " + validLineCounter);
			
			/*
			 * Creates an n * n 2D array, where n is the number of valid lines.
			 * This array represents the graph for the travelling salesman problem.
			 */
			graph = new double[validLineCounter][validLineCounter];
			validLineScan.close();
			numberOfCities = validLineCounter;

			
			Scanner cityValuesScan = new Scanner(csv);
			String[] cityValues = new String[validLineCounter];
			int index = 0;
			
			//Scans the file, and where there is a valid line it stores the x and y coordinates of that line in cityValues.
			while (cityValuesScan.hasNext()) {
				String line = cityValuesScan.nextLine();
				String[] splitLine = line.split(",");

				// Detects if line starts with the city ID
				if (splitLine[0].matches("[0-9]+")) {
					cityValues[index] = splitLine[1] + "," + splitLine[2];
					index++;
				}
			}
			cityValuesScan.close();
			/*	For each item in the cityValues array, the distance from the city to all other cities is calculated one by one.
			 *	This is then stored in the graph, as graph[cityTravellingFrom][cityTravellingTo]
			 *	e.g. graph[0][1] would store the distance from city 0 to city 1.  
			 */
			for (int fromIndex = 0; fromIndex < cityValues.length; fromIndex++) {
				for (int toIndex = 0; toIndex < cityValues.length; toIndex++) {
					graph[fromIndex][toIndex] = calculateDistance(cityValues[fromIndex], cityValues[toIndex]);
				}
			}
			//Creates a RouteSet that can store 11! routes.
			routes = new RouteSet(11); // 11 is the maximum amount before heapspace error
			
		} catch (FileNotFoundException e) {
			System.out.println(e);
		}

	}

	/**
	 * Calculates the distance travelled in the route.
	 * It enters the route into the route set so a route isn't checked twice.
	 * @param route A string value, such as "ABCD", which calculates the total route travelled.
	 * @return The distance travelled in the route.
	 */
	public static double getCostOfRoute(String route) {

		// If route not in set, enter the route.
		if (routes.enterRoute(route) != true) {
			return 0;
		}

		String[] splitRoute = route.split("");
		int from = 0;
		int to = 0;
		double routeCost = 0;
		
		// For each journey between cities, check the distance between that and the next entry
		for (int i = 0; i < splitRoute.length; i++) {
			int nextCity = i + 1;
			from = getCity(splitRoute[i]);
			to = 0;
			if (nextCity < splitRoute.length) {
				to = getCity(splitRoute[nextCity]);
			} else {
				to = getCity(splitRoute[0]);
			}
			routeCost += graph[from][to];
		}
		return routeCost;
	}

	/**
	 * Turns a Alphabetical representation of a city into a numerical representation, e.g. A = B.
	 * If an invalid String is entered, then the default case is 0.
	 * @param city A single letter
	 * @return The numerical representation of a city.
	 */
	private static int getCity(String city) {
		int cityNumber = 0;
		switch (city) {
		case "A":
			cityNumber = 0;
			break;

		case "B":
			cityNumber = 1;
			break;

		case "C":
			cityNumber = 2;
			break;

		case "D":
			cityNumber = 3;
			break;

		case "E":
			cityNumber = 4;
			break;

		case "F":
			cityNumber = 5;
			break;

		case "G":
			cityNumber = 6;
			break;

		case "H":
			cityNumber = 7;
			break;

		case "I":
			cityNumber = 8;
			break;

		case "J":
			cityNumber = 9;
			break;

		case "K":
			cityNumber = 10;
			break;

		case "L":
			cityNumber = 11;
			break;

		case "M":
			cityNumber = 12;
			break;

		case "N":
			cityNumber = 13;
			break;

		case "O":
			cityNumber = 14;
			break;

		case "P":
			cityNumber = 15;
			break;

		default:
			cityNumber = 0;
			break;
		}
		return cityNumber;
	}

	/**
	 * Converts a numerical representation into an alphabetical representation.
	 * @param cityNumber The numerical representation of a city.
	 * @return The alphabetical representation of a city.
	 */
	private static String getCityCharacter(int cityNumber) {
		String cityCharacter;
		// Switch statement to determine cityCharacter
		switch (cityNumber) {
		case 0:
			cityCharacter = "A";
			break;

		case 1:
			cityCharacter = "B";
			break;

		case 2:
			cityCharacter = "C";
			break;

		case 3:
			cityCharacter = "D";
			break;

		case 4:
			cityCharacter = "E";
			break;

		case 5:
			cityCharacter = "F";
			break;

		case 6:
			cityCharacter = "G";
			break;

		case 7:
			cityCharacter = "H";
			break;

		case 8:
			cityCharacter = "I";
			break;

		case 9:
			cityCharacter = "J";
			break;

		case 10:
			cityCharacter = "K";
			break;

		case 11:
			cityCharacter = "L";
			break;

		case 12:
			cityCharacter = "M";
			break;

		case 13:
			cityCharacter = "N";
			break;

		case 14:
			cityCharacter = "O";
			break;

		case 15:
			cityCharacter = "P";
			break;

		default:
			cityCharacter = "A";
			break;

		}
		return cityCharacter;
	}

	/**
	 * Creates a random route which includes all cities.
	 * @param numberOfCities The number of cities in the graph.
	 * @return The cost of the entire route.
	 */
	public double testRandomRoute(int numberOfCities) {
		Random rng = new Random();
		double routeCost = 0;
		while (routeCost == 0) {
			String route = "";
			for (int i = 0; i < numberOfCities; i++) {
				String newCityCharacter = getCityCharacter(rng.nextInt(numberOfCities));
				while (route.contains(newCityCharacter)) {
					newCityCharacter = getCityCharacter(rng.nextInt(numberOfCities));
				}
				route += newCityCharacter;
			}
			routeCost = getCostOfRoute(route);
			if (routeCost > 0) {
				System.out.println("Cost of route " + route + " is " + routeCost);
				return routeCost;
			}
		}
		return routeCost;
	}

	/**
	 * Calculates the distance using pythagoras' theorem:
	 * sqrt((xb - xa)^2 - (yb - ya)^2)
	 * @param a City the agent is travelling from.
	 * @param b	City the agent is travelling to.
	 * @return	
	 */
	public double calculateDistance(String a, String b) {
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

		double xDistance = Math.pow((xb - xa), 2);
		double yDistance = Math.pow((yb - ya), 2);
		double totalDistance = Math.sqrt(Math.abs(xDistance - yDistance));

		return totalDistance;
	}
	
	public double randomSearch(int numberOfSearches){
		double bestRoute = testRandomRoute(numberOfCities);
		int searchesComplete;
		for (searchesComplete = 1; searchesComplete < numberOfSearches; searchesComplete++) {
			double randomRoute = testRandomRoute(numberOfCities);
			if (randomRoute < bestRoute) {
				bestRoute = randomRoute;
			}
		}
		return bestRoute;
	}

}
