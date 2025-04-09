package entities;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


import controllers.AuthenticationController;
import view.View;
public class Manager extends User {
//    private Project assignedProject;
    private boolean hasProject;
	private boolean isVisible;
	
    public Manager(String name, String nric, int age, String maritalStatus, String password, boolean isVisible,AuthenticationController ac,Role role) {
        super(name, nric, age, maritalStatus, password,ac,role);
        this.isVisible=isVisible;
    }

    
    public void displayMenu() {
        View.menu(this,this.getMenuOptions());
    }

    public void createProject(String name, String neighbourhood, int roomType, int numberOfUnits, String openingDate, String closingDate, Manager manager, int availableSlots) {
        // TODO: construct and assign project
    	System.out.println("Project Created!");
    }

    public void editProject(String name, String neighbourhood, int roomType, int numberOfUnits, String openingDate, String closingDate, Manager manager, int availableSlots) {
        // TODO: edit project
    }

    public void deleteProject(Project project) {
        // TODO: delete project and unassign project
    }

    public void toggleVisibility(Project project) {
        project.setIsVisible(!this.isVisible);
    }

    public void viewAllProjects() {
        // TODO
        // is it view all projects assigned to manager? or all all projects
    }

    public void approveOfficerRegistration(Officer officer) {
        // TODO implement
    }

    public void approveApplication(ProjectApplication application) {
        // TODO implement
    }

    public void rejectApplication(ProjectApplication application) {
        // TODO implement
    }

    public void approveApplicantWithdrawal(ProjectApplication application) {
        // TODO implement
    }
    
    public void rejectApplicantWithdrawal(ProjectApplication application) {
        // TODO implement
    }

    public void generateReport(int filter) {
        // TODO implement
    }
    public List<String> getMenuOptions() {
        return Arrays.asList(
            "1. Project Details",
            "2. Toggle Visibility",
            "3. Approvals",
            "4. Enquiries",
            "5. Change Password",
            "6. Logout",
            "Please enter a choice:"
        );
    }
    public List<String> getProjectOptions() {
        return Arrays.asList(
				"1. View All Project listings",
				"2. Create BTO Project listings",
				"3. Delete BTO Project listings",
				"4. Edit BTO Project listings ",
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
//    public String viewEnquiry(Enquiry[] enquiryList ) {
//        // TODO implement
//    }
//
//    public void replyEnquiry(Project assignedProject) {
//        // TODO implement
//    }
}
