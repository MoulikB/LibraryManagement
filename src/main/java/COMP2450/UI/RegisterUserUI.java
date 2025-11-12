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

        while (!isValidEmail(email)) {
            System.out.println("Invalid email address");
            System.out.print("Enter your email: ");
            email = InputValidation.getStringInput();
        }



        System.out.print("Enter your phone number: ");
        String phone = InputValidation.getStringInput();

        while (!isValidPhoneNumber(phone)) {
            System.out.println("Invalid phone number");
            System.out.print("Enter your phone number: ");
            phone = InputValidation.getStringInput();
        }

        User newUser = RegisterUser.createUser(username, password, email, phone);

        if (newUser != null) {
            System.out.println("✅ User registered successfully!");
        } else {
            System.out.println("❌ Registration failed. User may already exist.");
        }

        return newUser;
    }

    /**
     * Check if email is valid
     * @param email to check
     * @return true if it contains @ and . in the specific format below
     */
    private static boolean isValidEmail(String email) {
        boolean output = email != null;
        return output && email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    }

    /**
     * Check if phone is valid
     * @param phoneNumber to check
     * @return true if 10 numbers
     */
    private static boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber.length() == 10 && phoneNumber.matches("[0-9]{10}");
    }
}
