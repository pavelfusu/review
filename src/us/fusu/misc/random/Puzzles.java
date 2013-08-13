package us.fusu.misc.random;

public class Puzzles {
	public static int rollOptimized() {
		int nr = 0;
		
		int bit1 = toss() - 1;
		
		do {
			int bit2 = toss() - 1;
			int bit3 = toss() - 1;
			
			nr = bit1 * 4 + bit2 * 2 + bit3;
			
			bit1 = bit3;
		} while (nr > 5);
		
		return nr + 1;
	}
	
	public static int roll() {
		int nr = 0;
		
		do {
			int bit1 = toss() - 1;
			int bit2 = toss() - 1;
			int bit3 = toss() - 1;
			
			nr = bit1 * 4 + bit2 * 2 + bit3;
		} while (nr > 5);
		
		return nr + 1;
	}
	
	private static int toss() {
		return random(1, 2);
	}
	
	private static int random(int rangeStart, int rangeEnd) {
		return rangeStart + (int)(Math.random() * ((rangeEnd - rangeStart) + 1));
	}
	
	public static void swap(int[] array, int a, int b) {
		int temp = array[a];
	    array[a] = array[b];
	    array[b] = temp;
	}
	
	public static void shuffle(int[] array) {
		for (int i = 0; i < array.length; i++) {
			int r = random(i, array.length - 1);
			swap(array, i, r);
		}
		
	}
	
	public static void main(String[] args) {
		System.out.println(roll());
		System.out.println(roll());
		System.out.println(roll());
		System.out.println(roll());
		System.out.println(roll());
		System.out.println(roll());
		System.out.println(roll());
		System.out.println(roll());
		System.out.println(roll());
		System.out.println(roll());
		System.out.println(roll());
		System.out.println(roll());
		System.out.println(roll());
		System.out.println(roll());
		System.out.println(roll());
	}
}
