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


public class UserDatabase {
    private final String filePath;
    private String filePathLogin;
    private String filePathUserDatabase;

    public UserDatabase(String filePath) {
        this.filePath = filePath;
        this.filePathLogin=filePath+"LoginInfo.txt";
        this.filePathUserDatabase=filePath+"UserDatabase.txt";
    }
    // Read all users from the file
    public Map<String, String> readUsers() {
        Map<String, String> users = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePathLogin))) {
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

    // Write a new user to the file
    public void writeUser(String userID, String hashPassword) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePathLogin, true))) {
            writer.write(userID + "|"+  hashPassword);
            writer.newLine();
        } catch (IOException e) {
            throw new RuntimeException("Error writing to file: " + e.getMessage(), e);
        }
    }
    public void writeUser(String name, String userID,int age,String maritalStatus,String role,boolean isVisible) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePathUserDatabase, true))) {
            writer.write(name + "|"+  userID+ "|"+  age+ "|"+  maritalStatus+ "|"+  role+ "|"+  isVisible);
            writer.newLine();
            
        } catch (IOException e) {
            throw new RuntimeException("Error writing to file: " + e.getMessage(), e);
        }
    }
    
    public void updateUserPassword(String userID, String newPassword) {
    	List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePathLogin))) {
            String line;
            
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\|", 2);
                if (parts[0].equals(userID)) {
                    lines.add(userID + "|" + newPassword);  // Update line
                } else {
                    lines.add(line);  // Keep line unchanged
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("BTOSystem/src/data/LoginInfo.txt"))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Error writing to file: " + e.getMessage(), e);
        }
    }
    
    public User getUserById(String nric,AuthenticationController ac) {
        try (BufferedReader reader = new BufferedReader(new FileReader("BTOSystem/src/data/UserDatabase.txt"))) {
            String line,password=null;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 6 && parts[1].equals(nric)) { // Check if ID matches
                    String name = parts[0];
                    int age = Integer.parseInt(parts[2]);  
                    String maritalStatus = parts[3];
                    String role=parts[4]; 
                    boolean isVisible=Boolean.parseBoolean(parts[5]);
                    BufferedReader reader1 = new BufferedReader(new FileReader("BTOSystem/src/data/LoginInfo.txt"));
                    while ((line = reader1.readLine()) != null) {
                    	parts = line.split("\\|");
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
        } catch (IOException e) {
            throw new RuntimeException("Error reading Users from file: " + e.getMessage(), e);
        }
        return null; // Return null if no matching patient is found
    }
}

