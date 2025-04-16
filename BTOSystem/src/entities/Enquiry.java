package entities;

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

	public void setText(String newText) {
		this.text = newText;
	}

	public void deleteEnquiry(int enquiryID) {
		if (this.enquiryID == enquiryID) {
			this.text = null;
			this.status = "Deleted";
			this.reply = null;
			this.visibleToApplicant = false;
		}
	}

	public void replyEnquiry(int enquiryID, String reply) {
		if (this.enquiryID == enquiryID) {
			this.reply = reply;
			this.status = "Replied";
		}
	}

	public boolean getVisibleToApplicant() {
		return visibleToApplicant;
	}

	public void setVisibleToApplicant(boolean visibleToApplicant) {
		this.visibleToApplicant = visibleToApplicant;
	}	

	public int getEnquiryID() {
		return enquiryID;
	}

	public String getProjectName() {
		return projectName;
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

	// Get the NRIC of the user who submitted the enquiry
	public String getUserNRIC() {
		return userNRIC;
	}

	public String getOfficerNRIC() {
		return officerNRIC;
	}

	public String getManagerNRIC() {
		return managerNRIC;
	}

	@Override
	public String toString() {
		return "Enquiry ID: " + enquiryID + "\nProject: " + projectName + "\nSubmitted By: " + userNRIC + "\nText: "
				+ text + "\nStatus: " + status + "\nReply: " + reply + "\nManager NRIC: " + managerNRIC
				+ "\nOfficer NRIC: " + officerNRIC + "\nVisible to Applicant: " + visibleToApplicant;
	}
}
