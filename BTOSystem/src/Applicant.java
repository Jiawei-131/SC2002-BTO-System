// package BTOSystem.src;

public class Applicant extends User {
    private boolean isVisible;
    private Application application;
    private String flat;
    private Enquiry enquiry;

    public Applicant(String name, String nric, int age, String maritalStatus, String password, boolean isVisible) {
        super(name, nric, age, maritalStatus, password);
        this.isVisible = isVisible;
        // appliedProject and flat to be null on fresh instance (?)

    }

    public void displayMenu() {

    }

    public boolean getIsVisible(){
        return this.isVisible;
    }

    public void setIsVisible(boolean isVisible) {
        this.isVisible = isVisible;
    }

    public void applyForProject(Project project) {
        // TODO: create new Application instance with project
    }
    
    public void viewApplication() {
        // TODO: print application or smth
    }

    public void requestWithdrawal() {
        // TODO: depends on Application class
    }

    public void createEnquiry(String enquiry) {
        // TODO: waiting on Enquiry class
    }

    public void viewEnquiry() {
        // TODO: idk what this is
    }

    public void editEnquiry(int enquiryID, String enquiry) {
        // TODO: waiting on Enquiry
    }

    public void deleteEnquiry(int enquiryID) {
        // TODO: waiting on Enquiry
    }
}
