package COMP2450.model.PrintLogic;

import COMP2450.model.Library;
import COMP2450.model.Resources.Resource;
import COMP2450.model.TimeSlots;
import com.google.common.base.Preconditions;

/**
 * Utility class responsible for printing information about resources
 *
 **/
public class PrintResource {
    public static void printResource(Resource resource) {
        Preconditions.checkNotNull(resource);
        System.out.println("\n--- Resource Information ---");
        System.out.println("Name: " + resource.getResourceName());
        System.out.println("Available Time Slots:");
        for (var slotValue : TimeSlots.values()) {
            System.out.println(slotValue);
        }
        Preconditions.checkNotNull(resource);
    }

    public static void printBookingAdjusted(Resource resource) {
        Preconditions.checkNotNull(resource);
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

    public static void printResources(Library library) {
        Preconditions.checkNotNull(library);
        System.out.printf("\n--- Library %s Information ---\n",library.getName());
        for (var resource : library.getResources()) {
            printResource(resource);
        }
    }
}
