import COMP2450.model.*;

import javax.print.attribute.standard.Media;
import java.util.ArrayList;
import java.util.Scanner;

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
        int input = scnr.nextInt();
        printOptions();
        switch (input) {
            case 1: {
                addMember(scnr);
                break;
            }
            case 2: {
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
            } case 6 : {
                addMedia(scnr);
                break;
            } case 7: {
                removeMedia(scnr);
                break;
            } case 8: {
                showMedia(scnr);
                break;
            } case 9: {
                break;
            } case 10: {
                break;
            } case 11: {
                addReview(scnr);
                break;
            } case 12: {
                showReviews(scnr);
                break;
            } case 13: {
                showMap(scnr);
            } case 14: {
                System.exit(0);
            } case 15: {
                reset();
            }

        }
        scnr.close();
    }

    public static void printOptions(){
        System.out.println("1. ADD MEMBER");
        System.out.println("2. REMOVE MEMBER");
        System.out.println("3. SHOW MEMBER");
        System.out.println("3. ADD LIBRARY");
        System.out.println("4. REMOVE LIBRARY");
        System.out.println("5. SHOW LIBRARY");
        System.out.println("6. ADD MEDIA");
        System.out.println("7. REMOVE MEDIA");
        System.out.println("8. SHOW MEDIA");
        System.out.println("9. ADD RESOURCE");
        System.out.println("10. SHOW RESOURCE");
        System.out.println("11. ADD REVIEW");
        System.out.println("12. SHOW REVIEW");
        System.out.println("13. SHOW MAP");
        System.out.println("14. EXIT");
        System.out.println("15. RESET");

    }

    static void addMember(Scanner scnr) {
        String username = scnr.next();
        int id = scnr.nextInt();
        System.out.print("Enter member name: ");
        System.out.print("Enter new ID: ");
        new User(username,id);
    }

    static String showMember(Scanner scnr) {
        String username = scnr.next();
        return User.userDB.getUser(username).userInfo() ;
    }

    static void removeMember( Scanner scnr) {
        int id = scnr.nextInt();
        User.userDB.removeUser(id);
    }

    static void addReview(Scanner scnr) {

        System.out.print("Enter UserName: ");
        User user = User.userDB.getUser(scnr.next());

        System.out.print("Enter library name : ");
        Library library = showLibrary(scnr);

        System.out.print("Enter mediaID: ");
        MediaInterface media = library.showMedia(scnr.nextInt());

        System.out.print("Enter your review: ");
        String comment = scnr.next();

        System.out.print("Enter total stars out of 10: ");
        int stars =  scnr.nextInt();


        addReview(user,media,comment,stars);
    }

    static void addReview(User user, MediaInterface media, String comment, int stars) {
        media.addReview(new Review(user,media,comment,stars));
    }

    static void ShowReviews(Scanner scnr) {
        System.out.print("Enter UserName: ");
        User user = User.userDB.getUser(scnr.next());

        System.out.print("Enter library name : ");
        Library library = showLibrary(scnr);

        System.out.print("Enter mediaID: ");
        MediaInterface media = library.showMedia(scnr.nextInt());

        System.out.print(showReviews(user,media);
    }

    static void showReviews(User user, MediaInterface media) {
        ArrayList<Review> reviews = media.getReviews();
        for (Review review : reviews) {
            if (review.user() == user) {
                System.out.println(review);
            }
        }
    }

    static Library addLibrary(Scanner scnr) {
        String name = scnr.next();
        Library library = new Library(name);
        System.out.println("Adding library " + name);
        return library;
    }

    static Library showLibrary(Scanner scnr) {
        Library library = addLibrary(scnr);
        return library;
    }

    static void addMedia(Scanner scnr) {
        String mediaType = scnr.next();
        if (mediaType.toLowerCase().equals("book")) {
            addBook(scnr);
        }  else if (mediaType.toLowerCase().equals("movie")) {
            addMovie(scnr);
        }
    }

    public static void addMovie(Scanner scnr) {
        System.out.println("Enter the movie title: ");
        String title = scnr.next();

        System.out.println("Enter the director: ");
        String director = scnr.next();


        System.out.println("Enter the genre: ");
        MediaGenres genre = findGenre(scnr.next());

        System.out.println("Enter the mediaID: ");
        int mediaID = scnr.nextInt();

        System.out.println("Enter the Library Name: ");
        Library library = showLibrary(scnr);

        Movie mov = new Movie( title, director, mediaID, library, genre);
    }

    public static void addBook(Scanner scnr) {

        System.out.println("Enter the book title: ");
        String title = scnr.next();

        System.out.println("Enter the author: ");
        String author = scnr.next();

        System.out.println("Enter the publisher: ");
        String publisher = scnr.next();

        System.out.println("Enter the genre: ");
        MediaGenres genre = findGenre(scnr.next());

        System.out.println("Enter the isbn or mediaID: ");
        int isbn = scnr.nextInt();

        System.out.println("Enter the Library Name: ");
        Library library = showLibrary(scnr);

        Book book = new Book(title,author,publisher,genre,isbn,library);
    }

    public static void showMedia(Scanner scnr) {

        System.out.println("Enter the Library Name: ");
        Library library = showLibrary(scnr);

        System.out.println("Enter the mediaID: ");
        int mediaID = scnr.nextInt();

        MediaInterface media = library.showMedia(mediaID);

        if (media != null) {
            System.out.println(media);
        } else  {
            System.out.println("Media Not Found");
        }
    }

    public static void removeMedia(Scanner scnr) {

        System.out.println("Enter the mediaID: ");
        int mediaID = scnr.nextInt();

        System.out.println("Enter the Library Name: ");
        Library library = showLibrary(scnr);

        library.removeMedia(mediaID);
    }

    public static void showMap(Library library) {
        System.out.println("Showing Map");
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
}
