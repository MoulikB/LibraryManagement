package COMP2450.model;

import java.util.Arrays;
import java.util.List;

/**
 * TimeSlots
 * Utility class defining canonical one-hour booking slots,
 * used to standardize scheduling across all resources.
 */

public class TimeSlots {
    public static final List<String> ONE_HOUR_SLOTS = Arrays.asList(
            "09:00-10:00",
            "10:00-11:00",
            "11:00-12:00",
            "12:00-13:00",
            "13:00-14:00",
            "14:00-15:00",
            "15:00-16:00",
            "16:00-17:00"
    );
}
