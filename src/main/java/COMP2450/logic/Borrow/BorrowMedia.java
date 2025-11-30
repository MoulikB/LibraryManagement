package COMP2450.logic.Borrow;

import COMP2450.Exceptions.UnavailableMediaException;
import COMP2450.domain.Media.MediaInterface;
import COMP2450.Exceptions.OverdueMediaException;
import COMP2450.domain.User;
import com.google.common.base.Preconditions;

/**
 * BorrowMedia
 * Handles the process of issuing a media item to a user.
 * This class simply checks the user's fines and then delegates
 * the actual issuing logic to the media object.
 */

public class BorrowMedia {

    /**
     * Attempts to issue the media item to the user.
     *
     * @param user  the user borrowing the media (must not be null)
     * @param media the media to issue (must not be null)
     * @throws OverdueMediaException      if the user has unpaid fines
     * @throws UnavailableMediaException  if the media cannot be issued
     */

    public BorrowMedia(User user, MediaInterface media) throws OverdueMediaException  , UnavailableMediaException {
        Preconditions.checkNotNull(media, "Media cannot be null");
        Preconditions.checkNotNull(user, "User cannot be null");
        issueUser(media, user);
    }


    /**
     * Issues the media to the user after checking for unpaid fines.
     *
     * @param media the media being issued
     * @param user  the user receiving the media
     * @throws OverdueMediaException      if the user has outstanding fines
     * @throws UnavailableMediaException  if the media is unavailable
     */
    private void issueUser(MediaInterface media, User user)
            throws OverdueMediaException, UnavailableMediaException {

        Preconditions.checkNotNull(media, "Media cannot be null");
        Preconditions.checkNotNull(user, "User cannot be null");

        if (user.calculateFinesDue() > 0) {
            throw new OverdueMediaException("Please pay your overdue fines first.");
        }

        media.issueUser(user);
    }
}
