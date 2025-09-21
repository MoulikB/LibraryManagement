package COMP2450.model;

import java.util.ArrayList;

public class Library {

    String name;
    String description;
    ArrayList<MediaInterface> mediaAvailable;
    Map map;

    public Library(String name) {
        this.name = name;
        this.description = "";
        this.mediaAvailable = new ArrayList<>();
        map = new Map(this);

    }

    public Library(String name,String description) {
        this.name = name;
        this.description = description;
        this.mediaAvailable = new ArrayList<>();
    }

    public void addDescription(String description) {
        this.description = description;
    }


    public void addMedia(MediaInterface media) {
        this.mediaAvailable.add(media);
    }

    public ArrayList<MediaInterface> getMediaAvailable() {
        return mediaAvailable;
    }

    public String toString() {
        String output = "Library :" + this.name;
        output += "\nDescription: " + this.description;
        return output;    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }

    public Map getMap() {
        return map;
    }

    public void printMap() {
        map.printMap();
    }


}
