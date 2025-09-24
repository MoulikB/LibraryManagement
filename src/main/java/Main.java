import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import COMP2450.model.Book;
import COMP2450.model.Computer;
import COMP2450.model.Library;
import COMP2450.model.LibraryManagement;
import COMP2450.model.MediaGenres;
import COMP2450.model.MediaInterface;
import COMP2450.model.Movie;
import COMP2450.model.Resource;
import COMP2450.model.Review;
import COMP2450.model.StudyRoom;
import COMP2450.model.TimeSlots;
import COMP2450.model.User;

public class Main {

    public static void main(String[] args) {


        mainMenu();


    }

    public static void mainMenu(){
        System.out.println("Welcome to the library database management system");
        chooseOption();
    }

    public static void chooseOption(){
        Scanner scnr = new Scanner(System.in);
        printOptions();
        int input = getIntInput(scnr);
        switch (input) {
            case 1: {
                addMember(scnr);
                break;
            } case 2: {
                removeMember(scnr);
                break;
            } case 3 : {
                showMember(scnr);
                break;
            } case 4: {
                addLibrary(scnr);
                break;
            } case 5: {
                showLibrary(scnr);
                break;
            } case 6: {
                removeLibrary(scnr);
                break;
            }
            case 7 : {
                addMedia(scnr);
                break;
            } case 8: {
                removeMedia(scnr);
                break;
            } case 9: {
                showMedia(scnr);
                break;
            } case 10: {
                break;
            } case 11: {
                break;
            } case 12: {
                addReview(scnr);
                break;
            } case 13: {
                showReviews(scnr);
                break;
            } case 14: {
                showMap(scnr);
            } case 15: {
                System.exit(0);
            } case 16: {
                reset();
            }

        }
        scnr.close();
    }

    public static void reset(){

    }

    public static void printOptions(){
        System.out.println("1. ADD MEMBER");
        System.out.println("2. REMOVE MEMBER");
        System.out.println("3. SHOW MEMBER");
        System.out.println("4. ADD LIBRARY");
        System.out.println("5. SHOW LIBRARY");
        System.out.println("6. REMOVE LIBRARY");
        System.out.println("7. ADD MEDIA");
        System.out.println("8. REMOVE MEDIA");
        System.out.println("9. SHOW MEDIA");
        System.out.println("10. ADD RESOURCE");
        System.out.println("11. SHOW RESOURCE");
        System.out.println("12. ADD REVIEW");
        System.out.println("13. SHOW REVIEW");
        System.out.println("14. SHOW MAP");
        System.out.println("15. EXIT");
        System.out.println("16. RESET");

    }

    static void addMember(Scanner scnr) {
        System.out.print("Enter member name: ");
        String username = getStringInput(scnr);

        System.out.print("Enter new ID: ");
        int id = getIntInput(scnr);

        new User(username,id);
        chooseOption();
    }

    static void showMember(Scanner scnr) {
        System.out.print("Enter member name: ");
        String username = getStringInput(scnr);
        User userFound = User.userDB.getUser(username);
        if (userFound == null) {
            System.out.println("No user found with username: " + username);
        } else {
            System.out.println(userFound.userInfo());
        }
        chooseOption();
    }

    static void removeMember( Scanner scnr) {
        System.out.print("Enter member ID: ");
        int id = getIntInput(scnr);

        User.userDB.removeUser(id);
        chooseOption();
    }

    static void addReview(Scanner scnr) {

        System.out.print("Enter UserName: ");
        User user = User.userDB.getUser(getStringInput(scnr));

        System.out.print("Enter library name : ");
        Library library = LibraryManagement.findLibrary(getStringInput(scnr));

        System.out.print("Enter mediaID: ");
        MediaInterface media = library.showMedia(getIntInput(scnr));

        System.out.print("Enter your review: ");
        String comment = getStringInput(scnr);

        System.out.print("Enter total stars out of 10: ");
        int stars =  getIntInput(scnr);


        addReview(user,media,comment,stars);
    }

    static void addReview(User user, MediaInterface media, String comment, int stars) {
        media.addReview(new Review(user,media,comment,stars));
    }

    static void showReviews(Scanner scnr) {
        System.out.print("Enter UserName: ");
        User user = User.userDB.getUser(getStringInput(scnr));

        System.out.print("Enter library name : ");
        Library library = LibraryManagement.findLibrary(getStringInput(scnr));

        System.out.print("Enter mediaID: ");
        MediaInterface media = library.showMedia(getIntInput(scnr));

        showReviews(user,media);
    }

    static void showReviews(User user, MediaInterface media) {
        ArrayList<Review> reviews = media.getReviews();
        for (Review review : reviews) {
            if (review.user() == user) {
                System.out.println(review);
            }
        }
    }

    static void addLibrary(Scanner scnr) {

        System.out.println("Enter library name: ");
        String name = getStringInput(scnr);

        Library library = new Library(name);
        System.out.println("Adding library " + name);
        chooseOption();
    }

    static void showLibrary(Scanner scnr) {
        System.out.println("Enter library name: ");
        String name = getStringInput(scnr);
        Library library = LibraryManagement.findLibrary(name);
        if (library == null) {
            System.out.println("No library found with name: " + name);
        }
        System.out.println(library);
        chooseOption();
    }

    static void removeLibrary(Scanner scnr) {
        System.out.print("Enter library name: ");
        String name = getStringInput(scnr);
        Library library = LibraryManagement.findLibrary(name);
        for (Library dbLibrary: LibraryManagement.libraries) {
            if (dbLibrary.equals(library)) {
                LibraryManagement.libraries.remove(dbLibrary);
                System.out.println("Library " + name + " removed.");
                break;
            }
        }
        chooseOption();
    }

    static void addMedia(Scanner scnr) {
        System.out.println("Enter media type (book/movie): ");
        String mediaType = getStringInput(scnr);
        if (mediaType.toLowerCase().equals("book")) {
            addBook(scnr);
        }  else if (mediaType.toLowerCase().equals("movie")) {
            addMovie(scnr);
        }
    }

    public static void addMovie(Scanner scnr) {
        System.out.println("Enter the movie title: ");
        String title = getStringInput(scnr);

        System.out.println("Enter the director: ");
        String director = getStringInput(scnr);


        System.out.println("Enter the genre: ");
        MediaGenres genre = findGenre(getStringInput(scnr));

        System.out.println("Enter the mediaID: ");
        int mediaID = getIntInput(scnr);

        System.out.println("Enter the Library Name: ");
        Library library = LibraryManagement.findLibrary(getStringInput(scnr));

        Movie mov = new Movie( title, director, mediaID, library, genre);
    }

    final  static MediaGenres findGenre(String genreString){
        boolean found = false;
        MediaGenres genre = null;
        for (MediaGenres genres : MediaGenres.values()) {
            if (genres.name().toLowerCase().equals(genreString.toLowerCase())) {
                genre = genres;
                found = true;
            }
        }
        if (!found) {
            System.out.println("Genre " + genreString + " not found");
        }
        return genre;

    }

    public static void addBook(Scanner scnr) {

        System.out.println("Enter the book title: ");
        String title = getStringInput(scnr);

        System.out.println("Enter the author: ");
        String author = getStringInput(scnr);

        System.out.println("Enter the publisher: ");
        String publisher = getStringInput(scnr);

        System.out.println("Enter the genre: ");
        MediaGenres genre = findGenre(getStringInput(scnr));

        System.out.println("Enter the isbn or mediaID: ");
        int isbn = getIntInput(scnr);

        System.out.println("Enter the Library Name: ");
        Library library = LibraryManagement.findLibrary(getStringInput(scnr));

        Book book = new Book(title,author,publisher,genre,isbn,library);
    }

    public static void showMedia(Scanner scnr) {

        System.out.println("Enter the Library Name: ");
        Library library = LibraryManagement.findLibrary(getStringInput(scnr));

        System.out.println("Enter the mediaID: ");
        int mediaID = getIntInput(scnr);

        MediaInterface media = library.showMedia(mediaID);

        if (media != null) {
            System.out.println(media);
        } else  {
            System.out.println("Media Not Found");
        }
    }

    public static void removeMedia(Scanner scnr) {

        System.out.println("Enter the mediaID: ");
        int mediaID = getIntInput(scnr);

        System.out.println("Enter the Library Name: ");
        Library library = LibraryManagement.findLibrary(getStringInput(scnr));

        library.removeMedia(mediaID);
    }

    public static void showMap(Scanner scnr) {
        System.out.println("Showing Map");
        Library library = LibraryManagement.findLibrary(getStringInput(scnr));
        library.printMap();
    }

    // ---------- ADD RESOURCE ----------
    public static void addResource(Scanner sc, Library library) {
        System.out.print("Enter type of resource (computer/studyroom): ");
        String type = sc.nextLine().trim().toLowerCase();

        if (type.equals("computer")) {
            System.out.print("Enter computer ID: ");
            String compId = sc.nextLine();
            Computer comp = new Computer(compId,library);
            library.addResource(comp);
            System.out.println("Added new Computer: " + comp.getResourceName());
        } else if (type.equals("studyroom")) {
            System.out.print("Enter room number: ");
            String roomNum = sc.nextLine();
            StudyRoom room = new StudyRoom(roomNum,library);
            library.addResource(room);
            System.out.println("Added new Study Room: " + room.getResourceName());
        } else {
            System.out.println("Unknown resource type.");
        }
    }

    // ---------- SHOW RESOURCE ----------
    public static void showResource(Scanner sc, Library library) {
        System.out.print("Enter the name of the resource to show (e.g. Computer C1 or Study Room R1): ");
        String resourceName = sc.nextLine().trim();

        boolean found = false;
        for (Resource resource : library.getResources()) {
            if (resource.getResourceName().equalsIgnoreCase(resourceName)) {
                found = true;
                System.out.println("\n--- Resource Information ---");
                System.out.println("Name: " + resource.getResourceName());
                System.out.println("Available Time Slots:");
                for (String slot : TimeSlots.ONE_HOUR_SLOTS) {
                    if (resource.isAvailable(slot)) {
                        System.out.println(" - " + slot);
                    }
                }
                break;
            }
        }

        if (!found) {
            System.out.println("Resource not found: " + resourceName);
        }
    }

    public static String getStringInput(Scanner scnr) {
        try {
            String input = scnr.next();
            return input;
        } catch (InputMismatchException e) {
            System.out.println("Invalid input");
        }
        return "Invalid Input";
    }

    public static int getIntInput(Scanner scnr) {
        try {
            int input = scnr.nextInt();
            return input;
        } catch (InputMismatchException e) {
            System.out.println("Invalid input");
        }
        return 0;
    }
}
