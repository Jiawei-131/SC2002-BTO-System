package controllers;

import java.util.InputMismatchException;
import data.UserDatabase;
import java.util.Scanner;
import entities.Applicant;
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
    	case 4 ->{
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
		case 1->applicant.viewProjects();
        case 2->{
        	String projectName=GetInput.getLineInput(sc,"Project Name");
        	String flatType=GetInput.getLineInput(sc,"Flat Type");
        	applicant.applyForProject(projectName, flatType);
        }

      //TODO Implement view applications
        case 3-> applicant.viewApplication();
      //TODO Implement requestWithdrawal
        case 4 -> applicant.requestWithdrawal();
        default -> View.invalidChoice();
		}}
    	while(choice!=5);
    }

    


}
