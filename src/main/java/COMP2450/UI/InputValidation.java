package COMP2450.UI;

import java.util.Scanner;

/**
 * Utility class for safely receiving validated user input from the console.
 * This class provides methods to read strings and integers using a shared Scanner instance.
 * It ensures that integer inputs are validated before being returned.
 */
public class InputValidation {


    private final Scanner scnr;

    public InputValidation(Scanner scnr) {
        this.scnr = scnr;
    }

    /**
     * Reads a full line of user input as a String.
     *
     * @return the line of text entered by the user
     */
    public String getStringInput() {
        String input;
        input = scnr.nextLine();
        while (input.isEmpty()) {
            System.out.println("Please enter a valid non empty string : ");
            input = scnr.nextLine();
        }
        return input;
    }

    /**
     * Continuously prompts the user until a valid integer is entered.
     *
     * @return the integer entered by the user
     */
    public int getIntInput() {
        while (true) {
            String s = scnr.nextLine().trim();
            try {
                int i = Integer.parseInt(s);
                if (i > 0) {
                    return Integer.parseInt(s);
                }
                else  {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException e) {
                System.out.print("Enter a valid integer: ");
            }
        }
    }
}
