package COMP2450.logic.PrintLogic;

import COMP2450.domain.Media.Book;
import COMP2450.domain.Library;
import COMP2450.domain.Media.MediaInterface;
import COMP2450.domain.Media.Movie;
import com.google.common.base.Preconditions;
import java.util.List;

/**
 * PrintMedia
 * Handles printing summaries and detailed information
 * about movies, books, and other library media.
 */
public class PrintMedia {

    /**
     * Prints a simple text summary of a movie,
     * including title, director, ID, genre, and its reviews.
     *
     * @param movie the movie to print
     */
    public static void printMovie(Movie movie) {
        Preconditions.checkNotNull(movie, "Movie cannot be null");
        System.out.println("Movie ---------------");
        System.out.println("Title : " + movie.getTitle());
        System.out.println("Director : " + movie.getCreator());
        System.out.println("MediaID : " + movie.getMediaID());
        System.out.println("Genre : " + movie.getMediaGenre());
        System.out.println("---------------");

        for (var review : movie.getReviews()) {
            PrintReview.printReview(review);
        }
    }

    /**
     * Prints a simple text summary of a book,
     * including title, author, ISBN, genre, and its reviews.
     *
     * @param book the book to print
     */
    public static void printBook(Book book) {
        Preconditions.checkNotNull(book, "Book cannot be null");
        System.out.println("Book ---------------");
        System.out.println("Title : " + book.getTitle());
        System.out.println("Author : " + book.getCreator());
        System.out.println("ISBN : " + book.getMediaID());
        System.out.println("Genre : " + book.getMediaGenre());
        System.out.println("---------------");

        for (var review : book.getReviews()) {
            PrintReview.printReview(review);
        }
    }

    /**
     * Prints either a book or movie depending on media type.
     *
     * @param media the media item to print
     */
    public static void printMedia(MediaInterface media) {
        Preconditions.checkNotNull(media, "Media cannot be null");

        if (media instanceof Book) {
            printBook((Book) media);
        } else if (media instanceof Movie) {
            printMovie((Movie) media);
        }
    }

    /**
     * Prints all media items stored in a given library.
     *
     * @param library the library whose media should be printed
     */
    public static void printAllMedia(Library library) {
        Preconditions.checkNotNull(library, "Library cannot be null");
        List<MediaInterface> mediaAvailable = library.getMediaAvailable();
        for (MediaInterface media : mediaAvailable) {
            printMedia(media);
        }
    }

    /**
     * Prints all movies stored in the given library.
     *
     * @param library the library to list movies from
     */
    public static void printAllMovies(Library library) {
        Preconditions.checkNotNull(library, "Library cannot be null");
        List<MediaInterface> mediaAvailable = library.getMediaAvailable();

        for (MediaInterface media : mediaAvailable) {
            if (media instanceof Movie) {
                printMedia(media);
            }
        }
    }

    /**
     * Prints all books stored in the given library.
     *
     * @param library the library to list books from
     */
    public static void printAllBooks(Library library) {
        Preconditions.checkNotNull(library, "Library cannot be null");
        List<MediaInterface> mediaAvailable = library.getMediaAvailable();

        for (MediaInterface media : mediaAvailable) {
            if (media instanceof Book) {
                printMedia(media);
            }
        }
    }

    /**
     * Prints all movies in the library by a given director.
     *
     * @param library  the library to search
     * @param director the director name to match
     */
    public static void printByDirector(Library library, String director) {
        Preconditions.checkNotNull(library, "Library cannot be null");
        Preconditions.checkNotNull(director, "Director name cannot be null");

        List<MediaInterface> mediaAvailable = library.getMediaAvailable();

        for (MediaInterface media : mediaAvailable) {
            if (media instanceof Movie) {
                if (media.getCreator().equals(director)) {
                    printMedia(media);
                }
            }
        }
    }

    /**
     * Prints all books in the library by a given author.
     *
     * @param library the library to search
     * @param author  the author name to match
     */
    public static void printByAuthor(Library library, String author) {
        Preconditions.checkNotNull(library, "Library cannot be null");
        Preconditions.checkNotNull(author, "Author name cannot be null");

        List<MediaInterface> mediaAvailable = library.getMediaAvailable();

        for (MediaInterface media : mediaAvailable) {
            if (media instanceof Book) {
                if (media.getCreator().equals(author)) {
                    printMedia(media);
                }
            }
        }
    }
}
