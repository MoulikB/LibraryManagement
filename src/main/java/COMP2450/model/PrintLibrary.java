package COMP2450.model;

import java.util.List;

public class PrintLibrary {
    public static void printLibrary(Library library) {

        System.out.println( "Library : " + library.getName());
        List<MediaInterface> mediaAvailable = library.getMediaAvailable();
        for (MediaInterface media : mediaAvailable) {
            if (media instanceof Movie) {
                PrintMedia.printMovie((Movie )media);
            } else {
                PrintMedia.printBook((Book)media);
            }
        }
    }

    public static void printLibraryList() {
        for (var Library : LibraryManagement.getLibraries()) {
            printLibrary(Library);
        }
    }

    public static void printMap(Library library) {
        PrintMap.printMap(library.getMap());
    }
}
