package COMP2450.model;

import com.google.common.base.Preconditions;

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
        System.out.println( "Movie [title=" + movie.getTitle() + ", director=" + movie.getCreator() +
                ", mediaID=" + movie.getMediaID() +" , genre= " + movie.getMediaGenre() + "]");
    }

    /**
     * * Print a text summary of the book.
     */
    public static void printBook(Book book) {
         Preconditions.checkNotNull(book);
         System.out.println("Book [title=" +  book.getTitle() + ", author=" + book.getCreator() +
                ", publisher=" + book.getPublisher() + ", Genre : " + book.getMediaGenre() + ", isbn=" + book.getMediaID() + "]");
    }
}
