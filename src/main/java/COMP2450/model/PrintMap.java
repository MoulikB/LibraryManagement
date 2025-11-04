package COMP2450.model;

import com.google.common.base.Preconditions;

/**
 * Utility class responsible for printing map and its legend
 *
 **/
public class PrintMap {
    /**
     * Method responsible for printing map and its legends.
     * @param inputMap the map being provided (cannot be null)
     *
     **/
    public static void printMap(Map inputMap) {
        Preconditions.checkNotNull(inputMap);
        for (char[] chars : inputMap.getMap()) {
            for (char c : chars) {
                System.out.print(c);
            }
            System.out.println();
        }
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
        System.out.println("|  : Wall of private study spaces");
        System.out.println("D  : Door");
        System.out.println("~  : Boundary of Main Desk");
        Preconditions.checkNotNull(inputMap);
    }
}

