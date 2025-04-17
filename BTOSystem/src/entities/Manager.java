package entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import entities.*;
import handlers.ManagerProjectService;
import util.ApplicationStatus;
import util.GetInput;
import util.Role;
import controllers.AuthenticationController;
import controllers.DateTimeController;
import controllers.EnquiryController;
import controllers.ProjectController;
import data.EnquiryDatabase;
import data.OfficerApplicationDatabase;
import data.ProjectApplicationDatabase;
import data.ProjectDatabase;
import view.View;
public class Manager extends User {
    private Project assignedProject;
    private boolean hasProject=false;
	private boolean isVisible;
	
	//Constructor
    public Manager(String name, String nric, int age, String maritalStatus, String password, boolean isVisible,AuthenticationController ac,Role role) {
        super(name, nric, age, maritalStatus, password,ac,role);
        this.isVisible=isVisible;
    }
    
    public void displayMenu(List<String> options) {
        View.menu(this,options);
    }

    //Project related implementations
    public void createProject(Scanner sc) 
    {
    	if(hasProject==true)
    	{
    		System.out.println("Already has an active project!");
    	}
    	else {
        	ManagerProjectService.createProject(this, sc);
        	System.out.println("Project Created!");
    	}
    }

    //Called at login to get active project for manager
    public Project getActiveProject()
    {
    	if(ProjectController.hasActiveProject(ProjectDatabase.loadAllProjects(), this))
    	{
    		hasProject=true;
    		assignedProject=ManagerProjectService.getActiveProject(this);
    	}
    	return assignedProject;
    }
    
    
    //Print active project
    public void viewActiveProject()
    {
    	if(hasProject==true)
    	{
    		View.displayProjectDetails(this, assignedProject);
    	}
    	else {
    		System.out.println("You do not have any active project");
    	}
    }
    
    public void editProject(Scanner sc) {
    	ManagerProjectService.editProject(this, sc);
    }

    public void deleteProject(Scanner sc) {
    	ManagerProjectService.deleteProject(this, sc);
    	if(hasProject==true)
    	{
    		assignedProject=null;
        	hasProject=false;
    	}

    }

    public void toggleVisibility() {
        assignedProject.setIsVisible(!this.isVisible);
    }

    public void viewProjects(int type) {
        // TODO
        /* Able to view all created projects, including projects created by other 
    	HDB Manager, regardless of visibility setting. */
    	ManagerProjectService.showProject(this, type);
    }
    public void generateReport(Scanner sc) {
    	ManagerProjectService.generateReport(this,sc);

    }

    
    //Approval implementation
    public void approveOfficerRegistration(OfficerApplication application) {
        // TODO implement get officer slot if no slot show approval failed
    	ProjectController pc = new ProjectController();
    	application.setApplicationStatus("Approved");
    	OfficerApplicationDatabase.updateApplication(application);
    	if(pc.assignOfficer(application.getProjectName(), application.getApplicantId())){
    		System.out.println("Approved");
    	}
    	else {
        	System.out.println("Approval failed");
    	}

    }
    public void rejectOfficerRegistration(OfficerApplication application) {
        // TODO implement remove officer slot
    	ProjectController pc = new ProjectController();
    	application.setApplicationStatus("Rejected");
    	OfficerApplicationDatabase.updateApplication(application);
    	pc.removeOfficer(application.getProjectName(), application.getApplicantId());
    }

    public void approveApplication(ProjectApplication application) {
    	ProjectController pc = new ProjectController();
    	if(application.getFlatType().equals("2-Room")&&pc.getProject(application.getProjectName()).getNumberOfType1Units()>0) {
        	application.setApplicationStatus("Successful");
        	ProjectApplicationDatabase.updateApplication(application);
    	}
    	else if(application.getFlatType().equals("3-Room")&&pc.getProject(application.getProjectName()).getNumberOfType2Units()>0)
    	{
        	application.setApplicationStatus("Successful");
        	ProjectApplicationDatabase.updateApplication(application);
    	}
    	else{
    		System.out.println("Approval Failed not enough supply of the flat");
    	}
    }

    public void rejectApplication(ProjectApplication application) {
    	ProjectController pc = new ProjectController();
    	application.setApplicationStatus("Unsuccessful");
    	ProjectApplicationDatabase.updateApplication(application);
//    	pc.getProject(application.getProjectName()).addOfficer(application.getApplicantId());
    }

    public void approveApplicantWithdrawal(ProjectApplication application) {
        // TODO implement
    	application.setApplicationStatus("Withdrawn");
    	ProjectApplicationDatabase.updateApplication(application);
    }
    
    public void rejectApplicantWithdrawal(ProjectApplication application) {
        // TODO implement
    	application.setApplicationStatus("Withdrawn Rejected");
    	ProjectApplicationDatabase.updateApplication(application);
    }
    

    
    // MENU options
    public List<String> getMenuOptions() {
        return Arrays.asList(
            "1. Project Details",
            "2. Approvals",
            "3. Enquiries",
            "4. Change Password",
            "5. Filter Settings",
            "6. Logout"
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
        		"3. View My Active Project",
        		"4. Create BTO Project listing",
        		"5. Delete BTO Project listing",
        		"6. Edit BTO Project listing ",
        		"7. Generate report",
        		"8. Back to Main Menu"
        );
    }
    public List<String> getEnquiryOptions() {
        return Arrays.asList(
				"1. View enquiry of all projects",
				"2. View enquiries of my project",
				"3. Reply to enquiry",
				"4. Back to Main Menu"
        );
    }
    
    public List<String> getReportFilterOptions() {
        return Arrays.asList(
        		"1. Project Name",
    			"2. Flat Type",
    			"3. Age",
    			"4. Marital Status"
        );
    }
    
    public List<String> getBtoOptions() {
        return Arrays.asList(
        		"1. the neighbourhood",
        		"2. the Number of 2 Room Units",
        		"3. the Price of 2 Room Units",
        		"4. the Number of 3 Room Units",
        		"5. the Price of 3 Room Units",
        		"6. the Opening Date in DD-MM-YYYY format",
        		"7. the Closing Date in DD-MM-YYYY format",
        		"8. the Number of HDB Officer slots",
        		"9. the Visibility",
        		"10.finish Editing"

        );
    }
    
    
    //Enquiry Implementations
    public void viewAllEnquiries(EnquiryController enquiryController, int choice) {
        List<Enquiry> allEnquiries = enquiryController.getAllEnquiries();
        boolean found = false;

        System.out.println("\n--- Enquiries ---");

        for (Enquiry e : allEnquiries) {
            if (choice == 1) {
                // Show all project enquiries
                System.out.println(e);
                System.out.println("------------------");
                found = true;
            } else if (e.getManagerNRIC().equals(this.getNric())) {
                // Show only enquiries for manager's project
                System.out.println(e);
                System.out.println("------------------");
                found = true;
            }
        }

        if (!found) {
            System.out.println("No enquiries found.");
        }
    }

    public void replyToEnquiry(Scanner sc, EnquiryController enquiryController) {
        System.out.print("Enter Enquiry ID to reply: ");
        int id = sc.nextInt();
        sc.nextLine(); // consume newline

        Enquiry enquiry = enquiryController.findEnquiryById(String.valueOf(id));

        if (enquiry == null) {
            System.out.println("Enquiry not found.");
            return;
        }

        if (!enquiry.getManagerNRIC().equals(this.getNric())) {
            System.out.println("You are not authorized to reply to this enquiry.");
            return;
        }

        System.out.print("Enter your reply: ");
        String reply = sc.nextLine();

        enquiry.replyEnquiry(id, reply);
        boolean success = EnquiryDatabase.update(enquiry);
        System.out.println(success ? "Reply submitted successfully." : "Failed to update enquiry.");
    }

}
