package COMP2450.model;

import com.google.common.base.Preconditions;

import java.util.ArrayList;

/**
 * MediaInterface
 * Common interface for borrowable media such as books and movies,
 * defining access and borrow/return behavior.
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
        boolean mediaExists = false;
        Preconditions.checkNotNull(media);
        Library library = media.getLibrary();

        ArrayList<MediaInterface> mediaAvailable = library.getMediaAvailable();
        for (int i = 0; i < mediaAvailable.size() && !mediaExists; i++) {
            if (mediaAvailable.get(i).getMediaID() == media.getMediaID()) {
                mediaExists = true;
            }
        }
        return mediaExists;
    }

    /*
     * Get the actual media object from the library that matches the given mediaID.
     * Returns the matching media, or null if not found.
     */
    static MediaInterface getMedia(MediaInterface media) {
        Preconditions.checkNotNull(media);
        MediaInterface mediaFound = null;
        Library library = media.getLibrary();
        for (MediaInterface mediaAvailable : library.getMediaAvailable()) {
            if (mediaAvailable.getMediaID() == media.getMediaID()) {
                mediaFound = mediaAvailable;
            }
        }
        return mediaFound;
    }

    /*
     * Add this media to its library if it does not already exist.
     * If it exists, increase the copy count instead.
     */
    default void addToLibrary(MediaInterface media) {
        Preconditions.checkNotNull(media);
        if (!mediaExists(media)) {
            media.getLibrary().addMedia(media);
        } else {
            var existingCopy =  getMedia(media);
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
