package COMP2450.model;

public class TestPathFinder {
    public static void main(String[] args) {
        Library lib = new Library("M"); // your class
        Map map = new Map(lib);

        Position start = new Position(1, 27); // entrance
        Position goal = new Position(19, 35); // main desk

        PathFinder.solveMap(map, start, goal);
    }
}