package COMP2450.domain.Pathfinder;

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
        this.x = x;
        this.y = y;
    }

    /**
     * Returns the x value (row index) of this coordinate.
     *
     * @return the x position
     */
    public int getX() {
        return x;
    }

    /**
     * Returns the y value (column index) of this coordinate.
     *
     * @return the y position
     */
    public int getY() {
        return y;
    }

    /**
     * Checks if this coordinate is equal to another coordinate.
     * Two coordinates are equal if they have the same x and y values.
     *
     * @param other the coordinate to compare to
     * @return true if both coordinates have the same x and y values, false otherwise
     */
    public boolean equals(Coordinate other) {
        return this.x == other.x && this.y == other.y;
    }
}
