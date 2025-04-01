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
        try (BufferedReader reader = new BufferedReader(new FileReader("data/LoginInfo.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 2) {
                    String userID = parts[0];
//                    String salt = parts[1];
                    String hashPassword=parts[1];
//                    String hashedPassword = parts[2];
                    users.put(userID,hashPassword);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading file: " + e.getMessage(), e);
        }
        return users;
    }

    // Write a new user to the file
    public void writeUser(String userID, String hashPassword) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("DataBase/LoginInfo.txt", true))) {
            writer.write(userID + "|"+  hashPassword);
            writer.newLine();
        } catch (IOException e) {
            throw new RuntimeException("Error writing to file: " + e.getMessage(), e);
        }
    }

    public void replaceUserLine(String userID, String newSalt, String newHashedPassword) {
        File file = new File("DataBase/LoginInfo.txt");
        List<String> lines = new ArrayList<>();
        boolean found = false;

        // Construct the new line for the user
        String newUserData = userID + "|" + newHashedPassword;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith(userID + "|")) { // Check if the line matches the user ID
                    lines.add(newUserData); // Replace the line with the new user data
                    found = true;
                } else {
                    lines.add(line); // Keep the line as-is
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading the password file: " + e.getMessage(), e);
        }

        if (!found) {
            throw new IllegalArgumentException("User with ID " + userID + " not found.");
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (String updatedLine : lines) {
                writer.write(updatedLine);
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Error writing to the password file: " + e.getMessage(), e);
        }
    }


}
