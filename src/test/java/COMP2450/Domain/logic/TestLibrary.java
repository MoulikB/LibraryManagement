package COMP2450.Domain.logic;

import COMP2450.domain.Library;
import COMP2450.ColourOutput;
import COMP2450.TestResults;

public class TestLibrary {

    private int successes;
    private int failures;

    public TestResults runTests() {
        testLibraryCreation();
        testAddMedia();
        testAddResource();
        return new TestResults(successes, failures);
    }

    private void testLibraryCreation() {
        try {
            Library lib = new Library("Test");
            pass("Library created successfully");
        } catch (Exception e) {
            fail("Library creation threw an exception");
        }
    }

    private void testAddMedia() {
        try {
            Library lib = new Library("Test");
            lib.addMedia(TestObjects.sampleBook(lib));

            if (lib.getMediaAvailable().size() == 1)
                pass("addMedia() works");
            else
                fail("Media not added to list");
        } catch (Exception e) {
            fail("Crash in addMedia()");
        }
    }

    private void testAddResource() {
        try {
            Library lib = new Library("Test");
            TestObjects.sampleRoom(lib);

            if (lib.getResources().size() == 1)
                pass("addResource() works");
            else
                fail("Resource not added");
        } catch (Exception e) {
            fail("Crash in addResource()");
        }
    }

    private void pass(String m) { successes++; ColourOutput.pass(m); }
    private void fail(String m) { failures++; ColourOutput.fail(m); }
}
