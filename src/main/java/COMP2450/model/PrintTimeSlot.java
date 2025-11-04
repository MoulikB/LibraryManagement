package COMP2450.model;

public class PrintTimeSlot {

    /**
     * A printer function for a timeslot
     * @param timeSlot the timeslot we would like to print
     * @return the string label of that timeslot
     */
    public static String printSlot(TimeSlots timeSlot) {
        return timeSlot.getLabel();
    }
}
