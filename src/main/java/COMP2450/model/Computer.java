package COMP2450.model;

import com.google.common.base.Preconditions;

import java.util.ArrayList;

/**
 * Computer
 * A computer resource located in a library, identified by computer ID,
 * and maintaining non-overlapping bookings.
 */

public class Computer implements Resource {
    private final String computerId;
    private ArrayList<Booking> bookings;
    Library library;

    /*
     * Make a new Computer and add it to the library.
     * Checks:
     *  - computerId is not null/empty
     *  - library is not null
     */
    public Computer(String computerId, Library library) {
        Preconditions.checkArgument(computerId != null && !computerId.isEmpty(),"computerId cannot be null or empty");
        Preconditions.checkNotNull(library);
        this.computerId = computerId;
        this.bookings = new ArrayList<>();
        this.library = library;
        library.addResource(this);
    }

    // The display name of this resource.
    public String getResourceName() {
        return "Computer " + computerId;
    }

    /*
     * Is this time slot free?
     * Returns false if any existing booking has the same timeSlot.
     */
    public boolean isAvailable(String timeSlot) {
        Preconditions.checkNotNull(timeSlot);
        boolean result = true;
        for (int i = 0; i < bookings.size() && result; i++ ) {
            if ((bookings.get(i).getTimeSlot().equals(timeSlot))) {
                result = false;
            }
        }
        return result;
    }

    /*
     * Add a booking to this computer.
     */
    public void addBooking(Booking booking) {
        Preconditions.checkNotNull(booking);
        bookings.add(booking);
    }

    // Get all bookings for this computer.
    public ArrayList<Booking> getBookings() {
        return bookings;
    }

    // Which library this computer belongs to
    public Library getLibrary() {
        return library;
    }
}