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
