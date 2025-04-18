package BTOSystem;
import data.UserDatabase;

import java.io.*;
import java.util.*;
import data.PasswordHasher;
import controllers.*;
import util.*;
//Flow of program MainMenuController-> AccountController->User specific menu in handler->user action
public class BTOSystem implements FilePath {
    public static void main(String[] args)
    {
    	
    	PasswordHasher passwordHasher = new PasswordHasher(); 
    	UserDatabase db= new UserDatabase();
    	AuthenticationController ac = new AuthenticationController();
    	Scanner sc = new Scanner(System.in);

    	 Map<String,String>users;
    	//TODO Implement password hashing when done?
        while(true) {
        	users=db.readUsers();
        	MainMenuController.mainMenu(sc,users,passwordHasher,db,ac);
        }
    }
        

    
}
    
        
        
        
        
        
       
    
     	
        	
        
        
