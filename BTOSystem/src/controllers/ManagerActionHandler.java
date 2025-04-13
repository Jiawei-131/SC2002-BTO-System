package controllers;

import java.time.LocalDate;
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
    	case 2 -> handleApprovalAction(choice,currentUser,sc);
    	case 3-> handleEnquiryAction(choice,currentUser,sc);
        case 4->{
        	return(PasswordReset.resetPassword(sc, currentUser, db));
        }
        case 5->{
    		return currentUser.logout();
    	}
    	default->View.invalidChoice();
        }
		return currentUser;

	}
	
	
	/*	1. Approve or reject HDB Officer’s registration as the HDB 
			Manager in-charge of the project – update project’s remaining HDB 
			Officer slots 
		2. Approve or reject Applicant’s BTO application – approval is 
			limited to the supply of the flats (number of units for the respective flat 
			types) 
		3. Approve or reject Applicant's request to withdraw the application. 
		4. Logout
			"""*/
	private void handleApprovalAction(int choice,User currentUser, Scanner sc) {
		Manager manager	=(Manager)currentUser;
		do {
			manager.displayMenu(manager.getApprovalOption());
			choice=GetInput.getIntInput(sc, "your choice");
			switch(choice) {
				case 1,2 ->approveReject(sc,manager);//TODO Print list of officer applying and key in who to approve?
				case 3 ->System.out.println("Not Done");//TODO Print list of Applicant withdraw and key in who to approve?
				default-> View.invalidChoice();
			}
		}while(choice!=4);
		
	}

	/*	"1. View enquiry of all projects",
		"2. View enquiries of my project",
		"3. Reply to enquiry",
		"4. Delete enquiry",
		"5. Back to Main Menu" */
	public void handleEnquiryAction(int choice,User currentUser, Scanner sc) {
		Manager manager	=(Manager)currentUser;
		do {
			manager.displayMenu(manager.getEnquiryOptions());
			choice=GetInput.getIntInput(sc, "your choice");
			switch(choice) {
			case 1,2 ->System.out.println("Not Done");
			case 3 ->System.out.println("Not Done");
			case 4 ->System.out.println("Not Done");
			default-> View.invalidChoice();
			}
		}while(choice!=5);
		
		
	}
	
	
	/*	"1. View All Project listings",
	  	"2. View My Project listings",
		"3. Toggle Visibility of Project",
		"4. Create BTO Project listings",
		"5. Delete BTO Project listings",
		"6. Edit BTO Project listings ",
		"7. Generate report",
		"8. Back to Main Menu", */
    public void handleProjectAction(int choice,User currentUser, Scanner sc) {
    	Manager manager	=(Manager)currentUser;
    	do {
		manager.displayMenu(manager.getProjectOptions());
		choice=GetInput.getIntInput(sc, "your choice");
		switch(choice)
		{
		//Print all projects
		case 1,2-> manager.viewProjects(choice);
        //Add projects, check if project already existed?
        case 3-> manager.toggleVisibility();
        //Delete BTO Projects , check if exists
        case 4->createProject(currentUser,sc);
        //Edit BTO Projects, check if exists
        case 5 ->manager.deleteProject();
        case 6 ->System.out.println("Not Done");
        case 7 ->System.out.println("Not Done");
        case 8 ->System.out.println("Not Done");
        default-> View.invalidChoice();
		}}
    	while(choice!=8);
    }
    
    private void createProject(User currentUser,Scanner sc) {
 		String btoName=GetInput.getLineInput(sc, "the BTO Name");
 		String neighbourhood=GetInput.getLineInput(sc, "the neighbourhood");
 		int unitType1=GetInput.getIntInput(sc, "the Number of 2 Room Units");
 		int unitType2=GetInput.getIntInput(sc, "the Number of 3 Room Units");
 		String openDate=GetInput.inputLoop("the Opening Date in DD-MM-YYYY format", sc, s->s, i->i.matches("^\\d{2}-\\d{2}-\\d{4}$"));
 		String closeDate=GetInput.inputLoop("the Closing Date in DD-MM-YYYY format", sc, s->s, i->i.matches("^\\d{2}-\\d{2}-\\d{4}$"));
 		int availableSlots = GetInput.inputLoop("the Number of HDB Officer slots", sc, Integer::parseInt, i->i<=10);
 	    int isVisibleChoice = GetInput.inputLoop("""
 	            the visibility to applicants
 	            1. Yes
 	            2. No
 	            """, sc, Integer::parseInt, i -> i == 1 || i == 2);
 	   boolean isVisible = (isVisibleChoice == 1) ? true : false;
 		((Manager)currentUser).createProject(btoName, neighbourhood, unitType1,unitType2, openDate, closeDate,((Manager)currentUser), availableSlots,isVisible);
    }
    
    private void approveReject(Scanner sc,Manager manager)
    {
 	    int choice = GetInput.inputLoop("""
 	            your choice
 	            1. Approve
 	            2. Reject
 	            """, sc, Integer::parseInt, i -> i == 1 || i == 2);
 	   if (choice == 1) {
 		    manager.approveApplication(null);
 		} else {
 		    manager.rejectApplication(null);
 		}
    }

}
