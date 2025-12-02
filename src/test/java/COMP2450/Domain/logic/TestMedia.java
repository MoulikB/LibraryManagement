package COMP2450.Domain.logic;

import COMP2450.domain.Library;
import COMP2450.domain.Media.MediaInterface;
import COMP2450.ColourOutput;
import COMP2450.TestResults;

public class TestMedia {

    private int successes;
    private int failures;

    public TestResults runTests() {
        testBookCreation();
        testBorrowReturn();
        return new TestResults(successes, failures);
    }

    private void testBookCreation() {
        try {
            Library lib = new Library("Test");
            TestObjects.sampleBook(lib);
            pass("Book constructor OK");
        } catch (Exception e) {
            fail("Book constructor crashed");
        }
    }

    private void testBorrowReturn() {
        try {
            Library lib = new Library("Test");
            MediaInterface media = TestObjects.sampleBook(lib);

            media.addCopies();
            int before = media.getAvailableCopies();

            media.borrowMedia(TestObjects.sampleUser());

            if (media.getAvailableCopies() == before - 1)
                pass("borrowMedia() works");
            else
                fail("borrowMedia() incorrect");

            media.returnMedia();
            pass("returnMedia() works");

        } catch (Exception e) {
            fail("Crash in borrow/return test");
        }
    }

    private void pass(String m) { successes++; ColourOutput.pass(m); }
    private void fail(String m) { failures++; ColourOutput.fail(m); }
}
