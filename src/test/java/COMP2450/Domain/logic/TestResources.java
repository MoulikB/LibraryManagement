package COMP2450.Domain.logic;

import COMP2450.domain.*;
import COMP2450.Exceptions.BookingConflictException;
import COMP2450.logic.BookResource;
import COMP2450.ColourOutput;
import COMP2450.TestResults;

public class TestResources {

    private int successes;
    private int failures;

    public TestResults runTests() {
        testSingleBooking();
        testConflict();
        return new TestResults(successes, failures);
    }

    private void testSingleBooking() {
        try {
            Library lib = new Library("Test");
            var room = TestObjects.sampleRoom(lib);
            var user = TestObjects.sampleUser();

            new BookResource(new Booking(room, user, TimeSlots.NINE_TO_TEN));
            pass("Single booking OK");

        } catch (Exception e) {
            fail("Crash booking a room");
        }
    }

    private void testConflict() {
        try {
            Library lib = new Library("Test");
            var room = TestObjects.sampleRoom(lib);
            var u1 = TestObjects.sampleUser();
            var u2 = TestObjects.sampleUser();

            new BookResource(new Booking(room, u1, TimeSlots.TEN_TO_ELEVEN));

            try {
                new BookResource(new Booking(room, u2, TimeSlots.TEN_TO_ELEVEN));
                fail("Conflict NOT detected");
            } catch (BookingConflictException expected) {
                pass("Conflict correctly detected");
            }

        } catch (Exception e) {
            fail("Crash in conflict test");
        }
    }

    private void pass(String m) { successes++; ColourOutput.pass(m); }
    private void fail(String m) { failures++; ColourOutput.fail(m); }
}
