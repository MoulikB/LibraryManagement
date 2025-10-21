package COMP2450.model;

import com.google.common.base.Preconditions;

import java.util.ArrayList;
import java.util.List;

/**
 * Computer
 * A computer resource located in a library, identified by computer ID,
 * and maintaining non-overlapping bookings.
 */

public class Computer implements Resource {
    private final String computerId;
    private final List<Booking> bookings = new ArrayList<>();
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
        this.library = library;
        checkInvariants();
        library.addResource(this);
    }

    public void checkInvariants(){
        Preconditions.checkArgument(computerId != null && !computerId.isEmpty(),"computerId cannot be null or empty");
        Preconditions.checkNotNull(library, "library can't be null");
    }

    // The display name of this resource.
    public String getResourceName() {
        return computerId;
    }

    /*
     * Is this time slot free?
     * Returns false if any existing booking has the same timeSlot.
     */
    public boolean isAvailable(TimeSlots timeSlot) {
        Preconditions.checkNotNull(timeSlot);
        checkInvariants();
        boolean result = true;
        int index =0;
        while (index < bookings.size() && result){
            if ((bookings.get(index).timeSlot().equals(timeSlot))) {
                result = false;
            }
            index++;
        }
        checkInvariants();
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
    public List<Booking> getBookings() {
        return bookings;
    }

    // Which library this computer belongs to
    public Library getLibrary() {
        return library;
    }
}