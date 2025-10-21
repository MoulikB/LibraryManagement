package COMP2450.model;

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
    int totalCopies = 0;
    static List<Review> reviews = new ArrayList<>();

    /*
     * Constructor: makes a new Movie and adds it to the given library.
     *
     * Preconditions:
     *  - title, director, library, genre are not null or empty
     *  - mediaID is not negative
     */
    public Movie(String title, String director, int mediaID,
                 Library library, MediaGenres genre) {

        this.title = title;
        this.director = director;
        this.mediaID = mediaID;
        this.library = library;
        this.genre = genre;
        checkInvariants();
        library.addMedia(this);

    }

    public void checkInvariants() {
        Preconditions.checkNotNull(title);
        Preconditions.checkNotNull(director);
        Preconditions.checkArgument(mediaID >= 0);
        Preconditions.checkNotNull(library);
        Preconditions.checkNotNull(genre);
        Preconditions.checkArgument(!title.isEmpty());
    }

    // This media is a "Movie".
    public String getMediaType() {
        return "Movie";
    }

    /**
    @return The director of the movie
     */
    public String getCreator() {
        return this.director;
    }

    /**
     *
     * @return Return the genre of this movie.
     */
    public MediaGenres getMediaGenre() {
        return this.genre;
    }

    /**
     * Try to borrow one copy.
     * If at least one copy is available, decrease the count and return true.
     */
    public boolean borrowMedia() {
        checkInvariants();
        boolean result = false;
        if (this.totalCopies > 0) {
            this.totalCopies--;
            result = true;
        }
        checkInvariants();
        return result;
    }

    /** Return one copy (increase the available count by 1).
     */
    public void returnMedia() {
        totalCopies++;
    }

    /** @return Get the title.
     */
    public String getTitle() {
        return this.title;
    }

    /** @return How many copies can be borrowed right now.
     */
    public int getAvailableCopies() {
        return this.totalCopies;
    }

    /**
     *
     * @return Which library this movie belongs to.
     */
    public Library getLibrary() {
        return this.library;
    }

    /**
     *
     * @return Get the media ID for this movie.
     */
    public int getMediaID() {
        return this.mediaID;
    }

    // Add one more available copy.
    public void addCopies() {
        this.totalCopies++;
    }

    /*
     * Check if this media already exists in its library (same mediaID).
     * Returns true if found, false otherwise.
     */
    public boolean mediaExists(MediaInterface media) {
        checkInvariants();
        Preconditions.checkNotNull(media);
        Preconditions.checkArgument(media instanceof Movie);
        boolean mediaExists = false;
        Preconditions.checkNotNull(media);
        Library library = media.getLibrary();

        List<MediaInterface> mediaAvailable = library.getMediaAvailable();
        int index = 0;
        while (!mediaExists && index < mediaAvailable.size()) {
            if (mediaAvailable.get(index).getMediaID() == media.getMediaID()) {
                mediaExists = true;
            }
            index++;
        }
        checkInvariants();
        return mediaExists;
    }

    /*
     * Add a review to the shared reviews list.
     */
    public void addReview(Review review) {
        Preconditions.checkNotNull(review);
        reviews.add(review);
    }

    // Get all reviews from the shared list.
    public List<Review> getReviews() {
        return reviews;
    }
}
