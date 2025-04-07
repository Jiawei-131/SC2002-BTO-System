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
import entities.Officer;
import entities.Manager;
import entities.Applicant;
import view.View;

public class BTOSystem {
    public static void main(String[] args)
    {
    	User currentUser = null;
    	View view = new View();
    	
    	int choice;
    	final String File_Path="LoginInfo.txt";
    	
    	PasswordHasher passwordHasher = new PasswordHasher(); 
    	UserDatabase db= new UserDatabase(File_Path);
    	AuthenticationController ac = new AuthenticationController();
    	PermissionController pc= new PermissionController();
    	ChoiceController cc=new ChoiceController();
    	Scanner sc = new Scanner(System.in);

    	
    	//Implement password hashing when done?
        Map<String,String>users=db.readUsers();
        while(true) {
        //login
        do {
        	view.menuView();
            choice = sc.nextInt();
            sc.nextLine();
            switch(choice) {
            case 1:
            	view.prompt("NRIC");
                String nric = sc.nextLine();
                
                while(!users.containsKey(nric)) {
                	view.promptRetry("NRIC not found");
                    choice = sc.nextInt();
                    if (choice == 2){
                    	view.exit();
                        System.exit(0);
                    }
                    view.prompt("NRIC");
                    sc.nextLine();
                    nric = sc.nextLine();
                }
                
                view.prompt("Password");
        		String password = sc.nextLine();
        		String storedHash = users.get(nric); 
                String hashedPassword = passwordHasher.hashPassword(password);  //Not used rn
                while (!Objects.equals(users.get(nric), password)) {
                	view.promptRetry("Incorrect password");
                    choice = sc.nextInt();
                    if (choice == 2){
                    	view.exit();
                        System.exit(0);
                    }
                    view.prompt("Password");
                    sc.nextLine();
                    password = sc.nextLine();
                    hashedPassword = passwordHasher.hashPassword(password); //Not used rn
                }
                currentUser= db.getUserById(nric,ac);
                currentUser.login();
                choice=3;
                break;
            case 2:
            	view.register(); // Do we need to have a register?
            	//sc.nextLine();
            	break;
            case 3:
            	view.exit();
            	System.exit(0);
            	break;
            default:
                System.out.println("Please enter a valid choice.");
            	
            }
        } while (choice != 3 && currentUser == null);
        do {
        currentUser.displayMenu(view);
        choice=sc.nextInt();
        currentUser=cc.choice(choice, currentUser, view, sc);
        }
        while(currentUser != null);
        }
    }
}
    
        
        
        
        
        
       
    
     	
        	
        
        
