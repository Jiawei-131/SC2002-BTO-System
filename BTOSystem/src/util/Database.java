package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Interface providing utility methods for interacting with files to perform CRUD operations.
 * It includes methods for writing data to files, reading data from files, and updating files.
 */
public interface Database {

    /**
     * Writes the provided fields to a file at the specified file path.
     * Fields are joined by a pipe ('|') separator.
     * 
     * @param filePath The path to the file where data should be written.
     * @param fields   The data fields to be written to the file.
     */
    public static void writeFile(String filePath, String... fields) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            String line = String.join("|", fields);
            writer.write(line);
            writer.newLine();
        } catch (IOException e) {
            throw new RuntimeException("Error writing to file: " + e.getMessage(), e);
        }
    }

    /**
     * Writes the provided list of lines to a file at the specified file path.
     * 
     * @param filePath The path to the file where data should be written.
     * @param lines    The list of lines to be written to the file.
     */
    public static void writeFile(String filePath, List<String> lines) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Error writing to file: " + e.getMessage(), e);
        }
    }

    /**
     * Reads the contents of the file at the specified file path into a list of strings.
     * Each line from the file is added to the list.
     * 
     * @param filePath The path to the file to be read.
     * @return A list of strings representing the lines in the file.
     */
    public static List<String> readFile(String filePath) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading from file: " + e.getMessage(), e);
        }
        return lines;
    }

    /**
     * Updates the file at the specified file path with the provided fields.
     * Fields are joined by a pipe ('|') separator and overwrite the existing contents.
     * 
     * @param filePath The path to the file to be updated.
     * @param fields   The data fields to be written to the file.
     */
    public static void updateFile(String filePath, String... fields) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            String line = String.join("|", fields);
            writer.write(line);
            writer.newLine();
        } catch (IOException e) {
            throw new RuntimeException("Error writing to file: " + e.getMessage(), e);
        }
    }

    /**
     * Updates the file at the specified file path with the provided list of lines.
     * The lines will overwrite the existing contents of the file.
     * 
     * @param filePath The path to the file to be updated.
     * @param lines    The list of lines to overwrite the file contents.
     */
    public static void updateFile(String filePath, List<String> lines) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Error writing to file: " + e.getMessage(), e);
        }
    }
}
