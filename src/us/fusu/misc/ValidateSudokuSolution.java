package us.fusu.misc;

import java.util.HashSet;
import java.util.Set;

import us.fusu.io.IO;

public class ValidateSudokuSolution {
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static boolean isValidSudoku(int[][] board, int order) {

		Set[] rows = new HashSet[order * order];
		Set[] columns = new HashSet[order * order];
		Set[][] boxes = new HashSet[order][order];

		// init the sets so that we don't deal with nulls
		initialize(rows, columns, boxes, order);

		int length = order * order;

		for (int i = 0; i < length; i++) {
			int boxI = i / 3;
			for (int j = 0; j < length; j++) {
				int boxJ = j / 3;

				int value = board[i][j];

				if (value == 0) {
					continue;
				}

				if (rows[i].contains(value)) {
					return false;
				} else {
					rows[i].add(value);
				}

				if (columns[j].contains(value)) {
					return false;
				} else {
					columns[j].add(value);
				}

				if (boxes[boxI][boxJ].contains(value)) {
					return false;
				} else {
					boxes[boxI][boxJ].add(value);
				}

			}
		}

		return true;
	}

	public static boolean isValidSudokuOptimized(int[][] board, int order) {

		long[] rows = new long[order * order];
		long[] columns = new long[order * order];
		long[][] boxes = new long[order][order];

		int length = order * order;

		for (int i = 0; i < length; i++) {
			int boxI = i / 3;
			for (int j = 0; j < length; j++) {
				int boxJ = j / 3;

				int value = board[i][j];

				if (value == 0) {
					continue;
				}

				if ((rows[i] & 1 << (value - 1)) > 0) {
					return false;
				} else {
					rows[i] = rows[i] | 1 << (value - 1);
				}

				if ((columns[j] & 1 << (value - 1)) > 0) {
					return false;
				} else {
					columns[j] = columns[j] | 1 << (value - 1);
				}

				if ((boxes[boxI][boxJ] & 1 << (value - 1)) > 0) {
					return false;
				} else {
					boxes[boxI][boxJ] = boxes[boxI][boxJ] | 1 << (value - 1);
				}

			}
		}

		return true;
	}

	private static void initialize(Set<?>[] rows, Set<?>[] columns, Set<?>[][] boxes, int order) {
		for (int i = 0; i< order * order; i++) {
			rows[i] = new HashSet<Integer>();
			columns[i] = new HashSet<Integer>();
			boxes[i / order][i % order] = new HashSet<Integer>();
		}
	}

	public static void main(String[] args) {
		int[][] board = new int[][] {
				{5, 3, 4,  6, 7, 8,  9, 1, 2}, 
				{6, 7, 2,  1, 9, 5,  3, 4, 8},
				{1, 9, 8,  3, 4, 2,  5, 6, 7},

				{8, 5, 9,  7, 6, 1,  4, 2, 3},
				{4, 2, 6,  8, 5, 3,  7, 9, 1},
				{7, 1, 3,  9, 2, 4,  8, 5, 6},

				{9, 6, 1,  5, 3, 7,  2, 8, 4},
				{2, 8, 7,  4, 1, 9,  6, 3, 5},
				{3, 4, 5,  2, 8, 6,  1, 7, 9}
		};

		int[][] unsolvedBoard = new int[][] {
				{5, 3, 0,  0, 7, 0,  0, 0, 0}, 
				{6, 0, 0,  1, 9, 5,  0, 0, 0},
				{0, 9, 8,  0, 0, 0,  0, 6, 0},

				{8, 0, 0,  0, 6, 0,  0, 0, 3},
				{4, 0, 0,  8, 0, 3,  0, 0, 1},
				{7, 0, 0,  0, 2, 0,  0, 0, 6},

				{0, 6, 0,  0, 0, 0,  2, 8, 0},
				{0, 0, 0,  4, 1, 9,  0, 0, 5},
				{0, 0, 0,  0, 8, 0,  0, 7, 9}
		};
		
		int[][] boardErrored = new int[][] {
				{5, 3, 4,  6, 7, 8,  9, 1, 2}, 
				{6, 7, 2,  1, 9, 5,  3, 4, 8},
				{1, 9, 8,  3, 4, 2,  5, 6, 7},

				{8, 5, 9,  7, 6, 1,  4, 2, 3},
				{4, 2, 6,  8, 5, 3,  7, 9, 1},
				{7, 1, 3,  9, 2, 4,  8, 5, 6},

				{9, 6, 1,  5, 3, 7,  2, 8, 4},
				{2, 8, 7,  4, 1, 9,  6, 3, 5},
				{3, 4, 5,  2, 8, 6,  9, 7, 1}
		};
		
		int[][] unsolvedBoardErrored = new int[][] {
				{5, 3, 0,  0, 7, 0,  0, 0, 0}, 
				{6, 0, 0,  1, 9, 5,  0, 0, 0},
				{0, 9, 8,  0, 0, 0,  0, 6, 0},

				{8, 0, 0,  0, 6, 0,  0, 0, 3},
				{4, 0, 0,  8, 0, 3,  0, 0, 1},
				{7, 0, 0,  0, 2, 0,  0, 0, 6},

				{0, 6, 0,  0, 0, 0,  2, 8, 0},
				{0, 0, 0,  4, 1, 9,  0, 0, 5},
				{0, 0, 0,  0, 8, 0,  9, 7, 1}
		};

		IO.writeLn(isValidSudokuOptimized(board, 3));
		IO.writeLn(isValidSudokuOptimized(unsolvedBoard, 3));
		IO.writeLn(isValidSudokuOptimized(boardErrored, 3));
		IO.writeLn(isValidSudokuOptimized(unsolvedBoardErrored, 3));
	}
}
