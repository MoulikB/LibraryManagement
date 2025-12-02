package COMP2450.Domain;

import COMP2450.Domain.logic.*;
import COMP2450.TestResults;

import static COMP2450.ColourOutput.fail;
import static COMP2450.ColourOutput.pass;

public class DomainTestHarness {

    public static void main(String[] args) {

        int successes = 0;
        int failures = 0;

        TestResults[] suites = {
                new TestLibrary().runTests(),
                new TestMedia().runTests(),
                new TestBorrowing().runTests(),
                new TestResources().runTests(),
                new TestWaitlist().runTests()
        };

        for (TestResults t : suites) {
            successes += t.successes();
            failures += t.failures();
        }

        System.out.println("\n======= FINAL RESULTS =======");
        System.out.printf("Total tests: %d\n", successes + failures);
        pass(String.format("Successes:   %d\n", successes));
        fail(String.format("Failures:    %d\n", failures));

        if (failures > 0)
            fail("❌ Some tests failed.");
        else
            pass("🎉 All tests passed!");
    }
}
