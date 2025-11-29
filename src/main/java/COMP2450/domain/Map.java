package COMP2450.domain;


import com.google.common.base.Preconditions;

/**
 * Map
 * An ASCII grid map that represents the physical layout of a library,
 * including spaces, categories, and walkable areas.
 */

public class Map {
    private final char[][] map;

    /**
     Constructor : Creates map, we are using a default map

     */
    public Map() {
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

    /**
     * Returns a defensive copy of the map so callers
     * cannot mutate internal state.
     */
    public char[][] getMap() {
        char[][] copy = new char[map.length][];
        for (int i = 0; i < map.length; i++) {
            copy[i] = map[i].clone();
        }
        return copy;
    }

}