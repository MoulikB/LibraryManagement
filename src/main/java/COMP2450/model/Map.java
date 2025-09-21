package COMP2450.model;


public class Map {
    private char[][] map;
    public Library library;

    public Map(Library library) {
        this.library = library;
        map = new char[][]{
                "+--------------------------------------------------------+".toCharArray(),
                "| 💻💻💻💻💻💻💻💻💻💻                                🚪   |".toCharArray(),
                "| 💻💻💻💻💻💻💻💻💻💻                                    |".toCharArray(),
                "| HH HH HH             CC CC            AA AA AA         |".toCharArray(),
                "| HH HH HH             CC CC            AA AA AA         |".toCharArray(),
                "| RR RR RR             TT TT            FF FF  FF        |".toCharArray(),
                "| RR RR RR             TT TT            FF FF  FF        |".toCharArray(),
                "| NN NN NN             🖨️🖨️             3️⃣ 3️⃣ 3️⃣         |".toCharArray(),
                "| NN NN NN             🖨️🖨️             3️⃣ 3️⃣ 3️⃣         |".toCharArray(),
                "|                                                        |".toCharArray(),
                "| |--------D---------|  |-------D--------|  |------D-------| |".toCharArray(),
                "| |                |  |               |  |             | |".toCharArray(),
                "| |  Private Study |  | Private Study |  | Private Study| |".toCharArray(),
                "| |     Space 1    |  |    Space 2    |  |    Space 3  | |".toCharArray(),
                "| |                |  |               |  |             | |".toCharArray(),
                "| |----------------|  |---------------|  |-------------| |".toCharArray(),
                "|                                                        |".toCharArray(),
                "|                                   |~~~~~~~~~~~~~~~|    |".toCharArray(),
                "|                                   |    Main Desk  |    |".toCharArray(),
                "|                                   |     (Help)    |    |".toCharArray(),
                "|                                   |~~~~~~~~~~~~~~~|    |".toCharArray(),
                "+--------------------------------------------------------+".toCharArray()
        };
    }

    public void printMap() {
        for (char[] chars : map) {
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
