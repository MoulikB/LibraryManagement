package COMP2450.model.BorrowMedia;

import COMP2450.model.Booking;
import COMP2450.model.Exceptions.BookingConflictException;
import com.google.common.base.Preconditions;
import java.util.ArrayList;

/**
 * BookResource
 * Handles the process of reserving a resource for a specific time slot.
 * Prevents conflicts by checking existing bookings before adding a new one.
 */
public class BookResource {

    /** The booking details for this resource. */
    public Booking booking;

    /** Shared list of all active bookings in the system. */
    public static ArrayList<BookResource> bookings = new ArrayList<>();

    /**
     * Creates a new BookResource entry after validating that
     * no conflicting booking already exists.
     *
     * @param booking the booking to register
     * @throws BookingConflictException if a booking already exists for the same resource and time slot
     */
    public BookResource(Booking booking) throws BookingConflictException {
        Preconditions.checkNotNull(booking, "Booking cannot be null");
        this.booking = booking;
        checkBooking();
        bookings.add(this);
    }

    /**
     * Checks the existing bookings to ensure that this booking does not
     * overlap with another booking for the same resource and time slot.
     *
     * @throws BookingConflictException if a conflict is found
     */
    public void checkBooking() throws BookingConflictException {
        int index = 0;
        boolean conflictFound = false;

        while (!conflictFound && index < bookings.size()) {
            var currBooking = bookings.get(index);

            if (currBooking.booking.getResource().equals(booking.getResource())
                    && currBooking.booking.getTimeSlot().equals(booking.getTimeSlot())) {
                conflictFound = true;
            }

            index++;
        }

        if (conflictFound) {
            throw new BookingConflictException("A booking has already been made for this time slot.");
        }
    }
}
