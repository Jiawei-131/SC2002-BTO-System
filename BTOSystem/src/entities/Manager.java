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

    public void createProject(String name, String neighbourhood, int unitType1, int unitType2, String openingDate, String closingDate
    		,int availableSlots,double type1SellingPrice,double type2SellingPrice) 
    {
    	new Project(name, neighbourhood,unitType1,type1SellingPrice, unitType2,type2SellingPrice,
    			openingDate, closingDate,this.getNric(),availableSlots,true);
    	System.out.println("Project Created!");
    }

    public void handleProject(Project project)
    {
    	assignedProject=project;
    	hasProject=true;
    }
    
    public void editProject(String name, String neighbourhood, int roomType, int numberOfUnits, String openingDate, String closingDate, Manager manager, int availableSlots) {
        // TODO: edit project
    }

    public void deleteProject(String BTOname,Project project) {
        // TODO: delete project and unassign project remove from db straight?
    	project.deleteFromDatabase(BTOname);
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
    		
	List<Project>projects=Project.loadAllProjects();
	for(Project project:projects)
	{
		if(type==1)//Print all project
		{
			project.displayProjectDetails();
		}
		else {//Print only projects that the manager created
			if(this.getNric().equals(project.getManager()))
			{
				project.displayProjectDetails();
			}
		}
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
				"3. Create BTO Project listing",
				"4. Delete BTO Project listing",
				"5. Edit BTO Project listing ",
				"6. Generate report",
				"7. Back to Main Menu"
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
//    public String viewEnquiry(Enquiry[] enquiryList ) {
//        // TODO implement
//    }
//
//    public void replyEnquiry(Project assignedProject) {
//        // TODO implement
//    }
}
