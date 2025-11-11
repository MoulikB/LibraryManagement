package COMP2450.model.Media;

import COMP2450.model.Library;
import COMP2450.model.PrintLogic.PrintMedia;

/**
 * BrowseMedia
 * Handles viewing and searching through all media available in the library.
 * Works together with PrintMedia to display formatted output.
 */
public class BrowseMedia {

    /** Shared reference to the active library instance. */
    public static Library library;

    /**
     * Creates a new BrowseMedia helper for a given library.
     *
     * @param library the library to browse
     */
    public BrowseMedia(Library library) {
        BrowseMedia.library = library;
    }

    /**
     * Displays all media items available in the current library.
     */
    public static void showAllMedia() {
        System.out.println("Here is all the media available at this library:");
        PrintMedia.printAllMedia(library);
    }

    /**
     * Displays all movies available in the current library.
     */
    public static void showAllMovies() {
        System.out.println("Here is all the movies available at this library:");
        PrintMedia.printAllMovies(library);
    }

    /**
     * Displays all books available in the current library.
     */
    public static void showAllBooks() {
        System.out.println("Here is all the books available at this library:");
        PrintMedia.printAllBooks(library);
    }

    /**
     * Displays all movies by a given director.
     *
     * @param director the director name to search for
     */
    public static void printByDirector(String director) {
        System.out.printf("Here is all the movies by %s available at this library:%n", director);
        PrintMedia.printByDirector(library, director);
    }

    /**
     * Displays all books by a given author.
     *
     * @param author the author name to search for
     */
    public static void printByAuthor(String author) {
        System.out.printf("Here is all the books by %s available at this library:%n", author);
        PrintMedia.printByAuthor(library, author);
    }

    /**
     * Searches media titles that match a keyword and prints the results.
     *
     * @param toSearch the keyword or title fragment to search for
     */
    public static void searchMedia(String toSearch) {
        for (var media : library.getMediaAvailable()) {
            if (media.getTitle().toLowerCase().contains(toSearch.toLowerCase())) {
                PrintMedia.printMedia(media);
            }
        }
    }
}
