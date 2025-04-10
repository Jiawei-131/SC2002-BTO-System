package controllers;

import java.util.Map;
import java.util.Objects;
import data.PasswordHasher;
import entities.User;
import view.View;
import java.util.Scanner;
import data.UserDatabase;

public class LoginController {

	public static User loginProcess(int choice,Scanner sc,Map<String,String>users,PasswordHasher passwordHasher,UserDatabase db,AuthenticationController ac)
	{
		User currentUser = null;
		View.prompt("NRIC");
        String nric = sc.nextLine();
        while(!users.containsKey(nric)||!AuthenticationController.checkNRIC(nric)) {

        	if(!AuthenticationController.checkNRIC(nric))
        	{
        		View.promptRetry("Invalid NRIC");
        	}
        	else if(!users.containsKey(nric))
        	{
        	View.promptRetry("NRIC not found");
        	}
            choice = sc.nextInt();
            switch(choice) {
            case 1:View.prompt("NRIC");
	                sc.nextLine();
	                nric = sc.nextLine();
            break;
            case 2:View.exit();
	                System.exit(0);
            break;
            default:View.invalidChoice();
            }

        }
        
        View.prompt("Password");
		String password = sc.nextLine();
		String storedHash = users.get(nric); 
        String hashedPassword = passwordHasher.hashPassword(password);  //Not used rn
        while (!Objects.equals(users.get(nric), password)) {
        	View.promptRetry("Incorrect password");
            choice = sc.nextInt();
            switch(choice)
            {
            case 1:View.prompt("Password");
                sc.nextLine();
                password = sc.nextLine();
                hashedPassword = passwordHasher.hashPassword(password); //Not used rn
            break;
            case 2:View.exit();
            	   System.exit(0);
            break;
            default:View.invalidChoice();
            }
        }
        
        
        currentUser= db.getUserById(nric,ac);
        currentUser.login();
        if(AuthenticationController.isDefaultPassword(password)) {
                System.out.println("Users need to update their password when logging in for the first time");
                currentUser=AuthenticationController.resetPassword(currentUser, db, nric, password, sc);
        	}

        return currentUser;
	}
}
