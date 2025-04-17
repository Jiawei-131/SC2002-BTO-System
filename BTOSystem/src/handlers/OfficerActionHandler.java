package handlers;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import controllers.EnquiryController;
import controllers.ProjectController;
import data.ProjectApplicationDatabase;
import data.ProjectDatabase;
import data.UserDatabase;
import entities.Applicant;
import entities.Manager;
import entities.Officer;
import entities.Project;
import entities.ProjectApplication;
import entities.User;
import util.ActionHandler;
import util.ApplicationStatus;
import util.GetInput;
import util.PasswordReset;
import view.View;

public class OfficerActionHandler implements ActionHandler,PasswordReset,GetInput {
	public User handleAction(int choice,User currentUser, Scanner sc,UserDatabase db){
        switch(choice) {
      // Officer || Applicant ? menu
        case 1->    {
        	handleProjectAction(choice,currentUser,sc);
//        	System.out.println("bruh");
        }
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
    		Officer officer	=(Officer)currentUser;
    		officer.displayMenu(officer.getProjectOptions());
		choice=GetInput.getIntInput(sc, "your choice");
		switch(choice)
		{
		//Print all projects
		case 1 -> handleProjectApplicantAction(choice, currentUser, sc);
        case 2 -> handleProjectOfficerAction(choice, currentUser, sc);
        case 3 -> System.out.println("Returning to main menu...");
        default -> View.invalidChoice();
		}}
    	while(choice!=3);
    }
    
    public void handleProjectApplicantAction(int choice, User currentUser, Scanner sc) {
    	// project > applicant
    	do {
        	Applicant applicant	=(Applicant)currentUser;
        	Officer officer = (Officer)currentUser;
        	officer.displayMenu(officer.getProjectApplicantOptions());
    		choice = GetInput.getIntInput(sc, "your choice");
    		switch(choice)
    		{
    		//Print all projects
    		case 1->applicant.viewProjects(applicant);
            case 2->{
            	if (ProjectApplicationDatabase.getApplicationByApplicantId(applicant.getNric()) != null) {
            		System.out.println("You have an existing application!");
            		break;
            	} if (applicant.getMaritalStatus().equals("Single") && applicant.getAge() < 35) {
        			System.out.println("Ineligible applicant. No projects available.");
        			break;
        		} else {
            		String flatType;
                	String projectName=GetInput.inputLoop("the Project Name",sc,s->s,s->ProjectDatabase.findByName(s)!=null);
                	
                	if (!ProjectController.checkOfficerApplicantEligibility(projectName, officer)) {
                		System.out.println("You cannot apply for a project you are handling.");
                		break;
                	}
                	
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
            	ProjectApplication application = ProjectApplicationDatabase.getApplicationByApplicantId(applicant.getNric());
            	if (application == null) {
            		System.out.println("You do not have an existing application!");
            		break;
            	} else if (application.getApplicationStatus().equals(ApplicationStatus.WITHDRAWREJ.getStatus())) {
            		System.out.println("Your withdrawal request has been rejected.");
            		
            		// resets status
            		application.setApplicationStatus(ApplicationStatus.SUCCESSFUL.getStatus());
                    ProjectApplicationDatabase.updateApplication(application);
            	}
            	applicant.viewApplication();
            }
            
            case 4 -> {
            	ProjectApplication application = ProjectApplicationDatabase.getApplicationByApplicantId(applicant.getNric());
            	if (application == null) {
            		System.out.println("You do not have an existing application!");
            	} else if (application.getApplicationStatus().equals(ApplicationStatus.PENDING.getStatus())) {
            		System.out.println("Your application is still pending!");
            	} else if (application.getApplicationStatus().equals(ApplicationStatus.UNSUCCESSFUL.getStatus())) {
            		System.out.println("Your application was unsuccessful!");
            	} else if (application.getApplicationStatus().equals(ApplicationStatus.BOOKREQ.getStatus())) {
            		System.out.println("There is an on-going booking request. Please wait for an officer to assist.");
            	} else if (application.getApplicationStatus().equals(ApplicationStatus.BOOKED.getStatus())) {
            		System.out.println("You have already successfully booked a flat.");
            	} else if (application.getApplicationStatus().equals(ApplicationStatus.WITHDRAWREQ.getStatus())) {
            		System.out.println("There is an on-going withdrawal request. Please wait for an officer to assist.");
            	} else if (application.getApplicationStatus().equals(ApplicationStatus.WITHDRAWN.getStatus())) {
            		System.out.println("This application has been withdrawn.");
            	} else { // status is {successful, withdrawal rejected}
            		applicant.requestFlatBooking();
            		System.out.println("Successfully requested booking.");
            	}
            }
          //TODO Implement requestWithdrawal
            case 5 -> {
            	ProjectApplication application = ProjectApplicationDatabase.getApplicationByApplicantId(applicant.getNric());
            	
            	if (application.getApplicationStatus().equals(ApplicationStatus.BOOKREQ.getStatus())) {
                	System.out.println("Withdrawal requests are not allowed while there is an on-going booking request.");
                } else if (application.getApplicationStatus().equals(ApplicationStatus.WITHDRAWREQ.getStatus())) {
                	System.out.println("There is already an on-going withdrawal request.");
                } else if (application.getApplicationStatus().equals(ApplicationStatus.WITHDRAWN.getStatus()) ||
                		application.getApplicationStatus().equals(ApplicationStatus.UNSUCCESSFUL.getStatus())) {
                	System.out.println("Application already withdrawn.");
                } else {
                	applicant.requestWithdrawal();
                	System.out.println("Successfully requested withdrawal of application.");
                }
            }
            case 6->{}
            default -> View.invalidChoice();
    		}}
        	while(choice!=6);
    }
    
    public void handleProjectOfficerAction(int choice, User currentUser, Scanner sc) {
    	// project > officer
    	Officer officer	=(Officer)currentUser;
    	
    	do {
    		officer	=(Officer)currentUser;
    		officer.displayMenu(officer.getProjectOfficerOptions());
    		choice=GetInput.getIntInput(sc, "your choice");
    		if (officer.getCanRegister()) { // if officer is unassigned
    			switch(choice)
    			{
    			//Print all projects
    			case 1 -> officer.viewAllProjects(); 
    	        case 2 -> { // apply to handle project
    	        	String projectName = GetInput.inputLoop("the Project Name",sc,s->s,s->ProjectDatabase.findByName(s)!=null);
    	        	
    	        	if (!ProjectController.checkOfficerHandleEligibility(projectName, officer)) {
                		System.out.println("You cannot apply for this project. You have an existing application as an applicant.");
                		break;
                	}
    	        	
    	        	officer.applyForProject(projectName);
    	        	System.out.println("Successfully applied for project.");
    	        }
    	        case 3 -> officer.viewApplications(); // view all officer applications
    	        case 4 -> System.out.println("Returning to main menu...");
    	        default -> View.invalidChoice();
    			}
    		} else { // if officer has an active project
    			switch(choice)
    			{
    			//Print all projects
    			case 1 -> {
    				Project project = officer.getActiveProject();
    				System.out.println("----- Assigned Project Details -----");
    				System.out.println(project);
    				System.out.println("------------------------");
    			}
    	        case 2 -> { // view assigned projects applications
    	        	String projectName = officer.getActiveProject().getName();
    	        	List<ProjectApplication> applications = ProjectApplicationDatabase.getApplicationsByProjectName(projectName);
    	        	if (applications.size() <= 0) {
    	        		System.out.println("There are no applications for this project.");
    	        	} else {
    	        		System.out.println("----- Applications for " + projectName + " -----");
    	        		for (ProjectApplication application : applications) {
    	        			System.out.println("Applicant: " + application.getApplicantId());
    	        			System.out.println("Marital Status: " + application.getMaritalStatus());
    	        			System.out.println("Age: " + application.getAge());
    	        			System.out.println("Flat Type: " + application.getFlatType());
    	        			System.out.println("Status: " + application.getApplicationStatus());
    	        			System.out.println("------------------------------------");
    	        		}
    	        	}
    	        }
    	        case 3 -> { // book flat
    	        	String applicantId = GetInput.getLineInput(sc,  " Applicant's NRIC");
    	        	ProjectApplication application = ProjectApplicationDatabase.getApplicationByApplicantId(applicantId);
    	        	
    	        	if (application == null) {
    	        		System.out.println("Application not found.");
    	        	} else if (application.getApplicationStatus().equals(ApplicationStatus.BOOKREQ.getStatus())) { // include status check
    	        		officer.bookFlat(application);
    	        		System.out.println("Flat successfully booked.");
    	        		officer.generateReceipt(application);
    	        	} else {
    	        		System.out.println("Booking has not been requested.");
    	        	}
    	        }
    	        case 4 -> System.out.println("Returning to main menu...");
    	        default -> View.invalidChoice();
    			}
    		}
    	}
    	while(choice!=4);
    }

}
