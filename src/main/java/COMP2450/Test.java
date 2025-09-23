package COMP2450;

import COMP2450.model.*;
import java.util.Scanner;

import javax.print.attribute.standard.Media;
import java.util.ArrayList;

public class Test {
    public static void main(String[] args) {
        User m = new User("Moulik",123);
        System.out.println("Adding member Moulik");
        System.out.println(showMember("Moulik"));
        removeMember(123);
        User.getStringOutput();
        Library lib = addLibrary("moulik's library");
        Book nef = new Book("1984","George Orwell" , "Penguin" , MediaGenres.FICTION,234,lib);
        nef.addReview(new Review(m,nef,"Amazing" , 10));
        showMap(lib);

        Library myLibrary = new Library("Downtown Library");

        // ADD RESOURCE: a computer and a room
        Computer comp1 = new Computer("C1",myLibrary);
        myLibrary.addResource(comp1);

        StudyRoom room1 = new StudyRoom("R1",myLibrary);
        myLibrary.addResource(room1);

        // SHOW RESOURCE: show all info about one resource
        System.out.println("\n--- Show Computer C1 ---");
        myLibrary.showResource("Computer C1");

        System.out.println("\n--- Show Study Room R1 ---");
        myLibrary.showResource("Study Room R1");

        // Book a time slot to test availability display
        String timeSlot = TimeSlots.ONE_HOUR_SLOTS.get(0); // 09:00-10:00
        if (comp1.isAvailable(timeSlot)) {
            comp1.addBooking(new Booking(comp1, "Moulik", timeSlot));
        }

        // Show again after booking
        System.out.println("\n--- Show Computer C1 (after booking) ---");
        myLibrary.showResource("Computer C1");




    }

    static void addMember(String username, int id) {
        new User(username,id);
    }

    static String showMember(String username) {
        return User.userDB.getUser(username).userInfo() ;
    }

    static void removeMember( int id) {
        User.userDB.removeUser(id);
    }

    static void AddReview(User user, MediaInterface media, String comment, int stars) {
        media.addReview(new Review(user,media,comment,stars));
    }

    static void showReviews(User user, MediaInterface media) {
        ArrayList<Review> reviews = media.getReviews();
        for (Review review : reviews) {
            if (review.user() == user) {
                System.out.println(review);
            }
        }
    }

    static Library addLibrary(String name) {
        Library library = new Library(name);
        System.out.println("Adding library " + name);
        return library;
    }

    static void showLibrary(Library library) {
        System.out.println(library);
    }

    public void addMovie(String title, String director, int mediaID, Library library, MediaGenres genre) {
        Movie mov = new Movie( title, director, mediaID, library, genre);
    }

    public void addBook(String title, String author, String publisher,
                        MediaGenres genre, int isbn, Library library) {
        Book book = new Book(title,author,publisher,genre,isbn,library);
    }

    public void showMedia(MediaInterface media) {
        System.out.println(media);
    }

    public void removeMedia(MediaInterface media, Library library) {
        library.getMediaAvailable().remove(media);
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


