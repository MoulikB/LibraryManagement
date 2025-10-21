package COMP2450.model;

import com.google.common.base.Preconditions;

/**
 * Booking
 * A reservation record for a resource at a specific time slot,
 * storing which member made the booking.
 *
 * @param resource   Which resource is booked
 * @param memberName Who booked it
 * @param timeSlot   1-hour time slot string
 */

public record Booking(Resource resource, User memberName, TimeSlots timeSlot) {
    /*
     * Make a new Booking.
     *
     * Preconditions:
     *  - resource is not null
     *  - memberName is not null/empty
     *  - timeSlot is not null
     */
    public Booking(Resource resource, User memberName, TimeSlots timeSlot) {
        Preconditions.checkNotNull(resource, "Resource can't be null");
        Preconditions.checkArgument(memberName != null, "member name can't be null or empty");
        Preconditions.checkNotNull(timeSlot, "TimeSlots can't be null");
        this.resource = resource;
        this.memberName = memberName;
        this.timeSlot = timeSlot;
        checkInvariants();
    }

    public void checkInvariants() {
        Preconditions.checkNotNull(resource, "resource can't be null");
        Preconditions.checkArgument(memberName != null, "member name can't be null or empty");
        Preconditions.checkNotNull(timeSlot, "TimeSlots can't be null");
    }

    // A simple text summary of the booking for debugging purposes.
    @Override
    public String toString() {
        checkInvariants();
        return "Booking for " + resource.getResourceName() + " by " + memberName +
                " at " + timeSlot;
    }
}
