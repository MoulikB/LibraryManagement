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


    /**
     * Make a new Library.
     * @param name
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

    /**
     * Check the invariants for our domain model object and throw an error if violated
     */
    public void checkInvariants(){
        Preconditions.checkArgument(name!=null && !name.isEmpty(), "Library name cannot be null or empty");
        Preconditions.checkArgument(mediaAvailable!=null, "Library media cannot be null or empty");
        Preconditions.checkArgument(resources!=null, "Library resources cannot be null or empty");
        Preconditions.checkArgument(map!=null, "Library map cannot be null or empty");
    }

    /** Add a media item (book/movie) to this library.
     *
     * @param media
     */
    public void addMedia(MediaInterface media) {
        Preconditions.checkArgument(media!=null, "Media object cannot be null");
        this.mediaAvailable.add(media);
    }

    /** Get all media in this library.
     *
     * @return list of all media
     */
    public List<MediaInterface> getMediaAvailable() {
        return mediaAvailable;
    }

    /** Get/set the library name.
     *
     * @return library.name
     */
    public String getName() {
        return name;
    }

    /** Murate name of library
     *
     * @param name
     */
    public void setName(String name) {
        Preconditions.checkArgument(name != null && !name.isEmpty(), "Library name cannot be null or empty");
        this.name = name;
        checkInvariants();
    }

    /** Access the map object
     *
     * @return the map to the library
     */
    public Map getMap() {
        return map;
    }

    /** Get all resources (computers, study rooms, etc.).
     *
     * @return list of all resources
     */
    public List<Resource> getResources() {
        return resources;
    }

    /** Add a resource to this library.
     *
     * @param resource
     */
    public void addResource(Resource resource) {
        Preconditions.checkArgument(resource!=null, "Resource object cannot be null");
        resources.add(resource);
    }

    /** Finds a resource from the list and returns it
     *
     * @param resourceName
     * @return Resource object
     */
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

    /**
     * Remove a media item by its ID.
     * @param mediaId
     */
    public boolean removeMedia(int mediaId) {
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
        checkInvariants();
        return removed;

    }

    /**
     * Find and return a media item by ID.
     * Prints a message if not found and returns null.
     * @param mediaId
     * @return media object
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
        checkInvariants();
        return media;
    }
}
