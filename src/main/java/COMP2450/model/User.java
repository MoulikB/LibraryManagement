package COMP2450.model;

import java.util.ArrayList;
public class User {
   
    private final String username;
    private final int id;
    private double finesDue;
    private ArrayList<Integer> itemsIssued = new ArrayList<Integer>();

    public static UserManagement userDB = new UserManagement();


    public User(String username,int id)  {
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

    public void removeUser(int id) {
        userDB.removeUser(id);
    }

    public static void getStringOutput() {
         System.out.println(userDB.getUsers());
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        return this.id == ((User)other).id;
    }

}

