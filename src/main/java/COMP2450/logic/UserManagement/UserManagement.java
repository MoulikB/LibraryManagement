package COMP2450.logic.UserManagement;

import COMP2450.domain.User;
import com.google.common.base.Preconditions;

import java.util.ArrayList;
import java.util.List;

/**
 * UserManagement
 * Manages the system’s USERS, allowing creation, removal, lookup,
 * and listing of all registered members.
 */

public class UserManagement {
    private final List<User> USERS = new ArrayList<>();
    private static int nextID = 1;

    /**
     * Add a user to the list.
     * @param user to be added (cannot be null)
     * @return whether the user was added
     */
    public boolean addUser(User user) {
        Preconditions.checkNotNull(user, "User cannot be null");
        checkInvariants();
        boolean output = false;
        boolean exists = userExistsBoolean(user.getUsername());
        if (!exists) {
            USERS.add(user);
            output = true; // user successfully added
        }
        return output; // user already existed
    }

    /**
     * Check the invariants for our domain model object and throw an error if violated
     */
    public void checkInvariants() {
        Preconditions.checkNotNull(USERS);
    }

    public List<User> getUsers() {
        return USERS;
    }

    /**
     * Check if a user with this username exists (true/false).
     * @param username the username we are checking for (can't be null or empty)
     * @return whether user exists
     */
    public boolean userExistsBoolean(String username) {
        checkInvariants();
        Preconditions.checkArgument(username != null && !username.isEmpty(), "Invalid ID");
        boolean userExists = false;
        int i = 0;
        while (!userExists && i < USERS.size()) {
            if (USERS.get(i).getUsername().equals(username)) {
                userExists = true;
            }
            i++;
        }
        checkInvariants();
        return userExists;
    }

    public static void incrementID() {
        nextID++;
    }

    public static int getNextID() {
        return nextID;
    }



}