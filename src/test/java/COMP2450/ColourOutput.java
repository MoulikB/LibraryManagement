package COMP2450;

/**
 * Utility class for colored console output.
 * Red = failures
 * Green = successes
 */
public class ColourOutput {

    private static final String RESET  = "\u001B[0m";
    private static final String RED    = "\u001B[31m";
    private static final String GREEN  = "\u001B[32m";

    public static void pass(String message) {
        System.out.println(GREEN + "PASS: " + message + RESET);
    }

    public static void fail(String message) {
        System.out.println(RED + "FAIL: " + message + RESET);
    }
}
