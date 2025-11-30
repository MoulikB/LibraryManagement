package COMP2450.UI;

import COMP2450.domain.User;
import COMP2450.logic.UserManagement.LogIn;

/**
 * LoginUI
 * Handles all user-facing interactions during the login process.
 */
public class LogInUI {

    public LogInUI() {

    }

    /**
     * Displays the login prompt and delegates authentication to LogIn logic.
     * @return the logged-in User, or null if login fails or user cancels
     */
    public User promptLogin() {
        System.out.println("\n=== User Login ===");

        User result = null;
        boolean keepTrying = true;

        while (keepTrying && result == null) {
            System.out.print("Enter your username: ");
            String username = InputValidation.getStringInput();

            System.out.print("Enter your password: ");
            String password = InputValidation.getStringInput();

            if (username.isBlank() || password.isBlank()) {
                System.out.println("❌ Username or password cannot be empty. Try again.\n");
                continue;
            }

            result = LogIn.authenticate(username, password);

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
        }
        return result;
    }
}
