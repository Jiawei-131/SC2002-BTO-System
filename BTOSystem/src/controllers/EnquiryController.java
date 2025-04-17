package controllers;

import data.EnquiryDatabase;
import data.ProjectDatabase;
import entities.Enquiry;
import entities.Project;
import entities.Officer;

import java.util.ArrayList;
import java.util.List;

public class EnquiryController {

	public void addEnquiry(String projectName, String userNRIC, String content) {
	    Project project = ProjectDatabase.findByName(projectName);

	    if (project == null) {
	        System.out.println("Project not found: " + projectName);
	        return;
	    }

	    String managerNRIC = project.getManager();
	    String officerNRIC = "None";

	    List<String> officerNRICs = project.getOfficerNRICs();
	    if (!officerNRICs.isEmpty()) {
	        officerNRIC = officerNRICs.get(0);
	    }


	    Enquiry enquiry = new Enquiry(
	        projectName,
	        content,
	        "Pending",
	        userNRIC,
	        managerNRIC,
	        officerNRIC
	    );

	    EnquiryDatabase.save(enquiry);
	    System.out.println("Enquiry added! ID: " + enquiry.getEnquiryID());
	}


    public List<Enquiry> getAllEnquiries() {
        return EnquiryDatabase.loadAll();
    }

    public List<Enquiry> getUserEnquiries(String userNRIC) {
        List<Enquiry> userEnquiries = new ArrayList<>();
        for (Enquiry e : EnquiryDatabase.loadAll()) {
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
