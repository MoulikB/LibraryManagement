package COMP2450.model;

import java.util.ArrayList;

public class StudyRoom implements Resource {
    private String roomNumber;
    private ArrayList<Booking> bookings;
    Library library;

    public StudyRoom(String roomNumber, Library library) {
        this.roomNumber = roomNumber;
        this.bookings = new ArrayList<>();
        this.library = library;
        library.addResource(this);
    }

    @Override
    public String getResourceName() {
        return "Study Room " + roomNumber;
    }

    @Override
    public boolean isAvailable(String timeSlot) {
        for (Booking booking : bookings) {
            if (booking.getTimeSlot().equals(timeSlot)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void addBooking(Booking booking) {
        bookings.add(booking);
    }

    public ArrayList<Booking> getBookings() {
        return bookings;
    }
}
