package entities;

import java.util.Scanner;
import java.util.Arrays;
import java.util.List;
import data.*;

import controllers.AuthenticationController;
import data.UserDatabase;
import view.View;


public class Applicant extends User {
    private boolean isVisible;
    private Application application;
    private String flat;
//    private Enquiry enquiry;

    public Applicant(String name, String nric, int age, String maritalStatus, String password, boolean isVisible,AuthenticationController ac,Role role) {
        super(name, nric, age, maritalStatus, password,ac,role);
        this.isVisible = isVisible;
        // appliedProject and flat to be null on fresh instance (?)

    }

    public void displayMenu() {
    	View.menu(this);
    }

    public boolean getIsVisible(){
        return this.isVisible;
    }

    public void setIsVisible(boolean isVisible) {
        this.isVisible = isVisible;
    }

    public void applyForProject(String projectName, String flatType) {
        // TODO: create new Application instance with project
    	ApplicationDatabase db = new ApplicationDatabase();
    	Application application = new Application(this.nric, projectName, flatType);
    	
    	db.writeApplication(application);
    }

    
    public void viewProjects() {
        // TODO: print application or smth
    }

    
    private Application retrieveApplication() {

    	// bad code but..
    	// TODO if able, refine code?
    	ApplicationDatabase db = new ApplicationDatabase();

    	Application application = db.getApplicationByApplicantId(this.nric);
    	
    	return application;
    }
    
    public void viewApplication() {
        // TODO: print application or smth
    	Application application = this.retrieveApplication();
    	
    	System.out.printf("""
    			APPLICATION
    			Project Name: %s
    			Flat Type: %s
    			Status: %s
    			Managing Officer: %s
    			
    			""", application.getProjectName(), application.getFlatType(), application.getApplicationStatus(), application.getManagingOfficer());
    	
    }

    public void requestWithdrawal() {
        // TODO: depends on Application class
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
    
    public List<String> getMenuOptions() {
        return Arrays.asList(
            "1. Projects",
            "2. Enquiries",
            "3. Logout"
        );
    }

}
