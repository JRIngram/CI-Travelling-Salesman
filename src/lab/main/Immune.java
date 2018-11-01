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

	public Immune(int poolSize, int initialPoolSize) {
		super();
		pool = new ArrayList<Tuple<String,Double>>();
		this.poolSize = poolSize;
		cloneParents(pool, 3);
		this.bestFitness = getBestFitness();
	}

	public Immune(String filePath, int poolSize) {
		super(filePath);
		pool = new ArrayList<Tuple<String,Double>>();
		pool = createInitialPool(poolSize, filePath, numberOfCities);
		this.poolSize = poolSize;
	}
	
	public ArrayList<Tuple<String, Double>> search(int cloneNumber, double p){
		cloneParents(pool, cloneNumber);
		this.bestFitness = getBestFitness();
		for(int i = 0; i < pool.size(); i++) {
			double inverseFitness = 1 - normalizeFitness(i);
			System.out.println("EXP: " + (p * inverseFitness));
			double mutationProbability = Math.exp(-p * inverseFitness);
			mutate(pool.get(i));
		}
		return null;
	}
	
	public ArrayList<Tuple<String, Double>> createInitialPool(int initialPoolSize, String filePath, int numberOfCities) {
		ArrayList<Tuple<String, Double>> initialPool = new ArrayList<Tuple<String, Double>>(); 
		RandomSearch rs;
		if(!filePath.equals("")) {
			rs = new RandomSearch(filePath);
		}
		else{
			rs = new RandomSearch();
		}
		for(int i = 0; i < initialPoolSize; i++){
			initialPool.add(rs.testRandomRoute(numberOfCities)); 
		}
		return initialPool;
	}
	
	public ArrayList<Tuple<String, Double>> cloneParents(ArrayList<Tuple<String, Double>> parentsPool, int numberOfClones) {
		int originalPoolSize = parentsPool.size();
		for(int i = 0; i < originalPoolSize; i++){
			for(int j = 0; j < numberOfClones; j++){
				parentsPool.add(parentsPool.get(i));
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
	
	private ArrayList<Tuple<String, Double>> createNewPool(ArrayList<Tuple<String, Double>> pool){
		ArrayList<Tuple<String, Double>> newPool = cloneParents(pool,3);
		return null;
	}
	
}
