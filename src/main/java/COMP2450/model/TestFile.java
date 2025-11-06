package COMP2450.model;

import COMP2450.model.PrintLogic.PrintMap;

public class TestFile {
    public static void main(String[] args) {
        Library lib = new Library("as");
        PrintMap.printMap(lib.getMap());
        Stack<Coordinate> coordinateStack = new LinkedListStack<>();

        System.out.println(coordinateStack.isEmpty());
        System.out.println(coordinateStack.size());

        Coordinate coordinate = new Coordinate(2,3);
        coordinateStack.push(coordinate);
        Coordinate coordinate2 = new Coordinate(4,5);
        System.out.println(coordinateStack.isEmpty());
        System.out.println(coordinateStack.size());



        Coordinate output = (Coordinate) coordinateStack.pop();
        System.out.println("X = " + output.getX());
        System.out.println("Y = " + output.getY());


    }
}
