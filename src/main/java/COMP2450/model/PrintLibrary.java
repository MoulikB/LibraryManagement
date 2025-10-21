package COMP2450.model;

public class PrintLibrary {
    public static void printLibrary(Library library) {

        System.out.print( "Library : " + library.getName());
        library.getMediaAvailable();
    }

    public static void printMap(Library library) {
        library.getMap().printMap();
    }
}
