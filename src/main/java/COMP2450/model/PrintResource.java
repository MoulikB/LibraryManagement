package COMP2450.model;

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
            if (resource.isAvailable(slotValue)) {
                System.out.println(slotValue);
            }
        }
        Preconditions.checkNotNull(resource);
    }
}
