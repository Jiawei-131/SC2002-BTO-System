package BTOSystem;
import java.util.Scanner;
import data.UserDatabase;
import java.util.ArrayList;
import java.util.List;
import java.io.*;
import java.util.*;
import data.PasswordHasher;
import controllers.AuthenticationController;
import controllers.PermissionController;
import controllers.ChoiceController;
import controllers.MainController;
import entities.User;

import view.View;

public class BTOSystem {
    public static void main(String[] args)
    {
    	
    	

    	final String File_Path="LoginInfo.txt";
    	
    	PasswordHasher passwordHasher = new PasswordHasher(); 
    	UserDatabase db= new UserDatabase(File_Path);
    	AuthenticationController ac = new AuthenticationController();
    	PermissionController pc= new PermissionController();
    	ChoiceController cc=new ChoiceController();
    	Scanner sc = new Scanner(System.in);

    	
    	//TODO Implement password hashing when done?
        Map<String,String>users=db.readUsers();
        while(true) {
        //login
        MainController.mainMenu(sc,users,passwordHasher,db,ac,cc);
        }
    }
        

    
}
    
        
        
        
        
        
       
    
     	
        	
        
        
