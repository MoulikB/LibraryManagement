package COMP2450.UI;

import COMP2450.domain.Library;
import COMP2450.domain.Media.Book;
import COMP2450.domain.Media.Movie;
import COMP2450.domain.MediaGenres;
import COMP2450.domain.Resources.Computer;
import COMP2450.domain.Resources.StudyRoom;

import COMP2450.persistence.LibraryPersistence;
import COMP2450.persistence.json.NotFoundException;
import COMP2450.persistence.json.LibraryPersistenceJson;

import java.nio.file.Path;

/**
 * LibraryLoader
 * Loads a persisted library if it exists, otherwise builds and saves
 * an initial demo library. This is used to ensure the system preserves
 * state between program runs.
 */
public class LibraryLoader {

    private static final String LIBRARY_NAME = "2450";
    private static final Path SAVE_FILE = Path.of("library.json");

    /**
     * Loads an existing library, or creates and saves a new one
     * if no saved file exists.
     *
     * @return a fully initialized Library
     */
    public static Library loadOrCreateLibrary() {
        LibraryPersistence persistence = new LibraryPersistenceJson(SAVE_FILE);

        try {
            // Try loading from disk
            Library existing = persistence.load(LIBRARY_NAME);
            System.out.println(" Loaded existing library from persistence.");
            return existing;

        } catch (NotFoundException e) {
            // If no file exists, create default library and save it
            System.out.println(" No saved library found. Creating a new one...");
            Library lib = buildInitialLibrary();
            persistence.save(lib);
            System.out.println(" Saved new default library.");
            return lib;
        }
    }

    /**
     * Builds a fresh default library (used on first run only).
     */
    private static Library buildInitialLibrary() {
        Library lib = new Library(LIBRARY_NAME);

        addMedia(lib);
        addResources(lib);

        return lib;
    }

    /**
     * Seeds books and movies into the library.
     */
    private static void addMedia(Library library) {
        // ===== BOOKS =====
        library.addMedia(new Book.BookBuilder()
                .title("1984")
                .author("George Orwell")
                .publisher("Penguin")
                .genre(MediaGenres.FICTION)
                .isbn(123)
                .library(library)
                .build());

        library.addMedia(new Book.BookBuilder()
                .title("To Kill a Mockingbird")
                .author("Harper Lee")
                .publisher("J.B. Lippincott & Co.")
                .genre(MediaGenres.FICTION)
                .isbn(124)
                .library(library)
                .build());

        library.addMedia(new Book.BookBuilder()
                .title("The Great Gatsby")
                .author("F. Scott Fitzgerald")
                .publisher("Scribner")
                .genre(MediaGenres.ROMANCE)
                .isbn(125)
                .library(library)
                .build());

        library.addMedia(new Book.BookBuilder()
                .title("The Hobbit")
                .author("J.R.R. Tolkien")
                .publisher("Allen & Unwin")
                .genre(MediaGenres.FICTION)
                .isbn(126)
                .library(library)
                .build());

        library.addMedia(new Book.BookBuilder()
                .title("The Catcher in the Rye")
                .author("J.D. Salinger")
                .publisher("Little, Brown and Company")
                .genre(MediaGenres.FICTION)
                .isbn(127)
                .library(library)
                .build());

        library.addMedia(new Book.BookBuilder()
                .title("Animal Farm")
                .author("George Orwell")
                .publisher("Penguin")
                .genre(MediaGenres.FICTION)
                .isbn(128)
                .library(library)
                .build());

        library.addMedia(new Book.BookBuilder()
                .title("Go Set a Watchman")
                .author("Harper Lee")
                .publisher("HarperCollins")
                .genre(MediaGenres.FICTION)
                .isbn(129)
                .library(library)
                .build());

        library.addMedia(new Book.BookBuilder()
                .title("Tender Is the Night")
                .author("F. Scott Fitzgerald")
                .publisher("Scribner")
                .genre(MediaGenres.ROMANCE)
                .isbn(130)
                .library(library)
                .build());

        library.addMedia(new Book.BookBuilder()
                .title("The Silmarillion")
                .author("J.R.R. Tolkien")
                .publisher("Allen & Unwin")
                .genre(MediaGenres.FICTION)
                .isbn(131)
                .library(library)
                .build());

        library.addMedia(new Book.BookBuilder()
                .title("Franny and Zooey")
                .author("J.D. Salinger")
                .publisher("Little, Brown and Company")
                .genre(MediaGenres.FICTION)
                .isbn(132)
                .library(library)
                .build());


// ===== MOVIES =====
        library.addMedia(new Movie.MovieBuilder()
                .title("Inception")
                .director("Christopher Nolan")
                .mediaID(201)
                .library(library)
                .genre(MediaGenres.THRILLER)
                .build());

        library.addMedia(new Movie.MovieBuilder()
                .title("The Godfather")
                .director("Francis Ford Coppola")
                .mediaID(202)
                .library(library)
                .genre(MediaGenres.ACTION)
                .build());

        library.addMedia(new Movie.MovieBuilder()
                .title("Titanic")
                .director("James Cameron")
                .mediaID(203)
                .library(library)
                .genre(MediaGenres.ROMANCE)
                .build());

        library.addMedia(new Movie.MovieBuilder()
                .title("The Shining")
                .director("Stanley Kubrick")
                .mediaID(204)
                .library(library)
                .genre(MediaGenres.HORROR)
                .build());

        library.addMedia(new Movie.MovieBuilder()
                .title("Forrest Gump")
                .director("Robert Zemeckis")
                .mediaID(205)
                .library(library)
                .genre(MediaGenres.COMEDY)
                .build());

        library.addMedia(new Movie.MovieBuilder()
                .title("Interstellar")
                .director("Christopher Nolan")
                .mediaID(206)
                .library(library)
                .genre(MediaGenres.FICTION)
                .build());

        library.addMedia(new Movie.MovieBuilder()
                .title("The Dark Knight")
                .director("Christopher Nolan")
                .mediaID(207)
                .library(library)
                .genre(MediaGenres.ACTION)
                .build());

        library.addMedia(new Movie.MovieBuilder()
                .title("Avatar")
                .director("James Cameron")
                .mediaID(208)
                .library(library)
                .genre(MediaGenres.THRILLER)
                .build());

        library.addMedia(new Movie.MovieBuilder()
                .title("Aliens")
                .director("James Cameron")
                .mediaID(209)
                .library(library)
                .genre(MediaGenres.ACTION)
                .build());

        library.addMedia(new Movie.MovieBuilder()
                .title("Apocalypse Now")
                .director("Francis Ford Coppola")
                .mediaID(210)
                .library(library)
                .genre(MediaGenres.THRILLER)
                .build());

        // Ensure at least one copy is available for each item
        for (var media : library.getMediaAvailable()) {
            media.addCopies();
        }
    }

    /**
     * Seeds study rooms and computers into the library.
     */
    private static void addResources(Library library) {

        library.addResource(new StudyRoom.StudyRoomBuilder()
                .roomName("Study Room 1")
                .library(library)
                .build());

        library.addResource(new StudyRoom.StudyRoomBuilder()
                .roomName("Study Room 2")
                .library(library)
                .build());

        library.addResource(new Computer.ComputerBuilder()
                .computerId("Computer 1")
                .library(library)
                .build());

        library.addResource(new Computer.ComputerBuilder()
                .computerId("Computer 2")
                .library(library)
                .build());

        library.addResource(new Computer.ComputerBuilder()
                .computerId("Computer 3")
                .library(library)
                .build());

    }
}
