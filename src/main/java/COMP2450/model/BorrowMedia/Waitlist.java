package COMP2450.model.BorrowMedia;

import COMP2450.model.Media.MediaInterface;
import COMP2450.model.User;
import com.google.common.base.Preconditions;

/**
 * Waitlist
 * Handles adding users to a media item's waitlist.
 * If the user is already on the list, no changes are made.
 */
public class Waitlist {

    /**
     * Adds a user to the waitlist for a specific media item,
     * if they are not already on it. This method runs silently
     * — it does not print any confirmation messages.
     *
     * @param media the media item to add the user to
     * @param user  the user requesting to be added
     */
    public static void waitlistUser(MediaInterface media, User user) {
        Preconditions.checkNotNull(media, "Media cannot be null");
        Preconditions.checkNotNull(user, "User cannot be null");

        // Adds the user only if they aren't already on the waitlist
        boolean alreadyListed = media.getWaitlist().contains(user);

        if (!alreadyListed) {
            media.addWaitlist(user);
        } else {
            System.out.println("Already listed user");
        }
    }
}
