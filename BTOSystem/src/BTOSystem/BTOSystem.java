package BTOSystem;
import data.UserDatabase;

import java.io.*;
import java.util.*;
import data.PasswordHasher;
import controllers.AuthenticationController;
import controllers.PermissionController;
import controllers.ChoiceController;
import controllers.MainMenuController;
import entities.User;
import view.View;

public class BTOSystem {
    public static void main(String[] args)
    {
    	final String File_Path="BTOSystem/src/data/";
    	
    	PasswordHasher passwordHasher = new PasswordHasher(); 
    	UserDatabase db= new UserDatabase(File_Path);
    	AuthenticationController ac = new AuthenticationController();
    	PermissionController pc= new PermissionController();
    	ChoiceController cc=new ChoiceController();
    	Scanner sc = new Scanner(System.in);

    	 Map<String,String>users;
    	//TODO Implement password hashing when done?
        while(true) {
        	users=db.readUsers();
        	MainMenuController.mainMenu(sc,users,passwordHasher,db,ac,cc);
        }
    }
        

    
}
    
        
        
        
        
        
       
    
     	
        	
        
        
