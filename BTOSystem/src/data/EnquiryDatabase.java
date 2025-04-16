package data;

import entities.Enquiry;
import util.Database;
import util.FilePath;

import java.util.ArrayList;
import java.util.List;

public class EnquiryDatabase implements Database, FilePath {

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

    public static Enquiry findById(int id) {
        for (Enquiry e : loadAll()) {
            if (e.getEnquiryID() == id) return e;
        }
        return null;
    }

    public static List<Enquiry> loadAll() {
        List<Enquiry> enquiries = new ArrayList<>();
        List<String> lines = Database.readFile(enquiryDatabaseFilePath);
        int maxId = 0;

        for (String line : lines) {
            String[] parts = line.split("\\|");
            if (parts.length >= 7) {
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


    private static Enquiry parseEnquiry(String[] parts) {
        try {
            int enquiryID = Integer.parseInt(parts[0]);
            String text = parts[1];
            String status = parts[2];
            String reply = parts[3];
            boolean visibleToApplicant = Boolean.parseBoolean(parts[4]);
            boolean visibleToManager = Boolean.parseBoolean(parts[5]);
            String userNRIC = parts[6];

            Enquiry e = new Enquiry(text, status, userNRIC);
            // Set ID manually
            java.lang.reflect.Field idField = Enquiry.class.getDeclaredField("enquiryID");
            idField.setAccessible(true);
            idField.setInt(e, enquiryID);

            e.setVisibleToApplicant(visibleToApplicant);
            e.setVisibleToManager(visibleToManager);

            return e;
        } catch (Exception ex) {
            System.err.println("Error parsing enquiry: " + ex.getMessage());
            return null;
        }
    }


    private static String formatEnquiry(Enquiry e) {
        return String.format("%d|%s|%s|%s|%b|%b|%s",
            e.getEnquiryID(),
            e.getText(),
            e.getStatus(),
            e.getReply(),
            e.getVisibleToApplicant(),
            e.getVisibleToManager(),
            e.getUserNRIC());
    }


}
