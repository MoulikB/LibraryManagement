package COMP2450.logic;

import COMP2450.domain.Media.Book;
import COMP2450.domain.Media.Movie;
import COMP2450.domain.Resources.Computer;
import COMP2450.domain.Resources.StudyRoom;
import COMP2450.domain.Library;
import COMP2450.domain.MediaGenres;

/**
 * TestLibraryBuilder
 * Helps initialize a sample library with preloaded media and resources.
 * Used mainly at startup for testing or demonstration.
 */
public class TestLibraryBuilder {

    public static void main(String[] args) {
        Library newLib = initializeLibrary();

    }
    /**
     * Builds and returns a new Library pre-filled with media and resources.
     *
     * @return the initialized Library object
     */
    public static Library initializeLibrary() {
        Library.LibraryBuilder builder = new Library.LibraryBuilder();
        builder.name("2450-Library");
        addMedia(builder);
        addResources(builder);
        return builder.build();
    }

    /**
     * Adds predefined books and movies to the library’s media collection.
     *
     * @param builder the library to add media to
     */
    private static void addMedia(Library.LibraryBuilder builder) {
        Library library = builder.build();
        builder.withMedia(new Book("1984", "George Orwell", "Penguin", MediaGenres.FICTION, 123,library ));
        builder.withMedia(new Book("To Kill a Mockingbird", "Harper Lee", "J.B. Lippincott & Co.", MediaGenres.FICTION, 124, library));
        builder.withMedia(new Book("The Great Gatsby", "F. Scott Fitzgerald", "Scribner", MediaGenres.ROMANCE, 125, library));
        builder.withMedia(new Book("The Hobbit", "J.R.R. Tolkien", "Allen & Unwin", MediaGenres.FICTION, 126, library));
        builder.withMedia(new Book("The Catcher in the Rye", "J.D. Salinger", "Little, Brown and Company", MediaGenres.FICTION, 127, library));
        builder.withMedia(new Book("Animal Farm", "George Orwell", "Penguin", MediaGenres.FICTION, 128, library));
        builder.withMedia(new Book("Go Set a Watchman", "Harper Lee", "HarperCollins", MediaGenres.FICTION, 129, library));
        builder.withMedia(new Book("Tender Is the Night", "F. Scott Fitzgerald", "Scribner", MediaGenres.ROMANCE, 130, library));
        builder.withMedia(new Book("The Silmarillion", "J.R.R. Tolkien", "Allen & Unwin", MediaGenres.FICTION, 131, library));
        builder.withMedia(new Book("Franny and Zooey", "J.D. Salinger", "Little, Brown and Company", MediaGenres.FICTION, 132, library));

        builder.withMedia(new Movie("Inception", "Christopher Nolan", 201, library, MediaGenres.THRILLER));
        builder.withMedia(new Movie("The Godfather", "Francis Ford Coppola", 202, library, MediaGenres.ACTION));
        builder.withMedia(new Movie("Titanic", "James Cameron", 203, library, MediaGenres.ROMANCE));
        builder.withMedia(new Movie("The Shining", "Stanley Kubrick", 204, library, MediaGenres.HORROR));
        builder.withMedia(new Movie("Forrest Gump", "Robert Zemeckis", 205, library, MediaGenres.COMEDY));
        builder.withMedia(new Movie("Interstellar", "Christopher Nolan", 206, library, MediaGenres.FICTION));
        builder.withMedia(new Movie("The Dark Knight", "Christopher Nolan", 207, library, MediaGenres.ACTION));
        builder.withMedia(new Movie("Avatar", "James Cameron", 208, library, MediaGenres.THRILLER));
        builder.withMedia(new Movie("Aliens", "James Cameron", 209, library, MediaGenres.ACTION));
        builder.withMedia(new Movie("Apocalypse Now", "Francis Ford Coppola", 210, library, MediaGenres.THRILLER));

        for (var media : library.getMediaAvailable()) {
            media.addCopies(); // Add at least one copy of each media
        }
    }

    /**
     * Adds study rooms and computers to the library’s resource list.
     *
     * @param builder the library to add resources to
     */
    private static void addResources(Library.LibraryBuilder builder) {
        Library library = builder.build();
        builder.withResource(new StudyRoom("Study Room 1", library));
        builder.withResource(new StudyRoom("Study Room 2", library));
        builder.withResource(new Computer("Computer 1", library));
        builder.withResource(new Computer("Computer 2", library));
        builder.withResource(new Computer("Computer 3", library));
    }
}
