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
    
    public AuthenticationController() {
    	
    }
    
    public boolean logIn(User user,String password)
    {
        if(user.getPassword().equals(password)){
        	//this.currentUser = user;
        	System.out.println("Login successful");
//        	user.login();
            return true;
        }
        return false;
    }
    public void logOut(User user)
    {
//    	currentUser.logout();
    }
    
    public static boolean isDefaultPassword(String password)
    {
    	return "password".equals(password);
    }
    
    private static boolean isValidPassword(String password,User user) {
    	if(password.length()<8 || password.equals(user.getPassword())) {
    		System.out.println("Please enter a valid password");
    	return false;
    	}
    	return true;
    }
    
    public static User resetPassword(User currentUser,UserDatabase db,String nric,String password,Scanner sc) {
        View.prompt("new password");
        do {
        password = sc.nextLine();
        }while(!AuthenticationController.isValidPassword(password,currentUser));
        return(currentUser.changePassword(password, db));
    }

}
