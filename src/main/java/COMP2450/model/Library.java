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
    List<Resource> resources;
    public static LibraryManagement libraryManagement = new LibraryManagement();


    /**
     * Constructor:  Make a new Library
     * @param name the name of the library (checks that the name is not null)
     * - creates empty media/resources lists
     * - creates a Map for this library
     * - registers this library in LibraryManagement
     */
    public Library(String name) {
        Preconditions.checkArgument(name!=null && !name.isEmpty(), "Library name cannot be null or empty");
        this.name = name;
        this.mediaAvailable = new ArrayList<>();
        this.resources = new ArrayList<>();
        map = new Map(this);
        checkInvariants();
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
     * @param media the media to be added to the resources list (must not be null)
     */
    public void addMedia(MediaInterface media) {
        checkInvariants();
        Preconditions.checkArgument(media!=null, "Media object cannot be null");
        this.mediaAvailable.add(media);
        checkInvariants();
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

    /** Mutate name of library
     *
     * @param name new name of the library (must not be null or empty)
     */
    public void setName(String name) {
        Preconditions.checkArgument(name != null && !name.isEmpty(), "Library name cannot be null or empty");
        checkInvariants();
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
     * @param resource the new resource being added (must not be null)
     */
    public void addResource(Resource resource) {
        Preconditions.checkArgument(resource!=null, "Resource object cannot be null");
        checkInvariants();
        resources.add(resource);
        checkInvariants();
    }

    /** Finds a resource from the list and returns it
     *
     * @param resourceName the name of the resource being searched for (must not be null or empty)
     * @return Resource object
     */
    public Resource getResource(String resourceName) {
        checkInvariants();
        Preconditions.checkArgument(resourceName!=null && !resourceName.isEmpty(),
                "Resource name cannot be null or mepty");
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
     * @param mediaId the ID which we are searching for to remove (cant be less than 1)
     */
    public boolean removeMedia(int mediaId) {
        Preconditions.checkArgument(mediaId > 0, "Media ID cannot be less than 1");
        checkInvariants();
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
     * @param mediaId the ID we are seaching for (cant be less than 1)
     * @return media object
     */
    public MediaInterface showMedia(int mediaId) {
        Preconditions.checkArgument(mediaId>0, "Media ID cannot be less than 1");
        checkInvariants();
        MediaInterface media = null;
        int index = 0;
        while (index < mediaAvailable.size() && media == null) {
            if (mediaAvailable.get(index).getMediaID() == mediaId) {
                media = mediaAvailable.get(index);
            }
            index++;
        }
        checkInvariants();
        return media;
    }
}
