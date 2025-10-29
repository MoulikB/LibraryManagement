package COMP2450.model;

public class Position {
    public int row;
    public int column;

    /**
     * Constructor: Creates a new position instance. It holds various info about the
     * book
     *
     * @param row The row (X value) of the point
     * @param column The column (y value) of the point
     *
     */
    public Position(int row, int column) {
        this.row = row;
        this.column = column;
    }

    /**
     * Checks whether two positions are equal
     * @param other another positiong
     * @return a boolean confirming if 2 positions is equal
     */
    public boolean equals(Position other) {
        return this.row == other.row && this.column == other.column;
    }
}