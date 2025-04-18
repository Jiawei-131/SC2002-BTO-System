package util;
/**
 * Custom exception thrown when a user fails to complete the registration process
 * after a defined number of invalid input attempts.
 */
public class RegistrationFailedException extends Exception {
    /**
     * Constructs a new RegistrationFailedException with the specified detail message.
     *
     * @param message The detail message explaining the reason for the failure.
     */
    public RegistrationFailedException(String message) {
        super(message);
    }
}
