# Pente Game

A console-based implementation of the classic board game Pente, written in Java. This game is designed for two players and features customizable board size and winning conditions.

## Description

In Pente, players take turns placing stones on a grid, aiming to either align five stones in a row or capture a set number of opponent's stones. The game provides:
- Customizable board size (10x10 to 19x19).
- Adjustable number of stones needed to win (5 to 10).
- Real-time updates of captured stones and game status.

## Features

- Interactive console interface.
- Input validation for player moves and configurations.
- Dynamic game board display.
- Automatic detection of wins (five-in-a-row or capturing the required stones).
- Replay option after each game.

## Requirements

- Java 11 or higher.

## How to Play

1. Run the program:
   ```bash
   java -cp out pente.main.PenteApp
   
2. Follow the prompts to:
	Set the board size and stones-to-win threshold.
	Enter player names.
	Take turns placing stones by selecting row and column numbers.
	
3. The game ends when one player aligns five stones, captures the required number of stones, or the board is full.

Enjoy playing my Java Pente!

## License
This project is licensed under the MIT License. See the LICENSE file for details.

## Demo
![image](https://github.com/user-attachments/assets/731bfdf3-1e8e-43e2-811d-27f51cd5ec2e)

![image](https://github.com/user-attachments/assets/60e00948-52f6-4cde-b15f-249f9c4511ff)

<p style="width:100%">Capture opponent's Token</p>

![image](https://github.com/user-attachments/assets/ccd3c2bb-0781-4d51-85d8-215f9d847695)

<p style="width:100%">Five in a row</p>

![image](https://github.com/user-attachments/assets/fbfab881-7b30-4652-bdf6-a26ce3d45873)

## Classes
```bash
Pente
├── src
 ├── pente.main
 │ ├── Board.java - Manages the game board
 │ ├── Color.java - Defines piece or player colors
 │ ├── PenteApp.java - Main application class
 │ ├── Player.java - Represents a player in the game
 │ └── Token.java - Defines game tokens (pieces)
 └── pente.utils
```

## Featured Methods
Check if there are 5 stones aligned on the secondary diagonal
```java
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
```

Capture opponent's stones if possible on the secondary diagonal

```java
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
```
