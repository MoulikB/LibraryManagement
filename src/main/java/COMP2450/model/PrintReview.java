package COMP2450.model;

public class PrintReview {
    /*
    Returns the review as a string
     */
    public static void printReview(Review review) {
        String output = "";
        output += "User: " + review.user().getUsername();
        output += "\nMedia: " + review.media().getTitle();
        output += "\nComment: " + review.comment();
        output += "\nStars: " + review.stars();
        System.out.println(output);
    }

    public static void printReviews(MediaInterface media) {
        for (Review rev : media.getReviews()) {
            printReview(rev);
        }
    }
}
