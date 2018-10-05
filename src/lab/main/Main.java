package lab.main;

import lab.main.TravellingSalesman;
import lab.main.RouteSet;

public class Main {

	private static int[][] graph;
	private static RouteSet routes;

	public static void main(String[] args) {
		RandomSearch randFixed = new RandomSearch();
		System.out.println("Best route of Fixed: " + randFixed.randomSearch(24).getItemTwo());
		RandomSearch randUlysses = new RandomSearch("src/lab/main/ulysses16.csv");
		System.out.println("**************");
		Tuple<String, Double> randUlyResult = randUlysses.timeLimitedRandomSearch(5);
		System.out.println("Best route of Ulysses: " + randUlyResult.getItemOne()  + " with a distance of " + randUlyResult.getItemTwo());
		System.out.println("**************");
		LocalSearch ls = new LocalSearch();
		Tuple<String, Double> twoOpt = ls.twoOpt(ls.createNeighbourhood(randUlyResult));
		System.out.println("Best route of 2Opt is: " + twoOpt.getItemOne() + " with a distance of " + twoOpt.getItemTwo());
	}
}