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

	public static boolean isPalindrome(int x) {
		if (x < 0) return false;
		int div = 1;
		while (x / div >= 10) {
			div *= 10;
		}        
		while (x != 0) {
			int l = x / div;
			int r = x % 10;
			if (l != r) return false;
			x = (x % div) / 10;
			div /= 100;
		}
		return true;
	}


	public static boolean isPalindromeRecursive(int x, int y) {
		if (x < 0) return false;
		if (x == 0) return true;
		if (isPalindromeRecursive(x/10, y) && (x%10 == y%10)) {
			y /= 10;
			return true;
		} else {
			return false;
		}
	}

	public static boolean isPalindromeRecursive(int x) {
		return isPalindromeRecursive(x, x);
	}
	
	public static int modulo(int divisor, int dividend) {
	  int i;

	  if (dividend == 0) {
	    throw new IllegalArgumentException("Division by 0");
	  }
	  
	  i = 16;  /* Number of bits to process */
	  while(i > 0) {
	    int d1, d2, d3;
	    i -= 2;
	    d1 = dividend << i;
	    d2 = dividend << (i+1);
	    d3 = d1+d2;

	    if(divisor < d1) {
	      
	    } else if(divisor < d2) {
	      divisor -= d1;
	    } else if(divisor < d3) {
	      divisor -= d2;
	    } else {
	      divisor -= d3;
	    }
	  }
	  return divisor;
	}
	
	public static int divide(int divisor, int dividend) {

	    int quotient = 0;
	    int i = 16;  /* Number of bits to process */

        if(dividend == 0) {
          throw new IllegalArgumentException("Division by 0");
        }
      
        while(i > 0) {
          int j;
          int d1, d2, d3;
      
          i -= 2;
      
          d1 = dividend << i;
          d2 = dividend << (i+1);
          d3 = d1+d2;
      
          if(divisor < d1) {
            j = 0;
          } else if(divisor < d2) {
            j = 1;
            divisor -= d1;
          } else if(divisor < d3) {
            j = 2;
            divisor -= d2;
          } else {
            j = 3;
            divisor -= d3;
          }
          quotient = (quotient << 2) + j;
        }
        return quotient;
	}
	
	public static void main(String[] args) {
//		getNthFibonacci(84);
//		for (int i = 0; i<= 84; i++) {
//			System.out.println(getNthFibonacci(i));
//		}
	  
	    System.out.println(divide(7, 2));
	    System.out.println(divide(9, 3));
	    System.out.println(modulo(7, 2));
        System.out.println(modulo(9, 3));
	}
}
