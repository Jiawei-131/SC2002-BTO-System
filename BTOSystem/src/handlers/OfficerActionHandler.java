package handlers;

import java.util.InputMismatchException;
import java.util.Scanner;

import controllers.EnquiryController;
import data.UserDatabase;
import entities.Officer;
import entities.User;
import util.ActionHandler;
import util.GetInput;
import util.PasswordReset;
import view.View;

public class OfficerActionHandler implements ActionHandler,PasswordReset,GetInput {
	public User handleAction(int choice,User currentUser, Scanner sc,UserDatabase db){
        switch(choice) {
      // Officer || Applicant ? menu
        case 1->    handleProjectAction(choice,currentUser,sc);
    	case 2->	handleEnquiryAction(choice,currentUser,sc);
    	case 3->{
    		return(PasswordReset.resetPassword(sc, currentUser, db));
    	}
      	case 4->handleFilterAction(currentUser,sc);
    	case 5->{
        	return currentUser.logout();	
    	}
    	default->View.invalidChoice();
        }
		return currentUser;

	}
	
	/*			"1. Submit Enquiry",
			"2. View Enquiry",
			"3. Edit Enquiry",
			"4. Delete Enquiry",
			"5. Back to Main Menu"*/

	public void handleEnquiryAction(int choice, User currentUser, Scanner sc) {
//		View.menu(currentUser,((Officer)currentUser).getEnquiryOptions());
	    Officer officer = (Officer) currentUser;
	    EnquiryController controller = new EnquiryController();

	    int selection;
	    do {
	        View.menu(officer, officer.getEnquiryOptions());
	        selection = GetInput.getIntInput(sc, "your choice");

	        switch (selection) {
	            case 1 -> officer.viewEnquiries(controller);
	            case 2 -> officer.replyToEnquiry(sc, controller);
	            case 3 -> System.out.println("Returning to main menu...");
	            default -> View.invalidChoice();
	        }
	    } while (selection != 3);
	}

	
	
	/*    		"1. View list of projects",
		 	"2. Apply for project",
    		"3. View applied projects",
    		"4. Withdraw from BTO Application",
    		"5. Generate Receipt",
    		"6. Check Status",
			"7. Back to Main Menu"*/
    public void handleProjectAction(int choice,User currentUser, Scanner sc) {
    	do {
		currentUser.displayMenu(currentUser.getProjectOptions());
		choice=GetInput.getIntInput(sc, "your choice");
		switch(choice)
		{
		//Print all projects
		case 1: 
        break;
        case 2:
        break;
        case 3:
        break;
        
        case 4:
        break;
        case 5://TODO Generate Receipt
        break;
        default: View.invalidChoice();
		}}
    	while(choice!=7);
    }
    

}
