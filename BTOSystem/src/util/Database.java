package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import entities.OfficerApplication;

public interface Database {
	
    default public void writeFile(String filePath,String... fields) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
        	String line = String.join("|", fields);
            writer.write(line);
            writer.newLine();
            
        } catch (IOException e) {
            throw new RuntimeException("Error writing to file: " + e.getMessage(), e);
        }
    }
    
    default public void writeFile(String filePath,List<String> lines) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
            
        } catch (IOException e) {
            throw new RuntimeException("Error writing to file: " + e.getMessage(), e);
        }
    }
    
    default public List<String> readFile(String filePath) {
    	List<String> lines = new ArrayList<>();
		try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
			
            String line;
            while ((line = reader.readLine()) != null) {
            		lines.add(line);
                }
        } catch (IOException e) {
            throw new RuntimeException("Error reading Applications from file: " + e.getMessage(), e);
        }
        return lines; // Return null
	}
    
    default public void updateFile(String filePath,String... fields) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
        	String line = String.join("|", fields);
            writer.write(line);
            writer.newLine();
            
        } catch (IOException e) {
            throw new RuntimeException("Error writing to file: " + e.getMessage(), e);
        }
    }
    default public void updateFile(String filePath,List<String> lines) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
            
        } catch (IOException e) {
            throw new RuntimeException("Error writing to file: " + e.getMessage(), e);
        }
    }
    
    // Write a new user to the file
//  public void writeUser(String userID, String hashPassword) {
//      try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePathLogin, true))) {
//          writer.write(userID + "|"+  hashPassword);
//          writer.newLine();
//      } catch (IOException e) {
//          throw new RuntimeException("Error writing to file: " + e.getMessage(), e);
//      }
//  }
//  public void writeUser(String name, String userID,int age,String maritalStatus,String role,boolean isVisible) {
//      try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePathUserDatabase, true))) {
//          writer.write(name + "|"+  userID+ "|"+  age+ "|"+  maritalStatus+ "|"+  role+ "|"+  isVisible);
//          writer.newLine();
//          
//      } catch (IOException e) {
//          throw new RuntimeException("Error writing to file: " + e.getMessage(), e);
//      }
//  }
}
