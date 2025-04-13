package util;

import view.View;
import java.util.Scanner;
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
	static public String promptUntilValid(Scanner sc,String prompt,Predicate<String> validator)
	{
	    String input;
	    do {
	        input=getLineInput(sc,prompt);
	    } while (!validator.test(input));
	    return input;
	}
}
