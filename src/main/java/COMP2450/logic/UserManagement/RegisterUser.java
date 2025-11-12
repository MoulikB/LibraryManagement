package COMP2450.logic.UserManagement;

import COMP2450.domain.User;
import com.google.common.base.Preconditions;

/**
 * RegisterUser
 * Handles user creation, validation, and addition to the user database.
 * No user input or printing occurs here.
 */
public class RegisterUser {

    /**
     * Validates input and creates a new user if possible.
     *
     * @param username The desired username
     * @param password The desired password
     * @param email The user's email address
     * @param phone The user's phone number
     * @return A User object if registration is successful, null otherwise
     */
    public static User createUser(String username, String password, String email, int phone) {
        try {
            Preconditions.checkArgument(username != null && !username.isEmpty(), "Username can't be empty");
            Preconditions.checkArgument(password != null && !password.isEmpty(), "Password can't be empty");
            Preconditions.checkArgument(email != null && !email.isEmpty(), "Email can't be empty");
            Preconditions.checkArgument(phone > 0, "Phone number must be positive");

            User user = new User(username, password, UserManagement.nextID, email, phone);
            boolean added = UserManagement.addUser(user);

            if (added) {
                UserManagement.nextID++;
                return user;
            }

        } catch (IllegalArgumentException e) {
            System.out.println("⚠️ Invalid input: " + e.getMessage());
        }

        return null;
    }
}
