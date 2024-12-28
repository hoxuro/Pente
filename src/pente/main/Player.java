package pente.main;

/**
 * Represents a player in the Pente game. The Player class encapsulates the
 * basic details of a player, such as their name, and provides methods to access
 * and update this information.
 */
public class Player {

	/**
	 * The name of the player.
	 */
	private final String name;

	/**
	 * The token the player is playing with
	 */
	private final Token token;

	/**
	 * The number of stones captured by the player.
	 */
	private int stonesCaptured;

	/**
	 * Constructs a new Player with the specified name.
	 *
	 * @param name the name of the player
	 */
	public Player(String name, Token token) {
		if (name == null || name.trim().isEmpty()) {
			throw new IllegalArgumentException("The name of the player can`t be empty or null");
		}
		if (name.length() > 20) {
			throw new IllegalArgumentException("The name of the player cannot be longer than 20 characters");
		}
		if (token == null) {
			throw new IllegalArgumentException("A player must have a valid token");
		}
		this.name = name;
		this.token = token;
		this.stonesCaptured = 0;
	}

	/**
	 * Retrieves the name of the player.
	 *
	 * @return the name of the player
	 */
	public String getName() {
		return name;
	}

	/**
	 * Retrieves the token of the player.
	 *
	 * @return the token of the player (either CIRCLE or CROSS)
	 */
	public Token getToken() {
		return token;
	}

	/**
	 * Retrieves all the stones captured by the player.
	 * 
	 * @return the number of stones captured
	 */
	public int getStonesCaptured() {
		return this.stonesCaptured;
	}

	/**
	 * Adds a specified number of stones to the player's total captured stones
	 * count.
	 *
	 * @param stones the number of stones to add to the captured stones count.
	 * @throws IllegalArgumentException if the number of stones is negative.
	 */
	public void addStones(int stones) {
		if (stones < 0) {
			throw new IllegalArgumentException("The number of stones cannot be negative");
		}

		this.stonesCaptured += stones;
	}

	/**
	 * Returns a string representation of the player.
	 *
	 * @return a string with the player's name, token, and stones captured
	 */
	@Override
	public String toString() {
		return "Player{name='" + name + "', token=" + token + ", stonesCaptured=" + stonesCaptured + "}";
	}

}
