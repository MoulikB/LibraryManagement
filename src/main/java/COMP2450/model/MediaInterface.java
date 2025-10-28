package COMP2450.model;

import java.util.List;

/**
 * MediaInterface
 * Common interface for borrowable media such as books and movies,
 * defining access and borrow/return behavior.
 */

public interface MediaInterface {

    String getMediaType();

    String getCreator();

    MediaGenres getMediaGenre();

    void borrowMedia(User user);

    void returnMedia();

    String getTitle();

    int getAvailableCopies();

    Library getLibrary();

    int getMediaID();

    void addCopies();

    void addReview(Review review);

    List<Review> getReviews();

    boolean mediaExists(MediaInterface media);


    boolean issueUser(User user);

    void addWaitlist(User user);
}
