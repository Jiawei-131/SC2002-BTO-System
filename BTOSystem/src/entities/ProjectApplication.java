package entities;

import controllers.AuthenticationController;
import data.UserDatabase;

public class ProjectApplication {
    private String applicantNRIC;
    private String applicationStatus;
    private String projectName;
    private String flatType;
    private boolean flatBooked;
    private String managingOfficerNRIC;
    

    // new applications
    public ProjectApplication(String nric, String projectName, String flatType) {
        this.applicantNRIC = nric;
        this.projectName = projectName;
        this.flatType = flatType;
        this.applicationStatus = "Pending";
        this.flatBooked = false;
        this.managingOfficerNRIC = "nil";
    }
    
    // existing applications
    public ProjectApplication(String nric, String applicationStatus, String projectName, String flatType, boolean flatBooked, String officerNRIC) {
        this.applicantNRIC = nric;
        this.projectName = projectName;
        this.flatType = flatType;
        this.applicationStatus = applicationStatus;
        this.flatBooked = flatBooked;
        this.managingOfficerNRIC = officerNRIC;
    }
    
    public String getApplicantId() {
    		return this.applicantNRIC;
    }
    
    public String getProjectName() {
    	return this.projectName;
    }
    
    public void setProjectName(String projectName) {
    	this.projectName = projectName;
    }
    
    public String getManagingOfficer() {
    	// bad code but..
    	// TODO if able, refine code?
    	UserDatabase db= new UserDatabase("LoginInfo.txt");
    	AuthenticationController ac = new AuthenticationController();
    	User officer = db.getUserById(this.managingOfficerNRIC, ac);
    	
    	return officer.getUsername();
    }
    
    public void setManagingOfficer(String officerNRIC) {
    	this.managingOfficerNRIC = officerNRIC;
    }

    public String getApplicationStatus() {
        return this.applicationStatus;
    }

    public void setApplicationStatus(String applicationStatus) {
        this.applicationStatus = applicationStatus;
    }

    public String getFlatType() {
        return this.flatType;
    }

    public void setFlatType(String flatType) {
        this.flatType = flatType;
    }

    public boolean getFlatBooked() {
        return this.flatBooked;
    }

    public void setFlatBooked(boolean flatBooked) {
        this.flatBooked = flatBooked;
    }

    public void bookFlat() {
        // TODO: what does this do bruh
    }
}
