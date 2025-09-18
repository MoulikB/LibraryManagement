package COMP2450.model;

import java.util.ArrayList;

public class Media {

    String title;
    String mediaType;
    String author;
    ArrayList<Review> reviews = new ArrayList<>();
    static Library library;
    String publisher;
    String genre;
    Shelf location;
    int copies;
    int availableCopies = 0;
    int yearOfRelease;
    double versionOrVolume;
    String format;

    public Media(String title, String mediaType, String author, Library library) {
        this.title = title;
        this.mediaType = mediaType;
        this.author = author;
        this.copies++;
        this.availableCopies++;
        this.library = library;
        library.addMedia(this);
    }

    public String getMediaType() {
        return mediaType;
    }
    public String getAuthor() {
        return author;
    }

    public String getPublisher() {
        return publisher;
    }

    public boolean borrowMedia() {
        if (availableCopies > 0) {
            availableCopies--;
            return true; // success
        }
        return false; // no copies available
        }
    
    public void returnMedia() {
        if (availableCopies < copies) {
            availableCopies++;
        }
    }

    public String getTitle() {

        return title;
    }

    public int getAvailableCopies() {
        return availableCopies;
    }

    public void addReview(Review review) {
        reviews.add(review);
    }

    public ArrayList<Review> getReviews() {
        return reviews;
    }







}

class MediaManagement {

}
 