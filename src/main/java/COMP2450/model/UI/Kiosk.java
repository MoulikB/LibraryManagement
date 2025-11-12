package COMP2450.model.UI;

import COMP2450.model.*;
import COMP2450.model.BorrowMedia.BookResource;
import COMP2450.model.BorrowMedia.BorrowMedia;
import COMP2450.model.BorrowMedia.TimeSlotSearch;
import COMP2450.model.BorrowMedia.Waitlist;
import COMP2450.model.Exceptions.BookingConflictException;
import COMP2450.model.Exceptions.OverdueMediaException;
import COMP2450.model.Exceptions.UnavailableMediaException;
import COMP2450.model.Media.*;
import COMP2450.model.Pathfinder.PathFinder;
import COMP2450.model.PrintLogic.PrintMap;
import COMP2450.model.PrintLogic.PrintMedia;
import COMP2450.model.PrintLogic.PrintResource;
import COMP2450.model.Resources.Resource;

/**
 * Kiosk
 * Acts as the main user interface for the Library Management System.
 * Handles user login, registration, browsing, borrowing, and booking operations.
 */
public class Kiosk {

    private static User user = null;
    private static Library library;

    /**
     * Entry point of the program. Initializes the library and starts the kiosk.
     */
    public static void main(String[] args) {
        library = LibraryBuilder.initializeLibrary();
        runKiosk();
    }

    /**
     * Keeps the kiosk running continuously.
     * Displays the appropriate menu depending on whether a user is logged in.
     */
    private static void runKiosk() {
        boolean running = true;
        while (running) {
            if (user == null) {
                showWelcomeScreen();
            } else {
                showUserMenu();
            }
        }
    }

    /**
     * Displays the initial welcome screen with login and registration options.
     */
    private static void showWelcomeScreen() {
        System.out.println("\n=== Welcome to the Library " + library.getName() + " Kiosk ===");
        System.out.println("1. Log In");
        System.out.println("2. Register");
        System.out.print("Enter your choice: ");

        int option = InputValidation.getIntInput();

        if (option == 1) {
            user = LogIn.loginUser();
        } else if (option == 2) {
            user = RegisterUser.registerUser();
        } else {
            System.out.println("Invalid option. Try again.");
        }

        if (user != null) {
            System.out.println("Welcome, " + user.getUsername() + "!");
        }
    }

    /**
     * Displays the main user menu and handles user input for each option.
     */
    private static void showUserMenu() {
        boolean stayInMenu = true;

        while (stayInMenu) {
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
                    stayInMenu = false;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }

    /**
     * Logs out the current user.
     */
    private static void logout() {
        System.out.println("Logging out " + user.getUsername() + "...");
        user = null;
    }

    /**
     * Displays the browsing menu for different types of media.
     */
    private static void browseMedia() {
        boolean stayInMenu = true;

        while (stayInMenu) {
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

            if (choice == 1) {
                BrowseMedia.showAllMedia();
            } else if (choice == 2) {
                BrowseMedia.showAllMovies();
            } else if (choice == 3) {
                BrowseMedia.showAllBooks();
            } else if (choice == 4) {
                System.out.print("Enter director name: ");
                BrowseMedia.printByDirector(InputValidation.getStringInput());
            } else if (choice == 5) {
                System.out.print("Enter author name: ");
                BrowseMedia.printByAuthor(InputValidation.getStringInput());
            } else if (choice == 6) {
                System.out.print("Enter media title: ");
                BrowseMedia.searchMedia(InputValidation.getStringInput());
            } else if (choice == 7) {
                stayInMenu = false;
            } else {
                System.out.println("Invalid choice. Try again.");
            }
        }
    }

    /**
     * Allows the user to borrow a media item by ID.
     */
    private static void borrowMedia() {
        System.out.print("Enter media ID to borrow: ");
        int mediaID = InputValidation.getIntInput();
        MediaInterface media = library.showMedia(mediaID);

        if (media == null) {
            System.out.println("❌ Media not found.");
        } else {
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
                } else {
                    System.out.println("Okay. Returning to Menu");
                }
            }
        }
    }

    /**
     * Handles returning borrowed media and optionally adds a review.
     */
    private static void returnMedia() {
        System.out.print("This is the media you have borrowed: ");
        for (var media : user.getItemsIssued()) {
            PrintMedia.printMedia(media);
        }

        System.out.print("\nEnter media ID to return: ");
        int mediaID = InputValidation.getIntInput();
        MediaInterface media = library.showMedia(mediaID);

        if (media == null || !user.getItemsIssued().contains(media)) {
            System.out.println("❌ Media not found or not issued.");
        } else {
            media.returnMedia();
            System.out.println("✅ Media returned successfully.");
            System.out.println("Would you like to leave a review?");
            if (InputValidation.getStringInput().equalsIgnoreCase("Y")) {
                System.out.println("Enter a small comment/para to describe the media: ");
                String comment = InputValidation.getStringInput();

                System.out.println("Enter a star rating out of 10: ");
                int rating = InputValidation.getIntInput();

                new Review(user, media, comment, rating);
                System.out.println("Review has been successfully returned.");
            }
        }
    }

    /**
     * Displays all available library resources.
     */
    private static void viewResources() {
        System.out.println("\n=== Available Resources ===");
        PrintResource.printResources(library);
        System.out.println("=============================");
    }

    /**
     * Allows users to view and book resources, including future bookings.
     */
    private static void bookResource() {
        System.out.print("Enter resource name to view or book: ");
        String resourceName = InputValidation.getStringInput();
        Resource resource = library.getResource(resourceName);
        if (resource == null) {
            System.out.println("❌ Resource not found.");
            return;
        }
        while (true) {
            int choice = promptMenu( "Book a specific time slot (today)",
                    "Book a resource for a future date (within 2 weeks)",
                    "View all available slots (next 2 weeks)",
                    "View next X available slots after a time",
                    "View available slots in a date range", "Go Back" );
            switch (choice) {
                case 1 -> handleBooking(resource, java.time.LocalDate.now());
                case 2 -> handleFutureBooking(resource);
                case 3 -> showTwoWeekAvailability(resource);
                case 4 -> showNextXAfterTime(resource);
                case 5 -> showRangeAvailability(resource);
                case 6 -> { return; }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }

    /**
     * Books a resource for the current day.
     */
    private static void handleBooking(Resource resource, java.time.LocalDate date) {
        TimeSlots[] slots = TimeSlots.values();
        System.out.println("\n📅 " + date + " — Available slots:");
        PrintResource.printBookingAdjusted(resource);

        System.out.print("Select a time slot number: ");
        int slotChoice = InputValidation.getIntInput() - 1;

        if (slotChoice < 0 || slotChoice >= slots.length) {
            System.out.println("Invalid slot number.");
        } else {
            try {
                new BookResource(new Booking(resource, user, slots[slotChoice]));
                System.out.println("✅ Booked " + resource.getResourceName() +
                        " on " + date + " for " + slots[slotChoice]);
            } catch (BookingConflictException e) {
                System.out.println("⚠️ Booking conflict: Already booked by another user.");
            }
        }
    }

    /**
     * Books a resource for a specific future date within 14 days.
     */
    private static void handleFutureBooking(Resource resource) {
        System.out.print("Enter booking date (YYYY-MM-DD): ");
        String input = InputValidation.getStringInput();
        boolean validDate = true;

        try {
            java.time.LocalDate chosenDate = java.time.LocalDate.parse(input);
            java.time.LocalDate today = java.time.LocalDate.now();

            if (chosenDate.isBefore(today) || chosenDate.isAfter(today.plusDays(13))) {
                System.out.println("⚠️ Date must be within the next 14 days.");
                validDate = false;
            }

            if (validDate) {
                System.out.println("\n📅 " + chosenDate + " — Checking available time slots:");
                showDailyAvailability(resource);

                System.out.print("Select an available slot number to book: ");
                int slotChoice = InputValidation.getIntInput() - 1;
                TimeSlots[] slots = TimeSlots.values();

                if (slotChoice < 0 || slotChoice >= slots.length) {
                    System.out.println("Invalid slot number.");
                } else {
                    try {
                        new BookResource(new Booking(resource, user, slots[slotChoice]));
                        System.out.println("✅ Booked " + resource.getResourceName() +
                                " on " + chosenDate + " for " + slots[slotChoice]);
                    } catch (BookingConflictException e) {
                        System.out.println("⚠️ Booking conflict: " + e.getMessage());
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("⚠️ Invalid date format. Use YYYY-MM-DD.");
        }
    }

    /**
     * Displays all time slots for a given date, showing which are available or booked.
     */
    private static void showDailyAvailability(Resource resource) {
        TimeSlots[] slots = TimeSlots.values();
        System.out.println("-------------------------------");
        for (int i = 0; i < slots.length; i++) {
            boolean booked = resource.getUnavailableTimeSlots().contains(slots[i]);
            String status = booked ? "❌ BOOKED" : "✅ AVAILABLE";
            System.out.printf("%d. %s %s%n", (i + 1), slots[i].getLabel(), status);
        }
        System.out.println("-------------------------------");
    }

    /**
     * Shows all available time slots in the next two weeks.
     */
    private static void showTwoWeekAvailability(Resource resource) {
        System.out.println("\n📅 Available slots in the next 2 weeks:");
        var slots = TimeSlotSearch.viewNextTwoWeeks(resource);
        slots.forEach(System.out::println);
    }

    /**
     * Shows the next X available slots after a given time.
     */
    private static void showNextXAfterTime(Resource resource) {
        System.out.print("Enter start time (HH:mm, e.g. 13:00): ");
        String timeInput = InputValidation.getStringInput();
        System.out.print("How many available slots to show? ");
        int count = InputValidation.getIntInput();

        try {
            var slots = TimeSlotSearch.nextXAvailable(resource, java.time.LocalTime.parse(timeInput), count);
            System.out.println("\nNext available slots:");
            slots.forEach(System.out::println);
        } catch (Exception e) {
            System.out.println("⚠️ Invalid time format. Use HH:mm (e.g., 14:00).");
        }
    }

    /**
     * Shows available slots between two given dates.
     */
    private static void showRangeAvailability(Resource resource) {
        System.out.print("Enter start date (YYYY-MM-DD): ");
        String startInput = InputValidation.getStringInput();
        System.out.print("Enter end date (YYYY-MM-DD): ");
        String endInput = InputValidation.getStringInput();

        try {
            var start = java.time.LocalDate.parse(startInput);
            var end = java.time.LocalDate.parse(endInput);
            var slots = TimeSlotSearch.viewInRange(resource, start, end);

            System.out.println("\n📆 Available slots in selected range:");
            slots.forEach(System.out::println);
        } catch (Exception e) {
            System.out.println("⚠️ Invalid date format. Use YYYY-MM-DD (e.g., 2025-11-11).");
        }
    }

    /**
     * Finds and prints a path on the library map from 'K' to a chosen symbol.
     */
    private static void findPathOnMap() {
        System.out.println("\n=== Library Path Finder ===");
        PathFinder pathFinder = new PathFinder(library);
        pathFinder.clearPath();

        System.out.println("Choose a destination symbol:");
        PrintMap.printLegend();
        System.out.print("Enter symbol (e.g., T): ");

        String input = InputValidation.getStringInput().trim().toUpperCase();
        boolean found;
        if (input.isEmpty()) {
            System.out.println("❌ Input cannot be empty.");
        } else {
            found = pathFinder.runForTarget(input.charAt(0));
            System.out.println(found ? "✅ Path found!" : "❌ No path found.");
            pathFinder.printMap();
        }
    }

    /**
     * Displays a numbered menu with options and returns the user's choice.
     *
     * @param options list of options to display
     * @return the selected menu choice as an integer
     */
    private static int promptMenu(String... options) {
        System.out.println("\n--- Select an Option ---");
        for (int i = 0; i < options.length; i++) {
            System.out.println((i + 1) + ". " + options[i]);
        }
        System.out.print("Enter choice: ");
        return InputValidation.getIntInput();
    }
}
