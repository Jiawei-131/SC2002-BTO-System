package data;

import java.io.*;
import java.util.*;
import java.time.LocalDateTime;
import entities.User;
import controllers.AuthenticationController;
//import entities.User;
import entities.*;


public class ProjectApplicationDatabase {
    public ProjectApplicationDatabase() {
    	
    }

    // Write a new application to the file
    public void writeApplication(ProjectApplication application) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("BTOSystem/src/ProjectApplicationDatabase.txt", true))) {
        	String flatBooked = application.getFlatBooked() ? "true" : "false";
            writer.write(application.getApplicantId() + "|"+  application.getApplicationStatus() + "|"+  application.getProjectName() + "|"+  application.getFlatType() + "|"+ flatBooked + "|"+  application.getManagingOfficer());
            writer.newLine();
        } catch (IOException e) {
            throw new RuntimeException("Error writing to file: " + e.getMessage(), e);
        }
    }
    
    public ProjectApplication getApplicationByApplicantId(String nric) {
        try (BufferedReader reader = new BufferedReader(new FileReader("BTOSystem/src/data/ProjectApplicationDatabase.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 6 && parts[0].equals(nric)) { // Check if applicantNRIC matches
                    String applicationStatus = parts[1];
                    String projectName = parts[2];  
                    String flatType = parts[3];
                    boolean flatBooked=Boolean.parseBoolean(parts[4]);
                    String officerNRIC=parts[5];
                    return new ProjectApplication(nric, applicationStatus, projectName, flatType, flatBooked, officerNRIC);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading Applications from file: " + e.getMessage(), e);
        }
        return null; // Return null if no matching patient is found
    }
    
    public ProjectApplication getApplicationByOfficerId(String nric) {
        try (BufferedReader reader = new BufferedReader(new FileReader("BTOSystem/src/data/ProjectApplicationDatabase.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 6 && parts[5].equals(nric)) { // Check if applicantNRIC matches
                    String applicationStatus = parts[1];
                    String projectName = parts[2];  
                    String flatType = parts[3];
                    boolean flatBooked=Boolean.parseBoolean(parts[4]);
                    String applicantNRIC=parts[0];
                    return new ProjectApplication(applicantNRIC, applicationStatus, projectName, flatType, flatBooked, nric);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading Applications from file: " + e.getMessage(), e);
        }
        return null; // Return null if no matching patient is found
    }
}