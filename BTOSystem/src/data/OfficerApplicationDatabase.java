package data;

import java.io.*;
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
	
	public void writeApplication(OfficerApplication application) {
            writeFile(officerApplicationDatabaseFilePath,application.getApplicantId(),application.getApplicationStatus(),application.getProjectName());
	}
	
	public OfficerApplication getApplicationByApplicantId(String applicantId) {
		try (BufferedReader reader = new BufferedReader(new FileReader(officerApplicationDatabaseFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 3 && parts[0].equals(applicantId)) { // Check if applicantId matches
                    String applicationStatus = parts[1];
                    String projectName = parts[2]; 
                    return new OfficerApplication(applicantId, applicationStatus, projectName);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading Applications from file: " + e.getMessage(), e);
        }
        return null; // Return null
	}
	
	public OfficerApplication getApplicationByProjectName(String projectName) {
		try (BufferedReader reader = new BufferedReader(new FileReader(officerApplicationDatabaseFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 3 && parts[2].equals(projectName)) { // Check if projectName matches
                    String applicantId = parts[0];
                    String applicationStatus = parts[1]; 
                    return new OfficerApplication(applicantId, applicationStatus, projectName);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading Applications from file: " + e.getMessage(), e);
        }
        return null; // Return null
	}
}