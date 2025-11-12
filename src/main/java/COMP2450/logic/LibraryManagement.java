package COMP2450.logic;

import COMP2450.domain.Library;
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
     * Constructor : libraries keeps track of all Library objects in the program.
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
     * @param library the library to be added (can't be null)
     */
    public static void addLibrary(Library library) {
        checkInvariants();
        Preconditions.checkNotNull(library, "Library data cant't be null");
        libraries.add(library);
        checkInvariants();
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
     * @param name the name of the library we are searching for
     * @return an instance of the library object if found otherwise null
     */
    public static Library findLibrary(String name) {
        Preconditions.checkArgument(name!=null && !name.isEmpty() , "name is null or empty");
        checkInvariants();
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
