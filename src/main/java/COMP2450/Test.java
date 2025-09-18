package COMP2450;

import COMP2450.model.Library;
import COMP2450.model.Media;
import COMP2450.model.Review;
import COMP2450.model.User;

import java.util.ArrayList;

public class Test {
    public static void main(String[] args) {
        User m = new User("Moulik",123);
        System.out.println("Adding member Moulik");
        System.out.println(showMember("Moulik"));
        removeMember(123);
        User.getStringOutput();
        Library lib = addLibrary("moulik's library");
        Media nef = new Media("1984","Book","George Orwell", lib);
        AddReview(m,nef,"amazing",10);
        showReviews(m,nef);



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

    static void AddReview(User user, Media media, String comment, int stars) {
        media.addReview(new Review(user,media,comment,stars));
    }

    static void showReviews(User user, Media media) {
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


}
