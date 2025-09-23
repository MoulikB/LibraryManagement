package COMP2450.model;

import javax.print.attribute.standard.Media;
import java.util.ArrayList;

public class Library {

    String name;
    String description;
    ArrayList<MediaInterface> mediaAvailable;
    Map map;
    ArrayList<Resource> resources = new ArrayList<>();

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


    public ArrayList<Resource> getResources() {
        return resources;
    }

    public void addResource(Resource resource) {
        resources.add(resource);
    }

    public void showResource(String resourceName) {
        for (Resource resource : resources) {
            if(resource.getResourceName().equals(resourceName)) {
                System.out.println(resource);
            }
        }
    }

    public void removeMedia(int mediaId) {
        boolean removed = false;
        for (MediaInterface media : mediaAvailable) {
            if (media.getMediaID() == mediaId) {
                mediaAvailable.remove(media);
                removed = true;
            }
        }
        if (!removed) {
            System.out.println("Resource not found");
        }

    }

    public MediaInterface showMedia(int mediaId) {
        MediaInterface media = null;
        boolean removed = false;
        for (MediaInterface mediaSearch : mediaAvailable) {
            if (mediaSearch.getMediaID() == mediaId) {
                media = mediaSearch;
                removed = true;
            }
        }  if (!removed) {
            System.out.println("Resource not found");
        }
        return media;
    }
}
