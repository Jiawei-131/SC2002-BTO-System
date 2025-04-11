package entities;

import java.util.List;

import data.ProjectApplicationDatabase;

public class Project {
	public String name;
	public String neighbourhood;
	public int numberOfType1Units;
	private int numberOfType2Units;
	private String openingDate; // Unused Parameters?
	private String closingDate;
	private Manager manager;
	private List<Officer> officersInCharge;
	private int availableSlots;
	private boolean visibleToApplicant;
	private List<Enquiry> enquiryList;
	private List<ProjectApplication> applicantList;

	// Constructor?
	public Project(String name, String neighbourhood, int numberOfType1Units, int numberOfType2Units,
			String openingDate, String closingDate, Manager manager, List<Officer> officersInCharge, int availableSlots,
			boolean visibleToApplicant, List<Enquiry> enquiryList, List<ProjectApplication> applicantList) {
		super();
		this.name = name;
		this.neighbourhood = neighbourhood;
		this.numberOfType1Units = numberOfType1Units;
		this.numberOfType2Units = numberOfType2Units;
		this.openingDate = openingDate;
		this.closingDate = closingDate;
		this.manager = manager;
		this.officersInCharge = officersInCharge;
		this.availableSlots = availableSlots;
		this.visibleToApplicant = visibleToApplicant;
		this.enquiryList = enquiryList;
		this.applicantList = applicantList;
	}

	// Methods
	public void displayProjects() {
		System.out.println("Project Name: " + name);
		System.out.println("Type 1 Units: " + numberOfType1Units);
		System.out.println("Type 2 Units: " + numberOfType2Units);
		System.out.println("Available Slots: " + availableSlots);
	}

	public void viewEnquiry(Enquiry enquiry, User currentUser) {
	    if (enquiry == null) {
	        System.out.println("Enquiry not found.");
	        return;
	    }
	    if (currentUser instanceof Manager || currentUser instanceof Officer) {
	        displayEnquiryDetails(enquiry);
	    } 
	    else if (currentUser instanceof Applicant) {
	        if (enquiry.isVisibleToApplicant()) {
	            displayEnquiryDetails(enquiry);
	        } else {
	            System.out.println("You don't have permission to view this enquiry.");
	        }
	    }
	    else {
	        System.out.println("Unknown user type. Access denied.");
	    }
	}
	private void displayEnquiryDetails(Enquiry enquiry) {
	    System.out.println("---------------------");
	    System.out.println("Enquiry Details:");
	    System.out.println("ID: " + enquiry.getEnquiryID());
	    System.out.println("Question: " + enquiry.getText());
	    System.out.println("Status: " + enquiry.getStatus());
	    if (enquiry.getReply() != null) {
	        System.out.println("Reply: " + enquiry.getReply());
	    } else {
	        System.out.println("Reply: Not yet answered");
	    }
	    System.out.println("---------------------");
	}
	public void addManager(Manager manager) {
		this.manager = manager;
	}

	// Getters and Setters for NumberOfUnits
	public int getNumberOfUnits() {
		return numberOfType1Units + numberOfType2Units;
	}

	public void setNumberOfUnits(int type1, int type2) {
		this.numberOfType1Units = type1;
		this.numberOfType2Units = type2;
		this.availableSlots = getNumberOfUnits();
	}

	// Getters and Setters for AvailableSlots
	public int getAvailableSlots() {
		return availableSlots;
	}

	public void setAvailableSlots(int availableSlots) {
		this.availableSlots = availableSlots;
	}
	
	// Getters and Setters for visible
	public boolean getIsVisible() {
		return visibleToApplicant;
	}

	public void setIsVisible(boolean visible) {
		this.visibleToApplicant = visible;
	}
	
	// Check if Eligible
	// Applicant should Pass in Age and Marriage Status
	 public boolean isEligible(Applicant applicant) {
	        // Check if applicant has existing application
	        ProjectApplicationDatabase appDB = new ProjectApplicationDatabase();
	        if (appDB.getApplicationByApplicantId(applicant.getNric()) != null) {
	            return false;
	        }
	        int applicantAge = applicant.getAge();
	        boolean isMarried = applicant.getMaritalStatus().equalsIgnoreCase("married");
	        if (isMarried) {
	            if (applicantAge < 21) {
	                return false;
	            }
	        } else {
	            if (applicantAge < 35) {
	                return false;
	            }
	            // Singles can only apply for 2-Room
	            if (this.numberOfType2Units > 0) {
	                return false;
	            }
	        }
	        
	        return true;
	    }
	// Getters and Setters for Type1 and Type2 units
	public int getNumberOfType1Units() {
		return numberOfType1Units;
	}

	public void setNumberOfType1Units(int numberOfType1Units) {
		this.numberOfType1Units = numberOfType1Units;
	}

	public int getNumberOfType2Units() {
		return numberOfType2Units;
	}

	public void setNumberOfType2Units(int numberOfType2Units) {
		this.numberOfType2Units = numberOfType2Units;
	}
}