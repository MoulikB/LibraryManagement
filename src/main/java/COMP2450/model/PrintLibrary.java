package COMP2450.model;

import java.util.List;

public class PrintLibrary {
    public static void printLibrary(Library library) {

        System.out.print( "Library : " + library.getName());
        List<MediaInterface> mediaAvailable = library.getMediaAvailable();
        for (MediaInterface media : mediaAvailable) {
            System.out.print(media);
        }
    }

    public static void printMap(Library library) {
        PrintMap.printMap(library.getMap());
    }
}
