package COMP2450.logic.UserManagement;

import COMP2450.domain.User;
import COMP2450.logic.UserManagement.UserManagement;

import java.util.List;

/**
 * LogIn
 * Handles authentication logic for returning users.
 * Does not interact with the console directly.
 */
public class LogIn {

    /**
     * Authenticates a username/password combination against stored users.
     * @param username input username
     * @param password input password
     * @return User object if credentials match, otherwise null
     */
    public static User authenticate(String username, String password) {
        if (username == null || password == null ||
                username.isBlank() || password.isBlank()) {
            return null;
        }

        List<User> users = UserManagement.getUsers();

        for (User currUser : users) {
            if (currUser.getUsername().equals(username)
                    && currUser.getPassword().equals(password)) {
                return currUser;
            }
        }
        return null;
    }
}
