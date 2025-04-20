package entities;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import controllers.AuthenticationController;
import controllers.EnquiryController;
import controllers.ProjectController;
import data.EnquiryDatabase;
import data.OfficerApplicationDatabase;
import data.ProjectApplicationDatabase;
import data.ProjectDatabase;
import data.UserDatabase;
import handlers.ManagerProjectService;
import util.ApplicationStatus;
import util.Role;
import view.View;

/**
 * The Officer class represents a user with officer privileges who can manage and register for HDB projects.
 * It extends the Applicant class, inheriting its basic properties and adding additional features
 * specific to the Officer role, such as project management and enquiry handling.
 */
public class Officer extends Applicant {
    private boolean isVisible;
    private Project assignedProject = null;
    private boolean registrationStatus;
    private boolean canRegister = true;

    /**
     * Constructor for creating an Officer object.
     *
     * @param name          The name of the officer.
     * @param nric          The NRIC of the officer.
     * @param age           The age of the officer.
     * @param maritalStatus The marital status of the officer.
     * @param password      The password of the officer.
     * @param isVisible     The visibility status of the officer.
     * @param ac            The authentication controller.
     * @param role          The role of the officer.
     */
    public Officer(String name, String nric, int age, String maritalStatus, String password, boolean isVisible, AuthenticationController ac, Role role) {
        super(name, nric, age, maritalStatus, password, isVisible, ac, role);
    }

    /**
     * Retrieves the active project assigned to this officer.
     * 
     * @return The assigned project, or null if no project is assigned.
     */
    public Project getActiveProject() {
        ProjectController pc = new ProjectController();
        List<OfficerApplication> projects = OfficerApplicationDatabase.readApplication();
        for (OfficerApplication project : projects) {
            if (this.getNric().equals(project.getApplicantId()) && project.getApplicationStatus().equals(ApplicationStatus.SUCCESSFUL.getStatus())) {
                assignedProject = pc.getProject(project.getProjectName());
                setCanRegister(false);
            }
        }
        return assignedProject;
    }

    /**
     * Displays the main menu for the officer, showing different options based on role and registration status.
     */
    public void displayMenu() {
        View.menu(this, this.getMenuOptions());
    }

    /**
     * Displays the options available for the officer based on their registration status.
     */
    public void displayChoice() {
        View.menu(this, getRoleOptions());
    }

    /**
     * Displays the current registration status of the officer for the projects they have applied for.
     */
    public void viewRegistrationStatus() {
        ProjectController pc = new ProjectController();
        List<OfficerApplication> projects = OfficerApplicationDatabase.readApplication();
        for (OfficerApplication project : projects) {
            if (this.getNric().equals(project.getApplicantId())) {
                System.out.println(project.getApplicantId() + " - " + project.getApplicationStatus() +
                        " - " + project.getProjectName());
            }
        }
    }

    /**
     * Views the list of all projects available and displays their details.
     */
    public void viewAllProjects() {
        List<Project> projects = this.sort();
        for (Project project : projects) {
            if (project.isVisibleToApplicant()) {
                View.displayProjectDetails(this, project);
            }
        }
    }

    /**
     * Applies for a project as an officer.
     *
     * @param projectName The name of the project to apply for.
     */
    public void applyForProject(String projectName) {
        if (ProjectController.checkOfficerHandleEligibility(projectName, this)) {
            OfficerApplication application = new OfficerApplication(this.nric, projectName);
            OfficerApplicationDatabase.writeApplication(application);
        }
    }

    /**
     * Views all applications made by the officer.
     */
    public void viewApplications() {
        List<OfficerApplication> applications = OfficerApplicationDatabase.getApplicationsByApplicantId(this.nric);
        if (applications == null) {
            System.out.println("You have no past applications!");
        } else {
            System.out.println("\n ----- Your Applications -----");
            for (OfficerApplication application : applications) {
                System.out.println(application);
                System.out.println("------------------------------");
            }
        }
    }

    /**
     * Books a flat for a project application and updates the project's available units.
     *
     * @param application The project application to book the flat for.
     */
    public void bookFlat(ProjectApplication application) {
        application.setApplicationStatus(ApplicationStatus.BOOKED.getStatus());
        ProjectApplicationDatabase.updateApplication(application);

        Project project = ProjectDatabase.findByName(application.getProjectName());

        if (application.getFlatType().equals("2-Room")) {
            project.setNumberOfType1Units(project.getNumberOfType1Units() - 1);
        } else {
            project.setNumberOfType2Units(project.getNumberOfType2Units() - 1);
        }
        ProjectDatabase.update(project);
    }

    /**
     * Generates a receipt for the flat booking.
     *
     * @param application The project application to generate the receipt for.
     */
    public void generateReceipt(ProjectApplication application) {
        Project project = ProjectDatabase.findByName(application.getProjectName());
        AuthenticationController ac = new AuthenticationController();

        System.out.println("===== Receipt ======");
        System.out.println("Project Details:");
        System.out.println(project);
        System.out.println("\n");
        System.out.println("Applicant Name: " + UserDatabase.getUserById(application.getApplicantId(), ac).getUsername());
        System.out.println("Applicant NRIC: " + application.getApplicantId());
        System.out.println("Age: " + application.getAge());
        System.out.println("Marital Status: " + application.getMaritalStatus());
        System.out.println("Flat Type Booked: " + application.getFlatType());
        System.out.println("===== END =====");
    }

    /**
     * Views all enquiries for the officer's assigned project.
     *
     * @param controller The enquiry controller to fetch enquiries.
     */
    public void viewEnquiries(EnquiryController controller) {
        String myNRIC = this.getNric();
        List<Enquiry> enquiries = controller.getAllEnquiries();

        boolean found = false;
        System.out.println("\n--- Enquiries for Your Project ---");
        for (Enquiry e : enquiries) {
            if (e.getOfficerNRIC().equals(myNRIC)) {
                System.out.println(e);
                System.out.println("------------------");
                found = true;
            }
        }

        if (!found) {
            System.out.println("No enquiries found for your project.");
        }
    }

    /**
     * Replies to an enquiry regarding the officer's project.
     *
     * @param sc        The scanner to read input from the user.
     * @param controller The enquiry controller to handle enquiry replies.
     */
    public void replyToEnquiry(Scanner sc, EnquiryController controller) {
        System.out.print("Enter Enquiry ID to reply: ");
        int id = Integer.parseInt(sc.nextLine());
        Enquiry e = controller.findEnquiryById(String.valueOf(id));

        if (e == null || !e.getOfficerNRIC().equals(this.getNric())) {
            System.out.println("You do not have access to this enquiry or it does not exist.");
            return;
        }

        System.out.print("Enter your reply: ");
        String reply = sc.nextLine();
        e.replyEnquiry(id, reply);
        boolean updated = EnquiryDatabase.update(e);
        System.out.println(updated ? "Reply sent." : "Failed to send reply.");
    }

    /**
     * Retrieves whether the officer can register for a project.
     *
     * @return true if the officer can register, false otherwise.
     */
    public boolean getCanRegister() {
        return canRegister;
    }

    /**
     * Retrieves the officer's NRIC.
     *
     * @return The NRIC of the officer.
     */
    public String getNric() {
        return this.nric;
    }

    /**
     * Sets whether the officer can register for a project.
     *
     * @param canRegister true if the officer can register, false otherwise.
     */
    private void setCanRegister(boolean canRegister) {
        this.canRegister = canRegister;
    }

    /**
     * Retrieves the role-specific menu options.
     *
     * @return A list of menu options available to the officer.
     */
    public List<String> getRoleOptions() {
        return Arrays.asList("1. Applicant", "2. Officer");
    }

    /**
     * Retrieves the menu options available for project-related activities.
     *
     * @return A list of project-related options.
     */
    public List<String> getProjectOptions() {
        return Arrays.asList(
                "Please choose which role you want to access the system as:",
                "1. Applicant",
                "2. Officer",
                "3. Back to Main Menu"
        );
    }

    /**
     * Retrieves the options available to applicants for project-related activities.
     *
     * @return A list of options for applicants.
     */
    public List<String> getProjectApplicantOptions() {
        return Arrays.asList(
                "1. View list of projects",
                "2. Apply for project",
                "3. View applied project",
                "4. Request Flat Booking",
                "5. Withdraw from BTO Application",
                "6. Back to Main Menu"
        );
    }

    /**
     * Retrieves the options available to officers for project-related activities.
     *
     * @return A list of options for officers.
     */
    public List<String> getProjectOfficerOptions() {
        if (canRegister == false) {
            return Arrays.asList(
                    String.format("Project Officer for %s", assignedProject.getName()),
                    "1. View assigned project details",
                    "2. View applications",
                    "3. Book Flat",
                    "4. Back to Main Menu"
            );
        } else {
            return Arrays.asList(
                    "1. View list of projects",
                    "2. Apply for project", // apply as officer
                    "3. View applied projects", // see all applications as officer
                    "4. Back to Main Menu"
            );
        }
    }

    /**
     * Retrieves the options available for viewing and replying to enquiries.
     *
     * @return A list of options for managing enquiries.
     */
    @Override
    public List<String> getEnquiryOptions() {
        return Arrays.asList(
                "1. View enquiries for my project",
                "2. Reply to an enquiry",
                "3. Back to Main Menu"
        );
    }
}
