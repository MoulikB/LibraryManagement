package COMP2450.domain.Resources;

import COMP2450.domain.Library;
import COMP2450.domain.TimeSlots;
import com.google.common.base.Preconditions;

import java.util.ArrayList;
import java.util.List;

/**
 * StudyRoom
 * A room resource located in a library, identified by room number
 */

public class StudyRoom implements Resource {
    private final String roomName;
    private final Library library;
    private final List<TimeSlots> unavailableTimeSlots = new ArrayList<>();

    /**
     * Make a new StudyRoom and add it to the library.
     * @param library The library the study room belongs to (can not be null)
     * @param roomName The room name/number of the object (can not be null or empty)

     */
    public StudyRoom(String roomName, Library library) {
        Preconditions.checkArgument( roomName != null && !roomName.isEmpty(), "Room name cannot be null or empty");
        Preconditions.checkArgument( library != null);
        this.roomName = roomName;
        this.library = library;
        checkInvariants();
        library.addResource(this);
    }

    /**
     * Check the invariants for our domain model object and throw an error if violated
     */
    public void checkInvariants() {
        Preconditions.checkArgument(roomName != null && !roomName.isEmpty(), "Room name cannot be null");
        Preconditions.checkNotNull(library, "Library cannot be null");
        Preconditions.checkNotNull(unavailableTimeSlots, "Unavailable Timeslots List cannot be null");
    }

    /** The display name of this resource.
     *
     * @return resource name
     */
    public String getResourceName() {
        return roomName;
    }

    @Override
    public Library getLibrary() {
        return this.library;
    }

    /**
     * Marks a specific time slot as unavailable for this resource.
     *
     * @param timeSlots the time slot to mark as unavailable
     */
    public void markUnavailable(TimeSlots timeSlots) {
        unavailableTimeSlots.add(timeSlots);
    }

    /**
     * Returns the list of time slots that are currently unavailable.
     *
     * @return a list of unavailable TimeSlots
     */
    public List<TimeSlots> getUnavailableTimeSlots() {
        return unavailableTimeSlots;
    }


}
