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
import entities.User;

import view.View;

public class BTOSystem {
    public static void main(String[] args)
    {
    	User currentUser = null;
    	
    	int choice;
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
        do {
        	View.loginView();
            choice = sc.nextInt();
            sc.nextLine();
            switch(choice) {
            case 1:
            	View.prompt("NRIC");
                String nric = sc.nextLine();
                
                while(!users.containsKey(nric)) {
                	View.promptRetry("NRIC not found");
                    choice = sc.nextInt();
                    if (choice == 2){
                    	View.exit();
                        System.exit(0);
                    }
                    View.prompt("NRIC");
                    sc.nextLine();
                    nric = sc.nextLine();
                }
                
                View.prompt("Password");
        		String password = sc.nextLine();
        		String storedHash = users.get(nric); 
                String hashedPassword = passwordHasher.hashPassword(password);  //Not used rn
                while (!Objects.equals(users.get(nric), password)) {
                	View.promptRetry("Incorrect password");
                    choice = sc.nextInt();
                    if (choice == 2){
                    	View.exit();
                        System.exit(0);
                    }
                    View.prompt("Password");
                    sc.nextLine();
                    password = sc.nextLine();
                    hashedPassword = passwordHasher.hashPassword(password); //Not used rn
                }
                currentUser= db.getUserById(nric,ac);
                currentUser.login();
                if(AuthenticationController.isDefaultPassword(password)) {
                    System.out.println("Users need to update their password when logging in for the first time");
                    View.prompt("new password");
                    do {
                    password = sc.nextLine();
                    }while(!AuthenticationController.isValidPassword(password,currentUser));
                    currentUser=currentUser.changePassword(password, db);
                }
                choice=3;
                break;
            case 2:
            	View.register(); // Do we need to have a register?
            	//sc.nextLine();
            	break;
            case 3:
            	View.exit();
            	System.exit(0);
            	break;
            default:
            	View.invalidChoice();
            	
            }
        } while (choice != 3 || currentUser == null);
        do {
        currentUser.displayMenu();
        choice=sc.nextInt();
        sc.nextLine();
        currentUser=cc.choice(choice, currentUser, sc,db);
        }
        while(currentUser != null);
        }
        
        
    }
    
}
    
        
        
        
        
        
       
    
     	
        	
        
        
