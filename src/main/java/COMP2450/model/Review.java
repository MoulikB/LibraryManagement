package COMP2450.model;


import com.google.common.base.Preconditions;

/**
 * Review
 * A user-written review of a media item, storing the reviewer, comment text,
 * and a star rating to capture feedback.
 */

public record Review(User user, MediaInterface media, String comment, int stars) {



    /*
    Returns the review as a string
     */
    public String toString() {
        String output = "";
        output += "User: " + user.getUsername();
        output += "\nMedia: " + media.getTitle();
        output += "\nComment: " + comment;
        output += "\nStars: " + stars;
        return output;
    }
}