package view;

import entities.Applicant;
import entities.Manager;
import entities.Officer;
import entities.User;
import java.util.List;

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
	public static void validPassword()
	{
    	System.out.println("""
        		Please enter a valid password that fufils these requirements
        		1. Length more than 8
        		2. Not default password
        		3. Not your old password
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

	public static void menu(User user,List<String> menuOptions) {
		printHeader(user);
		for(String option :menuOptions)
		{
			System.out.println(option);
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
	public static void projectMenu(User user,List<String> options)
	{
		printHeader(user);		
		for(String option :options)
		{
			System.out.println(option);
		}
	}
	public static void enquiryMenu(User user,List<String> options)
	{
		printHeader(user);	
		for(String option :options)
		{
			System.out.println(option);
		}
	}
	public static void invalidChoice()
	{
		System.out.println("Please enter a valid choice");
	}

}
