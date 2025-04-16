package controllers;

import entities.Enquiry;
import java.util.ArrayList;
import java.util.List;

public class EnquiryController {
    private List<Enquiry> enquiries;

    public EnquiryController() {
        enquiries = new ArrayList<>();
    }

    // Create a new enquiry (assuming status and userNRIC provided)
    public void addEnquiry(String projectId, String userNRIC, String content) {
        Enquiry enquiry = new Enquiry(content, "Pending", userNRIC);  // Assuming 'Pending' default status
        enquiries.add(enquiry);
        System.out.println("Enquiry added! ID: " + enquiry.getEnquiryID());
    }

    public List<Enquiry> getAllEnquiries() {
        return enquiries;
    }

    public List<Enquiry> getUserEnquiries(String userNRIC) {
        List<Enquiry> userEnquiries = new ArrayList<>();
        for (Enquiry e : enquiries) {
            if (userNRIC.equals(e.getUserNRIC())) {
                userEnquiries.add(e);
            }
        }
        return userEnquiries;
    }

    public Enquiry findEnquiryById(String id) {
        try {
            int enquiryId = Integer.parseInt(id);
            for (Enquiry e : enquiries) {
                if (e.getEnquiryID() == enquiryId) {
                    return e;
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid Enquiry ID format.");
        }
        return null;
    }

    public boolean editEnquiry(String id, String userNRIC, String newText) {
        Enquiry e = findEnquiryById(id);
        if (e != null && userNRIC.equals(e.getUserNRIC())) {
            e.setText(newText);
            return true;
        }
        return false;
    }

    public boolean deleteEnquiry(String id, String userNRIC) {
        Enquiry e = findEnquiryById(id);
        if (e != null && userNRIC.equals(e.getUserNRIC())) {
            enquiries.remove(e);
            return true;
        }
        return false;
    }

    public void replyEnquiry(int id, String reply) {
        Enquiry e = findEnquiryById(String.valueOf(id));
        if (e != null) {
            e.replyEnquiry(id, reply);
        }
    }
}
