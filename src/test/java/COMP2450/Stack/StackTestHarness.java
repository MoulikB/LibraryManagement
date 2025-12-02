package COMP2450.Stack;

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
        System.out.println("Successes: " + successes);
        System.out.println("Failures: " + failures);

        if (failures == 0) {
            System.out.println("All tests passed!");
        }
        else {
            System.out.println("There were failures.");
        }
    }

}