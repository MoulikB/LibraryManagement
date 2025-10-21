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

    /*
     * Try to borrow one copy.
     * If at least one copy is available, decrease the count and return true.
     * Otherwise, return false and do nothing.
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

    // Return one copy (increase the available count by 1).
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

    // Add one more available copy.
    public void addCopies() {
        checkInvariants();
        totalCopies++;
        checkInvariants();
    }

    // Change which library this book belongs to.
    public void setLibrary(Library library) {
        Preconditions.checkArgument(library != null, "library can't be null");
        this.library = library;
        checkInvariants();
    }

    /*
     * Check if this media already exists in its library (same mediaID).
     * Returns true if found, false otherwise.
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

    /*
     * Add a review to the shared reviews list.
     */
    public void addReview(Review review) {
        Preconditions.checkNotNull(review,"review can't be null");
        reviews.add(review);
    }

    // Get all reviews from the shared list.
    public List<Review> getReviews() {
        return reviews;
    }
}

