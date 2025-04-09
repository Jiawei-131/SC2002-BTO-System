package entities;

import java.util.Scanner;
import java.util.Arrays;
import java.util.List;
import data.*;

import controllers.AuthenticationController;
import view.View;


public class Applicant extends User {
    private boolean isVisible;
//    private Application application;
    private String flat;
//    private Enquiry enquiry;

    public Applicant(String name, String nric, int age, String maritalStatus, String password, boolean isVisible,AuthenticationController ac,Role role) {
        super(name, nric, age, maritalStatus, password,ac,role);
        this.isVisible = isVisible;
        // appliedProject and flat to be null on fresh instance (?)

    }

    public void displayMenu() {
    	View.menu(this,this.getMenuOptions());
    }

    public boolean getIsVisible(){
        return this.isVisible;
    }

    public void setIsVisible(boolean isVisible) {
        this.isVisible = isVisible;
    }

    public void applyForProject(String projectName, String flatType) {
        // TODO: create new Application instance with project
    	ProjectApplicationDatabase db = new ProjectApplicationDatabase();
    	ProjectApplication application = new ProjectApplication(this.nric, projectName, flatType);
    	
    	db.writeApplication(application);
    }

    
    public void viewProjects() {
        // TODO: print application or smth
    }

    
    private ProjectApplication retrieveApplication() {

    	// bad code but..
    	// TODO if able, refine code?
    	ProjectApplicationDatabase db = new ProjectApplicationDatabase();

    	ProjectApplication application = db.getApplicationByApplicantId(this.nric);
    	
    	return application;
    }
    
    public void viewApplication() {
        // TODO: print application or smth
    	ProjectApplication application = this.retrieveApplication();
    	
    	System.out.printf("""
    			APPLICATION
    			Project Name: %s
    			Flat Type: %s
    			Status: %s
    			Managing Officer: %s
    			
    			""", application.getProjectName(), application.getFlatType(), application.getApplicationStatus(), application.getManagingOfficer());
    	
    }

    public void requestWithdrawal() {
        ProjectApplication application = this.retrieveApplication();
        application.setApplicationStatus("Withdraw Requested");
    }

    public void createEnquiry(String enquiry) {
        // TODO: waiting on Enquiry class
    }

    public void viewEnquiry() {
        // TODO: idk what this is
    }

    public void editEnquiry(int enquiryID, String enquiry) {
        // TODO: waiting on Enquiry
    }

    public void deleteEnquiry(int enquiryID) {
        // TODO: waiting on Enquiry
    }
    
//    public List<String> getMenuOptions() {
//        return Arrays.asList(
//            "1. Projects",
//            "2. Enquiries",
//            "3. Logout"
//        );     
//    }
    public List<String> getProjectOptions() {
        return Arrays.asList(
        		"1. View list of projects",
    		 	"2. Apply for projects",
        		"3. View applied projects",
        		"4. Withdraw from BTO Application",
				"5. Back to Main Menu",
				"Please enter a choice:"
        );
    }
    public List<String> getEnquiryOptions() {
        return Arrays.asList(
				"1. Submit Enquiry",
				"2. View Enquiry",
				"3. Edit Enquiry",
				"4. Delete Enquiry",
				"5. Back to Main Menu",
				"Please enter a choice:"
        );
    }

}
