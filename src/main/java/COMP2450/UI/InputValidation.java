package COMP2450.UI;

import com.google.common.base.Preconditions;

import java.util.Scanner;

/**
 * Utility class for safely receiving validated user input from the console.
 * This class provides methods to read strings and integers using a Scanner instance.
 * It ensures that integer inputs are validated before being returned.
 */
public class InputValidation {


    private final Scanner scnr;

    public InputValidation(Scanner scnr) {
        Preconditions.checkNotNull(scnr);
        this.scnr = scnr;
        Preconditions.checkNotNull(this.scnr);
    }

    /**
     * Reads a full line of user input as a String.
     *
     * @return the line of text entered by the user
     */
    public String getStringInput() {
        Preconditions.checkNotNull(scnr);
        String input;
        input = scnr.nextLine();
        while (input.isEmpty()) {
            System.out.println("Please enter a valid non empty and not null string : ");
            input = scnr.nextLine();
        }
        Preconditions.checkNotNull(scnr);
        return input;
    }

    /**
     * Continuously prompts the user until a valid integer is entered.
     *
     * @return the integer entered by the user
     */
    public int getIntInput() {
        Preconditions.checkNotNull(scnr);
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
                System.out.print("Enter a valid integer greater than 0: ");
            }
        }

    }
}
