package data;

import java.io.*;
import java.util.*;
import java.time.LocalDateTime;
import controllers.AuthenticationController;
//import entities.User;
import entities.*;
import util.*;


public class ProjectApplicationDatabase implements FilePath,Database {
    public ProjectApplicationDatabase() {
    	
    }

    // Write a new application to the file
    public static void writeApplication(ProjectApplication application) {
        	String flatBooked = application.getFlatBooked() ? "true" : "false";
            Database.writeFile(projectApplicationDatabaseFilePath,application.getApplicantId(),application.getMaritalStatus(), Integer.toString(application.getAge()), application.getApplicationStatus(),application.getProjectName(),application.getFlatType(), flatBooked);
    }
    
    public static List<ProjectApplication> readApplication()
    {
    	List<String> applications=Database.readFile(projectApplicationDatabaseFilePath);
    	List<ProjectApplication> projectApplications =new ArrayList<>();
    	for(String application: applications)
    	{
    		
            String[] parts = application.split("\\|");
            String applicantId = parts[0];
            String maritalStatus = parts[1];
            String age = parts[2];
            String status = parts[3];
            String projectName = parts[4];
            String flatType = parts[5];
            String flatBooked =parts[6];
            ProjectApplication pa = new ProjectApplication(applicantId, maritalStatus, Integer.parseInt(age), status, projectName, flatType,Boolean.parseBoolean(flatBooked));
            projectApplications.add(pa);
    	}
    	return projectApplications;
    }
    
    // update
    public static void updateApplication(ProjectApplication application) {
        List<String> lines = Database.readFile(projectApplicationDatabaseFilePath);
        List<String> newLines = new ArrayList<>();
        
        for (String line : lines) {
        	 String[] parts = line.split("\\|");
        	 if (parts.length == 7 && parts[0].equals(application.getApplicantId())) {
             	newLines.add(application.getApplicantId() + "|" + application.getMaritalStatus() + "|" + application.getAge() + "|" + application.getApplicationStatus() + "|" +
             	application.getProjectName() + "|" + application.getFlatType() + "|" + String.valueOf(application.getFlatBooked()));
             }
        	 else {
        		 newLines.add(line);
        	 }
        }
        Database.updateFile(projectApplicationDatabaseFilePath,newLines);
    }
    
    public static ProjectApplication getApplicationByApplicantId(String nric) {
    	List<String> lines = Database.readFile(projectApplicationDatabaseFilePath);
            for (String line : lines)
            {
            	String[] parts = line.split("\\|");
            	if (parts.length == 7 && parts[0].equals(nric)) { // Check if applicantNRIC matches
                    String maritalStatus = parts[1];
                    String age = parts[2];
            		String applicationStatus = parts[3];
                    String projectName = parts[4];  
                    String flatType = parts[5];
                    boolean flatBooked=Boolean.parseBoolean(parts[6]);
                    return new ProjectApplication(nric, maritalStatus, Integer.parseInt(age), applicationStatus, projectName, flatType, flatBooked);
                }
            }
            return null; // Return null if no matching application is found
    }
    
    public static List<ProjectApplication> getApplicationsByProjectName(String projectName) {
    	List<String> lines = Database.readFile(projectApplicationDatabaseFilePath);
    	List<ProjectApplication> applications = new ArrayList<>();
            for (String line : lines)
            {
            	String[] parts = line.split("\\|");
            	if (parts.length == 7 && parts[4].equals(projectName)) { // Check if projectName matches
            		String nric = parts[0];
                    String maritalStatus = parts[1];
                    String age = parts[2];
            		String applicationStatus = parts[3];
                    String flatType = parts[5];
                    boolean flatBooked=Boolean.parseBoolean(parts[6]);
                    applications.add(new ProjectApplication(nric, maritalStatus, Integer.parseInt(age), applicationStatus, projectName, flatType, flatBooked));
                }
            }
            return applications; // Return null if no matching application is found
    }

}