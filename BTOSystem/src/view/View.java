package view;

import entities.Applicant;
import entities.Project;
import entities.User;
import java.util.List;
import java.util.Scanner;

/**
 * The View class is responsible for presenting various user interfaces to interact with the system.
 * It handles login, registration, menu display, project details, and other prompts to the user.
 */
public class View {
    
    /**
     * Constructor for the View class.
     */
    public View() {}

    /**
     * Displays the login options for the BTO Management System.
     */
    public static void loginView() {
        System.out.println("""
                BTO Management System (BMS)
                1. Login
                2. Register
                3. Exit
                """);
    }

    /**
     * Prompts the user to enter a valid password by displaying the password requirements.
     */
    public static void validPassword() {
        System.out.println("""
                Please enter a valid password that fulfils these requirements:
                1. Length more than 8
                2. Not default password
                3. Not your old password
                """);
    }

    /**
     * Prompts the user to enter a valid NRIC.
     */
    public static void validNRIC() {
        System.out.println("Please enter a valid NRIC");
    }

    /**
     * Displays the header with the user's information, including their name, role, and filter settings.
     * 
     * @param user The user whose details are displayed.
     */
    private static void printHeader(User user) {
        System.out.println("===================================");
        System.out.println(" Welcome, " + user.getUsername());
        System.out.printf(" Role: %s \n", user.getRole());
        System.out.printf(" Filter Setting: %s \n", user.getFilterDescription());
        System.out.println("===================================");
    }

    /**
     * Prompts the user to enter their NRIC during the registration process.
     */
    public static void register() {
        System.out.println("Enter your NRIC");
    }

    /**
     * Prints a goodbye message when the user decides to exit.
     */
    public static void exit() {
        System.out.println("Goodbye!");
    }

    /**
     * General prompt for the user to enter a specific string of information.
     * 
     * @param string The type of input requested (e.g., "name", "address").
     */
    public static void prompt(String string) {
        System.out.printf("Please enter %s: \n", string);
    }

    /**
     * Prompts the user to retry an operation or exit, providing retry and exit options.
     * 
     * @param string The message to display before the retry/exit prompt.
     */
    public static void promptRetry(String string) {
        System.out.printf("%s\n", string);
        System.out.println("1: Retry\n2: Exit");
    }

    /**
     * Displays the menu options for the user, depending on their role and available actions.
     * 
     * @param user The user whose menu is being displayed.
     * @param menuOptions The list of available menu options for the user.
     */
    public static void menu(User user, List<String> menuOptions) {
        printHeader(user);
        for (String option : menuOptions) {
            System.out.println(option);
        }
    }

    /**
     * Notifies the user when they have entered an invalid choice.
     */
    public static void invalidChoice() {
        System.out.println("Please enter a valid choice");
    }

    /**
     * Displays the details of a project to the user. If the user is an applicant, their eligibility and visibility are checked.
     * 
     * @param user The user viewing the project details.
     * @param project The project whose details are displayed.
     */
    public static void displayProjectDetails(User user, Project project) {
        if (user instanceof Applicant && !project.isVisibleToApplicant()) {
            System.out.println("You do not have access to view this project.");
            return;
        }
        if (user instanceof Applicant applicant) {
            if (!project.isEligible(applicant)) {
                System.out.println("You are not eligible to apply for this project.");
                return;
            }
            System.out.println("\n=== Project Details ===");
            System.out.println("Name: " + project.getName());
            System.out.println("Neighborhood: " + project.getNeighbourhood());
            if (project.isEligibleFor2and3Room(applicant)) {
                System.out.println("2-room Units: " + project.getNumberOfType1Units() +
                        " (Price: $" + String.format("%.2f", project.getType1SellingPrice()) + ")");
                System.out.println("3-room Units: " + project.getNumberOfType2Units() +
                        " (Price: $" + String.format("%.2f", project.getType2SellingPrice()) + ")");
            } else if (project.isEligibleFor2RoomOnly(applicant)) {
                System.out.println("2-room Units: " + project.getNumberOfType1Units() +
                        " (Price: $" + String.format("%.2f", project.getType1SellingPrice()) + ")");
            }
        } else {
            System.out.println("\n=== Project Details ===");
            System.out.println("Name: " + project.getName());
            System.out.println("Neighborhood: " + project.getNeighbourhood());
            System.out.println("2-room Units: " + project.getNumberOfType1Units() +
                    " (Price: $" + String.format("%.2f", project.getType1SellingPrice()) + ")");
            System.out.println("3-room Units: " + project.getNumberOfType2Units() +
                    " (Price: $" + String.format("%.2f", project.getType2SellingPrice()) + ")");
        }

        System.out.println("Application Period: " + project.getOpeningDate() +
            " to " + project.getClosingDate());
        System.out.println("Manager: " + project.getManager());
        System.out.println("Officers: " + project.getOfficerNRICs().size() +
            "/" + project.getOfficerSlot());
        for (String officerNRIC : project.getOfficerNRICs()) {
            System.out.println("  - " + officerNRIC);
        }
        System.out.println("Visibility: " +
            (project.isVisibleToApplicant() ? "Visible to applicants" : "Hidden from applicants"));
    }

    /**
     * Displays a list of all projects with their names, neighbourhoods, and visibility status.
     * 
     * @param projects The list of projects to display.
     */
    public static void displayAllProjects(List<Project> projects) {
        System.out.println("\n=== All Projects ===");
        for (Project project : projects) {
            System.out.println(project.getName() + " - " + project.getNeighbourhood() +
                " (" + (project.isVisibleToApplicant() ? "Visible" : "Hidden") + ")");
        }
    }
}
