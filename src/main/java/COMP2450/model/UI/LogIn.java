package COMP2450.model.UI;

import COMP2450.model.InputValidation;
import COMP2450.model.User;
import COMP2450.model.UserManagement;

public class LogIn {

    public static User loginUser() {
        System.out.println("\n=== User Login ===");

        User userFound = null;
        boolean keepTrying = true;

        while (keepTrying && userFound == null) {
            System.out.print("Enter your username: ");
            String username = InputValidation.getStringInput();

            System.out.print("Enter your password: ");
            String password = InputValidation.getStringInput();

            if (isInvalid(username) || isInvalid(password)) {
                System.out.println("❌ Username or password cannot be empty. Try again.\n");
                continue;
            }

            // Search for user
            for (User currUser : UserManagement.getUsers()) {
                if (currUser.getUsername().equals(username)
                        && currUser.getPassword().equals(password)) {
                    userFound = currUser;
                    break;
                }
            }

            if (userFound == null) {
                System.out.println("❌ Invalid username or password.");
                System.out.print("Would you like to try again? [Y/N]: ");
                String retry = InputValidation.getStringInput();

                if (!retry.equalsIgnoreCase("Y")) {
                    keepTrying = false;
                }
            } else {
                System.out.println("✅ Login successful! Welcome, " + userFound.getUsername() + ".");
            }
        }

        return userFound;
    }

    private static boolean isInvalid(String input) {
        return input == null || input.trim().isEmpty();
    }
}
