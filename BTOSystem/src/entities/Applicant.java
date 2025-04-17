package entities;

import java.util.Scanner;
import java.util.Arrays;
import java.util.List;
import util.ApplicationStatus;
import util.Role;
import data.*;

import controllers.AuthenticationController;
import controllers.EnquiryController;
import view.View;


public class Applicant extends User {
    private boolean isVisible;
//    private Application application;
    private String flat;
    private Enquiry enquiry;
    private String appliedProject;

    public Applicant(String name, String nric, int age, String maritalStatus, String password, boolean isVisible,AuthenticationController ac,Role role) {
        super(name, nric, age, maritalStatus, password,ac,role);
        this.isVisible = isVisible;
        this.appliedProject=null;
        // appliedProject and flat to be null on fresh instance (?)
    }

    public void displayMenu(List<String> options) {
    	View.menu(this,options);
    }

    public boolean getIsVisible(){
        return this.isVisible;
    }

    public void setIsVisible(boolean isVisible) {
        this.isVisible = isVisible;
    }

    public void applyForProject(String projectName, String flatType) {
        // TODO: create new Application instance with project
    	
    	ProjectApplication application = new ProjectApplication(this.nric, this.maritalStatus, this.age, projectName, flatType);
    	
    	ProjectApplicationDatabase.writeApplication(application);
    	appliedProject=projectName;
    	
    	
    	// quantity should only be updated when flat is successfully booked (officer action)
//    	Project project = ProjectDatabase.findByName(projectName);
//    	
//    	if (flatType == "2-Room") {
//    		project.setNumberOfType1Units(project.getNumberOfType1Units()-1);
//    	} else {
//    		project.setNumberOfType2Units(project.getNumberOfType2Units()-1);
//    	}
//    	ProjectDatabase.update(project);
    }

    
    public void viewProjects(Applicant applicant) {
        /* TODO: Show based on applicant details
    	Singles, 35 years old and above, can ONLY apply for 2-Room 
    	o Married, 21 years old and above, can apply for any flat types (2
    			Room or 3-Room) */

		List<Project> projects = this.sort();
    	
    	if (applicant.getMaritalStatus().equals("Single")) {
    		if (applicant.getAge() < 35) {
    			System.out.println("Ineligible applicant. No projects available.");
    		} else {
    			for (Project project : projects) {
                    if (Project.isVisibleToApplicant() && project.getNumberOfType1Units() > 0) {
                        View.displayProjectDetails(applicant, project);
                    }
                }
    		}
        	
//        	System.out.println("Available projects: " + projects.size());
            
    	} else {
    		if (applicant.getAge() < 21) {
    			System.out.println("Ineligible applicant. No projects available.");
    		} else {
    			for (Project project : projects) {
                    if (Project.isVisibleToApplicant()) {
                        View.displayProjectDetails(applicant, project);
                    }
                }
    		}
    	}
    }


    private ProjectApplication retrieveApplication() {
    	ProjectApplication application = ProjectApplicationDatabase.getApplicationByApplicantId(this.nric);
    	
    	return application;
    }
    
    public void viewApplication() {
        // TODO: print application or smth
    	ProjectApplication application = this.retrieveApplication();
    	
    	System.out.printf("""
    			Application Details
    			Project Name: %s
    			Flat Type: %s
    			Status: %s
    			
    			""", application.getProjectName(), application.getFlatType(), application.getApplicationStatus());
    	
    }

    public void requestWithdrawal() {
        ProjectApplication application = this.retrieveApplication();
        application.setApplicationStatus(ApplicationStatus.WITHDRAWREQ.getStatus());
        
        ProjectApplicationDatabase.updateApplication(application);
    }
    
    public void requestFlatBooking() {
        ProjectApplication application = this.retrieveApplication();
        application.setApplicationStatus(ApplicationStatus.BOOKREQ.getStatus());
        
        ProjectApplicationDatabase.updateApplication(application);
    }

    public void createEnquiry(String projectName, String text, EnquiryController enquiryController) {
        Project project = ProjectDatabase.findByName(projectName);

        if (project == null || !Project.isVisibleToApplicant()) {
            System.out.println("You can only enquire about visible projects.");
            return;
        }

        enquiryController.addEnquiry(projectName, this.nric, text);
    }


	public void viewEnquiries(EnquiryController enquiryController) {
	    List<Enquiry> userEnquiries = enquiryController.getUserEnquiries(this.nric);
	    if (userEnquiries.isEmpty()) {
	        System.out.println("No enquiries found.");
	        return;
	    }

	    System.out.println("\nYour Enquiries:");
	    for (Enquiry e : userEnquiries) {
	        System.out.println(e);
	        System.out.println("------------------");
	    }
	}

	public void editEnquiry(String enquiryID, String newText, EnquiryController enquiryController) {
		// TODO: waiting on Enquiry
		boolean updated = enquiryController.editEnquiry(enquiryID, this.nric, newText);
		System.out.println(
				updated ? "Enquiry updated." : "Failed to update enquiry (ID not found or permission denied).");
	}

	public void deleteEnquiry(String enquiryID, EnquiryController enquiryController) {
		boolean deleted = enquiryController.deleteEnquiry(enquiryID, this.nric);
		System.out.println(
				deleted ? "Enquiry deleted." : "Failed to delete enquiry (ID not found or permission denied).");
	}
    
    public List<String> getProjectOptions() {
        return Arrays.asList(
        		"1. View list of projects",
    		 	"2. Apply for project",
        		"3. View applied project",
        		"4. Request Flat Booking",
        		"5. Withdraw from BTO Application",
				"6. Back to Main Menu"
        );
    }
    public List<String> getEnquiryOptions() {
        return Arrays.asList(
				"1. Submit Enquiry",
				"2. View Enquiry",
				"3. Edit Enquiry",
				"4. Delete Enquiry",
				"5. Back to Main Menu"
        );
    }

}
