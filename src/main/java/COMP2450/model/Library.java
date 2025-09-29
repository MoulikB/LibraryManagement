package COMP2450.model;

import com.google.common.base.Preconditions;

import java.util.ArrayList;

public class Library {

    String name;
    ArrayList<MediaInterface> mediaAvailable;
    Map map;
    ArrayList<Resource> resources = new ArrayList<>();

    /*
     * Make a new Library.
     * - checks that the name is not null
     * - creates empty media/resources lists
     * - creates a Map for this library
     * - registers this library in LibraryManagement
     */
    public Library(String name) {
        this.name = name;
        Preconditions.checkArgument(name!=null, "Library name cannot be null");
        this.mediaAvailable = new ArrayList<>();
        map = new Map(this);
        LibraryManagement.addLibrary(this);
    }

    // Add a media item (book/movie) to this library.
    public void addMedia(MediaInterface media) {
        Preconditions.checkArgument(media!=null, "Media object cannot be null");
        this.mediaAvailable.add(media);
    }

    // Get all media in this library.
    public ArrayList<MediaInterface> getMediaAvailable() {
        return mediaAvailable;
    }

    // A simple text summary of the library.
    @Override
    public String toString() {
        return "Library : " + this.name;
    }

    // Get/set the library name.
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Access the map object
    public Map getMap() {
        return map;
    }

    public void printMap() {
        map.printMap();
    }

    // Get all resources (computers, study rooms, etc.).
    public ArrayList<Resource> getResources() {
        return resources;
    }

    // Add a resource to this library.
    public void addResource(Resource resource) {
        Preconditions.checkArgument(resource!=null, "Resource object cannot be null");
        resources.add(resource);
    }

    // Print info about one resource by name
    public void showResource(String resourceName) {
        for (Resource resource : resources) {
            if (resource.getResourceName().equals(resourceName)) {
                System.out.println(resource);
            }
        }
    }

    /*
     * Remove a media item by its ID.
     */
    public void removeMedia(int mediaId) {
        Preconditions.checkArgument(mediaId > 0, "Media ID cannot be less than 1");
        boolean removed = false;
        for (MediaInterface media : mediaAvailable) {
            if (media.getMediaID() == mediaId) {
                mediaAvailable.remove(media);
                removed = true;
                break;
            }
        }
        if (!removed) {
            System.out.println("Resource not found");
        }

    }

    /*
     * Find and return a media item by ID.
     * Prints a message if not found and returns null.
     */
    public MediaInterface showMedia(int mediaId) {
        Preconditions.checkArgument(mediaId>0, "Media ID cannot be less than 1");
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
