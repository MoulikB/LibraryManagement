package COMP2450.logic.UserManagement;

import COMP2450.domain.User;
import com.google.common.base.Preconditions;

import java.util.List;

/**
 * LogIn
 * Handles authentication logic for returning users.
 * Does not interact with the console directly.
 */
public class LogIn {


    public LogIn(String username, String password) {
        Preconditions.checkNotNull(username, "Username cannot be null");
        Preconditions.checkNotNull(password, "Password cannot be null");
    }

    /**
     * Authenticates a username/password combination against stored users.
     * @param username input username
     * @param password input password
     * @param userManagement the user manager containing registered users
     * @return User object if credentials match, otherwise null
     */
    public static User authenticate(String username, String password , UserManagement userManagement) {
        Preconditions.checkNotNull(username, "Username cannot be null");
        Preconditions.checkNotNull(password, "Password cannot be null");
        User outputUser = null;
        List<User> users = userManagement.getUsers();

        for (User currUser : users) {
            if (currUser.getUsername().equals(username)
                    && currUser.getPassword().equals(password)) {
                outputUser =  currUser;
            }
        }
        return outputUser;
    }
}
