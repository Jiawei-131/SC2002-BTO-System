package entities;

import controllers.AuthenticationController;
import data.ProjectDatabase;
import data.UserDatabase;
import util.Role;
import util.Filter;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;

/**
 * Abstract class representing a user in the system.
 * This class provides general functionality for user management, including login/logout, 
 * filtering, and sorting of project listings. 
 * Specific user roles and behaviors are defined by subclasses.
 */
public abstract class User implements Filter {

    private String filterDescription = "Alphabetical";
    private Comparator<Project> comparator = Comparator.comparing(Project::getName);
    private Predicate<Project> predicate=project->true;
    private String name;
    protected String nric;
    protected int age;
    protected String maritalStatus;
    private String password;
    private boolean isLogin = false;
    private AuthenticationController ac;
    private Role role;

    /**
     * Default constructor for User class.
     */
    public User() {
    }

    /**
     * Abstract method to display the user's menu options.
     * Specific implementations are provided by subclasses.
     * 
     * @param options A list of menu options to be displayed to the user
     */
    public abstract void displayMenu(List<String> options);

    /**
     * Constructor to initialize a user with specific details.
     * 
     * @param name The name of the user
     * @param nric The NRIC of the user
     * @param age The age of the user
     * @param maritalStatus The marital status of the user
     * @param password The password of the user
     * @param ac The authentication controller for handling login/logout
     * @param role The role of the user (e.g., applicant, officer)
     */
    public User(String name, String nric, int age, String maritalStatus, String password, AuthenticationController ac, Role role) {
        this.name = name;
        this.nric = nric;
        this.age = age;
        this.maritalStatus = maritalStatus;
        this.password = password;
        this.ac = ac;
        this.role = role;
    }

    /**
     * Sorts and returns all projects from the project database using the specified comparator.
     * 
     * @return A sorted list of projects
     */
    public List<Project> sort() {
        return ProjectDatabase.loadAllProjects().stream()
        		.filter(getPredicate())
                .sorted(comparator)
                .toList();
    }

    /**
     * Gets the role of the user.
     * 
     * @return The user's role
     */
    public Role getRole() {
        return this.role;
    }

    /**
     * Logs the user in by changing the login status to true.
     */
    public void login() {
        // ac.logIn(this, password);
        System.out.println("Login successful");
        this.isLogin = true;
    }

    /**
     * Logs the user out and changes the login status to false.
     * 
     * @return null after logging out the user
     */
    public User logout() {
        System.out.printf("Goodbye %s!\n", this.name);
        // ac.logOut(this);
        this.isLogin = false;
        return null;
    }

    /**
     * Checks whether the user is logged in.
     * 
     * @return true if the user is logged in, false otherwise
     */
    public boolean isLogin() {
        return isLogin;
    }

    /**
     * Gets the user's age.
     * 
     * @return The user's age
     */
    public int getAge() {
        return age;
    }

    /**
     * Gets the user's NRIC.
     * 
     * @return The user's NRIC
     */
    public String getNric() {
        return nric;
    }

    /**
     * Gets the user's name.
     * 
     * @return The user's name
     */
    public String getUsername() {
        return name;
    }

    /**
     * Sets the user's password.
     * 
     * @param password The new password for the user
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the user's password.
     * 
     * @return The user's password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Changes the user's password and updates it in the user database.
     * 
     * @param newPassword The new password to set
     * @param db The user database for updating the password
     * @return The user instance after logging out
     */
    public User changePassword(String newPassword, UserDatabase db) {
        db.updateUserPassword(this.getNric(), newPassword);
        System.out.println("Password is changed");
        System.out.println("Please login again!");
        return this.logout();
    }

    /**
     * Gets the marital status of the user.
     * 
     * @return The marital status of the user
     */
    public String getMaritalStatus() {
        return maritalStatus;
    }

    /**
     * Sets the marital status of the user.
     * 
     * @param maritalStatus The new marital status of the user
     */
    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    /**
     * Gets the description of the current filter being applied to projects.
     * 
     * @return The filter description
     */
    public String getFilterDescription() {
        return filterDescription;
    }

    /**
     * Sets the description of the current filter being applied to projects.
     * 
     * @param desc The new filter description
     */
    @Override
    public void setFilterDescription(String desc) {
        this.filterDescription = desc;
    }

    /**
     * Gets the comparator used for sorting projects.
     * 
     * @return The comparator used to sort projects
     */
    @Override
    public Comparator<Project> getComparator() {
        return comparator;
    }
    
    public Predicate<Project> getPredicate() {
        return predicate;
    }

    /**
     * Sets the comparator to be used for sorting projects.
     * 
     * @param comp The comparator to set
     */
    @Override
    public void setComparator(Comparator<Project> comp) {
        this.comparator = comp;
    }
    
    public void setPredicate(Predicate<Project> pred) {
    	this.predicate=pred;
    }

    /**
     * Provides the menu options for the user.
     * 
     * @return A list of menu options for the user
     */
    public List<String> getMenuOptions() {
        return Arrays.asList(
                "1. Projects",
                "2. Enquiries",
                "3. Change Password",
                "4. Filter Settings",
                "5. Logout"
        );
    }

    /**
     * Provides the available sorting options for projects.
     * 
     * @return A list of sorting options for projects
     */
    public List<String> getSortOptions() {
        return Arrays.asList(
                "1. Neighbourhood",
                "2. 2-Room Types",
                "3. 3-Room Types",
                "4. Opening Date",
                "5. Closing Date",
                "6. Default setting"
        );
    }

    /**
     * Abstract method for getting the options related to projects.
     * Specific implementations are provided by subclasses.
     * 
     * @return A list of project options
     */
    public abstract List<String> getProjectOptions();

    /**
     * Abstract method for getting the options related to enquiries.
     * Specific implementations are provided by subclasses.
     * 
     * @return A list of enquiry options
     */
    public abstract List<String> getEnquiryOptions();
}
