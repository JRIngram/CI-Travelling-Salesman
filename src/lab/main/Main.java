package lab.main;

import lab.main.TravellingSalesman;
import lab.main.RouteSet;

public class Main {

	private static int[][] graph;
	private static RouteSet routes;

	public static void main(String[] args) {
		TravellingSalesman tsFixed = new TravellingSalesman();
		System.out.println("Best route of Fixed: " + tsFixed.randomSearch(24));
		TravellingSalesman tsUlysses = new TravellingSalesman("src/lab/main/ulysses16.csv");
		System.out.println("Best route of Ulysses: " + tsUlysses.randomSearch(2500));
		
	}
}