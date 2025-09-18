package COMP2450.model;

import java.util.ArrayList;

public class UserManagement {
    private ArrayList<User> users;

    public UserManagement() {
        users = new ArrayList<>();
    }

    public void addUser(User user) {
        if (userExists(user.getID()) == null) {
            users.add(user);
        } else {
            System.out.println("duplicate");
        }
    }

    public User getUser(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public User userExists(int id) {
        User userAlreadyExists = null;
        for (int i = 0; i < users.size() && (userAlreadyExists == null); i++) {
            if ( (users.get(i)).getID() == (id) ) {
                userAlreadyExists = users.get(i);
            }
        }
        return (userAlreadyExists);
    }

    public void removeUser(int id) {
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
}