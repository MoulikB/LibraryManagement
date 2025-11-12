package COMP2450.logic.PrintLogic;

import COMP2450.domain.Library;
import COMP2450.logic.LibraryManagement;
import com.google.common.base.Preconditions;

/**
 * Utility class responsible for printing information about libraries and their contents.
 *
 **/
public class PrintLibrary {

    /**
     * Prints the details of a single library, including all available media items.
     *
     * @param library the Library object to print (has to be not null)
     */
    public static void printLibrary(Library library) {
        Preconditions.checkNotNull(library, "Library is null");
        library.checkInvariants();
        System.out.println( "Library : " + library.getName());

        PrintMedia.printAllMedia(library);

        Preconditions.checkNotNull(library, "Library is null");
        library.checkInvariants();
    }

    /**
     * Prints all libraries currently stored in the LibraryManagement system.
     **/
    public static void printLibraryList() {
        Preconditions.checkNotNull(LibraryManagement.getLibraries() , "List of Libraries is null");
        LibraryManagement.checkInvariants();
        for (var library : LibraryManagement.getLibraries()) {
            Preconditions.checkNotNull(library, "Library is null");
            library.checkInvariants();
            printLibrary(library);
            library.checkInvariants();
        }
        LibraryManagement.checkInvariants();
    }

    /**
     * Prints the map currently stored for the library.
     * @param library the library for which the map is being printed (cannot be null)
     **/
    public static void printMap(Library library) {
        Preconditions.checkNotNull(library, "Library is null");
        Preconditions.checkNotNull(library.getMap(), "Map is null");
        PrintMap.printMap(library.getMap());
    }
}
