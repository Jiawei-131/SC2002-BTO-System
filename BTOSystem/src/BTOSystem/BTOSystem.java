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
//        do {
//            currentUser.handleChoice(choice,view,sc);
//        }while(currentUser.isLogin()!=false);
        do {
        currentUser.displayMenu(view);
        choice=sc.nextInt();
        switch(choice) {
        case 1: if (currentUser instanceof Applicant) {
        	((Applicant)currentUser).viewProjects();
        } 
		else if (currentUser instanceof Officer) {
            System.out.println("The current user is an HDB Officer.");
        } 
        //Manager Project menu
		else if (currentUser instanceof Manager) {
			do {
        		view.projectMenu(currentUser);
    			choice=sc.nextInt();
    			sc.nextLine();
    			switch(choice)
    			{
    			//Print all projects
    			case 1: ((Manager)currentUser).viewAllProjects();
		        break;
		        //Add projects, check if project already existed?
    	        case 2:	try { 
    	        		view.prompt("BTO Name"); 
    	        		String btoName=sc.nextLine();
    	        		view.prompt("Neighbourhood");
    	        		String neighbourhood=sc.nextLine();
    	        		view.prompt("RoomType");
    	        		int roomType=sc.nextInt();
    	        		view.prompt("Number of Units");
    	        		int numOfUnit=sc.nextInt();
    	        		sc.nextLine();
    	        		view.prompt("Opening Date");
    	        		String openDate=sc.nextLine();
    	        		view.prompt("Closing Date");
    	        		String closeDate=sc.nextLine();
    	        		view.prompt("Available slots");
    	        		int availableSlots=sc.nextInt();
    	        		((Manager)currentUser).createProject(btoName, neighbourhood, roomType, numOfUnit, openDate, closeDate,((Manager)currentUser), availableSlots);
    	        	}
    	        catch(InputMismatchException e)
    	        {
    	        	System.out.println("Invalid input! Please enter the correct type of data");
    	        	sc.nextLine();
    	        }
    	        catch (Exception e) {
    	        	System.out.println("An unexpected error has occured"+e.getMessage());
    	        }
    	        break;
    	        //Delete BTO Projects , check if exists
    	        case 3:
    	        break;
    	        //Edit BTO Projects, check if exists
    	        case 4:
    	        break;
    	        default: System.out.println("Please enter a valid input!");
    			}}while(choice!=5);
        }
    	break;
    	case 2:if (currentUser instanceof Applicant) {
	            	System.out.println("The current user is an Applicant.");
		        } 
        		else if (currentUser instanceof Officer) {
		            System.out.println("The current user is an HDB Officer.");
		        } 
    			else if (currentUser instanceof Manager) {
		            System.out.println("The current user is an HDB Manager.");
		        }
    	break;
    	case 3:if (currentUser instanceof Applicant) {
		            System.out.println("The current user is an Applicant.");
		        } 
				else if (currentUser instanceof Officer) {
		            System.out.println("The current user is an HDB Officer.");
		        } 
				else if (currentUser instanceof Manager) {
		            System.out.println("The current user is an HDB Manager.");
	        }
    	break;
        case 4:if (currentUser instanceof Applicant) {
		            System.out.println("The current user is an Applicant.");
		        } 
				else if (currentUser instanceof Officer) {
		            System.out.println("The current user is an HDB Officer.");
		        } 
				else if (currentUser instanceof Manager) {
		            System.out.println("The current user is an HDB Manager.");
		        }
    	break;
    	case 5:if (currentUser instanceof Applicant) {
		            System.out.println("The current user is an Applicant.");
		        } 
				else if (currentUser instanceof Officer) {
		            System.out.println("The current user is an HDB Officer.");
		        } 
				else if (currentUser instanceof Manager) {
		            System.out.println("The current user is an HDB Manager.");
		        }
    	break;
        case 6:if (currentUser instanceof Applicant) {
		            System.out.println("The current user is an Applicant.");
		        } 
				else if (currentUser instanceof Officer) {
		            System.out.println("The current user is an HDB Officer.");
		        } 
				else if (currentUser instanceof Manager) {
		            view.approvalMenu(currentUser);
		            sc.nextLine();
		        }
    	break;
        case 7:	if (currentUser instanceof Applicant) {
            System.out.println("The current user is an Applicant.");
        } 
		else if (currentUser instanceof Officer) {
            currentUser=currentUser.logout();
        } 
		else if (currentUser instanceof Manager) {
            currentUser=currentUser.logout();	
            }
        	break;
        case 8:if (currentUser instanceof Applicant) {
            System.out.println("The current user is an Applicant.");
        } 
		else if (currentUser instanceof Officer) {
            System.out.println("The current user is an HDB Officer.");
        } 
		else if (currentUser instanceof Manager) {
            currentUser=currentUser.logout();	
            }
        default:
        	System.out.println("Please enter a valid choice");
        }
        }
        while(currentUser != null);
        }
        
        
        
        
        
        
       
    
     	
        	
        
        
    }
}
