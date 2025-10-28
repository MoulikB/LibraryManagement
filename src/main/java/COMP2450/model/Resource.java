package COMP2450.model;

/**
 * Resource
 * Interface for in library bookable resources : study rooms or computers,
 * providing naming and booking availability checks.
 */

public interface Resource {
    /** Each resource should have a name or ID to identify it
     *
     * @return the name of resource
     */
    String getResourceName();

    /** Each resource should have a way to check availability
     *
     * @param timeSlot the time slot we are searching for
     * @return whether we can book the timeslot
     */
    boolean isAvailable(TimeSlots timeSlot);

    /** Each resource should be able to add a booking
     *
     * @param booking the booking we are adding to for that resource
     */
    void addBooking(Booking booking);
}

