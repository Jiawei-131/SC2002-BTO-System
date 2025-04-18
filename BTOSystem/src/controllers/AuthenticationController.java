package controllers;

import entities.User;
import view.View;
import data.PasswordHasher;
import data.UserDatabase;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

/**
 * Handles user authentication and input validation for the BTO system.
 * Includes NRIC checks, password verification, and input sanitization.
 */
public class AuthenticationController {
	
//In charge of validating user inputs when logging in
//    private User currentUser;
    private Map<String,User>users;
    private static final String defaultPassword="password";
    private static final String adminPassword="adminpassword";

    /**
     * Default constructor for AuthenticationController.
     */
    public AuthenticationController() {
    	
    }
    
    
    /**
     * Verifies that the input password matches the user's stored hashed password.
     *
     * @param password The plain-text password entered by the user.
     * @param user The user whose password is being verified.
     * @return true if password matches, false otherwise.
     */
    public static boolean passwordCheck(String password,User user)
    {
    	if(!PasswordHasher.verifyPassword(password, user.getPassword()))
		{
    		System.out.println("Wrong password, Try again!");
    		return false;
		}
    	return true;
    }
    
    /**
     * Checks if an NRIC is already present in the users map.
     *
     * @param nric The NRIC to check.
     * @param users Map of NRIC to hashed password.
     * @return false if NRIC exists, true otherwise.
     */
    public static boolean nricExists(String nric,Map<String,String>users) {
    	if(users.containsKey(nric))
    	{	
    		System.out.println("Users already exits");
    		return false;
    	}
    	
    	return true;
    }
    
    /**
     * Validates that the age is between 18 and 80 (inclusive).
     *
     * @param age Age input.
     * @return true if valid, false otherwise.
     */
    public static boolean ageCheck(int age)
    {
    	if(age <18 || age>80)
    	{
    		System.out.println("Invalid age");
    		return false;
    	}
    	return true;
    }
    
    /**
     * Validates that the name contains only alphabets and is at least 4 characters long.
     *
     * @param name Name input.
     * @return true if valid, false otherwise.
     */
    public static boolean validName(String name)
    {
    	if(!name.matches("^[A-Za-z]{4,}$"))
    	{    	
    		System.out.println("Please enter a name that is more than 3 characters with only alphabets");
    		return false;
    	}

    	return true;
    }
    
    /**
     * Checks if a given password is the system's default password.
     *
     * @param password Input password.
     * @return true if it is the default password, false otherwise.
     */
    public static boolean isDefaultPassword(String password)
    {
    	return PasswordHasher.verifyPassword(password,PasswordHasher.hashPassword(defaultPassword));
//    	return defaultPassword.equals(password.toLowerCase());
    }
    
    
    /**
     * Validates a password during user update. Ensures length, non-default, and difference from old password.
     *
     * @param password The new password input.
     * @param user The user whose password is being updated.
     * @return true if valid, false otherwise.
     */
    public static boolean isValidPassword(String password,User user) {
    	if(password.length()<8 || PasswordHasher.verifyPassword(password,user.getPassword())||isDefaultPassword(password)) {
    		View.validPassword();
    		return false;
    	}
    	return true;
    }
    
    /**
     * Validates a password during initial registration. Ensures length and non-default.
     *
     * @param password Password input.
     * @return true if valid, false otherwise.
     */
    public static boolean isValidPassword(String password) {
    	if(password.length()<8 ||isDefaultPassword(password)) {
    		View.validPassword();
    	return false;
    	}
    	return true;
    }
    
    /**
     * Checks if a given password matches the admin password.
     *
     * @param password The admin password input.
     * @return true if matches the hardcoded admin password, false otherwise.
     */
    public static boolean isAdmin(String password)
    {
    	if (PasswordHasher.verifyPassword(password, PasswordHasher.hashPassword(adminPassword))){
    		return true;
    	}
    	return false;
    }
    
    /**
     * Resets the user's password by updating it in the database.
     *
     * @param currentUser The user who is changing their password.
     * @param db The database instance.
     * @param password The new password (plain-text).
     * @return The updated user object.
     */
    public static User resetPassword(User currentUser,UserDatabase db,String password) {
        return(currentUser.changePassword(PasswordHasher.hashPassword(password), db));
    }
    
    /**
     * Validates that an NRIC follows the format: Starts with 'S' or 'T', followed by 7 digits, and ends with an uppercase letter.
     *
     * @param nric NRIC input.
     * @return true if valid, false otherwise.
     */
    public static boolean checkNRIC(String nric)
    {
    	if(!nric.matches("^[ST]\\d{7}[A-Z]$"))
    	{
        	View.validNRIC();
    		return false;
    	}
    	return true;
    }

}
