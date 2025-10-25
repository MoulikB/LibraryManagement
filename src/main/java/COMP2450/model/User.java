package COMP2450.model;

import com.google.common.base.Preconditions;

/**
 * User
 * A registered library member with a unique ID, username, fines,
 * issued items, and a history of written reviews.
 */
import java.util.ArrayList;
import java.util.List;

public class User {
   
    private final String username;
    private final int id;
    public static UserManagement userDB = new UserManagement();
    private final String email;
    private final int phone;
    private final List<Review> reviewsWritten;

    private double finesDue;  // how much is owed (if any)
    private final List<Integer> itemsIssued = new ArrayList<>(); // media IDs issued
    // Not using the above two in current implementation, will be added later when waitlist and mediaIssue class is added

    /**
     * Create a new User and add it to the shared user database.
     * @param email
     * @param id
     * @param phone
     * @param username
     * Checks:
     *  - username must not be null or empty
     *  - id must be > 0
     *
     * If checks pass, the user is registered in userDB.
     */
    public User(String username,int id, String email,int phone)  {
        Preconditions.checkArgument(username!= null && !username.isEmpty(), "Username cannot be empty");
        Preconditions.checkArgument(id > 0, "ID cannot be less than 1");
        Preconditions.checkArgument(email != null && !email.isEmpty(), "Email cannot be empty");
        Preconditions.checkArgument(phone > 0, "Phone number cannot be negative has to be in format : (1234567890)");
        this.username = username;
        this.id = id;
        this.email = email;
        this.phone = phone;
        checkInvariants();
        reviewsWritten = new ArrayList<>();
    }

    /**
     * Check the invariants for our domain model object and throw an error if violated
     */
    public void checkInvariants() {
        Preconditions.checkArgument(username!= null && !username.isEmpty(), "Username cannot be empty");
        Preconditions.checkArgument(id > 0, "ID cannot be less than 1");
        Preconditions.checkArgument(email != null && !email.isEmpty(), "Email cannot be empty");
        Preconditions.checkArgument(phone > 0, "Phone number cannot be negative has to be in format : (1234567890)");
    }

    /** Get the user's ID.
     *
     * @return userID
     */
    public int getID() {
        return this.id;
    }

    /** Get the user's username.
     *
     * @return username
     */
    public String getUsername() {
        return this.username;
    }

    /** Get the user's email
     *
     * @return email
     */
    public String getEmail() {
        return this.email;
    }

    /** Get the user's phone
     *
     * @return phone
     */
    public int getPhone() {
        return this.phone;
    }

    /** Add a review
     *
     * @param review
     */
    public void addReview(Review review) {
        Preconditions.checkNotNull(review);
        checkInvariants();
        reviewsWritten.add(review);
        checkInvariants();
    }

    /** Return lists of reviews
     *
     * @return list of reviews
     */
    public List<Review> getReviews() {
        return reviewsWritten;
    }

    /**
     * Compare two users for equality.
     * @param other
     * - Returns true if they are the same object.
     * - Otherwise, returns true if their IDs are equal.
     * @return whether the users or equal
     */
    public boolean equals(User other) {
        checkInvariants();
        Preconditions.checkNotNull(other, "User cannot be null");
        other.checkInvariants();
        boolean output;
        if (this == other) {
            output = true;
        } else {
            output =  (this.id == other.id);
        }
        return output;
    }

    public List<Integer> getItemsIssued() {
        return itemsIssued;
    }
}

