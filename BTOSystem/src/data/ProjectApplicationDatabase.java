package data;

import java.io.*;
import java.util.*;
import controllers.AuthenticationController;
import entities.*;
import util.*;

/**
 * Provides methods for interacting with the project application database.
 * Includes functionality to read, write, update, and retrieve project applications from a file-based database.
 */
public class ProjectApplicationDatabase implements FilePath, Database {

    /**
     * Constructor for the ProjectApplicationDatabase class.
     */
    public ProjectApplicationDatabase() {
        
    }

    /**
     * Writes a new project application to the database.
     * 
     * @param application The project application to be written.
     */
    public static void writeApplication(ProjectApplication application) {
        String flatBooked = application.getFlatBooked() ? "true" : "false";
        Database.writeFile(projectApplicationDatabaseFilePath,
            application.getApplicantId(),
            application.getMaritalStatus(),
            Integer.toString(application.getAge()),
            application.getApplicationStatus(),
            application.getProjectName(),
            application.getFlatType(),
            flatBooked
        );
    }

    /**
     * Reads all project applications from the database.
     * 
     * @return A list of all project applications.
     */
    public static List<ProjectApplication> readApplication() {
        List<String> applications = Database.readFile(projectApplicationDatabaseFilePath);
        List<ProjectApplication> projectApplications = new ArrayList<>();
        for (String application : applications) {
            String[] parts = application.split("\\|");
            String applicantId = parts[0];
            String maritalStatus = parts[1];
            String age = parts[2];
            String status = parts[3];
            String projectName = parts[4];
            String flatType = parts[5];
            String flatBooked = parts[6];
            ProjectApplication pa = new ProjectApplication(
                applicantId,
                maritalStatus,
                Integer.parseInt(age),
                status,
                projectName,
                flatType,
                Boolean.parseBoolean(flatBooked)
            );
            projectApplications.add(pa);
        }
        return projectApplications;
    }

    /**
     * Updates an existing project application in the database.
     * 
     * @param application The project application to be updated.
     */
    public static void updateApplication(ProjectApplication application) {
        List<String> lines = Database.readFile(projectApplicationDatabaseFilePath);
        List<String> newLines = new ArrayList<>();
        
        for (String line : lines) {
            String[] parts = line.split("\\|");
            if (parts.length == 7 && parts[0].equals(application.getApplicantId())) {
                newLines.add(application.getApplicantId() + "|" +
                    application.getMaritalStatus() + "|" +
                    application.getAge() + "|" +
                    application.getApplicationStatus() + "|" +
                    application.getProjectName() + "|" +
                    application.getFlatType() + "|" +
                    String.valueOf(application.getFlatBooked())
                );
            } else {
                newLines.add(line);
            }
        }
        Database.updateFile(projectApplicationDatabaseFilePath, newLines);
    }

    /**
     * Retrieves a single project application by the applicant's ID.
     * 
     * @param nric The ID of the applicant whose application is to be retrieved.
     * @return The project application corresponding to the specified applicant ID, or null if not found.
     */
    public static ProjectApplication getApplicationByApplicantId(String nric) {
        List<String> lines = Database.readFile(projectApplicationDatabaseFilePath);
        for (String line : lines) {
            String[] parts = line.split("\\|");
            if (parts.length == 7 && parts[0].equals(nric)) {
                String maritalStatus = parts[1];
                String age = parts[2];
                String applicationStatus = parts[3];
                String projectName = parts[4];  
                String flatType = parts[5];
                boolean flatBooked = Boolean.parseBoolean(parts[6]);
                return new ProjectApplication(nric, maritalStatus, Integer.parseInt(age), applicationStatus, projectName, flatType, flatBooked);
            }
        }
        return null; // Return null if no matching application is found
    }

    /**
     * Retrieves a list of project applications by project name.
     * 
     * @param projectName The project name whose applications are to be retrieved.
     * @return A list of project applications for the specified project name.
     */
    public static List<ProjectApplication> getApplicationsByProjectName(String projectName) {
        List<String> lines = Database.readFile(projectApplicationDatabaseFilePath);
        List<ProjectApplication> applications = new ArrayList<>();
        for (String line : lines) {
            String[] parts = line.split("\\|");
            if (parts.length == 7 && parts[4].equals(projectName)) {
                String nric = parts[0];
                String maritalStatus = parts[1];
                String age = parts[2];
                String applicationStatus = parts[3];
                String flatType = parts[5];
                boolean flatBooked = Boolean.parseBoolean(parts[6]);
                applications.add(new ProjectApplication(nric, maritalStatus, Integer.parseInt(age), applicationStatus, projectName, flatType, flatBooked));
            }
        }
        return applications; // Return the list of applications
    }
}
