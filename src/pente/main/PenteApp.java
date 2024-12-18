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
	private int stonesCaptured1;
	private int stonesCaptured2;

	public void run() {
		//
		//
		// *** Game components initialization ***

		stonesCaptured1 = 0;
		stonesCaptured2 = 0;

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

		// Setting current player
		currentPlayer = player2;

		//
		//
		// *** Starting a new game ***

		do {
			// new turn
			currentPlayer = (currentPlayer == player1) ? player2 : player1;
			System.out.println((currentPlayer == player1 ? COLOR_RED + player1 : COLOR_BLUE + player2)
					+ " it's your turn" + COLOR_RESET);

			// asking position to place the player stone
			Boolean isPlaced;
			do {

				// asking row position
				int rowToPlace;
				do {
					rowToPlace = ReadKBData.enterInt("Row to place your stone:");
					if (rowToPlace < 0 || rowToPlace > boardSize) {
						System.err.println("Row must be on range! (0-" + boardSize + ")");
					}
				} while (rowToPlace < 0 || rowToPlace > boardSize);

				// asking column position
				int colToPlace;
				do {
					colToPlace = ReadKBData.enterInt("Row to place your stone");
					if (colToPlace < 0 || colToPlace > boardSize) {
						System.err.println("Row must be on range! (0-" + boardSize + ")");
					}
				} while (colToPlace < 0 || colToPlace > boardSize);

				// Setting the token
				isPlaced = board.setToken(currentPlayer.getToken(), rowToPlace, colToPlace);

			} while (!isPlaced);

			// Now we need to check if the current player can catch any stone
			

			// code for catch token

			// Display the board status

			// Display two players captured stones

		} while (stonesCaptured1 < stonesToWin && stonesCaptured2 < stonesToWin
				&& board.hasEmptySquares() /* and check five in line */ );

	}

	// ANSI color codes
	final String COLOR_RED = "\u001B[31m";
	final String COLOR_BLUE = "\u001B[34m";
	final String COLOR_RESET = "\u001B[0m";

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
