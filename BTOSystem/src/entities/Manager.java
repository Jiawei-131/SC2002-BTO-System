package entities;

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
//
//    public User handleChoice(int choice,View view,Scanner sc) {
//        do {
//        this.displayMenu(view);
//        choice=sc.nextInt();
//        switch(choice)
//        {	
//
//        case 1:do {
//        		view.projectMenu(this);
//    			choice=sc.nextInt();
//    			switch(choice)
//    			{
//    			case 1:
//		        break;
//    	        case 2:
//    	        break;
//    	        case 3:
//    	        break;
//    	        case 4:
//    	        break;
//    	        case 5:
//    	        break;
//    			}}while(choice!=5);
//        break;
//        case 2:
//        break;
//        case 3:
//        break;
//        case 4:
//        break;
//        case 5:
//        break;
//        case 6:
//        break;
//        case 7:
//        break;
//        case 8:this.logout();
//        break;
//        default: System.out.println("Please enter a valid choice!");
//        }
//        }
//        while(this.isLogin()!=false);
//        return this;
//    }
    
    public void displayMenu() {
        View.menu(this);
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

    public void approveApplication(Application application) {
        // TODO implement
    }

    public void rejectApplication(Application application) {
        // TODO implement
    }

    public void approveApplicantWithdrawal(Application application) {
        // TODO implement
    }
    
    public void rejectApplicantWithdrawal(Application application) {
        // TODO implement
    }

    public void generateReport(int filter) {
        // TODO implement
    }


//    public String viewEnquiry(Enquiry[] enquiryList ) {
//        // TODO implement
//    }
//
//    public void replyEnquiry(Project assignedProject) {
//        // TODO implement
//    }
}
