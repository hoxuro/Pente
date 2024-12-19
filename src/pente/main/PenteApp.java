package pente.main;

import pente.utilis.ReadKBData;

public class PenteApp {

	public static void main(String[] args) {
		PenteApp app = new PenteApp();
		app.run();
	}

	private int boardSize;
	private Board board;
	private int stonesToWin;
	private Player player1;
	private Player player2;
	private Player currentPlayer;

	public void run() {
		//
		//
		// *** Game components initialization ***

		// Asking the current game board size
		do {
			boardSize = ReadKBData.enterInt("Enter the board size (10 - 19):");
			if (boardSize < 10 || boardSize > 19) {
				System.out.println("Incorrect board size (10-19)");
			}
		} while (boardSize < 10 || boardSize > 19);

		// Creation of a board with the asked size
		this.board = new Board(boardSize);

		// Asking and setting the amount of tokens to win
		do {
			stonesToWin = ReadKBData.enterInt("Enter the amount of stones to win");
			if (stonesToWin < 5 || stonesToWin > 10) {
				System.out.println("Incorrect number of stones (5-10)");
			}
		} while (stonesToWin < 5 || stonesToWin > 10);

		// Player 1 initialization
		player1 = new Player(askPlayerName(1), Token.CIRCLE);

		// Player 2 initialization
		player2 = new Player(askPlayerName(2), Token.CROSS);

		System.out.println(); // pretty console

		// Setting current player
		currentPlayer = player2;

		// Display the empty board
		Board.printGameBoard(board, -1, -1);

		System.out.println(); // pretty console

		//
		//
		// *** Starting a new game ***
		boolean hasFiveInLine;
		do {
			// new turn
			currentPlayer = (currentPlayer == player1) ? player2 : player1;
			System.out.println((currentPlayer == player1 ? Color.RED.getCode() + player1.getName()
					: Color.YELLOW.getCode() + player2.getName()) + " it's your turn" + Color.RESET.getCode());

			// asking position to place the player stone
			Boolean isPlaced;
			int rowToPlace;
			int colToPlace;
			do {

				// asking row position
				do {
					rowToPlace = ReadKBData.enterInt("Row to place your stone:") - 1;
					if (rowToPlace < 0 || rowToPlace >= boardSize) {
						System.err.println("Row must be on range! (1-" + boardSize + ")");
					}
				} while (rowToPlace < 0 || rowToPlace >= boardSize);

				// asking column position

				do {
					colToPlace = ReadKBData.enterInt("Column to place your stone:") - 1;
					if (colToPlace < 0 || colToPlace >= boardSize) {
						System.err.println("Column must be on range! (1-" + boardSize + ")");
					}
				} while (colToPlace < 0 || colToPlace >= boardSize);

				// Setting the token
				isPlaced = board.setToken(currentPlayer.getToken(), rowToPlace, colToPlace);

				// notify the square is not empty
				if (!isPlaced) {
					System.out
							.println(Color.RED.getCode() + "Error! The square is not empty..." + Color.RESET.getCode());
				}

			} while (!isPlaced);

			// Now we need to check if the current player can catch any stone
			int capturedStones = board.captureStones(currentPlayer.getToken(), rowToPlace, colToPlace);
			// add captured stones to the player
			currentPlayer.addStones(capturedStones);

			// Display the board status
			Board.printGameBoard(board, rowToPlace, colToPlace);

			// Display two players captured stones
			System.out.println(Color.RED.getCode() + player1.getName() + Color.RESET.getCode() + " captured tokens: "
					+ player1.getStonesCaptured());
			System.out.println(Color.YELLOW.getCode() + player2.getName() + Color.RESET.getCode() + " captured tokens: "
					+ player2.getStonesCaptured());

			System.out.println(); // pretty console

			// Check if the board has five in line
			hasFiveInLine = board.checkFiveInLine(currentPlayer.getToken(), rowToPlace, colToPlace);

		} while (player1.getStonesCaptured() < stonesToWin && player2.getStonesCaptured() < stonesToWin
				&& board.hasEmptySquares() && !hasFiveInLine);

	}

	/**
	 * Prompts the player to enter their name and validates the input.
	 *
	 * @param playerNum the number of the player.
	 * @return the valid name entered by the player.
	 * @throws IllegalArgumentException if the player name is empty or exceeds 20
	 *                                  characters.
	 */
	public static String askPlayerName(int playerNum) {
		String playerName;

		do {
			playerName = ReadKBData.enterString("Player " + playerNum + ", enter your name:");
			if ((playerName == null || playerName.trim().isEmpty()) || (playerName.length() > 20)) {
				System.out.println("Invalid name. Name cannot be empty or exceeds 20 characters.");
			}
		} while ((playerName == null || playerName.trim().isEmpty()) || (playerName.length() > 20));

		return playerName;
	}

}
