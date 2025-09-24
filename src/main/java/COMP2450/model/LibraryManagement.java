package COMP2450.model;

import java.util.ArrayList;

public class LibraryManagement {

    public static ArrayList<Library> libraries = new ArrayList<>();

    public LibraryManagement() {
        libraries = new ArrayList<>();
    }

    public static void addLIbrary(Library library) {
        libraries.add(library);
    }

    public static ArrayList<Library> getLibraries() {
        return libraries;
    }

    public static Library findLibrary(String name) {
        boolean found = false;
        Library output = null;
        for (Library library : libraries) {
            if (library.getName().equals(name)) {
                output = library;
            }
        }
        if (output == null) {
            System.out.println("Library with name " + name + " not found");
        }
        return output;
    }
}
