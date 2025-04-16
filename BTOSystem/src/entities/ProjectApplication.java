package entities;

public class ProjectApplication {
    private String applicantNRIC;
    private String applicationStatus;
    private String projectName;
    private String flatType;
    private String maritalStatus;
    private int age; // applicants age for filtering
    private boolean flatBooked;
    

    // new applications
    public ProjectApplication(String nric, String maritalStatus, int age, String projectName, String flatType) {
        this.applicantNRIC = nric;
        this.maritalStatus = maritalStatus;
        this.age = age;
        this.projectName = projectName;
        this.flatType = flatType;
        this.applicationStatus = "Pending";
        this.flatBooked = false;
    }
    
    // existing applications
    public ProjectApplication(String nric, String maritalStatus, int age, String applicationStatus, String projectName, String flatType, boolean flatBooked) {
        this.applicantNRIC = nric;
        this.maritalStatus = maritalStatus;
        this.age = age;
        this.projectName = projectName;
        this.flatType = flatType;
        this.applicationStatus = applicationStatus;
        this.flatBooked = flatBooked;
    }
    
    public String getApplicantId() {
    		return this.applicantNRIC;
    }
    
    public String getMaritalStatus() {
    	return this.maritalStatus;
    }
    
    public void setMaritalStatus(String maritalStatus) {
    	this.maritalStatus = maritalStatus;
    }
    
    public int getAge() {
    	return this.age;
    }
    
    public void setAge(int age) {
    	this.age = age;
    }
    
    public String getProjectName() {
    	return this.projectName;
    }
    
    public void setProjectName(String projectName) {
    	this.projectName = projectName;
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
