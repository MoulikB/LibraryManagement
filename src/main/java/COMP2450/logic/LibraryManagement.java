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

    private List<Library> libraries;

    /**
     * Constructor : libraries keeps track of all Library objects in the program.
     */
    public LibraryManagement() {
        libraries = new ArrayList<>();
    }

    /**
     * Check the invariants for our domain model object and throw an error if violated
     */
    private void checkInvariants() {
        Preconditions.checkArgument(libraries != null, "LibraryManagement data cant't be null");
    }

    /**
     * Add a library to the list.
     * @param library the library to be added (can't be null)
     */
    public void addLibrary(Library library) {
        checkInvariants();
        Preconditions.checkNotNull(library, "Library data cant't be null");
        libraries.add(library);
        checkInvariants();
    }


    /**
     * Reset the list to empty.
     * This clears all libraries from the registry.
     */
    public void reset() {
        libraries = new ArrayList<>();
        checkInvariants();
    }
}
