package COMP2450.logic;

import COMP2450.domain.Resources.Resource;
import COMP2450.domain.TimeSlots;
import com.google.common.base.Preconditions;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * TimeSlotSearch
 * <p>
 * Helps find and display available booking times for any resource.
 * All searches stay within two weeks (14 days) from today.
 */
public class TimeSlotSearch {

    /** The maximum number of days ahead users can view or book. */
    private static final int MAX_DAYS_AHEAD = 14;

    /**
     * Shows all available time slots for the next two weeks.
     *
     * @param resource the resource to check
     * @return a list of available time slots with dates
     */
    public static List<String> viewNextTwoWeeks(Resource resource) {
        Preconditions.checkNotNull(resource, "Resource cannot be null");
        List<String> available = new ArrayList<>();
        LocalDate today = LocalDate.now();

        for (int i = 0; i < MAX_DAYS_AHEAD; i++) {
            LocalDate date = today.plusDays(i);
            for (TimeSlots slot : TimeSlots.values()) {
                if (!resource.getUnavailableTimeSlots().contains(slot)) {
                    available.add(date + " " + slot.getLabel());
                }
            }
        }
        Preconditions.checkNotNull(resource, "Resource cannot be null");
        return available;
    }

    /**
     * Shows available slots between two given dates.
     * The search will not go outside the 14-day window.
     *
     * @param resource the resource to check
     * @param start starting date (inclusive)
     * @param end ending date (inclusive)
     * @return a list of available slots between those dates
     */
    public static List<String> viewInRange(Resource resource, LocalDate start, LocalDate end) {
        Preconditions.checkNotNull(resource, "Resource cannot be null");
        Preconditions.checkNotNull(start, "Start date cannot be null");
        Preconditions.checkNotNull(end, "End date cannot be null");
        List<String> available = new ArrayList<>();
        LocalDate today = LocalDate.now();
        LocalDate maxDate = today.plusDays(MAX_DAYS_AHEAD - 1);

        // keep the range within the next two weeks
        if (start.isBefore(today)) start = today;
        if (end.isAfter(maxDate)) end = maxDate;

        LocalDate date = start;
        while (!date.isAfter(end)) {
            for (TimeSlots slot : TimeSlots.values()) {
                if (!resource.getUnavailableTimeSlots().contains(slot)) {
                    available.add(date + " " + slot.getLabel());
                }
            }
            date = date.plusDays(1);
        }
        Preconditions.checkNotNull(resource, "Resource cannot be null");
        Preconditions.checkNotNull(start, "Start date cannot be null");
        Preconditions.checkNotNull(end, "End date cannot be null");
        return available;
    }

    /**
     * Finds the next X available time slots after a given time.
     * Looks ahead up to 14 days.
     *
     * @param resource the resource to check
     * @param afterTime the time to start checking from
     * @param x how many open slots to list
     * @return a list of the next available slots
     */
    public static List<String> nextXAvailable(Resource resource, LocalTime afterTime, int x) {
        Preconditions.checkNotNull(resource, "Resource cannot be null");
        Preconditions.checkNotNull(afterTime, "After time cannot be null");
        Preconditions.checkArgument(x > 0, "X number must be greater than zero");
        List<String> available = new ArrayList<>();
        LocalDate today = LocalDate.now();

        int i = 0;
        while (i < MAX_DAYS_AHEAD && available.size() < x) {
            LocalDate date = today.plusDays(i);
            for (TimeSlots slot : TimeSlots.values()) {
                LocalTime slotStart = parseStartTime(slot);
                if (slotStart.isAfter(afterTime) && !resource.getUnavailableTimeSlots().contains(slot)) {
                    available.add(date + " " + slot.getLabel());
                    if (available.size() == x) break;
                }
            }
            i++;
        }
        return available;
    }

    /**
     * Reads the start time from a time slot label and converts it to a LocalTime.
     *
     * @param slot the time slot
     * @return the start time of that slot
     */
    private static LocalTime parseStartTime(TimeSlots slot) {
        Preconditions.checkNotNull(slot, "Slot cannot be null");
        String time = slot.getLabel().substring(0, 5);
        return LocalTime.parse(time);
    }
}
