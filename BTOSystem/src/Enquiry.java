public class Enquiry {
    private int enquiryID; // Getters and Setters?
    private String text;
    private String status;
    private String reply;
    private boolean visibleToApplicant;
    private boolean visibleToManager;

    public Enquiry(String text, String status, String reply) {
		super();
		this.text = text;
		this.status = status;
		this.reply = null; // reply : null?
	}

	public void editEnquiry(int enquiryID, String enquiry) {
		this.enquiryID = enquiryID;
        this.text = enquiry;
    }

    public void deleteEnquiry(int enquiryID) {
    	enquiryID = -1; // ?
    }

    public void replyEnquiry(int enquiryID, String reply) {
    	// TBC
    }

    // Getters and Setters
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
    
}