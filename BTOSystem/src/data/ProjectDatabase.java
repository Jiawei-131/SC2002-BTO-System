package data;

import entities.Officer;
import entities.Project;
import java.util.List;
import java.util.ArrayList;
import java.io.*;

public class ProjectDatabase {
    private static final String PROJECT_DB_FILE = "BTOSystem/src/data/ProjectDatabase.txt";
    private UserDatabase userDB = new UserDatabase();

    

    public static boolean save(Project project) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PROJECT_DB_FILE, true))) {
            StringBuilder officersData = new StringBuilder();
            if (!project.getOfficersInCharge().isEmpty()) {
                for (Officer officer : project.getOfficersInCharge()) {
                    if (officersData.length() > 0) officersData.append(";");
                    officersData.append(officer.toString());
                }
            } else {
                officersData.append("null");
            }
            
            writer.write(String.format("%s|%s|%d|%.2f|%d|%.2f|%s|%s|%s|%d|%s|%b\n",
                project.getName(), project.getNeighbourhood(), 
                project.getNumberOfType1Units(), project.getType1SellingPrice(),
                project.getNumberOfType2Units(), project.getType2SellingPrice(), 
                project.getOpeningDate(), project.getClosingDate(),
                project.getManager(), project.getOfficerSlot(), 
                officersData.toString(), project.isVisibleToApplicant()));
            return true;
        } catch (IOException e) {
            System.err.println("Error saving project to database: " + e.getMessage());
            return false;
        }
    }

    public boolean update(Project project) {
        List<String> projects = new ArrayList<>();
        boolean found = false;
        
        try (BufferedReader reader = new BufferedReader(new FileReader(PROJECT_DB_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length >= 1 && parts[0].equals(project.getName())) {
                    found = true;
                    
                    StringBuilder officersData = new StringBuilder();
                    if (!project.getOfficersInCharge().isEmpty()) {
                        for (Officer officer : project.getOfficersInCharge()) {
                            if (officersData.length() > 0) officersData.append(";");
                            officersData.append(officer.toString());
                        }
                    } else {
                        officersData.append("null");
                    }
                    
                    line = String.format("%s|%s|%d|%.2f|%d|%.2f|%s|%s|%s|%d|%s|%b",
                        project.getName(), project.getNeighbourhood(), 
                        project.getNumberOfType1Units(), project.getType1SellingPrice(),
                        project.getNumberOfType2Units(), project.getType2SellingPrice(), 
                        project.getOpeningDate(), project.getClosingDate(),
                        project.getManager(), project.getOfficerSlot(), 
                        officersData.toString(), project.isVisibleToApplicant());
                }
                projects.add(line);
            }
        } catch (IOException e) {
            System.err.println("Error reading projects from database: " + e.getMessage());
            return false;
        }

        if (!found) return false;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PROJECT_DB_FILE))) {
            for (String projectLine : projects) {
                writer.write(projectLine + "\n");
            }
            return true;
        } catch (IOException e) {
            System.err.println("Error updating projects in database: " + e.getMessage());
            return false;
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
            
            Project project = new Project(
                name, neighbourhood, type1Units, type1Price,
                type2Units, type2Price, openingDate, closingDate,
                managerData, officerSlot
            );
            
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

    public static boolean delete(String name) {
        List<String> projects = new ArrayList<>();
        boolean found = false;
        
        try (BufferedReader reader = new BufferedReader(new FileReader(PROJECT_DB_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length >= 1 && parts[0].equals(name)) {
                    found = true;
                    continue;
                }
                projects.add(line);
            }
        } catch (IOException e) {
            System.err.println("Error reading projects from database: " + e.getMessage());
            return false;
        }

        if (!found) return false;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PROJECT_DB_FILE))) {
            for (String project : projects) {
                writer.write(project + "\n");
            }
            return true;
        } catch (IOException e) {
            System.err.println("Error updating projects in database: " + e.getMessage());
            return false;
        }
    }
}
