package COMP2450.model.UI;
//need to comment this out
import COMP2450.model.*;
import COMP2450.model.BorrowMedia.BookResource;
import COMP2450.model.BorrowMedia.BorrowMedia;
import COMP2450.model.BorrowMedia.Waitlist;
import COMP2450.model.Exceptions.BookingConflictException;
import COMP2450.model.Exceptions.OverdueMediaException;
import COMP2450.model.Exceptions.UnavailableMediaException;
import COMP2450.model.Media.*;
import COMP2450.model.PrintLogic.PrintMap;
import COMP2450.model.PrintLogic.PrintMedia;
import COMP2450.model.PrintLogic.PrintResource;
import COMP2450.model.Resources.Resource;

public class Kiosk {

    private static User user = null;
    private static Library library;

    public static void main(String[] args) {
        library = LibraryBuilder.initializeLibrary();
        runKiosk();
    }

    private static void runKiosk() {
        while (true) {
            if (user == null) {
                showWelcomeScreen();
            } else {
                showUserMenu();
            }
        }
    }

    private static void showWelcomeScreen() {
        System.out.println("\n=== Welcome to the Library " + library.getName() +" Kiosk ===");
        System.out.println("1. Log In");
        System.out.println("2. Register");
        System.out.println("0. Exit");
        System.out.print("Enter your choice: ");

        int option = InputValidation.getIntInput();
        switch (option) {
            case 1 -> user = LogIn.loginUser();
            case 2 -> user = RegisterUser.registerUser();
            case 0 -> {
                System.out.println("Goodbye!");
                System.exit(0);
            }
            default -> System.out.println("Invalid option. Try again.");
        }

        if (user != null) {
            System.out.println("Welcome, " + user.getUsername() + "!");
        }
    }

    // =======================
    // USER MENU
    // =======================
    private static void showUserMenu() {
        while (true) {
            int choice = promptMenu(
                    "Browse Media",
                    "Borrow Media",
                    "Return Media",
                    "View Resources",
                    "Book a Resource",
                    "Find Path on Map",
                    "Log Out"
            );

            switch (choice) {
                case 1 -> browseMedia();
                case 2 -> borrowMedia();
                case 3 -> returnMedia();
                case 4 -> viewResources();
                case 5 -> bookResource();
                case 6 -> findPathOnMap();
                case 7 -> {
                    logout();
                    return;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void logout() {
        System.out.println("Logging out " + user.getUsername() + "...");
        user = null;
    }

    // =======================
    // BROWSE MEDIA
    // =======================
    private static void browseMedia() {
        while (true) {
            int choice = promptMenu(
                    "Browse all media",
                    "Browse all movies",
                    "Browse all books",
                    "View movies by director",
                    "View books by author",
                    "Search by title",
                    "Go Back"
            );

            BrowseMedia.library = library;
            switch (choice) {
                case 1 -> BrowseMedia.showAllMedia();
                case 2 -> BrowseMedia.showAllMovies();
                case 3 -> BrowseMedia.showAllBooks();
                case 4 -> {
                    System.out.print("Enter director name: ");
                    BrowseMedia.printByDirector(InputValidation.getStringInput());
                }
                case 5 -> {
                    System.out.print("Enter author name: ");
                    BrowseMedia.printByAuthor(InputValidation.getStringInput());
                }
                case 6 -> {
                    System.out.print("Enter media title: ");
                    BrowseMedia.searchMedia(InputValidation.getStringInput());
                }
                case 7 -> { return; } // Go back
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }

    // =======================
    // BORROW / RETURN
    // =======================
    private static void borrowMedia() {
        System.out.print("Enter media ID to borrow: ");
        int mediaID = InputValidation.getIntInput();
        MediaInterface media = library.showMedia(mediaID);

        if (media == null) {
            System.out.println("❌ Media not found.");
            return;
        }

        try {
            BorrowMedia.issueUser(media, user);
            System.out.println("✅ Media borrowed successfully!");
        } catch (OverdueMediaException e) {
            System.out.println("⚠️  You have overdue items. Please return them first.");
        } catch (UnavailableMediaException e) {
            System.out.print("No copies available. Join waitlist? (Y/N): ");
            if (InputValidation.getStringInput().equalsIgnoreCase("Y")) {
                Waitlist.waitlistUser(media, user);
                System.out.println("📋 Added to waitlist.");
            }
        }
    }

    private static void returnMedia() {
        System.out.print("This is the media you have borrowed : ");

        for (var media : user.getItemsIssued()) {
            PrintMedia.printMedia(media);
        }

        System.out.print("\nEnter media ID to return: ");

        int mediaID = InputValidation.getIntInput();
        MediaInterface media = library.showMedia(mediaID);

        if (media == null || !user.getItemsIssued().contains(media)) {
            System.out.println("❌ Media not found or not issued.");
            return;
        }

        media.returnMedia();
        System.out.println("✅ Media returned successfully.");
    }

    // =======================
    // RESOURCES
    // =======================
    private static void viewResources() {
        System.out.println("\n=== Available Resources ===");
        PrintResource.printResources(library);
        System.out.println("=============================");
    }

    private static void bookResource() {
        System.out.print("Enter resource name to book: ");
        String resourceName = InputValidation.getStringInput();
        Resource resource = library.getResource(resourceName);

        if (resource == null) {
            System.out.println("❌ Resource not found.");
            return;
        }


        TimeSlots[] slots = TimeSlots.values();

        PrintResource.printBookingAdjusted(resource);

        System.out.print("Select a time slot number: ");
        int slotChoice = InputValidation.getIntInput() - 1;
        if (slotChoice < 0 || slotChoice >= slots.length) {
            System.out.println("Invalid slot number.");
            return;
        }

        try {
            new BookResource(new Booking(resource, user, slots[slotChoice]));
            System.out.println("✅ Resource booked for " + slots[slotChoice]);
        } catch (BookingConflictException e) {
            System.out.println("⚠️ Booking conflict: " + e.getMessage());
        }
    }

    // =======================
    // MAP PATH FINDER
    // =======================
    private static void findPathOnMap() {
        System.out.println("\n=== Library Path Finder ===");
        PathFinder pathFinder = new PathFinder(library);
        pathFinder.clearPath();

        System.out.println("Choose a destination symbol:");
        PrintMap.printLegend();
        System.out.print("Enter symbol (e.g., T): ");

        String input = InputValidation.getStringInput().trim().toUpperCase();
        if (input.isEmpty()) {
            System.out.println("❌ Input cannot be empty.");
            return;
        }

        boolean found = pathFinder.runForTarget(input.charAt(0));
        System.out.println(found ? "✅ Path found!" : "❌ No path found.");
        pathFinder.printMap();
    }

    // =======================
    // HELPER MENU
    // =======================
    private static int promptMenu(String... options) {
        System.out.println("\n--- Select an Option ---");
        for (int i = 0; i < options.length; i++) {
            System.out.println((i + 1) + ". " + options[i]);
        }
        System.out.print("Enter choice: ");
        return InputValidation.getIntInput();
    }
}
