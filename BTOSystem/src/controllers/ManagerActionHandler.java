package controllers;

import java.util.InputMismatchException;

import java.util.Scanner;

import data.UserDatabase;
import entities.Applicant;
import entities.Manager;
import entities.Officer;
import entities.User;
import view.View;

public class ManagerActionHandler implements ActionHandler {
	public User handleAction(int choice,User currentUser, Scanner sc,UserDatabase db){
        switch(choice) {
      //Manager Officer menu
        case 1: handleProjectAction(choice,currentUser,sc);
    	break;
    	case 2:
    	case 3: handleApprovalAction(choice,currentUser,sc);
    	break;
        case 4:handleEnquiryAction(choice,currentUser,sc);
    	break;
        case 5:return(AuthenticationController.resetPassword(currentUser, db, currentUser.getNric(), currentUser.getPassword(), sc));
    	case 6:currentUser=currentUser.logout();
    	return currentUser;
    	default:View.invalidChoice();
        }
		return currentUser;

	}
	
	private void handleApprovalAction(int choice,User currentUser, Scanner sc) {
		View.approvalMenu(currentUser);
	}
	public void handleEnquiryAction(int choice,User currentUser, Scanner sc) {
		View.enquiryMenu(currentUser,((Manager)currentUser).getEnquiryOptions());
	}
	
    public void handleProjectAction(int choice,User currentUser, Scanner sc) {
    	do {
		View.projectMenu(currentUser,((Manager)currentUser).getProjectOptions());
		choice=sc.nextInt();
		sc.nextLine();
		switch(choice)
		{
		//Print all projects
		case 1: ((Manager)currentUser).viewAllProjects();
        break;
        //Add projects, check if project already existed?
        case 2:	
//        	try { 
        	createProject(currentUser,sc);
//        	}
//        catch(InputMismatchException e)
//        {
//        	System.out.println("Invalid input! Please enter the correct type of data");
//        	sc.nextLine();
//        }
//        catch (Exception e) {
//        	System.out.println("An unexpected error has occured"+e.getMessage());
//        }
        break;
        //Delete BTO Projects , check if exists
        case 3:
        break;
        //Edit BTO Projects, check if exists
        case 4:
        break;
        default: View.invalidChoice();
		}}
    	while(choice!=5);
    }
    
    private void createProject(User currentUser,Scanner sc) {
 		View.prompt("BTO Name"); 
 		String btoName=sc.nextLine();
 		View.prompt("Neighbourhood");
 		String neighbourhood=sc.nextLine();
 		View.prompt("RoomType");
 		int roomType=sc.nextInt();
 		View.prompt("Number of Units");
 		int numOfUnit=sc.nextInt();
 		sc.nextLine();
 		View.prompt("Opening Date");
 		String openDate=sc.nextLine();
 		View.prompt("Closing Date");
 		String closeDate=sc.nextLine();
 		View.prompt("Available slots");
 		int availableSlots=sc.nextInt();
 		((Manager)currentUser).createProject(btoName, neighbourhood, roomType, numOfUnit, openDate, closeDate,((Manager)currentUser), availableSlots);
    }
    

}
