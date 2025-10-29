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
    static int nextID = 0;

    /**
     * Constructor : UserManagement: stores and manages all User objects.
     */
    public UserManagement() {
        users = new ArrayList<>();
    }

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
    public User userExists(int id) {
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

    /*
     * Handles the user registration process by taking and validating input
     */
    public static void registerUser() {

        System.out.print("Welcome to the Registration process ");

        System.out.print("Enter Your Username :");
        String username = InputValidation.getStringInput();

        System.out.print("Enter Your Password :");
        String password = InputValidation.getStringInput();

        System.out.print("Enter Your Email Address :");
        String email = InputValidation.getStringInput();

        System.out.print("Enter Your Number :");
        int number = InputValidation.getIntInput();

        Preconditions.checkArgument(username != null && !username.isEmpty(), "Username cant be null");
        Preconditions.checkArgument(password != null && !password.isEmpty(), "Password cant be null");
        Preconditions.checkArgument(email != null && !email.isEmpty(), "Email cant be null");
        Preconditions.checkArgument((number > 0), "Number must be not null and 10 digits");

        new User(username,password,UserManagement.nextID,email,number );

    }


    /*
     * Handles the user login process by taking and validating input. It retries till the user gives up or gets a valid user
     */
    public User loginUser() {

        User userFound = null;
        boolean flag = true;

        System.out.println("Welcome to the Log In process.");


        while (flag && userFound == null) {
            System.out.print("Enter Your Username :");
            String username = InputValidation.getStringInput();

            System.out.print("Enter Your Password :");
            String password = InputValidation.getStringInput();

            Preconditions.checkArgument(username != null && !username.isEmpty(), "Username cant be null");
            Preconditions.checkArgument(password != null && !password.isEmpty(), "Password cant be null");

            for (var currUser : users) {
                if (((currUser.getUsername()).equals(username)) && ((currUser.getPassword()).equals(password))) {
                    userFound = currUser;
                }
            }

            if (userFound == null) {

                System.out.println("Invalid Username or password");
                System.out.println("Try again? [Y/N]");

                if (!InputValidation.getStringInput().equalsIgnoreCase("y")) {
                    flag = false;
                }
            }
        }

        return userFound;

    }
}