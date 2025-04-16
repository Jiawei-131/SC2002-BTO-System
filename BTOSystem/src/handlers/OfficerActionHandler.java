package handlers;

import java.util.InputMismatchException;
import java.util.Scanner;
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
      	case 4->{
    		handleFilterAction(currentUser,sc);
    	}
    	case 5->{
        	return currentUser.logout();	
    	}
    	default->View.invalidChoice();
        }
		return currentUser;

	}
	
	
	public void handleEnquiryAction(int choice,User currentUser, Scanner sc) {
		View.menu(currentUser,((Officer)currentUser).getEnquiryOptions());
	}
	
    public void handleProjectAction(int choice,User currentUser, Scanner sc) {
    	do {
		currentUser.displayMenu(currentUser.getProjectOptions());
		choice=GetInput.getIntInput(sc, "your choice");
		switch(choice)
		{
		//Print all projects
		case 1: 
        break;
        //Add projects, check if project already existed?
        case 2:
        break;
        //Delete BTO Projects , check if exists
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
