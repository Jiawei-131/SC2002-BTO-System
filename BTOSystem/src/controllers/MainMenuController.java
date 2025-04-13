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
import util.GetInput;

public class MainMenuController implements GetInput {
	
public static void mainMenu(Scanner sc,Map<String,String>users,PasswordHasher passwordHasher,UserDatabase db,AuthenticationController ac,ChoiceController cc)
{
	
	User currentUser = null;
	int choice;
	do {
		users=db.readUsers();
    	View.loginView();
        choice = GetInput.getIntInput(sc,"your Choice");
        switch(choice) {
        case 1->{
        	currentUser=LoginController.loginProcess(choice, sc, users, passwordHasher, db, ac);
            choice=3;
        }
        case 2->register(db,sc,users);
        case 3->{
        	View.exit();
        	System.exit(0);
        }
        default->View.invalidChoice();
        	
        }
    } while (choice != 3 || currentUser == null);
	
    do {
    currentUser=cc.choice(choice, currentUser, sc,db);
    }
    while(currentUser != null);
    }
	


private static void register(UserDatabase db, Scanner sc, Map<String, String> users) {
    String name = GetInput.inputLoop("your Name", sc, s -> s, AuthenticationController::validName);
    String nric = GetInput.inputLoop("your NRIC", sc, s -> s, s ->
        AuthenticationController.checkNRIC(s) && AuthenticationController.nricExists(s, users)
    );
    int age = GetInput.inputLoop("your Age", sc, Integer::parseInt, AuthenticationController::ageCheck);
    
    int maritalStatusChoice = GetInput.inputLoop("""
            Marital Status:
            1. Single
            2. Married
            """, sc, Integer::parseInt, i -> i == 1 || i == 2);
    String maritalStatus = (maritalStatusChoice == 1) ? "Single" : "Married";
    
    String password = GetInput.inputLoop("your Password", sc, s -> s, AuthenticationController::isValidPassword);

    db.writeUser(nric, password);
    db.writeUser(name, nric, age, maritalStatus, "A", true);

    System.out.println("Registration Successful");
}



}
