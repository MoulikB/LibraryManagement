package COMP2450.model;

import com.google.common.base.Preconditions;

import java.util.ArrayList;
public class User {
   
    private final String username;
    private final int id;
    public static UserManagement userDB = new UserManagement();
    private ArrayList<Review> reviewsWritten;

    private double finesDue;  // how much is owed (if any)
    private ArrayList<Integer> itemsIssued = new ArrayList<>(); // media IDs issued
    // Not using the above two in current implementation, will be added later when waitlist and mediaIssue class is added

    /*
     * Create a new User and add it to the shared user database.
     *
     * Checks:
     *  - username must not be null or empty
     *  - id must be > 0
     *
     * If checks pass, the user is registered in userDB.
     */
    public User(String username,int id)  {
        Preconditions.checkArgument(username!= null && !username.isEmpty(), "Username cannot be empty");
        Preconditions.checkArgument(id > 0, "ID cannot be less than 1");
        this.username = username;
        this.id = id;
        userDB.addUser(this);
        reviewsWritten = new ArrayList<>();
    }

    // Get the user's ID.
    public int getID() {
        return this.id;
    }

    // Get the user's username.
    public String getUsername() {
        return this.username;
    }

    // Get the user's info.
    public String userInfo() {
        return "ID : " + getID() + " , Name :" + getUsername();
    }

    // Print all users in the database as a string (from userDB)
    public static void getStringOutput() {
         System.out.println(userDB.getUsers());
    }

    public void addReview(Review review) {
        Preconditions.checkNotNull(review);
        reviewsWritten.add(review);
    }

    public ArrayList<Review> getReviews() {
        return reviewsWritten;
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
            output =  this.id == other.id;
        }
        return output;
    }

}

