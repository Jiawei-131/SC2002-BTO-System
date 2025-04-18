package controllers;

import entities.User;
import view.View;
import data.PasswordHasher;
import data.UserDatabase;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
public class AuthenticationController {
	
//In charge of validating user inputs when logging in
//    private User currentUser;
    private Map<String,User>users;
    private static final String defaultPassword="password";
    private static final String adminPassword="adminpassword";
    public AuthenticationController() {
    	
    }
    
    public static boolean passwordCheck(String password,User user)
    {
    	if(!PasswordHasher.verifyPassword(password, user.getPassword()))
		{
    		System.out.println("Wrong password, Try again!");
    		return false;
		}
    	return true;
    }
    
    public static boolean nricExists(String nric,Map<String,String>users) {
    	if(users.containsKey(nric))
    	{	
    		System.out.println("Users already exits");
    		return false;
    	}
    	
    	return true;
    }
    public static boolean ageCheck(int age)
    {
    	if(age <18 || age>80)
    	{
    		System.out.println("Invalid age");
    		return false;
    	}
    	return true;
    }
    
    public static boolean validName(String name)
    {
    	if(!name.matches("^[A-Za-z]{4,}$"))
    	{    	
    		System.out.println("Please enter a name that is more than 3 characters with only alphabets");
    		return false;
    	}

    	return true;
    }
    
    public static boolean isDefaultPassword(String password)
    {
    	return PasswordHasher.verifyPassword(password,PasswordHasher.hashPassword(defaultPassword));
//    	return defaultPassword.equals(password.toLowerCase());
    }
    
    public static boolean isValidPassword(String password,User user) {
    	if(password.length()<8 || PasswordHasher.verifyPassword(password,user.getPassword())||isDefaultPassword(password)) {
    		View.validPassword();
    		return false;
    	}
    	return true;
    }
    public static boolean isValidPassword(String password) {
    	if(password.length()<8 ||isDefaultPassword(password)) {
    		View.validPassword();
    	return false;
    	}
    	return true;
    }
    
    public static boolean isAdmin(String password)
    {
    	if (PasswordHasher.verifyPassword(password, PasswordHasher.hashPassword(adminPassword))){
    		return true;
    	}
    	return false;
    }
    
    
    public static User resetPassword(User currentUser,UserDatabase db,String password) {
        return(currentUser.changePassword(PasswordHasher.hashPassword(password), db));
    }
    
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
