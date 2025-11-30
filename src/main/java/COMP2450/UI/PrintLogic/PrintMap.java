package COMP2450.UI.PrintLogic;

import com.google.common.base.Preconditions;

/**
 * Utility class responsible for printing map and its legend
 *
 **/
public class PrintMap {


    public static void printLegend() {
        System.out.println("\nLegend:");
        System.out.println("L : Computer Lab");
        System.out.println("D : Entrance");
        System.out.println("H  : Horror Shelves");
        System.out.println("C  : Comedy Shelves");
        System.out.println("A  : Action Shelves");
        System.out.println("R  : Romance Shelves");
        System.out.println("T  : Thriller Shelves");
        System.out.println("F  : Fiction Shelves");
        System.out.println("N  : Non-fiction Shelves");
        System.out.println("P️  : Printing Lab");
        System.out.println("3  : 3D Printing Room");
    }

    /**
     * Method responsible for printing a 2D map and its legends.
     * @param inputMap the map being provided (cannot be null)
     *
     **/
    public static void printMap(char[][] inputMap) {
        Preconditions.checkNotNull(inputMap);
        for (char[] chars : inputMap) {
            for (char c : chars) {
                System.out.print(c);
            }
            System.out.println();
        }

        printLegend();

        Preconditions.checkNotNull(inputMap);
    }
}
