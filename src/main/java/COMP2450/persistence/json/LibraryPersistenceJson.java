package COMP2450.persistence.json;

import COMP2450.domain.Library;
import COMP2450.domain.Media.Book;
import COMP2450.domain.Media.MediaInterface;
import COMP2450.domain.Media.Movie;
import COMP2450.domain.MediaGenres;
import COMP2450.domain.Resources.Computer;
import COMP2450.domain.Resources.Resource;
import COMP2450.domain.Resources.StudyRoom;
import COMP2450.domain.TimeSlots;

import COMP2450.persistence.LibraryPersistence;
import com.google.common.base.Preconditions;

import javax.json.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class LibraryPersistenceJson implements LibraryPersistence {

    private final Path storage;

    public LibraryPersistenceJson(Path storage) {
        Preconditions.checkNotNull(storage, "Storage path cannot be null");
        this.storage = storage;
    }

    // ============================================================
    // SAVE
    // ============================================================

    public Library save(Library library) {
        Preconditions.checkNotNull(library, "Library cannot be null");

        JsonObjectBuilder root = Json.createObjectBuilder();

        root.add("name", library.getName());
        root.add("media", mediaToJson(library.getMediaAvailable()));
        root.add("resources", resourcesToJson(library.getResources()));

        try (JsonWriter writer = Json.createWriter(Files.newOutputStream(storage))) {
            writer.writeObject(root.build());
        } catch (Exception e) {
            throw new RuntimeException("Failed to save library", e);
        }

        return library;
    }

    // ============================================================
    // LOAD
    // ============================================================

    @Override
    public Library load(String name) throws NotFoundException {
        Preconditions.checkNotNull(name);

        if (!Files.exists(storage)) {
            throw new NotFoundException();
        }

        try (JsonReader reader = Json.createReader(Files.newInputStream(storage))) {

            JsonObject root = reader.readObject();

            String storedName = root.getString("name");

            if (!storedName.equals(name)) {
                throw new NotFoundException();
            }

            // Build library
            Library.LibraryBuilder builder = new Library.LibraryBuilder().name(storedName);

            // Load media
            for (JsonValue v : root.getJsonArray("media")) {
                JsonObject mediaJson = v.asJsonObject();
                MediaInterface media = mediaFromJson(mediaJson, builder.build());
                builder.withMedia(media);
            }

            // Load resources
            for (JsonValue v : root.getJsonArray("resources")) {
                JsonObject resJson = v.asJsonObject();
                Resource r = resourceFromJson(resJson, builder.build());
                builder.withResource(r);
            }

            return builder.build();

        } catch (IOException | JsonException e) {
            throw new RuntimeException("Failed to load library", e);
        }
    }

    // ============================================================
    // MEDIA (Book, Movie)
    // ============================================================

    private JsonArray mediaToJson(List<MediaInterface> mediaList) {
        JsonArrayBuilder arr = Json.createArrayBuilder();

        for (MediaInterface m : mediaList) {
            if (m instanceof Book b) {
                arr.add(Json.createObjectBuilder()
                        .add("mediaType", "Book")
                        .add("title", b.getTitle())
                        .add("author", b.getCreator())
                        .add("publisher", b.getLibrary().getName()) // skipped publisher fix if needed
                        .add("genre", b.getMediaGenre().toString())
                        .add("isbn", b.getMediaID())
                        .add("availableCopies", b.getAvailableCopies())
                        .add("issuedDays", b.getIssuedDay())
                );
            } else if (m instanceof Movie mo) {
                arr.add(Json.createObjectBuilder()
                        .add("mediaType", "Movie")
                        .add("title", mo.getTitle())
                        .add("director", mo.getCreator())
                        .add("genre", mo.getMediaGenre().toString())
                        .add("id", mo.getMediaID())
                        .add("availableCopies", mo.getAvailableCopies())
                        .add("issuedDays", mo.getIssuedDay())
                );
            }
        }
        return arr.build();
    }

    private MediaInterface mediaFromJson(JsonObject json, Library library) {

        String type = json.getString("mediaType");

        return switch (type) {
            case "Book" -> new Book.BookBuilder()
                    .title(json.getString("title"))
                    .author(json.getString("author"))
                    .publisher(json.getString("publisher"))
                    .genre(MediaGenres.valueOf(json.getString("genre")))
                    .isbn(json.getInt("isbn"))
                    .library(library)
                    .totalCopies(json.getInt("availableCopies"))
                    .issuedDays(json.getInt("issuedDays"))
                    .build();
            case "Movie" -> new Movie.MovieBuilder()
                    .title(json.getString("title"))
                    .director(json.getString("director"))
                    .mediaID(json.getInt("id"))
                    .genre(MediaGenres.valueOf(json.getString("genre")))
                    .library(library)
                    .totalCopies(json.getInt("availableCopies"))
                    .issuedDays(json.getInt("issuedDays"))
                    .build();
            default -> throw new RuntimeException("Unknown media type: " + type);
        };
    }

    // ============================================================
    // RESOURCES (Computer, StudyRoom)
    // ============================================================

    private JsonArray resourcesToJson(List<Resource> list) {
        JsonArrayBuilder arr = Json.createArrayBuilder();

        for (Resource r : list) {
            if (r instanceof Computer c) {
                arr.add(Json.createObjectBuilder()
                        .add("resourceType", "Computer")
                        .add("name", c.getResourceName())
                        .add("unavailable", timeSlotsToJson(c.getUnavailableTimeSlots()))
                );
            } else if (r instanceof StudyRoom s) {
                arr.add(Json.createObjectBuilder()
                        .add("resourceType", "StudyRoom")
                        .add("name", s.getResourceName())
                        .add("unavailable", timeSlotsToJson(s.getUnavailableTimeSlots()))
                );
            }
        }

        return arr.build();
    }

    private Resource resourceFromJson(JsonObject json, Library library) {

        String type = json.getString("resourceType");

        switch (type) {

            case "Computer": {
                Computer.ComputerBuilder builder = new Computer.ComputerBuilder()
                        .computerId(json.getString("name"))
                        .library(library);

                for (JsonValue v : json.getJsonArray("unavailable")) {
                    builder.addUnavailable(timeSlotFromJson((JsonString) v));
                }

                return builder.build();
            }

            case "StudyRoom": {
                StudyRoom.StudyRoomBuilder builder = new StudyRoom.StudyRoomBuilder()
                        .roomName(json.getString("name"))
                        .library(library);

                for (JsonValue v : json.getJsonArray("unavailable")) {
                    builder.addUnavailable(timeSlotFromJson((JsonString) v));
                }

                return builder.build();
            }

            default:
                throw new RuntimeException("Unknown resource type: " + type);
        }
    }

    // ============================================================
    // TIMESLOTS
    // ============================================================

    private JsonArray timeSlotsToJson(List<TimeSlots> slots) {
        JsonArrayBuilder arr = Json.createArrayBuilder();
        for (TimeSlots t : slots) {
            arr.add(t.name());  // Save enum name
        }
        return arr.build();
    }


    private TimeSlots timeSlotFromJson(JsonString json) {
        return TimeSlots.valueOf(json.getString());
    }

}
