package view;

import entities.Applicant;
import entities.Manager;
import entities.Officer;
import entities.User;
public class View {
	
	public View() {
	}
	

	public static void loginView() {
    	System.out.println("""
        		BTO Management System (BMS)
        		1. Login
        		2. Register
        		3. Exit
        		Please enter a choice:
        		""");
	}
	private static void printHeader(User user) {
        System.out.println("===================================");
        System.out.println(" Welcome, " + user.getUsername());
        System.out.printf(" Role: %s \n", user.getRole());
        System.out.println("===================================");
	}
	
	public static void register() {
    	System.out.println("Sorry no registration allowed !");
	}
	public static void exit() {
    	System.out.println("Goodbye!");
	}
	public static void prompt(String string)
	{
		System.out.printf("Please enter your %s: \n",string);
	}

	public static void promptRetry(String string) {
    	System.out.printf("%s\n",string);
        System.out.println("1: Retry\n2: Exit");
	}
//	1. View list of projects
//	2. Apply for projects
//	3. View applied projects
//	4. Withdraw from BTO Application
//	5. Generate Receipt
	public static void menu(User user) {
		printHeader(user);	
		if (user instanceof Officer) {
	
			System.out.println("""
				1. Projects
				2. Enquiries
				3. Logout
				Please enter a choice:
				""");
        } 
//		1. View list of projects
//		2. Apply for projects
//		3. View applied projects
//		4. Withdraw from BTO Application

		else if (user instanceof Applicant) {
			System.out.println("""
					1. Projects
					2. Enquiries
					3. Logout
					Please enter a choice:
					""");
        } 
        //Manager Project menu
		else if (user instanceof Manager) {			
			System.out.println("""
				1. Project Details
				2. Toggle Visibility
				3. Approvals
				4. Enquiries
				5. Logout
				Please enter a choice:
				""");
		}
	}
	
	public static void approvalMenu(User user)
	{
		printHeader(user);	
		System.out.println("""
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
	public static void projectMenu(User user)
	{
		printHeader(user);		
		System.out.println("""
				1. View All Project listings
				2. Create BTO Project listings
				3. Delete BTO Project listings
				4. Edit BTO Project listings 
				5. Back to Main Menu
				Please enter a choice:
				""");
	}
	public static void enquiryMenu(User user)
	{
		printHeader(user);	
		System.out.println("""
				1. Submit Enquiry
				2. View Enquiry
				3. Edit Enquiry
				4. Delete Enquiry
				5. Back to Main Menu
				Please enter a choice:
				""");
	}
	public static void invalidChoice()
	{
		System.out.println("Please enter a valid choice");
	}

}
