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
    private List<Booking> bookings;
    final Library library;

    /*
     * Make a new StudyRoom and add it to the library.
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

    public void checkInvariants() {
        Preconditions.checkArgument(roomName != null && !roomName.isEmpty(), "Room name cannot be null");
        Preconditions.checkNotNull(bookings, "Bookings cannot be null");
        Preconditions.checkNotNull(library, "Library cannot be null");
    }

    // The display name of this resource.
    public String getResourceName() {
        return roomName;
    }

    /*
     * Is this time slot free?
     * Returns false if any existing booking has the same timeSlot.
     */
    public boolean isAvailable(TimeSlots timeSlot) {
        Preconditions.checkArgument( timeSlot != null, "Time Slot cannot be null");
        boolean result = true;
        int index = 0;
        while (index < bookings.size() && result) {
            if (bookings.get(index).timeSlot().equals(timeSlot)) {
                result = false;
            }
        }
        checkInvariants();
        return result;
    }

    /*
     * Add a booking to this room.
     */
    public void addBooking(Booking booking) {
        checkInvariants();
        Preconditions.checkArgument(booking != null);
        bookings.add(booking);
        checkInvariants();
    }

    // Get all bookings for this study room.
    public List<Booking> getBookings() {
        checkInvariants();
        return bookings;
    }
}
