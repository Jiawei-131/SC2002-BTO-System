package util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

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
    
    default public void updateFile(String filePath,String... fields) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
        	String line = String.join("|", fields);
            writer.write(line);
            writer.newLine();
            
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
