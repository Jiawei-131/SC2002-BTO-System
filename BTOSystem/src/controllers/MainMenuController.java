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
import entities.Applicant;
import entities.Manager;
import entities.Officer;
import entities.User;
import handlers.ApplicantActionHandler;
import handlers.ManagerActionHandler;
import handlers.OfficerActionHandler;
import util.*;
//Handles flow from main menu to user specific menus
/**
 * Controller that handles the main flow of the system from the main menu
 * to the user-specific menu and actions based on login and role.
 */
public class MainMenuController implements GetInput {
    /**
     * Entry point of the main menu controller that allows a user to log in or register,
     * and based on their role, redirects them to their respective action handlers.
     *
     * @param sc Scanner object to capture user input
     * @param users A map of NRICs to hashed passwords
     * @param passwordHasher Utility class for password hashing and verification
     * @param db The UserDatabase to retrieve user information
     * @param ac The AuthenticationController for validating credentials
     */
public static void mainMenu(Scanner sc,Map<String,String>users,PasswordHasher passwordHasher,UserDatabase db,AuthenticationController ac)
{
	
	User currentUser = null;
	int choice;
	do {
		users=db.readUsers();
    	View.loginView();
        choice = GetInput.getIntInput(sc,"your Choice");
        switch(choice) {
        case 1->{
        	currentUser=AccountController.loginProcess(choice, sc, users, passwordHasher, db, ac);
            choice=3;
        }
        case 2->AccountController.register(db,sc,users,passwordHasher);
        case 3->{
        	View.exit();
        	System.exit(0);
        }
        default->View.invalidChoice();
        	
        }
    } while (choice != 3 || currentUser == null);
	
    do {
	    currentUser.displayMenu(currentUser.getMenuOptions());
	    choice=GetInput.getIntInput(sc,"your Choice");
		ActionHandler handler = null;
		if (currentUser instanceof Officer) {
			handler=new OfficerActionHandler();
			((Officer) currentUser).getActiveProject();
        } 
		else if (currentUser instanceof Applicant) {
			handler=new ApplicantActionHandler();
        } 
		else if (currentUser instanceof Manager) {
			handler = new ManagerActionHandler();
			((Manager) currentUser).getActiveProject();
        }
		currentUser=handler.handleAction(choice, currentUser, sc,db);
    }
    while(currentUser != null);
    }
	





}
