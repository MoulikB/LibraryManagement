package COMP2450.model.BorrowMedia;

import COMP2450.model.Exceptions.UnavailableMediaException;
import COMP2450.model.Media.MediaInterface;
import COMP2450.model.Exceptions.OverdueMediaException;
import COMP2450.model.User;
import com.google.common.base.Preconditions;

public class BorrowMedia {
    public static void issueUser(MediaInterface media, User user) throws OverdueMediaException, UnavailableMediaException {
        Preconditions.checkNotNull(media);
        Preconditions.checkNotNull(user);
        if (user.calculateFinesDue() > 0) {
            throw new OverdueMediaException("Please pay your overdue fines first");
        }
        if (!media.issueUser(user)) {
            throw new UnavailableMediaException("No more copies available");
        }
    }
}
