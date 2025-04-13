package entities;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Enquiry {
    private static final String ENQUIRY_DB_FILE = "BTOSystem/src/data/EnquiryDatabase.txt";
    
    private String enquiryID;
    private String projectName;
    private String applicantNric;
    private String text;
    private String reply;
    private String status; // "PENDING", "ANSWERED", "REJECTED"
    private boolean visibleToApplicant;

    public Enquiry(String enquiryID, String projectName, String applicantNric, String text) {
        this.enquiryID = enquiryID;
        this.projectName = projectName;
        this.applicantNric = applicantNric;
        this.text = text;
        this.reply = null;
        this.status = "PENDING";
        this.visibleToApplicant = true;
        saveToDatabase();
    }

    private void saveToDatabase() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ENQUIRY_DB_FILE, true))) {
            writer.write(String.format("%s|%s|%s|%s|%s|%s|%b\n",
                enquiryID,
                projectName,
                applicantNric,
                text,
                (reply != null) ? reply : "null",
                status,
                visibleToApplicant));
        } catch (IOException e) {
            System.err.println("Error saving enquiry to database: " + e.getMessage());
        }
    }

    public void updateInDatabase() {
        List<String> enquiries = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(ENQUIRY_DB_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length > 0 && parts[0].equals(enquiryID)) {
                    line = String.format("%s|%s|%s|%s|%s|%s|%b",
                        enquiryID,
                        projectName,
                        applicantNric,
                        text,
                        (reply != null) ? reply : "null",
                        status,
                        visibleToApplicant);
                }
                enquiries.add(line);
            }
        } catch (IOException e) {
            System.err.println("Error reading enquiries from database: " + e.getMessage());
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ENQUIRY_DB_FILE))) {
            for (String enquiry : enquiries) {
                writer.write(enquiry + "\n");
            }
        } catch (IOException e) {
            System.err.println("Error updating enquiries in database: " + e.getMessage());
        }
    }

    public static List<Enquiry> loadAllEnquiries() {
        List<Enquiry> enquiries = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(ENQUIRY_DB_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 7) {
                    Enquiry enquiry = new Enquiry(
                        parts[0], // enquiryID
                        parts[1], // projectName
                        parts[2], // applicantNric
                        parts[3]  // text
                    );
                    if (!parts[4].equals("null")) {
                        enquiry.setReply(parts[4]);
                    }
                    enquiry.setStatus(parts[5]);
                    enquiry.setVisibleToApplicant(Boolean.parseBoolean(parts[6]));
                    enquiries.add(enquiry);
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading enquiries from database: " + e.getMessage());
        }
        return enquiries;
    }

    public static List<Enquiry> getEnquiriesForProject(String projectName) {
        List<Enquiry> projectEnquiries = new ArrayList<>();
        for (Enquiry enquiry : loadAllEnquiries()) {
            if (enquiry.getProjectName().equals(projectName)) {
                projectEnquiries.add(enquiry);
            }
        }
        return projectEnquiries;
    }

    public void deleteFromDatabase() {
        List<String> enquiries = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(ENQUIRY_DB_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length > 0 && !parts[0].equals(enquiryID)) {
                    enquiries.add(line);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading enquiries from database: " + e.getMessage());
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ENQUIRY_DB_FILE))) {
            for (String enquiry : enquiries) {
                writer.write(enquiry + "\n");
            }
        } catch (IOException e) {
            System.err.println("Error updating enquiries in database: " + e.getMessage());
        }
    }

    // Getters and Setters

    public String getEnquiryID() {
        return enquiryID;
    }

    public String getProjectName() {
        return projectName;
    }

    public String getApplicantNric() {
        return applicantNric;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
        updateInDatabase();
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
        this.status = "ANSWERED";
        updateInDatabase();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
        updateInDatabase();
    }

    public boolean isVisibleToApplicant() {
        return visibleToApplicant;
    }

    public void setVisibleToApplicant(boolean visibleToApplicant) {
        this.visibleToApplicant = visibleToApplicant;
        updateInDatabase();
    }

    public void displayDetails() {
        System.out.println("Enquiry ID: " + enquiryID);
        System.out.println("Project: " + projectName);
        System.out.println("From: " + applicantNric);
        System.out.println("Question: " + text);
        System.out.println("Status: " + status);
        if (reply != null) {
            System.out.println("Reply: " + reply);
        }
        System.out.println("Visible to applicant: " + visibleToApplicant);
    }
}
