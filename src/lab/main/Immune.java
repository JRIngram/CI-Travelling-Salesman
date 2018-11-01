package lab.main;
import java.util.ArrayList;

public class Immune extends TravellingSalesman{
	
	private ArrayList<Tuple> pool;

	public Immune(int poolSize, int initialPoolSize) {
		super();
		pool = new ArrayList<Tuple>();
	}

	public Immune(String filePath, int poolSize) {
		super(filePath);
		pool = new ArrayList<Tuple>();
		pool = createInitialPool(poolSize, filePath, numberOfCities);
	}
	
	public ArrayList<Tuple> createInitialPool(int initialPoolSize, String filePath, int numberOfCities) {
		ArrayList<Tuple> initialPool = new ArrayList<Tuple>(); 
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
}
