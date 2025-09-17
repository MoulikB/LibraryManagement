package COMP2450.model;

public class Media {

    private String title;
    private String mediaType;
    private String author;
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

}

public class MediaManagement {

}
 