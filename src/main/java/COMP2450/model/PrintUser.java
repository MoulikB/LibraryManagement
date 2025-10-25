package COMP2450.model;

public class PrintUser {
    // Get the user's info.
    public static void userInfo(User user) {
        System.out.println( "ID : " + user.getID() + " , Name :" + user.getUsername() + ", Email : " + user.getEmail() + ", Phone : " + user.getPhone());
    }

    public static void printAllUsers() {
        for (var user : UserManagement.getUsers()) {
            userInfo(user);
        }
    }
}