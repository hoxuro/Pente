package pente.utilis;

/**
 * Class that gives us different methods to work with arrays.
 *
 * @author Hoxuro
 * @version 1.0
 * @since JDK 19
 */
public class MyMatrix {

	/**
	 * Displays the entered matrix on the console.
	 *
	 * @param matrix the matrix we want to display
	 */
	public static void display(int[][] matrix) {
		StringBuilder finalStr = new StringBuilder("");
		for (int row = 0; row < matrix.length; row++) {
			for (int col = 0; col < matrix[row].length; col++) {
				if (col + 1 == matrix[row].length) {
					finalStr.append(matrix[row][col] + "\n");
				} else {
					finalStr.append(matrix[row][col] + " ");
				}
			}
		}
		System.out.println(finalStr);
	}

	/**
	 * Displays the entered matrix on the console. With a highlighted element.
	 * 
	 * @param matrix the matrix we want to display with a highlighted element
	 * @param row    the row of the element we want to display
	 * @param col    the col of the element we want to display
	 */
	public static void highlightMax(int[][] matrix, int max) {
		StringBuilder finalStr = new StringBuilder("");
		for (int row = 0; row < matrix.length; row++) {
			for (int col = 0; col < matrix[row].length; col++) {

				// highlight or not the element
				if (matrix[row][col] == max) {
					finalStr.append("\u001B[31m").append(matrix[row][col]).append("\033[0m");
				} else {
					finalStr.append(matrix[row][col]);
				}

				if (col + 1 == matrix[row].length) {
					finalStr.append("\n");
				} else {
					finalStr.append(" ");
				}
			}
		}
		System.out.println(finalStr);
	}

	/**
	 * Fills a matrix with the number entered by the user.
	 *
	 * @param rows    the rows of the matrix
	 * @param columns the columns of the matrix
	 * @param number  the number to fill the matrix with
	 * @return the matrix filled in with the number we wanted
	 */
	public static int[][] fill(int rows, int columns, int number) {
		int[][] matrix = new int[rows][columns];

		for (int row = 0; row < matrix.length; row++) {
			for (int column = 0; column < matrix[row].length; column++) {
				matrix[row][column] = number;
			}
		}

		return matrix;
	}

	/**
	 * Fills a matrix with a specific number entered by the user for each case.
	 *
	 * @param rows    the rows of the matrix
	 * @param columns the columns of the matrix
	 * @return a matrix whose elements are given by the user
	 */
	public static int[][] fill(int rows, int columns) {
		int[][] matrix = new int[rows][columns];

		for (int row = 0; row < matrix.length; row++) {
			for (int column = 0; column < matrix[row].length; column++) {
				matrix[row][column] = ReadKBData.enterInt("Enter number for position " + row + column + ":");
			}
		}

		return matrix;
	}

	/**
	 * Fills a matrix with a random int number from 0 to 9.
	 *
	 * @param rows    the rows of the matrix
	 * @param columns the columns of the matrix
	 * @return a matrix whose elements have been generated randomly.
	 */
	public static int[][] fillRandom(int rows, int columns) {
		int[][] matrix = new int[rows][columns];

		for (int row = 0; row < matrix.length; row++) {
			for (int column = 0; column < matrix[row].length; column++) {
				matrix[row][column] = (int) (Math.random() * (9 + 1));
			}
		}

		return matrix;
	}

	/**
	 * Fills a matrix with a random int number in a range given by the user.
	 *
	 * @param rows    the rows of the matrix
	 * @param columns the columns of the matrix
	 * @param min     the integer from which the random can start to be generated.
	 * @param max     the integer from which the random stops being generated.
	 * @return a matrix whose elements have been generated randomly in a range
	 *         given.
	 */
	public static int[][] fillRandom(int rows, int columns, int min, int max) {
		int[][] matrix = new int[rows][columns];

		for (int row = 0; row < matrix.length; row++) {
			for (int column = 0; column < matrix[row].length; column++) {
				matrix[row][column] = (int) (Math.random() * (max + 1 - min) + min);
			}
		}

		return matrix;
	}

	/**
	 * Extract the wanted row from the matrix by giving the index of it.
	 *
	 * @param matrix   the matrix we want to extract a row
	 * @param rowIndex the index of the row we want to extract starts in 0
	 * @return an array which is the wanted row
	 */
	public static int[] extractRow(int[][] matrix, int rowIndex) {

		int[] extractedRow = new int[matrix[rowIndex].length];

		for (int element = 0; element < matrix[rowIndex].length; element++) {
			extractedRow[element] = matrix[rowIndex][element];
		}

		return extractedRow;
	}

	/**
	 * Extract the wanted column from the matrix by giving the index of it.
	 *
	 * @param matrix      the matrix we want to extract a row
	 * @param columnIndex the index of the column we want to extract starts in 0
	 * @return an array which is the wanted column
	 */
	public static int[] extractColumn(int[][] matrix, int columnIndex) {
		// only works for square matrices
		int[] extractedColumn = new int[matrix.length];

		for (int element = 0; element < matrix.length; element++) {
			extractedColumn[element] = matrix[element][columnIndex];
		}

		return extractedColumn;
	}

	/**
	 * Method which extracts the main diagonal from a matrix.
	 *
	 * @param matrix the matrix from which we want to extract the diagonal
	 * @return an array which is the main diagonal of the matrix
	 */
	public static int[] extractMainDiagonal(int[][] matrix) {
		int[] extractMain = new int[matrix.length];

		for (int row = 0; row < matrix.length; row++) {
			for (int column = 0; column < matrix.length; column++) {
				extractMain[row] = matrix[row][row];
			}
		}

		return extractMain;
	}

	/**
	 * Method that extracts the "main" diagonal of the position entered by the user.
	 * Do not works for unique elements.
	 *
	 * @param matrix the matrix from which we want to extract the diagonal
	 * @param row    the row of the element starts in 1
	 * @param column the column of the element starts in 1
	 * @return an array which is the main diagonal of the given position
	 */
	public static int[] extractMainDiagonal(int[][] matrix, int row, int column) {
		int height = matrix.length;
		int width = matrix[row].length;

		// depending on the position, the length of the main diagonal will be different
		int[] mainDiagonal = new int[width - Math.abs(row - column)];
		int element = 0;

		// I have taken as reference position the row=0, column=0
		for (int diagonal = 1 - width; diagonal <= height - 1; diagonal++) {
			for (int verticalPos = Math.max(0, diagonal), horizontalPos = -Math.min(0, diagonal); verticalPos < height
					&& horizontalPos < width; verticalPos++, horizontalPos++) {
				if (verticalPos - horizontalPos == diagonal && row - column == diagonal) {
					mainDiagonal[element] = matrix[verticalPos][horizontalPos];
					element++;
				}

			}
		}

		return mainDiagonal;

	}

	/**
	 * Method which extracts the secondary diagonal from a matrix.
	 *
	 * @param matrix
	 * @return the secondary diagonal from the matrix inserted
	 */
	public static int[] extractSecDiagonal(int[][] matrix) {
		int[] secDiagonal = new int[matrix.length];

		for (int row = 0; row < matrix.length; row++) {
			for (int column = 0; column < matrix[row].length; column++) {
				if (row + column == matrix[row].length - 1) {
					secDiagonal[row] = matrix[row][column];
				}
			}
		}

		return secDiagonal;
	}

	/**
	 * Method that extracts the "secondary" diagonal of the position entered by the
	 * user. Do not works for unique elements. WARNING DO NOT CHANGE THIS CODE
	 *
	 * @param matrix the matrix from which we want to extract the diagonal
	 * @param row    the row of the element starts in 0
	 * @param column the column of the element starts in 0
	 * @return an array which is the secondary diagonal of the given position
	 */
	public static int[] extractSecDiagonal(int[][] matrix, int row, int column) {
		int height = matrix.length;
		int width = matrix[row].length;
		int diagonalLength = 0;

		// dependiendo de la posicion la longitud del la diagonal secundaria sera
		// distinta
		if (height - row + width - column - 1 <= width) {
			diagonalLength = height - row + width - column - 1;
		} else {
			diagonalLength = row + column + 1;
		}

		int[] secDiagonal = new int[diagonalLength];
		int element = 0;

		// he tomado como posicion de referencia la row=0, column=0
		if (height - row + width - column - 1 <= width) {
			for (int diagonal = 1 - width; diagonal <= height - 1; diagonal++) {
				for (int verticalPos = Math.max((height - 1), diagonal),
						horizontalPos = -Math.min((height - 1), diagonal); verticalPos >= 0
								&& horizontalPos < width; verticalPos--, horizontalPos++) {
					if (verticalPos + horizontalPos == row + column) {
						secDiagonal[element] = matrix[verticalPos][horizontalPos];
						element++;
					}

				}
			}
		} else {
			for (int diagonal = 1 - width; diagonal <= height - 1; diagonal++) {
				for (int verticalPos = Math.min((height - 1), diagonal),
						horizontalPos = -Math.min(0, diagonal); verticalPos >= 0
								&& horizontalPos < width; verticalPos--, horizontalPos++) {
					if (verticalPos + horizontalPos == row + column) {
						secDiagonal[element] = matrix[verticalPos][horizontalPos];
						element++;
					}

				}
			}
		}

		return secDiagonal;

	}

	/**
	 * shorts the matrix in ascending order.
	 *
	 * @param matrix the matrix we want to short.
	 */
	public static void bubbleShort(int[][] matrix) {

		// the number of times that the matrix must be ordered to be sure that it is
		// ordered
		int iterations = matrix.length * matrix[0].length;

		// as long as the number of times to be sorted is not zero
		do {
			// For each element of the matrix
			for (int row = 0; row < matrix.length; row++) {
				for (int col = 0; col < matrix.length; col++) {
					// I get the following element
					int nextCol = col + 1;

					// if it is the last element of the row
					if (nextCol == matrix[row].length && row != matrix.length - 1) {
						// I will compare with the first of the following
						nextCol = 0;
						// If the next one is higher, I will exchange positions
						if (matrix[row][col] > matrix[row + 1][nextCol]) {
							int aux = matrix[row][col];
							matrix[row][col] = matrix[row + 1][nextCol];
							matrix[row + 1][nextCol] = aux;
						}

					} else if (nextCol != matrix.length) {
						// I compare with the following and if it is greater, I exchange positions
						if (matrix[row][col] > matrix[row][nextCol]) {
							int aux = matrix[row][col];
							matrix[row][col] = matrix[row][nextCol];
							matrix[row][nextCol] = aux;
						}
					}

				}

			}

			iterations--;
		} while (iterations != 0);

	}

	/**
	 * Calculates the transposed matrix.
	 * 
	 * @param matrix the matrix you want to calculate their transposed
	 * @return a transposed matrix
	 */
	public static int[][] calcTransMatrix(int[][] matrix) {
		int[][] transMatrix = new int[matrix[0].length][matrix.length];

		for (int row = 0; row < transMatrix[0].length; row++) {
			for (int col = 0; col < transMatrix.length; col++) {
				transMatrix[col][row] = matrix[row][col];
			}
		}

		return transMatrix;
	}

	/**
	 * Sum all elements of a matrix
	 * 
	 * @param matrix the matrix whose elements we want to sum
	 * @return the summation of all elements
	 */
	public static int sumTotal(int[][] matrix) {
		int sumRows = 0;
		int sumCols = 0;

		// storing the sum of each row number
		for (int[] row : matrix) {
			sumRows += MyArray.sumAll(row);
		}

		// storing the sum of each col number
		for (int col = 0; col < matrix.length; col++) {
			sumCols += MyArray.sumAll(MyMatrix.extractColumn(matrix, col));
		}
		return sumRows + sumCols;
	}
}
