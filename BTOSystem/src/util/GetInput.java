package util;

import view.View;
import java.util.Scanner;
import java.util.function.Function;
import java.util.function.Predicate;

public interface GetInput {
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
	static public  <T> T inputLoop(String prompt, Scanner sc, Function<String, T> parser, Predicate<T> validator) {
	    while (true) {
	        String line = GetInput.getLineInput(sc, prompt);
	        try {
	            T value = parser.apply(line);
	            if (validator.test(value)) {
	                return value;
	            }
	            else {
	            	System.out.println("Invalid input!");
	            }
	        } catch (Exception e) {
	            System.out.println("Invalid format. Please try again.");
	        }
	    }
	}
}
