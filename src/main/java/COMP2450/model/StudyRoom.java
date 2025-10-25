package COMP2450.model;

import com.google.common.base.Preconditions;

import java.util.ArrayList;
import java.util.List;

/**
 * StudyRoom
 * A room resource located in a library, identified by room number,
 * and maintaining non-overlapping bookings.
 */

public class StudyRoom implements Resource {
    private final String roomName;
    private final List<Booking> bookings;
    final Library library;

    /**
     * Make a new StudyRoom and add it to the library.
     * @param library
     * @param roomName
     * Checks:
     *  - roomName is not null or empty
     *  - library is not null
     */
    public StudyRoom(String roomName, Library library) {
        Preconditions.checkArgument( roomName != null && !roomName.isEmpty(), "Room name cannot be null or empty");
        Preconditions.checkArgument( library != null);
        this.roomName = roomName;
        this.bookings = new ArrayList<>();
        this.library = library;
        checkInvariants();
        library.addResource(this);
    }

    /**
     * Check the invariants for our domain model object and throw an error if violated
     */
    public void checkInvariants() {
        Preconditions.checkArgument(roomName != null && !roomName.isEmpty(), "Room name cannot be null");
        Preconditions.checkNotNull(bookings, "Bookings cannot be null");
        Preconditions.checkNotNull(library, "Library cannot be null");
    }

    /** The display name of this resource.
     *
     * @return resource name
     */
    public String getResourceName() {
        return roomName;
    }

    /**
     * Is this time slot free?
     * Returns false if any existing booking has the same timeSlot.
     * @param timeSlot
     * @return whether timeslot is free
     */
    public boolean isAvailable(TimeSlots timeSlot) {
        Preconditions.checkArgument( timeSlot != null, "Time Slot cannot be null");
        boolean result = true;
        int index = 0;
        while (index < bookings.size() && result) {
            if (bookings.get(index).timeSlot.equals(timeSlot)) {
                result = false;
            }
            index++;
        }
        checkInvariants();
        return result;
    }

    /**
     * Add a booking to this room.
     * @param booking
     */
    public void addBooking(Booking booking) {
        checkInvariants();
        Preconditions.checkArgument(booking != null);
        bookings.add(booking);
        checkInvariants();
    }

    /** Get all bookings for this study room.
     *
     * @return list of all bookings
     */
    public List<Booking> getBookings() {
        checkInvariants();
        return bookings;
    }
}
