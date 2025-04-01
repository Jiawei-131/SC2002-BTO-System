import java.util.List;

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
	private List<Application> applicantList;

	// Constructor?
	public Project(String name, String neighbourhood, int numberOfType1Units, int numberOfType2Units,
			String openingDate, String closingDate, Manager manager, List<Officer> officersInCharge, int availableSlots,
			boolean visibleToApplicant, List<Enquiry> enquiryList, List<Application> applicantList) {
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

	public void viewEnquiry(Enquiry enquiry) {
		// TBC
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
	public boolean isEligible(Applicant applicant) {
		// TBC
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