package entities;

import java.util.List;

import data.ProjectApplicationDatabase;

import java.util.ArrayList;

public class Project {
	// Project details
	private String name;
	private String neighbourhood;

	// Unit information
	private int numberOfType1Units;
	private double type1SellingPrice;
	private int numberOfType2Units;
	private double type2SellingPrice;

	// Application dates
	private String openingDate;
	private String closingDate;

	// Staff assignments
	private String managerData;
	private List<String> officerNRICs;
	private int officerSlot;

	// Visibility and applications
	private static boolean visibleToApplicant;
	private List<Enquiry> enquiryList;
	private List<ProjectApplication> applicantList;

	// Constants
	private static final int MAX_OFFICERS = 10;

	// Constructors
	public Project() {
		this.officerNRICs = new ArrayList<>();
		this.enquiryList = new ArrayList<>();
		this.applicantList = new ArrayList<>();
	}

	public Project(String name, String neighbourhood, int numberOfType1Units, double type1SellingPrice,
			int numberOfType2Units, double type2SellingPrice, String openingDate, String closingDate,
			String managerNRIC, int officerSlot) {
		this();
		this.name = name;
		this.managerData = managerNRIC;
		this.neighbourhood = neighbourhood;
		this.numberOfType1Units = numberOfType1Units;
		this.type1SellingPrice = type1SellingPrice;
		this.numberOfType2Units = numberOfType2Units;
		this.type2SellingPrice = type2SellingPrice;
		this.openingDate = openingDate;
		this.closingDate = closingDate;
		this.officerSlot = Math.min(officerSlot, MAX_OFFICERS);
		Project.visibleToApplicant = true;
	}

	// Staff Management
	public boolean addOfficer(String officerNRIC) {
	    if (officerNRICs.size() >= officerSlot) return false;
	    if (officerNRICs.contains(officerNRIC)) return false;
	    officerNRICs.add(officerNRIC);
	    return true;
	}

	public boolean removeOfficer(String officerNRIC) {
	    return officerNRICs.remove(officerNRIC);
	}


	public void setOfficerSlot(int slot) {
		this.officerSlot = Math.min(slot, MAX_OFFICERS);
		while (officerNRICs.size() > officerSlot) {
			officerNRICs.remove(officerNRICs.size() - 1);
		}
	}

	// Eligibility check method
	public boolean isEligible(Applicant applicant) {
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
			if (this.numberOfType2Units <= 0) {
				return false;
			}
		}
		return true;
	}

	// Getters and Setters
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNeighbourhood() {
		return neighbourhood;
	}

	public void setNeighbourhood(String neighbourhood) {
		this.neighbourhood = neighbourhood;
	}

	public int getNumberOfType1Units() {
		return numberOfType1Units;
	}

	public void setNumberOfType1Units(int units) {
		this.numberOfType1Units = units;
	}

	public double getType1SellingPrice() {
		return type1SellingPrice;
	}

	public void setType1SellingPrice(double price) {
		this.type1SellingPrice = price;
	}

	public int getNumberOfType2Units() {
		return numberOfType2Units;
	}

	public void setNumberOfType2Units(int units) {
		this.numberOfType2Units = units;
	}

	public double getType2SellingPrice() {
		return type2SellingPrice;
	}

	public void setType2SellingPrice(double price) {
		this.type2SellingPrice = price;
	}

	public String getOpeningDate() {
		return openingDate;
	}

	public void setOpeningDate(String date) {
		this.openingDate = date;
	}

	public String getClosingDate() {
		return closingDate;
	}

	public void setClosingDate(String date) {
		this.closingDate = date;
	}

	public String getManager() {
		return this.managerData;
	}

	public void setManagerData(String managerData) {
		this.managerData = managerData;
	}

	public int getOfficerSlot() {
		return officerSlot;
	}
	public List<String> getOfficerNRICs() {
	    return officerNRICs;
	}
	public void setOfficerNRICs(List<String> nrics) {
	    this.officerNRICs = nrics;
	}


	public static boolean isVisibleToApplicant() {
		return visibleToApplicant;
	}

	public void setVisibleToApplicant(boolean visible) {
		Project.visibleToApplicant = visible;
	}

	public List<Enquiry> getEnquiryList() {
		return enquiryList;
	}

	public void setEnquiryList(List<Enquiry> enquiryList) {
		this.enquiryList = enquiryList;
	}

	public List<ProjectApplication> getApplicantList() {
		return applicantList;
	}

	public void setApplicantList(List<ProjectApplication> applicantList) {
		this.applicantList = applicantList;
	}

	public void setIsVisible(boolean b) {
		Project.visibleToApplicant = b;
	}
	
	@Override
	public String toString() {
		return "Project Name:" + this.name + "\nNeighbourhood: " + this.neighbourhood + "\n2-Room Units Available: " + this.numberOfType1Units
				 + "\nSelling Price: " + this.type1SellingPrice + "\n3-Room Units Available: " + this.numberOfType2Units + "\nSelling Price: " + this.type2SellingPrice
				 + "\nOpening Date: " + this.openingDate + "\nClosing Date: " + this.closingDate;
	}
}
