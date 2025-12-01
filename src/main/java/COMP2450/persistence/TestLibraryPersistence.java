package COMP2450.persistence;

import COMP2450.domain.Library;
import COMP2450.domain.Media.Book;
import COMP2450.domain.Media.MediaInterface;
import COMP2450.domain.MediaGenres;
import COMP2450.domain.Resources.Computer;
import COMP2450.domain.Resources.Resource;

import java.nio.file.Path;

public class TestLibraryPersistence {

    public static void main(String[] args) throws Exception {
        Path path = Path.of("test-library.json");
        LibraryPersistence p = new LibraryPersistenceJson(path);

        // Create library
        Library lib = new Library.LibraryBuilder()
                .name("TestLibrary")
                .build();

        // Add one resource
        Resource c = new Computer.ComputerBuilder()
                .computerId("PC-1")
                .library(lib)
                .build();

        // Add one book
        MediaInterface b = new Book.BookBuilder()
                .title("1984")
                .author("George Orwell")
                .publisher("Moon Co.")
                .genre(MediaGenres.ROMANCE)
                .isbn(1234)
                .library(lib)
                .totalCopies(3)
                .build();

        lib.addMedia(b);
        lib.addResource(c);

        // Save
        p.save(lib);
        System.out.println("Saved!");

        // Load
        Library loaded = p.load("TestLibrary");
        System.out.println("Loaded library: " + loaded.getName());
        System.out.println("Media: " + loaded.getMediaAvailable().size());
        System.out.println("Resources: " + loaded.getResources().size());
    }
}
