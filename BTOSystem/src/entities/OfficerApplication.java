package entities;

public class OfficerApplication {
	private String applicantId;
	private String applicationStatus;
	private String projectName;
	
	public OfficerApplication() {
		
	}
	
	public OfficerApplication(String applicantId, String projectName) {
		this.applicantId = applicantId;
		this.applicationStatus = "Pending";
		this.projectName = projectName;
	}
	
	public OfficerApplication(String applicantId, String applicationStatus, String projectName) {
		this.applicantId = applicantId;
		this.applicationStatus = applicationStatus;
		this.projectName = projectName;
	}
	
	public String getApplicantId() {
		return this.applicantId;
	}
	
	// shouldnt be used though
	public void setApplicantId(String applicantId) {
		this.applicantId = applicantId;
	}
	
	public String getApplicationStatus() {
		return this.applicationStatus;
	}
	
	public void setApplicationStatus(String applicationStatus) {
		this.applicationStatus = applicationStatus;
	}
	
	public String getProjectName() {
		return this.projectName;
	}
	
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	@Override
	public String toString() {
		return "Project Name:" + this.projectName + "\nStatus: " + this.applicationStatus;
	}
}