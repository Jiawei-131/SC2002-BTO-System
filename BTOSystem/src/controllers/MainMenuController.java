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
import entities.Role;
import entities.User;
import handlers.ApplicantActionHandler;
import handlers.ManagerActionHandler;
import handlers.OfficerActionHandler;
import util.*;
//Handles flow from main menu to user specific menus
public class MainMenuController implements GetInput,FilePath {
	
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
        case 2->AccountController.register(db,sc,users);
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
