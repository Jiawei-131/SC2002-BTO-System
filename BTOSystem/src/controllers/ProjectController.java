package controllers;

import entities.Applicant;
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
        return projectDB.save(project);
    }

    public Project getProject(String name) {
        return projectDB.findByName(name);
    }

    public List<Project> getAllProjects() {
        return projectDB.loadAllProjects();
    }

    public boolean updateProject(Project project) {
        return projectDB.update(project);
    }

    public boolean deleteProject(String name) {
        return projectDB.delete(name);
    }

    // Staff Management
    public boolean assignOfficer(String projectName, Officer officer) {
        Project project = projectDB.findByName(projectName);
        if (project != null && project.addOfficer(officer)) {
            return projectDB.update(project);
        }
        return false;
    }

    public boolean removeOfficer(String projectName, Officer officer) {
        Project project = projectDB.findByName(projectName);
        if (project != null && project.removeOfficer(officer)) {
            return projectDB.update(project);
        }
        return false;
    }

    public boolean setOfficerSlot(String projectName, int slot) {
        Project project = projectDB.findByName(projectName);
        if (project != null) {
            project.setOfficerSlot(slot);
            return projectDB.update(project);
        }
        return false;
    }

    // Visibility Management
    public boolean toggleVisibility(String projectName) {
        Project project = projectDB.findByName(projectName);
        if (project != null) {
            project.setVisibleToApplicant(!project.isVisibleToApplicant());
            return projectDB.update(project);
        }
        return false;
    }

    // Eligibility Check
    public boolean checkEligibility(String projectName, Applicant applicant) {
        Project project = projectDB.findByName(projectName);
        return project != null && project.isEligible(applicant);
    }
}
