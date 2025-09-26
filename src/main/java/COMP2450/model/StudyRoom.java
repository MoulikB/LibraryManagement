package COMP2450.model;

import com.google.common.base.Preconditions;

import java.util.ArrayList;

public class StudyRoom implements Resource {
    private String roomName;
    private ArrayList<Booking> bookings;
    Library library;

    public StudyRoom(String roomName, Library library) {
        Preconditions.checkArgument( roomName != null && roomName.length()>0, "Room name cannot be null or empty");
        Preconditions.checkArgument( library != null);
        this.roomName = roomName;
        this.bookings = new ArrayList<>();
        this.library = library;
        library.addResource(this);
    }

    @Override
    public String getResourceName() {
        return "Study Room " + roomName;
    }

    @Override
    public boolean isAvailable(String timeSlot) {
        Preconditions.checkArgument( timeSlot != null, "Time Slot cannot be null");
        boolean result = true;
        for (Booking booking : bookings) {
            if (booking.getTimeSlot().equals(timeSlot)) {
                result = false;
            }
        }
        return true;
    }

    @Override
    public void addBooking(Booking booking) {
        Preconditions.checkArgument(booking != null);
        bookings.add(booking);
    }

    public ArrayList<Booking> getBookings() {
        return bookings;
    }
}
