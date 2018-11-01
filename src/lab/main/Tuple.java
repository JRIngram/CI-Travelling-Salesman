package lab.main;

public class Tuple<T,E> implements Cloneable {

	private T itemOne;
	private E itemTwo;
	
	public Tuple(T itemOne, E itemTwo){
		if(itemOne != null) {
			this.itemOne = itemOne;
		}
		if(itemTwo != null) {
			this.itemTwo = itemTwo;
		}
	}
	
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

	public T getItemOne() {
		return itemOne;
	}

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
	
	public E getItemTwo() {
		return itemTwo;
	}
	
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

}
