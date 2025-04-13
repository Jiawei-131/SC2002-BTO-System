package entities;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import entities.*;


import controllers.AuthenticationController;
import view.View;
public class Manager extends User {
    private Project assignedProject;
    private boolean hasProject;
	private boolean isVisible;
	
    public Manager(String name, String nric, int age, String maritalStatus, String password, boolean isVisible,AuthenticationController ac,Role role) {
        super(name, nric, age, maritalStatus, password,ac,role);
        this.isVisible=isVisible;
    }
 
    public void displayMenu(List<String> options) {
        View.menu(this,options);
    }

    public void createProject(String name, String neighbourhood, int unitType1, int unitType2, String openingDate, String closingDate, Manager manager, int availableSlots,boolean isVisible) {
        // TODO: construct and assign project
//    	assignedProject = new Project(name, neighbourhood,unitType1, unitType2,
//    			openingDate, closingDate,manager,null,availableSlots,
//    			false, null, null);
//    	System.out.println("Project Created!");
    }
    // poosible to use this >
//    String name, String neighbourhood, 
//    int numberOfType1Units, double type1SellingPrice,
//    int numberOfType2Units, double type2SellingPrice,
//    String openingDate, String closingDate,
//    Manager manager, int officerSlot

    public void handleProject(Project project)
    {
    	assignedProject=project;
    	hasProject=true;
    }
    
    public void editProject(String name, String neighbourhood, int roomType, int numberOfUnits, String openingDate, String closingDate, Manager manager, int availableSlots) {
        // TODO: edit project
    }

    public void deleteProject() {
        // TODO: delete project and unassign project remove from db straight?
//    	assignedProject.Delete?
    	hasProject=false;
    }

    public void toggleVisibility() {
        assignedProject.setIsVisible(!this.isVisible);
    }

    public void viewProjects(int type) {
        // TODO
        /* Able to view all created projects, including projects created by other 
    	HDB Manager, regardless of visibility setting. */
    	if(type==1)
    	{
    		//TODO Print all project
    	}
    	else
    	{
    		//TODO Print only projects that the manager created
    	}
    		
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
            "2. Approvals",
            "3. Enquiries",
            "4. Change Password",
            "5. Logout"
        );
    }
    
    public List<String>getApprovalOption()
    {
    	return Arrays.asList(
				"1. Approve or reject HDB Officer’s registration",
				"2. Approve or reject Applicant’s BTO application",
				"3. Approve or reject Applicant's request to withdraw the application.",
				"4. Back to Main Menu"
				);
    }
    public List<String> getProjectOptions() {
        return Arrays.asList(
				"1. View All Project listings",
				"2. View My Project listings",
				"3. Toggle Visibility of Project",
				"4. Create BTO Project listings",
				"5. Delete BTO Project listings",
				"6. Edit BTO Project listings ",
				"7. Generate report",
				"8. Back to Main Menu"
        );
    }
    public List<String> getEnquiryOptions() {
        return Arrays.asList(
				"1. View enquiry of all projects",
				"2. View enquiries of my project",
				"3. Reply to enquiry",
				"4. Delete enquiry",
				"5. Back to Main Menu"
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
