package lab.main;

import lab.main.TravellingSalesman;
import lab.main.RouteSet;

public class Main {

	private static int[][] graph;
	private static RouteSet routes;

	public static void main(String[] args) {
		//TravellingSalesman ts = new TravellingSalesman();
		TravellingSalesman tsf = new TravellingSalesman("src/lab/main/ulysses16.csv");
	}
}