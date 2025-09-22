package COMP2450.model;

import java.util.ArrayList;

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

    default boolean mediaExists(MediaInterface media) {
        Library library = media.getLibrary();
        for (MediaInterface mediaAvailable : library.getMediaAvailable()) {
            if (mediaAvailable.getMediaID() == media.getMediaID()) {
                return true;
            }
        }
        return false;
    }

    default MediaInterface getMedia(MediaInterface media) {
        Library library = media.getLibrary();
        for (MediaInterface mediaAvailable : library.getMediaAvailable()) {
            if (mediaAvailable.getMediaID() == media.getMediaID()) {
                return mediaAvailable;
            }
        }
        return null;
    }

    default void addToLibrary(MediaInterface media) {
        if (!mediaExists(media)) {
            media.getLibrary().addMedia(this);
        } else {
            var existingCopy =  getMedia(this);
            media.addCopies();
        }
    }

    default void addReview(Review review) {
        reviews.add(review);
    }

    default ArrayList<Review> getReviews() {
        return reviews;
    }

}
