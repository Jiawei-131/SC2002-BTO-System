package entities;

import data.ProjectApplicationDatabase;
import data.UserDatabase;
import controllers.AuthenticationController;
import java.util.List;
import java.util.ArrayList;
import java.io.*;

public class Project {
    public String name;
    public String neighbourhood;
    public int numberOfType1Units;
    private int numberOfType2Units;
    private String openingDate;
    private String closingDate;
    private Manager manager;
    private List<Officer> officersInCharge;
    private int availableSlots;
    private boolean visibleToApplicant;
    private List<Enquiry> enquiryList;
    private List<ProjectApplication> applicantList;

    // Database file path
    private static final String PROJECT_DB_FILE = "BTOSystem/src/data/ProjectDatabase.txt";

    public Project() {
        // Default constructor
    }

    public Project(String name, String neighbourhood, int numberOfType1Units, int numberOfType2Units,
            String openingDate, String closingDate, Manager manager, List<Officer> officersInCharge, int availableSlots,
            boolean visibleToApplicant, List<Enquiry> enquiryList, List<ProjectApplication> applicantList) {
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
        
        // Save to database when a new project is created
        saveToDatabase();
    }

    // Database Operations

    private void saveToDatabase() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PROJECT_DB_FILE, true))) {
            String managerNric = (manager != null) ? manager.getNric() : "null";
            String officers = (officersInCharge != null && !officersInCharge.isEmpty()) ? 
                String.join(",", officersInCharge.stream().map(Officer::getNric).toArray(String[]::new)) : "null";
            
            writer.write(String.format("%s|%s|%d|%d|%s|%s|%s|%s|%d|%b\n",
                name,
                neighbourhood,
                numberOfType1Units,
                numberOfType2Units,
                openingDate,
                closingDate,
                managerNric,
                officers,
                availableSlots,
                visibleToApplicant));
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
                    // Replace with current project data
                    String managerNric = (manager != null) ? manager.getNric() : "null";
                    String officers = (officersInCharge != null && !officersInCharge.isEmpty()) ? 
                        String.join(",", officersInCharge.stream().map(Officer::getNric).toArray(String[]::new)) : "null";
                    
                    line = String.format("%s|%s|%d|%d|%s|%s|%s|%s|%d|%b",
                        name,
                        neighbourhood,
                        numberOfType1Units,
                        numberOfType2Units,
                        openingDate,
                        closingDate,
                        managerNric,
                        officers,
                        availableSlots,
                        visibleToApplicant);
                }
                projects.add(line);
            }
        } catch (IOException e) {
            System.err.println("Error reading projects from database: " + e.getMessage());
            return;
        }

        // Write all projects back
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PROJECT_DB_FILE))) {
            for (String project : projects) {
                writer.write(project + "\n");
            }
        } catch (IOException e) {
            System.err.println("Error updating projects in database: " + e.getMessage());
        }
    }

    public static List<Project> loadAllProjects(UserDatabase userDatabase) {
        List<Project> projects = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(PROJECT_DB_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 10) {
                    Project project = parseProjectFromDatabase(parts, userDatabase);
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

    private static Project parseProjectFromDatabase(String[] parts, UserDatabase userDatabase) {
        try {
            String name = parts[0];
            String neighbourhood = parts[1];
            int type1Units = Integer.parseInt(parts[2]);
            int type2Units = Integer.parseInt(parts[3]);
            String openingDate = parts[4];
            String closingDate = parts[5];
            String managerNric = parts[6];
            String officersNric = parts[7];
            int availableSlots = Integer.parseInt(parts[8]);
            boolean isVisible = Boolean.parseBoolean(parts[9]);
            
            // Get manager
            Manager manager = null;
            if (!managerNric.equals("null")) {
                User user = userDatabase.getUserById(managerNric, new AuthenticationController());
                if (user instanceof Manager) {
                    manager = (Manager) user;
                }
            }
            
            // Get officers
            List<Officer> officers = new ArrayList<>();
            if (!officersNric.equals("null")) {
                for (String nric : officersNric.split(",")) {
                    User user = userDatabase.getUserById(nric, new AuthenticationController());
                    if (user instanceof Officer) {
                        officers.add((Officer) user);
                    }
                }
            }
            
            return new Project(
                name, neighbourhood, type1Units, type2Units,
                openingDate, closingDate, manager, officers, availableSlots,
                isVisible, null, null
            );
        } catch (Exception e) {
            System.err.println("Error parsing project from database: " + e.getMessage());
            return null;
        }
    }

    public static Project findByName(String projectName, UserDatabase userDatabase) {
        List<Project> projects = loadAllProjects(userDatabase);
        for (Project project : projects) {
            if (project.name.equals(projectName)) {
                return project;
            }
        }
        return null;
    }
    
    // Delete Project from Database
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

        // Write remaining projects back
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PROJECT_DB_FILE))) {
            for (String project : projects) {
                writer.write(project + "\n");
            }
        } catch (IOException e) {
            System.err.println("Error updating projects in database: " + e.getMessage());
        }
    }

    // Methods

    public void displayProjects() {
        System.out.println("Project Name: " + name);
        System.out.println("Neighborhood: " + neighbourhood);
        System.out.println("Type 1 Units: " + numberOfType1Units);
        System.out.println("Type 2 Units: " + numberOfType2Units);
        System.out.println("Available Slots: " + availableSlots);
        System.out.println("Opening Date: " + openingDate);
        System.out.println("Closing Date: " + closingDate);
        System.out.println("Visible to Applicants: " + visibleToApplicant);
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
        updateInDatabase();
    }

    public void addOfficer(Officer officer) {
        if (officersInCharge == null) {
            officersInCharge = new ArrayList<>();
        }
        if (!officersInCharge.contains(officer)) {
            officersInCharge.add(officer);
            updateInDatabase();
        }
    }

    public void removeOfficer(Officer officer) {
        if (officersInCharge != null) {
            officersInCharge.remove(officer);
            updateInDatabase();
        }
    }

    // Getters and Setters

    public int getNumberOfUnits() {
        return numberOfType1Units + numberOfType2Units;
    }

    public void setNumberOfUnits(int type1, int type2) {
        this.numberOfType1Units = type1;
        this.numberOfType2Units = type2;
        this.availableSlots = getNumberOfUnits();
        updateInDatabase();
    }

    public int getAvailableSlots() {
        return availableSlots;
    }

    public void setAvailableSlots(int availableSlots) {
        this.availableSlots = availableSlots;
        updateInDatabase();
    }

    public boolean getIsVisible() {
        return visibleToApplicant;
    }

    public void setIsVisible(boolean visible) {
        this.visibleToApplicant = visible;
        updateInDatabase();
    }

    public int getNumberOfType1Units() {
        return numberOfType1Units;
    }

    public void setNumberOfType1Units(int numberOfType1Units) {
        this.numberOfType1Units = numberOfType1Units;
        updateInDatabase();
    }

    public int getNumberOfType2Units() {
        return numberOfType2Units;
    }

    public void setNumberOfType2Units(int numberOfType2Units) {
        this.numberOfType2Units = numberOfType2Units;
        updateInDatabase();
    }

    public String getOpeningDate() {
        return openingDate;
    }

    public void setOpeningDate(String openingDate) {
        this.openingDate = openingDate;
        updateInDatabase();
    }

    public String getClosingDate() {
        return closingDate;
    }

    public void setClosingDate(String closingDate) {
        this.closingDate = closingDate;
        updateInDatabase();
    }

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
        updateInDatabase();
    }

    public List<Officer> getOfficersInCharge() {
        return officersInCharge;
    }

    public void setOfficersInCharge(List<Officer> officersInCharge) {
        this.officersInCharge = officersInCharge;
        updateInDatabase();
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
            if (this.numberOfType2Units > 0) {
                return false;
            }
        }
        
        return true;
    }
}
