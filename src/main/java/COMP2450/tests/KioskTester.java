package COMP2450.tests;

import COMP2450.domain.*;
import COMP2450.domain.Media.*;
import COMP2450.domain.Resources.*;
import COMP2450.logic.*;
import COMP2450.logic.Borrow.*;
import COMP2450.logic.PrintLogic.*;
import COMP2450.logic.UserManagement.*;
import COMP2450.Exceptions.*;


/**
 * KioskTester
 * Integration test class that validates both valid and invalid user paths.
 * Simulates full kiosk flows without requiring console input.
 */
public class KioskTester {

    public static void main(String[] args) {
        System.out.println("=== 🧪 Starting Library Kiosk System Integration Tests ===");
        LibraryBuilder.initializeLibrary();

        // Step 1: Initialize Library
        Library library = LibraryBuilder.initializeLibrary();
        System.out.println("✅ Library initialized successfully: " + library.getName());

        // Step 2: Test Registration — valid and invalid
        testRegistrationFlow();

        // Step 3: Test Login — valid and invalid credentials
        testLoginFlow();

        // Step 4: Test Borrowing and Returning Media
        testBorrowAndReturnMedia(library);

        // Step 5: Test Booking Resources — success and conflict
        testBookingFlow(library);

        // Step 6: Test PathFinder — valid and invalid symbols
        testPathFinder(library);

        System.out.println("\n=== ✅ All Kiosk Tests Completed ===");
    }

    // === REGISTRATION TESTS ===
    private static void testRegistrationFlow() {
        System.out.println("\n--- Testing Registration Flow ---");

        // Valid user registration
        User validUser = RegisterUser.createUser("tester", "password123", "tester@mail.com", 2049998888);
        System.out.println(validUser != null ? "✅ Valid registration passed." : "❌ Valid registration failed.");

        // Duplicate registration
        User duplicateUser = RegisterUser.createUser("tester", "password123", "tester@mail.com", 2049998888);
        System.out.println(duplicateUser == null ? "✅ Duplicate user blocked correctly." : "❌ Duplicate user allowed (BUG).");

        // Invalid input (missing username)
        User badUser = RegisterUser.createUser("", "1234", "nope@mail.com", 2049997777);
        System.out.println(badUser == null ? "✅ Invalid registration rejected." : "❌ Invalid registration allowed (BUG).");
    }

    // === LOGIN TESTS ===
    private static void testLoginFlow() {
        System.out.println("\n--- Testing Login Flow ---");

        // Valid login
        User validLogin = LogIn.authenticate("tester", "password123");
        System.out.println(validLogin != null ? "✅ Valid login successful." : "❌ Valid login failed.");

        // Wrong password
        User badLogin = LogIn.authenticate("tester", "wrongpass");
        System.out.println(badLogin == null ? "✅ Invalid password rejected." : "❌ Wrong password accepted (BUG).");

        // Nonexistent user
        User ghostLogin = LogIn.authenticate("ghost", "1234");
        System.out.println(ghostLogin == null ? "✅ Nonexistent user blocked." : "❌ Nonexistent user logged in (BUG).");
    }

    // === MEDIA BORROW / RETURN TESTS ===
    private static void testBorrowAndReturnMedia(Library library) {
        System.out.println("\n--- Testing Media Borrow and Return ---");
        User user = LogIn.authenticate("tester", "password123");
        if (user == null) {
            System.out.println("❌ Cannot proceed — user not logged in.");
            return;
        }

        MediaInterface media = library.showMedia(123);
        try {
            BorrowMedia.issueUser(media, user);
            System.out.println("✅ Media borrowed successfully.");
        } catch (UnavailableMediaException | OverdueMediaException e) {
            System.out.println("⚠️ Borrowing failed: " + e.getMessage());
        }

        // Try borrowing the same media again — should fail if unavailable
        try {
            BorrowMedia.issueUser(media, user);
            System.out.println("❌ Media reissued without availability (BUG).");
        } catch (UnavailableMediaException e) {
            System.out.println("✅ UnavailableMediaException correctly thrown.");
        } catch (Exception e) {
            System.out.println("⚠️ Unexpected exception: " + e);
        }

        // Return the media
        media.returnMedia();
        System.out.println("✅ Media returned successfully.");
    }

    // === RESOURCE BOOKING TESTS ===
    private static void testBookingFlow(Library library) {
        System.out.println("\n--- Testing Resource Booking ---");
        User user = LogIn.authenticate("tester", "password123");
        Resource resource = library.getResource("Computer 1");

        if (resource == null) {
            System.out.println("❌ Could not find 'Computer1' to test booking.");
            return;
        }

        // Successful booking
        try {
            Booking booking1 = new Booking(resource, user, TimeSlots.ONE_TO_TWO);
            new BookResource(booking1);
            System.out.println("✅ Resource booked successfully (MORNING).");
        } catch (BookingConflictException e) {
            System.out.println("⚠️ Booking conflict on first booking: " + e.getMessage());
        }

        // Conflicting booking (same time/resource)
        try {
            Booking conflict = new Booking(resource, user, TimeSlots.ONE_TO_TWO);
            new BookResource(conflict);
            System.out.println("❌ Conflict booking allowed (BUG).");
        } catch (BookingConflictException e) {
            System.out.println("✅ Conflict correctly detected.");
        }

        // Booking another slot (should succeed)
        try {
            Booking booking2 = new Booking(resource, user, TimeSlots.valueOf("14:00-15:00"));
            new BookResource(booking2);
            System.out.println("✅ Different timeslot booking succeeded.");
        } catch (Exception e) {
            System.out.println("⚠️ Unexpected booking error: " + e.getMessage());
        }
    }

    // === MAP / PATHFINDER TESTS ===
    private static void testPathFinder(Library library) {
        System.out.println("\n--- Testing PathFinder ---");
        PathFinder pathFinder = new PathFinder(library);

        // Valid path
        try {
            pathFinder.runForTarget('T'); // T = target shelf
            System.out.println("✅ Valid target path found.");
        }  catch (IllegalArgumentException e) {
            System.out.println("❌ Valid path not found (BUG).");
    }


        // Invalid target
        try {
            pathFinder.runForTarget(']'); // T = target shelf
            System.out.println("❌ Invalid target unexpectedly succeeded (BUG).");
        }  catch (IllegalArgumentException e) {
            System.out.println("✅ Invalid target handled gracefully.");
        }

        System.out.println("\n🗺️ Final Map State:");
        PrintMap.printMap(pathFinder.getMap());
    }
}
