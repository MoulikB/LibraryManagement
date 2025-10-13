package COMP2450.model;

import java.util.Scanner;

public class InputValidation {

    final static Scanner scnr = new Scanner(System.in);

    public static String getStringInput() {
        return scnr.nextLine();
    }

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
