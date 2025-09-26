package COMP2450.model;

import com.google.common.base.Preconditions;

import java.util.ArrayList;

public class UserManagement {
    private static ArrayList<User> users;

    public UserManagement() {
        users = new ArrayList<>();
    }

    public void addUser(User user) {
        if (userExistsBoolean(user.getID())) {
            users.add(user);
        } else {
            System.out.println("This user is duplicate");
        }
    }

    public User getUser(int id) {
        Preconditions.checkArgument(id > 0, "Invalid ID");
        for (User user : users) {
            if (user.getID() == id) {
                return user;
            }
        }
        return null;
    }

    public boolean userExistsBoolean(int id) {
        Preconditions.checkArgument(id > 0, "Invalid ID");
        boolean userExists = false;
        for (int i = 0; i < users.size() && !userExists; i++) {
            if ( (users.get(i)).getID() == (id) ) {
                userExists = true;
            }
        }
        return userExists;
    }
    public User userExists(int id) {
        Preconditions.checkArgument(id > 0, "Invalid ID");
        User userAlreadyExists = null;
        for (int i = 0; i < users.size() && (userAlreadyExists == null); i++) {
            if ( (users.get(i)).getID() == (id) ) {
                userAlreadyExists = users.get(i);
            }
        }
        return (userAlreadyExists);
    }

    public void removeUser(int id) {
        Preconditions.checkArgument(id > 0, "Invalid ID");
        User userExists = userExists(id);
        if (userExists!=null) {
            users.remove(userExists);
        } else {
            System.out.println("User does not exist!");
        }
    }

    public String getUsers() {
        String output ="";
        for (User user : users) {
            output += user.getUsername() + ", ";
        }
        return output;
    }

    public static void reset() {
        users = new ArrayList<>();
    }
}