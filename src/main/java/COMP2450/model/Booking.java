package COMP2450.model;

import com.google.common.base.Preconditions;

/**
 * Booking
 * A reservation record for a resource at a specific time slot,
 * storing which member made the booking.
 */

public class Booking {
    Resource resource;
    User user;
    TimeSlots timeSlot;
    /**
     * Make a new Booking.
     *  @param resource   Which resource is booked
     *  @param user Who booked it
     *  @param timeSlot   1-hour time slot
     * Preconditions:
     *  - resource is not null
     *  - memberName is not null/empty
     *  - timeSlot is not null
     */
    public Booking(Resource resource, User user, TimeSlots timeSlot) {
        Preconditions.checkNotNull(resource, "Resource can't be null");
        Preconditions.checkArgument(user != null, "member name can't be null or empty");
        Preconditions.checkNotNull(timeSlot, "TimeSlots can't be null");
        this.resource = resource;
        this.user = user;
        this.timeSlot = timeSlot;
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
