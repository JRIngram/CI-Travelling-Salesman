package lab.main;

/**
 * A class which represents a Tuple datatype.
 * @author JRIngram
 * @param <T> A generic data type.
 * @param <E> A generic data type.
 */
public class Tuple<T,E> implements Cloneable {

	private T itemOne;
	private E itemTwo;
	
	/**
	 * Creates a tuple with two points of data.
	 * If one of the data points is left blank, then the value of that data point will stay as null until set. 
	 * @param itemOne First item of data to load in.
	 * @param itemTwo Second item of data to load in.
	 */
	public Tuple(T itemOne, E itemTwo){
		if(itemOne != null) {
			this.itemOne = itemOne;
		}
		if(itemTwo != null) {
			this.itemTwo = itemTwo;
		}
	}
	
	/**
	 * Sets the value of itemOne in the tuple
	 * @param itemOne The new data to be loaded into itemOne
	 * @return true if the change has been successful; false if the change has been unsuccessful.
	 */
	public boolean setItemOne(T itemOne){
		try {
			this.itemOne = itemOne;	
			return true;
		}
		catch(Exception e){
			System.out.println("An error has occurred when setting Item One!\n" + e.toString());
			return false;
		}
	}
	
	/**
	 * Returns the value of itemOne in the tuple.
	 * @return the value of itemOne in the tuple.
	 */
	public T getItemOne() {
		return itemOne;
	}

	/**
	 * Sets the value of itemTwo in the tuple
	 * @param itemOne The new data to be loaded into itemTwo
	 * @return true if the change has been successful; false if the change has been unsuccessful.
	 */
	public boolean setItemTwo(E itemTwo) {
		try {
			this.itemTwo = itemTwo;	
			return true;
		}
		catch(Exception e){
			System.out.println("An error has occurred when setting Item Two!\n" + e.toString());
			return false;
		}
	}
	
	/**
	 * Returns the value of itemTwo in the tuple.
	 * @return the value of itemTwo in the tuple.
	 */
	public E getItemTwo() {
		return itemTwo;
	}
	
	/**
	 * Clones the Tuple.
	 */
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

}
