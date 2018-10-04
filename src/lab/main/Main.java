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
		System.out.println("Best route of Ulysses: " + randUlysses.randomSearch(2500).getItemTwo());
		
	}
}