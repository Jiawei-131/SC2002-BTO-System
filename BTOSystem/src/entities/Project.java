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
	private List<Officer> officersInCharge;
	private int officerSlot;

	// Visibility and applications
	private static boolean visibleToApplicant;
	private List<Enquiry> enquiryList;
	private List<ProjectApplication> applicantList;

	// Constants
	private static final int MAX_OFFICERS = 10;

	// Constructors
	public Project() {
		this.officersInCharge = new ArrayList<>();
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
		this.visibleToApplicant = true;
	}

	// Staff Management
	public boolean addOfficer(Officer officer) {
		if (officersInCharge.size() >= officerSlot) {
			return false;
		}
		if (officersInCharge.contains(officer)) {
			return false;
		}
		officersInCharge.add(officer);
		return true;
	}

	public boolean removeOfficer(Officer officer) {
		if (!officersInCharge.contains(officer)) {
			return false;
		}
		officersInCharge.remove(officer);
		return true;
	}

	public void setOfficerSlot(int slot) {
		this.officerSlot = Math.min(slot, MAX_OFFICERS);
		while (officersInCharge.size() > officerSlot) {
			officersInCharge.remove(officersInCharge.size() - 1);
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

	public List<Officer> getOfficersInCharge() {
		return officersInCharge;
	}

	public static boolean isVisibleToApplicant() {
		return visibleToApplicant;
	}

	public void setVisibleToApplicant(boolean visible) {
		this.visibleToApplicant = visible;
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
		this.visibleToApplicant = b;
	}
}
