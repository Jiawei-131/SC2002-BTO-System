package entities;
import java.util.Arrays;
import java.util.List;

import java.util.Scanner;

import controllers.AuthenticationController;
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
public void replyEnquiry(){

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
public void setCanRegister(boolean canRegister)
{
    this.canRegister=canRegister;
}
public void updateApplicantStatus(Application application){
    
}
//public List<String> getMenuOptions() {
//    return Arrays.asList(
//        "1. Projects",
//        "2. Enquiries",
//        "3. Logout"
//    );
//}
public List<String> getProjectOptions() {
    return Arrays.asList(
    		"1. View list of projects",
		 	"2. Apply for projects",
    		"3. View applied projects",
    		"4. Withdraw from BTO Application",
    		"5. Generate Receipt",
			"6. Back to Main Menu",
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
}
