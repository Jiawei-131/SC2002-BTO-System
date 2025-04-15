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
import util.*;

public class MainMenuController implements GetInput,FilePath {
	
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
    currentUser=cc.choice(choice, currentUser, sc,db);
    }
    while(currentUser != null);
    }
	





}
