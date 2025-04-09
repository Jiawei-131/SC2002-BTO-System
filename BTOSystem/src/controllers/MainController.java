package controllers;

import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import view.View;
import data.PasswordHasher;
import data.UserDatabase;
import entities.User;

public class MainController {
	
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
        	View.prompt("NRIC");
            String nric = sc.nextLine();
            
            while(!users.containsKey(nric)) {
            	View.promptRetry("NRIC not found");
                choice = sc.nextInt();
                if (choice == 2){
                	View.exit();
                    System.exit(0);
                }
                View.prompt("NRIC");
                sc.nextLine();
                nric = sc.nextLine();
            }
            
            View.prompt("Password");
    		String password = sc.nextLine();
    		String storedHash = users.get(nric); 
            String hashedPassword = passwordHasher.hashPassword(password);  //Not used rn
            while (!Objects.equals(users.get(nric), password)) {
            	View.promptRetry("Incorrect password");
                choice = sc.nextInt();
                if (choice == 2){
                	View.exit();
                    System.exit(0);
                }
                View.prompt("Password");
                sc.nextLine();
                password = sc.nextLine();
                hashedPassword = passwordHasher.hashPassword(password); //Not used rn
            }
            currentUser= db.getUserById(nric,ac);
            currentUser.login();
            if(AuthenticationController.isDefaultPassword(password)) {
                System.out.println("Users need to update their password when logging in for the first time");
                View.prompt("new password");
                do {
                password = sc.nextLine();
                }while(!AuthenticationController.isValidPassword(password,currentUser));
                currentUser=currentUser.changePassword(password, db);
            }
            choice=3;
            break;
        case 2:
        	View.register(); // Do we need to have a register?
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
