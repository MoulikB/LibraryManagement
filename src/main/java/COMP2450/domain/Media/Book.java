package COMP2450.domain.Media;

import COMP2450.Exceptions.UnavailableMediaException;
import COMP2450.domain.Review;
import COMP2450.domain.User;
import com.google.common.base.Preconditions;
import COMP2450.domain.Library;
import COMP2450.domain.MediaGenres;

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
    private final List<Review> reviews = new ArrayList<>();
    private int totalCopies = 0;
    private int issuedDays = 1;
    private final List<User> waitlist = new ArrayList<>();

    /**
     * Constructor: makes a new Book and adds it to the given library with some preconditions
     *
     * @param title     the title of the book (must not be null or empty)
     * @param author    the author's name (must not be null or empty)
     * @param publisher the publisher's name (must not be null or empty)
     * @param genre     the book's literary genre (must not be null)
     * @param isbn      the unique identifier for this book (must be positive)
     * @param library   the library to which this book belongs (must not be null)
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
        checkInvariants();
    }

    /**
     * Check the invariants for our domain model object and throw an error if violated
     */
    private void checkInvariants() {
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
    public String getMediaType() {
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
     * Borrow one copy.
     */
    public void borrowMedia(User user) {
        Preconditions.checkArgument(user != null, "user can't be null");
        checkInvariants();
        this.totalCopies--;
        checkInvariants();
    }

    /**
     * Return one copy.
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
     * @param library the library to which this book belongs (must not be null)
     */
    public void setLibrary(Library library) {
        Preconditions.checkArgument(library != null, "library can't be null");
        this.library = library;
        checkInvariants();
    }

    /**
     * Add a review to the shared reviews list.
     * @param review A review for the media (must not be null)
     */
    public void addReview(Review review) {
        Preconditions.checkNotNull(review,"review can't be null");
        reviews.add(review);
    }

    /**
     * Get all reviews from the shared list.
     *
     * @return List of reviews
     */
    public List<Review> getReviews() {
        return reviews;
    }

    /**
     * Issues this media item to a user if available or if they are first in the waitlist.
     *
     * @param user the user requesting the media
     * @throws UnavailableMediaException if the media is not available for this user
     */
    public void issueUser(User user) throws UnavailableMediaException {
        checkInvariants();
        Preconditions.checkNotNull(user, "User cannot be null");

        boolean userIsFirst = waitlist.isEmpty() || waitlist.get(0).equals(user);
        boolean hasCopy = this.getAvailableCopies() >= 1;


        // Only allow issuing if the waitlist is empty or user is first in line
        if (userIsFirst && hasCopy) {
            this.borrowMedia(user);
            user.issue(this);
            this.removeFromWaitlist(user);
            checkInvariants();
        } else {
            throw new UnavailableMediaException("Media is not available for this user.");
        }

        checkInvariants();
    }

    /**
     * Adds a user to the waitlist for this media item.
     *
     * @param user the user to add to the waitlist
     */
    public void addWaitlist(User user) {
        checkInvariants();
        Preconditions.checkNotNull(user, "User cannot be null");
        waitlist.add(user);
        checkInvariants();
    }

    /**
     * Returns the current waitlist for this media item.
     *
     * @return the list of users waiting for this media
     */
    public List<User> getWaitlist() {
        return waitlist;
    }

    /**
     *
     * @return the number of days media has been issued
     */
    public int getIssuedDay() {
        return issuedDays;
    }

    /**
     * Removes the user from waitlist if media is issued.
     * @param user the user being removed
     */
    public void removeFromWaitlist(User user) {
        checkInvariants();
        this.getWaitlist().remove(user);
        checkInvariants();
    }

    public void setIssuedDay(int  num) {
        checkInvariants();
        issuedDays = num;
        checkInvariants();
    }

    public static class BookBuilder {
        private String title;
        private String author;
        private String publisher;
        private MediaGenres genre;
        private Integer isbn;
        private Library library;

        private int totalCopies = 0;
        private int issuedDays = 1;

        // reviews and waitlist only saved if you want (optional)
        private final List<Review> reviews = new ArrayList<>();
        private final List<User> waitlist = new ArrayList<>();

        public BookBuilder title(String t) {
            Preconditions.checkArgument(t != null && !t.isEmpty());
            this.title = t;
            return this;
        }

        public BookBuilder author(String a) {
            Preconditions.checkArgument(a != null && !a.isEmpty());
            this.author = a;
            return this;
        }

        public BookBuilder publisher(String p) {
            Preconditions.checkArgument(p != null && !p.isEmpty());
            this.publisher = p;
            return this;
        }

        public BookBuilder genre(MediaGenres g) {
            Preconditions.checkNotNull(g);
            this.genre = g;
            return this;
        }

        public BookBuilder isbn(int id) {
            Preconditions.checkArgument(id >= 0);
            this.isbn = id;
            return this;
        }

        public BookBuilder library(Library lib) {
            Preconditions.checkNotNull(lib);
            this.library = lib;
            return this;
        }

        public BookBuilder totalCopies(int copies) {
            Preconditions.checkArgument(copies >= 0);
            this.totalCopies = copies;
            return this;
        }

        public BookBuilder issuedDays(int days) {
            Preconditions.checkArgument(days >= 1);
            this.issuedDays = days;
            return this;
        }

        public BookBuilder addReview(Review r) {
            Preconditions.checkNotNull(r);
            reviews.add(r);
            return this;
        }

        public BookBuilder addWaitlistUser(User u) {
            Preconditions.checkNotNull(u);
            waitlist.add(u);
            return this;
        }

        public Book build() {
            Preconditions.checkArgument(title != null);
            Preconditions.checkArgument(author != null);
            Preconditions.checkArgument(publisher != null);
            Preconditions.checkNotNull(genre);
            Preconditions.checkNotNull(isbn);
            Preconditions.checkNotNull(library);

            Book b = new Book(title, author, publisher, genre, isbn, library);

            // Restore state
            for (Review r : reviews) {
                b.addReview(r);
            }
            for (User u : waitlist) {
                b.addWaitlist(u);
            }

            // Set copies
            while (b.getAvailableCopies() < totalCopies) {
                b.addCopies();
            }

            b.setIssuedDay(issuedDays);

            return b;
        }
    }

}
