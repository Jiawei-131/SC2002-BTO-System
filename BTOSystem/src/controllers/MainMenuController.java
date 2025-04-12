package controllers;

import java.util.InputMismatchException;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.function.Function;
import java.util.function.Predicate;

import view.View;
import data.PasswordHasher;
import data.UserDatabase;
import entities.Role;
import entities.User;

public class MainMenuController {
	
public static void mainMenu(Scanner sc,Map<String,String>users,PasswordHasher passwordHasher,UserDatabase db,AuthenticationController ac,ChoiceController cc)
{
	
	User currentUser = null;
	int choice;
	do {
		users=db.readUsers();
    	View.loginView();
        choice = sc.nextInt();
        sc.nextLine();
        switch(choice) {
        case 1:
        	currentUser=LoginController.loginProcess(choice, sc, users, passwordHasher, db, ac);
            choice=3;
            break;
        case 2:
        	register(db,sc,users);
        	break;
        case 3:
        	View.exit();
        	System.exit(0);
        	break;
        default:
        	View.invalidChoice();
        	
        }
    } while (choice != 3 || currentUser == null);
	
    do {
    currentUser.displayMenu();
    choice=sc.nextInt();
    sc.nextLine();
    currentUser=cc.choice(choice, currentUser, sc,db);
    }
    while(currentUser != null);
    }
	


private static void register(UserDatabase db, Scanner sc, Map<String, String> users) {
    String name = inputLoop("Name", sc, s -> s, AuthenticationController::validName);
    String nric = inputLoop("NRIC", sc, s -> s, s ->
        AuthenticationController.checkNRIC(s) && AuthenticationController.nricExists(s, users)
    );
    int age = inputLoop("Age", sc, Integer::parseInt, AuthenticationController::ageCheck);
    
    int maritalStatusChoice = inputLoop("""
            Marital Status:
            1. Single
            2. Married
            """, sc, Integer::parseInt, i -> i == 1 || i == 2);
    String maritalStatus = (maritalStatusChoice == 1) ? "Single" : "Married";
    
    String password = inputLoop("Password", sc, s -> s, AuthenticationController::isValidPassword);

    db.writeUser(nric, password);
    db.writeUser(name, nric, age, maritalStatus, "A", true);

    System.out.println("Registration Successful");
}

// Generic input loop method
private static <T> T inputLoop(String prompt, Scanner sc, Function<String, T> parser, Predicate<T> validator) {
    while (true) {
        View.prompt(prompt);
        String line = sc.nextLine();
        try {
            T value = parser.apply(line);
            if (validator.test(value)) {
                return value;
            }
        } catch (Exception e) {
            System.out.println("Invalid format. Please try again.");
        }
    }
}

}
