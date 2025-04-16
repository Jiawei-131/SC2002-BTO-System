package handlers;

import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import controllers.DateTimeController;
import controllers.EnquiryController;
import data.*;
import entities.Applicant;
import entities.Manager;
import entities.Officer;
import entities.Project;
import entities.User;
import util.*;
import view.View;

public class ManagerActionHandler implements ActionHandler,PasswordReset,GetInput {
	public User handleAction(int choice,User currentUser, Scanner sc,UserDatabase db){
        switch(choice) {
      //Manager Officer menu
        case 1 -> handleProjectAction(choice,currentUser,sc);
    	case 2 -> handleApprovalAction(choice,currentUser,sc);
    	case 3->  handleEnquiryAction(choice,currentUser,sc);
        case 4->{
        	return(PasswordReset.resetPassword(sc, currentUser, db));
        }
      	case 5->{
    		handleFilterAction(currentUser,sc);
    	}
        case 6->{
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
		final EnquiryController enquiryController = new EnquiryController();
		do {
			manager.displayMenu(manager.getEnquiryOptions());
			choice=GetInput.getIntInput(sc, "your choice");
			switch(choice) {
			case 1,2 ->manager.viewAllEnquiries(enquiryController,choice);
			case 3 -> manager.replyToEnquiry(sc, enquiryController);
			case 4-> {}
			default-> View.invalidChoice();
			}
		}while(choice!=4);
	}
	
/*	"1. View All Project listings",
	"2. View My Project listings",
	"3. Create BTO Project listing",
	"4. Delete BTO Project listing",
	"5. Edit BTO Project listing ",
	"6. Generate report",
	"7. Back to Main Menu"*/
   public void handleProjectAction(int choice,User currentUser, Scanner sc) {
	    	Manager manager	=(Manager)currentUser;
	    	do {
			manager.displayMenu(manager.getProjectOptions());
			choice=GetInput.getIntInput(sc, "your choice");
			switch(choice)
			{
			case 1,2->manager.viewProjects(choice);
	        case 3-> manager.createProject(sc);
	        //Edit BTO Projects, check if exists
	        case 4 ->manager.deleteProject(sc);
	        case 5 ->manager.editProject(sc);
	        case 6 ->manager.generateReport(sc);
	        case 7 -> {}
	        default-> View.invalidChoice();
			}}
	    	while(choice!=7);
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
