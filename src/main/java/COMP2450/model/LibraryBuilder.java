package COMP2450.model;

import COMP2450.model.Media.Book;
import COMP2450.model.Media.Movie;

public class LibraryBuilder {
    public static Library initializeLibrary() {
        String name = "2450";
        Library lib = new Library(name);
        addMedia(lib);
        addResources(lib);
        return lib;
    }

    private static void addMedia(Library library) {
        library.addMedia(new Book("1984", "George Orwell", "Penguin", MediaGenres.FICTION, 123, library));
        library.addMedia(new Book("To Kill a Mockingbird", "Harper Lee", "J.B. Lippincott & Co.", MediaGenres.FICTION, 124, library));
        library.addMedia(new Book("The Great Gatsby", "F. Scott Fitzgerald", "Scribner", MediaGenres.ROMANCE, 125, library));
        library.addMedia(new Book("The Hobbit", "J.R.R. Tolkien", "Allen & Unwin", MediaGenres.FICTION, 126, library));
        library.addMedia(new Book("The Catcher in the Rye", "J.D. Salinger", "Little, Brown and Company", MediaGenres.FICTION, 127, library));
        library.addMedia(new Book("Animal Farm", "George Orwell", "Penguin", MediaGenres.FICTION, 128, library));
        library.addMedia(new Book("Go Set a Watchman", "Harper Lee", "HarperCollins", MediaGenres.FICTION, 129, library));
        library.addMedia(new Book("Tender Is the Night", "F. Scott Fitzgerald", "Scribner", MediaGenres.ROMANCE, 130, library));
        library.addMedia(new Book("The Silmarillion", "J.R.R. Tolkien", "Allen & Unwin", MediaGenres.FICTION, 131, library));
        library.addMedia(new Book("Franny and Zooey", "J.D. Salinger", "Little, Brown and Company", MediaGenres.FICTION, 132, library));

        library.addMedia(new Movie("Inception", "Christopher Nolan", 201, library, MediaGenres.THRILLER));
        library.addMedia(new Movie("The Godfather", "Francis Ford Coppola", 202, library, MediaGenres.ACTION));
        library.addMedia(new Movie("Titanic", "James Cameron", 203, library, MediaGenres.ROMANCE));
        library.addMedia(new Movie("The Shining", "Stanley Kubrick", 204, library, MediaGenres.HORROR));
        library.addMedia(new Movie("Forrest Gump", "Robert Zemeckis", 205, library, MediaGenres.COMEDY));
        library.addMedia(new Movie("Interstellar", "Christopher Nolan", 206, library, MediaGenres.FICTION));
        library.addMedia(new Movie("The Dark Knight", "Christopher Nolan", 207, library, MediaGenres.ACTION));
        library.addMedia(new Movie("Avatar", "James Cameron", 208, library, MediaGenres.THRILLER));
        library.addMedia(new Movie("Aliens", "James Cameron", 209, library, MediaGenres.ACTION));
        library.addMedia(new Movie("Apocalypse Now", "Francis Ford Coppola", 210, library, MediaGenres.THRILLER));

        for (var media : library.getMediaAvailable()) {
            media.addCopies(); // Add at least one copy of each media
        }
    }

    private static void addResources(Library library) {
        library.addResource(new StudyRoom("Study Room 1", library));
        library.addResource(new StudyRoom("Study Room 2", library));
        library.addResource(new Computer("Computer 1", library));
        library.addResource(new Computer("Computer 2", library));
        library.addResource(new Computer("Computer 3", library));
    }
}
