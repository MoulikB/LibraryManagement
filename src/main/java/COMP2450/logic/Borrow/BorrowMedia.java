package COMP2450.logic.Borrow;

import COMP2450.Exceptions.UnavailableMediaException;
import COMP2450.domain.Media.MediaInterface;
import COMP2450.Exceptions.OverdueMediaException;
import COMP2450.domain.User;
import com.google.common.base.Preconditions;

/**
 * BorrowMedia
 * Handles the process of issuing media items to users.
 * Checks for overdue fines and media availability before issuing.
 */
public class BorrowMedia {

    /**
     * Issues a media item to a user if possible.
     * Throws exceptions if the user has outstanding fines
     * or if the media is unavailable.
     *
     * @param media the media item to issue
     * @param user  the user borrowing the media
     * @throws OverdueMediaException      if the user has unpaid fines
     * @throws UnavailableMediaException  if no copies of the media are available
     */
    public static void issueUser(MediaInterface media, User user)
            throws OverdueMediaException, UnavailableMediaException {

        Preconditions.checkNotNull(media, "Media cannot be null");
        Preconditions.checkNotNull(user, "User cannot be null");

        if (user.calculateFinesDue() > 0) {
            throw new OverdueMediaException("Please pay your overdue fines first.");
        }

        media.issueUser(user);

        Preconditions.checkNotNull(media, "Media cannot be null");
        Preconditions.checkNotNull(user, "User cannot be null");
    }
}
