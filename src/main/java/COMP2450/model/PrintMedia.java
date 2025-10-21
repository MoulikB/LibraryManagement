package COMP2450.model;

public class PrintMedia {

    /**
     *
     * @return A simple text summary of the movie
     */
    public static void printMovie(Movie movie) {
        System.out.println( "Movie [title=" + movie.getTitle() + ", director=" + movie.getCreator() +
                ", mediaID=" + movie.getMediaID() +" , genre= " + movie.getMediaGenre() + "]");
    }

    /**
     * * @return A simple text summary of the book.
     */
    public static void printBook(Book book) {
         System.out.println("Book [title=" +  book.getTitle() + ", author=" + book.getCreator() +
                ", publisher=" + book.getPublisher() + ", Genre : " + book.getMediaGenre() + ", isbn=" + book.getMediaID() + "]");
    }
}
