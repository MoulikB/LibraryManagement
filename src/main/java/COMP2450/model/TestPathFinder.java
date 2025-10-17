package COMP2450.model;

public class TestPathFinder {
    public static void main(String[] args) {
        Library lib = new Library("M"); // your class
        Map map = new Map(lib);

        Position start = new Position(1, 50); // entrance
        Position goal = new Position(19, 35); // main desk

        PathFinder.findPath(map, start, goal);
    }
}