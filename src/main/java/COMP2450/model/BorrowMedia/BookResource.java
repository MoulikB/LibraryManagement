package COMP2450.model.BorrowMedia;

import COMP2450.model.Booking;
import COMP2450.model.Exceptions.BookingConflictException;
import com.google.common.base.Preconditions;

import java.util.ArrayList;

public class BookResource {
    public Booking booking;
    public static ArrayList<BookResource> bookings = new ArrayList<>();

    public BookResource(Booking booking) throws BookingConflictException {
        Preconditions.checkNotNull(booking , "booking can't be null");
        this.booking = booking;
        checkBooking();
        bookings.add(this);
    }

    public void checkBooking() throws BookingConflictException {
        int index = 0;
        boolean found = false;
        while (!found && index < bookings.size()) {
            var currBooking = bookings.get(index);
            if (currBooking.booking.getResource() ==  booking.getResource()) {
                if (currBooking.booking.getTimeSlot() == booking.getTimeSlot()) {
                    throw new BookingConflictException("A booking has already been made for this timeslot");
                }
            }
        }
    }


}
