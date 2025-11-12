package COMP2450.domain;


import com.google.common.base.Preconditions;

/**
 * Map
 * An ASCII grid map that represents the physical layout of a library,
 * including spaces, categories, and walkable areas.
 */

public class Map {
    private final char[][] map;
    public Library library;

    /**
     Constructor : Creates map, we are using a default map
     @param library The library this map belongs to
     */
    public Map(Library library) {
        Preconditions.checkNotNull(library, "library cannot be null");
        this.library = library;
        map = new char[][]{
                "+---------------------------------------+".toCharArray(),
                "| L                                    D|".toCharArray(),
                "| L                                     |".toCharArray(),
                "| H             C            A          |".toCharArray(),
                "| R             T            F          |".toCharArray(),
                "| N             P            3          |".toCharArray(),
                "|                                       |".toCharArray(),
                "|                                       |".toCharArray(),
                "|                                       |".toCharArray(),
                "|               K                       |".toCharArray(),  // K on open floor
                "|                                       |".toCharArray(),
                "|                  |~~~~~~~|            |".toCharArray(),
                "|                  | Desk  |            |".toCharArray(),
                "|                  |_______|            |".toCharArray(),
                "+---------------------------------------+".toCharArray()
        };
    }

    public char[][] getMap() {
        return map;
    }

}