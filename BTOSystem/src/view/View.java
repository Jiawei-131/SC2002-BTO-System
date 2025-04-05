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

}
