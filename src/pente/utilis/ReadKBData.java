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
     * Method that asks the user to enter an integer data type and also
     * validates it.
     *
     * @param message the message that we want to display on the screen before
     * requesting the number.
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
       
        input.close();

        return input.nextInt();
    }

    /**
     * Method that asks the user to enter an String data type and also validates
     * it.
     *
     * @param message the message that we want to display on the screen before
     * requesting the String.
     * @return a valid String.
     */
    public static String enterString(String message) {
        Scanner input = new Scanner(System.in);

        //remember that you can change the delimiter
        if (!Pattern.matches("^\\s*$", message)) {
            System.out.println(message);
        }

        if (!input.hasNext()) {
            do {
                System.err.println("Please, enter the data correctly");
                input.nextLine();

            } while (!input.hasNext());
        }
        
        input.close();

        return input.next();
    }

    /**
     * Method that asks the user to enter a String data type and also validates
     * it.
     *
     * @param message the message we want to display on the screen requesting
     * the String.
     * @return a valid string.
     */
    public static String enterStringNextLine(String message) {
        Scanner input = new Scanner(System.in);

        //remember that you can change the delimiter
        if (!Pattern.matches("^\\s*$", message)) {
            System.out.println(message);
        }
        if (!input.hasNextLine()) {
            do {
                System.err.println("Please, enter the data correctly");
                input.nextLine();

            } while (!input.hasNextLine());
        }
        
        input.close();

        return input.nextLine();
    }

    /**
     * Method that asks the user to enter a double data type and also validates
     * it.
     *
     * @param message the message that we want to display on the screen before
     * requesting the String.
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
        
        input.close();

        return input.nextDouble();
    }

    /**
     * Method that asks the user to enter a Boolean data type and also validates
     * it.
     *
     * @param message the message we want to display on the screen requesting
     * the String.
     * @return a valid boolean.
     */
    public static boolean enterBoolean(String message) {
        Scanner input = new Scanner(System.in);

        //remember that you can change the delimiter
        System.out.println(message);

        if (!input.hasNextBoolean()) {
            do {
                System.err.println("Please, enter the data correctly");
                input.nextLine();
                System.out.println(message);

            } while (!input.hasNextBoolean());
        }
        
        input.close();

        return input.nextBoolean();
    }

}
