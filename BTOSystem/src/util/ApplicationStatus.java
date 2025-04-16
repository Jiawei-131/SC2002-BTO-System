package util;

public enum ApplicationStatus {
	PENDING("Pending"),
	UNSUCCESSFUL("Unsuccessful"),
	SUCCESSFUL("Successful"),
	BOOKED("Booked"),
	BOOKREQ("Booking requested"),
	BOOKREJ("Booking rejected"), // should not be used
	WITHDRAWN("Withdrawn"),
	WITHDRAWREQ("Withdrawal Requested"),
	WITHDRAWREJ("Withdrawal Rejected");
	
	private final String status;
	
	ApplicationStatus(String status) {
		this.status = status;
	}
	
	public String getStatus() {
		return this.status;
	}
}