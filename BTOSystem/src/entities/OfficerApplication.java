package entities;

/**
 * Represents an application made by an officer for a specific project.
 * The officer can apply for a project, and the application contains details
 * about the applicant's ID, the project's name, and the current status of the application.
 */
public class OfficerApplication {
    private String applicantId;
    private String applicationStatus;
    private String projectName;

    /**
     * Default constructor for creating an OfficerApplication object.
     */
    public OfficerApplication() {
    }

    /**
     * Constructor for creating an OfficerApplication object with the given applicant ID and project name.
     * The application status is set to "Pending" by default.
     *
     * @param applicantId The ID of the applicant (officer).
     * @param projectName The name of the project the officer is applying for.
     */
    public OfficerApplication(String applicantId, String projectName) {
        this.applicantId = applicantId;
        this.applicationStatus = "Pending";
        this.projectName = projectName;
    }

    /**
     * Constructor for creating an OfficerApplication object with the given applicant ID, application status, 
     * and project name.
     *
     * @param applicantId    The ID of the applicant (officer).
     * @param applicationStatus The status of the application (e.g., "Pending", "Approved", "Rejected").
     * @param projectName    The name of the project the officer is applying for.
     */
    public OfficerApplication(String applicantId, String applicationStatus, String projectName) {
        this.applicantId = applicantId;
        this.applicationStatus = applicationStatus;
        this.projectName = projectName;
    }

    /**
     * Gets the applicant's ID.
     *
     * @return The ID of the applicant (officer).
     */
    public String getApplicantId() {
        return this.applicantId;
    }

    /**
     * Sets the applicant's ID.
     * This method is not intended for use as the applicant ID is set when the application is created.
     *
     * @param applicantId The ID of the applicant to set.
     */
    public void setApplicantId(String applicantId) {
        this.applicantId = applicantId;
    }

    /**
     * Gets the application status.
     *
     * @return The current status of the application.
     */
    public String getApplicationStatus() {
        return this.applicationStatus;
    }

    /**
     * Sets the application status.
     *
     * @param applicationStatus The new status of the application (e.g., "Pending", "Approved").
     */
    public void setApplicationStatus(String applicationStatus) {
        this.applicationStatus = applicationStatus;
    }

    /**
     * Gets the project name associated with this application.
     *
     * @return The name of the project.
     */
    public String getProjectName() {
        return this.projectName;
    }

    /**
     * Sets the project name for this application.
     *
     * @param projectName The name of the project to set.
     */
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    /**
     * Returns a string representation of the OfficerApplication object.
     * Displays the project name and application status.
     *
     * @return A string containing the project name and application status.
     */
    @Override
    public String toString() {
        return "Project Name: " + this.projectName + "\nStatus: " + this.applicationStatus;
    }
}
