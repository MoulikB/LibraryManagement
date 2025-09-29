package COMP2450.model;

import com.google.common.base.Preconditions;

import java.util.ArrayList;

public class StudyRoom implements Resource {
    private String roomName;
    private ArrayList<Booking> bookings;
    Library library;

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
        library.addResource(this);
    }

    // The display name of this resource.
    public String getResourceName() {
        return "Study Room " + roomName;
    }

    /*
     * Is this time slot free?
     * Returns false if any existing booking has the same timeSlot.
     */
    public boolean isAvailable(String timeSlot) {
        Preconditions.checkArgument( timeSlot != null, "Time Slot cannot be null");
        boolean result = true;
        for (Booking booking : bookings) {
            if (booking.getTimeSlot().equals(timeSlot)) {
                result = false;
                break;
            }
        }
        return result;
    }

    /*
     * Add a booking to this room.
     */
    public void addBooking(Booking booking) {
        Preconditions.checkArgument(booking != null);
        bookings.add(booking);
    }

    // Get all bookings for this study room.
    public ArrayList<Booking> getBookings() {
        return bookings;
    }
}
