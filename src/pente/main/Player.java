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
	private String name;

	/**
	 * The token the player is playing with
	 */
	private Token token;

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
	 * Updates the name of the player.
	 *
	 * @param name the new name of the player
	 */
	public void setName(String name) {
		if (name == null || name.trim().isEmpty()) {
			throw new IllegalArgumentException("The name of the player can`t be empty or null");
		}
		if (name.length() > 20) {
			throw new IllegalArgumentException("The name of the player cannot be longer than 20 characters");
		}
		this.name = name;
	}

	/**
	 * Retrieves the token of the player.
	 *
	 * @return the token of the player (either CIRCLE or CROSS)
	 */
	public Token getToken() {
		return token;
	}

}
