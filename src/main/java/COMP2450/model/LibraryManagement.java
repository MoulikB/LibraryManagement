package COMP2450.model;

import com.google.common.base.Preconditions;

import java.util.ArrayList;

/**
 * LibraryManagement
 * Top-level container for all libraries in the system,
 * supporting addition, lookup, and reset of library records.
 */

public class LibraryManagement {

    public static ArrayList<Library> libraries = new ArrayList<>();

    /*
     * LibraryManagement: keeps track of all Library objects in the program.
     */
    public LibraryManagement() {
        libraries = new ArrayList<>();
    }

    /*
     * Add a library to the list.
     * Precondition: library is not null.
     */
    public static void addLibrary(Library library) {
        Preconditions.checkNotNull(library);
        libraries.add(library);
    }

    // Shared list of all libraries in the system.
    public static ArrayList<Library> getLibraries() {
        return libraries;
    }

    /*
     * Find a library by its name.
     * - name must not be null or empty.
     * - returns the Library if found, otherwise prints a message and returns null.
     */
    public static Library findLibrary(String name) {
        Preconditions.checkArgument(name!=null && !name.isEmpty() , "name is null or empty");
        Library output = null;
        for (Library library : libraries) {
            if  (library.getName().equals(name)) {
                output = library;
            }
        }
        if (output == null) {
            System.out.println("Library with name " + name + " not found");
        }
        return output;
    }

    /*
     * Reset the list to empty.
     * This clears all libraries from the registry.
     */
    public static void reset() {
       libraries = new ArrayList<>();
    }
}
