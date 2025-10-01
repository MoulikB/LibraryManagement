package COMP2450.model;

/**
 * Resource
 * Interface for in library bookable resources : study rooms or computers,
 * providing naming and booking availability checks.
 */

public interface Resource {
    // Each resource should have a name or ID to identify it
    String getResourceName();

    // Each resource should have a way to check availability
    boolean isAvailable(String timeSlot);

    // Each resource should be able to add a booking
    void addBooking(Booking booking);
}

