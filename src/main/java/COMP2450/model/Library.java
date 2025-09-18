package COMP2450.model;

import java.util.ArrayList;

public class Library {

    String name;
    ArrayList<Shelf> shelves;
    ArrayList<MediaInterface> mediaAvailable;


    public Library(String name) {
        this.name = name;
        this.shelves = new ArrayList<>();
        this.mediaAvailable = new ArrayList<>();
    }

    public void addShelf(Shelf shelf) {
        this.shelves.add(shelf);
    }

    public void addMedia(MediaInterface media) {
        this.mediaAvailable.add(media);
    }

    public ArrayList<Shelf> getShelves() {
        return shelves;
    }

    public ArrayList<MediaInterface> getMediaAvailable() {
        return mediaAvailable;
    }


}
