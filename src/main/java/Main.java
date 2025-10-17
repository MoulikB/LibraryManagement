//import java.util.ArrayList;
//import java.util.Scanner;
//
//import COMP2450.model.Book;
//import COMP2450.model.Computer;
//import COMP2450.model.Library;
//import COMP2450.model.LibraryManagement;
//import COMP2450.model.MediaGenres;
//import COMP2450.model.MediaInterface;
//import COMP2450.model.Movie;
//import COMP2450.model.Resource;
//import COMP2450.model.Review;
//import COMP2450.model.StudyRoom;
//import COMP2450.model.TimeSlots;
//import COMP2450.model.User;
//import COMP2450.model.UserManagement;
//
//public class Main {
//
//    public static void main(String[] args) {
//
//        Scanner scnr = new Scanner(System.in);
//        mainMenu(scnr);
//        scnr.close();
//
//    }
//
//    public static void mainMenu(Scanner scnr){
//        System.out.println("Welcome to the library database management system");
//        chooseOption(scnr);
//    }
//
//    public static void chooseOption(Scanner scnr) {
//        printOptions();
//        int input = getIntInput(scnr);
//        switch (input) {
//            case 1: {
//                addMember(scnr);
//                break;
//            }
//            case 2: {
//                showMember(scnr);
//                break;
//            }
//            case 3: {
//                addLibrary(scnr);
//                break;
//            }
//            case 4: {
//                showLibrary(scnr);
//                break;
//            }
//            case 5: {
//                addMedia(scnr);
//                break;
//            }
//            case 6: {
//                showMedia(scnr);
//                break;
//            }
//            case 7: {
//                addResource(scnr);
//                break;
//            }
//            case 8: {
//                showResource(scnr);
//                break;
//            }
//            case 9: {
//                addReview(scnr);
//                break;
//            }
//            case 10: {
//                showReviews(scnr);
//                break;
//            }
//            case 11: {
//                showMap(scnr);
//                break;
//            }
//            case 12: {
//                removeMedia(scnr);
//                break;
//            }
//            case 13: {
//                removeMember(scnr);
//                break;
//            }
//            case 14: {
//                removeLibrary(scnr);
//                break;
//            }
//            case 15: {
//                reset(scnr);
//                break;
//            }
//            case 16: {
//                System.exit(0);
//                break;
//            }
//            default: {
//                System.out.println("Invalid choice, please try again.");
//                chooseOption(scnr);
//            }
//        }
//    }
//
//    public static void printOptions() {
//        System.out.println("1. ADD MEMBER");
//        System.out.println("2. SHOW MEMBER");
//        System.out.println("3. ADD LIBRARY");
//        System.out.println("4. SHOW LIBRARY");
//        System.out.println("5. ADD MEDIA");
//        System.out.println("6. SHOW MEDIA");
//        System.out.println("7. ADD RESOURCE");
//        System.out.println("8. SHOW RESOURCE");
//        System.out.println("9. ADD REVIEW");
//        System.out.println("10. SHOW REVIEWS");
//        System.out.println("11. SHOW MAP");
//        System.out.println("12. REMOVE MEDIA");
//        System.out.println("13. REMOVE MEMBER");
//        System.out.println("14. REMOVE LIBRARY");
//        System.out.println("15. RESET");
//        System.out.println("16. EXIT");
//        System.out.print("Enter the corresponding choice by number: ");
//    }
//
//
//    public static void reset(Scanner scnr){
//        UserManagement.reset();
//        LibraryManagement.reset();
//        System.out.println("Reset successful");
//        mainMenu(scnr);
//    }
//
//    static void addMember(Scanner scnr) {
//        System.out.print("Enter member name (input can't be null or empty) : ");
//        String username = getStringInput(scnr);
//
//        System.out.print("Enter new ID (input has to be an integer greater than 0) : ");
//        int id = getIntInput(scnr);
//
//        System.out.print("Enter your email (input can't be null or empty):");
//        String email = getStringInput(scnr);
//
//        System.out.print("Enter your phone number (input has to be an integer greater than 0) :");
//        int phone = getIntInput(scnr);
//
//        new User(username,id , email, phone);
//        chooseOption(scnr);
//    }
//
//    static void showMember(Scanner scnr) {
//        System.out.print("Enter member id (input has to be an integer greater than 0) : ");
//        int id = getIntInput(scnr);
//        User userFound = User.userDB.getUser(id);
//        if (userFound == null) {
//            System.out.println("No user found with that id");
//        } else {
//            System.out.println(userFound.userInfo());
//        }
//        chooseOption(scnr);
//    }
//
//    static void removeMember( Scanner scnr) {
//        System.out.print("Enter member ID (input has to be an integer greater than 0) : ");
//        int id = getIntInput(scnr);
//
//        User.userDB.removeUser(id);
//        chooseOption(scnr);
//    }
//
//    static void addReview(Scanner scnr) {
//
//        System.out.print("Enter member ID (input has to be an integer greater than 0) : ");
//        User user = User.userDB.getUser(getIntInput(scnr));
//
//        System.out.print("Enter library name (input can't be null or empty) : ");
//        Library library = LibraryManagement.findLibrary(getStringInput(scnr));
//
//        if (library!=null) {
//            System.out.print("Enter mediaID (input has to be an integer greater than 0) : ");
//            MediaInterface media = library.showMedia(getIntInput(scnr));
//            if (media!=null) {
//                System.out.print("Enter your review (input can't be null or empty): ");
//                String comment = getStringInput(scnr);
//
//                System.out.print("Enter total stars out of 10 (input has to be an integer greater than 0) : ");
//                int stars = getIntInput(scnr);
//
//                if (stars <= 10 && stars >= 1) {
//                    addReview(user, media, comment, stars);
//
//                } else {
//                    System.out.println("Invalid input for starts, must be between 1 and 10");
//                }
//            }
//        } else {
//            System.out.println("No library found with that name");
//        }
//        chooseOption(scnr);
//    }
//
//    static void addReview(User user, MediaInterface media, String comment, int stars) {
//        Review review = new Review(user,media,comment,stars);
//        user.addReview(review);
//        media.addReview(review);
//    }
//
//    static void showReviews(Scanner scnr) {
//        System.out.print("Enter UserID (input has to be an integer greater than 0) : ");
//        User user = User.userDB.getUser(getIntInput(scnr));
//
//        if (user!=null) {
//            System.out.print("Enter library name (input can't be null or empty): ");
//            Library library = LibraryManagement.findLibrary(getStringInput(scnr));
//
//            if (library != null) {
//                System.out.print("Enter mediaID (input has to be an integer greater than 0) : ");
//                MediaInterface media = library.showMedia(getIntInput(scnr));
//
//                showReviews(user, media);
//            } else {
//                System.out.println("No library found with that name");
//            }
//        }
//        chooseOption(scnr);
//    }
//
//    static void showReviews(User user, MediaInterface media) {
//        ArrayList<Review> reviews = media.getReviews();
//        for (Review review : reviews) {
//            if (review.user() == user) {
//                System.out.println(review);
//            }
//        }
//    }
//
//    static void addLibrary(Scanner scnr) {
//
//        System.out.println("Enter library name (input can't be null or empty) : ");
//        String name = getStringInput(scnr);
//
//        new Library(name);
//        System.out.println("Adding library " + name);
//
//        chooseOption(scnr);
//    }
//
//    static void showLibrary(Scanner scnr) {
//
//        System.out.println("Enter library name (input can't be null or empty) : ");
//        String name = getStringInput(scnr);
//
//        Library library = LibraryManagement.findLibrary(name);
//        if (library == null) {
//            System.out.println("No library found with name: " + name);
//        }
//        if (library != null) {
//            System.out.println(library);
//        }
//
//        chooseOption(scnr);
//    }
//
//    static void removeLibrary(Scanner scnr) {
//        System.out.print("Enter library name (input can't be null or empty): ");
//        String name = getStringInput(scnr);
//        Library library = LibraryManagement.findLibrary(name);
//        if (library!=null){
//            for (Library dbLibrary : LibraryManagement.libraries) {
//                if (dbLibrary.equals(library)) {
//                    LibraryManagement.libraries.remove(dbLibrary);
//                    System.out.println("Library " + name + " removed.");
//                    break;
//                }
//            }
//        } else {
//            System.out.println("Library " + name + " not found.");
//        }
//
//        chooseOption(scnr);
//    }
//
//    static void addMedia(Scanner scnr) {
//        System.out.println("Enter media type (book/movie) (input can't be null or empty): ");
//        String mediaType = getStringInput(scnr);
//        if (mediaType.equalsIgnoreCase("book")) {
//            addBook(scnr);
//        }  else if (mediaType.equalsIgnoreCase("movie")) {
//            addMovie(scnr);
//        }
//
//        chooseOption(scnr);
//    }
//
//    public static void addMovie(Scanner scnr) {
//        System.out.println("Enter the movie title (input can't be null or empty): ");
//        String title = getStringInput(scnr);
//
//        System.out.println("Enter the director (input can't be null or empty): ");
//        String director = getStringInput(scnr);
//
//
//        System.out.println("""
//                Enter the genre (input has to be :\s
//                HORROR,
//                    COMEDY,
//                    ACTION,
//                    ROMANCE,
//                    THRILLER,
//                    FICTION,
//                    NONFICTION):\s""");
//        MediaGenres genre = findGenre(getStringInput(scnr));
//
//        System.out.println("Enter the mediaID (input has to be an integer greater than 0) : ");
//        int mediaID = getIntInput(scnr);
//
//        System.out.println("Enter the Library Name (input can't be null or empty): ");
//        Library library = LibraryManagement.findLibrary(getStringInput(scnr));
//
//        new Movie( title, director, mediaID, library, genre);
//    }
//
//    static MediaGenres findGenre(String genreString){
//        boolean found = false;
//        MediaGenres genre = null;
//        for (MediaGenres genres : MediaGenres.values()) {
//            if (genres.name().equalsIgnoreCase(genreString)) {
//                genre = genres;
//                found = true;
//            }
//        }
//        if (!found) {
//            System.out.println("Genre " + genreString + " not found");
//        }
//        return genre;
//
//    }
//
//    public static void addBook(Scanner scnr) {
//
//        System.out.println("Enter the book title (input can't be null or empty): ");
//        String title = getStringInput(scnr);
//
//        System.out.println("Enter the author (input can't be null or empty): ");
//        String author = getStringInput(scnr);
//
//        System.out.println("Enter the publisher (input can't be null or empty): ");
//        String publisher = getStringInput(scnr);
//
//        System.out.println("Enter the genre (input can't be null or empty): ");
//        MediaGenres genre = findGenre(getStringInput(scnr));
//
//        System.out.println("Enter the isbn or mediaID (input has to be an integer greater than 0) : ");
//        int isbn = getIntInput(scnr);
//
//        System.out.println("Enter the Library Name (input can't be null or empty): ");
//        Library library = LibraryManagement.findLibrary(getStringInput(scnr));
//
//        if (library !=null) {
//            new Book(title,author,publisher,genre,isbn,library);
//        }
//    }
//
//    public static void showMedia(Scanner scnr) {
//
//        System.out.println("Enter the Library Name (input can't be null or empty): ");
//        Library library = LibraryManagement.findLibrary(getStringInput(scnr));
//
//        if (library!=null) {
//            System.out.println("Enter the mediaID (input has to be an integer greater than 0) : ");
//            int mediaID = getIntInput(scnr);
//
//            MediaInterface media = library.showMedia(mediaID);
//
//            if (media != null) {
//                System.out.println(media);
//            } else {
//                System.out.println("Media Not Found");
//            }
//        } else {
//            System.out.println("Library Not Found");
//        }
//
//        chooseOption(scnr);
//    }
//
//    public static void removeMedia(Scanner scnr) {
//
//        System.out.println("Enter the mediaID (input has to be an integer greater than 0) : ");
//        int mediaID = getIntInput(scnr);
//
//        System.out.println("Enter the Library Name (input can't be null or empty): ");
//        Library library = LibraryManagement.findLibrary(getStringInput(scnr));
//        if (library!=null) {
//            library.removeMedia(mediaID);
//        } else {
//            System.out.println("Library Not Found");
//            System.out.println("Media Not Found");
//        }
//        chooseOption(scnr);
//    }
//
//    public static void showMap(Scanner scnr) {
//
//        System.out.println("Enter library name (input can't be null or empty): ");
//        Library library = LibraryManagement.findLibrary(getStringInput(scnr));
//
//        if (library!=null) {
//            System.out.println("Showing Map");
//            library.printMap();
//        } else {
//            System.out.println("Library Not Found");
//        }
//        chooseOption(scnr);
//    }
//
//
//    public static void addResource(Scanner scnr) {
//
//        System.out.print("Enter the name of the library to which the resource is to be added (input can't be null or empty) : ");
//        String name = getStringInput(scnr);
//
//        Library library = LibraryManagement.findLibrary(name);
//        if (library!=null) {
//            System.out.print("Enter type of resource (computer/studyroom) (input can't be null or empty) : ");
//            String type = getStringInput(scnr).toLowerCase();
//
//            if (type.equals("computer")) {
//                System.out.print("Enter computer ID (input can't be null or empty) : ");
//                String compId = getStringInput(scnr);
//                Computer comp = new Computer(compId, library);
//                library.addResource(comp);
//                System.out.println("Added new Computer: " + comp.getResourceName());
//            } else if (type.equals("studyroom")) {
//                System.out.print("Enter room number (input can't be null or empty) : ");
//                String roomNum = getStringInput(scnr);
//                StudyRoom room = new StudyRoom(roomNum, library);
//                library.addResource(room);
//                System.out.println("Added new Study Room: " + room.getResourceName());
//            } else {
//                System.out.println("Unknown resource type.");
//            }
//        } else {
//            System.out.println("Library Not Found");
//        }
//
//        chooseOption(scnr);
//    }
//
//    public static void showResource(Scanner scnr) {
//
//        System.out.print("Enter the name of the library name (input can't be null or empty) : ");
//        String name = getStringInput(scnr);
//
//        Library library = LibraryManagement.findLibrary(name);
//        if (library!=null) {
//            System.out.print("Enter the name of the resource to show (e.g. Computer C1 or Study Room R1) (input can't be null or empty): ");
//            String resourceName = getStringInput(scnr);
//
//            boolean found = false;
//            for (Resource resource : library.getResources()) {
//                if (resource.getResourceName().equalsIgnoreCase(resourceName)) {
//                    found = true;
//                    System.out.println("\n--- Resource Information ---");
//                    System.out.println("Name: " + resource.getResourceName());
//                    System.out.println("Available Time Slots:");
//                    for (String slot : TimeSlots.ONE_HOUR_SLOTS) {
//                        if (resource.isAvailable(slot)) {
//                            System.out.println(" - " + slot);
//                        }
//                    }
//                    break;
//                }
//            }
//
//            if (!found) {
//                System.out.println("Resource not found: " + resourceName);
//            }
//        } else {
//            System.out.println("Library Not Found");
//        }
//
//        chooseOption(scnr);
//    }
//
//    public static String getStringInput(Scanner scnr) {
//        return scnr.nextLine();
//    }
//
//    static int getIntInput(Scanner scnr) {
//    while (true) {
//        String s = scnr.nextLine().trim();
//        try {
//            return Integer.parseInt(s);
//        }
//        catch (NumberFormatException e) {
//            System.out.print("Enter a valid integer: ");
//        }
//    }
//}
//}
