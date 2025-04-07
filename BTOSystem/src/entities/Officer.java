package entities;

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

//public User handleChoice(int choice,View view,Scanner sc) {
//    do {
//    this.displayMenu(view);
//    choice=sc.nextInt();
//    switch(choice)
//    {	
//
//    case 1:do {
//    		view.projectMenu(this);
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
//    break;
//    case 2:
//    break;
//    case 3:
//    break;
//    case 4:
//    break;
//    case 5:
//    break;
//    case 6:
//    break;
//    case 7:this.logout();
//    break;
//    default: System.out.println("Please enter a valid choice!");
//    }
//    }
//    while(this.isLogin()!=false);
//    return this;
//}

public void displayMenu(){
View.menu(this);
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

}
