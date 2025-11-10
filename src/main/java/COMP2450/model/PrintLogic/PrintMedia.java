package COMP2450.model.PrintLogic;

import COMP2450.model.Media.Book;
import COMP2450.model.Library;
import COMP2450.model.Media.MediaInterface;
import COMP2450.model.Media.Movie;
import com.google.common.base.Preconditions;

import java.util.List;

/**
 * Utility class responsible for printing information about various medias
 *
 **/
public class PrintMedia {

    /**
     *
     * Print a simple text summary of the movie
     */
    public static void printMovie(Movie movie) {
        Preconditions.checkNotNull(movie);
        System.out.println( "Movie --------------- \nTitle : " + movie.getTitle() + "\nDirector : " + movie.getCreator() +
                "\nMediaID : " + movie.getMediaID() +" \nGenre : " + movie.getMediaGenre() + "\n ---------------");
    }

    /**
     * * Print a text summary of the book.
     */
    public static void printBook(Book book) {
        Preconditions.checkNotNull(book);
        System.out.println( "Book --------------- \nTitle : " + book.getTitle() + "\nAuthor : " + book.getCreator() +
                "\nISBN : " + book.getMediaID() +" \nGenre : " + book.getMediaGenre() + "\n ---------------");
    }

    public static void printMedia(MediaInterface media) {
        Preconditions.checkNotNull(media);
        if (media instanceof Book ) {
            printBook((Book) media);
        } else if (media instanceof Movie ) {
            printMovie((Movie) media);
        }
    }

    public static void printAllMedia(Library library) {
        Preconditions.checkNotNull(library);
        List<MediaInterface> mediaAvailable = library.getMediaAvailable();
        for (MediaInterface media : mediaAvailable) {
            printMedia(media);
        }
    }

    public static void printAllMovies(Library library) {
        Preconditions.checkNotNull(library);
        List<MediaInterface> mediaAvailable = library.getMediaAvailable();
        for (MediaInterface media : mediaAvailable) {
            if (media instanceof Movie) {
                PrintMedia.printMedia(media);
            }
        }
    }
    public static void printAllBooks(Library library) {
        Preconditions.checkNotNull(library);
        List<MediaInterface> mediaAvailable = library.getMediaAvailable();
        for (MediaInterface media : mediaAvailable) {
            if (media instanceof Book) {
                PrintMedia.printMedia(media);
            }
        }
    }

    public static void printByDirector(Library library, String director) {
        Preconditions.checkNotNull(library);
        List<MediaInterface> mediaAvailable = library.getMediaAvailable();
        for (MediaInterface media : mediaAvailable) {
            if (media instanceof Movie) {
                if (media.getCreator().equals(director)) {
                    PrintMedia.printMedia(media);
                }
            }
        }
    }

    public static void printByAuthor(Library library, String author) {
        Preconditions.checkNotNull(library);
        List<MediaInterface> mediaAvailable = library.getMediaAvailable();
        for (MediaInterface media : mediaAvailable) {
            if (media instanceof Book) {
                if (media.getCreator().equals(author)) {
                    PrintMedia.printMedia(media);
                }
            }
        }
    }
}
