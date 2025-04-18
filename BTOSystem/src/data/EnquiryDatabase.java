package data;

import entities.Enquiry;
import util.Database;
import util.FilePath;

import java.util.ArrayList;
import java.util.List;

/**
 * Provides methods for interacting with the enquiry database. 
 * Includes functionality to save, update, delete, find, and load enquiries from a file-based database.
 */
public class EnquiryDatabase implements Database, FilePath {

    /**
     * Saves an enquiry to the database.
     * 
     * @param enquiry The enquiry to be saved.
     * @return True if the enquiry is successfully saved, false otherwise.
     */
    public static boolean save(Enquiry enquiry) {
        try {
            String line = formatEnquiry(enquiry);
            Database.writeFile(enquiryDatabaseFilePath, line);
            return true;
        } catch (Exception e) {
            System.err.println("Error saving enquiry: " + e.getMessage());
            return false;
        }
    }

    /**
     * Updates an existing enquiry in the database.
     * 
     * @param enquiry The enquiry to be updated.
     * @return True if the enquiry is successfully updated, false otherwise.
     */
    public static boolean update(Enquiry enquiry) {
        List<String> lines = Database.readFile(enquiryDatabaseFilePath);
        List<String> updatedLines = new ArrayList<>();
        boolean found = false;

        for (String line : lines) {
            String[] parts = line.split("\\|");
            if (parts.length >= 1 && Integer.parseInt(parts[0]) == enquiry.getEnquiryID()) {
                found = true;
                line = formatEnquiry(enquiry);
            }
            updatedLines.add(line);
        }

        if (!found) return false;

        Database.updateFile(enquiryDatabaseFilePath, updatedLines);
        return true;
    }

    /**
     * Deletes an enquiry from the database by its ID.
     * 
     * @param enquiryId The ID of the enquiry to be deleted.
     * @return True if the enquiry is successfully deleted, false otherwise.
     */
    public static boolean delete(int enquiryId) {
        List<String> lines = Database.readFile(enquiryDatabaseFilePath);
        List<String> updatedLines = new ArrayList<>();
        boolean found = false;

        for (String line : lines) {
            String[] parts = line.split("\\|");
            if (parts.length >= 1 && Integer.parseInt(parts[0]) == enquiryId) {
                found = true;
                continue;
            }
            updatedLines.add(line);
        }

        if (!found) return false;

        Database.updateFile(enquiryDatabaseFilePath, updatedLines);
        return true;
    }

    /**
     * Finds an enquiry by its ID.
     * 
     * @param id The ID of the enquiry.
     * @return The enquiry with the specified ID, or null if not found.
     */
    public static Enquiry findById(int id) {
        for (Enquiry e : loadAll()) {
            if (e.getEnquiryID() == id) return e;
        }
        return null;
    }

    /**
     * Loads all enquiries from the database.
     * 
     * @return A list of all enquiries.
     */
    public static List<Enquiry> loadAll() {
        List<Enquiry> enquiries = new ArrayList<>();
        List<String> lines = Database.readFile(enquiryDatabaseFilePath);
        int maxId = 0;

        for (String line : lines) {
            String[] parts = line.split("\\|");
            if (parts.length == 9) {
                Enquiry enquiry = parseEnquiry(parts);
                if (enquiry != null) {
                    enquiries.add(enquiry);
                    maxId = Math.max(maxId, enquiry.getEnquiryID());
                }
            }
        }

        // Bump nextID so new enquiries get unique IDs
        try {
            java.lang.reflect.Field nextIdField = Enquiry.class.getDeclaredField("nextID");
            nextIdField.setAccessible(true);
            nextIdField.setInt(null, maxId + 1);
        } catch (Exception e) {
            System.err.println("Error updating nextID: " + e.getMessage());
        }

        return enquiries;
    }

    /**
     * Parses a line of text into an Enquiry object.
     * 
     * @param parts The string array representing the parts of an enquiry.
     * @return The parsed Enquiry object, or null if parsing failed.
     */
    private static Enquiry parseEnquiry(String[] parts) {
        try {
            int enquiryID = Integer.parseInt(parts[0]);
            String projectName = parts[1];
            String text = parts[2];
            String status = parts[3];
            String reply = parts[4];
            String officerNRIC = parts[5];
            String managerNRIC = parts[6];
            String userNRIC = parts[7];
            boolean visibleToApplicant = Boolean.parseBoolean(parts[8]);

            Enquiry e = new Enquiry(projectName, text, status, userNRIC, managerNRIC, officerNRIC);

            // Manually set enquiryID
            java.lang.reflect.Field idField = Enquiry.class.getDeclaredField("enquiryID");
            idField.setAccessible(true);
            idField.setInt(e, enquiryID);

            e.setVisibleToApplicant(visibleToApplicant);
            if (reply != null && !reply.equals("null")) {
                e.replyEnquiry(enquiryID, reply);
            }

            return e;
        } catch (Exception ex) {
            System.err.println("Error parsing enquiry: " + ex.getMessage());
            return null;
        }
    }

    /**
     * Formats an Enquiry object into a string suitable for storage.
     * 
     * @param e The Enquiry object to be formatted.
     * @return A formatted string representation of the enquiry.
     */
    private static String formatEnquiry(Enquiry e) {
        return String.format("%d|%s|%s|%s|%s|%s|%s|%s|%b",
            e.getEnquiryID(),
            e.getProjectName(),
            e.getText(),
            e.getStatus(),
            e.getReply(),
            e.getOfficerNRIC(),
            e.getManagerNRIC(),
            e.getUserNRIC(),
            e.getVisibleToApplicant()
        );
    }
}
