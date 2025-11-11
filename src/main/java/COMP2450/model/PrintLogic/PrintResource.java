package COMP2450.model.PrintLogic;

import COMP2450.model.Library;
import COMP2450.model.Resources.Resource;
import COMP2450.model.TimeSlots;
import com.google.common.base.Preconditions;

/**
 * PrintResource
 * Handles printing details about library resources and their time slots.
 */
public class PrintResource {

    /**
     * Prints general information about a single resource,
     * including its name and all possible time slots.
     *
     * @param resource the resource to print
     */
    public static void printResource(Resource resource) {
        Preconditions.checkNotNull(resource, "Resource cannot be null");
        System.out.println("\n--- Resource Information ---");
        System.out.println("Name: " + resource.getResourceName());
        System.out.println("Available Time Slots:");

        for (var slotValue : TimeSlots.values()) {
            System.out.println(slotValue);
        }

        Preconditions.checkNotNull(resource);
    }

    /**
     * Prints resource information with current booking availability.
     * Displays each time slot and marks whether it is booked or available.
     *
     * @param resource the resource to print
     */
    public static void printBookingAdjusted(Resource resource) {
        Preconditions.checkNotNull(resource, "Resource cannot be null");
        System.out.println("\n--- Resource Information ---");
        System.out.println("Name: " + resource.getResourceName());
        System.out.println("Currently Available Time Slots:");

        int index = 1;
        for (var slotValue : TimeSlots.values()) {
            System.out.print(index + ". ");
            if (resource.getUnavailableTimeSlots().contains(slotValue)) {
                System.out.println(slotValue + " : Already Booked by another user.");
            } else {
                System.out.println(slotValue);
            }
            index++;
        }
    }

    /**
     * Prints all resources belonging to the specified library.
     *
     * @param library the library whose resources should be printed
     */
    public static void printResources(Library library) {
        Preconditions.checkNotNull(library, "Library cannot be null");
        System.out.printf("\n--- Library %s Information ---%n", library.getName());
        for (var resource : library.getResources()) {
            printResource(resource);
        }
    }
}
