package controllers;

import java.util.InputMismatchException;

import java.util.Scanner;

import data.UserDatabase;
import entities.Applicant;
import entities.Manager;
import entities.Officer;
import entities.User;
import util.ActionHandler;
import util.GetInput;
import util.PasswordReset;
import view.View;

public class ManagerActionHandler implements ActionHandler,PasswordReset,GetInput {
	public User handleAction(int choice,User currentUser, Scanner sc,UserDatabase db){
        switch(choice) {
      //Manager Officer menu
        case 1 -> handleProjectAction(choice,currentUser,sc);
    	case 2 -> System.out.println("Test");
    	case 3-> handleApprovalAction(choice,currentUser,sc);
        case 4->handleEnquiryAction(choice,currentUser,sc);
        case 5->{
        	return(PasswordReset.resetPassword(sc, currentUser, db));
        }
    	case 6->{
    		return currentUser.logout();
    	}
    	default->View.invalidChoice();
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
		choice=GetInput.getIntInput(sc, "your choice");
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
 		String btoName=GetInput.getLineInput(sc, "the BTO Name");
 		String neighbourhood=GetInput.getLineInput(sc, "the neighbourhood");
 		int roomType=GetInput.getIntInput(sc, "the RoomType");
 		int numOfUnit=GetInput.getIntInput(sc, "the Number of Units");
 		String openDate=GetInput.getLineInput(sc, "the Opening Date");
 		String closeDate=GetInput.getLineInput(sc, "the Closing Date");
 		int availableSlots=GetInput.getIntInput(sc, "the Available slots");
 		((Manager)currentUser).createProject(btoName, neighbourhood, roomType, numOfUnit, openDate, closeDate,((Manager)currentUser), availableSlots);
    }
    

}
