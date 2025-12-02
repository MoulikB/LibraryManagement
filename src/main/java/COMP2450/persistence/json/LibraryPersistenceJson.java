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

    // SAVE

    @Override
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

    // LOAD

    @Override
    public Library load(String name) throws NotFoundException {
        Preconditions.checkNotNull(name, "Library name cannot be null");

        if (!Files.exists(storage)) {
            throw new NotFoundException();
        }

        try (JsonReader reader = Json.createReader(Files.newInputStream(storage))) {

            JsonObject root = reader.readObject();

            Preconditions.checkArgument(root.containsKey("name"), "Library JSON missing field: name");

            String storedName = root.getString("name");

            if (!storedName.equals(name)) {
                throw new NotFoundException();
            }

            Library.LibraryBuilder builder =
                    new Library.LibraryBuilder().name(storedName);

            // Media
            JsonArray mediaArray = root.getJsonArray("media");
            Preconditions.checkNotNull(mediaArray, "Missing media array in JSON");

            Library tempLib = builder.build(); // temp for linking

            for (JsonValue v : mediaArray) {
                Preconditions.checkArgument(v.getValueType() == JsonValue.ValueType.OBJECT,
                        "Media entry is not a JSON object");

                MediaInterface media = mediaFromJson(v.asJsonObject(), tempLib);
                builder.withMedia(media);
            }

            // Resources
            JsonArray resourceArray = root.getJsonArray("resources");
            Preconditions.checkNotNull(resourceArray, "Missing resources array in JSON");

            for (JsonValue v : resourceArray) {
                Preconditions.checkArgument(v.getValueType() == JsonValue.ValueType.OBJECT,
                        "Resource entry is not a JSON object");

                Resource r = resourceFromJson(v.asJsonObject(), tempLib);
                builder.withResource(r);
            }

            return builder.build();

        } catch (IOException | JsonException e) {
            throw new RuntimeException("Failed to load library", e);
        }
    }

    // MEDIA

    private JsonArray mediaToJson(List<MediaInterface> mediaList) {
        Preconditions.checkNotNull(mediaList, "Media list cannot be null");

        JsonArrayBuilder arr = Json.createArrayBuilder();

        for (MediaInterface m : mediaList) {
            Preconditions.checkNotNull(m, "Media item is null");

            if (m instanceof Book b) {
                arr.add(Json.createObjectBuilder()
                        .add("mediaType", "Book")
                        .add("title", b.getTitle())
                        .add("author", b.getCreator())
                        .add("publisher", b.getLibrary().getName())
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
        Preconditions.checkNotNull(json, "Media JSON cannot be null");
        Preconditions.checkNotNull(library, "Library cannot be null");
        Preconditions.checkArgument(json.containsKey("mediaType"), "mediaType missing in JSON");

        String type = json.getString("mediaType");

        return switch (type) {
            case "Book" -> {
                Preconditions.checkArgument(json.containsKey("isbn"), "Book missing isbn");
                yield new Book.BookBuilder()
                        .title(json.getString("title"))
                        .author(json.getString("author"))
                        .publisher(json.getString("publisher"))
                        .genre(MediaGenres.valueOf(json.getString("genre")))
                        .isbn(json.getInt("isbn"))
                        .library(library)
                        .totalCopies(json.getInt("availableCopies"))
                        .issuedDays(json.getInt("issuedDays"))
                        .build();
            }
            case "Movie" -> {
                Preconditions.checkArgument(json.containsKey("id"), "Movie missing id");
                yield new Movie.MovieBuilder()
                        .title(json.getString("title"))
                        .director(json.getString("director"))
                        .mediaID(json.getInt("id"))
                        .genre(MediaGenres.valueOf(json.getString("genre")))
                        .library(library)
                        .totalCopies(json.getInt("availableCopies"))
                        .issuedDays(json.getInt("issuedDays"))
                        .build();
            }
            default -> throw new RuntimeException("Unknown media type: " + type);
        };
    }

    // RESOURCES

    private JsonArray resourcesToJson(List<Resource> list) {
        Preconditions.checkNotNull(list, "Resources list cannot be null");

        JsonArrayBuilder arr = Json.createArrayBuilder();

        for (Resource r : list) {
            Preconditions.checkNotNull(r, "Resource cannot be null");

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
        Preconditions.checkNotNull(json, "Resource JSON cannot be null");
        Preconditions.checkArgument(json.containsKey("resourceType"), "resourceType missing in JSON");

        String type = json.getString("resourceType");

        switch (type) {
            case "Computer" -> {
                Computer.ComputerBuilder builder = new Computer.ComputerBuilder()
                        .computerId(json.getString("name"))
                        .library(library);

                for (JsonValue v : json.getJsonArray("unavailable")) {
                    builder.addUnavailable(timeSlotFromJson((JsonString) v));
                }
                return builder.build();
            }

            case "StudyRoom" -> {
                StudyRoom.StudyRoomBuilder builder = new StudyRoom.StudyRoomBuilder()
                        .roomName(json.getString("name"))
                        .library(library);

                for (JsonValue v : json.getJsonArray("unavailable")) {
                    builder.addUnavailable(timeSlotFromJson((JsonString) v));
                }
                return builder.build();
            }

            default -> throw new RuntimeException("Unknown resource type: " + type);
        }
    }


    // TIMESLOTS

    private JsonArray timeSlotsToJson(List<TimeSlots> slots) {
        Preconditions.checkNotNull(slots, "TimeSlot list cannot be null");

        JsonArrayBuilder arr = Json.createArrayBuilder();
        for (TimeSlots t : slots) {
            Preconditions.checkNotNull(t, "TimeSlot entry cannot be null");
            arr.add(t.name());
        }
        return arr.build();
    }

    private TimeSlots timeSlotFromJson(JsonString json) {
        Preconditions.checkNotNull(json, "TimeSlot JSON cannot be null");
        return TimeSlots.valueOf(json.getString());
    }
}
