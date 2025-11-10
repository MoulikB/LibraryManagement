package COMP2450.model.BorrowMedia;

import COMP2450.model.Media.MediaInterface;
import COMP2450.model.User;

public class Waitlist {
    public static void waitlistUser(MediaInterface media, User user) {
        if (!media.getWaitlist().contains(user)) {
            media.addWaitlist(user);
        }
    }
}
