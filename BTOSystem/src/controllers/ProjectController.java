package controllers;

import entities.Applicant;
import entities.Manager;
import entities.Officer;
import entities.Project;
import data.ProjectDatabase;
import data.UserDatabase;
import java.util.List;

public class ProjectController {
    private ProjectDatabase projectDB;
    private UserDatabase userDB;

    public ProjectController() {
        this.projectDB = new ProjectDatabase();
        this.userDB = new UserDatabase();
    }

    // CRUD Operations
    public boolean createProject(Project project) {
        return ProjectDatabase.save(project);
    }

    public Project getProject(String name) {
        return ProjectDatabase.findByName(name);
    }

    public List<Project> getAllProjects() {
        return ProjectDatabase.loadAllProjects();
    }

    public boolean updateProject(Project project) {
        return projectDB.update(project);
    }

    public boolean deleteProject(String name) {
        return ProjectDatabase.delete(name);
    }

    // Staff Management
    public boolean assignOfficer(String projectName, String officerNRIC) {
        Project project = ProjectDatabase.findByName(projectName);
        if (project != null && project.addOfficer(officerNRIC)) {
            return projectDB.update(project);
        }
        return false;
    }

    public boolean removeOfficer(String projectName, String officerNRIC) {
        Project project = ProjectDatabase.findByName(projectName);
        if (project != null && project.removeOfficer(officerNRIC)) {
            return projectDB.update(project);
        }
        return false;
    }

    public boolean setOfficerSlot(String projectName, int slot) {
        Project project = ProjectDatabase.findByName(projectName);
        if (project != null) {
            project.setOfficerSlot(slot);
            return projectDB.update(project);
        }
        return false;
    }

    // Visibility Management
    public boolean toggleVisibility(String projectName) {
        Project project = ProjectDatabase.findByName(projectName);
        if (project != null) {
            project.setVisibleToApplicant(!Project.isVisibleToApplicant());
            return projectDB.update(project);
        }
        return false;
    }

    // Eligibility Check
    public boolean checkEligibility(String projectName, Applicant applicant) {
        Project project = ProjectDatabase.findByName(projectName);
        return project != null && project.isEligible(applicant);
    }
    
    public static boolean hasActiveProject(List<Project> allProjects,Manager manager) {
        for (Project project : allProjects) {
            if (manager.getNric().equals(project.getManager()) && DateTimeController.isActive(project.getOpeningDate(),project.getClosingDate())) {
                return true;
            }
        }
        return false;
    }
}
