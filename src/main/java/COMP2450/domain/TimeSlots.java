package COMP2450.domain;

import com.google.common.base.Preconditions;

/**
 * TimeSlots
 * Utility class defining canonical one-hour booking slots,
 * used to standardize scheduling across all resources.
 */

public enum TimeSlots {
    NINE_TO_TEN("09:00–10:00"),
    TEN_TO_ELEVEN("10:00–11:00"),
    ELEVEN_TO_TWELVE("11:00–12:00"),
    TWELVE_TO_ONE("12:00–13:00"),
    ONE_TO_TWO("13:00–14:00"),
    TWO_TO_THREE("14:00–15:00"),
    THREE_TO_FOUR("15:00–16:00"),
    FOUR_TO_FIVE("16:00–17:00");

    private final String label;

    TimeSlots(String label) {
        this.label = label;
    }
    /**
     * @return A human-readable label for this time slot (e.g., "09:00–10:00").
     */
    public String getLabel() {
        return label;
    }

    @Override
    public String toString() {
        return label;
    }

    /**
     * Converts a string to the matching TimeSlots value, ignoring case and spacing.
     * @param input A string label or enum name (can not be null)
     * @return The matching TimeSlots constant, or {@code null} if none match.
     */
    public static TimeSlots fromString(String input) {
        Preconditions.checkArgument(input != null && !input.isEmpty(), "Input cannot be null or empty");
        TimeSlots outputSlot = null;
        for (TimeSlots slot : TimeSlots.values()) {
            if (slot.name().equalsIgnoreCase(input.trim())
                    || slot.label.equalsIgnoreCase(input.trim())) {
                outputSlot = slot;
            }
        }
        return outputSlot;
    }
}
