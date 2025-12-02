package COMP2450.Stack;

import COMP2450.ColourOutput;
import COMP2450.TestResults;

public class StackTestHarness {
    private static int successes;
    private static int failures;

    public static void main(String[] args) {
        System.out.println("Test Harness");
        TestResults stackResults = new TestStack().runTests();
        successes += stackResults.successes();
        failures += stackResults.failures();

        System.out.println("\n=== FINAL RESULTS ===");
        System.out.println("Total tests: " + (successes + failures));
        ColourOutput.pass("Successes: " + successes);
        ColourOutput.fail("Failures: " + failures);

        if (failures == 0) {
            ColourOutput.pass("All tests passed!");
        }
        else {
            ColourOutput.fail("There were failures.");
        }
    }

}