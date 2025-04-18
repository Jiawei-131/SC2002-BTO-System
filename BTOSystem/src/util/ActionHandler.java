package util;
import java.util.Scanner;
import data.UserDatabase;
import entities.User;
/**
 * Interface representing a generic action handler for different user roles.
 * It provides methods to handle user actions related to projects, enquiries, and filtering.
 */
public interface ActionHandler {
    /**
     * Handles filtering actions for the current user.
     * Prompts the user to select a filter option and sets it.
     *
     * @param currentUser The user performing the action
     * @param sc Scanner object for reading input
     */
	default void handleFilterAction(User currentUser,Scanner sc)
	{
		currentUser.displayMenu(currentUser.getSortOptions());
		try {
			currentUser.setFilter(GetInput.inputLoop("your choice", sc, Integer::parseInt, i->i>0&&i<6));
		} catch (RegistrationFailedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    /**
     * Handles a user action selected from the main menu based on the choice number.
     *
     * @param choice The user's chosen menu option
     * @param user The user performing the action
     * @param sc Scanner object for reading input
     * @param db The database used to read or update user-related data
     * @return The updated User object, or null if the session ends
     */
	User handleAction(int choice,User user, Scanner sc,UserDatabase db);
    /**
     * Handles actions related to enquiries submitted by or for the user.
     *
     * @param choice The selected enquiry option
     * @param currentUser The user performing the enquiry-related action
     * @param sc Scanner object for reading input
     */
	void handleEnquiryAction(int choice,User currentUser, Scanner sc);
    /**
     * Handles actions related to project management for the current user.
     *
     * @param choice The selected project-related menu option
     * @param currentUser The user performing the project-related action
     * @param sc Scanner object for reading input
     */
	void handleProjectAction(int choice,User currentUser, Scanner sc);
}
