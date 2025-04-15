package view;

import entities.Project;
import entities.Officer;
import java.util.List;
import java.util.Scanner;

public class ProjectView {
    private Scanner scanner;

    public ProjectView() {
        this.scanner = new Scanner(System.in);
    }

    public static void displayProjectDetails(Project project) {
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
            (project.isVisibleToApplicant() ? "Visible to applicants" : "Hidden from applicants"));
    }

    public void displayAllProjects(List<Project> projects) {
        System.out.println("\n=== All Projects ===");
        for (Project project : projects) {
            System.out.println(project.getName() + " - " + project.getNeighbourhood() + 
                " (" + (project.isVisibleToApplicant() ? "Visible" : "Hidden") + ")");
        }
    }

    public Project promptForNewProject() {
        System.out.println("\n=== Create New Project ===");
        System.out.print("Enter project name: ");
        String name = scanner.nextLine();
        
        System.out.print("Enter neighborhood: ");
        String neighbourhood = scanner.nextLine();
        
        System.out.print("Enter number of Type 1 units: ");
        int type1Units = Integer.parseInt(scanner.nextLine());
        
        System.out.print("Enter Type 1 selling price: ");
        double type1Price = Double.parseDouble(scanner.nextLine());
        
        System.out.print("Enter number of Type 2 units: ");
        int type2Units = Integer.parseInt(scanner.nextLine());
        
        System.out.print("Enter Type 2 selling price: ");
        double type2Price = Double.parseDouble(scanner.nextLine());
        
        System.out.print("Enter opening date (YYYY-MM-DD): ");
        String openingDate = scanner.nextLine();
        
        System.out.print("Enter closing date (YYYY-MM-DD): ");
        String closingDate = scanner.nextLine();
        
        System.out.print("Enter manager NRIC: ");
        String managerNRIC = scanner.nextLine();
        
        System.out.print("Enter officer slot: ");
        int officerSlot = Integer.parseInt(scanner.nextLine());

        return new Project(name, neighbourhood, type1Units, type1Price,
                         type2Units, type2Price, openingDate, closingDate,
                         managerNRIC, officerSlot);
    }

    public String promptForProjectName() {
        System.out.print("\nEnter project name: ");
        return scanner.nextLine();
    }

    public void displayMessage(String message) {
        System.out.println(message);
    }

    public void displayError(String error) {
        System.err.println("Error: " + error);
    }
    
}
