package COMP2450.model.BorrowMedia;

import COMP2450.model.Media.MediaInterface;
import COMP2450.model.User;

public class Waitlist {
    public void waitlistUser(MediaInterface media, User user) {
        media.addWaitlist(user);
    }


}
