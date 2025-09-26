package COMP2450.model;

import com.google.common.base.Preconditions;

import java.util.ArrayList;

/**
 * Represents a computer resource that can be booked.
 */
public class Computer implements Resource {
    private String computerId;
    private ArrayList<Booking> bookings;
    Library library;

    public Computer(String computerId, Library library) {
        Preconditions.checkArgument(computerId != null && !computerId.isEmpty(),"computerId cannot be null or empty");
        Preconditions.checkNotNull(library);
        this.computerId = computerId;
        this.bookings = new ArrayList<>();
        this.library = library;
        library.addResource(this);
    }

    @Override
    public String getResourceName() {
        return "Computer " + computerId;
    }

    @Override
    public boolean isAvailable(String timeSlot) {
        Preconditions.checkNotNull(timeSlot);
        for (Booking booking : bookings) {
            if (booking.getTimeSlot().equals(timeSlot)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void addBooking(Booking booking) {
        Preconditions.checkNotNull(booking);
        bookings.add(booking);
    }

    public ArrayList<Booking> getBookings() {
        return bookings;
    }

    public Library getLibrary() {
        return library;
    }
}