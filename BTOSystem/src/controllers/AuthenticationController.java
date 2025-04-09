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
    public static boolean isValidPassword(String password,User user) {
    	if(password.length()<8 || password.equals(user.getPassword())) {
    		System.out.println("Please enter a valid password");
    	return false;
    	}
    	return true;
    }
//    public static User checkFirstLogin(String password, Scanner sc, User user,UserDatabase db) {
//        if (Objects.equals(password, "password")){
//            System.out.println("Users need to update their password when logging in for the first time");
//            View.prompt("new password");
//            password = sc.nextLine();
//            while (password.length() < 8) {
//                System.out.println("Password must be at least 8 characters");
//                password = sc.nextLine();
//            }
//            user.changePassword(password,db);
//            System.out.println("Password updated successfully");
//            System.out.println("Please login again with your new password");
//            return(user.logout());
//        }
//        return user;
//    }
}
