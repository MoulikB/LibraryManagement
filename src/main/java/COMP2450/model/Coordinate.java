package COMP2450.model;

public class Coordinate {
    private final int x;
    private final int y;

    public Coordinate(int x, int y) { this.x = x; this.y = y; }

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }

    public boolean equals(Coordinate other) {
        return this.x == other.x && this.y == other.y;
    }
}
