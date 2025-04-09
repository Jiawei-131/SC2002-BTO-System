package controllers;

import java.util.InputMismatchException;
import data.UserDatabase;
import java.util.Scanner;
import entities.Applicant;
import entities.Manager;
import entities.User;
import view.View;

public class ApplicantActionHandler implements ActionHandler {
	public User handleAction(int choice,User currentUser, Scanner sc,UserDatabase db){
		String password=null;
        switch(choice) {
      //Manager Officer menu
        case 1: handleProjectAction(choice,currentUser,sc);
    	break;	
    	case 2: handleEnquiryAction(choice,currentUser,sc);
    	break;
    	case 3:	return(AuthenticationController.resetPassword(currentUser, db, currentUser.getNric(), currentUser.getPassword(), sc));
    	case 4: currentUser=currentUser.logout();
    	return currentUser;
    	default:View.invalidChoice();
        }
		return currentUser;

	}
	
	public void handleEnquiryAction(int choice,User currentUser, Scanner sc) {
		String enquiry;
		int enquiryID;
    	do {
    	Applicant applicant	=(Applicant)currentUser;
    	View.enquiryMenu(currentUser,((Applicant)currentUser).getEnquiryOptions());
		choice=sc.nextInt();
		sc.nextLine();
		switch(choice)
		{
		case 1:View.prompt("Enquiry"); 
				enquiry=sc.nextLine();
				applicant.createEnquiry(enquiry);
        break;
        case 2:	applicant.viewEnquiry();
        break;
        //TODO Implement Edit Enquiry
        case 3: try { 
        		View.prompt("Enquiry ID"); 
        		enquiryID=sc.nextInt();
				sc.nextLine();
        		View.prompt("new Enquiry"); 
        		enquiry=sc.nextLine();
        		applicant.editEnquiry(enquiryID, enquiry);
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
        //TODO Implement Delete Enquiry
        case 4:View.prompt("Enquiry ID"); 
				enquiryID=sc.nextInt();
				applicant.deleteEnquiry(enquiryID);
        break;
        default: View.invalidChoice();
		}}
    	while(choice!=5);
	}
	
    public void handleProjectAction(int choice,User currentUser, Scanner sc) {
    	do {
    	Applicant applicant	=(Applicant)currentUser;
		View.projectMenu(applicant,applicant.getProjectOptions());
		choice=sc.nextInt();
		sc.nextLine();
		switch(choice)
		{
		//Print all projects
		case 1: applicant.viewProjects();
        break;
        case 2:	try { 
        	View.prompt("Project Name");
        	String projectName=sc.nextLine();
        	View.prompt("Flat Type");
        	String flatType=sc.nextLine();
        	applicant.applyForProject(projectName, flatType);
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
      //TODO Implement view applications
        case 3: applicant.viewApplication();
        break;
      //TODO Implement requestWithdrawal
        case 4: applicant.requestWithdrawal();
        break;
        default: View.invalidChoice();
		}}
    	while(choice!=5);
    }
    


}
