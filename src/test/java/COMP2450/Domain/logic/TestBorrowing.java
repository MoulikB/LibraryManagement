package COMP2450.Domain.logic;

import COMP2450.domain.Library;
import COMP2450.Exceptions.OverdueMediaException;
import COMP2450.logic.Borrow.BorrowMedia;
import COMP2450.ColourOutput;
import COMP2450.TestResults;

public class TestBorrowing {

    private int successes;
    private int failures;

    public TestResults runTests() {
        testCanBorrow();
        testOverdueBlocked();
        return new TestResults(successes, failures);
    }

    private void testCanBorrow() {
        try {
            Library lib = new Library("Test");
            var user = TestObjects.sampleUser();
            var media = TestObjects.sampleBook(lib);

            media.addCopies();
            new BorrowMedia(user, media);

            pass("Borrow works when copies available");
        } catch (Exception e) {
            fail("Borrow crashed unexpectedly");
        }
    }

    private void testOverdueBlocked() {
        try {
            Library lib = new Library("Test");
            var user = TestObjects.sampleUser();
            var media = TestObjects.sampleBook(lib);

            media.addCopies();
            new BorrowMedia(user, media);

            media.setIssuedDay(10); // overdue

            try {
                new BorrowMedia(user, media);
                fail("Overdue did NOT throw exception");
            } catch (OverdueMediaException expected) {
                pass("Overdue borrowing correctly blocked");
            }

        } catch (Exception e) {
            fail("Crash inside overdue test");
        }
    }

    private void pass(String m) { successes++; ColourOutput.pass(m); }
    private void fail(String m) { failures++; ColourOutput.fail(m); }
}
