package COMP2450.logic;

import COMP2450.domain.Library;
import COMP2450.UI.PrintLogic.PrintMedia;
import com.google.common.base.Preconditions;

/**
 * BrowseMedia
 * Handles viewing and searching through all media available in the library.
 * Works together with PrintMedia to display formatted output.
 */
public class BrowseMedia {

    public BrowseMedia(Library library) {
        Preconditions.checkNotNull(library, "Library cannot be null");
        this.library = library;
    }

    /** Shared reference to the active library instance. */
    private Library library;

    /**
     * Displays all media items available in the current library.
     */
    public void showAllMedia() {
        System.out.println("Here is all the media available at this library:");
        PrintMedia.printAllMedia(library);
    }

    /**
     * Displays all movies available in the current library.
     */
    public void showAllMovies() {
        System.out.println("Here is all the movies available at this library:");
        PrintMedia.printAllMovies(library);
    }

    /**
     * Displays all books available in the current library.
     */
    public void showAllBooks() {
        Preconditions.checkNotNull(library, "Library cannot be null");
        System.out.println("Here is all the books available at this library:");
        PrintMedia.printAllBooks(library);
    }

    /**
     * Displays all movies by a given director.
     *
     * @param director the director name to search for
     */
    public void printByDirector(String director) {
        Preconditions.checkNotNull(director, "Director cannot be null");
        System.out.printf("Here is all the movies by %s available at this library:%n", director);
        PrintMedia.printByDirector(library, director);
    }

    /**
     * Displays all books by a given author.
     *
     * @param author the author name to search for
     */
    public void printByAuthor(String author) {
        Preconditions.checkNotNull(author, "Author cannot be null");
        System.out.printf("Here is all the books by %s available at this library:%n", author);
        PrintMedia.printByAuthor(library, author);
    }

    /**
     * Searches media titles that match a keyword and prints the results.
     *
     * @param toSearch the keyword or title fragment to search for
     */
    public void searchMedia(String toSearch) {
        Preconditions.checkNotNull(toSearch, "ToSearch cannot be null");
        for (var media : library.getMediaAvailable()) {
            if (media.getTitle().toLowerCase().contains(toSearch.toLowerCase())) {
                PrintMedia.printMedia(media);
            }
        }
    }

}
