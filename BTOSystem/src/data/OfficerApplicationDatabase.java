package data;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import entities.*;
import util.*;


public class OfficerApplicationDatabase implements FilePath,Database {
	public OfficerApplicationDatabase() {
		
	}
	
//	public void writeApplication(String applicantId, String applicationStatus, String projectName) {
//		try (BufferedWriter writer = new BufferedWriter(new FileWriter("BTOSystem/src/OfficerApplicationDatabase.txt", true))) {
//            writer.write(applicantId + "|" +  applicationStatus + "|"+  projectName);
//            writer.newLine();
//        } catch (IOException e) {
//            throw new RuntimeException("Error writing to file: " + e.getMessage(), e);
//        }
//	}
	
    public static List<OfficerApplication> readApplication()
    {
    	List<String> applications=Database.readFile(officerApplicationDatabaseFilePath);
    	List<OfficerApplication> projectApplications =new ArrayList<>();
    	for(String application: applications)
    	{
            String[] parts = application.split("\\|");
            String applicantId = parts[0];
            String status = parts[1];
            String projectName = parts[2];
            OfficerApplication pa = new OfficerApplication(applicantId, status, projectName);
            projectApplications.add(pa);
    	}
    	return projectApplications;
    }
	
    public static void updateApplication(OfficerApplication application) {
        List<String> lines = Database.readFile(officerApplicationDatabaseFilePath);
        List<String> newLines = new ArrayList<>();
        
        for (String line : lines) {
        	 String[] parts = line.split("\\|");
        	 if (parts.length == 3 && parts[0].equals(application.getApplicantId())) {
        		 //TODO Change to required field?
             	newLines.add(application.getApplicantId() + "|" + application.getApplicationStatus() + "|" +
             	application.getProjectName());
             }
        	 else {
        		 newLines.add(line);
        	 }
        }
        Database.updateFile(officerApplicationDatabaseFilePath,newLines);
    }
	
	public static void writeApplication(OfficerApplication application) {
            Database.writeFile(officerApplicationDatabaseFilePath,application.getApplicantId(),application.getApplicationStatus(),application.getProjectName());
	}
	
    public static OfficerApplication getApplicationByApplicantId(String applicantId) {
    	List<String> lines = Database.readFile(officerApplicationDatabaseFilePath);
            for (String line : lines)
            {
            	String[] parts = line.split("\\|");
            	if (parts.length == 3 && parts[0].equals(applicantId)) { // Check if applicantId matches
                  String applicationStatus = parts[1];
                  String projectName = parts[2]; 
                  return new OfficerApplication(applicantId, applicationStatus, projectName);
            	}
            }
            return null; // Return null if no matching patient is found
    }
    
    public static List<OfficerApplication> getApplicationsByApplicantId(String applicantId) {
    	List<String> lines = Database.readFile(officerApplicationDatabaseFilePath);
    	List<OfficerApplication> applications = null;
            for (String line : lines)
            {
            	String[] parts = line.split("\\|");
            	if (parts.length == 3 && parts[0].equals(applicantId)) { // Check if applicantId matches
                  String applicationStatus = parts[1];
                  String projectName = parts[2]; 
                  applications.add(new OfficerApplication(applicantId, applicationStatus, projectName));
            	}
            }
            return null; // Return null if no matching patient is found
    }
    
    public OfficerApplication getApplicationByProjectName(String projectName) {
    	List<String> lines = Database.readFile(officerApplicationDatabaseFilePath);
            for (String line : lines)
            {
            	String[] parts = line.split("\\|");
                if (parts.length == 3 && parts[2].equals(projectName)) { // Check if projectName matches
                    String applicantId = parts[0];
                    String applicationStatus = parts[1]; 
                    return new OfficerApplication(applicantId, applicationStatus, projectName);
                }
            }
            return null; // Return null if no matching patient is found
    }
	
//	public OfficerApplication getApplicationByApplicantId(String applicantId) {
//		try (BufferedReader reader = new BufferedReader(new FileReader(officerApplicationDatabaseFilePath))) {
//            String line;
//            while ((line = reader.readLine()) != null) {
//                String[] parts = line.split("\\|");
//                if (parts.length == 3 && parts[0].equals(applicantId)) { // Check if applicantId matches
//                    String applicationStatus = parts[1];
//                    String projectName = parts[2]; 
//                    return new OfficerApplication(applicantId, applicationStatus, projectName);
//                }
//            }
//        } catch (IOException e) {
//            throw new RuntimeException("Error reading Applications from file: " + e.getMessage(), e);
//        }
//        return null; // Return null
//	}
	
//	public OfficerApplication getApplicationByProjectName(String projectName) {
//		try (BufferedReader reader = new BufferedReader(new FileReader(officerApplicationDatabaseFilePath))) {
//            String line;
//            while ((line = reader.readLine()) != null) {
//                String[] parts = line.split("\\|");
//                if (parts.length == 3 && parts[2].equals(projectName)) { // Check if projectName matches
//                    String applicantId = parts[0];
//                    String applicationStatus = parts[1]; 
//                    return new OfficerApplication(applicantId, applicationStatus, projectName);
//                }
//            }
//        } catch (IOException e) {
//            throw new RuntimeException("Error reading Applications from file: " + e.getMessage(), e);
//        }
//        return null; // Return null
//	}
}