package COMP2450.model;

public interface Resource {
    String getResourceName();
    String getResourceType();
    boolean isAvailable(TimeSlot slot);
    boolean bookResource(User user, TimeSlot slot);
    void releaseResource(TimeSlot slot);
    List<Booking> getBookings();
}

