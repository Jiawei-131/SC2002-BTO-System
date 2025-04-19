package entities;

import java.util.List;
import data.ProjectApplicationDatabase;
import java.util.ArrayList;

/**
 * Represents a project that is available for application by eligible applicants.
 * The project contains details like the number of units, their selling prices,
 * the project opening and closing dates, and the staff managing the project.
 */
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
    private boolean visibleToApplicant;
    private List<Enquiry> enquiryList;
    private List<ProjectApplication> applicantList;

    // Constants
    private static final int MAX_OFFICERS = 10;

    // Constructors

    /**
     * Default constructor that initializes the lists for officers, enquiries, and applicants.
     */
    public Project() {
        this.officerNRICs = new ArrayList<>();
        this.enquiryList = new ArrayList<>();
        this.applicantList = new ArrayList<>();
    }

    /**
     * Constructor that initializes the project with the provided details.
     * The number of officer slots is constrained by a maximum limit (MAX_OFFICERS),
     * and the visibility of the project to applicants is set to true.
     *
     * @param name The name of the project.
     * @param neighbourhood The neighbourhood where the project is located.
     * @param numberOfType1Units The number of Type 1 units available in the project.
     * @param type1SellingPrice The selling price of each Type 1 unit.
     * @param numberOfType2Units The number of Type 2 units available in the project.
     * @param type2SellingPrice The selling price of each Type 2 unit.
     * @param openingDate The opening date for applications.
     * @param closingDate The closing date for applications.
     * @param managerNRIC The NRIC of the project manager.
     * @param officerSlot The number of officer slots available for the project.
     */
	public Project(String name, String neighbourhood, int numberOfType1Units, double type1SellingPrice,
			int numberOfType2Units, double type2SellingPrice, String openingDate, String closingDate,
			String managerNRIC, int officerSlot) {
		this(name, neighbourhood, numberOfType1Units, type1SellingPrice, numberOfType2Units, type2SellingPrice,
				openingDate, closingDate, managerNRIC, officerSlot, true); // default visibility
	}

    // Constructor with visibleToApplicant parameter
    /**
     * Constructor that initializes the project with the provided details.
     * The number of officer slots is constrained by a maximum limit (MAX_OFFICERS),
     * and the visibility of the project to applicants is set to true.
     *
     * @param name The name of the project.
     * @param neighbourhood The neighbourhood where the project is located.
     * @param numberOfType1Units The number of Type 1 units available in the project.
     * @param type1SellingPrice The selling price of each Type 1 unit.
     * @param numberOfType2Units The number of Type 2 units available in the project.
     * @param type2SellingPrice The selling price of each Type 2 unit.
     * @param openingDate The opening date for applications.
     * @param closingDate The closing date for applications.
     * @param managerNRIC The NRIC of the project manager.
     * @param officerSlot The number of officer slots available for the project.
     * @param visibleToApplicant Whether the project is visible to applicants.
     */
    public Project(String name, String neighbourhood, int numberOfType1Units, double type1SellingPrice,
                   int numberOfType2Units, double type2SellingPrice, String openingDate, String closingDate,
                   String managerNRIC, int officerSlot, boolean visibleToApplicant) {
        this(); // calls default constructor
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
        this.visibleToApplicant = visibleToApplicant;
    }
    
    

    /**
     * Adds an officer to the project if there is space and the officer is not already added.
     *
     * @param officerNRIC The NRIC of the officer to be added.
     * @return true if the officer is added successfully, false otherwise.
     */
    public boolean addOfficer(String officerNRIC) {
        if (officerNRICs.size() >= officerSlot)
            return false;
        if (officerNRICs.contains(officerNRIC))
            return false;
        officerNRICs.add(officerNRIC);
        return true;
    }

    /**
     * Removes an officer from the project.
     *
     * @param officerNRIC The NRIC of the officer to be removed.
     * @return true if the officer is removed successfully, false otherwise.
     */
    public boolean removeOfficer(String officerNRIC) {
        return officerNRICs.remove(officerNRIC);
    }

    /**
     * Sets the number of officer slots for the project. If the number of officers exceeds the new slot limit,
     * the extra officers will be removed.
     *
     * @param slot The new number of officer slots.
     */
    public void setOfficerSlot(int slot) {
        this.officerSlot = Math.min(slot, MAX_OFFICERS);
        while (officerNRICs.size() > officerSlot) {
            officerNRICs.remove(officerNRICs.size() - 1);
        }
    }

    // Eligibility Check Methods

    /**
     * Checks if the applicant is eligible for a 2-room unit only.
     *
     * @param applicant The applicant whose eligibility is to be checked.
     * @return true if the applicant is eligible, false otherwise.
     */
    public boolean isEligibleFor2RoomOnly(Applicant applicant) {
        int age = applicant.getAge();
        String status = applicant.getMaritalStatus();
        return status.equalsIgnoreCase("Single") && age >= 35;
    }

    /**
     * Checks if the applicant is eligible for both 2-room and 3-room units.
     *
     * @param applicant The applicant whose eligibility is to be checked.
     * @return true if the applicant is eligible, false otherwise.
     */
    public boolean isEligibleFor2and3Room(Applicant applicant) {
        int age = applicant.getAge();
        String status = applicant.getMaritalStatus();
        return status.equalsIgnoreCase("Married") && age >= 21;
    }

    /**
     * Checks if the applicant is eligible for the project.
     * An applicant is eligible if they haven't already applied and meet the age and marital status requirements.
     *
     * @param applicant The applicant whose eligibility is to be checked.
     * @return true if the applicant is eligible, false otherwise.
     */
    public boolean isEligible(Applicant applicant) {
        ProjectApplicationDatabase appDB = new ProjectApplicationDatabase();
        if (appDB.getApplicationByApplicantId(applicant.getNric()) != null) {
            return false;
        }

        return isEligibleFor2and3Room(applicant) || isEligibleFor2RoomOnly(applicant);
    }

    // Getter and Setter Methods

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

    public boolean isVisibleToApplicant() {
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


    /**
     * Returns a string representation of the Project object, displaying key project details.
     *
     * @return A string representing the project details.
     */
    @Override
    public String toString() {
        return "Project Name:" + this.name + "\nNeighbourhood: " + this.neighbourhood + "\n2-Room Units Available: "
                + this.numberOfType1Units + "\nSelling Price: " + this.type1SellingPrice + "\n3-Room Units Available: "
                + this.numberOfType2Units + "\nSelling Price: " + this.type2SellingPrice + "\nOpening Date: "
                + this.openingDate + "\nClosing Date: " + this.closingDate;
    }
}
