package data;

import entities.Officer;
import entities.Project;
import util.Database;
import util.FilePath;

import java.util.List;
import java.util.ArrayList;
import java.io.*;

/**
 * Provides methods for interacting with the project database.
 * Includes functionality to save, update, load, find, and delete project records in a file-based database.
 */
public class ProjectDatabase implements Database, FilePath {

    /**
     * Saves a new project to the database.
     * 
     * @param project The project to be saved.
     * @return true if the project is successfully saved, false otherwise.
     */
    public static boolean save(Project project) {
        try {
            StringBuilder officersData = new StringBuilder();
            if (!project.getOfficerNRICs().isEmpty()) {
                for (String officerNRIC : project.getOfficerNRICs()) {
                    if (officersData.length() > 0) officersData.append(";");
                    officersData.append(officerNRIC);
                }
            } else {
                officersData.append("null");
            }

            String line = String.format("%s|%s|%d|%.2f|%d|%.2f|%s|%s|%s|%d|%s|%b",
                project.getName(), project.getNeighbourhood(),
                project.getNumberOfType1Units(), project.getType1SellingPrice(),
                project.getNumberOfType2Units(), project.getType2SellingPrice(),
                project.getOpeningDate(), project.getClosingDate(),
                project.getManager(), project.getOfficerSlot(),
                officersData.toString(), Project.isVisibleToApplicant());

            Database.writeFile(projectDatabaseFilePath, line);
            return true;
        } catch (Exception e) {
            System.err.println("Error saving project: " + e.getMessage());
            return false;
        }
    }

    /**
     * Updates an existing project in the database.
     * 
     * @param project The project to be updated.
     * @return true if the project is successfully updated, false otherwise.
     */
    public static boolean update(Project project) {
        List<String> lines = Database.readFile(projectDatabaseFilePath);
        List<String> updatedLines = new ArrayList<>();
        boolean found = false;

        for (String line : lines) {
            String[] parts = line.split("\\|");
            if (parts.length >= 1 && parts[0].equals(project.getName())) {
                found = true;

                StringBuilder officersData = new StringBuilder();
                if (!project.getOfficerNRICs().isEmpty()) {
                    for (String officerNRIC : project.getOfficerNRICs()) {
                        if (officersData.length() > 0) officersData.append(";");
                        officersData.append(officerNRIC);
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
                    officersData.toString(), Project.isVisibleToApplicant());
            }
            updatedLines.add(line);
        }

        if (!found) return false;

        Database.updateFile(projectDatabaseFilePath, updatedLines);
        return true;
    }

    /**
     * Loads all projects from the database.
     * 
     * @return A list of all projects.
     */
    public static List<Project> loadAllProjects() {
        List<Project> projects = new ArrayList<>();
        List<String> lines = Database.readFile(projectDatabaseFilePath);

        for (String line : lines) {
            String[] parts = line.split("\\|");
            if (parts.length == 12) {
                Project project = parseProjectFromDatabase(parts);
                if (project != null) {
                    projects.add(project);
                }
            }
        }

        return projects;
    }

    /**
     * Parses a project from a database record.
     * 
     * @param parts The array of parts representing a database record.
     * @return The parsed project, or null if parsing fails.
     */
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

            if (!officersData.equals("null")) {
                String[] officerArray = officersData.split(";");
                for (String officer : officerArray) {
                    project.addOfficer(officer.trim());
                }
            }

            project.setVisibleToApplicant(isVisible);
            return project;
        } catch (Exception e) {
            System.err.println("Error parsing project from database: " + e.getMessage());
            return null;
        }
    }

    /**
     * Finds a project by its name.
     * 
     * @param projectName The name of the project to be found.
     * @return The project with the specified name, or null if not found.
     */
    public static Project findByName(String projectName) {
        return loadAllProjects().stream()
            .filter(p -> p.getName().equalsIgnoreCase(projectName))
            .findFirst()
            .orElse(null);
    }

    /**
     * Deletes a project by its name.
     * 
     * @param name The name of the project to be deleted.
     * @return true if the project is successfully deleted, false otherwise.
     */
    public static boolean delete(String name) {
        List<String> lines = Database.readFile(projectDatabaseFilePath);
        List<String> updatedLines = new ArrayList<>();
        boolean found = false;

        for (String line : lines) {
            String[] parts = line.split("\\|");
            if (parts.length >= 1 && parts[0].equals(name)) {
                found = true;
                continue;
            }
            updatedLines.add(line);
        }

        if (!found) return false;

        Database.updateFile(projectDatabaseFilePath, updatedLines);

        return true;
    }
}
