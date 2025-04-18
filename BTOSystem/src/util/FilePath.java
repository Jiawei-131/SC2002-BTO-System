package util;

/**
 * Interface containing constants for file paths used in the BTO system.
 * These file paths are used to access and store data for various system components.
 */
public interface FilePath {
    
    /**
     * The base file path where all data files are stored.
     */
    final String filePath = "BTOSystem/src/data/";
    
    /**
     * The file path for storing login information.
     */
    final String loginFilePath = filePath + "LoginInfo.txt";
    
    /**
     * The file path for storing user database information.
     */
    final String userDatabaseFilePath = filePath + "UserDatabase.txt";
    
    /**
     * The file path for storing enquiry database information.
     */
    final String enquiryDatabaseFilePath = filePath + "EnquiryDatabase.txt";
    
    /**
     * The file path for storing project database information.
     */
    final String projectDatabaseFilePath = filePath + "ProjectDatabase.txt";
    
    /**
     * The file path for storing officer application database information.
     */
    final String officerApplicationDatabaseFilePath = filePath + "OfficerApplicationDatabase.txt";
    
    /**
     * The file path for storing project application database information.
     */
    final String projectApplicationDatabaseFilePath = filePath + "ProjectApplicationDatabase.txt";
}
