package entities;

import java.util.Scanner;

import controllers.AuthenticationController;
import view.View;


public class Applicant extends User {
    private boolean isVisible;
    private Application application;
    private String flat;
    private Role role;
//    private Enquiry enquiry;

    public Applicant(String name, String nric, int age, String maritalStatus, String password, boolean isVisible,AuthenticationController ac,Role role) {
        super(name, nric, age, maritalStatus, password,ac,role);
        this.isVisible = isVisible;
        // appliedProject and flat to be null on fresh instance (?)

    }
    
//    public User handleChoice(int choice,View view,Scanner sc) {
//        do {
//        this.displayMenu(view);
//        choice=sc.nextInt();
//        switch(choice)
//        {	
//
//        case 1:
//        break;
//        case 2:
//        break;
//        case 3:
//        break;
//        case 4:
//        break;
//        case 5:do {
//    		view.enquiryMenu(this);
//			choice=sc.nextInt();
//			switch(choice)
//			{
//			case 1:
//	        break;
//	        case 2:
//	        break;
//	        case 3:
//	        break;
//	        case 4:
//	        break;
//	        case 5:
//	        break;
//			}}while(choice!=5);
//        break;
//        case 6:this.logout();
//        break;
//        default: System.out.println("Please enter a valid choice!");
//        }
//        }
//        while(this.isLogin()!=false);
//        return this;
//    }
    public void displayMenu() {
    	View.applicantMenu(this);
    }

    public boolean getIsVisible(){
        return this.isVisible;
    }

    public void setIsVisible(boolean isVisible) {
        this.isVisible = isVisible;
    }

    public void applyForProject(Project project) {
        // TODO: create new Application instance with project
    }

    
    public void viewProjects() {
        // TODO: print application or smth
    }
    
    public void viewApplication() {
        // TODO: print application or smth
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

}
