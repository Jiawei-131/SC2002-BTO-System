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
    
    // update
    public void updateApplication(ProjectApplication application) {
        List<String> lines = readFile(projectApplicationDatabaseFilePath);
        List<String> newLines = new ArrayList<>();
        
        for (String line : lines) {
        	 String[] parts = line.split("\\|");
        	 if (parts.length == 5 && parts[0].equals(application.getApplicantId())) {
             	String flatBooked = application.getFlatBooked() ? "true" : "false";
             	newLines.add(application.getApplicantId() + "|" + application.getApplicationStatus() + "|" +
             	application.getProjectName() + "|" + application.getFlatType() + "|" + flatBooked);
             }
        	 else {
        		 newLines.add(line);
        	 }
        }
        updateFile(projectApplicationDatabaseFilePath,newLines);
    }
//    	try {
//    		File inputFile = new File(projectApplicationDatabaseFilePath);
//            File tempFile = new File("BTOSystem/src/data/ProjectApplicationDatabaseTemp.txt");
//    		
//    		BufferedReader reader = new BufferedReader(new FileReader(inputFile));
//            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
//        	
//            String currentLine;
//            boolean updated = false;
//
//            while ((currentLine = reader.readLine()) != null) {
//                String[] parts = currentLine.split("\\|");
//
//                if (parts.length == 5 && parts[0].equals(application.getApplicantId())) {
//                	String flatBooked = application.getFlatBooked() ? "true" : "false";
//                    writer.write(application.getApplicantId() + "|" + application.getApplicationStatus() + "|" + application.getProjectName() + "|" + application.getFlatType() + "|" + flatBooked); // Write the updated line
//                    updated = true;
//                } else {
//                    writer.write(currentLine); // Write original line
//                }
//                writer.newLine(); // Add newline after each line
//            }
//            
//            reader.close();
//            writer.close();
//
//            // Replace original file with updated file
//            if (updated) {
//                inputFile.delete();
//                tempFile.renameTo(inputFile);
//            } else {
//                tempFile.delete(); // Clean up if no change was made
//            }
//    	} catch (IOException e) {
//            throw new RuntimeException("Error updating Project Application Database: " + e.getMessage(), e);
//    	}
//    	
//    }
    
    public ProjectApplication getApplicationByApplicantId(String nric) {
    	List<String> lines = readFile(projectApplicationDatabaseFilePath);
            for (String line : lines)
            {
            	String[] parts = line.split("\\|");
            	if (parts.length == 5 && parts[0].equals(nric)) { // Check if applicantNRIC matches
                    String applicationStatus = parts[1];
                    String projectName = parts[2];  
                    String flatType = parts[3];
                    boolean flatBooked=Boolean.parseBoolean(parts[4]);
                    return new ProjectApplication(nric, applicationStatus, projectName, flatType, flatBooked);
                }
            }
            return null; // Return null if no matching patient is found
    }
    
//    public ProjectApplication getApplicationByApplicantId(String nric) {
//        try (BufferedReader reader = new BufferedReader(new FileReader(projectApplicationDatabaseFilePath))) {
//            String line;
//            while ((line = reader.readLine()) != null) {
//                String[] parts = line.split("\\|");
//                if (parts.length == 5 && parts[0].equals(nric)) { // Check if applicantNRIC matches
//                    String applicationStatus = parts[1];
//                    String projectName = parts[2];  
//                    String flatType = parts[3];
//                    boolean flatBooked=Boolean.parseBoolean(parts[4]);
//                    return new ProjectApplication(nric, applicationStatus, projectName, flatType, flatBooked);
//                }
//            }
//        } catch (IOException e) {
//            throw new RuntimeException("Error reading Applications from file: " + e.getMessage(), e);
//        }
//        return null; // Return null if no matching patient is found
//    }
}