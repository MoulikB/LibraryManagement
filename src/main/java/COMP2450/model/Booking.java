package COMP2450.model;

import COMP2450.model.Resources.Resource;
import com.google.common.base.Preconditions;

/**
 * Booking
 * A reservation record for a resource at a specific time slot,
 * storing which member made the booking.
 */

public class Booking {
    private Resource resource;
    private User user;
    private TimeSlots timeSlot;

    /**
     * Constructor : Make a new Booking with some preconditions
     * @param resource  the resource being booked (must not be null)
     * @param user      the user who made the booking (must not be null)
     * @param timeSlot  the one-hour time slot for which the booking applies (must not be null)
     *
     */
    public Booking(Resource resource, User user, TimeSlots timeSlot) {
        Preconditions.checkNotNull(resource, "Resource can't be null");
        Preconditions.checkArgument(user != null, "member name can't be null or empty");
        Preconditions.checkNotNull(timeSlot, "TimeSlots can't be null");
        this.resource = resource;
        this.user = user;
        this.timeSlot = timeSlot;
        resource.markUnavailable(timeSlot);
        checkInvariants();
    }

    /**
     * Check the invariants for our domain model object and throw an error if violated
     */
    public void checkInvariants() {
        Preconditions.checkNotNull(resource, "resource can't be null");
        Preconditions.checkArgument(user != null, "member name can't be null or empty");
        Preconditions.checkNotNull(timeSlot, "TimeSlots can't be null");
    }

    /**
     * @return Resource which is booked
     */
    public Resource getResource() {
        return resource;
    }

    /**
     * @return provide the user
     */
    public User getUser() {
        return user;
    }

    /**
     *
     * @return provide the timeslot
     */
    public TimeSlots getTimeSlot() {
        return timeSlot;
    }



}
