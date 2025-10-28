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
    private final Library library;

    /**
     * Constructor : Make a new Computer and add it to the library.
     * @param computerId the ID of the new resource (must not be null/empty)
     * @param library the library being added to (must not be null)
     */
    public Computer(String computerId, Library library) {
        Preconditions.checkArgument(computerId != null && !computerId.isEmpty(),"computerId cannot be null or empty");
        Preconditions.checkNotNull(library);
        this.computerId = computerId;
        this.library = library;
        checkInvariants();
        library.addResource(this);
    }

    /**
     * Check the invariants for our domain model object and throw an error if violated
     */
    public void checkInvariants(){
        Preconditions.checkArgument(computerId != null && !computerId.isEmpty(),"computerId cannot be null or empty");
        Preconditions.checkNotNull(library, "library can't be null");
    }

    /**
     * Get Resource name, computerId in this case
     * @return computerId
     */
    public String getResourceName() {
        return computerId;
    }
    
    /**
     * Is this time slot free?
     * Returns false if any existing booking has the same timeSlot.
     * @param timeSlot A timeslot from the given timeslot options we have (must not be null)
     * @return whether available
     */
    public boolean isAvailable(TimeSlots timeSlot) {
        Preconditions.checkNotNull(timeSlot);
        checkInvariants();
        boolean result = true;
        int index =0;
        while (index < bookings.size() && result){
            if ((bookings.get(index).getTimeSlot().equals(timeSlot))) {
                result = false;
            }
            index++;
        }
        checkInvariants();
        return result;
    }

    /**
     * Add a booking to this computer.
     * @param booking The booking to be added for this resource (must not be null)
     */
    public void addBooking(Booking booking) {
        Preconditions.checkNotNull(booking);
        bookings.add(booking);
    }

    /** Get all bookings for this computer.
     *
     * @return a list of all bookings
     */
    public List<Booking> getBookings() {
        return bookings;
    }

    /** Which library this computer belongs to
     * @return library
     */
    public Library getLibrary() {
        return library;
    }
}