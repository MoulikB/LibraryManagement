package COMP2450;

import java.util.List;

import COMP2450.UI.InputValidation;
import COMP2450.domain.Library;
import COMP2450.domain.Media.Book;
import COMP2450.domain.Media.MediaInterface;
import COMP2450.domain.Media.Movie;
import COMP2450.domain.MediaGenres;
import COMP2450.domain.Review;
import COMP2450.logic.PrintLogic.*;
import COMP2450.domain.Resources.Computer;
import COMP2450.domain.Resources.Resource;
import COMP2450.domain.Resources.StudyRoom;
import COMP2450.domain.User;
import COMP2450.logic.*;
import COMP2450.logic.UserManagement.UserManagement;


public class Main {

    public static void main(String[] args) {

        mainMenu();


    }

    public static void mainMenu( ){
        System.out.println("Welcome to the library database management system");
        chooseOption();
    }

    public static void chooseOption() {
        printOptions();
        int input = InputValidation.getIntInput();
        switch (input) {
            case 1: {
                addMember();
                break;
            }
            case 2: {
                showMember();
                break;
            }
            case 3: {
                addLibrary();
                break;
            }
            case 4: {
                showLibrary();
                break;
            }
            case 5: {
                addMedia();
                break;
            }
            case 6: {
                showMedia();
                break;
            }
            case 7: {
                addResource();
                break;
            }
            case 8: {
                showResource();
                break;
            }
            case 9: {
                addReview();
                break;
            }
            case 10: {
                showReviews();
                break;
            }
            case 11: {
                showMap();
                break;
            }
            case 12: {
                removeMedia();
                break;
            }
            case 13: {
                removeMember();
                break;
            }
            case 14: {
                removeLibrary();
                break;
            }
            case 15: {
                reset();
                break;
            }
            case 16: {
                System.exit(0);
                break;
            }
            default: {
                System.out.println("Invalid choice, please try again.");
                chooseOption();
            }
        }
    }

    public static void printOptions() {
        System.out.println("1. ADD MEMBER");
        System.out.println("2. SHOW MEMBER");
        System.out.println("3. ADD LIBRARY");
        System.out.println("4. SHOW LIBRARY");
        System.out.println("5. ADD MEDIA");
        System.out.println("6. SHOW MEDIA");
        System.out.println("7. ADD RESOURCE");
        System.out.println("8. SHOW RESOURCE");
        System.out.println("9. ADD REVIEW");
        System.out.println("10. SHOW REVIEWS");
        System.out.println("11. SHOW MAP");
        System.out.println("12. REMOVE MEDIA");
        System.out.println("13. REMOVE MEMBER");
        System.out.println("14. REMOVE LIBRARY");
        System.out.println("15. RESET");
        System.out.println("16. EXIT");
        System.out.print("Enter the corresponding choice by number: ");
    }


    public static void reset( ){
        UserManagement.reset();
        LibraryManagement.reset();
        System.out.println("Reset successful");
        mainMenu();
    }

    static void addMember( ) {
        System.out.print("Enter member name (input can't be null or empty) : ");
        String username = InputValidation.getStringInput();

        System.out.print("Enter member password : ");
        String password = InputValidation.getStringInput();

        System.out.print("Enter new ID (input has to be an integer greater than 0) : ");
        int id = InputValidation.getIntInput();

        System.out.print("Enter your email (input can't be null or empty):");
        String email = InputValidation.getStringInput();

        System.out.print("Enter your phone number (input has to be an integer greater than 0) :");
        String phone = InputValidation.getStringInput();


        if (UserManagement.addUser(new User(username,password, id , email, phone))) {
            System.out.println("User already exists");
        }
        chooseOption();
    }

    static void showMember( ) {
        System.out.print("Enter member username : ");
        String username = InputValidation.getStringInput();
        User userFound = UserManagement.getUser(username);
        if (userFound == null) {
            System.out.println("No user found with that id");
        } else {
            PrintUser.userInfo(userFound);
        }
        chooseOption();
    }

    static void removeMember(  ) {
        System.out.print("Enter member username : ");
        String username = InputValidation.getStringInput();

        if (!UserManagement.removeUser(username)) {
            System.out.println("User does not exist, nothing has been removed");
        }
        chooseOption();
    }

    static void addReview( ) {

        System.out.print("Enter member username : ");
        String username = InputValidation.getStringInput();
        User user = UserManagement.getUser(username);

        System.out.print("Enter library name (input can't be null or empty) : ");
        Library library = LibraryManagement.findLibrary(InputValidation.getStringInput());

        if (library!=null) {
            System.out.print("Enter mediaID (input has to be an integer greater than 0) : ");
            MediaInterface media = library.showMedia(InputValidation.getIntInput());
            if (media!=null) {
                System.out.print("Enter your review (input can't be null or empty): ");
                String comment = InputValidation.getStringInput();

                System.out.print("Enter total stars out of 10 (input has to be an integer greater than 0) : ");
                int stars = InputValidation.getIntInput();

                if (stars <= 10 && stars >= 1) {
                    addReview(user, media, comment, stars);

                } else {
                    System.out.println("Invalid input for starts, must be between 1 and 10");
                }
            }
        } else {
            System.out.println("No library found with that name");
        }
        chooseOption();
    }

    static void addReview(User user, MediaInterface media, String comment, int stars) {
        Review review = new Review(user,media,comment,stars);
        user.addReview(review);
        media.addReview(review);
    }

    static void showReviews( ) {
        System.out.print("Enter member username : ");
        String username = InputValidation.getStringInput();
        User user = UserManagement.getUser(username);

        if (user!=null) {
            System.out.print("Enter library name (input can't be null or empty): ");
            Library library = LibraryManagement.findLibrary(InputValidation.getStringInput());

            if (library != null) {
                System.out.print("Enter mediaID (input has to be an integer greater than 0) : ");
                MediaInterface media = library.showMedia(InputValidation.getIntInput());

                showReviews(user, media);
            } else {
                System.out.println("No library found with that name");
            }
        }
        chooseOption();
    }

    static void showReviews(User user, MediaInterface media) {
        List<Review> reviews = media.getReviews();
        for (Review review : reviews) {
            if (review.user() == user) {
                PrintReview.printReview(review);
            }
        }
    }

    static void addLibrary( ) {

        System.out.println("Enter library name (input can't be null or empty) : ");
        String name = InputValidation.getStringInput();

        new Library(name);
        System.out.println("Adding library " + name);

        chooseOption();
    }

    static void showLibrary( ) {

        System.out.println("Enter library name (input can't be null or empty) : ");
        String name = InputValidation.getStringInput();

        Library library = LibraryManagement.findLibrary(name);
        if (library == null) {
            System.out.println("No library found with name: " + name);
        }
        if (library != null) {
            PrintLibrary.printLibrary(library);
        }

        chooseOption();
    }

    static void removeLibrary( ) {
        System.out.print("Enter library name (input can't be null or empty): ");
        String name = InputValidation.getStringInput();
        Library library = LibraryManagement.findLibrary(name);
        if (library!=null){
            for (Library dbLibrary : LibraryManagement.getLibraries()) {
                if (dbLibrary.equals(library)) {
                    LibraryManagement.getLibraries().remove(dbLibrary);
                    System.out.println("Library " + name + " removed.");
                    break;
                }
            }
        } else {
            System.out.println("Library " + name + " not found.");
        }

        chooseOption();
    }

    static void addMedia( ) {
        System.out.println("Enter media type (book/movie) (input can't be null or empty): ");
        String mediaType = InputValidation.getStringInput();
        if (mediaType.equalsIgnoreCase("book")) {
            addBook();
        }  else if (mediaType.equalsIgnoreCase("movie")) {
            addMovie();
        }

        chooseOption();
    }

    public static void addMovie( ) {
        System.out.println("Enter the movie title (input can't be null or empty): ");
        String title = InputValidation.getStringInput();

        System.out.println("Enter the director (input can't be null or empty): ");
        String director = InputValidation.getStringInput();


        System.out.println("""
                Enter the genre (input has to be :\s
                HORROR,
                    COMEDY,
                    ACTION,
                    ROMANCE,
                    THRILLER,
                    FICTION,
                    NONFICTION):\s""");
        MediaGenres genre = findGenre(InputValidation.getStringInput());

        System.out.println("Enter the mediaID (input has to be an integer greater than 0) : ");
        int mediaID = InputValidation.getIntInput();

        System.out.println("Enter the Library Name (input can't be null or empty): ");
        Library library = LibraryManagement.findLibrary(InputValidation.getStringInput());

        if (library !=null) {
            library.addMedia(new Movie( title, director, mediaID, library, genre));
        }
    }

    static MediaGenres findGenre(String genreString){
        boolean found = false;
        MediaGenres genre = null;
        for (MediaGenres genres : MediaGenres.values()) {
            if (genres.name().equalsIgnoreCase(genreString)) {
                genre = genres;
                found = true;
            }
        }
        if (!found) {
            System.out.println("Genre " + genreString + " not found");
        }
        return genre;

    }

    public static void addBook( ) {

        System.out.println("Enter the book title (input can't be null or empty): ");
        String title = InputValidation.getStringInput();

        System.out.println("Enter the author (input can't be null or empty): ");
        String author = InputValidation.getStringInput();

        System.out.println("Enter the publisher (input can't be null or empty): ");
        String publisher = InputValidation.getStringInput();

        System.out.println("Enter the genre (input can't be null or empty): ");
        MediaGenres genre = findGenre(InputValidation.getStringInput());

        System.out.println("Enter the isbn or mediaID (input has to be an integer greater than 0) : ");
        int isbn = InputValidation.getIntInput();

        System.out.println("Enter the Library Name (input can't be null or empty): ");
        Library library = LibraryManagement.findLibrary(InputValidation.getStringInput());

        if (library !=null) {
            library.addMedia(new Book(title,author,publisher,genre,isbn,library));
        }
    }

    public static void showMedia( ) {

        System.out.println("Enter the Library Name (input can't be null or empty): ");
        Library library = LibraryManagement.findLibrary(InputValidation.getStringInput());

        if (library!=null) {
            System.out.println("Enter the mediaID (input has to be an integer greater than 0) : ");
            int mediaID = InputValidation.getIntInput();

            MediaInterface media = library.showMedia(mediaID);

            if (media != null) {
                if (media instanceof Book) {
                    PrintMedia.printBook((Book) media);
                } else  {
                    PrintMedia.printMovie((Movie) media);
                }
            } else {
                System.out.println("Media Not Found");
            }
        } else {
            System.out.println("Library Not Found");
        }

        chooseOption();
    }

    public static void removeMedia( ) {

        System.out.println("Enter the mediaID (input has to be an integer greater than 0) : ");
        int mediaID = InputValidation.getIntInput();

        System.out.println("Enter the Library Name (input can't be null or empty): ");
        Library library = LibraryManagement.findLibrary(InputValidation.getStringInput());
        if (library!=null) {
            if (!library.removeMedia(mediaID)) {
                System.out.println("Media Not Found");
            }
        } else {
            System.out.println("Library Not Found");
            System.out.println("Media Not Found");
        }
        chooseOption();
    }

    public static void showMap( ) {

        System.out.println("Enter library name (input can't be null or empty): ");
        Library library = LibraryManagement.findLibrary(InputValidation.getStringInput());

        if (library!=null) {
            System.out.println("Showing Map");
            PrintLibrary.printMap(library);
        } else {
            System.out.println("Library Not Found");
        }
        chooseOption();
    }


    public static void addResource( ) {

        System.out.print("Enter the name of the library to which the resource is to be added (input can't be null or empty) : ");
        String name = InputValidation.getStringInput();

        Library library = LibraryManagement.findLibrary(name);
        if (library!=null) {
            System.out.print("Enter type of resource (computer/studyroom) (input can't be null or empty) : ");
            String type = InputValidation.getStringInput().toLowerCase();

            if (type.equals("computer")) {
                System.out.print("Enter computer ID (input can't be null or empty) : ");
                String compId = InputValidation.getStringInput();
                Computer comp = new Computer(compId, library);
                library.addResource(comp);
                System.out.println("Added new Computer: " + comp.getResourceName());
            } else if (type.equals("studyroom")) {
                System.out.print("Enter room number (input can't be null or empty) : ");
                String roomNum = InputValidation.getStringInput();
                StudyRoom room = new StudyRoom(roomNum, library);
                library.addResource(room);
                System.out.println("Added new Study Room: " + room.getResourceName());
            } else {
                System.out.println("Unknown resource type.");
            }
        } else {
            System.out.println("Library Not Found");
        }

        chooseOption();
    }

    public static void showResource( ) {

        System.out.print("Enter the name of the library name (input can't be null or empty) : ");
        String name = InputValidation.getStringInput();

        Library library = LibraryManagement.findLibrary(name);

        if (library!=null) {
            System.out.print("Enter the name of the resource to show (e.g. Computer C1 or Study Room R1) (input can't be null or empty): ");
            String resourceName = InputValidation.getStringInput();

            boolean found = false;
            for (Resource resource : library.getResources()) {
                if (resource.getResourceName().equalsIgnoreCase(resourceName)) {
                    found = true;
                    PrintResource.printResource(resource);
                    break;
                }
            }

            if (!found) {
                System.out.println("Resource not found: " + resourceName);
            }
        } else {
            System.out.println("Library Not Found");
        }

        chooseOption();
    }
}

