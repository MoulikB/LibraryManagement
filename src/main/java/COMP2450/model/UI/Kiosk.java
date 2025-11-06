package COMP2450.model.UI;

import COMP2450.model.*;
import COMP2450.model.BookResource;
import COMP2450.model.BorrowMedia.BorrowMedia;
import COMP2450.model.Exceptions.BookingConflictException;
import COMP2450.model.Exceptions.OverdueMediaException;
import COMP2450.model.Exceptions.UnavailableMediaException;
import COMP2450.model.Media.BrowseMedia;
import COMP2450.model.Media.MediaInterface;
import COMP2450.model.PrintLogic.PrintResource;

public class Kiosk {
    static User user = null;
    static Library library = null;
    public static void main(String[] args) {
        while (true) {
            welcomeScreen();
        }
    }

    public static void welcomeScreen() {

        BrowseMedia.library = library;
        while (user == null) {
            System.out.println("Welcome to the library kiosk");
            System.out.println("Please log in if you are already an User or to create an account");

            System.out.println("Type 1 or 2 respectively : ");
            int option = InputValidation.getIntInput();
            user = getUser(option);

        }

        System.out.println("Welcome" + user.getUsername());
        userChoices();

    }

    public static User getUser(int option) {


        User user = null;

        switch (option) {
            case 1: {
                user = LogIn.loginUser();
                break;
            } case 2:
                user = RegisterUser.registerUser();
                break;
        }
        return user;
    }

    public static void userChoices() {
        printChoices();

        int inputChoice = InputValidation.getIntInput();
        switch (inputChoice) {
            case 1: {
                browseMedia();
                break;
            } case 2: {
                borrowMedia();
                break;
            } case 3: {
                returnMedia();
                break;
            } case 4: {
                viewResources();
                break;
            } case 5: {
                bookResource();
                break;
            } case 6: {
                welcomeScreen();
                break;
            }
        }
    }
    public static void printChoices() {
        System.out.println("Please enter your choice :");
        System.out.println("1. Browse Media");
        System.out.println("2. Borrow Media");
        System.out.println("3. Return Media");
        System.out.println("4. View resources");
        System.out.println("5. Book a resource");
        System.out.println("6. Log Out\"");
    }

    public static void browseMedia() {
        browseMediaChoices();
        int inputChoice = InputValidation.getIntInput();
        switch (inputChoice) {
            case 1: {
                BrowseMedia.showAllMedia();
                break;
            } case  2: {
                BrowseMedia.showAllMovies();
                break;
            }case 3: {
                BrowseMedia.showAllBooks();
                break;
            }case 4: {
                System.out.println("Please enter director name :");
                String director = InputValidation.getStringInput();
                BrowseMedia.printByDirector(director);
                break;
            } case 5: {
                System.out.println("Please enter author name :");
                String author = InputValidation.getStringInput();
                BrowseMedia.printByAuthor(author);
                break;
            } case 7: {
                userChoices();
                break;
            } case 6: {
                System.out.println("Please enter the name of the media you are searching for:");
                String mediaSearch = InputValidation.getStringInput();
                BrowseMedia.searchMedia(mediaSearch);
                break;
            } case 8: {
                welcomeScreen();
                break;
            }
        }
    }

    public static void browseMediaChoices() {
        System.out.println("Please enter your choice :");
        System.out.println("1. Browse all media");
        System.out.println("2. Browse all movies");
        System.out.println("3. Browse all books");
        System.out.println("4. View movies by a certain director");
        System.out.println("5. View books by a certain author");
        System.out.println("6. Search");
        System.out.println("7. Go back");
        System.out.println("8. Log Out");
    }

    public static void borrowMedia() {
        System.out.println("Please enter the media ID of the media you would like to borrow: ");
        int mediaID = InputValidation.getIntInput();
        MediaInterface mediaFound = library.showMedia(mediaID);
        try  {
            BorrowMedia.issueUser(mediaFound,user);
        } catch (OverdueMediaException e) {
            System.out.println("Please pay any overdue fees and return all media before borrowing new media.");
        } catch(UnavailableMediaException e) {
            System.out.println("No more copies available, do you want to join the waitlist? [Y\\N]");
            String input = InputValidation.getStringInput();
            if (input.equals("Y")) {
                mediaFound.addWaitlist(user);
            }
        } finally{
            userChoices();
        }
    }

    public static void returnMedia() {
        System.out.println("Please enter the media ID of the media you would like to return: ");
        int mediaID = InputValidation.getIntInput();
        MediaInterface mediaFound = library.showMedia(mediaID);
        mediaFound.returnMedia();
        userChoices();
    }

    public static void viewResources() {
        PrintResource.printResources(library);
    }

    public static void bookResource() {
        System.out.println("Please enter the resource ID of the resource you would like to book: ");
        String resourceName = InputValidation.getStringInput();
        Resource resource = library.getResource(resourceName);

        System.out.println("Select a timeslot : ");
        for (var slot : TimeSlots.values()) {
            System.out.println(slot);
        }
        System.out.println("Please enter the corresponding timeslot number option :");
        TimeSlots timeSlot = TimeSlots.values()[InputValidation.getIntInput()];

        try {
            BookResource newBooking = new BookResource(new Booking(resource , user, timeSlot));
        } catch (BookingConflictException e) {
            System.out.println(e.getMessage());
            System.out.println("Try again? [Y\\N]");
            String input = InputValidation.getStringInput();
            if (input.equals("Y")) {
                bookResource();
            }
        } finally{
            userChoices();
        }
    }

}
