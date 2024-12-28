package pente.main;

import pente.utilis.ReadKBData;

public class PenteApp {

	// *****************
	//
	// *** CONSTANTS ***
	private static final int MIN_BOARD_SIZE = 10;
	private static final int MAX_BOARD_SIZE = 19;
	private static final int MIN_STONES_TO_WIN = 5;
	private static final int MAX_STONES_TO_WIN = 10;

	// ******************
	//
	// *** ATTRIBUTES ***
	private int boardSize;
	private Board board;
	private int stonesToWin;
	private Player player1;
	private Player player2;
	private Player currentPlayer;
	private int rowToPlace;
	private int colToPlace;

	/**
	 * The main method serves as the entry point for the application.
	 * 
	 * @param args Command-line arguments passed to the application. These are not
	 *             used in this implementation.
	 */
	public static void main(String[] args) {
		PenteApp app = new PenteApp();
		app.run();
	}

	/**
	 * Executes the main game loop of the Pente application. This method handles the
	 * initialization of the game, turn-based gameplay, and game-ending conditions,
	 * while interacting with the user through the console.
	 */
	public void run() {
		do {
			//
			// *** Game state initialization ***
			initializeGame();

			System.out.println(); // pretty console

			//
			// *** Starting a new game ***
			boolean hasFiveInLine;
			do {

				playTurn();

				// Check if the current player can catch any stone
				int capturedStones = board.captureStones(currentPlayer.getToken(), rowToPlace, colToPlace);
				// add captured stones to the current player
				currentPlayer.addStones(capturedStones);

				// Display the board status
				Board.printGameBoard(board, rowToPlace, colToPlace);

				// Display two players captured stones
				System.out.println(Color.RED.getCode() + player1.getName() + Color.RESET.getCode() + " "
						+ player1.getToken() + " captured tokens: " + player1.getStonesCaptured());
				System.out.println(Color.YELLOW.getCode() + player2.getName() + Color.RESET.getCode() + " "
						+ player2.getToken() + " captured tokens: " + player2.getStonesCaptured());

				System.out.println(); // pretty console

				// Check if the board has five in line
				hasFiveInLine = board.checkFiveInLine(currentPlayer.getToken(), rowToPlace, colToPlace);

			} while (player1.getStonesCaptured() < stonesToWin && player2.getStonesCaptured() < stonesToWin
					&& board.hasEmptySquares() && !hasFiveInLine);

			// Display end message that the game is over
			if (hasFiveInLine) {
				System.out.println(
						currentPlayer.getName() + " has managed to place 5 stones in a line and wins the game!!");
			} else if (player1.getStonesCaptured() >= stonesToWin) {
				System.out.println(player1.getName() + " has captured " + stonesToWin + " stones and wins the game!!");
			} else if (player2.getStonesCaptured() >= stonesToWin) {
				System.out.println(player2.getName() + " has captured " + stonesToWin + " stones and wins the game!!");
			} else if (!board.hasEmptySquares()) {
				System.out.println("The board is full. The game has ended in a draw!!");
			}

			// Pretty console
			System.out.println();

		} while (askValidInput("Do you want to play again? (0-no | 1-yes)", 0, 1) == 1);

	}

	// ***********************
	//
	// *** PRIVATE methods ***

	/**
	 * Prompts the player to enter their name and validates the input.
	 *
	 * @param playerNum the number of the player.
	 * @return the valid name entered by the player.
	 * @throws IllegalArgumentException if the player name is empty or exceeds 20
	 *                                  characters.
	 */
	private String askPlayerName(int playerNum) {
		String playerName;

		do {
			playerName = ReadKBData.enterString("Player " + playerNum + ", enter your name:");
			if ((playerName == null || playerName.trim().isEmpty()) || (playerName.length() > 20)) {
				System.out.println("Invalid name. Name cannot be empty or exceeds 20 characters.");
			}
		} while ((playerName == null || playerName.trim().isEmpty()) || (playerName.length() > 20));

		return playerName;
	}

	/**
	 * Handles the logic for a single turn in the game. This method alternates
	 * between players, prompts the current player for a valid row and column to
	 * place their token, and validates the input to ensure the chosen square is
	 * empty.
	 */
	private void playTurn() {
		currentPlayer = (currentPlayer == player1) ? player2 : player1;
		System.out.println((currentPlayer == player1 ? Color.RED.getCode() + player1.getName()
				: Color.YELLOW.getCode() + player2.getName()) + " it's your turn" + Color.RESET.getCode());

		// Row and Col to place the stone
		rowToPlace = askValidInput("Row to place your stone:", 1, boardSize) - 1;
		colToPlace = askValidInput("Column to place your stone:", 1, boardSize) - 1;

		// ask again if the square is not empty
		while (!board.setToken(currentPlayer.getToken(), rowToPlace, colToPlace)) {
			System.err.println("The square is not empty!!");
			rowToPlace = askValidInput("Row to place your stone:", 1, boardSize) - 1;
			colToPlace = askValidInput("Column to place your stone:", 1, boardSize) - 1;
		}
	}

	/**
	 * Initializes the game by setting up the board, the winning conditions, and the
	 * players.
	 */
	private void initializeGame() {
		boardSize = askValidInput("Enter the board size (10 - 19):", MIN_BOARD_SIZE, MAX_BOARD_SIZE);
		board = new Board(boardSize);
		stonesToWin = askValidInput("Enter the amount of stones to win (5-10):", MIN_STONES_TO_WIN, MAX_STONES_TO_WIN);

		player1 = new Player(askPlayerName(1), Token.CIRCLE);
		player2 = new Player(askPlayerName(2), Token.CROSS);
		currentPlayer = player2; // Player 2 starts the game
		Board.printGameBoard(board, -1, -1);
	}

	/**
	 * Prompts the user to enter a valid integer input within a specified range.
	 * 
	 * @param message The message displayed to the user prompting them for input.
	 * @param min     The minimum allowable input value.
	 * @param max     The maximum allowable input value.
	 * @return A valid integer input within the specified range.
	 */
	private int askValidInput(String message, int min, int max) {
		int value;
		do {
			value = ReadKBData.enterInt(message);
			if (value < min || value > max) {
				System.err.println("Input must be between " + min + " and " + max);
			}
		} while (value < min || value > max);
		return value;
	}

}
