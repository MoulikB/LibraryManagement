package COMP2450.model;

import com.google.common.base.Preconditions;

public class Movie implements MediaInterface {
    private final String title;
    private final String director;
    private final int mediaID;
    private final Library library;
    private final MediaGenres genre;
    int totalCopies = 0;

    /*
     * Constructor: makes a new Movie and adds it to the given library.
     *
     * Preconditions:
     *  - title, director, library, genre are not null
     *  - mediaID is not negative
     */
    public Movie(String title, String director, int mediaID,
                 Library library, MediaGenres genre) {
        Preconditions.checkNotNull(title);
        Preconditions.checkNotNull(director);
        Preconditions.checkArgument(mediaID >= 0);
        Preconditions.checkNotNull(library);
        Preconditions.checkNotNull(genre);
        Preconditions.checkArgument(!title.isEmpty());
        this.title = title;
        this.director = director;
        this.mediaID = mediaID;
        this.library = library;
        this.genre = genre;
        addToLibrary(this);

    }

    // This media is a "Movie".
    public String getMediaType() {
        return "Movie";
    }

    // The creator for a movie is the director.
    public String getCreator() {
        return this.director;
    }

    // Return the genre of this movie.
    public MediaGenres getMediaGenre() {
        return this.genre;
    }

    /*
     * Try to borrow one copy.
     * If at least one copy is available, decrease the count and return true.
     */
    public boolean borrowMedia() {
        boolean result = false;
        if (this.totalCopies > 0) {
            this.totalCopies--;
            result = true;
        }
        return result;
    }

    // Return one copy (increase the available count by 1).
    public void returnMedia() {
        totalCopies++;
    }

    // Get the title.
    public String getTitle() {
        return this.title;
    }

    // How many copies can be borrowed right now.
    public int getAvailableCopies() {
        return this.totalCopies;
    }

    // Which library this movie belongs to.
    public Library getLibrary() {
        return this.library;
    }

    // Get the media ID for this movie.
    public int getMediaID() {
        return this.mediaID;
    }

    // Add one more available copy.
    public void addCopies() {
        this.totalCopies++;
    }

    // A simple text summary of the movie
    public String toString() {
        return "Movie [title=" + title + ", director=" + director + ", mediaID=" + mediaID +" , genre= " + genre + "]";
    }
}
