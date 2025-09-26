package COMP2450.model;

import com.google.common.base.Preconditions;

public class Book implements MediaInterface {
    private final String title;
    private final String author;
    private final String publisher;
    private final int mediaID;
    private Library library;
    private final MediaGenres genre;
    int totalCopies = 0;


    public Book(String title, String author, String publisher,
                MediaGenres genre, int isbn, Library library) {

        Preconditions.checkArgument(title != null && !title.isEmpty(), "title can't be null");
        Preconditions.checkArgument(author != null && !title.isEmpty(), "author can't be null");
        Preconditions.checkArgument(publisher != null && !publisher.isEmpty(), "publisher can't be null");
        Preconditions.checkArgument(genre != null, "genre can't be null");
        Preconditions.checkArgument(isbn >= 0, "isbn can't be negative");
        Preconditions.checkArgument(library != null, "library can't be null");

        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.genre = genre;
        this.mediaID = isbn;
        this.library = library;
        addToLibrary(this);
    }




    public String getMediaType () {
        return "Book";
    }

    public String getCreator () {
        return author;
    }

    public MediaGenres getMediaGenre () {
        return genre;
    }

    public boolean borrowMedia () {
        if (this.totalCopies > 0) {
            this.totalCopies--;
            return true;
        } else
            return false;
    }

    public void returnMedia () {
        totalCopies++;
    }

    public String getTitle () {
        return title;
    }

    public int getAvailableCopies () {
        return totalCopies;
    }
    
    public String getPublisher () {
        return publisher;
    }

    public Library getLibrary () {
        return library;
    }

    public int getMediaID () {
        return mediaID;
    }

    public void addCopies() {
        totalCopies++;
    }

    public String toString () {
        return "Book [title=" + title + ", author=" + author + ", publisher=" + publisher + ", Genre : " + genre;
    }

    public void setLibrary(Library library) {
        this.library = library;
    }
}

