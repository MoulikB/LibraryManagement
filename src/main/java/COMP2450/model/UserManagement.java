package COMP2450.model;

import com.google.common.base.Preconditions;

import java.util.ArrayList;
import java.util.List;

/**
 * UserManagement
 * Manages the system’s users, allowing creation, removal, lookup,
 * and listing of all registered members.
 */

public class UserManagement {
    private static List<User> users = new ArrayList<>();
    public static int nextID = 1;

    /**
     * Add a user to the list.
     * @param user to be added (cannot be null)
     * @return whether the user was added
     */
    public static boolean addUser(User user) {
        Preconditions.checkNotNull(user, "User cannot be null");
        checkInvariants();
        boolean output = false;
        boolean exists = userExistsBoolean(user.getUsername());
        if (!exists) {
            users.add(user);
            output = true; // user successfully added
        }
        return output; // user already existed
    }


    /**
     * Check the invariants for our domain model object and throw an error if violated
     */
    public static void checkInvariants() {
        Preconditions.checkNotNull(users);
    }

    /**
     * Get a user by ID. Returns the user or null if not found.
     * @param username the username we are checking for (can't be null or empty)
     * @return user with corresponding id
     */
    public static User getUser(String username) {
        User userFound = null;
        Preconditions.checkArgument(username != null && !username.isEmpty(), "Invalid ID");
        checkInvariants();
        int i = 0;
        while (i < users.size() && userFound == null) {
            if (users.get(i).getUsername().equals(username)) {
                userFound = users.get(i);
            }
            i++;
        }
        checkInvariants();
        return userFound;
    }

    public static List<User> getUsers() {
        return users;
    }

    /**
     * Check if a user with this username exists (true/false).
     * @param username the username we are checking for (can't be null or empty)
     * @return whether user exists
     */
    public static boolean userExistsBoolean(String username) {
        checkInvariants();
        Preconditions.checkArgument(username != null && !username.isEmpty(), "Invalid ID");
        boolean userExists = false;
        int i = 0;
        while (!userExists && i < users.size()) {
            if (users.get(i).getUsername().equals(username)) {
                userExists = true;
            }
            i++;
        }
        checkInvariants();
        return userExists;
    }

    /**
     * Find a user by ID and return it (or null if not found).
     * @param username the username we are checking for (can't be null or empty)
     * @return return user if it exists
     */
    public static User userExists(String username) {
        checkInvariants();
        Preconditions.checkArgument(username != null && !username.isEmpty(), "Invalid ID");
        User userAlreadyExists = null;
        int index = 0;
        while (userAlreadyExists == null && index < users.size()) {
            if (users.get(index).getUsername().equals(username)) {
                userAlreadyExists = users.get(index);
            }
            index++;
        }
        checkInvariants();
        return (userAlreadyExists);
    }

    /**
     * Remove a user by ID.
     * @param username the username we are checking for (can't be null or empty)
     * @return if user was removed
     */
    public static boolean removeUser(String username) {
        checkInvariants();
        Preconditions.checkArgument(username != null && !username.isEmpty(), "Invalid ID");
        User userExists = userExists(username);
        if (userExists!=null) {
            users.remove(userExists);
        }
        checkInvariants();
        return userExists != null;
    }

    /**
     * Clear the list of users (reset to empty).
     */
    public static void reset() {
        users = new ArrayList<>();
    }





}