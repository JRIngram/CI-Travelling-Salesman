package lab.main;

import java.util.Arrays;
import java.util.Random;
import java.util.ArrayList;

public class Genetic extends TravellingSalesman {

	private Tuple<String, Double>[] population;
	private int generationLimit = 0;

	public Genetic(int populationSize, int generationLimit) {
		super();
		population = createPopulation("", 10, numberOfCities);
		this.generationLimit = generationLimit;
	}

	public Genetic(String filePath, int populationSize, int generationLimit) {
		super(filePath);
		population = createPopulation(filePath, 10, numberOfCities);
		this.generationLimit = generationLimit;
	}

	public void GeneticSearch(){
		for(int i = 0; i < generationLimit; i++) {
			System.out.println("Generation: " + (i+1));
			Tuple<String, Double>[] parents = parentSelection(population);
			population = createNextGeneration(parents);
			sortPopulation(population);
			System.out.println("[Gen: " + i + "] Current best route: " + population[0].getItemOne() + " with a cost of " + population[0].getItemTwo());
		}
		for(int i = 0; i < population.length; i++){
			System.out.println(population[i].getItemOne() + " : " + population[i].getItemTwo());
		}
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

	/**
	 * Sorts the current population
	 * @param population currentPopulation of the GA.
	 */
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

	/**
	 * Chooses the parents for the next generation, based on a 2k tournament search
	 * @param population Current population to pick parents from.
	 * @return
	 */
	private Tuple[] parentSelection(Tuple<String, Double>[] population) {
		Tuple<String, Double>[] parents = new Tuple[population.length / 2];
		ArrayList<Tuple<String, Double>> tournamentMembers = new ArrayList<Tuple<String, Double>>();
		Random rng = new Random();
		for(int i = 0; i < population.length; i++){
			tournamentMembers.add(population[i]);
		}
		//Perform tournament
		for(int i = 0; i < parents.length; i++) {
			Tuple<String, Double> participantOne = tournamentMembers.remove(rng.nextInt(tournamentMembers.size()));
			Tuple<String, Double> participantTwo = tournamentMembers.remove(rng.nextInt(tournamentMembers.size()));
			if(participantOne.getItemTwo() < participantTwo.getItemTwo()){
				parents[i] = participantOne;
			}
			else {
				parents[i] = participantTwo;
			}
		}
		return parents;
	}

	private Tuple[] createNextGeneration(Tuple<String, Double>[] parents){
		ArrayList<Tuple<String,Double>> newGeneration = new ArrayList<Tuple<String,Double>>();
		Tuple[] nextGeneration = new Tuple[population.length];
		for(int i = 0; i < parents.length; i++) {
			Tuple[] children = new Tuple[2];
			if(i+1 < parents.length) {
				children = breed(parents[i], parents[i + 1]);
			}
			else {
				children = breed(parents[i], parents[i - 1]);
			}
			newGeneration.add(children[0]);
			newGeneration.add(children[1]);
		}
		for(int i = 0; i < newGeneration.size(); i++) {
			nextGeneration[i] = newGeneration.get(i);
		}

		return nextGeneration;
	}

	private Tuple<String,Double>[] breed(Tuple<String, Double> parentOne, Tuple<String,Double> parentTwo){
		Tuple<String, Double> childOne = new Tuple<String, Double>("", 0.0);
		Tuple<String, Double> childTwo = new Tuple<String, Double>("", 0.0);
		Random rng = new Random();
		String[] parentOneRouteArray = parentOne.getItemOne().split("");
		String[] parentTwoRouteArray = parentTwo.getItemOne().split("");
		String[] childOneRouteArray = new String[parentOneRouteArray.length];
		String[] childTwoRouteArray = new String[parentTwoRouteArray.length];
		
		//Copy a random selection from one parent. Mask those cities in the other parent.
		//Place the selection in a set part of the child. 
		//Add the non-masked children from parent two in the order they are found.
		int startOfRouteSubset = rng.nextInt(parentOneRouteArray.length - (parentOneRouteArray.length / 2));
		String[] parentOneSubstring = new String[parentOneRouteArray.length];
		String[] parentTwoSubstring = new String[parentTwoRouteArray.length];
		
		//Inserts the substrings at the appropriate locations in the two children.
		for(int i = startOfRouteSubset; i < startOfRouteSubset + (parentOneRouteArray.length / 2); i++) {
			childOneRouteArray[i] = parentOneRouteArray[i];
			childTwoRouteArray[i] = parentTwoRouteArray[i];
		}
		
		//Finish creating child one
		for(int i = 0; i < parentOneRouteArray.length; i++) {
			boolean alreadyContained = false;
			Integer nextEmptyCell = null;
			for(int j = 0; j < childOneRouteArray.length; j++) {
				if(childOneRouteArray[j] == null && nextEmptyCell == null) {
					nextEmptyCell = j;
					break;
				}
			}
			for(int j = 0; j < childOneRouteArray.length; j++) {
				if(parentTwoRouteArray[i].equals(childOneRouteArray[j])){
					alreadyContained = true;
				}
			}
			if(alreadyContained == false) {
				//TODO NEED TO ADD IT IN THE NEXT EMPTY CELL NOT 'I'
				childOneRouteArray[nextEmptyCell] = parentTwoRouteArray[i];
			}
		}
		
		//Finish creating child two
		for(int i = 0; i < parentTwoRouteArray.length; i++) {
			boolean alreadyContained = false;
			Integer nextEmptyCell = null;
			for(int j = 0; j < childTwoRouteArray.length; j++) {
				if(childTwoRouteArray[j] == null && nextEmptyCell == null) {
					nextEmptyCell = j;
					break;
				}
			}
			for(int j = 0; j < childTwoRouteArray.length; j++) {
				if(parentOneRouteArray[i].equals(childTwoRouteArray[j])){
					alreadyContained = true;
				}
			}
			if(alreadyContained == false) {
				childTwoRouteArray[nextEmptyCell] = parentOneRouteArray[i];
			}
		}
		
		//Mutate the children
		childOneRouteArray = mutate(childOneRouteArray, 0.7);
		childTwoRouteArray = mutate(childTwoRouteArray, 0.7);
		
		
		for(int i = 0; i < childOneRouteArray.length; i++) {
			childOne.setItemOne(childOne.getItemOne() + childOneRouteArray[i]);
			childOne.setItemTwo(getCostOfRoute(childOne.getItemOne()));
			childTwo.setItemOne(childTwo.getItemOne() + childTwoRouteArray[i]);
			childTwo.setItemTwo(getCostOfRoute(childTwo.getItemOne()));
		}
		Tuple[] children = {childOne,childTwo};
		return children;
	}
	
	private String[] mutate(String[] childRouteArray, double mutationProbability){
		Random rng = new Random();
		String[] mutatedChild = childRouteArray;
		if(rng.nextDouble() > mutationProbability) {
			int firstChangeIndex = rng.nextInt(childRouteArray.length);
			int secondChangeIndex = rng.nextInt(childRouteArray.length);
			while(secondChangeIndex == firstChangeIndex) {
				secondChangeIndex = rng.nextInt(childRouteArray.length);
			}
			String firstChangeLetter = mutatedChild[firstChangeIndex];
			String secondChangeLetter = mutatedChild[secondChangeIndex];
			mutatedChild[secondChangeIndex] = firstChangeLetter;
			mutatedChild[firstChangeIndex] = secondChangeLetter;
		}
		return mutatedChild;
	}
}
