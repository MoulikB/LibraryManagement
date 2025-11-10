package COMP2450.model.Resources;

import COMP2450.model.Library;
import COMP2450.model.TimeSlots;

import java.sql.Time;
import java.util.List;

/**
 * Resource
 * Interface for in library bookable resources : study rooms or computers,
 * providing naming and booking availability checks.
 */

public interface Resource {
    /** Each resource should have a name or ID to identify it
     *
     * @return the name of resource
     */
    String getResourceName();

    Library getLibrary();

    void markUnavailable(TimeSlots timeSlots);

    List<TimeSlots> getUnavailableTimeSlots();

}

