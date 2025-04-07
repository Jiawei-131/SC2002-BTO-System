package controllers;

import java.util.InputMismatchException;

import java.util.Scanner;
import entities.Applicant;
import entities.Manager;
import entities.Officer;
import entities.User;
import view.View;

public class ChoiceController {

	public User choice(int choice,User currentUser,View view,Scanner sc) {
        switch(choice) {
        case 1: if (currentUser instanceof Officer) {
        	
        } 
		else if (currentUser instanceof Applicant) {
			((Applicant)currentUser).viewProjects();
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
    	        default: View.invalidChoice();
    			}}while(choice!=5);
        }
    	break;
    	case 2:if (currentUser instanceof Officer) {
	            	System.out.println("The current user is an Officer.");
		        } 
        		else if (currentUser instanceof Applicant) {
		            System.out.println("The current user is an Applicant.");
		        } 
    			else if (currentUser instanceof Manager) {
		            System.out.println("The current user is an HDB Manager.");
		        }
    	break;
    	case 3:if (currentUser instanceof Officer) {
		            System.out.println("The current user is an Applicant.");
		        } 
				else if (currentUser instanceof Applicant) {
		            System.out.println("The current user is an HDB Officer.");
		        } 
				else if (currentUser instanceof Manager) {
		            System.out.println("The current user is an HDB Manager.");
	        }
    	break;
        case 4:if (currentUser instanceof Officer) {
		            System.out.println("The current user is an Applicant.");
		        } 
				else if (currentUser instanceof Applicant) {
		            System.out.println("The current user is an HDB Officer.");
		        } 
				else if (currentUser instanceof Manager) {
		            System.out.println("The current user is an HDB Manager.");
		        }
    	break;
    	case 5:if (currentUser instanceof Officer) {
		            System.out.println("The current user is an Applicant.");
		        } 
				else if (currentUser instanceof Applicant) {
		            System.out.println("The current user is an HDB Officer.");
		        } 
				else if (currentUser instanceof Manager) {
		            System.out.println("The current user is an HDB Manager.");
		        }
    	break;
        case 6: if (currentUser instanceof Manager) {
		            View.approvalMenu(currentUser);
		            sc.nextLine();
		        }
		        else {
		        	currentUser=currentUser.logout();
		        }
    	break;
        case 7:	 if (currentUser instanceof Manager) {
            currentUser=currentUser.logout();	
            }
        else {
        	System.out.println("Please enter a valid choice");
        }

        	break;
//        case 8:if (currentUser instanceof Officer) {
//            System.out.println("The current user is an Applicant.");
//        } 
//		else if (currentUser instanceof Applicant) {
//            System.out.println("The current user is an HDB Officer.");
//        } 
//		else if (currentUser instanceof Manager) {
//            currentUser=currentUser.logout();	
//            }
        default:
        	View.invalidChoice();
        }
		return currentUser;
        }
        
}

