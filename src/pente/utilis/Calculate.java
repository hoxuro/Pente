package pente.utilis;

public class Calculate {

	/**
	 * Method that calculates a random number within a range determined by the user.
	 *
	 * @param startNum the number where the random number starts to be generated
	 *                 (included).
	 * @param endNum   the last number where the random number is generated
	 *                 (included).
	 * @return a random number within a user given range.
	 */
	public static int calcRandomInt(int startNum, int endNum) {
		return (int) (Math.random() * ((endNum + 1) - startNum) + startNum);
	}

}
