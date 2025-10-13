package COMP2450.model;

import com.google.common.base.Preconditions;

import java.util.ArrayList;

/**
 * UserManagement
 * Manages the system’s users, allowing creation, removal, lookup,
 * and listing of all registered members.
 */

public class UserManagement {
    static int nextID = 0;
    private static ArrayList<User> users;

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
        if (!userExistsBoolean(user.getID())) {
            users.add(user);
        } else {
            System.out.println("This user is duplicate");
        }
    }

    /*
     * Get a user by ID. Returns the user or null if not found.
     */
    public User getUser(int id) {
        User userFound = null;
        Preconditions.checkArgument(id > 0, "Invalid ID");
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
        for (int i = 0; i < users.size() && !userExists; i++) {
            if (users.get(i).getID() == (id)) {
                userExists = true;
            }
        }
        return userExists;
    }

    /*
     * Find a user by ID and return it (or null if not found).
     */
    public User userExists(int id) {
        Preconditions.checkArgument(id > 0, "Invalid ID");
        User userAlreadyExists = null;
        for (int i = 0; i < users.size() && (userAlreadyExists == null); i++) {
            if ( (users.get(i)).getID() == (id) ) {
                userAlreadyExists = users.get(i);
            }
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
     * Return all usernames as a single comma-separated string.
     */
    public String getUsers() {
        String output ="";
        for (User user : users) {
            output += user.getUsername() + ", ";
        }
        return output;
    }


    /*
     * Clear the list of users (reset to empty).
     */
    public static void reset() {
        users = new ArrayList<>();
    }

    /*
     * Handles the user registration process by taking and validating input
     */
    public void registerUser() {

        System.out.print("Welcome to the Registration process ");

        System.out.print("Enter Your Username :");
        String username = InputValidation.getStringInput();

        System.out.print("Enter Your Password :");
        String password = InputValidation.getStringInput();

        System.out.print("Enter Your Email Address :");
        String email = InputValidation.getStringInput();

        System.out.print("Enter Your Number :");
        String number = InputValidation.getStringInput();

        Preconditions.checkArgument(username != null && !username.isEmpty(), "Username cant be null");
        Preconditions.checkArgument(password != null && !password.isEmpty(), "Password cant be null");
        Preconditions.checkArgument(email != null && !email.isEmpty(), "Email cant be null");
        Preconditions.checkArgument((number != null) && ((number.length() == 10)), "Number must be not null and 10 digits");

        new User(username,password,email,number, UserManagement.nextID);

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