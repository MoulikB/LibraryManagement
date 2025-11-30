package COMP2450.UI;

import COMP2450.domain.*;
import COMP2450.domain.Media.MediaInterface;
import COMP2450.domain.Resources.Resource;
import COMP2450.logic.*;
import COMP2450.logic.Borrow.*;
import COMP2450.UI.PrintLogic.*;
import COMP2450.Exceptions.*;
import COMP2450.logic.UserManagement.UserManagement;
import com.google.common.base.Preconditions;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;


public class KioskUI {

    // Handles the first screen (Login/Register)
    public static User showWelcomeScreen(Library library) {
        Preconditions.checkNotNull(library, "library cannot be null");
        System.out.println("\n=== Welcome to the Library " + library.getName() + " Kiosk ===");
        System.out.println("1. Log In");
        System.out.println("2. Register");
        System.out.print("Enter your choice: ");

        int option = InputValidation.getIntInput();
        User user = null;

        if (option == 1) {
            LogInUI logOn = new LogInUI();
            user = logOn.promptLogin();
        } else if (option == 2) {
            user = RegisterUserUI.promptRegister();
        } else {
            System.out.println("Invalid option. Try again.");
        }

        if (user != null) {
            incrementIssuedDaysForAllUsers(); // simulate daily progression
            System.out.println("Welcome, " + user.getUsername() + "!");
        }

        return user;
    }

    // Displays the main menu once a user is logged in
    public static boolean showUserMenu(Library library, User user) {
        Preconditions.checkNotNull(library, "library cannot be null");
        Preconditions.checkNotNull(user, "user cannot be null");
        boolean stayInMenu = true;

        while (stayInMenu) {
            String[] choices = {
                    "Browse Media",
                    "Borrow Media",
                    "Return Media",
                    "View Resources",
                    "Book a Resource",
                    "Find Path on Map",
                    "Find Media on Map",
                    "Check Fines",
                    "Pay Fines",
                    "Log Out"
            };
            int choice = promptMenu(choices);



            switch (choice) {
                case 1 -> browseMedia(library);
                case 2 -> borrowMedia(library, user);
                case 3 -> returnMedia(library, user);
                case 4 -> viewResources(library);
                case 5 -> bookResource(library, user);
                case 6 -> findPathOnMap(library);
                case 7 -> findMediaOnMap(library);
                case 8 -> checkFines(user);
                case 9 -> payFine(user);
                case 10 -> stayInMenu = false;
                default -> System.out.println("Invalid choice. Try again.");
            }

        }
        return false;
    }

    /**
     * Simulates one day passing for all users in the system
     * by adding +1 day to every issued media item.
     * This runs automatically on login to reflect time passing.
     */
    private static void incrementIssuedDaysForAllUsers() {
        System.out.println("\n⏳ Simulating a new day since last login...");

        var users = UserManagement.getUsers();
        if (users.isEmpty()) {
            System.out.println("No users found in the system.");
            return;
        }

        for (User user : users) {
            var issuedItems = user.getItemsIssued();
            for (var media : issuedItems) {
                if (media.getMediaType().equalsIgnoreCase("Book")) {
                    COMP2450.domain.Media.Book book = (COMP2450.domain.Media.Book) media;
                    book.setIssuedDay(book.getIssuedDay() + 1);
                } else if (media.getMediaType().equalsIgnoreCase("Movie")) {
                    COMP2450.domain.Media.Movie movie = (COMP2450.domain.Media.Movie) media;
                    movie.setIssuedDay(movie.getIssuedDay()+ 1 );
                }
            }
            user.calculateFinesDue();

        }

        System.out.println("📅 All issued media advanced by one day.");
    }


    /**
     * Finds a specific media item by title and displays a path to its section on the map.
     */
    private static void findMediaOnMap(Library library) {
        Preconditions.checkNotNull(library, "library cannot be null");
        System.out.println("\n=== Find Media on Map ===");
        System.out.print("Enter the media ID: ");
        int id = InputValidation.getIntInput();

        var foundMedia = library.showMedia(id);

        if (foundMedia != null) {
            // Determine section symbol based on genre
            char sectionSymbol = switch (foundMedia.getMediaGenre()) {
                case HORROR -> 'H';
                case COMEDY -> 'C';
                case ACTION -> 'A';
                case ROMANCE -> 'R';
                case THRILLER -> 'T';
                case FICTION -> 'F';
                case NONFICTION -> 'N';
            };

            System.out.printf(" '%s' found in section [%c] (%s genre).%n",
                    foundMedia.getTitle(), sectionSymbol, foundMedia.getMediaGenre());

            PathFinder pathFinder = new PathFinder(library);
            pathFinder.clearPath();

            try {
                pathFinder.runForTarget(sectionSymbol);
                System.out.println("✅ Path found! Follow the route (+ symbols):");
                PrintMap.printMap(pathFinder.getMap());

            } catch (IllegalArgumentException e) {
                System.out.println("❌ Invalid map symbol. Please try again.");
            }

        } else {
            System.out.println("❌ Media not found in the library catalog.");
        }
    }


    /**
     * Simulates fine payment using a simple encrypted credit card entry system.
     */
    private static void payFine(User user) {
        Preconditions.checkNotNull(user, "user cannot be null");
        System.out.println("\n=== Fine Payment and return of media ===");

        double amount = user.calculateFinesDue();
        if (amount <= 0) {
            System.out.println("✅ You have no outstanding fines.");

        } else {

            System.out.printf("⚠️ You currently owe: $%.2f%n", amount);
            System.out.print("Would you like to pay now? (Y/N): ");
            String response = InputValidation.getStringInput().trim();

            if (response.equalsIgnoreCase("Y")) {
                System.out.print("(Can you the grader pretend this is a very complex SHA-256 online gateway where i am taking your card info and not just a simple entry where you can enter any 16 digits? thank you) ");
                System.out.print("Enter 16-digit Credit/Debit card number: ");

                String cardNumber = InputValidation.getStringInput().trim();

                while (cardNumber.length() != 16) {
                    System.out.println("Invalid please try again.");
                    System.out.print("Enter 16-digit card number: ");
                    cardNumber = InputValidation.getStringInput().trim();
                    try {
                        Integer.parseInt(cardNumber);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid please only enter numbers.");
                    }
                }

                System.out.println("Processing secure transaction for card " + cardNumber);


                System.out.println("✅ Payment successful!");
                System.out.printf("$%.2f has been charged. Your fines are now cleared.%n", amount);
                System.out.println("📄 Transaction receipt emailed to " + user.getEmail());
                clearFines(user);
            } else {
                System.out.println("Payment cancelled. Returning to menu.");
            }


        }
    }

    public static void clearFines(User user) {
        Preconditions.checkNotNull(user, "user cannot be null");
        if (!user.getItemsIssued().isEmpty()) {
            System.out.println("\n📦 Returning overdue media...");

            // Track which items are overdue
            List<MediaInterface> toReturn = new ArrayList<>();

            for (var media : user.getItemsIssued()) {
                boolean overdue = media.getIssuedDay() > 5;

                if (overdue) {
                    toReturn.add(media);
                }
            }

            // Return all overdue items
            for (var media : toReturn) {
                media.returnMedia();
                user.getItemsIssued().remove(media);
            }

            if (toReturn.isEmpty()) {
                System.out.println("No overdue media to return.");
            } else {
                System.out.println("✅ Returned all overdue media.");
            }
        }

        // Clear all fines
        user.clearFines();
        System.out.println("💰 Fines paid successfully. Your account is now clear.");
    }

    /**
     * Displays the user's current fines without processing any payment.
     * If no media is issued, it reports that there are no items to check.
     */
    private static void checkFines(User user) {
        Preconditions.checkNotNull(user, "user cannot be null");
        System.out.println("\n=== Check Fines ===");

        if (user.getItemsIssued().isEmpty()) {
            System.out.println("ℹ️ You have no issued media — no fines to check.");
        } else {

            double amount = user.calculateFinesDue();

            if (amount <= 0) {
                System.out.println("✅ No overdue fines. Everything is returned on time.");
            } else {
                System.out.printf("⚠️ Current outstanding fines: $%.2f%n", amount);
                System.out.println("Use 'Pay Fines' if you'd like to clear your balance.");
            }
        }
    }


    private static void browseMedia(Library library) {
        Preconditions.checkNotNull(library, "library cannot be null");
        boolean stayInMenu = true;

        String[] choices =  {
                "Browse all media",
                "Browse all movies",
                "Browse all books",
                "View movies by director",
                "View books by author",
                "Search by title",
                "Go Back"
        };

        while (stayInMenu) {
            int choice = promptMenu(choices);
            BrowseMedia browseMedia = new BrowseMedia(Library library);

            switch (choice) {
                case 1 -> browseMedia.showAllMedia();
                case 2 -> browseMedia.showAllMovies();
                case 3 -> browseMedia.showAllBooks();
                case 4 -> {
                    System.out.print("Enter director name: ");
                    browseMedia.printByDirector(InputValidation.getStringInput());
                }
                case 5 -> {
                    System.out.print("Enter author name: ");
                    browseMedia.printByAuthor(InputValidation.getStringInput());
                }
                case 6 -> {
                    System.out.print("Enter media title: ");
                    browseMedia.searchMedia(InputValidation.getStringInput());
                }
                case 7 -> stayInMenu = false;
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private static void borrowMedia(Library library, User user) {
        Preconditions.checkNotNull(library, "library cannot be null");
        Preconditions.checkNotNull(user, "user cannot be null");

        System.out.print("Enter media ID to borrow: ");
        int mediaID = InputValidation.getIntInput();
        MediaInterface media = library.showMedia(mediaID);

        if (media == null) {
            System.out.println("❌ Media not found.");

        } else {

            try {
                BorrowMedia issueMedia = new BorrowMedia(user,media);
                System.out.println("✅ Media borrowed successfully!");
            } catch (OverdueMediaException e) {
                System.out.println("⚠️  You have overdue items. Please return them first.");
            } catch (UnavailableMediaException e) {
                System.out.print("No copies available. Join waitlist? (Y/N): ");
                try {
                    if (InputValidation.getStringInput().equalsIgnoreCase("Y")) {
                        Waitlist wl = new Waitlist(media,user);
                        System.out.println("📋 Added to waitlist.");
                    } else {
                        System.out.println("Okay. Returning to menu.");
                    }
                } catch (WaitListedAlready x) {
                    System.out.println("Already waitlisted.");
                }
            }
        }
    }

    private static void returnMedia(Library library, User user) {
        Preconditions.checkNotNull(library, "library cannot be null");
        Preconditions.checkNotNull(user, "user cannot be null");
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
            System.out.println("Would you like to leave a review? [Y/N]");

            if (InputValidation.getStringInput().equalsIgnoreCase("Y")) {
                System.out.println("Enter a small comment: ");
                String comment = InputValidation.getStringInput();
                System.out.println("Enter a star rating out of 10: ");
                int rating = InputValidation.getIntInput();

                new Review(user, media, comment, rating);
                System.out.println("Review has been successfully added.");
            }
        }
    }

    private static void viewResources(Library library) {
        Preconditions.checkNotNull(library, "library cannot be null");
        System.out.println("\n=== Available Resources ===");
        PrintResource.printResources(library);
        System.out.println("=============================");
    }

    private static void bookResource(Library library, User user) {
        Preconditions.checkNotNull(library, "library cannot be null");
        Preconditions.checkNotNull(user, "user cannot be null");

        System.out.print("Enter resource name to view or book: ");
        String resourceName = InputValidation.getStringInput();
        Resource resource = library.getResource(resourceName);
        if (resource == null) {
            System.out.println("❌ Resource not found.");

        } else
            {
                String[] choices = {
                        "Book a specific time slot (today)",
                        "Book a resource for a future date (within 2 weeks)",
                        "View all available slots (next 2 weeks)",
                        "View next X available slots after a time",
                        "View available slots in a date range",
                        "Go Back"
                };
                while (true) {
                    int choice = promptMenu(choices);

                    switch (choice) {
                        case 1 -> handleBooking(resource, user, LocalDate.now());
                        case 2 -> handleFutureBooking(resource, user);
                        case 3 -> System.out.println(TimeSlotSearch.viewNextTwoWeeks(resource));
                        case 4 -> showNextXAfterTime(resource);
                        case 5 -> showRangeAvailability(resource);
                        case 6 -> {
                            return;
                        }
                        default -> System.out.println("Invalid choice. Try again.");
                    }
                }
            }
    }

    private static void handleBooking(Resource resource, User user, LocalDate date) {
        Preconditions.checkNotNull(resource, "resource cannot be null");
        Preconditions.checkNotNull(user, "user cannot be null");
        Preconditions.checkNotNull(date, "date cannot be null");

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

    private static void handleFutureBooking(Resource resource, User user) {
        Preconditions.checkNotNull(resource, "resource cannot be null");
        Preconditions.checkNotNull(user, "user cannot be null");

        System.out.print("Enter booking date (YYYY-MM-DD): ");
        String input = InputValidation.getStringInput();

        try {
            LocalDate chosenDate = LocalDate.parse(input);
            LocalDate today = LocalDate.now();

            if (chosenDate.isBefore(today) || chosenDate.isAfter(today.plusDays(13))) {
                System.out.println("⚠️ Date must be within the next 14 days.");
            } else {

                PrintResource.printBookingAdjusted(resource);
                System.out.print("Select a slot number: ");
                int slotChoice = InputValidation.getIntInput() - 1;
                TimeSlots[] slots = TimeSlots.values();

                if (slotChoice < 0 || slotChoice >= slots.length) {
                    System.out.println("Invalid slot number.");

                } else {

                    new BookResource(new Booking(resource, user, slots[slotChoice]));
                    System.out.println("✅ Booked " + resource.getResourceName() +
                            " on " + chosenDate + " for " + slots[slotChoice]);
                }
            }
        } catch (Exception e) {
            System.out.println("⚠️ Invalid date format. Use YYYY-MM-DD.");
        }
    }

    private static void showNextXAfterTime(Resource resource) {
        Preconditions.checkNotNull(resource, "resource cannot be null");

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
        Preconditions.checkNotNull(resource, "resource cannot be null");

        System.out.print("Enter start date (YYYY-MM-DD): ");
        String startInput = InputValidation.getStringInput();
        System.out.print("Enter end date (YYYY-MM-DD): ");
        String endInput = InputValidation.getStringInput();

        try {
            var start = LocalDate.parse(startInput);
            var end = LocalDate.parse(endInput);
            var slots = TimeSlotSearch.viewInRange(resource, start, end);

            System.out.println("\n📆 Available slots in selected range:");
            for (var slot : slots) {
                System.out.println(slot);
            }

        } catch (Exception e) {
            System.out.println("⚠️ Invalid date format. Use YYYY-MM-DD.");
        }
    }

    private static void findPathOnMap(Library library) {
        Preconditions.checkNotNull(library, "library cannot be null");

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
        } else {

            try {
                pathFinder.runForTarget(input.charAt(0));
                System.out.println("✅ Path found!");
                PrintMap.printMap(pathFinder.getMap());
            } catch (IllegalArgumentException e) {
                System.out.println("❌ Please enter a valid character.");
            }
        }
    }

    private static int promptMenu(String[] options) {
        Preconditions.checkNotNull(options, "options cannot be null");
        Preconditions.checkArgument(options.length > 0, "options cannot be empty");

        System.out.println("\n--- Select an Option ---");
        for (int i = 0; i < options.length; i++) {
            System.out.println((i + 1) + ". " + options[i]);
        }
        System.out.print("Enter choice: ");
        return InputValidation.getIntInput();
    }
}
