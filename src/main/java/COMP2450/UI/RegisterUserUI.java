package COMP2450.UI;

import COMP2450.domain.User;
import COMP2450.logic.InputValidation;
import COMP2450.logic.UserManagement.RegisterUser;

/**
 * RegisterUserUI
 * Handles all user-facing console interaction for registration.
 */
public class RegisterUserUI {

    /**
     * Guides the user through the registration process using console input.
     * Delegates logic to the RegisterUser class.
     *
     * @return the registered User, or null if registration failed
     */
    public static User promptRegister() {
        System.out.println("\n=== User Registration ===");
        System.out.print("Enter your username: ");
        String username = InputValidation.getStringInput();

        System.out.print("Enter your password: ");
        String password = InputValidation.getStringInput();

        System.out.print("Enter your email address: ");
        String email = InputValidation.getStringInput();

        System.out.print("Enter your phone number: ");
        int phone = InputValidation.getIntInput();

        User newUser = RegisterUser.createUser(username, password, email, phone);

        if (newUser != null) {
            System.out.println("✅ User registered successfully!");
        } else {
            System.out.println("❌ Registration failed. User may already exist.");
        }

        return newUser;
    }
}
