package COMP2450.Domain.logic;

import COMP2450.domain.Library;
import COMP2450.ColourOutput;
import COMP2450.TestResults;

public class TestWaitlist {

    private int successes;
    private int failures;

    public TestResults runTests() {
        testWaitlistAdd();
        return new TestResults(successes, failures);
    }

    private void testWaitlistAdd() {
        try {
            Library lib = new Library("Test");
            var user = TestObjects.sampleUser();
            var media = TestObjects.sampleBook(lib);

            media.addWaitlist(user);

            if (media.getWaitlist().contains(user))
                pass("Waitlist add OK");
            else
                fail("User not added to waitlist");

        } catch (Exception e) {
            fail("Crash in waitlist test");
        }
    }

    private void pass(String m) { successes++; ColourOutput.pass(m); }
    private void fail(String m) { failures++; ColourOutput.fail(m); }
}
