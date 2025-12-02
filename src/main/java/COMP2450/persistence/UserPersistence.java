package COMP2450.persistence;

import COMP2450.domain.Library;
import COMP2450.domain.User;

import java.util.List;

public interface UserPersistence {

    void saveUsers(List<User> users);

    List<User> loadUsers(Library library);
}
