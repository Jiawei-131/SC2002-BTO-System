package controllers;

import entities.User;
import java.util.Map;
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
}
