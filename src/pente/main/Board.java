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

		testBoard.gameBoard[0][0] = 1;
		testBoard.gameBoard[1][1] = 1;
		testBoard.gameBoard[2][2] = 1;
		testBoard.gameBoard[3][3] = 1;

		testBoard.gameBoard[4][4] = 1;
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

		System.out.println(testBoard.checkFiveInLine(Token.CIRCLE, 4, 4));
//		testBoard.gameBoard[5][0] = 2;
////		testBoard.gameBoard[5][6] = 2;
//		testBoard.gameBoard[2][3] = 2;
////		testBoard.gameBoard[8][3] = 2;
//		testBoard.gameBoard[2][0] = 2;
////		testBoard.gameBoard[8][6] = 2;
//		testBoard.gameBoard[7][7] = 2;
////		testBoard.gameBoard[2][6] = 2;

		testBoard.setToken(Token.CROSS, 10, 10);
		MyMatrix.display(testBoard.gameBoard);
		System.out.println("Total captured: " + testBoard.captureStones(Token.CROSS, 10, 10));

		MyMatrix.display(testBoard.gameBoard);

		System.out.println(Token.CROSS.getValue());

		Board.printGameBoard(testBoard, 1, 1);
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
	 * Prints the current state of the game board to the console. Each square is
	 * visually formatted with grid lines, and an optional square can be highlighted
	 * to indicate the last placed token or any specific position.
	 *
	 * 
	 * // Print the board without highlighting printGameBoard(gameBoard, -1, -1);
	 * 
	 * // Print the board with the square at (1, 2) highlighted
	 * printGameBoard(gameBoard, 1, 2);
	 * </pre>
	 *
	 * @param board        The game board represented as a {@code Board} object. The
	 *                     {@code gameBoard} attribute should be a 2D array where
	 *                     each other values represent different players' tokens.
	 * @param highlightRow The row index of the square to highlight. Pass -1 if no
	 *                     highlighting is required.
	 * @param highlightCol The column index of the square to highlight. Pass -1 if
	 *                     no highlighting is required.
	 *
	 * @throws IllegalArgumentException If the game board is null or empty.
	 * @throws IllegalArgumentException If the highlightRow or highlightCol are out
	 *                                  of bounds when highlighting is requested.
	 */
	public static void printGameBoard(Board board, int highlightRow, int highlightCol) {
		// Game board validation
		if (board.gameBoard == null || board.gameBoard.length == 0) {
			throw new IllegalArgumentException("The game board cannot be null or empty.");
		}
		if (highlightRow != -1 && (highlightRow < 0 || highlightRow >= board.gameBoard.length || highlightCol < 0
				|| highlightCol >= board.gameBoard[0].length)) {
			throw new IllegalArgumentException("Highlight position is out of bounds.");
		}

		// Loop through each row of the board
		for (int row = 0; row < board.gameBoard.length; row++) {
			// Print horizontal dividers for each row
			System.out.print(" ---".repeat(board.gameBoard[row].length));
			System.out.println();

			// Print the row content
			for (int col = 0; col < board.gameBoard[row].length; col++) {
				String token = getTokenSymbol(board.gameBoard[row][col]);

				// Highlight the last placed token
				if (row == highlightRow && col == highlightCol) {
					token = Color.RED.getCode() + token + Color.RESET.getCode();
				}

				printSquare(board.gameBoard, row, col, token);
			}
		}

		// Print the bottom boundary for the last row
		System.out.print(" ---".repeat(board.gameBoard[0].length));
		System.out.println();
	}

	public boolean checkFiveInLine(Token currentPlayerToken, int row, int col) {
		// Row and column validation
		if (row < 0 || row >= this.gameBoard.length || col < 0 || col >= this.gameBoard[0].length) {
			throw new IllegalArgumentException("Row or column is out of bounds.");
		}
		// Enum validation
		if (currentPlayerToken == null) {
			throw new IllegalArgumentException("currentPlayerToken cannot be null.");
		}
		boolean hasFiveInLine = false;

		// check five in line in row
		hasFiveInLine = checkFiveInRow(currentPlayerToken, row, col);

		if (!hasFiveInLine) {
			hasFiveInLine = checkFiveInColumn(currentPlayerToken, row, col);
		}
		if (!hasFiveInLine) {
			hasFiveInLine = checkFiveInMainDiag(currentPlayerToken, row, col);
		}
		if (!hasFiveInLine) {
			hasFiveInLine = checkFiveInSecDiag(currentPlayerToken, row, col);
		}

		return hasFiveInLine;
	}

	/**
	 * Checks and performs captures of opponent tokens in all possible directions
	 * (row, column, main diagonal, and secondary diagonal) according to the Pente
	 * game rules. A capture occurs when two opponent tokens are flanked by the
	 * current player's tokens. Captured tokens are removed from the board, and the
	 * method returns the total number of captured tokens.
	 * 
	 * @param currentPlayerToken The token of the current player, indicating whether
	 *                           it is Token.CIRCLE or Token.CROSS.
	 * @param row                The row of the board where the current player's
	 *                           token is located.
	 * @param col                The column of the board where the current player's
	 *                           token is located.
	 * 
	 * @return The total number of tokens captured in all directions.
	 *
	 * @throws IllegalArgumentException If the row or column values are out of the
	 *                                  board's bounds.
	 * @throws IllegalArgumentException If the currentPlayerToken parameter is null.
	 */
	public int captureStones(Token currentPlayerToken, int row, int col) {
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

	// ***********************
	//
	// *** PRIVATE METHODS ***

	/**
	 * Checks if there are five consecutive tokens in the secondary diagonal
	 * (top-right to bottom-left) of the game board starting from the given position
	 * (row, col) for the specified player.
	 *
	 * @param currentPlayerToken The token of the current player. Cannot be null.
	 * @param row                The row index of the starting position to check.
	 *                           Must be within the game board's bounds.
	 * @param col                The column index of the starting position to check.
	 *                           Must be within the game board's bounds.
	 * @return {@code true} if there are five or more consecutive tokens of the
	 *         current player in the secondary diagonal, {@code false} otherwise.
	 * @throws IllegalArgumentException If the row or column is out of bounds, or if
	 *                                  {@code currentPlayerToken} is {@code null}.
	 */
	private boolean checkFiveInSecDiag(Token currentPlayerToken, int row, int col) {
		// Row and column validation
		if (row < 0 || row >= this.gameBoard.length || col < 0 || col >= this.gameBoard[0].length) {
			throw new IllegalArgumentException("Row or column is out of bounds.");
		}
		// Enum validation
		if (currentPlayerToken == null) {
			throw new IllegalArgumentException("currentPlayerToken cannot be null.");
		}

		// Start counting from the current token
		int count = 1;

		// Check downward-left (↙)
		for (int r = row + 1, c = col - 1; r < this.gameBoard.length && c >= 0; r++, c--) {
			if (this.gameBoard[r][c] == currentPlayerToken.getValue()) {
				count++;
			} else {
				// Stop when the sequence is broken
				break;
			}
		}

		// Check upward-right (↗)
		for (int r = row - 1, c = col + 1; r >= 0 && c < this.gameBoard[0].length; r--, c++) {
			if (this.gameBoard[r][c] == currentPlayerToken.getValue()) {
				count++;
			} else {
				break; // Stop when the sequence is broken
			}
		}

		// If there are 5 or more tokens in the secondary diagonal, return true
		return count >= 5;
	}

	/**
	 * Checks if there are five consecutive tokens in the main diagonal (top-left to
	 * bottom-right) of the game board starting from the given position (row, col)
	 * for the specified player.
	 *
	 * @param currentPlayerToken The token of the current player. Cannot be null.
	 * @param row                The row index of the starting position to check.
	 *                           Must be within the game board's bounds.
	 * @param col                The column index of the starting position to check.
	 *                           Must be within the game board's bounds.
	 * @return {@code true} if there are five or more consecutive tokens of the
	 *         current player in the main diagonal, {@code false} otherwise.
	 * @throws IllegalArgumentException If the row or column is out of bounds, or if
	 *                                  {@code currentPlayerToken} is {@code null}.
	 */
	private boolean checkFiveInMainDiag(Token currentPlayerToken, int row, int col) {
		// Row and column validation
		if (row < 0 || row >= this.gameBoard.length || col < 0 || col >= this.gameBoard[0].length) {
			throw new IllegalArgumentException("Row or column is out of bounds.");
		}
		// Enum validation
		if (currentPlayerToken == null) {
			throw new IllegalArgumentException("currentPlayerToken cannot be null.");
		}

		// Start counting from the current token
		int count = 1;

		// Check downward-right (↘)
		for (int r = row + 1, c = col + 1; r < this.gameBoard.length && c < this.gameBoard[0].length; r++, c++) {
			if (this.gameBoard[r][c] == currentPlayerToken.getValue()) {
				count++;
			} else {
				// Stop when the sequence is broken
				break;
			}
		}

		// Check upward-left (↖)
		for (int r = row - 1, c = col - 1; r >= 0 && c >= 0; r--, c--) {
			if (this.gameBoard[r][c] == currentPlayerToken.getValue()) {
				count++;
			} else {
				break; // Stop when the sequence is broken
			}
		}

		// If there are 5 or more tokens in the main diagonal, return true
		return count >= 5;
	}

	/**
	 * Checks if there are five consecutive tokens in a specific column of the game
	 * board starting from the given position (row, col) for the specified player.
	 *
	 * @param currentPlayerToken The token of the current player. Cannot be null.
	 * @param row                The row index of the starting position to check.
	 *                           Must be within the game board's bounds.
	 * @param col                The column index of the starting position to check.
	 *                           Must be within the game board's bounds.
	 * @return {@code true} if there are five or more consecutive tokens of the
	 *         current player in the specified column, {@code false} otherwise.
	 * @throws IllegalArgumentException If the row or column is out of bounds, or if
	 *                                  {@code currentPlayerToken} is {@code null}.
	 */
	private boolean checkFiveInColumn(Token currentPlayerToken, int row, int col) {
		// Row and column validation
		if (row < 0 || row >= this.gameBoard.length || col < 0 || col >= this.gameBoard[0].length) {
			throw new IllegalArgumentException("Row or column is out of bounds.");
		}
		// Enum validation
		if (currentPlayerToken == null) {
			throw new IllegalArgumentException("currentPlayerToken cannot be null.");
		}

		// Start counting from the current token
		int count = 1;

		// Check downward
		for (int r = row + 1; r < this.gameBoard.length; r++) {
			if (this.gameBoard[r][col] == currentPlayerToken.getValue()) {
				count++;
			} else {
				// Stop when the sequence is broken
				break;
			}
		}

		// Check upward
		for (int r = row - 1; r >= 0; r--) {
			if (this.gameBoard[r][col] == currentPlayerToken.getValue()) {
				count++;
			} else {
				break; // Stop when the sequence is broken
			}
		}

		// If there are 5 or more tokens in a column, return true
		return count >= 5;
	}

	/**
	 * Checks if there are five consecutive tokens in a specific row of the game
	 * board starting from the given position (row, col) for the specified player.
	 *
	 * @param currentPlayerToken The token of the current player. Cannot be null.
	 * @param row                The row index of the starting position to check.
	 *                           Must be within the game board's bounds.
	 * @param col                The column index of the starting position to check.
	 *                           Must be within the game board's bounds.
	 * @return {@code true} if there are five or more consecutive tokens of the
	 *         current player in the specified row, {@code false} otherwise.
	 * @throws IllegalArgumentException If the row or column is out of bounds, or if
	 *                                  {@code currentPlayerToken} is {@code null}.
	 */
	private boolean checkFiveInRow(Token currentPlayerToken, int row, int col) {
		// Row and column validation
		if (row < 0 || row >= this.gameBoard.length || col < 0 || col >= this.gameBoard[0].length) {
			throw new IllegalArgumentException("Row or column is out of bounds.");
		}
		// Enum validation
		if (currentPlayerToken == null) {
			throw new IllegalArgumentException("currentPlayerToken cannot be null.");
		}

		// Start counting from the current token
		int count = 1;

		// Check to the right
		for (int c = col + 1; c < this.gameBoard[0].length; c++) {
			if (this.gameBoard[row][c] == currentPlayerToken.getValue()) {
				count++;
			} else {
				// Stop when the sequence is broken
				break;
			}
		}

		// Check to the left
		for (int c = col - 1; c >= 0; c--) {
			if (this.gameBoard[row][c] == currentPlayerToken.getValue()) {
				count++;
			} else {
				break; // Stop when the sequence is broken
			}
		}

		// If there are 5 or more tokens in a row, return true
		return count >= 5;
	}

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

	/**
	 * Prints a square with the appropriate formatting based on its position.
	 */
	private static void printSquare(int[][] gameBoard, int row, int col, String token) {
		if (col == 0) {
			System.out.print("| " + token + " |");
		} else if (col + 1 == gameBoard[row].length) {
			System.out.print(" " + token + " |\n");
		} else {
			System.out.print(" " + token + " |");
		}
	}

	/**
	 * Returns the symbol to display for a given token value.
	 *
	 * @param squareValue The value in the game board square. Typically, 0
	 *                    represents an empty square.
	 * @return The symbol to display as a {@code String}.
	 */
	private static String getTokenSymbol(int squareValue) {
		switch (squareValue) {
		case 1:
			return "O"; // Token for Player 1
		case 2:
			return "X"; // Token for Player 2
		default:
			return " "; // Empty cell
		}
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
