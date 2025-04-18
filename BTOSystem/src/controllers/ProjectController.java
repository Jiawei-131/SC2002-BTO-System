package controllers;

import entities.Applicant;
import entities.Manager;
import entities.Officer;
import entities.Project;
import entities.ProjectApplication;
import data.ProjectApplicationDatabase;
import data.ProjectDatabase;
import data.UserDatabase;
import java.util.List;

/**
 * Controller class for managing projects, staff, and eligibility checks.
 * Provides methods to create, retrieve, update, delete projects, and manage project staff.
 */
public class ProjectController {
    private ProjectDatabase projectDB;
    private UserDatabase userDB;

    /**
     * Constructor for ProjectController that initializes the Project and User databases.
     */
    public ProjectController() {
        this.projectDB = new ProjectDatabase();
        this.userDB = new UserDatabase();
    }

    // CRUD Operations

    /**
     * Creates a new project in the system.
     * 
     * @param project The project to be created.
     * @return True if the project is successfully created, false otherwise.
     */
    public boolean createProject(Project project) {
        return ProjectDatabase.save(project);
    }

    /**
     * Retrieves a project by its name.
     * 
     * @param name The name of the project.
     * @return The project with the specified name, or null if not found.
     */
    public Project getProject(String name) {
        return ProjectDatabase.findByName(name);
    }

    /**
     * Retrieves all projects in the system.
     * 
     * @return A list of all projects.
     */
    public List<Project> getAllProjects() {
        return ProjectDatabase.loadAllProjects();
    }

    /**
     * Updates an existing project in the system.
     * 
     * @param project The project to be updated.
     * @return True if the project is successfully updated, false otherwise.
     */
    public boolean updateProject(Project project) {
        return projectDB.update(project);
    }

    /**
     * Deletes a project by its name.
     * 
     * @param name The name of the project to be deleted.
     * @return True if the project is successfully deleted, false otherwise.
     */
    public boolean deleteProject(String name) {
        return ProjectDatabase.delete(name);
    }

    // Staff Management

    /**
     * Assigns an officer to a project.
     * 
     * @param projectName The name of the project.
     * @param officerNRIC The NRIC of the officer to be assigned.
     * @return True if the officer is successfully assigned, false otherwise.
     */
    public boolean assignOfficer(String projectName, String officerNRIC) {
        Project project = ProjectDatabase.findByName(projectName);
        if (project != null && project.addOfficer(officerNRIC)) {
            return projectDB.update(project);
        }
        return false;
    }

    /**
     * Removes an officer from a project.
     * 
     * @param projectName The name of the project.
     * @param officerNRIC The NRIC of the officer to be removed.
     * @return True if the officer is successfully removed, false otherwise.
     */
    public boolean removeOfficer(String projectName, String officerNRIC) {
        Project project = ProjectDatabase.findByName(projectName);
        if (project != null && project.removeOfficer(officerNRIC)) {
            return projectDB.update(project);
        }
        return false;
    }

    /**
     * Sets the officer slot for a project.
     * 
     * @param projectName The name of the project.
     * @param slot The slot to be assigned to the project.
     * @return True if the officer slot is successfully set, false otherwise.
     */
    public boolean setOfficerSlot(String projectName, int slot) {
        Project project = ProjectDatabase.findByName(projectName);
        if (project != null) {
            project.setOfficerSlot(slot);
            return projectDB.update(project);
        }
        return false;
    }

    // Visibility Management

    /**
     * Toggles the visibility of a project to applicants.
     * 
     * @param projectName The name of the project.
     * @return True if the visibility is successfully toggled, false otherwise.
     */
    public boolean toggleVisibility(String projectName) {
        Project project = ProjectDatabase.findByName(projectName);
        if (project != null) {
            project.setVisibleToApplicant(!Project.isVisibleToApplicant());
            return projectDB.update(project);
        }
        return false;
    }

    // Eligibility Check

    /**
     * Checks if an applicant is eligible for a specific project.
     * 
     * @param projectName The name of the project.
     * @param applicant The applicant to check eligibility for.
     * @return True if the applicant is eligible for the project, false otherwise.
     */
    public boolean checkEligibility(String projectName, Applicant applicant) {
        Project project = ProjectDatabase.findByName(projectName);
        return project != null && project.isEligible(applicant);
    }
    
    /**
     * Checks if a manager has any active projects.
     * 
     * @param allProjects The list of all projects.
     * @param manager The manager to check for active projects.
     * @return True if the manager has an active project, false otherwise.
     */
    public static boolean hasActiveProject(List<Project> allProjects, Manager manager) {
        for (Project project : allProjects) {
            if (manager.getNric().equals(project.getManager()) && DateTimeController.isActive(project.getOpeningDate(), project.getClosingDate())) {
                return true;
            }
        }
        return false;
    }
    
    // Officer Eligibility Checks

    /**
     * Checks if an officer can apply to a project as an applicant.
     * 
     * @param projectName The name of the project.
     * @param officer The officer to check eligibility for.
     * @return True if the officer can apply to the project, false otherwise.
     */
    public static boolean checkOfficerApplicantEligibility(String projectName, Officer officer) {
        Project project = ProjectDatabase.findByName(projectName);
        
        List<String> officers = project.getOfficerNRICs();
        for (String officerNRIC : officers) {
            if (officerNRIC.equals(officer.getNric())) {
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * Checks if an officer can apply to handle a project.
     * 
     * @param projectName The name of the project.
     * @param officer The officer to check eligibility for.
     * @return True if the officer can handle the project, false otherwise.
     */
    public static boolean checkOfficerHandleEligibility(String projectName, Officer officer) {
        ProjectApplication application = ProjectApplicationDatabase.getApplicationByApplicantId(officer.getNric());
        
        if (application != null && application.getProjectName().equals(projectName)) {
            return false;
        }
        
        return true;
    }
}
