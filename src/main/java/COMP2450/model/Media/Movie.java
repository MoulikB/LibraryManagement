package COMP2450.model.Media;

import COMP2450.model.Exceptions.UnavailableMediaException;
import COMP2450.model.Library;
import COMP2450.model.MediaGenres;
import COMP2450.model.Review;
import COMP2450.model.User;
import com.google.common.base.Preconditions;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

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
    public int issuedDays = 1;
    int totalCopies = 0;
    static List<Review> reviews = new ArrayList<>();
    static List<User> currentlyIssuedTo = new ArrayList<>();
    static Stack<User> waitlist = new Stack<>();


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
    public void checkInvariants() {
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
     * Check if this media already exists in its library (same mediaID).
     * Returns true if found, false otherwise.
     * @param media The media we are searching for (can not be null and has to be an instance of movie)
     * @return whether the media exists
     */
    public boolean mediaExists(MediaInterface media) {
        checkInvariants();
        Preconditions.checkNotNull(media);
        Preconditions.checkArgument(media instanceof Movie);
        boolean mediaExists = false;
        Library library = media.getLibrary();

        List<MediaInterface> mediaAvailable = library.getMediaAvailable();
        int index = 0;
        while (!mediaExists && index < mediaAvailable.size()) {
            if (library.getMediaAvailable().get(index) instanceof Movie) {
                if (mediaAvailable.get(index).getMediaID() == media.getMediaID()) {
                    mediaExists = true;
                }
            }
            index++;
        }
        checkInvariants();
        return mediaExists;
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

    public boolean issueUser(User user) throws UnavailableMediaException {
        checkInvariants();
        Preconditions.checkNotNull(user);
        boolean output = false;

        if (this.getAvailableCopies() >= 1) {
            this.borrowMedia(user);
            output = true;
        }
        user.issue(this);
        checkInvariants();
        return output;
    }

    public void addWaitlist(User user) {
        checkInvariants();
        Preconditions.checkNotNull(user);
        waitlist.push(user);
        checkInvariants();
    }
}
