package util;

/**
 * Enum representing the possible application statuses for a project application.
 * It includes various stages such as Pending, Successful, Unsuccessful, and others
 * related to booking and withdrawal requests.
 */
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

    /**
     * Constructor for the ApplicationStatus enum.
     * 
     * @param status The status string representing the application status.
     */
    ApplicationStatus(String status) {
        this.status = status;
    }

    /**
     * Gets the status as a string.
     * 
     * @return The string representing the application status.
     */
    public String getStatus() {
        return this.status;
    }
}
