package pente.main;

import java.util.Arrays;

import pente.utilis.MyArray;
import pente.utilis.MyMatrix;

public class Board {

	// ******************
	//
	// *** ATTRIBUTES ***
	private int[][] gameBoard;

	public static void main(String[] args) {
		// main method for dev testing
		Board testBoard = new Board(11);
		Player player1 = new Player("Heri", Token.CROSS);
		Player player2 = new Player("Heri", Token.CIRCLE);
//		testBoard.setToken(player1.getToken(), 0, 0);
//		testBoard.setToken(player2.getToken(), 0, 3);
//		testBoard.setToken(player1.getToken(), 0, 4);
//		testBoard.setToken(player2.getToken(), 0, 2);
//
//		MyArray.display(testBoard.extract5PositionsRow(0, 0, false));
//		MyArray.display(testBoard.extract5PositionsRow(0, 5, false));

		for (int i = 0; i < 11; i++) {
			Arrays.fill(testBoard.gameBoard[i], 0);
		}

		testBoard.gameBoard[5][1] = 1;
		testBoard.gameBoard[5][2] = 1;
		testBoard.gameBoard[5][4] = 1;
		testBoard.gameBoard[5][5] = 1;

		testBoard.gameBoard[3][3] = 1;
		testBoard.gameBoard[4][3] = 1;
		testBoard.gameBoard[6][3] = 1;
		testBoard.gameBoard[7][3] = 1;

		testBoard.gameBoard[4][2] = 1;
		testBoard.gameBoard[3][1] = 1;
		testBoard.gameBoard[6][4] = 1;
		testBoard.gameBoard[7][5] = 1;

		testBoard.gameBoard[4][4] = 1;
		testBoard.gameBoard[3][5] = 1;
		testBoard.gameBoard[8][8] = 1;
		testBoard.gameBoard[9][9] = 1;

		MyMatrix.display(testBoard.gameBoard);
		testBoard.gameBoard[5][0] = 2;
//		testBoard.gameBoard[5][6] = 2;
		testBoard.gameBoard[2][3] = 2;
//		testBoard.gameBoard[8][3] = 2;
		testBoard.gameBoard[2][0] = 2;
//		testBoard.gameBoard[8][6] = 2;
		testBoard.gameBoard[7][7] = 2;
//		testBoard.gameBoard[2][6] = 2;

		testBoard.setToken(Token.CROSS, 10, 10);
		MyMatrix.display(testBoard.gameBoard);
		System.out.println("Total captured: " + testBoard.captureStones(10, 10, Token.CROSS));

		MyMatrix.display(testBoard.gameBoard);

		System.out.println(Token.CROSS.getValue());
	}

	// ********************
	//
	// *** CONSTRUCTORS ***

	/**
	 * Constructs a new Board for the Pente game with the specified size.
	 * Initializes a two-dimensional array representing the game board.
	 *
	 * @param size the size of the board; the board will be a square with dimensions
	 *             size x size. Must be a positive integer.
	 */
	public Board(int size) {
		this.gameBoard = new int[size][size];
	}

	// **********************
	//
	// *** PUBLIC METHODS ***

	/**
	 * Checks and performs captures of opponent tokens in all possible directions
	 * (row, column, main diagonal, and secondary diagonal) according to the Pente
	 * game rules. A capture occurs when two opponent tokens are flanked by the
	 * current player's tokens. Captured tokens are removed from the board, and the
	 * method returns the total number of captured tokens.
	 * 
	 * @param row                The row of the board where the current player's
	 *                           token is located.
	 * @param col                The column of the board where the current player's
	 *                           token is located.
	 * @param currentPlayerToken The token of the current player, indicating whether
	 *                           it is Token.CIRCLE or Token.CROSS.
	 * 
	 * @return The total number of tokens captured in all directions.
	 *
	 * @throws IllegalArgumentException If the row or column values are out of the
	 *                                  board's bounds.
	 * @throws IllegalArgumentException If the currentPlayerToken parameter is null.
	 */
	public int captureStones(int row, int col, Token currentPlayerToken) {
		// Row and column validation
		if (row < 0 || row >= this.gameBoard.length || col < 0 || col >= this.gameBoard[0].length) {
			throw new IllegalArgumentException("Row or column is out of bounds.");
		}
		// Enum validation
		if (currentPlayerToken == null) {
			throw new IllegalArgumentException("currentPlayerToken cannot be null.");
		}
		int stonesCapt = 0;

		// check if can capture in row
		stonesCapt += captureInRow(currentPlayerToken, row, col);

		// check if can capture in column
		stonesCapt += captureInCol(currentPlayerToken, row, col);

		// check if can capture in the main diagonal
		stonesCapt += captureInMainDiagonal(currentPlayerToken, row, col);

		// captureInSecDiag
		stonesCapt += captureInSecDiagonal(currentPlayerToken, row, col);

		return stonesCapt;
	}

	/**
	 * Attempts to place a token on the game board at the specified position.
	 *
	 * @param token the {@link Token} to be placed on the board
	 * @param row   the row index of the cell where the token is to be placed
	 * @param col   the column index of the cell where the token is to be placed
	 * @return {@code true} if the token was successfully placed, {@code false} if
	 *         the cell was already occupied
	 * @throws IllegalArgumentException if the specified {@code row} or {@code col}
	 *                                  is outside the bounds of the game board
	 */
	public boolean setToken(Token token, int row, int col) {
		// Row and column validation
		if (row < 0 || row >= this.gameBoard.length || col < 0 || col >= this.gameBoard[0].length) {
			throw new IllegalArgumentException("Row or column is out of bounds.");
		}

		Boolean isSet = false;

		// check if any stone before
		if (this.gameBoard[row][col] == 0) {
			// set token
			this.gameBoard[row][col] = token.getValue();
			isSet = true;
		}

		return isSet;
	}

	/**
	 * Checks if the game board contains any empty squares.
	 *
	 * <p>
	 * This method iterates through the entire game board, represented as a 2D array
	 * of integers, to determine if there are any cells marked as empty. An empty
	 * square is identified by comparing the cell value to
	 * {@link Token#EMPTY#getValue()}.
	 *
	 * @return {@code true} if there is at least one empty square on the board;
	 *         {@code false} otherwise.
	 */
	public boolean hasEmptySquares() {
		boolean isEmpty = false;

		for (int[] row : this.gameBoard) {
			for (int elem : row) {
				if (elem == Token.EMPTY.getValue()) {
					isEmpty = true;
				}
			}
		}

		return isEmpty;
	}

	// **********************
	//
	// *** PUBLIC METHODS ***

	/**
	 * Checks and performs captures of opponent tokens along the secondary diagonal
	 * (/ direction) according to the Pente game rules. A capture occurs when two
	 * opponent tokens are flanked by the current player's tokens along the
	 * secondary diagonal. The captured tokens are removed from the board, and the
	 * method returns the number of captured tokens.
	 * 
	 * The secondary diagonal extends from the bottom-left to the top-right
	 * direction.
	 * 
	 * @param row                The row of the board where the current player's
	 *                           token is located.
	 * @param col                The column of the board to check for captures.
	 * @param currentPlayerToken The token of the current player, indicating whether
	 *                           it is Token.CIRCLE or Token.CROSS.
	 * 
	 * @return The total number of tokens captured along the secondary diagonal.
	 *
	 * @throws IllegalArgumentException If the row or column values are out of the
	 *                                  board's bounds.
	 * @throws IllegalArgumentException If the currentPlayerToken parameter is null.
	 */
	private int captureInSecDiagonal(Token currentPlayerToken, int row, int col) {
		// Row and column validation
		if (row < 0 || row >= this.gameBoard.length || col < 0 || col >= this.gameBoard[0].length) {
			throw new IllegalArgumentException("Row or column is out of bounds.");
		}
		// Enum validation
		if (currentPlayerToken == null) {
			throw new IllegalArgumentException("currentPlayerToken cannot be null.");
		}
		int stonesCapt = 0;
		Token opponentToken = (currentPlayerToken == Token.CIRCLE) ? Token.CROSS : Token.CIRCLE;

		// Check for captures upwards-right (/ direction)
		if (row - 3 >= 0 && col + 3 < this.gameBoard[0].length) {
			// Check pattern: X ⚪⚪ X (up-right to down-left)
			if (this.gameBoard[row - 3][col + 3] == currentPlayerToken.getValue()
					&& this.gameBoard[row - 2][col + 2] == opponentToken.getValue()
					&& this.gameBoard[row - 1][col + 1] == opponentToken.getValue()) {

				// Remove captured stones
				this.gameBoard[row - 2][col + 2] = 0;
				this.gameBoard[row - 1][col + 1] = 0;
				// Add captured stones
				stonesCapt += 2;
			}
		}

		// Check for captures downwards-left (/ direction)
		if (row + 3 < this.gameBoard.length && col - 3 >= 0) {
			// Check pattern: X ⚪⚪ X (down-left to up-right)
			if (this.gameBoard[row + 3][col - 3] == currentPlayerToken.getValue()
					&& this.gameBoard[row + 2][col - 2] == opponentToken.getValue()
					&& this.gameBoard[row + 1][col - 1] == opponentToken.getValue()) {

				// Remove captured stones
				this.gameBoard[row + 2][col - 2] = 0;
				this.gameBoard[row + 1][col - 1] = 0;
				// Add captured stones
				stonesCapt += 2;
			}
		}

		return stonesCapt;
	}

	/**
	 * Checks and performs captures of opponent tokens along the main diagonal (\
	 * direction) according to the Pente game rules. A capture occurs when two
	 * opponent tokens are flanked by the current player's tokens along the main
	 * diagonal. The captured tokens are removed from the board, and the method
	 * returns the number of captured tokens.
	 * 
	 * The main diagonal extends from the top-left to the bottom-right direction.
	 * 
	 * @param row                The row of the board where the current player's
	 *                           token is located.
	 * @param col                The column of the board to check for captures.
	 * @param currentPlayerToken The token of the current player, indicating whether
	 *                           it is Token.CIRCLE or Token.CROSS.
	 * 
	 * @return The total number of tokens captured along the main diagonal.
	 *
	 * @throws IllegalArgumentException If the row or column values are out of the
	 *                                  board's bounds.
	 * @throws IllegalArgumentException If the currentPlayerToken parameter is null.
	 */
	private int captureInMainDiagonal(Token currentPlayerToken, int row, int col) {
		// Row and column validation
		if (row < 0 || row >= this.gameBoard.length || col < 0 || col >= this.gameBoard[0].length) {
			throw new IllegalArgumentException("Row or column is out of bounds.");
		}
		// Enum validation
		if (currentPlayerToken == null) {
			throw new IllegalArgumentException("currentPlayerToken cannot be null.");
		}
		int stonesCapt = 0;
		Token opponentToken = (currentPlayerToken == Token.CIRCLE) ? Token.CROSS : Token.CIRCLE;

		// Check for captures upwards-left (\ direction)
		if (row - 3 >= 0 && col - 3 >= 0) {
			// Check pattern: X ⚪⚪ X (up-left to down-right)
			if (this.gameBoard[row - 3][col - 3] == currentPlayerToken.getValue()
					&& this.gameBoard[row - 2][col - 2] == opponentToken.getValue()
					&& this.gameBoard[row - 1][col - 1] == opponentToken.getValue()) {

				// Remove captured stones
				this.gameBoard[row - 2][col - 2] = 0;
				this.gameBoard[row - 1][col - 1] = 0;
				// Add captured stones
				stonesCapt += 2;
			}
		}

		// Check for captures downwards-right (\ direction)
		if (row + 3 < this.gameBoard.length && col + 3 < this.gameBoard[0].length) {
			// Check pattern: X ⚪⚪ X (down-right to up-left)
			if (this.gameBoard[row + 3][col + 3] == currentPlayerToken.getValue()
					&& this.gameBoard[row + 2][col + 2] == opponentToken.getValue()
					&& this.gameBoard[row + 1][col + 1] == opponentToken.getValue()) {

				// Remove captured stones
				this.gameBoard[row + 2][col + 2] = 0;
				this.gameBoard[row + 1][col + 1] = 0;
				// Add captured stones
				stonesCapt += 2;
			}
		}

		return stonesCapt;
	}

	/**
	 * Checks and performs captures of opponent tokens in a specific column
	 * according to the Pente game rules. A capture occurs when two opponent tokens
	 * are flanked by the current player's tokens in a column. The captured tokens
	 * are removed from the board, and the method returns the number of captured
	 * tokens.
	 * 
	 * @param row                The row of the board where the current player's
	 *                           token is located.
	 * @param col                The column of the board to check for captures.
	 * @param currentPlayerToken The token of the current player, indicating whether
	 *                           it is Token.CIRCLE or Token.CROSS.
	 * 
	 * @return The total number of tokens captured in the column.
	 *
	 * @throws IllegalArgumentException If the row or column values are out of the
	 *                                  board's bounds.
	 * @throws IllegalArgumentException If the currentPlayerToken parameter is null.
	 */
	private int captureInCol(Token currentPlayerToken, int row, int col) {
		// Row and column validation
		if (row < 0 || row >= this.gameBoard.length || col < 0 || col >= this.gameBoard[0].length) {
			throw new IllegalArgumentException("Row or column is out of bounds.");
		}
		// Enum validation
		if (currentPlayerToken == null) {
			throw new IllegalArgumentException("currentPlayerToken cannot be null.");
		}
		int stonesCapt = 0;
		Token opponentToken = (currentPlayerToken == Token.CIRCLE) ? Token.CROSS : Token.CIRCLE;

		// Check for captures upwards
		if (row - 3 >= 0) {
			// Check pattern: X ⚪⚪ X (top-to-bottom)
			if (this.gameBoard[row - 3][col] == currentPlayerToken.getValue()
					&& this.gameBoard[row - 2][col] == opponentToken.getValue()
					&& this.gameBoard[row - 1][col] == opponentToken.getValue()) {

				// Remove captured stones
				this.gameBoard[row - 2][col] = 0;
				this.gameBoard[row - 1][col] = 0;
				// add captured stones
				stonesCapt += 2;
			}
		}

		// Check for captures downwards
		if (row + 3 < this.gameBoard.length) {
			// Check pattern: X ⚪⚪ X (bottom-to-top)
			if (this.gameBoard[row + 3][col] == currentPlayerToken.getValue()
					&& this.gameBoard[row + 2][col] == opponentToken.getValue()
					&& this.gameBoard[row + 1][col] == opponentToken.getValue()) {

				// Remove captured stones
				this.gameBoard[row + 2][col] = 0;
				this.gameBoard[row + 1][col] = 0;
				// add captured stones
				stonesCapt += 2;
			}
		}

		return stonesCapt;
	}

	/**
	 * Checks and performs captures of opponent tokens in a specific row according
	 * to the Pente game rules. A capture occurs when two opponent tokens are
	 * flanked by the current player's tokens in a row. The captured tokens are
	 * removed from the board, and the method returns the number of captured tokens.
	 *
	 * @param row                The row of the board to check for captures.
	 * @param col                The column of the board where the current player's
	 *                           token is located.
	 * @param currentPlayerToken The token of the current player, indicating whether
	 *                           it is Token.CIRCLE or Token.CROSS.
	 * 
	 * @return The total number of tokens captured in the row.
	 *
	 * @throws IllegalArgumentException If the row or column values are out of the
	 *                                  board's bounds.
	 * @throws IllegalArgumentException If the currentPlayerToken parameter is null.
	 */
	private int captureInRow(Token currentPlayerToken, int row, int col) {
		// Row and column validation
		if (row < 0 || row >= this.gameBoard.length || col < 0 || col >= this.gameBoard[0].length) {
			throw new IllegalArgumentException("Row or column is out of bounds.");
		}
		// Enum validation
		if (currentPlayerToken == null) {
			throw new IllegalArgumentException("currentPlayerToken cannot be null.");
		}
		int stonesCapt = 0;
		Token opponentToken = (currentPlayerToken == Token.CIRCLE) ? Token.CROSS : Token.CIRCLE;

		// Check for captures to the left
		if (col - 3 >= 0) {
			// Check pattern: X ⚪⚪ X (left-to-right)
			if (this.gameBoard[row][col - 3] == currentPlayerToken.getValue()
					&& this.gameBoard[row][col - 2] == opponentToken.getValue()
					&& this.gameBoard[row][col - 1] == opponentToken.getValue()) {

				// Remove captured stones
				this.gameBoard[row][col - 2] = 0;
				this.gameBoard[row][col - 1] = 0;
				// add captured stones
				stonesCapt += 2;
			}
		}

		// Check for captures to the right
		if (col + 3 < this.gameBoard[0].length) {
			// Check pattern: X ⚪⚪ X (right-to-left)
			if (this.gameBoard[row][col + 3] == currentPlayerToken.getValue()
					&& this.gameBoard[row][col + 2] == opponentToken.getValue()
					&& this.gameBoard[row][col + 1] == opponentToken.getValue()) {

				// Remove captured stones
				this.gameBoard[row][col + 2] = 0;
				this.gameBoard[row][col + 1] = 0;
				// add captured stones
				stonesCapt += 2;
			}
		}

		return stonesCapt;
	}

	// *************************
	//
	// *** DISCARDED methods ***

	/**
	 * Extracts 5 consecutive positions from a row in the Pente game board, either
	 * to the right or to the left, starting from the specified column.
	 *
	 * @param row     The row from which to extract positions. Must be within bounds
	 *                of the game board.
	 * @param col     The starting column from which to extract positions. Must be
	 *                within bounds of the game board.
	 * @param toRight If {@code true}, extracts positions to the right; if
	 *                {@code false}, extracts positions to the left.
	 * @return An array of 5 integers containing the extracted positions. If there
	 *         are fewer than 5 valid positions in the specified direction, the
	 *         remaining values in the array are left as their default (0 for int).
	 * @throws IllegalArgumentException If the specified row or column is out of
	 *                                  bounds of the game board.
	 */
//	private int[] extract5PositionsRow(int row, int col, boolean toRight) {
//		// Row and column validation
//		if (row < 0 || row >= this.gameBoard.length || col < 0 || col >= this.gameBoard[0].length) {
//			throw new IllegalArgumentException("Row or column is out of bounds.");
//		}
//
//		int[] extrArr = new int[5];
//
//		// for the 5 elements of the array to extract
//		for (int i = 0; i < 5; i++) {
//			// check if right or left
//			int currentCol = toRight ? col + i : col - i;
//
//			// Check the bounds of the matrix for the row
//			if (currentCol >= 0 && currentCol < this.gameBoard[row].length) {
//				// Add the value of the board position to the array
//				extrArr[i] = this.gameBoard[row][currentCol];
//			}
//		}
//
//		return extrArr;
//	}
}
