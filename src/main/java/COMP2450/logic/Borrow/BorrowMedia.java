package COMP2450.logic.Borrow;

import COMP2450.Exceptions.UnavailableMediaException;
import COMP2450.domain.Media.MediaInterface;
import COMP2450.Exceptions.OverdueMediaException;
import COMP2450.domain.User;
import com.google.common.base.Preconditions;

public class BorrowMedia {

    public BorrowMedia(User user, MediaInterface media) throws OverdueMediaException  , UnavailableMediaException {
        Preconditions.checkNotNull(media, "Media cannot be null");
        Preconditions.checkNotNull(user, "User cannot be null");
        issueUser(media, user);
    }

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
