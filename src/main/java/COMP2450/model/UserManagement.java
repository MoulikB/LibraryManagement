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
    private static List<User> users;

    /*
    * UserManagement: stores and manages all User objects.
     */
    public UserManagement() {
        users = new ArrayList<>();
    }

    /*
     * Add a user to the list.
     */
    public void addUser(User user) {
        Preconditions.checkNotNull(user, "User cannot be null");
        checkInvariants();
        if (!userExistsBoolean(user.getID())) {
            users.add(user);
        } else {
            System.out.println("This user is duplicate");
        }
    }

    public void checkInvariants() {
        Preconditions.checkNotNull(users);
    }

    /*
     * Get a user by ID. Returns the user or null if not found.
     */
    public User getUser(int id) {
        User userFound = null;
        Preconditions.checkArgument(id > 0, "Invalid ID");
        checkInvariants();
        for (User user : users) {
            if (user.getID() == id) {
                userFound = user;
            }
        }
        return userFound;
    }

    /*
     * Check if a user with this ID exists (true/false).
     */
    public boolean userExistsBoolean(int id) {
        Preconditions.checkArgument(id > 0, "Invalid ID");
        boolean userExists = false;
        int i = 0;
            while (!userExists && i < users.size()) {
                if (users.get(i).getID() == (id)) {
                    userExists = true;
                }
                i++;
            }
        return userExists;
    }

    /*
     * Find a user by ID and return it (or null if not found).
     */
    public User userExists(int id) {
        Preconditions.checkArgument(id > 0, "Invalid ID");
        User userAlreadyExists = null;
        int index = 0;
        while (userAlreadyExists == null && index < users.size()) {
            if (users.get(index).getID() == id) {
                userAlreadyExists = users.get(index);
            }
            index++;
        }
        return (userAlreadyExists);
    }

    /*
     * Remove a user by ID. Prints a message if the user doesn't exist.
     */
    public void removeUser(int id) {
        Preconditions.checkArgument(id > 0, "Invalid ID");
        User userExists = userExists(id);
        if (userExists!=null) {
            users.remove(userExists);
        } else {
            System.out.println("User does not exist!");
        }
    }

    /*
     * Clear the list of users (reset to empty).
     */
    public static void reset() {
        users = new ArrayList<>();
    }
}