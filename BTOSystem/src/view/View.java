package view;

import entities.Applicant;
import entities.Manager;
import entities.Officer;
import entities.User;
public class View {
	
	public View() {
	}
	

	public static void menuView() {
    	System.out.println("""
        		BTO Management System (BMS)
        		1. Login
        		2. Register
        		3. Exit
        		Please enter a choice:
        		""");
	}
	private static void printHeader(User user) {
		System.out.printf("BTO Management System (%s)\n",user.getRole());
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
	
	public static void menu(User user) {
		if (user instanceof Officer) {
			printHeader(user);		
			System.out.println("""
				1. View list of projects
				2. Apply for projects
				3. View applied projects
				4. Withdraw from BTO Application
				5. Enquiries
				6. Logout
				Please enter a choice:
				""");
        } 
		else if (user instanceof Applicant) {
			printHeader(user);	
			System.out.println("""
					1. View list of projects
					2. Apply for projects
					3. View applied projects
					4. Withdraw from BTO Application
					5. Enquiries
					6. Logout
					Please enter a choice:
					""");
        } 
        //Manager Project menu
		else if (user instanceof Manager) {
			printHeader(user);			
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
	
	public static void invalidChoice()
	{
		System.out.println("Please enter a valid choice");
	}

}
