package BTOSystem;

import data.UserDatabase;
import data.PasswordHasher;
import controllers.*;
import util.*;

import java.util.*;

/**
 * Entry point for the BTO System application.
 * <p>
 * This class initializes required components and continuously invokes
 * the main menu for user interaction. The general program flow is:
 * MainMenuController → AccountController → User-specific handler → User actions.
 * </p>
 */
public class BTOSystem implements FilePath {

    /**
     * The main method that launches the BTO System application.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        PasswordHasher passwordHasher = new PasswordHasher();
        UserDatabase db = new UserDatabase();
        AuthenticationController ac = new AuthenticationController();
        Scanner sc = new Scanner(System.in);

        Map<String, String> users;

        // Application loop: constantly read updated user data and invoke the main menu
        while (true) {
            users = db.readUsers();
            MainMenuController.mainMenu(sc, users, passwordHasher, db, ac);
        }
    }
}
