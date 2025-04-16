package entities;

import java.util.Scanner;
import java.util.Arrays;
import java.util.List;
import data.*;

import controllers.AuthenticationController;
import view.ProjectView;
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
    	ProjectApplication application = new ProjectApplication(this.nric, this.maritalStatus, projectName, flatType);
    	
    	ProjectApplicationDatabase.writeApplication(application);
    	appliedProject=projectName;
    }

    
    public void viewProjects(Applicant applicant) {
        /* TODO: Show based on applicant details
    	Singles, 35 years old and above, can ONLY apply for 2-Room 
    	o Married, 21 years old and above, can apply for any flat types (2
    			Room or 3-Room) */
    	List<Project> projects = this.sort();
    	ProjectView projectView = new ProjectView();
    	System.out.println("Available projects: " + projects.size());
        for (Project project : projects) {
            if (Project.isVisibleToApplicant()) {
                View.displayProjectDetails(project);
            }
        }
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
    			Application Details
    			Project Name: %s
    			Flat Type: %s
    			Status: %s
    			
    			""", application.getProjectName(), application.getFlatType(), application.getApplicationStatus());
    	
    }

    public void requestWithdrawal() {
        ProjectApplication application = this.retrieveApplication();
        application.setApplicationStatus("Withdraw Requested");
    }

    public void createEnquiry(String text) {
        // TODO: waiting on Enquiry class
//    	enquiry=new Enquiry(text,this.nric,this.appliedProject);
    }

    public void viewEnquiry() {
        // TODO: idk what this is
//    	enquiry.displayDetails(this);
    }

    public void editEnquiry(int enquiryID, String newText) {
        // TODO: waiting on Enquiry
//    	enquiry.editEnquiry(newText);
    }

    public void deleteEnquiry(int enquiryID) {
        // TODO: waiting on Enquiry
//    	enquiry.deleteEnquiry();
    }
    
    public List<String> getProjectOptions() {
        return Arrays.asList(
        		"1. View list of projects",
    		 	"2. Apply for project",
        		"3. View applied project",
        		"4. Withdraw from BTO Application",
				"5. Back to Main Menu"
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
