package handlers;

import java.util.InputMismatchException;
import java.util.Scanner;

import controllers.EnquiryController;
import entities.Applicant;
import entities.Project;
import entities.ProjectApplication;
import data.*;
import entities.User;
import util.*;
import view.View;
/**
 * Handles actions specific to an Applicant user, such as project applications and enquiries.
 */
public class ApplicantActionHandler implements ActionHandler,GetInput,PasswordReset {
    private final EnquiryController enquiryController = new EnquiryController();
    /**
     * Handles the main menu selection for an Applicant.
     *
     * @param choice       The menu choice selected by the user.
     * @param currentUser  The current user object.
     * @param sc           The scanner used for input.
     * @param db           The user database.
     * @return             The updated user object, or null if logged out.
     */
	public User handleAction(int choice,User currentUser, Scanner sc,UserDatabase db){
        switch(choice) {
      //Manager Officer menu
        case 1-> handleProjectAction(choice,currentUser,sc);
    	case 2 -> handleEnquiryAction(choice,currentUser,sc);
    	case 3 ->{
    		return(PasswordReset.resetPassword(sc, currentUser, db));
    	}
    	case 4->handleFilterAction(currentUser,sc);
    	case 5 ->{
        	return currentUser.logout();
    	}
    	default->View.invalidChoice();
        }
		return currentUser;
	}
	
    /**
     * Handles enquiry-related actions for the Applicant.
     *
     * @param choice       The menu choice for enquiry actions.
     * @param currentUser  The currently logged-in user.
     * @param sc           The scanner used for input.
     */
    public void handleEnquiryAction(int choice, User currentUser, Scanner sc) {
        String enquiry;
        String enquiryID;
        Applicant applicant = (Applicant) currentUser;

        do {
            applicant.displayMenu(applicant.getEnquiryOptions());
            choice = GetInput.getIntInput(sc, "your choice");
            
			switch (choice) {
                case 1 -> {
                	String projectName = GetInput.getLineInput(sc, "the project name you want to enquire about");
                	String enquiryText = GetInput.getLineInput(sc, "your enquiry");

                	applicant.createEnquiry(projectName, enquiryText, enquiryController);

                }
                case 2 -> applicant.viewEnquiries(enquiryController);
                case 3 -> {
                    enquiryID = GetInput.getLineInput(sc, "your Enquiry ID");
                    enquiry = GetInput.getLineInput(sc, "your new enquiry");
                    applicant.editEnquiry(enquiryID, enquiry, enquiryController);
                }
                case 4 -> {
                    enquiryID = GetInput.getLineInput(sc, "your Enquiry ID");
                    applicant.deleteEnquiry(enquiryID, enquiryController);
                }
                case 5 -> System.out.println("Returning to main menu...");
                default -> View.invalidChoice();
            }
        } while (choice != 5);
    }
	
	
    /**
     * Handles project-related actions for the Applicant such as applying, viewing,
     * requesting bookings and withdrawals.
     *
     * @param choice       The menu choice for project actions.
     * @param currentUser  The currently logged-in user.
     * @param sc           The scanner used for input.
     */
    public void handleProjectAction(int choice,User currentUser, Scanner sc) {
    	  try {
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
        	} else if (applicant.getMaritalStatus().equals("Single") && applicant.getAge() < 35) {
    			System.out.println("Ineligible applicant. No projects available.");
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
    }catch (RegistrationFailedException e) {
        System.out.println(e.getMessage());
    }
    }
    

    


}
