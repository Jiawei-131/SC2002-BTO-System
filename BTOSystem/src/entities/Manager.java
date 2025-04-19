package entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import entities.*;
import handlers.ManagerProjectService;
import util.ApplicationStatus;
import util.GetInput;
import util.Role;
import controllers.AuthenticationController;
import controllers.DateTimeController;
import controllers.EnquiryController;
import controllers.ProjectController;
import data.EnquiryDatabase;
import data.OfficerApplicationDatabase;
import data.ProjectApplicationDatabase;
import data.ProjectDatabase;
import view.View;

/**
 * Represents a Manager who is responsible for managing projects, approving
 * or rejecting applications, and handling enquiries in the system.
 */
public class Manager extends User {
    private Project assignedProject;
    private boolean hasProject = false;
    private boolean isVisible;

    /**
     * Constructs a Manager object with the provided details.
     *
     * @param name        The name of the Manager.
     * @param nric        The NRIC of the Manager.
     * @param age         The age of the Manager.
     * @param maritalStatus The marital status of the Manager.
     * @param password    The password for the Manager.
     * @param isVisible   The visibility status of the Manager.
     * @param ac          The AuthenticationController instance for authentication.
     * @param role        The role of the Manager.
     */
    public Manager(String name, String nric, int age, String maritalStatus, String password, boolean isVisible, AuthenticationController ac, Role role) {
        super(name, nric, age, maritalStatus, password, ac, role);
        this.isVisible = isVisible;
    }

    /**
     * Displays the main menu for the Manager with the provided options.
     *
     * @param options The list of menu options to display.
     */
    public void displayMenu(List<String> options) {
        View.menu(this, options);
    }

    // Project-related implementations

    /**
     * Creates a new project if the Manager doesn't already have an active project.
     *
     * @param sc The Scanner instance to read user input.
     */
    public void createProject(Scanner sc) {
        if (hasProject == true) {
            System.out.println("Already has an active project!");
        } else {
            ManagerProjectService.createProject(this, sc);
            System.out.println("Project Created!");
        }
    }

    /**
     * Retrieves the active project assigned to the Manager at login.
     *
     * @return The active Project assigned to the Manager.
     */
    public Project getActiveProject() {
        if (ProjectController.hasActiveProject(ProjectDatabase.loadAllProjects(), this)) {
            hasProject = true;
            assignedProject = ManagerProjectService.getActiveProject(this);
        }
        return assignedProject;
    }

    /**
     * Displays the details of the active project if one exists.
     */
    public void viewActiveProject() {
        if (hasProject == true) {
            View.displayProjectDetails(this, assignedProject);
        } else {
            System.out.println("You do not have any active project");
        }
    }

    /**
     * Edits the details of the Manager's assigned project.
     *
     * @param sc The Scanner instance to read user input.
     */
    public void editProject(Scanner sc) {
        ManagerProjectService.editProject(this, sc);
    }

    /**
     * Deletes the Manager's active project and resets the project details.
     *
     * @param sc The Scanner instance to read user input.
     */
    public void deleteProject(Scanner sc) {
        ManagerProjectService.deleteProject(this, sc);
        if (hasProject == true) {
            assignedProject = null;
            hasProject = false;
        }
    }

    /**
     * Toggles the visibility of the assigned project.
     */
    public void toggleVisibility() {
        assignedProject.setIsVisible(!this.isVisible);
    }

    /**
     * Displays a list of all projects or only the Manager's own projects.
     *
     * @param type The type of projects to display (all or owned by the Manager).
     */
    public void viewProjects(int type) {
        ManagerProjectService.showProject(this, type);
    }

    /**
     * Generates a report based on the Manager's project.
     *
     * @param sc The Scanner instance to read user input.
     */
    public void generateReport(Scanner sc) {
        ManagerProjectService.generateReport(this, sc);
    }

    // Approval implementations

    /**
     * Approves the registration of an HDB Officer for a project.
     *
     * @param application The OfficerApplication to approve.
     */
    public void approveOfficerRegistration(OfficerApplication application) {
        ProjectController pc = new ProjectController();
        application.setApplicationStatus(ApplicationStatus.SUCCESSFUL.getStatus());
        OfficerApplicationDatabase.updateApplication(application);
        if (pc.assignOfficer(application.getProjectName(), application.getApplicantId())) {
            System.out.println("Approved");
        } else {
            System.out.println("Approval failed");
        }
    }

    /**
     * Rejects the registration of an HDB Officer for a project.
     *
     * @param application The OfficerApplication to reject.
     */
    public void rejectOfficerRegistration(OfficerApplication application) {
        ProjectController pc = new ProjectController();
        application.setApplicationStatus(ApplicationStatus.UNSUCCESSFUL.getStatus());
        OfficerApplicationDatabase.updateApplication(application);
        pc.removeOfficer(application.getProjectName(), application.getApplicantId());
    }

    /**
     * Approves a project application for an applicant based on the availability of flats.
     *
     * @param application The ProjectApplication to approve.
     */
    public void approveApplication(ProjectApplication application) {
        ProjectController pc = new ProjectController();
        if (application.getFlatType().equals("2-Room") && pc.getProject(application.getProjectName()).getNumberOfType1Units() > 0) {
            application.setApplicationStatus(ApplicationStatus.SUCCESSFUL.getStatus());
            ProjectApplicationDatabase.updateApplication(application);
        } else if (application.getFlatType().equals("3-Room") && pc.getProject(application.getProjectName()).getNumberOfType2Units() > 0) {
            application.setApplicationStatus(ApplicationStatus.SUCCESSFUL.getStatus());
            ProjectApplicationDatabase.updateApplication(application);
        } else {
            System.out.println("Approval Failed: not enough supply of the flat");
        }
    }

    /**
     * Rejects a project application for an applicant.
     *
     * @param application The ProjectApplication to reject.
     */
    public void rejectApplication(ProjectApplication application) {
        ProjectController pc = new ProjectController();
        application.setApplicationStatus(ApplicationStatus.UNSUCCESSFUL.getStatus());
        ProjectApplicationDatabase.updateApplication(application);
    }

    /**
     * Approves an applicant's request to withdraw their project application.
     *
     * @param application The ProjectApplication to approve withdrawal for.
     */
    public void approveApplicantWithdrawal(ProjectApplication application) {
        application.setApplicationStatus(ApplicationStatus.WITHDRAWN.getStatus());
        ProjectApplicationDatabase.updateApplication(application);
    }

    /**
     * Rejects an applicant's request to withdraw their project application.
     *
     * @param application The ProjectApplication to reject withdrawal for.
     */
    public void rejectApplicantWithdrawal(ProjectApplication application) {
        application.setApplicationStatus(ApplicationStatus.WITHDRAWREJ.getStatus());
        ProjectApplicationDatabase.updateApplication(application);
    }

    // Menu options

    /**
     * Retrieves the list of menu options available to the Manager.
     *
     * @return A list of menu options.
     */
    public List<String> getMenuOptions() {
        return Arrays.asList(
            "1. Project Details",
            "2. Approvals",
            "3. Enquiries",
            "4. Change Password",
            "5. Filter Settings",
            "6. Logout"
        );
    }

    /**
     * Retrieves the list of approval options available to the Manager.
     *
     * @return A list of approval options.
     */
    public List<String> getApprovalOption() {
        return Arrays.asList(
            "1. Approve or reject HDB Officer’s registration",
            "2. Approve or reject Applicant’s BTO application",
            "3. Approve or reject Applicant's request to withdraw the application.",
            "4. Back to Main Menu"
        );
    }

    /**
     * Retrieves the list of project options available to the Manager.
     *
     * @return A list of project options.
     */
    public List<String> getProjectOptions() {
        return Arrays.asList(
            "1. View All Project listings",
            "2. View My Project listings",
            "3. View My Active Project",
            "4. Create BTO Project listing",
            "5. Delete BTO Project listing",
            "6. Edit BTO Project listing",
            "7. Generate report",
            "8. Back to Main Menu"
        );
    }

    /**
     * Retrieves the list of enquiry options available to the Manager.
     *
     * @return A list of enquiry options.
     */
    public List<String> getEnquiryOptions() {
        return Arrays.asList(
            "1. View enquiry of all projects",
            "2. View enquiries of my project",
            "3. Reply to enquiry",
            "4. Back to Main Menu"
        );
    }

    /**
     * Retrieves the list of report filter options available to the Manager.
     *
     * @return A list of report filter options.
     */
    public List<String> getReportFilterOptions() {
        return Arrays.asList(
            "1. Project Name",
            "2. Flat Type",
            "3. Age",
            "4. Marital Status"
        );
    }

    /**
     * Retrieves the list of BTO editing options available to the Manager.
     *
     * @return A list of BTO editing options.
     */
    public List<String> getBtoOptions() {
        return Arrays.asList(
            "1. the neighbourhood",
            "2. the Number of 2 Room Units",
            "3. the Price of 2 Room Units",
            "4. the Number of 3 Room Units",
            "5. the Price of 3 Room Units",
            "6. the Opening Date in DD-MM-YYYY format",
            "7. the Closing Date in DD-MM-YYYY format",
            "8. the Number of HDB Officer slots",
            "9. the Visibility",
            "10.finish Editing"
        );
    }

    
    
    //Enquiry Implementations
    /**
     * Displays all enquiries for projects based on the provided choice.
     * If choice is 1, shows all enquiries. If the choice is anything else,
     * it filters the enquiries by the Manager's assigned project.
     *
     * @param enquiryController The controller responsible for handling enquiries.
     * @param choice The filter choice: 1 for all enquiries, other values for manager-specific enquiries.
     */
    public void viewAllEnquiries(EnquiryController enquiryController, int choice) {
        List<Enquiry> allEnquiries = enquiryController.getAllEnquiries();
        boolean found = false;

        System.out.println("\n--- Enquiries ---");

        for (Enquiry e : allEnquiries) {
            if (choice == 1) {
                // Show all project enquiries
                System.out.println(e);
                System.out.println("------------------");
                found = true;
            } else if (e.getManagerNRIC().equals(this.getNric())) {
                // Show only enquiries for manager's project
                System.out.println(e);
                System.out.println("------------------");
                found = true;
            }
        }

        if (!found) {
            System.out.println("No enquiries found.");
        }
    }

    /**
     * Allows the Manager to reply to an enquiry.
     * The Manager is prompted to enter the Enquiry ID and their reply.
     * If the enquiry belongs to the Manager's project, the reply is saved.
     * The enquiry is then updated in the EnquiryDatabase.
     *
     * @param sc The Scanner object for user input.
     * @param enquiryController The controller responsible for handling enquiries.
     */
    public void replyToEnquiry(Scanner sc, EnquiryController enquiryController) {
        System.out.print("Enter Enquiry ID to reply: ");
        int id = GetInput.getIntInput(sc,"the EnquiryID");

        Enquiry enquiry = enquiryController.findEnquiryById(String.valueOf(id));

        if (enquiry == null) {
            System.out.println("Enquiry not found.");
            return;
        }

        if (!enquiry.getManagerNRIC().equals(this.getNric())) {
            System.out.println("You are not authorized to reply to this enquiry.");
            return;
        }

        System.out.print("Enter your reply: ");
        String reply = sc.nextLine();

        enquiry.replyEnquiry(id, reply);
        boolean success = EnquiryDatabase.update(enquiry);
        System.out.println(success ? "Reply submitted successfully." : "Failed to update enquiry.");
    }

}
