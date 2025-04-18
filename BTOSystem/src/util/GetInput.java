package util;

import view.View;
import java.util.Scanner;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Provides utility methods for receiving and validating user input from the console.
 */
public interface GetInput {
    /**
     * The maximum number of attempts allowed for input validation.
     */
	int maxAttempts=3;
    /**
     * Prompts the user for an integer input and validates the input format.
     *
     * @param sc The Scanner object to read user input.
     * @param prompt The message to prompt the user.
     * @return The valid integer entered by the user.
     */
	static public int getIntInput(Scanner sc, String prompt) {
	    while (true) {
	        View.prompt(prompt);
	        try {
	            return Integer.parseInt(sc.nextLine());
	        } catch (NumberFormatException e) {
	            System.out.println("Invalid input. Please enter a number.");
	        }
	    }
	}
    /**
     * Prompts the user for a line of text input.
     *
     * @param sc The Scanner object to read user input.
     * @param prompt The message to prompt the user.
     * @return The line of text entered by the user.
     */
	static public String getLineInput(Scanner sc, String prompt) {
	    View.prompt(prompt);
	    return sc.nextLine();
	}
	
	// Generic input loop method
    /**
     * Generic input method that loops until valid input is received or maximum attempts are reached.
     * It parses and validates input based on the provided functions.
     *
     * @param <T> The type of value expected.
     * @param prompt The message to prompt the user.
     * @param sc The Scanner object to read user input.
     * @param parser A function to convert the string input to the desired type.
     * @param validator A predicate to validate the parsed value.
     * @return The valid, parsed, and validated value.
     * @throws RegistrationFailedException If the number of invalid attempts exceeds the maximum allowed.
     */
	public static <T> T inputLoop(String prompt, Scanner sc, Function<String, T> parser, Predicate<T> validator) throws RegistrationFailedException {
	    int attempts = 0;
	    while (attempts < maxAttempts) {
	        String line = GetInput.getLineInput(sc, prompt);
	        try {
	            T value = parser.apply(line);
	            if (validator.test(value)) {
	                return value;
	            } else {
	                System.out.println("Invalid input!");
	            }
	        } catch (Exception e) {
	            System.out.println("Invalid format. Please try again.");
	        }
	        attempts++;
	        System.out.println("Attempt " + attempts + " of " + maxAttempts);
	    }
	    throw new RegistrationFailedException("Too many invalid attempts. Returning to the main menu.");
	}
}
