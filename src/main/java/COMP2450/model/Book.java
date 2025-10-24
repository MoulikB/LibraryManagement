package COMP2450.model;

import com.google.common.base.Preconditions;

import java.util.ArrayList;
import java.util.List;

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
    static List<Review> reviews = new ArrayList<>();

    /**
     * Constructor: makes a new Book and adds it to the given library.
     *
     * @param title
     * @param author
     * @param genre
     * @param isbn
     * @param library
     * @param publisher
     * Preconditions:
     *  - strings are not null/empty
     *  - genre and library are not null
     *  - ISBN is a positive integer
     */
    public Book(String title, String author, String publisher,
                MediaGenres genre, int isbn, Library library) {



        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.genre = genre;
        this.mediaID = isbn;
        setLibrary(library);
        checkInvariants();
        library.addMedia(this);
        checkInvariants();
    }

    /**
     * Check the invariants for our domain model object and throw an error if violated
     */
    public void checkInvariants() {
        Preconditions.checkArgument(this.title != null && !this.title.isEmpty(), "title can't be null");
        Preconditions.checkArgument(this.author != null && !this.author.isEmpty(), "author can't be null");
        Preconditions.checkArgument(this.publisher != null && !this.publisher.isEmpty(), "publisher can't be null");
        Preconditions.checkArgument(this.genre != null, "genre can't be null");
        Preconditions.checkArgument(this.mediaID >= 0, "isbn can't be negative");
        Preconditions.checkArgument(this.library != null, "library can't be null");
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

    /**
     * Try to borrow one copy.
     * If at least one copy is available, decrease the count and return true.
     * Otherwise, return false and do nothing.
     * @return if you can borrow the book
     */
    public boolean borrowMedia () {
        checkInvariants();
        boolean result = false;
        if (this.totalCopies >= 1) {
            this.totalCopies--;
            result = true;
        }
        checkInvariants();
        return result;
    }

    /**
     * Try to return one copy.
     */
    public void returnMedia () {
        checkInvariants();
        totalCopies++;
        checkInvariants();
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

    /**
     * Add a new copy to the total amount of books
     */
    public void addCopies() {
        checkInvariants();
        totalCopies++;
        checkInvariants();
    }

    /** Change which library this book belongs to.
     * @param library
     */
    public void setLibrary(Library library) {
        Preconditions.checkArgument(library != null, "library can't be null");
        this.library = library;
        checkInvariants();
    }

    /**
     * Check if this media already exists in its library (same mediaID).
     * Returns true if found, false otherwise.
     * @param media
     * @return whether the media exists
     */
    public boolean mediaExists(MediaInterface media) {
        Preconditions.checkNotNull(media);
        Preconditions.checkArgument(media instanceof Book);
        boolean mediaExists = false;
        Library library = media.getLibrary();

        List<MediaInterface> mediaAvailable = library.getMediaAvailable();
        int index = 0;
        while (!mediaExists && index < mediaAvailable.size()) {
            if (mediaAvailable.get(index).getMediaID() == media.getMediaID()) {
                mediaExists = true;
            }
            index++;
        }
        checkInvariants();
        return mediaExists;
    }

    /**
     * Add a review to the shared reviews list.
     * @param review
     */
    public void addReview(Review review) {
        Preconditions.checkNotNull(review,"review can't be null");
        reviews.add(review);
    }

    /** Get all reviews from the shared list.
     *
     * @return List of reviews
     */
    public List<Review> getReviews() {
        return reviews;
    }
}

