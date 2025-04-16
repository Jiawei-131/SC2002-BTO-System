package controllers;

import entities.Enquiry;
import data.EnquiryDatabase;

import java.util.ArrayList;
import java.util.List;

public class EnquiryController {

    // All methods now interact directly with the database

    public void addEnquiry(String projectId, String userNRIC, String content) {
        Enquiry enquiry = new Enquiry(content, "Pending", userNRIC);
        EnquiryDatabase.save(enquiry);
        System.out.println("Enquiry added! ID: " + enquiry.getEnquiryID());
    }

    public List<Enquiry> getAllEnquiries() {
        return EnquiryDatabase.loadAll();
    }

    public List<Enquiry> getUserEnquiries(String userNRIC) {
        List<Enquiry> userEnquiries = new ArrayList<>();
        for (Enquiry e : EnquiryDatabase.loadAll()) {
        	System.out.println("Checking: " + e.getUserNRIC() + " vs " + userNRIC);
        	if (userNRIC.trim().equalsIgnoreCase(e.getUserNRIC().trim())) {
                userEnquiries.add(e);
            }
        }
        return userEnquiries;
    }

    public Enquiry findEnquiryById(String id) {
        try {
            int enquiryId = Integer.parseInt(id);
            return EnquiryDatabase.findById(enquiryId);
        } catch (NumberFormatException e) {
            System.out.println("Invalid Enquiry ID format.");
            return null;
        }
    }

    public boolean editEnquiry(String id, String userNRIC, String newText) {
        Enquiry e = findEnquiryById(id);
        if (e != null && userNRIC.equals(e.getUserNRIC())) {
            e.setText(newText);
            return EnquiryDatabase.update(e);
        }
        return false;
    }

    public boolean deleteEnquiry(String id, String userNRIC) {
        Enquiry e = findEnquiryById(id);
        if (e != null && userNRIC.equals(e.getUserNRIC())) {
            return EnquiryDatabase.delete(e.getEnquiryID());
        }
        return false;
    }

    public void replyEnquiry(int id, String reply) {
        Enquiry e = EnquiryDatabase.findById(id);
        if (e != null) {
            e.replyEnquiry(id, reply);
            EnquiryDatabase.update(e);
        }
    }
}
