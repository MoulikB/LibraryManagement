package COMP2450.model;

public class PrintResource {
    public static void printResource(Resource resource) {
        System.out.println("\n--- Resource Information ---");
        System.out.println("Name: " + resource.getResourceName());
        System.out.println("Available Time Slots:");
        for (var slotValue : TimeSlots.ONE_HOUR_SLOTS) {
            TimeSlots slot = new TimeSlots(slotValue);
            if (resource.isAvailable(slot)) {
                System.out.println(" - " + slotValue);
            }
        }
    }
}
