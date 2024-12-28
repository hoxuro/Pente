package pente.utilis;

import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Class with different methods to facilitate data keyboard entry.
 *
 * @author Hoxuro
 * @version 1.0
 * @since JDK 19
 */
public class ReadKBData {

	/**
	 * Method that asks the user to enter an integer data type and also validates
	 * it.
	 *
	 * @param message the message that we want to display on the screen before
	 *                requesting the number.
	 * @return a valid integer.
	 */
	public static int enterInt(String message) {
		Scanner input = new Scanner(System.in);

		if (!Pattern.matches("^\\s*$", message)) {
			System.out.println(message);
		}

		if (!input.hasNextInt()) {
			do {
				System.err.println("Please, enter the data correctly");
				input.nextLine();

			} while (!input.hasNextInt());
		}

		return input.nextInt();
	}

	/**
	 * Method that asks the user to enter an String data type and also validates it.
	 *
	 * @param message the message that we want to display on the screen before
	 *                requesting the String.
	 * @return a valid String.
	 */
	public static String enterString(String message) {
		Scanner input = new Scanner(System.in);

		// remember that you can change the delimiter
		if (!Pattern.matches("^\\s*$", message)) {
			System.out.println(message);
		}

		if (!input.hasNext()) {
			do {
				System.err.println("Please, enter the data correctly");
				input.nextLine();

			} while (!input.hasNext());
		}

		return input.next();
	}

	/**
	 * Method that asks the user to enter a String data type and also validates it.
	 *
	 * @param message the message we want to display on the screen requesting the
	 *                String.
	 * @return a valid string.
	 */
	public static String enterStringNextLine(String message) {
		Scanner input = new Scanner(System.in);

		// remember that you can change the delimiter
		if (!Pattern.matches("^\\s*$", message)) {
			System.out.println(message);
		}
		if (!input.hasNextLine()) {
			do {
				System.err.println("Please, enter the data correctly");
				input.nextLine();

			} while (!input.hasNextLine());
		}

		return input.nextLine();
	}

	/**
	 * Method that asks the user to enter a double data type and also validates it.
	 *
	 * @param message the message that we want to display on the screen before
	 *                requesting the String.
	 * @return a valid double.
	 */
	public static double enterDouble(String message) {

		Scanner input = new Scanner(System.in);

		if (!Pattern.matches("^\\s*$", message)) {
			System.out.println(message);
		}

		if (!input.hasNextDouble()) {
			do {
				System.out.println("Please enter the data correctly!");
				input.nextLine();

			} while (!input.hasNextDouble());
		}

		return input.nextDouble();
	}

	/**
	 * Method that asks the user to enter a Boolean data type and also validates it.
	 *
	 * @param message the message we want to display on the screen requesting the
	 *                String.
	 * @return a valid boolean.
	 */
	public static boolean enterBoolean(String message) {
		Scanner input = new Scanner(System.in);

		// remember that you can change the delimiter
		System.out.println(message);

		if (!input.hasNextBoolean()) {
			do {
				System.err.println("Please, enter the data correctly");
				input.nextLine();
				System.out.println(message);

			} while (!input.hasNextBoolean());
		}

		return input.nextBoolean();
	}

	/**
	 * Prompts the user to enter a valid integer input within a specified range.
	 * 
	 * @param message The message displayed to the user prompting them for input.
	 * @param min     The minimum allowable input value.
	 * @param max     The maximum allowable input value.
	 * @return A valid integer input within the specified range.
	 */
	public static int askValidInput(String message, int min, int max) {
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
