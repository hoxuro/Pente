package pente.utilis;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Arrays;
import java.util.Locale;

/**
 * Class that gives us different methods to work with arrays.
 *
 * @author Hoxuro
 * @version 1.0
 * @since JDK 19
 */
public class MyArray {

	/**
	 * Prints in screen your array of integer numbers.
	 *
	 * @param array the array you want to print on screen.
	 */
	public static void display(int[] array) {
		for (int element : array) {
			System.out.print(element + " ");
		}
		System.out.println("");
	}

	/**
	 * Prints in screen in your array of integer numbers upside down.
	 *
	 * @param array the array you want to print upside down on screen
	 */
	public static void displayReverse(int[] array) {
		for (int element = array.length - 1; element >= 0; element--) {
			System.out.print(array[element] + " ");
		}
		System.out.println("");
	}

	/**
	 * Ask the user to fill the array introducing by keyboard the int array
	 * elements.
	 *
	 * @param elements the length of the array.
	 * @return an array whose elements have been entered by the user.
	 */
	public static int[] fill(int elements) {
		int array[] = new int[elements];

		for (int element = 0; element < array.length; element++) {
			array[element] = ReadKBData.enterInt("Enter an integer number: ");
		}

		return array;
	}

	/**
	 * Ask the user to fill the array introducing by keyboard the position you want
	 * to fill, the int value and if you want to continue.
	 *
	 * @param elements the length of the array
	 * @return an array whose elements and positions have been entered by the user.
	 */
	public static int[] fillPosition(int elements) {
		int[] array = new int[elements];
		// the position you want to fill / if you want to stop
		int position = 0, stop;

		do {
			do {
				position = ReadKBData.enterInt(
						"Array with " + array.length + " positions\nEnter the position you want to fill :") - 1;
				if (position < 0 || position >= array.length) {
					System.out.println("ERROR, position our of range!");
				}
			} while (position < 0 || position >= array.length);

			array[position] = ReadKBData.enterInt("Enter the value for the position " + (position + 1) + ":");
			stop = ReadKBData.enterInt("You want to continue? (1 = yes, 0 = no)");
		} while (stop == 1);

		return array;
	}

	/**
	 * Creates a random array whose elements range from 0 to 9.
	 *
	 * @param elements the length of the array.
	 * @return an array whose elements have been generated randomly.
	 */
	public static int[] fillRandom(int elements) {
		int array[] = new int[elements];

		for (int element = 0; element < array.length; element++) {
			array[element] = (int) (Math.random() * 10);
		}
		return array;
	}

	/**
	 * Creates a random array whose elements range from min to max both entered by
	 * the user.
	 *
	 * @param elements the array length.
	 * @param min      the integer from which the random can start to be generated.
	 * @param max      the integer from which the random stops being generated.
	 * @return an array whose elements have been generated randomly from a range
	 *         entered by the user.
	 */
	public static int[] fillRandom(int elements, int min, int max) {
		int array[] = new int[elements];

		for (int element = 0; element < array.length; element++) {
			array[element] = (int) (Math.random() * ((max + 1) - min) + min);
		}
		return array;
	}

	/**
	 * Creates an array whose elements are squares from others of the array.
	 *
	 * @param array the array you want to extract other with only squares of other
	 *              elements.
	 * @return an array whose elements are squares from others of the introduced
	 *         array.
	 */
	public static int[] fillSquare(int[] array) {
		int numSquares = 0;

		// first I calculate the length of the square array
		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array.length; j++) {
				if (i != j) {
					if (array[i] == Math.pow(array[j], 2)) {
						numSquares++;
					}

				}
			}
		}

		int[] arraySquares = new int[numSquares];
		int counter = 0;
		for (int i = 0; i < array.length && counter != numSquares; i++) {
			for (int j = 0; j < array.length; j++) {
				if (i != j) {
					if (array[i] == Math.pow(array[j], 2)) {
						arraySquares[counter] = array[i];
						counter++;
					}

				}
			}
		}

		return arraySquares;

	}

	/**
	 * Creates an array whose elements are cubes from others of the array.
	 *
	 * @param array the array you want to extract other with only cubes of other
	 *              elements.
	 * @return an array whose elements are cubes from others of the introduced
	 *         array.
	 */
	public static int[] fillCubes(int[] array) {
		int numCubes = 0;

		// First I calc the length of the cubes array
		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array.length; j++) {
				if (i != j) {
					if (array[i] == Math.pow(array[j], 3)) {
						numCubes++;
					}

				}
			}
		}

		int[] arrayCubes = new int[numCubes];
		int counter = 0;
		for (int i = 0; i < array.length && counter != numCubes; i++) {
			for (int j = 0; j < array.length; j++) {
				if (i != j) {
					if (array[i] == Math.pow(array[j], 3)) {
						arrayCubes[counter] = array[i];
						counter++;
					}

				}
			}
		}

		return arrayCubes;

	}

	/**
	 * returns the largest number in the array.
	 *
	 * @param array the array you want to extract the largest number.
	 * @return the largest number of the array.
	 */
	public static int returnMax(int[] array) {

		int max = array[0];
		for (int element : array) {
			if (element > max) {
				max = element;
			}
		}

		return max;
	}

	/**
	 * returns the smallest number in the array.
	 *
	 * @param array the array you want to extract the largest number.
	 * @return the largest number of the array.
	 */
	public static int returnMin(int[] array) {

		int min = array[0];
		for (int element : array) {
			if (element < min) {
				min = element;
			}
		}

		return min;
	}

	/**
	 * Calculates the average of the sum of the elements of the array
	 *
	 * @param array the array we want to calculate the average.
	 * @return a double value that represents the average of the elements sum of the
	 *         array with two decimals.
	 */
	public static double calAverage(double[] array) {
		double media = 0;

		for (double elemento : array) {
			media += elemento;
		}

		DecimalFormat df = new DecimalFormat("#.##");
		df.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.ENGLISH));
		df.setRoundingMode(RoundingMode.FLOOR);

		return Double.parseDouble(df.format(media / array.length));
	}

	/**
	 * Check element of the array that repeats itself more often.
	 *
	 * @param array the array we are going to search the element.
	 * @return the element that repeats itself more often.
	 */
	public static double calcTrend(double[] array) {
		double trend = array[0];
		int repeatElem = 0, repeatTrend = 0;

		for (double elemento : array) {
			for (double elemento2 : array) {
				if (elemento == elemento2) {
					repeatElem++;
				}
			}

			if (repeatElem > repeatTrend) {
				trend = elemento;
				repeatTrend = repeatElem;
			}

			repeatElem = 0;
		}

		return trend;
	}

	/**
	 * Sorts the entered array in ascending order.
	 *
	 * @param array the array that we want to sort
	 */
	public static void sortAsc(int[] array) {

		// for each element of the array
		for (int elem = 0; elem < array.length; elem++) {
			// I get the value of the following element
			int nextElem = elem + 1;

			// as long as the next element is less than the length of the array
			while (nextElem < array.length) {
				// if the next element is less than the current element
				if (array[nextElem] < array[elem]) {
					// exchange positions
					int container = array[elem];
					array[elem] = array[nextElem];
					array[nextElem] = container;
				}

				nextElem++;
			}
		}

	}

	/**
	 * Compacts an array. Eliminates the element whose index we introduce by
	 * parameter, compacts the array and returns it.
	 *
	 * @param array the array we want to compact
	 * @param index the index of the element we are going to eliminate
	 * @return the compacted array
	 */
	public static int[] compact(int[] array, int index) {
		// copy all the elements from index+1 to the end of the array into another array
		int[] copy = new int[array.length - (index + 1)];
		System.arraycopy(array, index + 1, copy, 0, array.length - (index + 1));

		// move them one position to the left
		System.arraycopy(copy, 0, array, index, copy.length);

		// the last element need to be empty because it has been compacted and should be
		// 0
		array[array.length - 1] = 0;

		// creation of the compacted array
		int[] compacted = new int[array.length - 1];
		System.arraycopy(array, 0, compacted, 0, compacted.length);

		return compacted;
	}

	/**
	 * Shift all the positions of an entered array 1 to the right.
	 * 
	 * @param array the array we want to shift
	 * @return the shifted array
	 */
	public static int[] shift1El(int[] array) {
		// if empty return
		if (array.length == 0) {
			return array;
		}

		// store the last element
		int lastEl = array[array.length - 1];

		// shift 1 position to the right
		for (int i = array.length - 1; i > 0; i--) {
			array[i] = array[i - 1];
		}

		// put the last element first
		array[0] = lastEl;

		return array;
	}

	/**
	 * Swaps the value of all elements with the first entered value for a second
	 * value.
	 * 
	 * @param array the array where the magic happens
	 * @param val1  the value of the elements we want to swap
	 * @param val2  the new value of the elements
	 * @return the array with the elements swapped
	 */
	public static int[] swapValue(int[] array, int val1, int val2) {
		for (int i = 0; i < array.length; i++) {
			if (array[i] == val1) {
				array[i] = val2;
			}
		}

		return array;
	}

	/**
	 * Extracts all even integers of an array and create a new array with they
	 * 
	 * @param array the original array
	 * @return the array of all even numbers of the original array
	 */
	public static int[] fillEven(int[] array) {
		int even = 0;

		for (int i = 0; i < array.length; i++) {
			if (array[i] % 2 == 0 && array[i] != 0) {
				even++;
			}
		}

		int[] evenArr = new int[even];
		int j = 0;
		for (int i = 0; i < array.length; i++) {
			if (array[i] % 2 == 0 && array[i] != 0) {
				evenArr[j] = array[i];
				j++;
			}
		}

		return evenArr;
	}

	/**
	 * Extracts all odd integers of an array and create a new array with they
	 * 
	 * @param array the original array
	 * @return the array of all odd numbers of the original array
	 */
	public static int[] fillOdd(int[] array) {
		int odd = 0;

		for (int i = 0; i < array.length; i++) {
			if (array[i] % 2 != 0 && array[i] != 0) {
				odd++;
			}
		}

		int[] oddArr = new int[odd];
		int j = 0;
		for (int i = 0; i < array.length; i++) {
			if (array[i] % 2 != 0 && array[i] != 0) {
				oddArr[j] = array[i];
				j++;
			}
		}

		return oddArr;
	}

	/**
	 * Creates a new array by concatenating 2 arrays entered by parameter.
	 * 
	 * @param array1 the first array where concatenation will begin
	 * @param array2 the second array where concatenation will terminate
	 * @return an array whose elements are the concatenation of the 2 introduced by
	 *         parameter
	 */
	public static int[] concatenate(int[] array1, int[] array2) {
		int[] array3 = new int[array1.length + array2.length];

		// fill with arr 1 elements
		for (int i = 0; i < array1.length; i++) {
			array3[i] = array1[i];
		}

		// fill with arr 2 elements
		for (int i = 0; i < array2.length; i++) {
			array3[array1.length + i] = array2[i];
		}

		return array3;
	}

	/**
	 * Rotates an array to the number inside of it that the user wants. Note:
	 * because of the behaviour of the binarySearch algorithm, if 2 or more numbers
	 * are repeated the order may be unexpected
	 * 
	 * @param array        the original array
	 * @param elemToRotate the number of the array we want to start the rotation
	 * @return a rotated array in starting in the number entered by param
	 */
	public static int[] rotate(int[] array, int elemToRotate) {
		int indexToRotate = Arrays.binarySearch(array, elemToRotate);

		// creating an array from index 0 to the element when want to start the rotation
		int[] firstHalf = Arrays.copyOfRange(array, indexToRotate, (array.length - 1));

		int[] secondHalf = Arrays.copyOfRange(array, 0, indexToRotate);

		return MyArray.concatenate(firstHalf, secondHalf);
	}

	/**
	 * Print the index and the value of entered array.
	 * 
	 * @param array the array we want to print
	 * @return an StringBuilder with a
	 */
	public static void printIndexValue(int[] array) {
		StringBuilder finalMessage = new StringBuilder("Index:");

		// creating an array to later associate num an their figures
		int[] figuresArr = new int[array.length];
		for (int i = 0; i < array.length; i++) {
			figuresArr[i] = calcNumFigures(array[i]);
		}

		// generating the index string
		for (int index = 0; index < array.length; index++) {
			// Add the number of spaces according to the number of digits
			finalMessage.append(" ".repeat(figuresArr[index])).append(index);
		}

		finalMessage.append("\nValue: ");

		// generating the value string
		for (int index = 0; index < array.length; index++) {
			// Add the number of spaces according to the number of digits
			finalMessage.append(" ").append(array[index]);
		}

		System.out.println(finalMessage);
	}

	private static int calcNumFigures(int num) {
		int numFigures = 0;
		do {
			numFigures += 1;
			num /= 10;
		} while (num != 0);

		return numFigures;
	}

	/**
	 * Change the value of the index entered by param and shifts the elements to the
	 * right and discards the last number in the array.
	 * 
	 * @param array       the array we want to swap a value
	 * @param changeIndex the index of the value we want to swap
	 * @param the         new number we want to set
	 */
	public static void swapAndShift(int[] array, int changeIndex, int newNum) {
		// store the number to change
		int cont1 = array[changeIndex];
		int cont2 = 0;

		// change desired number
		array[changeIndex] = newNum;

		// starting from the number to change
		for (int index = changeIndex; index < array.length; index++) {
			// if the current index is less than the length
			if (index + 1 < array.length) {
				// store the following and replace the actual with the stored in cont1
				cont2 = array[index + 1];
				array[index + 1] = cont1;
				cont1 = cont2;
			}
		}
	}

	/**
	 * Calculates the sum of all elements in the provided integer array.
	 *
	 * @param array the array containing the integers to sum; must not be null.
	 * @return the sum of all elements in the array.
	 * @throws NullPointerException if the array is null.
	 */
	public static int sumAll(int[] array) {
		int sum = 0;
		for (int num : array) {
			sum += num;
		}
		return sum;
	}
}