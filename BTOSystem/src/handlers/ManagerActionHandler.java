package handlers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import controllers.AuthenticationController;
import controllers.DateTimeController;
import controllers.EnquiryController;
import data.*;
import entities.Applicant;
import entities.Manager;
import entities.Officer;
import entities.OfficerApplication;
import entities.Project;
import entities.ProjectApplication;
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
      	case 5->handleFilterAction(currentUser,sc);
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
		
		"1. Approve or reject HDB Officer’s registration",
		"2. Approve or reject Applicant’s BTO application",
		"3.	Approve or reject Applicant's request to withdraw the application.",
		"4. Back to Main Menu"
			"""*/
	private void handleApprovalAction(int choice,User currentUser, Scanner sc) {
		Manager manager	=(Manager)currentUser;
		do {
			manager.displayMenu(manager.getApprovalOption());
			choice=GetInput.getIntInput(sc, "your choice");
			switch(choice) {
				case 1 ->approveReject(sc,manager);//TODO Print list of officer applying and key in who to approve?
				case 2 ->approveRejectApplicant(sc,manager);//TODO Print list of Applicant withdraw and key in who to approve?
				case 3->approveRejectWithdrawl(sc,manager);
				case 4->{}
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
	"3. View My Active Project",
	"4. Create BTO Project listing",
	"5. Delete BTO Project listing",
	"6. Edit BTO Project listing ",
	"7. Generate report",
	"8. Back to Main Menu"*/
   public void handleProjectAction(int choice,User currentUser, Scanner sc) {
	    	Manager manager	=(Manager)currentUser;
	    	do {
			manager.displayMenu(manager.getProjectOptions());
			choice=GetInput.getIntInput(sc, "your choice");
			switch(choice)
			{
			case 1,2->manager.viewProjects(choice);
			case 3->manager.viewActiveProject();
	        case 4-> manager.createProject(sc);
	        case 5 ->manager.deleteProject(sc);
	        case 6 ->manager.editProject(sc);
	        case 7 ->manager.generateReport(sc);
	        case 8 -> {}
	        default-> View.invalidChoice();
			}}
	    	while(choice!=8);
	    }
   
    private void approveReject(Scanner sc,Manager manager)
    {
try {
    	List<OfficerApplication> officerApplications =OfficerApplicationDatabase.readApplication();
    	List<String> nricList = new ArrayList<>();
    	if(manager.getActiveProject()==null)
    	{
    		System.out.println("You do not have any active project");
    	}
    	else {
            System.out.println("\n=== All Projects ===");
            for (OfficerApplication officerApplication : officerApplications) {
            	
            	if(officerApplication.getProjectName().equals(manager.getActiveProject().getName())) {
            		nricList.add(officerApplication.getApplicantId());
                    System.out.println(officerApplication.getApplicantId() + " - " + officerApplication.getApplicationStatus() + 
                    		 " - " +officerApplication.getProjectName());
            	}
            }
            if(nricList.isEmpty())
            {
            	System.out.println("Nothing to approve");
            }
            else {
         	    int choice = GetInput.inputLoop("""
         	            your choice
         	            1. Approve
         	            2. Reject
         	            3. Back to Approval Menu
         	            """, sc, Integer::parseInt, i -> i >= 1 &&i<=3);
    	     	   if (choice == 1) {
    	         	   String NRIC= GetInput.inputLoop(" the officer NRIC", sc, s->s, s ->AuthenticationController.checkNRIC(s)&&nricList.contains(s));
    	     		    manager.approveOfficerRegistration(OfficerApplicationDatabase.getApplicationByApplicantId(NRIC));
    	     		} else if(choice==2){
    	          	   String NRIC= GetInput.inputLoop(" the officer NRIC", sc, s->s, s ->AuthenticationController.checkNRIC(s)&&nricList.contains(s));
    	     		    manager.rejectOfficerRegistration(OfficerApplicationDatabase.getApplicationByApplicantId(NRIC));
    	     		}
            }
    	}
    }catch (RegistrationFailedException e) {
        System.out.println(e.getMessage());
    }
    }
    
    private void approveRejectApplicant(Scanner sc,Manager manager)
    {
    	try {
    	List<ProjectApplication> ProjectApplications =ProjectApplicationDatabase.readApplication();
    	List<String> nricList = new ArrayList<>();
    	if(manager.getActiveProject()==null)
    	{
    		System.out.println("You do not have any active project");
    	}
    	else {
            System.out.println("\n=== All Projects ===");
            for (ProjectApplication ProjectApplication : ProjectApplications) {
            	
            	if(ProjectApplication.getProjectName().equals(manager.getActiveProject().getName()))
            	{
            		nricList.add(ProjectApplication.getApplicantId());
                    System.out.println(ProjectApplication.getApplicantId() + " - " + ProjectApplication.getApplicationStatus() + 
                    		 " - " +ProjectApplication.getProjectName());
            	}
            }
            if(nricList.isEmpty())
            {
            	System.out.println("Nothing to approve");
            }
            else {
         	    int choice = GetInput.inputLoop("""
         	            your choice
         	            1. Approve
         	            2. Reject
         	            3. Back to Approval Menu
         	            """, sc, Integer::parseInt, i -> i >= 1 &&i<=3);
    	     	   if (choice == 1) {
    	         	   String NRIC= GetInput.inputLoop(" the Applicant NRIC", sc, s->s, s ->AuthenticationController.checkNRIC(s)&&nricList.contains(s));
    	     		    manager.approveApplication(ProjectApplicationDatabase.getApplicationByApplicantId(NRIC));
    	     		} else if(choice==2) {
    	          	   String NRIC= GetInput.inputLoop(" the Applicant NRIC", sc, s->s, s ->AuthenticationController.checkNRIC(s)&&nricList.contains(s));
    	     		    manager.rejectApplication(ProjectApplicationDatabase.getApplicationByApplicantId(NRIC));
    	     		}
            }
    	}
    }catch (RegistrationFailedException e) {
        System.out.println(e.getMessage());
    }
    }
    private void approveRejectWithdrawl(Scanner sc,Manager manager)
    {
    	try {
    	List<ProjectApplication> ProjectApplications =ProjectApplicationDatabase.readApplication();
    	List<String> nricList = new ArrayList<>();
    	if(manager.getActiveProject()==null)
    	{
    		System.out.println("You do not have any active project");
    	}
    	else {
            System.out.println("\n=== All Projects ===");
            for (ProjectApplication ProjectApplication : ProjectApplications) {
            	
            	if(ProjectApplication.getProjectName().equals(manager.getActiveProject().getName()))
            	{
            		nricList.add(ProjectApplication.getApplicantId());
                    System.out.println(ProjectApplication.getApplicantId() + " - " + ProjectApplication.getApplicationStatus() + 
                    		 " - " +ProjectApplication.getProjectName());
            	}
            }
            if(nricList.isEmpty())
            {
            	System.out.println("Nothing to approve");
            }
            else {
         	    int choice = GetInput.inputLoop("""
         	            your choice
         	            1. Approve
         	            2. Reject
         	            3. Back to Approval Menu
         	            """, sc, Integer::parseInt, i -> i >= 1 &&i<=3);
    	     	   if (choice == 1) {
    	         	   String NRIC= GetInput.inputLoop(" the Applicant NRIC", sc, s->s, s ->AuthenticationController.checkNRIC(s)&&nricList.contains(s));
    	     		    manager.approveApplicantWithdrawal(ProjectApplicationDatabase.getApplicationByApplicantId(NRIC));
    	     		} else if (choice==2){
    	          	   String NRIC= GetInput.inputLoop(" the Applicant NRIC", sc, s->s, s ->AuthenticationController.checkNRIC(s)&&nricList.contains(s));
    	     		    manager.rejectApplicantWithdrawal(ProjectApplicationDatabase.getApplicationByApplicantId(NRIC));
    	     		}
            }
    	}
    	}catch (RegistrationFailedException e) {
            System.out.println(e.getMessage());
        }
    }
}
