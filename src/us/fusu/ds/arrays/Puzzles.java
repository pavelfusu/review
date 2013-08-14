package us.fusu.ds.arrays;

import java.util.Arrays;

import us.fusu.io.IO;

public class Puzzles {

	public static void processSubsets(int[] set, int k) {
		int[] subset = new int[k];
		processLargerSubsets(set, subset, 0, 0);
	}

	public static void processLargerSubsets(int[] set, int[] subset, int subsetSize, int nextIndex) {
		if (subsetSize == subset.length) {
			IO.writeLn(Arrays.toString(subset));
		} else {
			for (int j = nextIndex; j < set.length; j++) {
				subset[subsetSize] = set[j];
				processLargerSubsets(set, subset, subsetSize + 1, j + 1);
			}
		}
	}

	public static void snake(int [][] arr, int l1, int l2) {
		for (int i = 0; i < l1; i++) {
			for (int j = 0; j < l2; j++) {
				if (i % 2 == 0) {
					IO.write(arr[i][j] + " ");
				} else {
					IO.write(arr[i][l2 - 1 - j] + " ");
				}
			}
			IO.writeLn();
		}
	}

	public static int[] convertLessMore(int[] a, int n) {
		boolean less = true;

		for (int i=1; i < n ; i++) {
			if (less) {
				if (a[i-1] > a[i]) {
					swap(a, i-1, i);
				}
			} else {
				if (a[i-1] < a[i]) {
					swap(a, i-1, i);
				}
			}

			less = !less;
		}

		return a;
	}

	public static void subsets(int[] arr, int n) {
		long combinations = (long) Math.pow(2, n); 
		for (long i = 0; i< combinations; i++) {
			long temp = i;
			IO.write("{ ");
			while (temp != 0) {
				int bitIndex = Long.numberOfTrailingZeros(temp);
				IO.write(arr[bitIndex] + " ");
				temp ^= 1 << bitIndex; // clear the processed bit
			}
			IO.writeLn("}");
		}
	}

	public static void subsetsRecursive(int[] arr, int n) {
		subsets(arr, new int[n], n, 0, 0);
	}

	public static void subsets(int[] arr, int[] tempArray, int n, int level, int start) {
		for(int i=start; i<n; i++)	{
			tempArray[level] = arr[i];
			IO.write("{ ");
			for (int j=0; j<=level; j++) {
				IO.write(tempArray[j] + " ");
			}
			IO.writeLn("}");
			if( i < n-1) {
				subsets(arr, tempArray, n, level+1, i+1);
			}
		}
	}

	public static void rotate(int[] arr, int n, int k) {
		reverse(arr, 0, k - 1);
		reverse(arr, k, n - 1);
		reverse(arr, 0, n - 1);
	}

	public static void swap(int[] array, int a, int b) {
		int temp = array[a];
		array[a] = array[b];
		array[b] = temp;
	}

	public static void reverse(int[] arr, int start, int end) { 
		for (int i = start; i <= start + end /2 && i < (end - i + start); i++) {
			swap(arr, i, end - i + start);
		}
	}

	public static int binarySearch(int val, int[] arr) {
		if (arr == null || arr.length == 0) {
			return -1;
		}

		int start = 0;
		int end = arr.length;

		while (start < end - 1) {
			int half = start + (end - start) / 2;

			if (val > arr[half]) {
				start = half;
			} else if (val < arr[half]) {
				end = half;
			} else {
				return half;
			}
		}

		return -1;
	}

	public static int binarySearchRotated(int val, int[] arr) {
		if (arr == null || arr.length == 0) {
			return -1;
		}

		int start = 0;
		int end = arr.length;

		while (start < end) {
			int half = start + (end - start) / 2;

			if (val == arr[half]) {
				return half;
			} else if (arr[start] <= arr[half] && val >= arr[start] && val < arr[half]) {
				end = half;
			} else if (arr[half] <= arr[end - 1] && val > arr[half] && val <= arr[end-1]) {
				start = half;
			} else if (arr[half] <= arr[start]) {
				end = half;
			} else {
				start = half;
			}
		}

		return -1;
	}

	public static boolean isBinaryPalindrome(int n) {
		int bits = 31; // don't consider the sign bit
		for (int i = 0; i < bits / 2; i++) {
			int l = n & 1 << (bits - i - 1);
			int r = n & 1 << i;

			if ((l != 0 && r == 0) || (r != 0 && l == 0)) {
				return false;
			}
		}

		return true;
	}

	// find the sequence that sum up to the maximum sum
	public static int maxSum(int[] arr) {
		if (arr == null || arr.length == 0) {
			throw new IllegalArgumentException();
		}

		int max  = arr[0], currentMax = arr[0];

		// Track the position of the sub array
		// int begin = 0;
		// int beginC = 0;
		// int end = 0;

		for (int i = 1; i < arr.length; i++) {
			if(currentMax < 0) {
				currentMax = arr[i];
				// beginC = i;
			} else {
				currentMax += arr[i];
			}

			if (currentMax >= max ) {
				max  = currentMax;
				// begin = beginC;
				// end = i;
			}
		}
		return max;
	}

	public static int maxCoin(int[] coin, int start, int end) {
		if (start > end) {
			return 0;
		}

		int a = coin[start] + Math.min(maxCoin( coin, start+2,end ), maxCoin( coin, start+1,end-1));
		int b = coin[end] + Math.min(maxCoin( coin, start+1,end-1 ), maxCoin( coin, start,end-2));

		return Math.max(a,b);
	}

	public static void printArrayIntersection(int[] a1, int[] a2) {
		int i = 0; int j = 0;
		int n1 = a1.length;
		int n2 = a2.length;

		while (i < n1 && j < n2) {
			if (a1[i] == a2[j]) {
				IO.writeLn(a1[i] + " ");
				i++;
				j++;
			} else if (a1[i] < a2[j]) {
				i++;
			} else {
				j++;
			}
		}
	}

	public static boolean isSymmetric(int[][] a, int m, int n) {
		for(int i = 0;i < m;i ++) { 
			for(int j = 0; j< n; j++) { 
				if (a[i][j] != a[m-i-1][n-j-1]) { 
					return false; 
				} 
			} 
		} 
		return true;
	}

	public static int partition( int[] array, int first, int last) {
		int pivot = array[first];
		int pivotPosition = first;
		first++;
		while ( first <= last ) {
			// scan right
			while ( ( first <= last ) && (array[first] < pivot)) {
				first++;
			}

			// scan left
			while ( ( last >= first ) && (array[last] >= pivot)) {
				last--;
			}

			if ( first > last ) {
				swap( array, pivotPosition, last );
			}
			else {
				swap( array, first, last );
			}
		}
		return last;
	}

	private static int orderStatistic(int[] array, int k, int first, int last) {
		int pivotPosition = partition(array, first, last);
		if ( pivotPosition == k - 1) {
			return array[k - 1];
		}
		if (k - 1 < pivotPosition ) {
			return orderStatistic(array, k, first, pivotPosition - 1);
		}
		else {
			return orderStatistic(array, k, pivotPosition + 1, last);
		}
	}

	public static int kthSmallest(int[] array, int k) {
		return orderStatistic(array, k, 0, array.length - 1);
	}

	public static int kthLargest(int[] array, int k) {
		return orderStatistic(array, array.length - k + 1, 0, array.length - 1);
	}

	// iterative version
	public static int orderStatisticIterative(
			int[] array, int k, int first, int last) {

		int pivotPosition = partition(array, first, last);

		while (pivotPosition != k - 1) {
			if (k - 1 < pivotPosition) {
				last = pivotPosition - 1;
			}
			else {
				first = pivotPosition + 1;
			}

			pivotPosition = partition(array, first, last);
		}

		return array[k - 1];
	}

	public static int[] mergeSortedArrays(int[] a1, int[] a2) {
		int i = 0; int j = 0;
		int n1 = a1.length;
		int n2 = a2.length;
		int[] ret = new int[n1 + n2];

		while (i < n1 || j < n2) {
			if (i < n1 && j < n2) {
				if (a1[i] < a2[j]) {
					ret[i+j] = a1[i++];
				} else {
					ret[i+j] = a2[j++];
				}
			} else if (i < n1) {
				ret[i+j] = a1[i++];
			} else if (j < n2) {
				ret[i+j] = a2[j++];
			}
		}

		return ret;
	}

	public static void main(String[] args) {
		//		int a[][] = new int[][] 
		//				{{1, 2, 3},
		//				{4, 5, 6},
		//				{7, 8, 9}};
		//
		//		int b[][] = new int[][] 
		//				{{9, 8, 7},
		//				{6, 5, 4},
		//				{3, 2, 1}};
		//
		//		int c[][] = new int[][] 
		//				{{9, 8, 7},
		//				{4, 5, 4},
		//				{7, 8, 9}};
		//
		//		System.out.println(isSymmetric(a, 3, 3));
		//		System.out.println(isSymmetric(b, 3, 3));
		//		System.out.println(isSymmetric(c, 3, 3));
		//
		//		int[] array = { 42, 92, 31, 73, 46, 11, 29, 53, 64, 4 };
		//
		//		System.out.println( "The array elements: " );
		//		for ( int i = 0; i < array.length; i++ )
		//			System.out.print( "  " + array[i] );
		//
		//		System.out.println( "\n" );
		//		System.out.println( "1st smallest value is: " +
		//				kthSmallest(array, 1));
		//		System.out.println( "4th smallest value is: " +
		//				kthSmallest(array, 4) );
		//		System.out.println( "7th smallest value is: " +
		//				kthSmallest(array, 7) );
		//		System.out.println( "10th smallest value is: " +
		//				kthSmallest(array, 10) );

		int[] arr = mergeSortedArrays(new int[] {2, 3, 5, 7, 9, 13}, new int[] {1, 4, 6, 7, 8, 10, 11, 12});
		IO.writeLn(Arrays.toString(arr));
		
		printArrayIntersection(new int[] {2, 3, 5, 7, 9, 13}, new int[] {1, 4, 6, 7, 8, 10, 11, 12});
	}
}
