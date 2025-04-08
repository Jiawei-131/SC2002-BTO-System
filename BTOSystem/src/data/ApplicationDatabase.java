package data;

import java.io.*;
import java.util.*;
import java.time.LocalDateTime;
import entities.User;
import controllers.AuthenticationController;
//import entities.User;
import entities.*;


public class ApplicationDatabase {
    public ApplicationDatabase() {
    	
    }

    // Write a new application to the file
    public void writeApplication(String applicantNRIC, String applicationStatus, String projectName, String flatType, boolean booked, String officerNRIC) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("BTOSystem/src/ApplicationDatabase.txt", true))) {
            writer.write(applicantNRIC + "|"+  applicationStatus + "|"+  projectName + "|"+  flatType + "|"+  booked + "|"+  officerNRIC);
            writer.newLine();
        } catch (IOException e) {
            throw new RuntimeException("Error writing to file: " + e.getMessage(), e);
        }
    }
    
    public Application getApplicationByApplicantNRIC(String nric,AuthenticationController ac) {
        try (BufferedReader reader = new BufferedReader(new FileReader("BTOSystem/src/data/UserDatabase.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 7 && parts[1].equals(nric)) { // Check if ID matches
                    String name = parts[0];
                    int age = Integer.parseInt(parts[2]);  
                    String maritalStatus = parts[3];
                    String role=parts[4];
                    String password=parts[5];
                    boolean isVisible=Boolean.parseBoolean(parts[6]);
//                	switch(role) {
//                	case "A":
//                		// applicant                		
//                		return new Applicant(name,nric,age,maritalStatus,password,isVisible,ac,Role.APPLICANT);
//                	case "O":
//                		// officer.
//                		return new Officer(name,nric,age,maritalStatus,password,isVisible,ac,Role.OFFICER);
//                	case "M":
//                		// manager
//                		return new Manager(name,nric,age,maritalStatus,password,isVisible,ac,Role.MANAGER);
//                	default:
//                		return null;
//                	}
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading Users from file: " + e.getMessage(), e);
        }
        return null; // Return null if no matching patient is found
    }
}