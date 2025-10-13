package COMP2450.model;

import com.google.common.base.Preconditions;

/**
 * Book
 * A book stored in a library’s catalog, identified by ISBN,
 * with data like author and publisher, and tracked by available copies.
 */

public class Book implements MediaInterface {
    private final String title;
    private final String author;
    private final String publisher;
    private final int mediaID;
    private Library library;
    private final MediaGenres genre;
    int totalCopies = 0;
    int issuedDays = 1;

    /*
     * Constructor: makes a new Book and adds it to the given library.
     *
     * Preconditions:
     *  - strings are not null/empty
     *  - genre and library are not null
     *  - ISBN is a positive integer
     */
    public Book(String title, String author, String publisher,
                MediaGenres genre, int isbn, Library library) {

        Preconditions.checkArgument(title != null && !title.isEmpty(), "title can't be null");
        Preconditions.checkArgument(author != null && !author.isEmpty(), "author can't be null");
        Preconditions.checkArgument(publisher != null && !publisher.isEmpty(), "publisher can't be null");
        Preconditions.checkArgument(genre != null, "genre can't be null");
        Preconditions.checkArgument(isbn >= 0, "isbn can't be negative");
        Preconditions.checkArgument(library != null, "library can't be null");

        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.genre = genre;
        this.mediaID = isbn;
        setLibrary(library);
        addToLibrary(this);
    }



    /**
     * @return the fixed media type label for books.
     */
    public String getMediaType () {
        return "Book";
    }

    /**
     * @return the creator/author of the book.
     */
    public String getCreator () {
        return author;
    }

    /**
     * @return the genre of the book.
     */
    public MediaGenres getMediaGenre () {
        return genre;
    }

    /*
     * Try to borrow one copy.
     * If at least one copy is available, decrease the count and return true.
     * Otherwise, return false and do nothing.
     */
    public boolean borrowMedia () {
        boolean result = false;
        if (this.totalCopies > 0) {
            this.totalCopies--;
            result = true;
        }
        return result;
    }

    // Return one copy (increase the available count by 1).
    public void returnMedia () {
        totalCopies++;
    }

    /**
     * @return the title of the book.
     */
    public String getTitle () {
        return title;
    }

    /**
     * @return How many copies can be borrowed right now.
     */
    public int getAvailableCopies () {
        return totalCopies;
    }

    /**
     * @return Get the publisher name.
    */
    public String getPublisher () {
        return publisher;
    }

    /**
     * @return Get the library this book belongs to.
     */
    public Library getLibrary () {
        return library;
    }

    /**
     * @return Get the media ID (for books it is the ISBN).
     */
    public int getMediaID () {
        return mediaID;
    }

    // Add one more available copy.
    public void addCopies() {
        totalCopies++;
    }


    /**
     * * @return A simple text summary of the book.
     */
    public String toString () {
        return "Book [title=" + getTitle() + ", author=" + getCreator() + ", publisher=" + getPublisher() + ", Genre : " + getMediaGenre() + ", isbn=" + getMediaID() + "]";
    }

    // Change which library this book belongs to.
    public void setLibrary(Library library) {
        this.library = library;
    }
}

