package lab.main;

import lab.main.TravellingSalesman;
import lab.main.RouteSet;
import java.util.Scanner;

public class Main {

	private static int[][] graph;
	private static RouteSet routes;

	public static void main(String[] args) throws CloneNotSupportedException {
		algorithmMenu();
		Scanner ui = new Scanner(System.in);
		System.out.println("Would you like to run another algorithm? [Y/N]");
		String userInput = "";
		userInput = ui.nextLine().toString().toUpperCase();
		while(ui.nextLine().toString().toUpperCase().equals("Y")) {
			algorithmMenu();
			userInput = ui.nextLine().toString().toUpperCase();
		}

	}
	
	public static void algorithmMenu() throws CloneNotSupportedException{
		String filePath = "src/lab/main/test1.csv";
		Scanner ui = new Scanner(System.in);
		System.out.println("******** Computational Intelligence - TSP Problem ********");
		System.out.println("Which algorithm would you like to run?");
		System.out.println("\t1. Random Search");
		System.out.println("\t2. Local Search");
		System.out.println("\t3. Genetic Algorithm");
		System.out.println("\t4. Artifical Human Immune Algorithm");
		int chosenAlgorithm = ui.nextInt();
		while(chosenAlgorithm != 1 && chosenAlgorithm != 2 && chosenAlgorithm != 3 && chosenAlgorithm != 4) {
			System.out.println("INVALID ALGORITHM CHOSEN.");
			System.out.println("Which algorithm would you like to run?");
			System.out.println("\t1. Random Search");
			System.out.println("\t2. Local Search");
			System.out.println("\t3. Genetic Algorithm");
			System.out.println("\t4. Artifical Human Immune Algorithm");
			chosenAlgorithm = ui.nextInt();
		}
		int runningSeconds = 0;
		switch(chosenAlgorithm) {
			case 1:
				System.out.println("Random search selected.");
				System.out.print("How many seconds would you like to run a random search for?: ");
				runningSeconds = ui.nextInt();
				RandomSearch randUlysses = new RandomSearch(filePath);
				Tuple<String, Double> randUlyResult = randUlysses.timeLimitedRandomSearch(runningSeconds);
				System.out.println("Best route found: " + randUlyResult.getItemOne()  + " with a distance of " + randUlyResult.getItemTwo());
			break;
			
			case 2:
				System.out.println("Local search selected.");
				System.out.print("How many seconds would you like to run a local search for?: ");
				runningSeconds = ui.nextInt();
				LocalSearch ls = new LocalSearch();
				Tuple<String, Double> twoOpt = ls.twoOptLocalSearch(filePath, runningSeconds);
				System.out.println("Best route of 2Opt is: " + twoOpt.getItemOne() + " with a distance of " + twoOpt.getItemTwo());
			break;
			
			case 3:
				System.out.println("Genetic Algorithm selected.");	
				System.out.println("How big would you like each population to be?: ");
				int populationSize = ui.nextInt();
				System.out.println("How many generations would you like the algorithm to run for?: ");
				int numberOfGenerations = ui.nextInt();
				Genetic gen = new Genetic(filePath, populationSize, numberOfGenerations);
				gen.GeneticSearch();
			break;
			
			case 4:
				System.out.println("Artifical Human Immune Algorithm selected.");
				System.out.println("How big would you like the pool size to be?: ");
				int poolSize = ui.nextInt();
				System.out.println("How many generations would you like the algorithm to run for?: ");
				int numberOfRuns = ui.nextInt();
				System.out.println("How many clones would you like created in each generation?: ");
				int numberOfClones = ui.nextInt();
				System.out.println("What would you like to set the p value to?: ");
				int pValue = ui.nextInt();
				System.out.println("How many members of the pool would you like replaced each generation?: ");
				int numberToReplace = ui.nextInt();
				Immune im = new Immune(filePath, poolSize);
				im.search(numberOfRuns, numberOfClones, pValue, numberToReplace);
			break;
		}
	}
}