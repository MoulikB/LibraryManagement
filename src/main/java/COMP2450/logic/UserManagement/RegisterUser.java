package COMP2450.logic.UserManagement;

import COMP2450.domain.User;
import com.google.common.base.Preconditions;
import COMP2450.logic.InputValidation;

/**
 * RegisterUser
 * Handles the user registration process, taking input and validating it
 * before adding the user to the system.
 */
public class RegisterUser {

    /**
     * Registers a new user by prompting for username, password, email, and phone number.
     * Returns the created user if registration succeeds, or null if it fails.
     *
     * @return the registered User object, or null if registration was unsuccessful
     */
    public static User registerUser() {
        System.out.print("Welcome to the Registration process ");

        System.out.print("Enter Your Username: ");
        String username = InputValidation.getStringInput();

        System.out.print("Enter Your Password: ");
        String password = InputValidation.getStringInput();

        System.out.print("Enter Your Email Address: ");
        String email = InputValidation.getStringInput();

        System.out.print("Enter Your Number: ");
        int number = InputValidation.getIntInput();

        Preconditions.checkArgument(username != null && !username.isEmpty(), "Username can't be null or empty");
        Preconditions.checkArgument(password != null && !password.isEmpty(), "Password can't be null or empty");
        Preconditions.checkArgument(email != null && !email.isEmpty(), "Email can't be null or empty");
        Preconditions.checkArgument(number > 0, "Number must be a valid positive integer");

        User result;
        User user = new User(username, password, UserManagement.nextID, email, number);

        boolean added = UserManagement.addUser(user);

        if (added) {
            System.out.println("✅ User registered successfully!");
            UserManagement.nextID++;
            result = user;
        } else {
            System.out.println("❌ User already exists. Please try logging in.");
            result = null;
        }

        return result;
    }
}
