package COMP2450.model;

public class PrintMap {
    public static void printMap(Map inputMap) {
            for (char[] chars : inputMap.map) {
                for (char c : chars) {
                    System.out.print(c);
                }
                System.out.println();
            }
            System.out.println("\nLegend:");
            System.out.println("💻 : Computer Lab");
            System.out.println("🚪 : Entrance");
            System.out.println("H  : Horror Shelves");
            System.out.println("C  : Comedy Shelves");
            System.out.println("A  : Action Shelves");
            System.out.println("R  : Romance Shelves");
            System.out.println("T  : Thriller Shelves");
            System.out.println("F  : Fiction Shelves");
            System.out.println("N  : Non-fiction Shelves");
            System.out.println("🖨️ : Printing Lab");
            System.out.println("3️⃣ : 3D Printing Room");
            System.out.println("|  : Wall of private study spaces");
            System.out.println("D  : Door");
            System.out.println("~  : Boundary of Main Desk");
    }
}
