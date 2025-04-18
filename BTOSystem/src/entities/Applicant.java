package entities;

import java.util.Scanner;
import java.util.Arrays;
import java.util.List;
import util.ApplicationStatus;
import util.Role;
import data.*;
import controllers.AuthenticationController;
import controllers.EnquiryController;
import view.View;

/**
 * Represents an Applicant in the system. The Applicant can apply for projects, view available projects,
 * manage their applications, and interact with enquiries.
 */
public class Applicant extends User {
    private boolean isVisible;  // Indicates if the applicant's details are visible to the system
    private String flat;  // Flat associated with the applicant's project (if any)
    private Enquiry enquiry;  // Enquiry related to the applicant (if any)
    private String appliedProject;  // Name of the project the applicant has applied for

    /**
     * Constructs an Applicant with the provided details.
     * 
     * @param name The name of the applicant.
     * @param nric The NRIC of the applicant.
     * @param age The age of the applicant.
     * @param maritalStatus The marital status of the applicant.
     * @param password The password of the applicant.
     * @param isVisible Whether the applicant's details are visible in the system.
     * @param ac The AuthenticationController instance for the applicant's authentication.
     * @param role The role of the user (should be ROLE.APPLICANT).
     */
    public Applicant(String name, String nric, int age, String maritalStatus, String password, boolean isVisible, AuthenticationController ac, Role role) {
        super(name, nric, age, maritalStatus, password, ac, role);
        this.isVisible = isVisible;
        this.appliedProject = null;  // New applicant has no applied project by default
    }

    /**
     * Displays the applicant's menu options.
     * 
     * @param options A list of menu options to display.
     */
    public void displayMenu(List<String> options) {
        View.menu(this, options);
    }

    /**
     * Gets the visibility status of the applicant.
     * 
     * @return True if the applicant's details are visible, otherwise false.
     */
    public boolean getIsVisible() {
        return this.isVisible;
    }

    /**
     * Sets the visibility status of the applicant.
     * 
     * @param isVisible The new visibility status to set.
     */
    public void setIsVisible(boolean isVisible) {
        this.isVisible = isVisible;
    }

    /**
     * Allows the applicant to apply for a project with the specified project name and flat type.
     * The application is saved to the project application database.
     * 
     * @param projectName The name of the project to apply for.
     * @param flatType The type of flat the applicant is applying for.
     */
    public void applyForProject(String projectName, String flatType) {
        ProjectApplication application = new ProjectApplication(this.nric, this.maritalStatus, this.age, projectName, flatType);
        ProjectApplicationDatabase.writeApplication(application);
        appliedProject = projectName;
    }

    /**
     * Views available projects based on the applicant's age and marital status.
     * Filters projects according to eligibility (e.g., age, marital status).
     * 
     * @param applicant The applicant who wants to view projects.
     */
    public void viewProjects(Applicant applicant) {
        List<Project> projects = this.sort();

        if (applicant.getMaritalStatus().equals("Single")) {
            if (applicant.getAge() < 35) {
                System.out.println("Ineligible applicant. No projects available.");
            } else {
                for (Project project : projects) {
                    if (Project.isVisibleToApplicant() && project.getNumberOfType1Units() > 0) {
                        View.displayProjectDetails(applicant, project);
                    }
                }
            }
        } else {
            if (applicant.getAge() < 21) {
                System.out.println("Ineligible applicant. No projects available.");
            } else {
                for (Project project : projects) {
                    if (Project.isVisibleToApplicant()) {
                        View.displayProjectDetails(applicant, project);
                    }
                }
            }
        }
    }

    /**
     * Retrieves the applicant's project application details.
     * 
     * @return The applicant's project application.
     */
    private ProjectApplication retrieveApplication() {
        return ProjectApplicationDatabase.getApplicationByApplicantId(this.nric);
    }

    /**
     * Views the applicant's current application details.
     * Prints details such as project name, flat type, and application status.
     */
    public void viewApplication() {
        ProjectApplication application = this.retrieveApplication();

        System.out.printf("""
                Application Details
                Project Name: %s
                Flat Type: %s
                Status: %s
                
                """, application.getProjectName(), application.getFlatType(), application.getApplicationStatus());
    }

    /**
     * Requests to withdraw the applicant's current project application.
     * Updates the application's status to "withdrawal requested".
     */
    public void requestWithdrawal() {
        ProjectApplication application = this.retrieveApplication();
        application.setApplicationStatus(ApplicationStatus.WITHDRAWREQ.getStatus());
        ProjectApplicationDatabase.updateApplication(application);
    }

    /**
     * Requests to book a flat for the applicant's project application.
     * Updates the application's status to "flat booking requested".
     */
    public void requestFlatBooking() {
        ProjectApplication application = this.retrieveApplication();
        application.setApplicationStatus(ApplicationStatus.BOOKREQ.getStatus());
        ProjectApplicationDatabase.updateApplication(application);
    }

    /**
     * Creates a new enquiry related to a project.
     * The enquiry is added to the enquiry controller for further processing.
     * 
     * @param projectName The project the enquiry is about.
     * @param text The text of the enquiry.
     * @param enquiryController The controller managing enquiries.
     */
    public void createEnquiry(String projectName, String text, EnquiryController enquiryController) {
        Project project = ProjectDatabase.findByName(projectName);

        if (project == null || !Project.isVisibleToApplicant()) {
            System.out.println("You can only enquire about visible projects.");
            return;
        }

        enquiryController.addEnquiry(projectName, this.nric, text);
    }

    /**
     * Views all the enquiries submitted by the applicant.
     * Displays a list of the applicant's enquiries.
     * 
     * @param enquiryController The controller managing enquiries.
     */
    public void viewEnquiries(EnquiryController enquiryController) {
        List<Enquiry> userEnquiries = enquiryController.getUserEnquiries(this.nric);
        if (userEnquiries.isEmpty()) {
            System.out.println("No enquiries found.");
            return;
        }

        System.out.println("\nYour Enquiries:");
        for (Enquiry e : userEnquiries) {
            System.out.println(e);
            System.out.println("------------------");
        }
    }

    /**
     * Edits an existing enquiry.
     * 
     * @param enquiryID The ID of the enquiry to edit.
     * @param newText The new text to replace the old text in the enquiry.
     * @param enquiryController The controller managing enquiries.
     */
    public void editEnquiry(String enquiryID, String newText, EnquiryController enquiryController) {
        boolean updated = enquiryController.editEnquiry(enquiryID, this.nric, newText);
        System.out.println(updated ? "Enquiry updated." : "Failed to update enquiry (ID not found or permission denied).");
    }

    /**
     * Deletes an existing enquiry.
     * 
     * @param enquiryID The ID of the enquiry to delete.
     * @param enquiryController The controller managing enquiries.
     */
    public void deleteEnquiry(String enquiryID, EnquiryController enquiryController) {
        boolean deleted = enquiryController.deleteEnquiry(enquiryID, this.nric);
        System.out.println(deleted ? "Enquiry deleted." : "Failed to delete enquiry (ID not found or permission denied).");
    }

    /**
     * Gets the list of project-related options available to the applicant.
     * 
     * @return A list of strings representing project-related options.
     */
    public List<String> getProjectOptions() {
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
     * Gets the list of enquiry-related options available to the applicant.
     * 
     * @return A list of strings representing enquiry-related options.
     */
    public List<String> getEnquiryOptions() {
        return Arrays.asList(
                "1. Submit Enquiry",
                "2. View Enquiry",
                "3. Edit Enquiry",
                "4. Delete Enquiry",
                "5. Back to Main Menu"
        );
    }
}
