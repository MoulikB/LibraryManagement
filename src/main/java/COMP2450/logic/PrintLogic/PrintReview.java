package COMP2450.logic.PrintLogic;

import COMP2450.domain.Media.MediaInterface;
import COMP2450.domain.Review;
import com.google.common.base.Preconditions;

/**
 * Utility class responsible for printing reviews
 **/
public class PrintReview {
    /**
     prints the review as a string
     */
    public static void printReview(Review review) {
        Preconditions.checkNotNull(review, "review is null");
        System.out.println("==============");
        String output = "";
        output += "User: " + review.user().getUsername();
        output += "\nMedia: " + review.media().getTitle();
        output += "\nComment: " + review.comment();
        output += "\nStars: " + review.stars();
        System.out.println(output);
        System.out.println("==============");
        Preconditions.checkNotNull(review, "review is null");
    }

    /**
     prints the list of reviews for a media as a string
     */
    public static void printReviews(MediaInterface media) {
        Preconditions.checkNotNull(media, "media is null");
        for (Review rev : media.getReviews()) {
            Preconditions.checkNotNull(rev, "review is null");
            printReview(rev);
        }
        Preconditions.checkNotNull(media, "media is null");
    }
}
