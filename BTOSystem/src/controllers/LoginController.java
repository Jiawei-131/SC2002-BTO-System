package controllers;

import java.util.Map;
import java.util.Objects;
import data.PasswordHasher;
import entities.User;
import util.GetInput;
import util.PasswordReset;
import view.View;
import java.util.Scanner;
import data.UserDatabase;

public class LoginController implements GetInput,PasswordReset{

	public static User loginProcess(int choice,Scanner sc,Map<String,String>users,PasswordHasher passwordHasher,UserDatabase db,AuthenticationController ac)
	{
		User currentUser = null;
        String nric = GetInput.getLineInput(sc,"your NRIC");
        while(!users.containsKey(nric)||!AuthenticationController.checkNRIC(nric)) {

        	if(!AuthenticationController.checkNRIC(nric))
        	{
        		View.promptRetry("Invalid NRIC");
        	}
        	else if(!users.containsKey(nric))
        	{
        	View.promptRetry("NRIC not found");
        	}
            choice = GetInput.getIntInput(sc, "your choice");
            switch(choice) {
            case 1->nric = GetInput.getLineInput(sc, "your NRIC");
            case 2->{
            	View.exit();
            	System.exit(0);
            }
            default->View.invalidChoice();
            }

        }
		String password = GetInput.getLineInput(sc, "your Password");
		String storedHash = users.get(nric); 
        String hashedPassword = passwordHasher.hashPassword(password);  //Not used rn
        while (!Objects.equals(users.get(nric), password)) {
        	View.promptRetry("Incorrect password");
            choice = GetInput.getIntInput(sc, "your choice");
            switch(choice)
            {
            case 1->{
                password = GetInput.getLineInput(sc, "your Password");
                hashedPassword = passwordHasher.hashPassword(password); //Not used rn
            }
            case 2->{
            	View.exit();
         	   System.exit(0);
            }
            default->View.invalidChoice();
            }
        }
        currentUser= db.getUserById(nric,ac);
        currentUser.login();
        if(AuthenticationController.isDefaultPassword(password)) {
                System.out.println("Users need to update their password when logging in for the first time");
                currentUser=PasswordReset.resetPassword(sc, currentUser, db);
        	}

        return currentUser;
	}
	
}
