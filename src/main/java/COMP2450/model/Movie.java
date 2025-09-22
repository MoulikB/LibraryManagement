package COMP2450.model;

public class Movie implements MediaInterface {
    private final String title;
    private final String director;
    private final int mediaID;
    private final Library library;
    private final MediaGenres genre;
    int totalCopies = 0;

    public Movie(String title, String director, int mediaID,
                 Library library, MediaGenres genre) {
        this.title = title;
        this.director = director;
        this.mediaID = mediaID;
        this.library = library;
        this.genre = genre;
        addToLibrary(this);

    }


    public String getMediaType() {
        return "Movie";
    }

    public String getCreator() {
        return this.director;
    }

    public MediaGenres getMediaGenre() {
        return this.genre;
    }

    public boolean borrowMedia() {
        if (this.totalCopies > 0) {
            this.totalCopies--;
            return true;
        } else
            return false;
    }

    public void returnMedia() {
        totalCopies++;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public int getAvailableCopies() {
        return this.totalCopies;
    }

    @Override
    public Library getLibrary() {
        return this.library;
    }

    @Override
    public int getMediaID() {
        return this.mediaID;
    }

    @Override
    public void addCopies() {
        this.totalCopies++;
    }

    @Override
    public String toString() {
        return "Movie [title=" + title + ", director=" + director + ", mediaID=" + mediaID +" , genre= " + genre;
    }
}
