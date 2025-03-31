package BTOSystem.src;

public class Application {
    private String applicantNRIC;
    private String applicationStatus;
    private String flatType;
    private boolean flatBooked;

    public void Application(String nric, String flatType) {
        this.applicantNRIC = nric;
        this.flatType = flatType;
        this.applicationStatus = "Pending";
        this.flatBooked = false;
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
