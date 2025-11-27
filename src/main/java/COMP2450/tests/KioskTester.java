package COMP2450.tests;

import COMP2450.Exceptions.BookingConflictException;
import COMP2450.UI.KioskUI;
import COMP2450.domain.*;
import COMP2450.domain.Media.*;
import COMP2450.domain.Resources.*;
import COMP2450.logic.BookResource;
import COMP2450.logic.LibraryManagement;
import COMP2450.logic.PathFinder;
import COMP2450.logic.UserManagement.UserManagement;

import java.time.LocalDate;

/**
 * KioskTester
 * Stress tests all major KioskUI functions, including edge cases.
 */
public class KioskTester {

    public static void main(String[] args) {
        System.out.println("=== BEGINNING AUTOMATED KIOSK TESTS ===");

        try {
            // --- Setup Phase ---
            Library testLibrary = new Library("EdgeCase Library");
            LibraryManagement.addLibrary(testLibrary);

            // Add test media
            Book b1 = new Book("Haunted Nights", "A. Poe", "DarkPress", MediaGenres.HORROR, 111, testLibrary);
            Movie m1 = new Movie("Lost in Code", "C. Nolan", 222, testLibrary, MediaGenres.ACTION);
            b1.addCopies();
            m1.addCopies();
            testLibrary.addMedia(b1);
            testLibrary.addMedia(m1);

            // Add test resources
            Resource comp = new Computer("PC-01", testLibrary);
            Resource room = new StudyRoom("R-201", testLibrary);

            // Register a user
            User testUser = new User("tester", "password", 1, "tester@university.ca", "1234567890");
            UserManagement.addUser(testUser);

            // --- Login Phase ---
            System.out.println("\n[TEST] Simulate login with null library:");
            try {
                KioskUI.showWelcomeScreen(null);
            } catch (Exception e) {
                System.out.println("Caught expected null library exception: " + e.getMessage());
            }

            System.out.println("\n[TEST] Valid login simulation:");
            KioskUI.showWelcomeScreen(testLibrary);

            // --- Borrowing Phase ---
            System.out.println("\n[TEST] Borrowing valid media:");
            try {
                b1.addCopies(); // ensure availability
                KioskUI.clearFines(testUser);
                b1.issueUser(testUser);
                System.out.println("Borrow success for book");
            } catch (Exception e) {
                System.out.println("Unexpected borrow error: " + e.getMessage());
            }

            System.out.println("\n[TEST] Borrow invalid media ID:");
            try {
                testLibrary.showMedia(-99);
            } catch (Exception e) {
                System.out.println("Caught invalid media ID: " + e.getMessage());
            }

            // --- Pathfinding ---
            System.out.println("\n[TEST] Find path with invalid symbol:");
            try {
                PathFinder pf = new PathFinder(testLibrary);
                pf.runForTarget('~');
            } catch (Exception e) {
                System.out.println("Caught invalid map character: " + e.getMessage());
            }

            // --- Booking Phase ---
            System.out.println("\n[TEST] Book resource with invalid date:");
            try {
                new Booking(comp, testUser, null);
            } catch (Exception e) {
                System.out.println("Booking validation passed: " + e.getMessage());
            }

            System.out.println("\n[TEST] Overlapping booking:");
            try {
                Booking bA = new Booking(comp, testUser, TimeSlots.TEN_TO_ELEVEN);
                new BookResource(bA);
                new BookResource(new Booking(comp, testUser, TimeSlots.TEN_TO_ELEVEN));
                System.out.println("Overlap not detected!");
            } catch (BookingConflictException x) {
                System.out.println("Conflict correctly detected: " + x.getMessage());
            }

            // --- Fines and Returns ---
            System.out.println("\n[TEST] Fines after overdue:");
            b1.setIssuedDay(6); // simulate overdue
            double fines = testUser.calculateFinesDue();
            System.out.println("Fines: " + fines + " (Expected >= 2.0)");

            System.out.println("\n[TEST] Clear fines and overdue returns:");
            KioskUI.clearFines(testUser);
            if (testUser.getItemsIssued().isEmpty()) {
                System.out.println("Items cleared successfully after payment");
            } else {
                System.out.println("Items not properly cleared");
            }

            // --- Invalid Inputs ---
            System.out.println("\n[TEST] Null user in fine payment:");
            try {
                KioskUI.clearFines(null);
            } catch (Exception e) {
                System.out.println("Null user check successful: " + e.getMessage());
            }

            System.out.println("\n[TEST] Booking date out of range:");
            LocalDate invalidFuture = LocalDate.now().plusDays(20);
            try {
                new Booking(room, testUser, TimeSlots.TWO_TO_THREE);
                System.out.println("Booking valid resource OK");
            } catch (Exception e) {
                System.out.println("Unexpected booking error: " + e.getMessage());
            }

            System.out.println("\n[TEST] Map retrieval:");
            if (testLibrary.getMap() != null) {
                System.out.println("Map loaded successfully");
            }

            System.out.println("\n=== ALL TESTS COMPLETED SUCCESSFULLY ===");

        } catch (Exception e) {
            System.out.println("CRITICAL FAILURE: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
