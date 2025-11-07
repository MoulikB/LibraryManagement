package COMP2450.model.UI;

import COMP2450.model.*;
import COMP2450.model.BookResource;
import COMP2450.model.BorrowMedia.BorrowMedia;
import COMP2450.model.Exceptions.BookingConflictException;
import COMP2450.model.Exceptions.OverdueMediaException;
import COMP2450.model.Exceptions.UnavailableMediaException;
import COMP2450.model.Media.*;
import COMP2450.model.PrintLogic.PrintMap;
import COMP2450.model.PrintLogic.PrintResource;


public class Kiosk {

    private static User user = null;
    private static Library library;

    public static void main(String[] args) {
        library = initializeLibrary();
        runKiosk();
    }

    // =======================
    // INITIALIZATION
    // =======================
    private static Library initializeLibrary() {
        System.out.println("Enter library name (input can't be null or empty): ");
        String name = InputValidation.getStringInput();
        Library lib = new Library(name);
        addMedia(lib);
        addResources(lib);
        return lib;
    }

    private static void addMedia(Library library) {
        library.addMedia(new Book("1984", "George Orwell", "Penguin", MediaGenres.FICTION, 123, library));
        library.addMedia(new Book("To Kill a Mockingbird", "Harper Lee", "J.B. Lippincott & Co.", MediaGenres.FICTION, 124, library));
        library.addMedia(new Book("The Great Gatsby", "F. Scott Fitzgerald", "Scribner", MediaGenres.ROMANCE, 125, library));
        library.addMedia(new Book("The Hobbit", "J.R.R. Tolkien", "Allen & Unwin", MediaGenres.FICTION, 126, library));
        library.addMedia(new Book("The Catcher in the Rye", "J.D. Salinger", "Little, Brown and Company", MediaGenres.FICTION, 127, library));
        library.addMedia(new Book("Animal Farm", "George Orwell", "Penguin", MediaGenres.FICTION, 128, library));
        library.addMedia(new Book("Go Set a Watchman", "Harper Lee", "HarperCollins", MediaGenres.FICTION, 129, library));
        library.addMedia(new Book("Tender Is the Night", "F. Scott Fitzgerald", "Scribner", MediaGenres.ROMANCE, 130, library));
        library.addMedia(new Book("The Silmarillion", "J.R.R. Tolkien", "Allen & Unwin", MediaGenres.FICTION, 131, library));
        library.addMedia(new Book("Franny and Zooey", "J.D. Salinger", "Little, Brown and Company", MediaGenres.FICTION, 132, library));

        library.addMedia(new Movie("Inception", "Christopher Nolan", 201, library, MediaGenres.THRILLER));
        library.addMedia(new Movie("The Godfather", "Francis Ford Coppola", 202, library, MediaGenres.ACTION));
        library.addMedia(new Movie("Titanic", "James Cameron", 203, library, MediaGenres.ROMANCE));
        library.addMedia(new Movie("The Shining", "Stanley Kubrick", 204, library, MediaGenres.HORROR));
        library.addMedia(new Movie("Forrest Gump", "Robert Zemeckis", 205, library, MediaGenres.COMEDY));
        library.addMedia(new Movie("Interstellar", "Christopher Nolan", 206, library, MediaGenres.FICTION));
        library.addMedia(new Movie("The Dark Knight", "Christopher Nolan", 207, library, MediaGenres.ACTION));
        library.addMedia(new Movie("Avatar", "James Cameron", 208, library, MediaGenres.THRILLER));
        library.addMedia(new Movie("Aliens", "James Cameron", 209, library, MediaGenres.ACTION));
        library.addMedia(new Movie("Apocalypse Now", "Francis Ford Coppola", 210, library, MediaGenres.THRILLER));
    }

    private static void addResources(Library library) {
        library.addResource(new StudyRoom("Study Room 1", library));
        library.addResource(new StudyRoom("Study Room 2", library));
        library.addResource(new StudyRoom("Study Room 3", library));
        library.addResource(new Computer("Computer 1", library));
        library.addResource(new Computer("Computer 2", library));
        library.addResource(new Computer("Computer 3", library));
    }

    // =======================
    // MAIN LOOP
    // =======================
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
        System.out.println("\n=== Welcome to the Library Kiosk ===");
        System.out.println("1. Log In");
        System.out.println("2. Register");
        System.out.print("Enter your choice: ");

        int option = InputValidation.getIntInput();
        user = (option == 1) ? LogIn.loginUser() : RegisterUser.registerUser();

        if (user != null) {
            System.out.println("Welcome, " + user.getUsername() + "!");
        }
    }

    // =======================
    // USER MENU
    // =======================
    private static void showUserMenu() {
        int choice = promptMenu(
                "Browse Media",
                "Borrow Media",
                "Return Media",
                "View Resources",
                "Book a Resource",
                "Log Out",
                "Find Path on Map"
        );

        switch (choice) {
            case 1 -> browseMedia();
            case 2 -> borrowMedia();
            case 3 -> returnMedia();
            case 4 -> viewResources();
            case 5 -> bookResource();
            case 6 -> logout();
            case 7 -> findPathOnMap();
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
        BrowseMedia.library = library;

        int choice = promptMenu(
                "Browse all media",
                "Browse all movies",
                "Browse all books",
                "View movies by a certain director",
                "View books by a certain author",
                "Search by title",
                "Go back"
        );

        switch (choice) {
            case 1 -> BrowseMedia.showAllMedia();
            case 2 -> BrowseMedia.showAllMovies();
            case 3 -> BrowseMedia.showAllBooks();
            case 4 -> {
                System.out.println("Enter director name: ");
                String director = InputValidation.getStringInput();
                BrowseMedia.printByDirector(director);
            }
            case 5 -> {
                System.out.println("Enter author name: ");
                String author = InputValidation.getStringInput();
                BrowseMedia.printByAuthor(author);
            }
            case 6 -> {
                System.out.println("Enter media title: ");
                String title = InputValidation.getStringInput();
                BrowseMedia.searchMedia(title);
            }
            case 7 -> { return; }
        }
    }

    // =======================
    // BORROW / RETURN
    // =======================
    private static void borrowMedia() {
        System.out.println("Enter the media ID to borrow: ");
        int mediaID = InputValidation.getIntInput();
        MediaInterface mediaFound = library.showMedia(mediaID);

        try {
            BorrowMedia.issueUser(mediaFound, user);
            System.out.println("Media borrowed successfully!");
        } catch (OverdueMediaException e) {
            System.out.println("Overdue items detected. Please resolve before borrowing new media.");
        } catch (UnavailableMediaException e) {
            System.out.println("No copies available. Join waitlist? [Y/N]");
            if (InputValidation.getStringInput().equalsIgnoreCase("Y")) {
                mediaFound.addWaitlist(user);
                System.out.println("Added to waitlist.");
            }
        }
    }

    private static void returnMedia() {
        System.out.println("Enter the media ID to return: ");
        int mediaID = InputValidation.getIntInput();
        MediaInterface mediaFound = library.showMedia(mediaID);
        mediaFound.returnMedia();
        System.out.println("Media returned successfully.");
    }

    // =======================
    // RESOURCES
    // =======================
    private static void viewResources() {
        PrintResource.printResources(library);
    }

    private static void bookResource() {
        System.out.println("Enter the resource name to book: ");
        String resourceName = InputValidation.getStringInput();
        Resource resource = library.getResource(resourceName);

        if (resource == null) {
            System.out.println("Resource not found.");
            return;
        }

        System.out.println("Available time slots:");
        TimeSlots[] slots = TimeSlots.values();
        for (int i = 0; i < slots.length; i++) {
            System.out.println((i + 1) + ". " + slots[i]);
        }

        System.out.println("Select a timeslot number: ");
        int slotChoice = InputValidation.getIntInput() - 1;
        if (slotChoice < 0 || slotChoice >= slots.length) {
            System.out.println("Invalid choice.");
            return;
        }

        TimeSlots timeSlot = slots[slotChoice];
        try {
            new BookResource(new Booking(resource, user, timeSlot));
            System.out.println("Resource booked successfully for " + timeSlot);
        } catch (BookingConflictException e) {
            System.out.println("Booking conflict: " + e.getMessage());
        }
    }

    // =======================
// MAP PATHFINDER OPTION
// =======================
    private static void findPathOnMap() {
        System.out.println("\n=== Library Path Finder ===");

        // Step 1: Create a PathFinder linked to the current library
        PathFinder pathFinder = new PathFinder(library);

        // Step 2: Clear any previous paths
        pathFinder.clearPath();

        // Step 3: Show destination legend
        System.out.println("Choose a destination symbol:");
        PrintMap.printLegend();
        System.out.print("Enter symbol (e.g., T): ");

        // Step 4: Get user input
        String input = InputValidation.getStringInput().trim().toUpperCase();
        if (input.isEmpty()) {
            System.out.println("❌ Input cannot be empty.");
            return;
        }

        char target = input.charAt(0);

        // Step 5: Run the pathfinder
        boolean found = pathFinder.runForTarget(target);

        // Step 6: Show results
        if (found) {
            System.out.println("✅ Path found!");
        } else {
            System.out.println("❌ No path found between Kiosk and destination.");
        }

        // Step 7: Print the updated map
        pathFinder.printMap();
    }

    // =======================
    // HELPER MENU
    // =======================
    private static int promptMenu(String... options) {
        System.out.println("\nPlease select an option:");
        for (int i = 0; i < options.length; i++) {
            System.out.println((i + 1) + ". " + options[i]);
        }
        return InputValidation.getIntInput();
    }

}
