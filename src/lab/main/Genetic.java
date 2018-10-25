package lab.main;

import java.util.Arrays;

public class Genetic extends TravellingSalesman {
	
	private Tuple<String, Double>[] population;
	
	public Genetic(int populationSize) {
		super();
		population = createPopulation("", 10, numberOfCities);
	}
	
	public Genetic(String filePath, int populationSize) {
		super(filePath);
		population = createPopulation(filePath, 10, numberOfCities);
		System.out.println("YAY!");
	}
	
	/**
	 * Generates a population for use in the genetic algorithm.
	 * @param filePath The file path for which an initial random search is to be based on. Leave as "" if no file is being used.
	 * @param populationSize The size of the desired population
	 * @return A tuple array containing a population of routes.
	 */
	private Tuple[] createPopulation(String filePath, int populationSize,  int numberOfCities){
		RandomSearch rand;
		Tuple<String, Double>[] newPopulation = new Tuple[populationSize];
		if(filePath != "") {
			rand = new RandomSearch(filePath);
		}
		else {
			rand = new RandomSearch();
		}
		for(int i = 0; i < populationSize; i++){
			newPopulation[i] = rand.testRandomRoute(numberOfCities);
			System.out.println(newPopulation[i].getItemOne() + "," + newPopulation[i].getItemTwo());
		}
		sortPopulation(newPopulation);
		return newPopulation;
	}
	
	private void sortPopulation(Tuple<String, Double>[] population){
		boolean changeOccurred = true;
		while(changeOccurred){
			changeOccurred = false;
			for(int i = 0; i < population.length - 1; i++){
				if(population[i].getItemTwo() > population[i+1].getItemTwo()){
					Tuple<String, Double> temp = population[i+1];
					population[i+1] = population[i];
					population[i] = temp;
					changeOccurred = true;
				}
			}
		}
	}
}
