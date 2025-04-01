
import java.util.Scanner;
import data.UserDatabase;
import java.util.ArrayList;
import java.util.List;
import java.io.*;
import java.util.*;
import data.PasswordHasher;

public class BTOSystem {
    public static void main(String[] args)
    {
    	int choice;
    	PasswordHasher passwordHasher = new PasswordHasher(); 
    	
    	final String File_Path="LoginInfo.txt";
    	UserDatabase db= new UserDatabase(File_Path);
    	AuthenticationController ac = new AuthenticationController();
    	Scanner sc = new Scanner(System.in);

        Map<String,String>users=db.readUsers();
        
        do {
        	System.out.println("""
            		BTO Management System (BMS)
            		1. Login
            		2. Register
            		3. Exit
            		Please enter a choice:
            		""");
            choice = sc.nextInt();
            
            switch(choice) {
            case 1:
            	System.out.println("Please enter your NRIC:");
                String nric = sc.nextLine();
                
                while(!users.containsKey(nric)) {
                	System.out.println("NRIC not found");
                    System.out.println("1: Retry\n2: Exit");
                    choice = sc.nextInt();
                    if (choice == 2){
                        System.exit(0);
                    }
                    else{
                        System.out.println("Please enter your NRIC:");
                        sc.nextLine();
                        nric = sc.nextLine();
                    }
                }
                
                System.out.println("Please enter your password:");
        		String password = sc.nextLine();
        		String storedHash = users.get(nric);
                String hashedPassword = passwordHasher.hashPassword(password);
                while (!Objects.equals(users.get(nric), password)) {
                    System.out.println("Incorrect password");
                    System.out.println("1: Retry\n2: Exit");
                    choice = sc.nextInt();
                    if (choice == 2){
                        System.exit(0);
                    }
                    else{
                        System.out.println("Please enter your password:");
                        sc.nextLine();
                        password = sc.nextLine();
                        hashedPassword = passwordHasher.hashPassword(password);
                    }
                }
                break;
            case 2:
            	
            	break;
            case 3:
            	System.exit(0);
            	break;
            default:
                System.out.println("Please enter a valid choice.");
            	
            }
        } while (choice != 3 && ac.);
        
        
        
        
        
        System.out.println("hi");
    
     	
        	
        
        
    }
}
