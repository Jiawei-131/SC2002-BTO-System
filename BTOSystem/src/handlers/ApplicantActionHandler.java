package handlers;

import java.util.InputMismatchException;
import data.UserDatabase;
import java.util.Scanner;
import entities.Applicant;
import entities.Project;
import data.*;
import entities.User;
import util.*;
import view.View;

public class ApplicantActionHandler implements ActionHandler,GetInput,PasswordReset {
	public User handleAction(int choice,User currentUser, Scanner sc,UserDatabase db){
        switch(choice) {
      //Manager Officer menu
        case 1-> handleProjectAction(choice,currentUser,sc);
    	case 2 -> handleEnquiryAction(choice,currentUser,sc);
    	case 3 ->{
    		return(PasswordReset.resetPassword(sc, currentUser, db));
    	}
    	case 4->{
    		handleFilterAction(currentUser,sc);
    	}
    	case 5 ->{
        	return currentUser.logout();
    	}
    	default->View.invalidChoice();
        }
		return currentUser;
	}
	
	public void handleEnquiryAction(int choice,User currentUser, Scanner sc) {
		String enquiry;
		int enquiryID;
		Applicant applicant	=(Applicant)currentUser;
    	do {
    	applicant.displayMenu(applicant.getEnquiryOptions());
    	choice = GetInput.getIntInput(sc, "your choice");
		switch(choice)
		{
		case 1->{
				enquiry=GetInput.getLineInput(sc,"Your Enquiry");
				applicant.createEnquiry(enquiry);
		}
        case 2 ->	applicant.viewEnquiry();
        //TODO Implement Edit Enquiry
        case 3 ->{
        	 enquiryID =GetInput.getIntInput(sc,"your EnquiryID");
        	 enquiry= GetInput.getLineInput(sc,"your new enquiry");
        	 applicant.editEnquiry(enquiryID, enquiry);
        }
        //TODO Implement Delete Enquiry
        case 4->{
				enquiryID=GetInput.getIntInput(sc,"your Enquiry ID");
				applicant.deleteEnquiry(enquiryID);
        }
        default -> View.invalidChoice();
		}}
    	while(choice!=5);
	}
	
	
    public void handleProjectAction(int choice,User currentUser, Scanner sc) {
    	do {
    	Applicant applicant	=(Applicant)currentUser;
    	applicant.displayMenu(applicant.getProjectOptions());
		choice = GetInput.getIntInput(sc, "your choice");
		switch(choice)
		{
		//Print all projects
		case 1->applicant.viewProjects(applicant);
        case 2->{
        	if (ProjectApplicationDatabase.getApplicationByApplicantId(applicant.getNric()) != null) {
        		System.out.println("You have an existing application!");
        	} else {
        		String flatType;
            	String projectName=GetInput.inputLoop("the Project Name",sc,s->s,s->ProjectDatabase.findByName(s)!=null);
            	
            	Project project = ProjectDatabase.findByName(projectName);
            	int twoRoomQuantity = project.getNumberOfType1Units();
            	int threeRoomQuantity = project.getNumberOfType2Units();
            	
            	if (applicant.getMaritalStatus().equals("Single")) {
            		flatType = "2-Room";
            	} else {
            		int flatTypeChoice=GetInput.inputLoop("""
                            the flat type
                            1. 2-Room
                            2. 3-Room
                            """, sc, Integer::parseInt, i -> i == 1 || i == 2);
                	flatType=flatTypeChoice==1?"2-Room":"3-Room";
            	}
            	
            	if (flatType.equals("2-Room") && twoRoomQuantity < 1 ||
            			flatType.equals("3-Room") && threeRoomQuantity < 1) {
            		System.out.println("No units available!");
            		break;
            	}
            		
            	applicant.applyForProject(projectName, flatType);
            	System.out.println("Successfully applied for " + projectName + ".");
        	}
        }

      //TODO Implement view applications
        case 3-> {
        	if (ProjectApplicationDatabase.getApplicationByApplicantId(applicant.getNric()) == null) {
        		System.out.println("You do not have an existing application!");
        	} else {
        		applicant.viewApplication();
        	}
        }
      //TODO Implement requestWithdrawal
        case 4 -> {
        	applicant.requestWithdrawal();
        	System.out.println("Successfully requested withdrawal of application.");
        }
        default -> View.invalidChoice();
		}}
    	while(choice!=5);
    }

    


}
