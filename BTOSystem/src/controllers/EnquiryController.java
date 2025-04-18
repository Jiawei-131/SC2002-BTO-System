package controllers;

import data.EnquiryDatabase;
import data.ProjectDatabase;
import entities.Enquiry;
import entities.Project;
import entities.Officer;

import java.util.ArrayList;
import java.util.List;
/**
 * Controller class for managing and handling enquiries related to projects.
 * Provides methods to add, retrieve, update, and delete enquiries.
 */
public class EnquiryController {

    /**
     * Adds a new enquiry to a specific project.
     * 
     * @param projectName The name of the project the enquiry is related to.
     * @param userNRIC The NRIC of the user submitting the enquiry.
     * @param content The content of the enquiry.
     */
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

	   /**
     * Retrieves all enquiries in the system.
     * 
     * @return A list of all enquiries.
     */
    public List<Enquiry> getAllEnquiries() {
        return EnquiryDatabase.loadAll();
    }


    /**
     * Retrieves all enquiries submitted by a specific user.
     * 
     * @param userNRIC The NRIC of the user whose enquiries are to be retrieved.
     * @return A list of the user's enquiries.
     */
    public List<Enquiry> getUserEnquiries(String userNRIC) {
        List<Enquiry> userEnquiries = new ArrayList<>();
        for (Enquiry e : EnquiryDatabase.loadAll()) {
            if (userNRIC.trim().equalsIgnoreCase(e.getUserNRIC().trim())) {
                userEnquiries.add(e);
            }
        }
        return userEnquiries;
    }

    /**
     * Finds an enquiry by its ID.
     * 
     * @param id The ID of the enquiry.
     * @return The enquiry with the specified ID, or null if not found.
     */
    public Enquiry findEnquiryById(String id) {
        try {
            int enquiryId = Integer.parseInt(id);
            return EnquiryDatabase.findById(enquiryId);
        } catch (NumberFormatException e) {
            System.out.println("Invalid Enquiry ID format.");
            return null;
        }
    }

    /**
     * Edits the content of an existing enquiry.
     * 
     * @param id The ID of the enquiry to be edited.
     * @param userNRIC The NRIC of the user who is attempting to edit the enquiry.
     * @param newText The new content for the enquiry.
     * @return True if the enquiry was successfully edited, false otherwise.
     */
    public boolean editEnquiry(String id, String userNRIC, String newText) {
        Enquiry e = findEnquiryById(id);
        if (e != null && userNRIC.equals(e.getUserNRIC())) {
            e.setText(newText);
            return EnquiryDatabase.update(e);
        }
        return false;
    }

    /**
     * Deletes an existing enquiry.
     * 
     * @param id The ID of the enquiry to be deleted.
     * @param userNRIC The NRIC of the user who is attempting to delete the enquiry.
     * @return True if the enquiry was successfully deleted, false otherwise.
     */
    public boolean deleteEnquiry(String id, String userNRIC) {
        Enquiry e = findEnquiryById(id);
        if (e != null && userNRIC.equals(e.getUserNRIC())) {
            return EnquiryDatabase.delete(e.getEnquiryID());
        }
        return false;
    }

    /**
     * Replies to an enquiry.
     * 
     * @param id The ID of the enquiry to reply to.
     * @param reply The reply content.
     */
    public void replyEnquiry(int id, String reply) {
        Enquiry e = EnquiryDatabase.findById(id);
        if (e != null) {
            e.replyEnquiry(id, reply);
            EnquiryDatabase.update(e);
        }
    }
}
