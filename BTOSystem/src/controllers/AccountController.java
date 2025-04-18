package controllers;

import java.util.Map;
import java.util.Objects;
import data.PasswordHasher;
import entities.User;
import util.*;
import view.View;
import java.util.Scanner;
import data.UserDatabase;

/**
 * Handles account-related operations such as login and registration.
 */
public class AccountController implements GetInput,PasswordReset,FilePath{
	//Handles login process
	
	/**
	 * Handles the login process for a user. Prompts for NRIC and password,
	 * verifies them against stored credentials, and returns the authenticated user.
	 * If the user logs in with a default password, prompts for a password reset.
	 *
	 * @param choice user's menu choice input during retry prompts
	 * @param sc Scanner object for reading user input
	 * @param users Map of NRIC to hashed passwords
	 * @param passwordHasher PasswordHasher utility for verifying passwords
	 * @param db UserDatabase instance for retrieving user data
	 * @param ac AuthenticationController instance for validation logic
	 * @return the logged-in User object
	 */
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
        while (!passwordHasher.verifyPassword( password, storedHash)) {
        	View.promptRetry("Incorrect password");
            choice = GetInput.getIntInput(sc, "your choice");
            switch(choice)
            {
            case 1->{
                password = GetInput.getLineInput(sc, "your Password");
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

	/**
	 * Handles user registration. Prompts for user details, validates inputs,
	 * hashes password, stores login and user information into respective files.
	 * Also handles role-based registration, including admin password checks for Officer/Manager roles.
	 *
	 * @param db UserDatabase instance for writing user data
	 * @param sc Scanner object for reading user input
	 * @param users Map of existing users to validate uniqueness of NRIC
	 * @param passwordHasher PasswordHasher utility for hashing passwords
	 */
	public static void register(UserDatabase db, Scanner sc, Map<String, String> users,PasswordHasher passwordHasher) {
		try {
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
	    int roleChoice = GetInput.inputLoop("""
	            Role:
	            1. Applicant
	            2. Officer
	            3. Manager
	            """, sc, Integer::parseInt, i -> i>0&&i<=3);
	    Database.writeFile(loginFilePath,nric, passwordHasher.hashPassword(password));
	    if(roleChoice!=1)
	    {
	    	String adminpassword = GetInput.inputLoop("the admin Password ", sc, s -> s, s -> AuthenticationController.isValidPassword(s) && AuthenticationController.isAdmin(s));
	    	if(roleChoice==2)Database.writeFile(userDatabaseFilePath,name, nric, String.valueOf(age), maritalStatus, "O","true");
	    	else if(roleChoice==3)Database.writeFile(userDatabaseFilePath,name, nric, String.valueOf(age), maritalStatus, "M","true");
	    }
	    else Database.writeFile(userDatabaseFilePath,name, nric, String.valueOf(age), maritalStatus, "A","true");
	    System.out.println("Registration Successful");
	}catch (RegistrationFailedException e) {
        System.out.println(e.getMessage());
    }
	}

	
}
