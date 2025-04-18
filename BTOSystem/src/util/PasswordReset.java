package util;

import controllers.AuthenticationController;
import entities.User;
import java.util.Scanner;
import data.UserDatabase;

/**
 * Interface for handling password reset functionality for a user.
 * Provides a method to allow a user to reset their password securely.
 */
public interface PasswordReset {

    /**
     * Resets the password of the current user after verifying the old password
     * and ensuring the new password meets the required criteria.
     *
     * @param sc       the Scanner object to read user input.
     * @param currentUser the current user whose password is being reset.
     * @param db       the UserDatabase to update the user’s password.
     * @return the updated user with the new password.
     */
    static public User resetPassword(Scanner sc, User currentUser, UserDatabase db) {
        String password;
        
        // Verify the old password
        do {
            password = GetInput.getLineInput(sc, "your old Password");
        } while (!AuthenticationController.passwordCheck(password, currentUser));
        
        // Verify the new password is valid
        do {
            password = GetInput.getLineInput(sc, "your new Password");
        } while (!AuthenticationController.isValidPassword(password, currentUser));
        
        // Reset and return the updated user with the new password
        return AuthenticationController.resetPassword(currentUser, db, password);
    }
}
