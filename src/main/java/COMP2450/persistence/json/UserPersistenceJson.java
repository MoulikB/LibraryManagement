package COMP2450.persistence.json;

import COMP2450.domain.User;
import COMP2450.domain.Media.MediaInterface;
import COMP2450.persistence.UserPersistence;
import com.google.common.base.Preconditions;

import javax.json.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class UserPersistenceJson implements UserPersistence {

    private final Path storage;

    public UserPersistenceJson(Path storage) {
        Preconditions.checkNotNull(storage);
        this.storage = storage;
    }

    @Override
    public void saveUsers(List<User> users) {
        try (JsonWriter writer = Json.createWriter(Files.newOutputStream(storage))) {

            JsonArrayBuilder usersJson = Json.createArrayBuilder();

            for (User u : users) {
                JsonObjectBuilder jsonUser = Json.createObjectBuilder()
                        .add("username", u.getUsername())
                        .add("password", u.getPassword())
                        .add("email", u.getEmail())
                        .add("phone", u.getPhone())
                        .add("id", u.getID())
                        .add("finesDue", u.calculateFinesDue());

                // Save issued media IDs
                JsonArrayBuilder issuedJson = Json.createArrayBuilder();
                for (MediaInterface m : u.getItemsIssued()) {
                    issuedJson.add(m.getMediaID());
                }

                jsonUser.add("issued", issuedJson);

                usersJson.add(jsonUser);
            }

            writer.writeArray(usersJson.build());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> loadUsers() {
        List<User> list = new ArrayList<>();

        if (!Files.exists(storage)) return list;

        try (JsonReader reader = Json.createReader(Files.newInputStream(storage))) {

            JsonArray arr = reader.readArray();

            for (JsonValue v : arr) {
                JsonObject obj = v.asJsonObject();

                User u = new User(
                        obj.getString("username"),
                        obj.getString("password"),
                        obj.getInt("id"),
                        obj.getString("email"),
                        obj.getString("phone")
                );

                list.add(u);
            }

        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        return list;
    }
}
