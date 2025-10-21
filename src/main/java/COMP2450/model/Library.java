package COMP2450.model;

import com.google.common.base.Preconditions;

import java.util.ArrayList;
import java.util.List;
/**
 * Library
 * A library branch that holds media items, bookable resources, and a map of its layout.
 */

public class Library {

    String name;
    List<MediaInterface> mediaAvailable;
    Map map;
    List<Resource> resources = new ArrayList<>();
    public static LibraryManagement libraryManagement = new LibraryManagement();


    /*
     * Make a new Library.
     * - checks that the name is not null
     * - creates empty media/resources lists
     * - creates a Map for this library
     * - registers this library in LibraryManagement
     */
    public Library(String name) {
        Preconditions.checkArgument(name!=null && !name.isEmpty(), "Library name cannot be null or empty");
        this.name = name;
        this.mediaAvailable = new ArrayList<>();
        map = new Map(this);
        LibraryManagement.addLibrary(this);
    }

    public void checkInvariants(){
        Preconditions.checkArgument(name!=null && !name.isEmpty(), "Library name cannot be null or empty");
        Preconditions.checkArgument(mediaAvailable!=null, "Library media cannot be null or empty");
        Preconditions.checkArgument(resources!=null, "Library resources cannot be null or empty");
        Preconditions.checkArgument(map!=null, "Library map cannot be null or empty");
    }

    // Add a media item (book/movie) to this library.
    public void addMedia(MediaInterface media) {
        Preconditions.checkArgument(media!=null, "Media object cannot be null");
        this.mediaAvailable.add(media);
    }

    // Get all media in this library.
    public List<MediaInterface> getMediaAvailable() {
        return mediaAvailable;
    }

    // Get/set the library name.
    public String getName() {
        return name;
    }

    public void setName(String name) {
        Preconditions.checkArgument(name != null && !name.isEmpty(), "Library name cannot be null or empty");
        this.name = name;
        checkInvariants();
    }

    // Access the map object
    public Map getMap() {
        return map;
    }

    // Get all resources (computers, study rooms, etc.).
    public List<Resource> getResources() {
        return resources;
    }

    // Add a resource to this library.
    public void addResource(Resource resource) {
        Preconditions.checkArgument(resource!=null, "Resource object cannot be null");
        resources.add(resource);
    }

    // Print info about one resource by name
    public Resource getResource(String resourceName) {
        Resource resourceFound = null;
        for (Resource resource : resources) {
            if (resource.getResourceName().equals(resourceName)) {
                resourceFound = resource;
            }
        }
        checkInvariants();
        return resourceFound;
    }

    /*
     * Remove a media item by its ID.
     */
    public void removeMedia(int mediaId) {
        Preconditions.checkArgument(mediaId > 0, "Media ID cannot be less than 1");
        boolean removed = false;
        int index = 0;
        while (index < mediaAvailable.size() && !removed) {
            if (mediaAvailable.get(index).getMediaID() == mediaId) {
                mediaAvailable.remove(index);
                removed = true;
            }
            index++;
        }
        if (!removed) {
            System.out.println("Resource not found");
        }
        checkInvariants();

    }

    /*
     * Find and return a media item by ID.
     * Prints a message if not found and returns null.
     */
    public MediaInterface showMedia(int mediaId) {
        Preconditions.checkArgument(mediaId>0, "Media ID cannot be less than 1");
        MediaInterface media = null;
        int index = 0;
        while (index < mediaAvailable.size() && media == null) {
            if (mediaAvailable.get(index).getMediaID() == mediaId) {
                media = mediaAvailable.get(index);
            }
        }
        if (media == null) {
            System.out.println("Resource not found");
        }
        checkInvariants();
        return media;
    }
}
