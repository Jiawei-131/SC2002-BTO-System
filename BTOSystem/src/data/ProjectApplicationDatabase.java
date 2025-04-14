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
    public void writeApplication(ProjectApplication application) {
        	String flatBooked = application.getFlatBooked() ? "true" : "false";
            writeFile(projectApplicationDatabaseFilePath,application.getApplicantId(),application.getApplicationStatus(),application.getProjectName(),application.getFlatType(), flatBooked);
    }
//    
//    public void writeApplication(String applicantId, String applicationStatus, String projectName, String flatType, boolean flatBooked) {
//        try (BufferedWriter writer = new BufferedWriter(new FileWriter("BTOSystem/src/data/ProjectApplicationDatabase.txt", true))) {
//            writer.write(applicantId + "|"+  applicationStatus + "|"+  projectName + "|"+  flatType + "|"+ Boolean.toString(flatBooked));
//            writer.newLine();
//        } catch (IOException e) {
//            throw new RuntimeException("Error writing to file: " + e.getMessage(), e);
//        }
//    }
    
    public ProjectApplication getApplicationByApplicantId(String nric) {
        try (BufferedReader reader = new BufferedReader(new FileReader(projectApplicationDatabaseFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 5 && parts[0].equals(nric)) { // Check if applicantNRIC matches
                    String applicationStatus = parts[1];
                    String projectName = parts[2];  
                    String flatType = parts[3];
                    boolean flatBooked=Boolean.parseBoolean(parts[4]);
                    return new ProjectApplication(nric, applicationStatus, projectName, flatType, flatBooked);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading Applications from file: " + e.getMessage(), e);
        }
        return null; // Return null if no matching patient is found
    }
}