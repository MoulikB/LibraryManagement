package COMP2450.domain.Media;

import COMP2450.Exceptions.UnavailableMediaException;
import COMP2450.domain.Library;
import COMP2450.domain.MediaGenres;
import COMP2450.domain.Review;
import COMP2450.domain.User;
import com.google.common.base.Preconditions;

import java.util.ArrayList;
import java.util.List;

/**
 * Movie
 * A movie stored in a library’s catalog, identified by an ID,
 * with data like director and genre, and tracked by available copies.
 */

public class Movie implements MediaInterface {
    private final String title;
    private final String director;
    private final int mediaID;
    private final Library library;
    private final MediaGenres genre;
    private int issuedDays = 1;
    private int totalCopies = 0;
    private List<Review> reviews = new ArrayList<>();
    private List<User> currentlyIssuedTo = new ArrayList<>();
    private List<User> waitlist = new ArrayList<>();


    /**
     * Constructor: makes a new Movie and adds it to the given library.
     * @param library the library object this movie currently belongs to (can't be null)
     * @param genre the genre of the movie (can't be null)
     * @param director the director of the movie (can't be null)
     * @param mediaID the ID of the movie (has to be a positive number)
     * @param title the title of the movie (can not be null or empty)
     */
    public Movie(String title, String director, int mediaID,
                 Library library, MediaGenres genre) {
        Preconditions.checkArgument(title!= null && !title.isEmpty(), "Title cannot be null , or empty" );
        Preconditions.checkArgument(director != null && !director.isEmpty(), "Director cannot be null or empty" );
        Preconditions.checkArgument(mediaID > 0,"Media ID cannot be less than 1" );
        Preconditions.checkNotNull(library , "Library cannot be null" );
        Preconditions.checkNotNull(genre , "Genre cannot be null" );
        this.title = title;
        this.director = director;
        this.mediaID = mediaID;
        this.library = library;
        this.genre = genre;
        checkInvariants();

    }

    /**
     * Check the invariants for our domain model object and throw an error if violated
     */
    private void checkInvariants() {
        Preconditions.checkArgument(title!= null && !title.isEmpty(), "Title cannot be null , or empty" );
        Preconditions.checkArgument(director != null && !director.isEmpty(), "Director cannot be null or empty" );
        Preconditions.checkArgument(mediaID > 0,"Media ID cannot be less than 1" );
        Preconditions.checkNotNull(library , "Library cannot be null" );
        Preconditions.checkNotNull(genre , "Genre cannot be null" );
    }

    /** the media type of the interface
     *
     * @return Movie
     */
    public String getMediaType() {
        return "Movie";
    }

    /**
     @return The director of the movie
     */
    public String getCreator() {
        return this.director;
    }

    /** The genre of this movie
     * @return Return the genre of this movie.
     */
    public MediaGenres getMediaGenre() {
        return this.genre;
    }

    /**
     * Borrow one copy.
     * and add a copy to the list of all users who have borrowed it
     */
    public void borrowMedia(User user) {
        checkInvariants();
        this.totalCopies--;
        currentlyIssuedTo.add(user);
        checkInvariants();
    }

    /** Return one copy (increase the available count by 1).
     */
    public void returnMedia() {
        checkInvariants();
        totalCopies++;
        checkInvariants();
    }

    /** Return the title of the movie
     *@return Get the title.
     */
    public String getTitle() {
        return this.title;
    }

    /** Check total available copies of the movie
     * @return How many copies can be borrowed right now.
     */
    public int getAvailableCopies() {
        return this.totalCopies;
    }

    /** Return the library the media is stored in
     *
     * @return Which library this movie belongs to.
     */
    public Library getLibrary() {
        return this.library;
    }

    /** Return the mediaID
     *
     * @return Get the media ID for this movie.
     */
    public int getMediaID() {
        return this.mediaID;
    }

    /** Add one more available copy.
     */
    public void addCopies() {
        checkInvariants();
        this.totalCopies++;
        checkInvariants();
    }

    /**
     * Add a review to the shared reviews list.
     * @param review the review to be added (can not be null)
     */
    public void addReview(Review review) {
        Preconditions.checkNotNull(review);
        reviews.add(review);
    }

    /** Get all reviews from the shared list
     *
     * @return list of all reviews for the movie
     */
    public List<Review> getReviews() {
        return reviews;
    }

    /**
     * Issues this media item to a user if available or if they are first in the waitlist.
     *
     * @param user the user requesting the media
     * @throws UnavailableMediaException if the media is not available for this user
     */
    public void issueUser(User user) throws UnavailableMediaException {
        checkInvariants();
        Preconditions.checkNotNull(user, "User cannot be null");



        // Only allow issuing if the waitlist is empty or user is first in line
        if (waitlist.isEmpty() || waitlist.get(0).equals(user)) {
            if (this.getAvailableCopies() >= 1) {
                this.borrowMedia(user);

                user.issue(this);
                this.removeFromWaitlist(user);
            }
        } else {
            throw new UnavailableMediaException("Media is not available");
        }

        checkInvariants();

    }

    /**
     * Adds a user to the waitlist for this media item.
     *
     * @param user the user to add to the waitlist
     */
    public void addWaitlist(User user) {
        checkInvariants();
        Preconditions.checkNotNull(user, "User cannot be null");
        waitlist.add(user);
        checkInvariants();
    }

    /**
     * Returns the current waitlist for this media item.
     *
     * @return the list of users waiting for this media
     */
    public List<User> getWaitlist() {
        return waitlist;
    }

    /**
     *
     * @return the number of days the media has been issued
     */
    public int getIssuedDay() {
        return issuedDays;
    }

    /**
     * Removes the user from waitlist if media is issued.
     * @param user the user being removed
     */
    public void removeFromWaitlist(User user) {
        checkInvariants();
        this.getWaitlist().remove(user);
        checkInvariants();
    }

    public void setIssuedDay(int issuedDay) {
        this.issuedDays = issuedDay;

    }

}
