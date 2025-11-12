package COMP2450.UI;

import COMP2450.domain.*;
import COMP2450.domain.Media.MediaInterface;
import COMP2450.domain.Resources.Resource;
import COMP2450.logic.*;
import COMP2450.logic.Borrow.*;
import COMP2450.logic.PrintLogic.*;
import COMP2450.logic.UserManagement.*;
import COMP2450.Exceptions.*;
import java.time.LocalDate;
import java.time.LocalTime;

public class UI {

    // Handles the first screen (Login/Register)
    public static User showWelcomeScreen(Library library) {
        System.out.println("\n=== Welcome to the Library " + library.getName() + " Kiosk ===");
        System.out.println("1. Log In");
        System.out.println("2. Register");
        System.out.print("Enter your choice: ");

        int option = InputValidation.getIntInput();
        User user = null;

        if (option == 1) {
            user = LogInUI.promptLogin();
        } else if (option == 2) {
            user = RegisterUserUI.promptRegister();
        } else {
            System.out.println("Invalid option. Try again.");
        }

        if (user != null) {
            System.out.println("Welcome, " + user.getUsername() + "!");
        }

        return user;
    }

    // Displays the main menu once a user is logged in
    public static boolean showUserMenu(Library library, User user) {
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
                case 1 -> browseMedia(library);
                case 2 -> borrowMedia(library, user);
                case 3 -> returnMedia(library, user);
                case 4 -> viewResources(library);
                case 5 -> bookResource(library, user);
                case 6 -> findPathOnMap(library);
                case 7 -> stayInMenu = false;
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
        return false;
    }

    private static void browseMedia(Library library) {
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

            BrowseMedia.setLibrary(library);

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
                case 7 -> stayInMenu = false;
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private static void borrowMedia(Library library, User user) {
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
            try {
                if (InputValidation.getStringInput().equalsIgnoreCase("Y")) {
                    Waitlist.waitlistUser(media, user);
                    System.out.println("📋 Added to waitlist.");
                } else {
                    System.out.println("Okay. Returning to menu.");
                }
            } catch (WaitListedAlready x) {
                System.out.println("Already waitlisted.");
            }
        }
    }

    private static void returnMedia(Library library, User user) {
        System.out.print("This is the media you have borrowed: ");
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
        System.out.println("Would you like to leave a review?");

        if (InputValidation.getStringInput().equalsIgnoreCase("Y")) {
            System.out.println("Enter a small comment: ");
            String comment = InputValidation.getStringInput();
            System.out.println("Enter a star rating out of 10: ");
            int rating = InputValidation.getIntInput();

            new Review(user, media, comment, rating);
            System.out.println("Review has been successfully added.");
        }
    }

    private static void viewResources(Library library) {
        System.out.println("\n=== Available Resources ===");
        PrintResource.printResources(library);
        System.out.println("=============================");
    }

    private static void bookResource(Library library, User user) {
        System.out.print("Enter resource name to view or book: ");
        String resourceName = InputValidation.getStringInput();
        Resource resource = library.getResource(resourceName);
        if (resource == null) {
            System.out.println("❌ Resource not found.");
            return;
        }

        while (true) {
            int choice = promptMenu(
                    "Book a specific time slot (today)",
                    "Book a resource for a future date (within 2 weeks)",
                    "View all available slots (next 2 weeks)",
                    "View next X available slots after a time",
                    "View available slots in a date range",
                    "Go Back"
            );

            switch (choice) {
                case 1 -> handleBooking(resource, user, LocalDate.now());
                case 2 -> handleFutureBooking(resource, user);
                case 3 -> System.out.println(TimeSlotSearch.viewNextTwoWeeks(resource));
                case 4 -> showNextXAfterTime(resource);
                case 5 -> showRangeAvailability(resource);
                case 6 -> { return; }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void handleBooking(Resource resource, User user, LocalDate date) {
        TimeSlots[] slots = TimeSlots.values();
        System.out.println("\n📅 " + date + " — Available slots:");
        PrintResource.printBookingAdjusted(resource);

        System.out.print("Select a time slot number: ");
        int slotChoice = InputValidation.getIntInput() - 1;

        if (slotChoice < 0 || slotChoice >= slots.length) {
            System.out.println("Invalid slot number.");
            return;
        }

        try {
            new BookResource(new Booking(resource, user, slots[slotChoice]));
            System.out.println("✅ Booked " + resource.getResourceName() +
                    " on " + date + " for " + slots[slotChoice]);
        } catch (BookingConflictException e) {
            System.out.println("⚠️ Booking conflict: Already booked by another user.");
        }
    }

    private static void handleFutureBooking(Resource resource, User user) {
        System.out.print("Enter booking date (YYYY-MM-DD): ");
        String input = InputValidation.getStringInput();

        try {
            LocalDate chosenDate = LocalDate.parse(input);
            LocalDate today = LocalDate.now();

            if (chosenDate.isBefore(today) || chosenDate.isAfter(today.plusDays(13))) {
                System.out.println("⚠️ Date must be within the next 14 days.");
                return;
            }

            PrintResource.printBookingAdjusted(resource);
            System.out.print("Select a slot number: ");
            int slotChoice = InputValidation.getIntInput() - 1;
            TimeSlots[] slots = TimeSlots.values();

            if (slotChoice < 0 || slotChoice >= slots.length) {
                System.out.println("Invalid slot number.");
                return;
            }

            new BookResource(new Booking(resource, user, slots[slotChoice]));
            System.out.println("✅ Booked " + resource.getResourceName() +
                    " on " + chosenDate + " for " + slots[slotChoice]);
        } catch (Exception e) {
            System.out.println("⚠️ Invalid date format. Use YYYY-MM-DD.");
        }
    }

    private static void showNextXAfterTime(Resource resource) {
        System.out.print("Enter start time (HH:mm): ");
        String timeInput = InputValidation.getStringInput();
        System.out.print("How many available slots to show? ");
        int count = InputValidation.getIntInput();

        try {
            var slots = TimeSlotSearch.nextXAvailable(resource, LocalTime.parse(timeInput), count);
            System.out.println("\nNext available slots:");
            slots.forEach(System.out::println);
        } catch (Exception e) {
            System.out.println("⚠️ Invalid time format. Use HH:mm (e.g., 14:00).");
        }
    }

    private static void showRangeAvailability(Resource resource) {
        System.out.print("Enter start date (YYYY-MM-DD): ");
        String startInput = InputValidation.getStringInput();
        System.out.print("Enter end date (YYYY-MM-DD): ");
        String endInput = InputValidation.getStringInput();

        try {
            var start = LocalDate.parse(startInput);
            var end = LocalDate.parse(endInput);
            var slots = TimeSlotSearch.viewInRange(resource, start, end);

            System.out.println("\n📆 Available slots in selected range:");
            slots.forEach(System.out::println);
        } catch (Exception e) {
            System.out.println("⚠️ Invalid date format. Use YYYY-MM-DD.");
        }
    }

    private static void findPathOnMap(Library library) {
        System.out.println("\n=== Library Path Finder ===");
        PathFinder pathFinder = new PathFinder(library);
        pathFinder.clearPath();

        System.out.println("Choose a destination symbol:");
        PrintMap.printLegend();
        System.out.print("Enter from one of the above symbols: ");

        System.out.println();

        String input = InputValidation.getStringInput().trim().toUpperCase();
        if (input.isEmpty()) {
            System.out.println("❌ Input cannot be empty.");
            return;
        }

        try {
            pathFinder.runForTarget(input.charAt(0));
            System.out.println("✅ Path found!");
            PrintMap.printMap(pathFinder.getMap());
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Please enter a valid character.");
        }

    }

    private static int promptMenu(String... options) {
        System.out.println("\n--- Select an Option ---");
        for (int i = 0; i < options.length; i++) {
            System.out.println((i + 1) + ". " + options[i]);
        }
        System.out.print("Enter choice: ");
        return InputValidation.getIntInput();
    }
}
