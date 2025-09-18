package COMP2450.model;

import java.util.ArrayList;

public class Media {

    private String title;
    private String mediaType;
    private String author;
    public ArrayList<Review> reviews = new ArrayList<>();
    private String publisher;
    private String genre;
    private Shelf location;
    private int copies;
    private int availableCopies = 0;
    private int yearOfRelease;
    private double versionOrVolume;
    private String format;

    public Media(String title, String mediaType, String author) {
        this.title = title;
        this.mediaType = mediaType;
        this.author = author;
        this.copies++;
        this.availableCopies++;
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
 