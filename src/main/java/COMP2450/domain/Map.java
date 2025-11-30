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
     Constructor : Creates map
     */
    public Map(char[][] map) {
        Preconditions.checkNotNull(map);
        Preconditions.checkArgument(map.length > 0 && map[0].length > 0, "Map cannot be empty");
        this.map = map;
    }

    /**
     * Returns a defensive copy of the map so callers
     * cannot mutate internal state.
     */
    public char[][] getMap() {
        Preconditions.checkNotNull(map, "Map cannot be null");
        Preconditions.checkArgument(map.length>0, "Map has not been initialized");
        char[][] copy = new char[map.length][];
        for (int i = 0; i < map.length; i++) {
            copy[i] = map[i].clone();
        }
        return copy;
    }

}