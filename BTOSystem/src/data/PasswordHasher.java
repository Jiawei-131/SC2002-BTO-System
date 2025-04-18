package data;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * Utility class for hashing and verifying passwords using SHA-256.
 */
public class PasswordHasher {
    /**
     * Hashes the input password using SHA-256 and returns the resulting hexadecimal string.
     *
     * @param password The plain-text password to be hashed.
     * @return The hashed password as a hexadecimal string.
     * @throws RuntimeException if the SHA-256 algorithm is not available.
     */
    public static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes());

            StringBuilder hexString = new StringBuilder();
            for (byte b : hashedBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error: SHA-256 algorithm not found.", e);
        }
    }
    /**
     * Verifies whether the input password, when hashed, matches the stored hash.
     *
     * @param inputPassword The plain-text password to verify.
     * @param storedHash The hashed password stored in the database.
     * @return {@code true} if the input password matches the stored hash; {@code false} otherwise.
     */
    public static boolean verifyPassword( String inputPassword, String storedHash) {
        String inputHash = hashPassword(inputPassword);
        return inputHash.equals(storedHash);
    }
}