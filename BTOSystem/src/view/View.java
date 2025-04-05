package view;
import java.io.Console;
public class View {
	
	private Console console;
	public View() {
	}
	

	public void menuView() {
    	System.out.println("""
        		BTO Management System (BMS)
        		1. Login
        		2. Register
        		3. Exit
        		Please enter a choice:
        		""");
	}
	public void register() {
    	System.out.println("Sorry no registration allowed !");
	}
	public void exit() {
    	System.out.println("Goodbye!");
	}
	public void promptNric()
	{
		System.out.println("Please enter your NRIC:");
	}
	public void promptPassword()
	{
		System.out.println("Please enter your Password:");
	}
	public void promptRetryNric() {
    	System.out.println("NRIC not found");
        System.out.println("1: Retry\n2: Exit");
	}
	public void promptRetryPassword() {
	    System.out.println("Incorrect password");
	    System.out.println("1: Retry\n2: Exit");
	}
	public void applicantMenu() {
		System.out.println("""
				BTO Management System (Applicant)
				1. View list of projects
				2. Apply for projects
				3. View applied projects
				4. Withdraw from BTO Application
				5. Submit Enquiry
				6. View/Edit/Delete Enquiry
				7. Logout
				Please enter a choice:
				""");
	}
	public void officerMenu() {
		System.out.println("""
				BTO Management System (Officer)
				1. View list of projects
				2. Apply for projects
				3. View applied projects
				4. Withdraw from BTO Application
				5. Submit Enquiry
				6. View/Edit/Delete Enquiry
				7. Logout
				Please enter a choice:
				""");
	}
	public void managerMenu() {
		System.out.println("""
				BTO Management System (Manager)
				1. View of created projects
				2. Create/edit/delete BTO Projects
				3. Toggle Visibility
				4. View pending and approved HDB Officer registration. 
				5. Approval
				6. View all enquiries
				7. Reply to enquiries
				8. Logout
				Please enter a choice:
				""");
	}
	public void approvalMenu()
	{
		System.out.println("""
				BTO Management System (Manager)
				1. Approve or reject HDB Officer’s registration as the HDB 
				Manager in-charge of the project – update project’s remaining HDB 
				Officer slots 
				2. Approve or reject Applicant’s BTO application – approval is 
				limited to the supply of the flats (number of units for the respective flat 
				types) 
				3. Approve or reject Applicant's request to withdraw the application. 
				4. Logout
				Please enter a choice:
				""");
	}

}
