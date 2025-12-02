package COMP2450.domain;

import COMP2450.domain.Media.Book;
import COMP2450.domain.Media.MediaInterface;
import COMP2450.domain.Media.Movie;
import com.google.common.base.Preconditions;

/*
 * User
 * A registered library member with a unique ID, username, fines,
 * and issued items
 */
import java.util.ArrayList;
import java.util.List;

public class User {

    private final String username;
    private final String password;
    private final int id;
    private final String email;
    private final String phone;

    private double finesDue;  // how much is owed (if any)
    private final List<MediaInterface> itemsIssued = new ArrayList<>(); // media IDs issued

    /**
     * Create a new User
     * @param email the email of the yser (email can't be empty or null)
     * @param id the ID of the user (has to be greater than 0)
     * @param phone the phone number of the user (has to be a not null and non-empty string)
     * @param username the name of the yser (name can't be empty or null)
     * If checks pass, the user is registered in userDB.
     */
    public User(String username, String password, int id, String email, String phone)  {
        Preconditions.checkArgument(username!= null && !username.isEmpty(), "Username cannot be empty");
        Preconditions.checkArgument(password!=null && !password.isEmpty(), "Password cannot be empty");
        Preconditions.checkArgument(id > 0, "ID cannot be less than 1");
        Preconditions.checkArgument(email != null && !email.isEmpty(), "Email cannot be empty");
        Preconditions.checkArgument(phone != null && !phone.isEmpty(), "Phone number cannot be negative has to be in format : (1234567890)");
        this.username = username;
        this.password = password;
        this.id = id;
        this.email = email;
        this.phone = phone;
        checkInvariants();
    }

    /**
     * Check the invariants for our domain model object and throw an error if violated
     */
    private void checkInvariants() {
        Preconditions.checkArgument(username!= null && !username.isEmpty(), "Username cannot be empty");
        Preconditions.checkArgument(password!=null && !password.isEmpty(), "Password cannot be empty");
        Preconditions.checkArgument(id > 0, "ID cannot be less than 1");
        Preconditions.checkArgument(email != null && !email.isEmpty(), "Email cannot be empty");
        Preconditions.checkArgument(phone != null && !phone.isEmpty(), "Phone number cannot be negative has to be in format : (1234567890)");
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


    /**
     * Compare two users for equality.
     * @param otherUser the other user being compared to. ( can not be null)
     * - Returns true if they are the same object.
     * - Otherwise, returns true if their userNames are equal.
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
            output =  (this.username.equals(otherUser.username));
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

    /**
     * Adds a borrowed media item to the user's issued list.
     *
     * @param media the media item being issued to the user
     */
    public void issue(MediaInterface media) {
        this.itemsIssued.add(media);
    }

    /**
     * Calculates the total amount of fines the user owes.
     * This resets the current fine amount and checks all issued
     * books and movies for overdue status.
     *
     * @return the total fine amount owed
     */
    public double calculateFinesDue() {
        this.finesDue = 0;
        checkBooksFines();
        checkMovieFines();
        return finesDue;
    }

    /**
     * Checks all issued books for overdue fines.
     * Adds a $2 fine for each book overdue by more than 5 days.
     */
    public void checkBooksFines() {
        for (MediaInterface media : itemsIssued) {
            if (media.getMediaType().equals("Book")) {
                checkBookFines((Book) media);
            }
        }
    }

    /**
     * Checks all issued movies for overdue fines.
     * Adds a $2 fine for each movie overdue by more than 5 days.
     */
    public void checkMovieFines() {
        for (MediaInterface media : itemsIssued) {
            if (media.getMediaType().equals("Movie")) {
                checkMovieFines((Movie) media);
            }
        }
    }

    /**
     * Adds a fine if the given book has been issued for more than 5 days.
     *
     * @param book the book to check for overdue status
     */
    public void checkBookFines(Book book) {
        if (book.getIssuedDay() > 5) {
            finesDue += 2;
        }
    }

    /**
     * Adds a fine if the given movie has been issued for more than 5 days.
     *
     * @param movie the movie to check for overdue status
     */
    public void checkMovieFines(Movie movie) {
        if (movie.getIssuedDay() > 5) {
            finesDue += 2;
        }
    }

    /**
     * Returns the user's password.
     *
     * @return the user's password
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * Clear a users fines and gives them a clean slate
     */
    public void clearFines() {
        this.finesDue = 0;

    }


    public String getPhone() {
        return phone;
    }

    public void setItemsIssued(List<MediaInterface> relinked) {
        itemsIssued.clear();
        itemsIssued.addAll(relinked);
    }
}




