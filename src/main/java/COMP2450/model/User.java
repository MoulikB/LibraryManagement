package COMP2450.model;

import java.util.ArrayList;
public class User {
   
    private final String username;
    private final int id;
    private double finesDue;
    private ArrayList<Integer> itemsIssued = new ArrayList<Integer>();

    private static UserManagement userDB = new UserManagement();


    public User(String username,int id) {
        this.username = username;
        this.id = id;
        userDB.addUser(this);
    }

    public int getID() {
        return this.id;
    }

    public String getUsername() {
        return this.username;
    }

    public String userInfo() {
        return "ID : " + getID() + " , Name :" + getUsername();
    }

    public void removeUser(User user) {
        userDB.removeUser(user);
    }

    public static String getStringOutput() {
        return userDB.getUsers();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        return this.id == ((User)other).id;
    }

}

class UserManagement {
    private ArrayList<User> users;

    public UserManagement() {
        users = new ArrayList<User>();
    }

    public void addUser(User user) {
        if (!userExists(user)) {
            users.add(user);
        } else {
            System.out.println("duplicate");
        }
    }

    public boolean userExists(User toCheck) {
        boolean userAlreadyExists = false;
        for (int i = 0; i < users.size() && (!userAlreadyExists); i++) {
            if ( (users.get(i)).equals(toCheck) ) {
                userAlreadyExists = true;
            }
        }
        return userAlreadyExists;
    }

    public void removeUser(User toRemove) {
        if (userExists(toRemove)) {
            users.remove(toRemove);
        } else {
            System.out.println("User does not exist!");
        }
    }

    public String getUsers() {
        String output ="";
        for (User user : users) {
                output+= user.getUsername() + ", ";
        }
        return output;
    }
}