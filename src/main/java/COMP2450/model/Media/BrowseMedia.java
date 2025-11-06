package COMP2450.model.Media;

import COMP2450.model.Library;
import COMP2450.model.PrintLogic.PrintMedia;

public class BrowseMedia {
    public static Library library;

    public BrowseMedia(Library library) {
        this.library = library;
    }

    public static void showAllMedia() {
        System.out.println("Here is all the media availbale at this library : ");
        PrintMedia.printAllMedia(library);
    }

    public static void showAllMovies() {
        System.out.println("Here is all the movies available at this library : ");
        PrintMedia.printAllMovies(library);
    }

    public static void showAllBooks() {
        System.out.println("Here is all the books available at this library : ");
        PrintMedia.printAllBooks(library);
    }

    public static void printByDirector(String director) {
        System.out.printf("Here is all the movies by %s available at this library : \n" , director);
        PrintMedia.printByDirector(library, director);

    }

    public static void printByAuthor(String author) {
        System.out.printf("Here is all the books by %s available at this library : \n" , author);
        PrintMedia.printByAuthor(library, author);
    }

    public static void searchMedia(String toSearch) {
        for (var media : library.getMediaAvailable()) {
            if (media.getTitle().toLowerCase().contains(toSearch.toLowerCase())) {
                PrintMedia.printMedia(media);
            }
        }
    }
}
