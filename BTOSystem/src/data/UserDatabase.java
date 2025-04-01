package data;

import java.io.*;
import java.util.*;
import java.time.LocalDateTime;

public class UserDatabase {

    private final String filePath;

    public UserDatabase(String filePath) {
        this.filePath = filePath;
    }
    // Read all users from the file
    public Map<String, String> readUsers() {
        Map<String, String> users = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("BTOSystem/src/data/LoginInfo.txt"))) {
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
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("BTOSystem/src/LoginInfo.txt", true))) {
            writer.write(userID + "|"+  hashPassword);
            writer.newLine();
        } catch (IOException e) {
            throw new RuntimeException("Error writing to file: " + e.getMessage(), e);
        }
    }
}
