package util;

/**
 * Enum representing different roles in the system.
 * Each role corresponds to a specific level of access and functionality within the application.
 */
public enum Role {
    
    /** Represents an applicant who can apply for flats and view available projects. */
    APPLICANT,

    /** Represents an officer who manages applications, enquiries, and projects. */
    OFFICER,

    /** Represents a manager who oversees officers and applicants, managing the entire system. */
    MANAGER
}
