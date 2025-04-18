package data;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import entities.*;
import util.*;

/**
 * Provides methods for interacting with the officer application database.
 * Includes functionality to read, update, and write officer applications to a file-based database.
 */
public class OfficerApplicationDatabase implements FilePath, Database {

    /**
     * Constructor for the OfficerApplicationDatabase class.
     */
    public OfficerApplicationDatabase() {
        
    }

    /**
     * Reads all officer applications from the database.
     * 
     * @return A list of all officer applications.
     */
    public static List<OfficerApplication> readApplication() {
        List<String> applications = Database.readFile(officerApplicationDatabaseFilePath);
        List<OfficerApplication> projectApplications = new ArrayList<>();
        for (String application : applications) {
            String[] parts = application.split("\\|");
            String applicantId = parts[0];
            String status = parts[1];
            String projectName = parts[2];
            OfficerApplication pa = new OfficerApplication(applicantId, status, projectName);
            projectApplications.add(pa);
        }
        return projectApplications;
    }

    /**
     * Updates an existing officer application in the database.
     * 
     * @param application The officer application to be updated.
     */
    public static void updateApplication(OfficerApplication application) {
        List<String> lines = Database.readFile(officerApplicationDatabaseFilePath);
        List<String> newLines = new ArrayList<>();
        
        for (String line : lines) {
            String[] parts = line.split("\\|");
            if (parts.length == 3 && parts[0].equals(application.getApplicantId())) {
                // Update the officer application with the new data
                newLines.add(application.getApplicantId() + "|" + application.getApplicationStatus() + "|" +
                    application.getProjectName());
            } else {
                newLines.add(line);
            }
        }
        Database.updateFile(officerApplicationDatabaseFilePath, newLines);
    }

    /**
     * Writes a new officer application to the database.
     * 
     * @param application The officer application to be written.
     */
    public static void writeApplication(OfficerApplication application) {
        Database.writeFile(officerApplicationDatabaseFilePath, 
            application.getApplicantId(), application.getApplicationStatus(), application.getProjectName());
    }

    /**
     * Retrieves a single officer application by the applicant's ID.
     * 
     * @param applicantId The ID of the applicant whose application is to be retrieved.
     * @return The officer application corresponding to the specified applicant ID, or null if not found.
     */
    public static OfficerApplication getApplicationByApplicantId(String applicantId) {
        List<String> lines = Database.readFile(officerApplicationDatabaseFilePath);
        for (String line : lines) {
            String[] parts = line.split("\\|");
            if (parts.length == 3 && parts[0].equals(applicantId)) {
                String applicationStatus = parts[1];
                String projectName = parts[2];
                return new OfficerApplication(applicantId, applicationStatus, projectName);
            }
        }
        return null; // Return null if no matching application is found
    }

    /**
     * Retrieves a list of officer applications by the applicant's ID.
     * 
     * @param applicantId The ID of the applicant whose applications are to be retrieved.
     * @return A list of officer applications for the specified applicant ID.
     */
    public static List<OfficerApplication> getApplicationsByApplicantId(String applicantId) {
        List<String> lines = Database.readFile(officerApplicationDatabaseFilePath);
        List<OfficerApplication> applications = new ArrayList<>();
        for (String line : lines) {
            String[] parts = line.split("\\|");
            if (parts.length == 3 && parts[0].equals(applicantId)) {
                String applicationStatus = parts[1];
                String projectName = parts[2];
                applications.add(new OfficerApplication(applicantId, applicationStatus, projectName));
            }
        }
        return applications;
    }

    /**
     * Retrieves a single officer application by the project name.
     * 
     * @param projectName The project name whose officer application is to be retrieved.
     * @return The officer application corresponding to the specified project name, or null if not found.
     */
    public OfficerApplication getApplicationByProjectName(String projectName) {
        List<String> lines = Database.readFile(officerApplicationDatabaseFilePath);
        for (String line : lines) {
            String[] parts = line.split("\\|");
            if (parts.length == 3 && parts[2].equals(projectName)) {
                String applicantId = parts[0];
                String applicationStatus = parts[1];
                return new OfficerApplication(applicantId, applicationStatus, projectName);
            }
        }
        return null; // Return null if no matching application is found
    }
}
