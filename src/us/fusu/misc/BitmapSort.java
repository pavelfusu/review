package us.fusu.misc;

import us.fusu.io.IO;

public class BitmapSort {
	
	private static final int BYTE_SIZE = 32;
	
	public static void bitmapSort(int n, int max) {
		int[] bitmap = createBitMap(n, max);
		outputSortedSequence(bitmap, max);
	}

	public static int[] createBitMap(int n, int max) {
		int[] bitmap = new int[max / BYTE_SIZE];

		while (IO.hasMore()) {
			int i = IO.readInt();
			int mask = 1 << (int) (i % BYTE_SIZE);
			bitmap[(int) (i / BYTE_SIZE)] |= mask;
		}

		return bitmap;
	}

	public static void outputSortedSequence(int[] bitmap, int max) {
		int bitMapLength = max / BYTE_SIZE;
		IO.write("[ ");
		for (int i = 0; i < bitMapLength; i++) {
			int bits = bitmap[i];

			for (int bitIndex = 0; bitIndex < BYTE_SIZE; bitIndex++) {
				int mask = 1 << bitIndex;
				boolean bitSet = (bits & mask) > 0;
				if (bitSet) {
					IO.write(i * BYTE_SIZE + bitIndex + " ");
				}
			}
		}
		IO.writeLn("]");
	}
	
	public static void main(String[] args) {
		Integer[] a = new Integer[] {16, 34, 17, 20, 25, 11, 8, 30, 28, 23, 36, 46, 40};
		IO.loadInput(a);
		bitmapSort(a.length, 64000);
	}
}
