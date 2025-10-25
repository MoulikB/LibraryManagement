package COMP2450.model;

import java.util.Scanner;

/**
 * Utility class for safely receiving validated user input from the console.
 * This class provides methods to read strings and integers using a shared Scanner instance.
 * It ensures that integer inputs are validated before being returned.
 */
 public class InputValidation {


    final static Scanner scnr = new Scanner(System.in);

    /**
     * Reads a full line of user input as a String.
     *
     * @return the line of text entered by the user
     */
    public static String getStringInput() {
            return scnr.nextLine();
    }

    /**
     * Continuously prompts the user until a valid integer is entered.
     *
     * @return the integer entered by the user
     */
    public static int getIntInput() {
        while (true) {
            String s = scnr.nextLine().trim();
            try {
                return Integer.parseInt(s);
            } catch (NumberFormatException e) {
                System.out.print("Enter a valid integer: ");
            }
        }
    }
}
