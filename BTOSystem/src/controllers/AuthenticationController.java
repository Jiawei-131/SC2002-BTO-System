package controllers;

import entities.User;
import view.View;
import data.UserDatabase;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
public class AuthenticationController {
//    private User currentUser;
    private Map<String,User>users;
    private static final String defaultPassword="password";
    public AuthenticationController() {
    	
    }
    
    public static boolean passwordCheck(String password,User user)
    {
    	if(!Objects.equals(password, user.getPassword()))
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
    	return defaultPassword.equals(password.toLowerCase());
    }
    
    public static boolean isValidPassword(String password,User user) {
    	if(password.length()<8 || password.equals(user.getPassword())||isDefaultPassword(password)) {
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
    
    public static User resetPassword(User currentUser,UserDatabase db,String password) {
        return(currentUser.changePassword(password, db));
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
