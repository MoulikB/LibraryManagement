package COMP2450.persistence;

import COMP2450.domain.Library;

import java.nio.file.Path;

public class persistenceMain {
    public static void main(String[] args) {
        Path saveFile = Path.of("library.json");   // or choose your file location
        LibraryPersistence persistence = new LibraryPersistenceJson(saveFile);

        Library library;

        try {
            library = persistence.load("MyLibraryName");
            System.out.println("Loaded existing library.");
        } catch (NotFoundException e) {
            System.out.println("No saved library found. Creating a new one...");
            library = new Library.LibraryBuilder()
                    .name("MyLibraryName")
                    .build();
        }

        // --- Your main program logic goes here ---
        // UI, commands, borrowing, returning, booking … all modify `library`.

        // When the program ends:
        persistence.save(library);
    }

}
