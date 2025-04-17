package data;

import entities.Officer;
import entities.Project;
import util.Database;
import util.FilePath;

import java.util.List;
import java.util.ArrayList;
import java.io.*;

public class ProjectDatabase implements Database,FilePath  {
//    private UserDatabase userDB = new UserDatabase();

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
        	.filter(p -> p.getName().equalsIgnoreCase(projectName))
            .findFirst()
            .orElse(null);
    }

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
