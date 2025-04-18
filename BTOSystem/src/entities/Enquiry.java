package entities;

/**
 * Represents an enquiry made by an applicant for a project.
 * Enquiries can be replied to, deleted, and their visibility to applicants can be managed.
 */
public class Enquiry {
    private int enquiryID;
    private String projectName;
    private String text;
    private String status;
    private String reply;
    private boolean visibleToApplicant;
    private String userNRIC; // Track who submitted the enquiry
    private String officerNRIC;
    private String managerNRIC;

    private static int nextID = 1;

    /**
     * Constructor for creating a new enquiry.
     * 
     * @param projectName   The name of the project the enquiry is about.
     * @param text          The text content of the enquiry.
     * @param status        The current status of the enquiry.
     * @param userNRIC      The NRIC of the user who submitted the enquiry.
     * @param managerNRIC   The NRIC of the manager associated with the enquiry.
     * @param officerNRIC   The NRIC of the officer associated with the enquiry.
     */
    public Enquiry(String projectName, String text, String status, String userNRIC, String managerNRIC,
            String officerNRIC) {
        this.enquiryID = nextID++;
        this.projectName = projectName;
        this.text = text;
        this.status = status;
        this.reply = null;
        this.userNRIC = userNRIC;
        this.managerNRIC = managerNRIC;
        this.officerNRIC = officerNRIC;
        this.visibleToApplicant = true;
    }

    /**
     * Sets a new text for the enquiry.
     * 
     * @param newText The new text content for the enquiry.
     */
    public void setText(String newText) {
        this.text = newText;
    }

    /**
     * Marks the enquiry as deleted by clearing its text and changing its status.
     * 
     * @param enquiryID The ID of the enquiry to delete.
     */
    public void deleteEnquiry(int enquiryID) {
        if (this.enquiryID == enquiryID) {
            this.text = null;
            this.status = "Deleted";
            this.reply = null;
            this.visibleToApplicant = false;
        }
    }

    /**
     * Sets a reply to the enquiry and updates its status to "Replied".
     * 
     * @param enquiryID The ID of the enquiry to reply to.
     * @param reply     The reply to the enquiry.
     */
    public void replyEnquiry(int enquiryID, String reply) {
        if (this.enquiryID == enquiryID) {
            this.reply = reply;
            this.status = "Replied";
        }
    }

    /**
     * Gets the visibility status of the enquiry for the applicant.
     * 
     * @return true if the enquiry is visible to the applicant, false otherwise.
     */
    public boolean getVisibleToApplicant() {
        return visibleToApplicant;
    }

    /**
     * Sets the visibility status of the enquiry for the applicant.
     * 
     * @param visibleToApplicant true to make the enquiry visible, false otherwise.
     */
    public void setVisibleToApplicant(boolean visibleToApplicant) {
        this.visibleToApplicant = visibleToApplicant;
    }

    /**
     * Gets the unique enquiry ID.
     * 
     * @return the enquiry ID.
     */
    public int getEnquiryID() {
        return enquiryID;
    }

    /**
     * Gets the name of the project associated with the enquiry.
     * 
     * @return the project name.
     */
    public String getProjectName() {
        return projectName;
    }

    /**
     * Gets the text content of the enquiry.
     * 
     * @return the text of the enquiry.
     */
    public String getText() {
        return text;
    }

    /**
     * Gets the status of the enquiry (e.g., "Replied", "Pending", "Deleted").
     * 
     * @return the status of the enquiry.
     */
    public String getStatus() {
        return status;
    }

    /**
     * Gets the reply to the enquiry, if any.
     * 
     * @return the reply text or null if no reply has been provided.
     */
    public String getReply() {
        return reply;
    }

    /**
     * Gets the NRIC of the user who submitted the enquiry.
     * 
     * @return the NRIC of the user.
     */
    public String getUserNRIC() {
        return userNRIC;
    }

    /**
     * Gets the NRIC of the officer associated with the enquiry.
     * 
     * @return the NRIC of the officer.
     */
    public String getOfficerNRIC() {
        return officerNRIC;
    }

    /**
     * Gets the NRIC of the manager associated with the enquiry.
     * 
     * @return the NRIC of the manager.
     */
    public String getManagerNRIC() {
        return managerNRIC;
    }

    /**
     * Returns a string representation of the enquiry.
     * 
     * @return a string containing the enquiry details.
     */
    @Override
    public String toString() {
        return "Enquiry ID: " + enquiryID + "\nProject: " + projectName + "\nSubmitted By: " + userNRIC + "\nText: "
                + text + "\nStatus: " + status + "\nReply: " + reply + "\nManager NRIC: " + managerNRIC
                + "\nOfficer NRIC: " + officerNRIC + "\nVisible to Applicant: " + visibleToApplicant;
    }
}
