package lab.main;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Immune extends TravellingSalesman{
	
	private ArrayList<Tuple<String, Double>> pool;
	private int poolSize;
	private double bestFitness;
	private RandomSearch rs;
	
	/**
	 * Creates a pool with the specified number of routes based on the cities from the hardcoded example
	 * @param poolSize Number of routes that are in the initial pool.
	 */
	public Immune(int poolSize) throws CloneNotSupportedException {
		super();
		pool = new ArrayList<Tuple<String,Double>>();
		rs = new RandomSearch();
		this.poolSize = poolSize;
		cloneParents(pool, 3);
		this.bestFitness = setBestFitness();

	}
	
	/**
	 * Reads a csv file and creates a pool with the specified number of routes based on the cities loaded in from the file.
	 * @param filePath Path of the CSV to read.
	 * @param poolSize Number of routes that are in the initial pool.
	 */
	public Immune(String filePath, int poolSize) {
		super(filePath);
		rs = new RandomSearch(filePath);
		pool = new ArrayList<Tuple<String,Double>>();
		pool = createInitialPool(poolSize, numberOfCities);
		this.poolSize = poolSize;
	}
	
	/**
	 * Performs a searches until the search has been performed a specified number of times and returns the route with the shortest distance.
	 * @param numberOfSearches The number of 
	 * @param cloneNumber The number of times each parent should be cloned
	 * @param p Used to calculate the mutation rate
	 * @param numberToReplace How many of the weakest should be replaced by random routes once the pool has been trimmed to its original size.
	 * @return The route with the shortest fitness after the search has been completed.
	 * @throws CloneNotSupportedException
	 */
	public Tuple<String,Double> search(int numberOfSearches, int cloneNumber, double p, int numberToReplace) throws CloneNotSupportedException{
		System.out.println("[" + 0 + "] Best Current Route: " + getFittestRoute().getItemOne() + " with a distancce of " + getFittestRoute().getItemTwo());
		this.bestFitness = setBestFitness();
		for(int i = 0; i < numberOfSearches; i++) {
			cloneParents(pool, cloneNumber);
			for(int j = poolSize; j < pool.size(); j++) {
				double inverseFitness = 1 - normalizeFitness(pool.get(j));
				double mutationProbability = Math.exp(-p * inverseFitness);
				Random rng = new Random();
				if(rng.nextDouble() < mutationProbability) {
					pool.add(mutate(pool.get(j)));
					Collections.swap(pool, j, pool.size() - 1);
					pool.remove(pool.size() - 1);
				}
			}
			pool = sortPool(pool);
			trimPoolToOriginalSize();
			for(int j = poolSize - 1; j >= (poolSize - (numberToReplace)); j--){
				pool.remove(j);
				pool.add(rs.testRandomRoute(numberOfCities));
			}
			this.bestFitness = setBestFitness();
			System.out.println("[" + (i+1) + "] Best Current Route: " + getFittestRoute().getItemOne() + " with a distancce of " + getFittestRoute().getItemTwo());
		}
		
		return pool.get(0);
	}
	
	/**
	 * Creates a pool of a specified type, by using a random search.
	 * @param initialPoolSize The size of the initial pool.
	 * @param numberOfCities How many cities should be included in each route.
	 * @return
	 */
	public ArrayList<Tuple<String, Double>> createInitialPool(int initialPoolSize, int numberOfCities) {
		ArrayList<Tuple<String, Double>> initialPool = new ArrayList<Tuple<String, Double>>(); 
		for(int i = 0; i < initialPoolSize; i++){
			initialPool.add(rs.testRandomRoute(numberOfCities)); 
		}
		return initialPool;
	}
	
	/**
	 * Clones each index in the pool by the specified amount.
	 * @param parentsPool The pool that has indexes to be clones.
	 * @param numberOfClones How many times each index should be cloned
	 * @return A pool with cloned indexes.
	 * @throws CloneNotSupportedException
	 */
	public ArrayList<Tuple<String, Double>> cloneParents(ArrayList<Tuple<String, Double>> parentsPool, int numberOfClones) throws CloneNotSupportedException {
		int originalPoolSize = parentsPool.size();
		for(int i = 0; i < originalPoolSize; i++){
			for(int j = 0; j < numberOfClones; j++){
				parentsPool.add((Tuple<String, Double>) parentsPool.get(i).clone());
			}
		}
		return parentsPool;
	}
	
	/**
	 * Searches the entire pool for the value of the shortest route in the current pool.
	 * @return the value of the shortest route in the current pool
	 */
	private Double setBestFitness(){
		Double bestFitness = Double.MAX_VALUE;
		for(int i = 0; i < pool.size(); i++) {
			Double routesFitness = pool.get(i).getItemTwo();
			if(routesFitness < bestFitness) {
				bestFitness = routesFitness;
			}
		}
		return bestFitness;
	}
	
	/**
	 * Returns the route from the pool with the highest fitness
	 * @return Returns the route that's in pool index 0. This is the shortest route and thus has the highest fitness.
	 */
	public Tuple<String, Double> getFittestRoute(){
		return pool.get(0);
	}
	
	/**
	 * 
	 * @param route
	 * @return
	 */
	private double normalizeFitness(Tuple<String, Double> route) {
		return bestFitness / (route.getItemTwo());
	}
	
	/**
	 * Takes the two indices of an array, and reverses the substring corresponding to those indicies
	 * @param route The route to mutate
	 * @return A mutated route - the original route with a substring reversed.
	 */
	private Tuple<String, Double> mutate(Tuple<String, Double> route){
		Random rng = new Random();
		String[] splitRoute = route.getItemOne().split("");
		int firstSwapIndex = rng.nextInt(splitRoute.length);
		int finalSwapIndex = rng.nextInt(splitRoute.length);
		while(finalSwapIndex == firstSwapIndex) {
			finalSwapIndex = rng.nextInt(splitRoute.length);
		}
		if(firstSwapIndex > finalSwapIndex){
			int temp = finalSwapIndex;
			finalSwapIndex = firstSwapIndex;
			firstSwapIndex = temp;
		}
		String routeSubstring = route.getItemOne().substring(firstSwapIndex,(finalSwapIndex+1));
		String[] splitSubstring = routeSubstring.split("");
		List<String> reversedList = Arrays.asList(splitSubstring);
		Collections.reverse(reversedList);
		int indexCounter = 0;
		for(int i = firstSwapIndex; i <= finalSwapIndex; i++){
			splitRoute[i] = reversedList.get(indexCounter);
			indexCounter++;
		}
		String newRoute = "";
		for(int i = 0; i < splitRoute.length; i++){
			newRoute += splitRoute[i];
		}
		Tuple<String, Double> mutatedRoute = new Tuple(newRoute, getCostOfRoute(newRoute));
		return mutatedRoute;
	}
	
	/**
	 * Sorts the current pool
	 * @param population currentPopulation of the AIS.
	 */
	private ArrayList<Tuple<String, Double>> sortPool(ArrayList<Tuple<String, Double>> pool){
		ArrayList<Tuple<String,Double>> sortedPool = (ArrayList<Tuple<String, Double>>) pool.clone();
		boolean changeOccurred = true;
		while(changeOccurred){
			changeOccurred = false;
			for(int i = 0; i < sortedPool.size() - 1; i++){
				if(sortedPool.get(i).getItemTwo() > sortedPool.get(i+1).getItemTwo()){
					Collections.swap(sortedPool, i, i + 1);
					changeOccurred = true;
				}
			}
		}
		return sortedPool;
	}
	
	/**
	 * Trims the pool population to the original pool size, specified by the poolSize variable.
	 */
	private void trimPoolToOriginalSize(){
		while(poolSize < pool.size()) {
			pool.remove(pool.size() - 1);
		}
	}
}
