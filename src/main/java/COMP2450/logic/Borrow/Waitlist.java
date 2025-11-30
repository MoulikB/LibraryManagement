package COMP2450.logic.Borrow;

import COMP2450.Exceptions.WaitListedAlready;
import COMP2450.domain.Media.MediaInterface;
import COMP2450.domain.User;
import com.google.common.base.Preconditions;

/**
 * Waitlist
 * Handles adding users to a media item's waitlist.
 * If the user is already on the list, no changes are made.
 */
public class Waitlist {

    public Waitlist(MediaInterface media , User user) {
        Preconditions.checkNotNull(media, "Media cannot be null");
        Preconditions.checkNotNull(user, "User cannot be null");
        waitlistUser(media, user);
    }

    /**
     * Adds a user to the waitlist for a specific media item,
     * if they are not already on it. This method runs silently
     * — it does not print any confirmation messages.
     *
     * @param media the media item to add the user to
     * @param user  the user requesting to be added
     */
    private void waitlistUser(MediaInterface media, User user) throws WaitListedAlready {
        Preconditions.checkNotNull(media, "Media cannot be null");
        Preconditions.checkNotNull(user, "User cannot be null");

        // Adds the user only if they aren't already on the waitlist
        boolean alreadyListed = media.getWaitlist().contains(user);

        if (!alreadyListed) {
            media.addWaitlist(user);
        } else {
            throw new WaitListedAlready("Already listed user");
        }
    }
}
