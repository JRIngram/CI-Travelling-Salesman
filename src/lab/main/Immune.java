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

	public Immune(int poolSize, int initialPoolSize) throws CloneNotSupportedException {
		super();
		pool = new ArrayList<Tuple<String,Double>>();
		rs = new RandomSearch();
		this.poolSize = poolSize;
		cloneParents(pool, 3);
		this.bestFitness = getBestFitness();

	}

	public Immune(String filePath, int poolSize) {
		super(filePath);
		rs = new RandomSearch(filePath);
		pool = new ArrayList<Tuple<String,Double>>();
		pool = createInitialPool(poolSize, filePath, numberOfCities);
		this.poolSize = poolSize;
	}
	
	public Tuple<String,Double> search(int numberOfSearches, int cloneNumber, double p, int numberToReplace) throws CloneNotSupportedException{
		System.out.println("[" + 0 + "] Best Current Route: " + getFittestRoute().getItemOne() + " with a distancce of " + getFittestRoute().getItemTwo());
		for(int i = 0; i < numberOfSearches; i++) {
			cloneParents(pool, cloneNumber);
			this.bestFitness = getBestFitness();
			for(int j = poolSize; j < pool.size(); j++) {
				double inverseFitness = 1 - normalizeFitness(j);
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
			System.out.println("[" + (i+1) + "] Best Current Route: " + getFittestRoute().getItemOne() + " with a distancce of " + getFittestRoute().getItemTwo());
		}
		
		return null;
	}
	
	public ArrayList<Tuple<String, Double>> createInitialPool(int initialPoolSize, String filePath, int numberOfCities) {
		ArrayList<Tuple<String, Double>> initialPool = new ArrayList<Tuple<String, Double>>(); 
		for(int i = 0; i < initialPoolSize; i++){
			initialPool.add(rs.testRandomRoute(numberOfCities)); 
		}
		return initialPool;
	}
	
	public ArrayList<Tuple<String, Double>> cloneParents(ArrayList<Tuple<String, Double>> parentsPool, int numberOfClones) throws CloneNotSupportedException {
		int originalPoolSize = parentsPool.size();
		for(int i = 0; i < originalPoolSize; i++){
			for(int j = 0; j < numberOfClones; j++){
				parentsPool.add((Tuple<String, Double>) parentsPool.get(i).clone());
			}
		}
		return parentsPool;
	}
	
	private Double getBestFitness(){
		Double bestFitness = Double.MAX_VALUE;
		for(int i = 0; i < pool.size(); i++) {
			Double routesFitness = pool.get(i).getItemTwo();
			if(routesFitness < bestFitness) {
				bestFitness = routesFitness;
			}
		}
		return bestFitness;
	}
	
	private double normalizeFitness(int poolIndex) {
		return bestFitness / (pool.get(poolIndex).getItemTwo());
	}
	
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
	
	private ArrayList<Tuple<String, Double>> createNewPool(ArrayList<Tuple<String, Double>> pool) throws CloneNotSupportedException{
		ArrayList<Tuple<String, Double>> newPool = cloneParents(pool,3);
		return null;
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
	
	private void trimPoolToOriginalSize(){
		while(poolSize < pool.size()) {
			pool.remove(pool.size() - 1);
		}
	}
	
	public Tuple<String, Double> getFittestRoute(){
		return pool.get(0);
	}
}
