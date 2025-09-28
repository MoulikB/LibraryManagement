package COMP2450.model;

import com.google.common.base.Preconditions;

import java.util.ArrayList;
public class User {
   
    private final String username;
    private final int id;
    public static UserManagement userDB = new UserManagement();
    private double finesDue;  // how much is owed (if any)
    private ArrayList<Integer> itemsIssued = new ArrayList<>(); // media IDs issued
    // Not using the above

    /*
     * Create a new User and add it to the shared user database.
     *
     * Checks:
     *  - username must not be empty
     *  - id must be > 0
     *
     * If checks pass, the user is immediately registered in userDB.
     */
    public User(String username,int id)  {
        Preconditions.checkArgument(!username.isEmpty(), "Username cannot be empty");
        Preconditions.checkArgument(id > 0, "ID cannot be less than 1");
        this.username = username;
        this.id = id;
        userDB.addUser(this);
    }

    // Get the user's ID.
    public int getID() {
        return this.id;
    }


    public String getUsername() {
        return this.username;
    }

    public String userInfo() {
        return "ID : " + getID() + " , Name :" + getUsername();
    }

    /*
     * Remove a user by ID from the shared database.
     * (This method calls into userDB to do the work.)
     */
    public void removeUser(int id) {
        Preconditions.checkArgument(id > 0, "ID cannot be less than 1");
        userDB.removeUser(id);
    }

    // Print all users in the database as a string (from userDB)
    public static void getStringOutput() {
         System.out.println(userDB.getUsers());
    }

    /*
     * Compare two users for equality.
     * - Returns true if they are the same object.
     * - Otherwise, returns true if their IDs are equal.
     */
    public boolean equals(User other) {
        boolean output = false;
        if (this == other) {
            output = true;
        } else {
            output =  this.id == ((User)other).id;
        }
        return output;
    }

}

