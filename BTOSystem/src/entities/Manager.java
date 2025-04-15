package entities;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import entities.*;
import util.GetInput;
import controllers.AuthenticationController;
import controllers.DateTimeController;
import data.ProjectApplicationDatabase;
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
    
    public void editProject(Scanner sc) {
        // TODO: edit project
 		String btoName=GetInput.getLineInput(sc, "the BTO Name");
 		if(Project.findByName(btoName).equals(null)||!this.getNric().equals(Project.findByName(btoName).getManager()))
		{
 			System.out.println("BTO Project does not exist or you do not have access.");
		}
 		else {
 			int choice;
 			Project project=Project.findByName(btoName);
 			do {
 				this.displayMenu(this.getBtoOptions());
 				choice=GetInput.getIntInput(sc,"the field to update");
 				switch(choice)
 				{
 				case 1 -> project.setNeighbourhood(GetInput.getLineInput(sc, "the new neighbourhood"));
 	            case 2 -> project.setNumberOfType1Units(GetInput.inputLoop("the new Number of 2 Room Units", sc, Integer::parseInt, i -> i > 0));
 	            case 3 -> project.setType1SellingPrice(GetInput.inputLoop("the new Price of 2 Room Units", sc, Double::parseDouble, i -> i > 0));
 	            case 4 -> project.setNumberOfType2Units(GetInput.inputLoop("the new Number of 3 Room Units", sc, Integer::parseInt, i -> i > 0));
 	            case 5 -> project.setType2SellingPrice(GetInput.inputLoop("the new Price of 3 Room Units", sc, Double::parseDouble, i -> i > 0));
 	            case 6 -> project.setOpeningDate(GetInput.inputLoop("the new Opening Date in DD-MM-YYYY format", sc, s -> s, s -> DateTimeController.isValidFormat(s) && DateTimeController.isAfter(s)));
 	            case 7 -> project.setClosingDate(GetInput.inputLoop("the new Closing Date in DD-MM-YYYY format", sc, s -> s, s -> DateTimeController.isValidFormat(s) && DateTimeController.isAfter(s, project.getOpeningDate())));
 	            case 8 -> project.setOfficerSlot(GetInput.inputLoop("the new Number of HDB Officer slots", sc, Integer::parseInt, i -> i <= 10 && i > 0));
 	            case 9 -> {
 	                int isVisibleChoice = GetInput.inputLoop("""
 	                        the visibility to applicants
 	                        1. Yes
 	                        2. No
 	                        """, sc, Integer::parseInt, i -> i == 1 || i == 2);
 	                project.setVisibleToApplicant(isVisibleChoice == 1);
 	            }
 				default->{}
 				}
 			}while (choice != 9);
 
 		}
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
    	ProjectApplicationDatabase db=new ProjectApplicationDatabase();
    	db.readApplication();
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
