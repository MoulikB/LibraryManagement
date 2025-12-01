package COMP2450.domain.Resources;

import COMP2450.domain.Library;
import COMP2450.domain.TimeSlots;
import com.google.common.base.Preconditions;

import java.util.ArrayList;
import java.util.List;

/**
 * Computer
 * A computer resource located in a library, identified by computer ID
 */

public class Computer implements Resource {
    private final String computerId;
    private final Library library;
    private final List<TimeSlots> unavailableTimeSlots = new ArrayList<>();

    /**
     * Constructor : Make a new Computer and add it to the library.
     * @param computerId the ID of the new resource (must not be null/empty)
     * @param library the library being added to (must not be null)
     */
    public Computer(String computerId, Library library) {
        Preconditions.checkArgument(computerId != null && !computerId.isEmpty(),"computerId cannot be null or empty");
        Preconditions.checkNotNull(library);
        this.computerId = computerId;
        this.library = library;
        checkInvariants();
        library.addResource(this);
    }

    /**
     * Check the invariants for our domain model object and throw an error if violated
     */
    public void checkInvariants(){
        Preconditions.checkArgument(computerId != null && !computerId.isEmpty(),"computerId cannot be null or empty");
        Preconditions.checkNotNull(library, "library can't be null");
        Preconditions.checkNotNull(unavailableTimeSlots, "unavailableTimeSlots can't be null");
    }

    /**
     * Get Resource name, computerId in this case
     * @return computerId
     */
    public String getResourceName() {
        return computerId;
    }

    /**
     * Which library this computer belongs to
     *
     * @return library
     */
    public Library getLibrary() {
        return library;
    }

    /**
     * Marks a given time slot as unavailable for this resource.
     *
     * @param timeSlots the time slot to be marked as unavailable
     */
    public void markUnavailable(TimeSlots timeSlots) {
        Preconditions.checkNotNull(timeSlots, "Time slot cannot be null");
        unavailableTimeSlots.add(timeSlots);
    }

    /**
     * Returns the list of time slots that are currently unavailable for this resource.
     * Note: This returns the actual list, not a copy.
     *
     * @return the list of unavailable time slots
     */
    public List<TimeSlots> getUnavailableTimeSlots() {
        return unavailableTimeSlots;
    }

    public static class ComputerBuilder {
        private String computerId;
        private Library library;
        private final List<TimeSlots> unavailable = new ArrayList<>();

        public ComputerBuilder computerId(String id) {
            Preconditions.checkArgument(id != null && !id.isEmpty(), "computerId cannot be null or empty");
            this.computerId = id;
            return this;
        }

        public ComputerBuilder library(Library lib) {
            Preconditions.checkNotNull(lib, "library cannot be null");
            this.library = lib;
            return this;
        }

        public ComputerBuilder addUnavailable(TimeSlots slot) {
            Preconditions.checkNotNull(slot, "Time slot cannot be null");
            this.unavailable.add(slot);
            return this;
        }

        public Computer build() {
            Preconditions.checkArgument(computerId != null && !computerId.isEmpty(),
                    "computerId must be set before building");
            Preconditions.checkNotNull(library, "library must be set before building");

            // Build the Computer normally
            Computer c = new Computer(computerId, library);

            // Add unavailable time slots AFTER the object exists
            for (TimeSlots t : unavailable) {
                c.markUnavailable(t);
            }

            return c;
        }
    }




}