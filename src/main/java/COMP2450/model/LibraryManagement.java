package COMP2450.model;

import com.google.common.base.Preconditions;

import java.util.ArrayList;
import java.util.List;

/**
 * LibraryManagement
 * Top-level container for all libraries in the system,
 * supporting addition, lookup, and reset of library records.
 */

public class LibraryManagement {

    public static List<Library> libraries;

    /**
     * LibraryManagement: keeps track of all Library objects in the program.
     */
    public LibraryManagement() {
        libraries = new ArrayList<>();
    }

    /**
     * Check the invariants for our domain model object and throw an error if violated
     */
    public static void checkInvariants() {
        Preconditions.checkArgument(libraries != null, "LibraryManagement data cant't be null");
    }

    /**
     * Add a library to the list.
     * @param library
     * Precondition: library is not null.
     */
    public static void addLibrary(Library library) {
        Preconditions.checkNotNull(library, "Library data cant't be null");
        libraries.add(library);
    }

    /** Shared list of all libraries in the system.
     *
     * @return list of libraries
     */
    public static List<Library> getLibraries() {
        return libraries;
    }

    /**
     * Find a library by its name.
     * @param name
     * @return library object
     * - name must not be null or empty.
     * - returns the Library if found, otherwise returns null.
     */
    public static Library findLibrary(String name) {
        Preconditions.checkArgument(name!=null && !name.isEmpty() , "name is null or empty");
        Library output = null;
        int index = 0;
        while (output == null && index < libraries.size()) {
            if (libraries.get(index).getName().equals(name)) {
                output = libraries.get(index);
            }
            index++;
        }
        checkInvariants();
        return output;
    }

    /**
     * Reset the list to empty.
     * This clears all libraries from the registry.
     */
    public static void reset() {
       libraries = new ArrayList<>();
        checkInvariants();
    }
}
