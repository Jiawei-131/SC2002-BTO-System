package view;

import entities.Applicant;
import entities.Enquiry;
import entities.Manager;
import entities.Officer;
import entities.Project;
import entities.User;
import java.util.List;
import java.util.Scanner;

import controllers.EnquiryController;

public class View {
	
	private EnquiryController controller;
    private Scanner scanner;
	
	public View() {
	}
	

	public static void loginView() {
    	System.out.println("""
        		BTO Management System (BMS)
        		1. Login
        		2. Register
        		3. Exit
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
	public static void validNRIC()
	{
    	System.out.println("Please enter a valid NRIC");
	}
	private static void printHeader(User user) {
        System.out.println("===================================");
        System.out.println(" Welcome, " + user.getUsername());
        System.out.printf(" Role: %s \n", user.getRole());
        System.out.printf(" Filter Setting: %s \n", user.getFilter());
        System.out.println("===================================");
	}
	
	public static void register() {
    	System.out.println("Enter your NRIC");
	}
	public static void exit() {
    	System.out.println("Goodbye!");
	}
	public static void prompt(String string)
	{
		System.out.printf("Please enter %s: \n",string);
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
		
	public static void invalidChoice()
	{
		System.out.println("Please enter a valid choice");
	}
	
    public static void displayProjectDetails(User user, Project project) {
        if (user instanceof Applicant && !Project.isVisibleToApplicant()) {
            System.out.println("You do not have access to view this project.");
            return;
        }
        System.out.println("\n=== Project Details ===");
        System.out.println("Name: " + project.getName());
        System.out.println("Neighborhood: " + project.getNeighbourhood());
        System.out.println("Type 1 Units: " + project.getNumberOfType1Units() + 
            " (Price: $" + String.format("%.2f", project.getType1SellingPrice()) + ")");
        System.out.println("Type 2 Units: " + project.getNumberOfType2Units() + 
            " (Price: $" + String.format("%.2f", project.getType2SellingPrice()) + ")");
        System.out.println("Application Period: " + project.getOpeningDate() + 
            " to " + project.getClosingDate());
        System.out.println("Manager: " + project.getManager());
        System.out.println("Officers: " + project.getOfficersInCharge().size() + 
            "/" + project.getOfficerSlot());
        for (Officer officer : project.getOfficersInCharge()) {
            System.out.println("  - " + officer.getUsername());
        }
        System.out.println("Visibility: " + 
            (Project.isVisibleToApplicant() ? "Visible to applicants" : "Hidden from applicants"));
    }

    public static void displayAllProjects(List<Project> projects) {
        System.out.println("\n=== All Projects ===");
        for (Project project : projects) {
            System.out.println(project.getName() + " - " + project.getNeighbourhood() + 
                " (" + (Project.isVisibleToApplicant() ? "Visible" : "Hidden") + ")");
        }
    }


}
