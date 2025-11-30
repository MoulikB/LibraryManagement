package COMP2450.domain.Pathfinder;

import com.google.common.base.Preconditions;

/**
 * Coordinate
 * Represents a single (x, y) position on the library map.
 */
public class Coordinate {

    private final int x;
    private final int y;

    /**
     * Creates a new coordinate with the given x and y values.
     *
     * @param x the row index
     * @param y the column index
     */
    public Coordinate(int x, int y) {
        Preconditions.checkArgument(x >= 0 && y >= 0, "Coordinate x or y should be positive.");
        this.x = x;
        this.y = y;
    }

    /**
     * Returns the x value of this coordinate.
     *
     * @return the x position
     */
    public int getX() {
        return x;
    }

    /**
     * Returns the y value of this coordinate.
     *
     * @return the y position
     */
    public int getY() {
        return y;
    }

}
