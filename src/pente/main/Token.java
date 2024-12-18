package pente.main;

/**
 * Represents the different types of tokens used in the Pente game.
 */
public enum Token {
	/**
	 * Represents an empty cell on the board.
	 */
	EMPTY(0),

	/**
	 * Represents a circle token on the board.
	 */
	CIRCLE(1),

	/**
	 * Represents a cross token on the board.
	 */
	CROSS(2);

	private final int value;

	/**
	 * Constructor for the {@link Token} enum. Initializes the token with a
	 * specified value. The value must be between 0 and 2, inclusive. If the value
	 * is outside this range, an {@link IllegalArgumentException} will be thrown.
	 *
	 * @param value the integer value representing the token (0 for empty, 1 for
	 *              circle, 2 for cross)
	 * @throws IllegalArgumentException if the provided value is not between 0 and 2
	 */
	Token(int value) {
		if (value < 0 || value > 2) {
			throw new IllegalArgumentException("Invalid token value: " + value);
		}
		this.value = value;
	}

	/**
	 * Returns the integer value associated with the token.
	 * 
	 * @return the integer value of the token
	 */
	public int getValue() {
		return this.value;
	}

	/**
	 * Converts an integer value to its corresponding {@link Token} enum value.
	 * 
	 * @param i the integer value representing a token (0 for empty, 1 for circle, 2
	 *          for cross)
	 * @return the corresponding {@link Token} value
	 * @throws IllegalArgumentException if the provided integer is not 0, 1, or 2
	 */
	public static Token fromInt(int i) {
		switch (i) {
		case 0:
			return EMPTY;
		case 1:
			return CIRCLE;
		case 2:
			return CROSS;
		default:
			throw new IllegalArgumentException("Unexpected value: " + i);
		}
	}
}
