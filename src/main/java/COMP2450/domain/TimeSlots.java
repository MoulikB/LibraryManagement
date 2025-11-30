package COMP2450.domain;

import com.google.common.base.Preconditions;

/**
 * TimeSlots
 * Utility class defining  one-hour booking slots,
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
        Preconditions.checkNotNull(label, "label cannot be null");
        Preconditions.checkArgument(!label.isEmpty(), "label cannot be empty");
        this.label = label;
    }

    /**
     * @return A human-readable label for this time slot (e.g., "09:00–10:00").
     */
    public String getLabel() {
        Preconditions.checkNotNull(label, "label cannot be null");
        Preconditions.checkArgument(!label.isEmpty(), "label cannot be empty");
        return label;
    }


}
