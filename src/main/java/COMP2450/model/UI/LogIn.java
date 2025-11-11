package COMP2450.model.UI;

import COMP2450.model.InputValidation;
import COMP2450.model.User;
import COMP2450.model.UserManagement;

/**
 * LogIn
 * Handles the login process for returning users.
 * Prompts for credentials and checks them against stored users.
 */
public class LogIn {

    /**
     * Prompts the user for login credentials and validates them.
     * Keeps asking until the user either logs in successfully or chooses to stop.
     *
     * @return the logged-in User object, or null if login fails or is canceled
     */
    public static User loginUser() {
        System.out.println("\n=== User Login ===");

        User result = null;
        boolean keepTrying = true;

        while (keepTrying && result == null) {
            System.out.print("Enter your username: ");
            String username = InputValidation.getStringInput();

            System.out.print("Enter your password: ");
            String password = InputValidation.getStringInput();

            boolean validInput = !(isInvalid(username) || isInvalid(password));

            if (validInput) {
                for (User currUser : UserManagement.getUsers()) {
                    if (currUser.getUsername().equals(username)
                            && currUser.getPassword().equals(password)) {
                        result = currUser;
                    }
                }

                if (result == null) {
                    System.out.println("❌ Invalid username or password.");
                    System.out.print("Would you like to try again? [Y/N]: ");
                    String retry = InputValidation.getStringInput();
                    if (!retry.equalsIgnoreCase("Y")) {
                        keepTrying = false;
                    }
                } else {
                    System.out.println("✅ Login successful! Welcome, " + result.getUsername() + ".");
                }

            } else {
                System.out.println("❌ Username or password cannot be empty. Try again.\n");
            }
        }

        return result;
    }

    /**
     * Checks if a given string is invalid (null or empty).
     *
     * @param input the string to check
     * @return true if invalid, false otherwise
     */
    private static boolean isInvalid(String input) {
        boolean invalid = false;
        if (input == null || input.trim().isEmpty()) {
            invalid = true;
        }
        return invalid;
    }
}
