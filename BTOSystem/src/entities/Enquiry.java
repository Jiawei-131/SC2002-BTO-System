package entities;

public class Enquiry {
	private int enquiryID;
	private String text;
	private String status;
	private String reply;
	private boolean visibleToApplicant;
	private boolean visibleToManager;
	private String userNRIC; // Track who submitted the enquiry

	private static int nextID = 1;

	public Enquiry(String text, String status, String userNRIC) {
		this.enquiryID = nextID++;
		this.text = text;
		this.status = status;
		this.reply = null;
		this.userNRIC = userNRIC;
		this.visibleToApplicant = true;
		this.visibleToManager = true;
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
			this.visibleToManager = false;
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

	public boolean getVisibleToManager() {
		return visibleToManager;
	}

	public void setVisibleToManager(boolean visibleToManager) {
		this.visibleToManager = visibleToManager;
	}

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

	// Get the NRIC of the user who submitted the enquiry
	public String getUserNRIC() {
		return userNRIC;
	}

	@Override
	public String toString() {
		return "Enquiry ID: " + enquiryID + "\nSubmitted By: " + userNRIC + "\nText: " + text + "\nStatus: " + status
				+ "\nReply: " + reply + "\nVisible to Applicant: " + visibleToApplicant + "\nVisible to Manager: "
				+ visibleToManager;
	}
}
