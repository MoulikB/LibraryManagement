package COMP2450.domain.Resources;

import COMP2450.domain.Library;
import COMP2450.domain.TimeSlots;

import java.util.List;

/**
 * Resource
 * Interface for in library bookable resources : study rooms or computers
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

