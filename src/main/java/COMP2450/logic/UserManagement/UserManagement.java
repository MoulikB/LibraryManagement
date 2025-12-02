package COMP2450.logic.UserManagement;

import COMP2450.domain.User;
import com.google.common.base.Preconditions;

import java.util.ArrayList;
import java.util.List;

/**
 Stores and manages the list of registered users.
 Adds users, c hecks if a username exists, and generates user IDs
 */

public class UserManagement {
    private final List<User> users = new ArrayList<>();
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
            users.add(user);
            output = true; // user successfully added
        }
        return output; // user already existed
    }

    /**
     * Check the invariants for our domain model object and throw an error if violated
     */
    public void checkInvariants() {
        Preconditions.checkNotNull(users);
    }

    public List<User> getUsers() {
        return users;
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
        while (!userExists && i < users.size()) {
            if (users.get(i).getUsername().equals(username)) {
                userExists = true;
            }
            i++;
        }
        checkInvariants();
        return userExists;
    }

    public void load(List<User> users) {
        this.users.clear();
        this.users.addAll(users);
    }


    public static void incrementID() {
        nextID++;
    }

    public static int getNextID() {
        return nextID;
    }



}