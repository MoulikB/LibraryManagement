package COMP2450.model;

import com.google.common.base.Preconditions;

/**
 * Represents a booking of a resource for a specific time slot.
 */
public class Booking {
    private Resource resource;  // Which resource is booked
    private String memberName;  // Who booked it
    private String timeSlot;    // 1-hour time slot string

    public Booking(Resource resource, String memberName, String timeSlot) {
        Preconditions.checkNotNull(resource);
        Preconditions.checkArgument(memberName  != null && !memberName.isEmpty() , "member name can't be null or empty");
        Preconditions.checkNotNull(timeSlot);
        this.resource = resource;
        this.memberName = memberName;
        this.timeSlot = timeSlot;
    }

    public Resource getResource() {
        return resource;
    }

    public String getMemberName() {
        return memberName;
    }

    public String getTimeSlot() {
        return timeSlot;
    }

    @Override
    public String toString() {
        return "Booking for " + resource.getResourceName() + " by " + memberName +
                " at " + timeSlot;
    }
}
