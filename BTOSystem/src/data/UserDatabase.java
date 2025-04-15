package data;

import java.io.*;
import java.util.*;
import java.time.LocalDateTime;
import entities.User;
import controllers.AuthenticationController;
//import entities.User;
import entities.Applicant;
import entities.Officer;
import entities.Manager;
import entities.Role;
import util.*;


public class UserDatabase implements Database,FilePath{

    
    // Read all users from the file
    public Map<String, String> readUsers() {
        Map<String, String> users = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(loginFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 2) {
                    String userID = parts[0];
//                    String salt = parts[1];
                    String password=parts[1];
//                    String hashedPassword = parts[2];
                    users.put(userID,password);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading file: " + e.getMessage(), e);
        }
        return users;
    }
    
    public void updateUserPassword(String userID, String newPassword) {
    	List<String> lines = new ArrayList<>();
    	List<String> newLines = new ArrayList<>();
    	lines = Database.readFile(loginFilePath);
        for (String line : lines) {
            String[] parts = line.split("\\|", 2);
            if (parts[0].equals(userID)) {
                newLines.add(userID + "|" + newPassword);  // Update line
            } else {
                newLines.add(line);  // Keep line unchanged
            }
        }

        updateFile(loginFilePath,newLines);
    }
    
    public User getUserById(String nric,AuthenticationController ac) {
    	List<String> lines = Database.readFile(userDatabaseFilePath);
    	List<String> linesLogin = Database.readFile(loginFilePath);
    	 String password=null;
    	for (String line : lines) {
            String[] parts = line.split("\\|");
            if (parts.length == 6 && parts[1].equals(nric)) { // Check if ID matches
                String name = parts[0];
                int age = Integer.parseInt(parts[2]);  
                String maritalStatus = parts[3];
                String role=parts[4]; 
                boolean isVisible=Boolean.parseBoolean(parts[5]);
                for(String lineLogin: linesLogin)
                {
                	parts = lineLogin.split("\\|");
                	if(parts.length == 2 && parts[0].equals(nric)) {
                        password=parts[1];
                    }
                }
            	switch(role) {
            	case "A":
            		// applicant                		
            		return new Applicant(name,nric,age,maritalStatus,password,isVisible,ac,Role.APPLICANT);
            	case "O":
            		// officer.
            		return new Officer(name,nric,age,maritalStatus,password,isVisible,ac,Role.OFFICER);
            	case "M":
            		// manager
            		return new Manager(name,nric,age,maritalStatus,password,isVisible,ac,Role.MANAGER);
            	default:
            		return null;
            	}
            }
    	}
    	return null; // Return null if no matching patient is found
    }
//        try (BufferedReader reader = new BufferedReader(new FileReader(userDatabaseFilePath))) {
//            String line,password=null;
//            while ((line = reader.readLine()) != null) {
//                String[] parts = line.split("\\|");
//                if (parts.length == 6 && parts[1].equals(nric)) { // Check if ID matches
//                    String name = parts[0];
//                    int age = Integer.parseInt(parts[2]);  
//                    String maritalStatus = parts[3];
//                    String role=parts[4]; 
//                    boolean isVisible=Boolean.parseBoolean(parts[5]);
//                    BufferedReader reader1 = new BufferedReader(new FileReader(loginFilePath));
//                    while ((line = reader1.readLine()) != null) {
//                    	parts = line.split("\\|");
//                    	if(parts.length == 2 && parts[0].equals(nric)) {
//                            password=parts[1];
//                        }
//                    }
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
//                }
//            }
//        } catch (IOException e) {
//            throw new RuntimeException("Error reading Users from file: " + e.getMessage(), e);
//        }
//        return null; // Return null if no matching patient is found
    
}

