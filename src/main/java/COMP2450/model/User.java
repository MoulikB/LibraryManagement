package COMP2450.model;

import COMP2450.model.Media.Book;
import COMP2450.model.Media.MediaInterface;
import COMP2450.model.Media.Movie;
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
    private final String password;
    private final int id;
    private final String email;
    private final int phone;
    private final List<Review> reviewsWritten;

    private double finesDue;  // how much is owed (if any)
    private final List<MediaInterface> itemsIssued = new ArrayList<>(); // media IDs issued
    // Not using the above two in current implementation, will be added later when waitlist and mediaIssue class is added

    /**
     * Create a new User and add it to the shared user database.
     * @param email the email of the yser (email can't be empty or null)
     * @param id the ID of the user (has to be greater than 0)
     * @param phone the phone number of the user (has to be greater than 0)
     * @param username the name of the yser (name can't be empty or null)
     * If checks pass, the user is registered in userDB.
     */
    public User(String username, String password, int id, String email,int phone)  {
        Preconditions.checkArgument(username!= null && !username.isEmpty(), "Username cannot be empty");
        Preconditions.checkArgument(password!=null && !password.isEmpty(), "Password cannot be empty");
        Preconditions.checkArgument(id > 0, "ID cannot be less than 1");
        Preconditions.checkArgument(email != null && !email.isEmpty(), "Email cannot be empty");
        Preconditions.checkArgument(phone > 0, "Phone number cannot be negative has to be in format : (1234567890)");
        this.username = username;
        this.password = password;
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
        Preconditions.checkArgument(password!=null && !password.isEmpty(), "Password cannot be empty");
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
     * @param review the reviews that belong to this user
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
     * @param otherUser the other user being compared to. ( can not be null)
     * - Returns true if they are the same object.
     * - Otherwise, returns true if their IDs are equal.
     * @return whether the users or equal
     */
    public boolean equals(User otherUser) {
        checkInvariants();
        Preconditions.checkNotNull(otherUser, "User cannot be null");
        otherUser.checkInvariants();
        boolean output;
        if (this == otherUser) {
            output = true;
        } else {
            output =  (this.id == otherUser.id);
        }
        return output;
    }

    /**
     *
     * @return List of all items issued
     */
    public List<MediaInterface> getItemsIssued() {
        return itemsIssued;
    }

    public void issue(MediaInterface media) {
        this.itemsIssued.add(media);
    }


    public double calculateFinesDue() {
        this.finesDue = 0;
        checkBooksFines();
        checkMovieFines();
        return finesDue;
    }

    public void checkBooksFines() {

        for (MediaInterface media : itemsIssued) {
            if (media.getMediaType().equals("Book")) {
                checkBookFines((Book) media);
            }
        }

    }

    public void checkMovieFines() {

        for (MediaInterface media : itemsIssued) {
            if (media.getMediaType().equals("Movie")) {
                checkMovieFines((Movie) media);
            }
        }

    }

    public void checkBookFines(Book book) {
        if (book.issuedDays > 5) {
            finesDue += 2;
        }
    }

    public void checkMovieFines(Movie movie) {
        if (movie.issuedDays > 5) {
            finesDue += 2;
        }
    }

    public String getPassword() {
        return this.password;
    }
}




