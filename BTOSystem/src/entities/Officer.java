package entities;
import java.util.Arrays;
import java.util.List;

import java.util.Scanner;

import controllers.AuthenticationController;
import controllers.EnquiryController;
import controllers.ProjectController;
import data.EnquiryDatabase;
import data.OfficerApplicationDatabase;
import data.ProjectApplicationDatabase;
import data.ProjectDatabase;
import data.UserDatabase;
import handlers.ManagerProjectService;
import util.ApplicationStatus;
import util.Role;
import view.View;
public class Officer extends Applicant {
private boolean isVisible;
private Project assignedProject=null;
private boolean registrationStatus;
private boolean canRegister=true;


public Officer(String name, String nric, int age, String maritalStatus, String password, boolean isVisible,AuthenticationController ac,Role role)
{
    super(name, nric, age, maritalStatus, password,isVisible,ac,role);
}

public Project getActiveProject()
{
	ProjectController pc=new ProjectController();
		   List<OfficerApplication> projects = OfficerApplicationDatabase.readApplication();
		   for(OfficerApplication project:projects)
		   {
				if(this.getNric().equals(project.getApplicantId())&&project.getApplicationStatus().equals("Approved"))
				{
					assignedProject=pc.getProject(project.getProjectName());
					setCanRegister(false);
				}
		   }
	return assignedProject;
}

public void displayMenu(){
View.menu(this,this.getMenuOptions());
}

public void displayChoice()
{
	View.menu(this, getRoleOptions());
}
//public void registerForProject(Project project)
//{
////     Able to register to join a project if the following criteria are meant: 
//// o No intention to apply for the project as an Applicant (Cannot apply 
//// for the project as an Applicant before and after becoming an HDB 
//// Officer of the project) 
//// o Not a HDB Officer (registration not approved) for another project 
//// within an application period (from application opening date, 
//// inclusive, to application closing date, inclusive) 
// //   assignedProject=project;
//}
public void viewRegistrationStatus()
{
	ProjectController pc=new ProjectController();
	   List<OfficerApplication> projects = OfficerApplicationDatabase.readApplication();
	   for(OfficerApplication project:projects)
	   {
			if(this.getNric().equals(project.getApplicantId()))
			{
                System.out.println(project.getApplicantId() + " - " + project.getApplicationStatus() + 
               		 " - " +project.getProjectName());
			}
	   }
}
//public String viewProjectDetails(Project project){
//    return "hi";
//}
//public String viewEnquiry(Enquiry enquiryList)
//{
//

public void viewAllProjects() {
	List<Project> projects = this.sort();
	
	for (Project project : projects) {
        if (Project.isVisibleToApplicant()) {
            View.displayProjectDetails(this, project);
        }
    }
	
}


public void applyForProject(String projectName) {
	if (ProjectController.checkOfficerHandleEligibility(projectName, this)) {
		OfficerApplication application = new OfficerApplication(this.nric, projectName);
		OfficerApplicationDatabase.writeApplication(application);
	}
}

public void viewApplications() {
	List<OfficerApplication> applications = OfficerApplicationDatabase.getApplicationsByApplicantId(this.nric);
	
	if (applications == null) {
		System.out.println("You have no past applications!");
	} else {
		System.out.println("\n ----- Your Applications -----");
		for (OfficerApplication application : applications) {
			System.out.println(application);
			System.out.println("------------------------------");
		}
	}
}

public void bookFlat(ProjectApplication application) {
	application.setApplicationStatus(ApplicationStatus.BOOKED.getStatus());
	ProjectApplicationDatabase.updateApplication(application);
	
	Project project = ProjectDatabase.findByName(application.getProjectName());
	
	if (application.getFlatType().equals("2-Room")) {
		project.setNumberOfType1Units(project.getNumberOfType1Units()-1);
	} else {
		project.setNumberOfType2Units(project.getNumberOfType2Units()-1);
	}
	ProjectDatabase.update(project);
}

public void generateReceipt(ProjectApplication application) {
	Project project = ProjectDatabase.findByName(application.getProjectName());
	AuthenticationController ac = new AuthenticationController();
	
	System.out.println("===== Receipt ======");
	System.out.println("Project Details:");
	System.out.println(project);
	System.out.println("\n");
	System.out.println("Applicant Name: " + UserDatabase.getUserById(application.getApplicantId(), ac).getUsername());
	System.out.println("Applicant NRIC: " + application.getApplicantId());
	System.out.println("Age: " + application.getAge());
	System.out.println("Marital Status: " + application.getMaritalStatus());
	System.out.println("Flat Type Booked: " + application.getFlatType());
	System.out.println("===== END =====");
	
	
}

public void viewEnquiries(EnquiryController controller) {
    String myNRIC = this.getNric();
    List<Enquiry> enquiries = controller.getAllEnquiries();

    boolean found = false;
    System.out.println("\n--- Enquiries for Your Project ---");
    for (Enquiry e : enquiries) {
        if (e.getOfficerNRIC().equals(myNRIC)) {
            System.out.println(e);
            System.out.println("------------------");
            found = true;
        }
    }

    if (!found) {
        System.out.println("No enquiries found for your project.");
    }
}

public void replyToEnquiry(Scanner sc, EnquiryController controller) {
    System.out.print("Enter Enquiry ID to reply: ");
    int id = Integer.parseInt(sc.nextLine());
    Enquiry e = controller.findEnquiryById(String.valueOf(id));

    if (e == null || !e.getOfficerNRIC().equals(this.getNric())) {
        System.out.println("You do not have access to this enquiry or it does not exist.");
        return;
    }

    System.out.print("Enter your reply: ");
    String reply = sc.nextLine();
    e.replyEnquiry(id, reply);
    boolean updated = EnquiryDatabase.update(e);
    System.out.println(updated ? "Reply sent." : "Failed to send reply.");
}

public void updateApplicantProfile(Applicant applicant)
{

}
public boolean getCanRegister()
{
    return canRegister;
}

public String getNric() {
    return this.nric;
}

private void setCanRegister(boolean canRegister)
{
    this.canRegister=canRegister;
}
public void updateApplicantStatus(ProjectApplication application){
    
}

public List<String> getRoleOptions()
{
	return Arrays.asList("1. Applicant","2. Officer");
}

public List<String> getProjectOptions() {
//	if(canRegister==false)
//	{
//		return Arrays.asList(
//			    String.format("Project Officer for %s", assignedProject.getName()),
//			    "1. View list of projects",
//			    "2. Apply for project",
//			    "3. View applied projects",
//			    "4. Withdraw from BTO Application",
//			    "5. Generate Receipt",
//			    "6. Check Status",
//			    "7. Back to Main Menu"
//			);
//
//	}
//	else {
//	    return Arrays.asList(
//	    		"1. View list of projects",
//			 	"2. Apply for project",
//	    		"3. View applied projects",
//	    		"4. Withdraw from BTO Application",
//	    		"5. Generate Receipt",
//	    		"6. Check Status",
//				"7. Back to Main Menu"
//	    );
//	}
	return Arrays.asList(
			"Please choose which role you want to access the system as:",
			"1. Applicant",
			"2. Officer",
			"3. Back to Main Menu"
			);

}

public List<String> getProjectApplicantOptions() {
	return Arrays.asList(
			"1. Applicant",
			"2. Officer",
			"3. Back to Main Menu"
			);
	
	// maybe can just use applicants options and implementations?
}

public List<String> getProjectOfficerOptions() {
	if(canRegister==false)
	{
		return Arrays.asList(
			    String.format("Project Officer for %s", assignedProject.getName()),
			    "1. View assigned project details",
			    "2. View applications",
			    "3. Book Flat",
			    "4. Back to Main Menu"
			);

	}
	else {
	    return Arrays.asList(
	    		"1. View list of projects",
			 	"2. Apply for project", // apply as officer
	    		"3. View applied projects", // see all applications as officer
				"4. Back to Main Menu"
	    );
	}
}

//public List<String> getEnquiryOptions() {
//    return Arrays.asList(
//			"1. Submit Enquiry",
//			"2. View Enquiry",
//			"3. Edit Enquiry",
//			"4. Delete Enquiry",
//			"5. Back to Main Menu"
//    );
//}

// Able to view and reply to enquiries regarding the project he/she is handling
@Override
public List<String> getEnquiryOptions() {
    return Arrays.asList(
        "1. View enquiries for my project",
        "2. Reply to an enquiry",
        "3. Back to Main Menu"
    );
}

}
