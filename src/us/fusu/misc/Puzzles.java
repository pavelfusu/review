package us.fusu.misc;

public class Puzzles {
	
	/* 
	 * F(2n) = F(n) * (2*F(n+1) - F(n))
	 * F(2n+1) = F(n+1)^2 + F(n)^2
	 * Compute up to n = 92, due to long limitation
	 */
	public static long getNthFibonacci(int n) {
		long a = 0;
		long b = 1;
		for (int i = 31 - Integer.numberOfLeadingZeros(n); i >= 0; i--) {

			long d = a * ((b << 1) - a); // F(2n)
			long e = a * a + b * b; // F(2n+1)
			a = d;
			b = e;

			if (((1 << i) & n) != 0) { // advance by one
				long c = a + b;
				a = b;
				b = c;
			}
		}
		return a;
	}

	public static void main(String[] args) {
		getNthFibonacci(84);
		for (int i = 0; i<= 84; i++) {
			System.out.println(getNthFibonacci(i));
		}
	}
}
