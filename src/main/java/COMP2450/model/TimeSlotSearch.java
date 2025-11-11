package COMP2450.model;

import COMP2450.model.Resources.Resource;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * TimeSlotSearch
 * Provides filtering and viewing options for available time slots,
 * limited to a 14-day window starting today.
 */
public class TimeSlotSearch {

    private static final int MAX_DAYS_AHEAD = 14;

    /**
     * View all available time slots for the next 14 days (2 weeks).
     */
    public static List<String> viewNextTwoWeeks(Resource resource) {
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
        return available;
    }

    /**
     * View available slots within a custom date range.
     * The range will be clamped to within 14 days of today.
     */
    public static List<String> viewInRange(Resource resource, LocalDate start, LocalDate end) {
        List<String> available = new ArrayList<>();
        LocalDate today = LocalDate.now();
        LocalDate maxDate = today.plusDays(MAX_DAYS_AHEAD - 1);

        // Clamp range to stay within 14-day window
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
        return available;
    }

    /**
     * Get the next X available slots after a given time today.
     * Only checks slots within the 14-day window.
     */
    public static List<String> nextXAvailable(Resource resource, LocalTime afterTime, int x) {
        List<String> available = new ArrayList<>();
        LocalDate today = LocalDate.now();

        for (int i = 0; i < MAX_DAYS_AHEAD && available.size() < x; i++) {
            LocalDate date = today.plusDays(i);
            for (TimeSlots slot : TimeSlots.values()) {
                LocalTime slotStart = parseStartTime(slot);
                if (slotStart.isAfter(afterTime) && !resource.getUnavailableTimeSlots().contains(slot)) {
                    available.add(date + " " + slot.getLabel());
                    if (available.size() == x) break;
                }
            }
        }
        return available;
    }

    private static LocalTime parseStartTime(TimeSlots slot) {
        String time = slot.getLabel().substring(0, 5);
        return LocalTime.parse(time);
    }
}
