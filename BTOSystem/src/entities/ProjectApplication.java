package entities;

/**
 * Represents an application for a housing project by an applicant.
 * The application holds details such as the applicant's NRIC, marital status,
 * age, project details, and booking status.
 */
public class ProjectApplication {
    
    private String applicantNRIC;
    private String applicationStatus;
    private String projectName;
    private String flatType;
    private String maritalStatus;
    private int age; // Applicant's age for filtering
    private boolean flatBooked;

    /**
     * Creates a new ProjectApplication for an applicant with the given details.
     * The application status is set to "Pending" and flatBooking is set to false by default.
     * 
     * @param nric The NRIC of the applicant
     * @param maritalStatus The marital status of the applicant
     * @param age The age of the applicant
     * @param projectName The name of the project
     * @param flatType The type of flat applied for
     */
    public ProjectApplication(String nric, String maritalStatus, int age, String projectName, String flatType) {
        this.applicantNRIC = nric;
        this.maritalStatus = maritalStatus;
        this.age = age;
        this.projectName = projectName;
        this.flatType = flatType;
        this.applicationStatus = "Pending";
        this.flatBooked = false;
    }
    
    /**
     * Creates a ProjectApplication with existing application details.
     * 
     * @param nric The NRIC of the applicant
     * @param maritalStatus The marital status of the applicant
     * @param age The age of the applicant
     * @param applicationStatus The current status of the application
     * @param projectName The name of the project
     * @param flatType The type of flat applied for
     * @param flatBooked Whether the flat has been booked or not
     */
    public ProjectApplication(String nric, String maritalStatus, int age, String applicationStatus, String projectName, String flatType, boolean flatBooked) {
        this.applicantNRIC = nric;
        this.maritalStatus = maritalStatus;
        this.age = age;
        this.projectName = projectName;
        this.flatType = flatType;
        this.applicationStatus = applicationStatus;
        this.flatBooked = flatBooked;
    }
    
    /**
     * Gets the NRIC of the applicant.
     * 
     * @return The NRIC of the applicant
     */
    public String getApplicantId() {
        return this.applicantNRIC;
    }
    
    /**
     * Gets the marital status of the applicant.
     * 
     * @return The marital status of the applicant
     */
    public String getMaritalStatus() {
        return this.maritalStatus;
    }
    
    /**
     * Sets the marital status of the applicant.
     * 
     * @param maritalStatus The new marital status of the applicant
     */
    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }
    
    /**
     * Gets the age of the applicant.
     * 
     * @return The age of the applicant
     */
    public int getAge() {
        return this.age;
    }
    
    /**
     * Sets the age of the applicant.
     * 
     * @param age The new age of the applicant
     */
    public void setAge(int age) {
        this.age = age;
    }
    
    /**
     * Gets the name of the project the applicant is applying for.
     * 
     * @return The name of the project
     */
    public String getProjectName() {
        return this.projectName;
    }
    
    /**
     * Sets the name of the project the applicant is applying for.
     * 
     * @param projectName The name of the project
     */
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    /**
     * Gets the current status of the application.
     * 
     * @return The application status
     */
    public String getApplicationStatus() {
        return this.applicationStatus;
    }

    /**
     * Sets the status of the application.
     * 
     * @param applicationStatus The new application status
     */
    public void setApplicationStatus(String applicationStatus) {
        this.applicationStatus = applicationStatus;
    }

    /**
     * Gets the type of flat the applicant is applying for.
     * 
     * @return The type of flat
     */
    public String getFlatType() {
        return this.flatType;
    }

    /**
     * Sets the type of flat the applicant is applying for.
     * 
     * @param flatType The new flat type
     */
    public void setFlatType(String flatType) {
        this.flatType = flatType;
    }

    /**
     * Gets whether the flat has been booked.
     * 
     * @return true if the flat is booked, false otherwise
     */
    public boolean getFlatBooked() {
        return this.flatBooked;
    }

    /**
     * Sets the flat booking status.
     * 
     * @param flatBooked The new flat booking status
     */
    public void setFlatBooked(boolean flatBooked) {
        this.flatBooked = flatBooked;
    }
}
