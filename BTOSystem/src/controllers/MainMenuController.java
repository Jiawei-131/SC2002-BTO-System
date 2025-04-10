package controllers;

import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import view.View;
import data.PasswordHasher;
import data.UserDatabase;
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
        	View.register(); //TODO Do we need to have a register?
        	//sc.nextLine();
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
	
}
