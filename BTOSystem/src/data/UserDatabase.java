package data;

import java.io.*;
import java.util.*;
import entities.User;
import controllers.AuthenticationController;
import entities.Applicant;
import entities.Officer;
import entities.Manager;
import util.*;

/**
 * Provides methods for interacting with the user database.
 * Includes functionality to read, update, and retrieve user details from a file-based database.
 */
public class UserDatabase implements Database, FilePath {

    /**
     * Reads all users from the login file and returns a map with user IDs and their passwords.
     * 
     * @return A map containing user IDs as keys and their corresponding passwords as values.
     */
    public Map<String, String> readUsers() {
        Map<String, String> users = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(loginFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 2) {
                    String userID = parts[0];
                    String password = parts[1];
                    users.put(userID, password);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading file: " + e.getMessage(), e);
        }
        return users;
    }

    /**
     * Updates the password for a given user in the login file.
     * 
     * @param userID The user ID for which the password needs to be updated.
     * @param newPassword The new password to be set.
     */
    public void updateUserPassword(String userID, String newPassword) {
        List<String> lines = new ArrayList<>();
        List<String> newLines = new ArrayList<>();
        lines = Database.readFile(loginFilePath);
        
        for (String line : lines) {
            String[] parts = line.split("\\|", 2);
            if (parts[0].equals(userID)) {
                newLines.add(userID + "|" + newPassword);  // Update the password
            } else {
                newLines.add(line);  // Keep the line unchanged
            }
        }

        Database.updateFile(loginFilePath, newLines);
    }

    /**
     * Retrieves a user by their NRIC, and returns a user instance based on their role (Applicant, Officer, Manager).
     * 
     * @param nric The NRIC of the user to retrieve.
     * @param ac The AuthenticationController instance used for the authentication process.
     * @return The corresponding User object (Applicant, Officer, or Manager), or null if not found.
     */
    public static User getUserById(String nric, AuthenticationController ac) {
        List<String> lines = Database.readFile(userDatabaseFilePath);
        List<String> linesLogin = Database.readFile(loginFilePath);
        String password = null;
        
        for (String line : lines) {
            String[] parts = line.split("\\|");
            if (parts.length == 6 && parts[1].equals(nric)) {  // Check if NRIC matches
                String name = parts[0];
                int age = Integer.parseInt(parts[2]);
                String maritalStatus = parts[3];
                String role = parts[4];
                boolean isVisible = Boolean.parseBoolean(parts[5]);

                // Find the corresponding password
                for (String lineLogin : linesLogin) {
                    parts = lineLogin.split("\\|");
                    if (parts.length == 2 && parts[0].equals(nric)) {
                        password = parts[1];
                    }
                }

                // Return the corresponding user type based on the role
                switch (role) {
                    case "A":
                        return new Applicant(name, nric, age, maritalStatus, password, isVisible, ac, Role.APPLICANT);
                    case "O":
                        return new Officer(name, nric, age, maritalStatus, password, isVisible, ac, Role.OFFICER);
                    case "M":
                        return new Manager(name, nric, age, maritalStatus, password, isVisible, ac, Role.MANAGER);
                    default:
                        return null;  // Invalid role
                }
            }
        }
        return null;  // Return null if no matching user is found
    }
}
