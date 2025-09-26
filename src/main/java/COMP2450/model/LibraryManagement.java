package COMP2450.model;

import com.google.common.base.Preconditions;

import java.util.ArrayList;

public class LibraryManagement {

    public static ArrayList<Library> libraries = new ArrayList<>();

    public LibraryManagement() {
        libraries = new ArrayList<>();
    }

    public static void addLIbrary(Library library) {
        Preconditions.checkNotNull(library);
        libraries.add(library);
    }

    public static ArrayList<Library> getLibraries() {
        return libraries;
    }

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

    public static void reset() {
       libraries = new ArrayList<>();
    }
}
