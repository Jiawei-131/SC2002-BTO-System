package entities;

public class Enquiry {
    private int enquiryID;
    private String text;
    private String status;
    private String reply;

    private String applicantNRIC;
    private String projectName;
    private boolean visibleToApplicant;
    private boolean visibleToManager;

    private static int nextID = 1;
    
    public Enquiry(String text, String applicantNRIC, String projectName) {
        this.enquiryID = nextID++;
        this.text = text;
        this.status = "Pending";
        this.reply = null;

        this.applicantNRIC = applicantNRIC;
        this.projectName = projectName;
        this.visibleToApplicant = true;
        this.visibleToManager = true;
    }

    public void editEnquiry(String newText) {
        if (this.status.equals("Pending")) {
            this.text = newText;
            System.out.println("Enquiry updated successfully.");
        } else {
            System.out.println("Cannot edit enquiry that has been replied to.");
        }
    }

    public void deleteEnquiry() {
        this.visibleToApplicant = false;
        this.visibleToManager = false;
        System.out.println("Enquiry marked as deleted.");
    }

    public void replyEnquiry(String reply, User responder) {
        if (responder instanceof Officer || responder instanceof Manager) {
            this.reply = reply;
            this.status = "Answered";
            System.out.println("Reply submitted successfully.");
        } else {
            System.out.println("Only Officers or Managers can reply to enquiries.");
        }
    }

    public void closeEnquiry() {
        this.status = "Closed";
        System.out.println("Enquiry closed.");
    }

    public String displayDetails(User viewer) {
        if (!isVisibleTo(viewer)) {
            return "You don't have permission to view this enquiry.";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Enquiry ID: ").append(enquiryID).append("\n");
        sb.append("Project: ").append(projectName).append("\n");
        sb.append("Status: ").append(status).append("\n");
        sb.append("Question: ").append(text).append("\n");
        
        if (reply != null) {
            sb.append("Reply: ").append(reply).append("\n");
        }
        
        return sb.toString();
    }

    private boolean isVisibleTo(User user) {
        if (user instanceof Applicant) {
            return visibleToApplicant && user.getNric().equals(applicantNRIC);
        } else if (user instanceof Officer || user instanceof Manager) {
            return visibleToManager;
        }
        return false;
    }

    // Getters and Setters TBC
    public int getEnquiryID() {
        return enquiryID;
    }

    public String getText() {
        return text;
    }

    public String getStatus() {
        return status;
    }

    public String getReply() {
        return reply;
    }

    public String getApplicantNRIC() {
        return applicantNRIC;
    }

    public String getProjectName() {
        return projectName;
    }

    public boolean isVisibleToApplicant() {
        return visibleToApplicant;
    }

    public void setVisibleToApplicant(boolean visibleToApplicant) {
        this.visibleToApplicant = visibleToApplicant;
    }

    public boolean isVisibleToManager() {
        return visibleToManager;
    }

    public void setVisibleToManager(boolean visibleToManager) {
        this.visibleToManager = visibleToManager;
    }
}
