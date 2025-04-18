package util;

import view.View;
import java.util.Scanner;
import java.util.function.Function;
import java.util.function.Predicate;


public interface GetInput {
	int maxAttempts=3;
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

	static public String getLineInput(Scanner sc, String prompt) {
	    View.prompt(prompt);
	    return sc.nextLine();
	}
	
	// Generic input loop method
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
