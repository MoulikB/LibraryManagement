package COMP2450.model;

import com.google.common.base.Preconditions;

import java.util.ArrayList;

/*
Interface implementation for movie and book
 */
public interface MediaInterface {

    ArrayList<Review> reviews = new ArrayList<>();

    String getMediaType();

    String getCreator();

    MediaGenres getMediaGenre();

    boolean borrowMedia();

    void returnMedia();

    String getTitle();

    int getAvailableCopies();

    Library getLibrary();

    int getMediaID();

    void addCopies();

    /*
     * Check if this media already exists in its library (same mediaID).
     * Returns true if found, false otherwise.
     */
    default boolean mediaExists(MediaInterface media) {
        Preconditions.checkNotNull(media);
        Library library = media.getLibrary();
        for (MediaInterface mediaAvailable : library.getMediaAvailable()) {
            if (mediaAvailable.getMediaID() == media.getMediaID()) {
                return true;
            }
        }
        return false;
    }

    /*
     * Get the actual media object from the library that matches the given mediaID.
     * Returns the matching media, or null if not found.
     */
    static MediaInterface getMedia(MediaInterface media) {
        Preconditions.checkNotNull(media);
        Library library = media.getLibrary();
        for (MediaInterface mediaAvailable : library.getMediaAvailable()) {
            if (mediaAvailable.getMediaID() == media.getMediaID()) {
                return mediaAvailable;
            }
        }
        return null;
    }

    /*
     * Add this media to its library if it does not already exist.
     * If it exists, increase the copy count instead.
     */
    default void addToLibrary(MediaInterface media) {
        Preconditions.checkNotNull(media);
        if (!mediaExists(media)) {
            media.getLibrary().addMedia(this);
        } else {
            var existingCopy =  getMedia(this);
            existingCopy.addCopies();
        }
    }

    /*
     * Add a review to the shared reviews list.
     */
    default void addReview(Review review) {
        Preconditions.checkNotNull(review);
        reviews.add(review);
    }

    // Get all reviews from the shared list.
    default ArrayList<Review> getReviews() {
        return reviews;
    }

}
