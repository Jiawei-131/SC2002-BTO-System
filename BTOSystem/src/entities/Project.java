package entities;

import data.ProjectApplicationDatabase;
import data.UserDatabase;
import controllers.AuthenticationController;
import java.util.List;
import java.util.ArrayList;
import java.io.*;

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
    private Manager manager;
    private List<Officer> officersInCharge;
    private int officerSlot;
    
    // Visibility and applications
    private boolean visibleToApplicant;
    private List<Enquiry> enquiryList;
    private List<ProjectApplication> applicantList;

    // Constants
    private static final String PROJECT_DB_FILE = "BTOSystem/src/data/ProjectDatabase.txt";
    private static final int MAX_OFFICERS = 10;

    // Constructors
    public Project() {
        this.officersInCharge = new ArrayList<>();
        this.enquiryList = new ArrayList<>();
        this.applicantList = new ArrayList<>();
    }

    public Project(String name, String neighbourhood, 
                  int numberOfType1Units, double type1SellingPrice,
                  int numberOfType2Units, double type2SellingPrice,
                  String openingDate, String closingDate,
                  Manager manager, int officerSlot,boolean save) {
        this();
        this.name = name;
        this.manager = manager;
        this.neighbourhood = neighbourhood;
        this.numberOfType1Units = numberOfType1Units;
        this.type1SellingPrice = type1SellingPrice;
        this.numberOfType2Units = numberOfType2Units;
        this.type2SellingPrice = type2SellingPrice;
        this.openingDate = openingDate;
        this.closingDate = closingDate;
        System.out.println(manager);
        System.out.printf("%s",manager.toString());
        this.officerSlot = Math.min(officerSlot, MAX_OFFICERS);
        this.visibleToApplicant = true;
        
        if(save)
        {
            saveToDatabase();
        }
    }

    // Database Operations
    private void saveToDatabase() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PROJECT_DB_FILE, true))) {
            // Serialize manager
            String managerData = (manager != null) ? manager.getNric() : "null";
            
            // Serialize officers
            StringBuilder officersData = new StringBuilder();
            if (!officersInCharge.isEmpty()) {
                for (Officer officer : officersInCharge) {
                    if (officersData.length() > 0) officersData.append(";");
                    officersData.append(officer.toString());
                }
            } else {
                officersData.append("null");
            }
            
            writer.write(String.format("%s|%s|%d|%.2f|%d|%.2f|%s|%s|%s|%d|%s|%b\n",
                name, neighbourhood, numberOfType1Units, type1SellingPrice,
                numberOfType2Units, type2SellingPrice, openingDate, closingDate,
                managerData, officerSlot, officersData.toString(), visibleToApplicant));
        } catch (IOException e) {
            System.err.println("Error saving project to database: " + e.getMessage());
        }
    }

    public void updateInDatabase() {
        List<String> projects = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(PROJECT_DB_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length >= 1 && parts[0].equals(name)) {
                    // Update with current data
                    String managerData = (manager != null) ? manager.toString() : "null";
                    
                    StringBuilder officersData = new StringBuilder();
                    if (!officersInCharge.isEmpty()) {
                        for (Officer officer : officersInCharge) {
                            if (officersData.length() > 0) officersData.append(";");
                            officersData.append(officer.toString());
                        }
                    } else {
                        officersData.append("null");
                    }
                    
                    line = String.format("%s|%s|%d|%.2f|%d|%.2f|%s|%s|%s|%d|%s|%b",
                        name, neighbourhood, numberOfType1Units, type1SellingPrice,
                        numberOfType2Units, type2SellingPrice, openingDate, closingDate,
                        managerData, officerSlot, officersData.toString(), visibleToApplicant);
                }
                projects.add(line);
            }
        } catch (IOException e) {
            System.err.println("Error reading projects from database: " + e.getMessage());
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PROJECT_DB_FILE))) {
            for (String project : projects) {
                writer.write(project + "\n");
            }
        } catch (IOException e) {
            System.err.println("Error updating projects in database: " + e.getMessage());
        }
    }

    public static List<Project> loadAllProjects() {
        List<Project> projects = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(PROJECT_DB_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 12) {
                    Project project = parseProjectFromDatabase(parts);
                    if (project != null) {
                        projects.add(project);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading projects from database: " + e.getMessage());
        }
        return projects;
    }

    private static Project parseProjectFromDatabase(String[] parts) {
        try {
            String name = parts[0];
            String neighbourhood = parts[1];
            int type1Units = Integer.parseInt(parts[2]);
            double type1Price = Double.parseDouble(parts[3]);
            int type2Units = Integer.parseInt(parts[4]);
            double type2Price = Double.parseDouble(parts[5]);
            String openingDate = parts[6];
            String closingDate = parts[7];
            String managerData = parts[8];
            int officerSlot = Integer.parseInt(parts[9]);
            String officersData = parts[10];
            boolean isVisible = Boolean.parseBoolean(parts[11]);
            
            // Deserialize manager
            Manager manager = null;
//            if (!managerData.equals("null")) {
//                manager = Manager.fromString(managerData);
//            }
//            
            // Create project
            Project project = new Project(
                name, neighbourhood, type1Units, type1Price,
                type2Units, type2Price, openingDate, closingDate,
                manager, officerSlot,false
            );
            
            // Deserialize officers
//            if (!officersData.equals("null")) {
//                for (String officerStr : officersData.split(";")) {
//                    Officer officer = Officer.fromString(officerStr);
//                    if (officer != null) {
//                        project.addOfficer(officer);
//                    }
//                }
//            }
            
            project.setVisibleToApplicant(isVisible);
            return project;
        } catch (Exception e) {
            System.err.println("Error parsing project from database: " + e.getMessage());
            return null;
        }
    }

    public static Project findByName(String projectName) {
        return loadAllProjects().stream()
            .filter(p -> p.getName().equals(projectName))
            .findFirst()
            .orElse(null);
    }
    
    public void deleteFromDatabase() {
        List<String> projects = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(PROJECT_DB_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length >= 1 && !parts[0].equals(name)) {
                    projects.add(line);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading projects from database: " + e.getMessage());
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PROJECT_DB_FILE))) {
            for (String project : projects) {
                writer.write(project + "\n");
            }
        } catch (IOException e) {
            System.err.println("Error updating projects in database: " + e.getMessage());
        }
    }

    // Staff Management
    public boolean addOfficer(Officer officer) {
        if (officersInCharge.size() >= officerSlot) {
            System.out.println("Cannot add more officers. Officer slot limit (" + officerSlot + ") reached.");
            return false;
        }
        if (officersInCharge.contains(officer)) {
            System.out.println("Officer is already assigned to this project.");
            return false;
        }
        officersInCharge.add(officer);
        updateInDatabase();
        return true;
    }

    public boolean removeOfficer(Officer officer) {
        if (!officersInCharge.contains(officer)) {
            System.out.println("Officer is not assigned to this project.");
            return false;
        }
        officersInCharge.remove(officer);
        updateInDatabase();
        return true;
    }

    public void setOfficerSlot(int slot) {
        this.officerSlot = Math.min(slot, MAX_OFFICERS);
        // Remove excess officers if reducing slots
        while (officersInCharge.size() > officerSlot) {
            officersInCharge.remove(officersInCharge.size() - 1);
        }
        updateInDatabase();
    }

    // Display Methods
    public void displayProjectDetails() {
        System.out.println("\n=== Project Details ===");
        System.out.println("Name: " + name);
        System.out.println("Neighborhood: " + neighbourhood);
        System.out.println("Type 1 Units: " + numberOfType1Units + " (Price: $" + String.format("%.2f", type1SellingPrice) + ")");
        System.out.println("Type 2 Units: " + numberOfType2Units + " (Price: $" + String.format("%.2f", type2SellingPrice) + ")");
        System.out.println("Application Period: " + openingDate + " to " + closingDate);
        System.out.println("Manager: " + (manager != null ? manager.getUsername() : "Not assigned"));
        System.out.println("Officers: " + officersInCharge.size() + "/" + officerSlot);
        for (Officer officer : officersInCharge) {
            System.out.println("  - " + officer.getUsername());
        }
        System.out.println("Visibility: " + (visibleToApplicant ? "Visible to applicants" : "Hidden from applicants"));
    }

    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { 
        this.name = name; 
        updateInDatabase();
    }

    public String getNeighbourhood() { return neighbourhood; }
    public void setNeighbourhood(String neighbourhood) { 
        this.neighbourhood = neighbourhood; 
        updateInDatabase();
    }

    public int getNumberOfType1Units() { return numberOfType1Units; }
    public void setNumberOfType1Units(int units) { 
        this.numberOfType1Units = units; 
        updateInDatabase();
    }

    public double getType1SellingPrice() { return type1SellingPrice; }
    public void setType1SellingPrice(double price) { 
        this.type1SellingPrice = price; 
        updateInDatabase();
    }

    public int getNumberOfType2Units() { return numberOfType2Units; }
    public void setNumberOfType2Units(int units) { 
        this.numberOfType2Units = units; 
        updateInDatabase();
    }

    public double getType2SellingPrice() { return type2SellingPrice; }
    public void setType2SellingPrice(double price) { 
        this.type2SellingPrice = price; 
        updateInDatabase();
    }

    public String getOpeningDate() { return openingDate; }
    public void setOpeningDate(String date) { 
        this.openingDate = date; 
        updateInDatabase();
    }

    public String getClosingDate() { return closingDate; }
    public void setClosingDate(String date) { 
        this.closingDate = date; 
        updateInDatabase();
    }

    public Manager getManager() { return manager; }
    public void setManager(Manager manager) { 
        this.manager = manager; 
        updateInDatabase();
    }

    public int getOfficerSlot() { return officerSlot; }
    public List<Officer> getOfficersInCharge() { return officersInCharge; }

    public boolean isVisibleToApplicant() { return visibleToApplicant; }
    public void setVisibleToApplicant(boolean visible) { 
        this.visibleToApplicant = visible; 
        updateInDatabase();
    }

    public List<Enquiry> getEnquiryList() { return enquiryList; }
    public void setEnquiryList(List<Enquiry> enquiryList) { 
        this.enquiryList = enquiryList; 
    }

    public List<ProjectApplication> getApplicantList() { return applicantList; }
    public void setApplicantList(List<ProjectApplication> applicantList) { 
        this.applicantList = applicantList; 
    }

    // Eligibility check method
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
            if (this.numberOfType2Units <= 0) {
                return false;
            }
        }
        
        return true;
    }

	public void setIsVisible(boolean b) {
		// TODO Auto-generated method stub
		
	}
}
