package COMP2450.model;

import com.google.common.base.Preconditions;

/**
 * Booking
 * A reservation record for a resource at a specific time slot,
 * storing which member made the booking.
 */

public class Booking {
    private Resource resource;  // Which resource is booked
    private String memberName;  // Who booked it
    private String timeSlot;    // 1-hour time slot string

    /*
     * Make a new Booking.
     *
     * Preconditions:
     *  - resource is not null
     *  - memberName is not null/empty
     *  - timeSlot is not null
     */
    public Booking(Resource resource, String memberName, String timeSlot) {
        Preconditions.checkNotNull(resource);
        Preconditions.checkArgument(memberName  != null && !memberName.isEmpty() , "member name can't be null or empty");
        Preconditions.checkNotNull(timeSlot);
        this.resource = resource;
        this.memberName = memberName;
        this.timeSlot = timeSlot;
    }

    // Get the booked resource
    public Resource getResource() {
        return resource;
    }

    // Get the name of the member who booked it.
    public String getMemberName() {
        return memberName;
    }

    // Get the 1-hour time slot string.
    public String getTimeSlot() {
        return timeSlot;
    }

    // A simple text summary of the booking.
    @Override
    public String toString() {
        return "Booking for " + resource.getResourceName() + " by " + memberName +
                " at " + timeSlot;
    }
}
