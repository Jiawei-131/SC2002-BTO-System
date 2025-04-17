package entities;
import java.util.Arrays;
import java.util.List;

import java.util.Scanner;

import controllers.AuthenticationController;
import controllers.EnquiryController;
import data.EnquiryDatabase;
import util.Role;
import view.View;
public class Officer extends Applicant {
private boolean isVisible;
//private Project assignedProject;
private boolean registrationStatus;
private boolean canRegister;


public Officer(String name, String nric, int age, String maritalStatus, String password, boolean isVisible,AuthenticationController ac,Role role)
{
    super(name, nric, age, maritalStatus, password,isVisible,ac,role);
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
public boolean viewRegistrationStatus()
{
    return registrationStatus;
}
//public String viewProjectDetails(Project project){
//    return "hi";
//}
//public String viewEnquiry(Enquiry enquiryList)
//{
//
//}

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
public void generateReceipt(Applicant applicant)
{

}
public boolean getCanRegister()
{
    return true;
}

public String getNric() {
    return this.nric;
}

public void setCanRegister(boolean canRegister)
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
    return Arrays.asList(
    		"1. View list of projects",
		 	"2. Apply for project",
    		"3. View applied projects",
    		"4. Withdraw from BTO Application",
    		"5. Generate Receipt",
    		"6. Check Status",
			"7. Back to Main Menu"
    );
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
