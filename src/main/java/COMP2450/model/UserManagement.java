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
    private static List<User> users = new ArrayList<>();;
    public static int nextID = 1;

    /**
     * Add a user to the list.
     * @param user to be added (cannot be null)
     * @return whether the user was added
     */
    public static boolean addUser(User user) {
        Preconditions.checkNotNull(user, "User cannot be null");
        checkInvariants();
        boolean existsBoolean = userExistsBoolean(user.getID());
        if (!existsBoolean) {
            users.add(user);
        }
        checkInvariants();
        return existsBoolean;
    }

    /**
     * Check the invariants for our domain model object and throw an error if violated
     */
    public static void checkInvariants() {
        Preconditions.checkNotNull(users);
    }

    /**
     * Get a user by ID. Returns the user or null if not found.
     * @param id the ID we are searching for (has to be greater than 0)
     * @return user with corresponding id
     */
    public User getUser(int id) {
        User userFound = null;
        Preconditions.checkArgument(id > 0, "Invalid ID");
        checkInvariants();
        int i = 0;
        while (i < users.size() && userFound == null) {
            if (users.get(i).getID() == id) {
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
     * Check if a user with this ID exists (true/false).
     * @param id The ID we are checking for in our function (has to be greater than 0)
     * @return whether user exists
     */
    public static boolean userExistsBoolean(int id) {
        checkInvariants();
        Preconditions.checkArgument(id > 0, "Invalid ID");
        boolean userExists = false;
        int i = 0;
        while (!userExists && i < users.size()) {
            if (users.get(i).getID() == (id)) {
                userExists = true;
            }
            i++;
        }
        checkInvariants();
        return userExists;
    }

    /**
     * Find a user by ID and return it (or null if not found).
     * @param id the ID we are checking for (has to be greater than 0)
     * @return return user if it exists
     */
    public static User userExists(int id) {
        checkInvariants();
        Preconditions.checkArgument(id > 0, "Invalid ID");
        User userAlreadyExists = null;
        int index = 0;
        while (userAlreadyExists == null && index < users.size()) {
            if (users.get(index).getID() == id) {
                userAlreadyExists = users.get(index);
            }
            index++;
        }
        checkInvariants();
        return (userAlreadyExists);
    }

    /**
     * Remove a user by ID.
     * @param id the ID we are checking for (has to be greater than 0)
     * @return if user was removed
     */
    public boolean removeUser(int id) {
        checkInvariants();
        Preconditions.checkArgument(id > 0, "Invalid ID");
        User userExists = userExists(id);
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