package COMP2450.model;

public record Review(User user, Media media,String comment, int stars) {

    @Override
    public String toString() {
        String output = "";
        output += "User: " + user.getUsername();
        output += "\nMedia: " + media.getTitle();
        output += "\nComment: " + comment;
        output += "\nStars: " + stars;
        return output;
    }
}
