package entities;

import controllers.AuthenticationController;
import view.View;
public class Manager extends User {
//    private Project assignedProject;
    private boolean hasProject;

    public Manager(String name, String nric, int age, String maritalStatus, String password, boolean isVisible,AuthenticationController ac) {
        super(name, nric, age, maritalStatus, password,ac);
    }

    public void displayMenu(View view) {
        view.managerMenu();
    }

    public void createProject(String name, String neighbourhood, int roomType, int numberOfUnits, String openingDate, String closingDate, Manager manager, int availableSlots) {
        // TODO: construct and assign project
    }

    public void editProject(String name, String neighbourhood, int roomType, int numberOfUnits, String openingDate, String closingDate, Manager manager, int availableSlots) {
        // TODO: edit project
    }

//    public void deleteProject(Project project) {
//        // TODO: delete project and unassign project
//    }
//
//    public void toggleVisibility(Project project) {
//        project.setIsVisible(!project.isVisible);
//    }

    public void viewAllProjects() {
        // TODO
        // is it view all projects assigned to manager? or all all projects
    }

    public void approveOfficerRegistration(Officer officer) {
        // TODO implement
    }

    public void approveApplication(Application application) {
        // TODO implement
    }

    public void rejectApplication(Application application) {
        // TODO implement
    }

    public void approveApplicantWithdrawal(Application application) {
        // TODO implement
    }
    
    public void rejectApplicantWithdrawal(Application application) {
        // TODO implement
    }

    public void generateReport(int filter) {
        // TODO implement
    }

//    public String viewEnquiry(Enquiry[] enquiryList ) {
//        // TODO implement
//    }
//
//    public void replyEnquiry(Project assignedProject) {
//        // TODO implement
//    }
}
