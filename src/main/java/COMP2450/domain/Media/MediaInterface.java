package COMP2450.domain.Media;

import COMP2450.Exceptions.UnavailableMediaException;
import COMP2450.domain.Review;
import COMP2450.domain.User;
import COMP2450.domain.Library;
import COMP2450.domain.MediaGenres;

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


    void issueUser(User user) throws UnavailableMediaException;

    void addWaitlist(User user);

    void removeFromWaitlist(User user);

    List<User> getWaitlist();

    int getIssuedDay();
}
