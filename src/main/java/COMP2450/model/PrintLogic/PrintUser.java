package COMP2450.model.PrintLogic;


import COMP2450.model.User;
import COMP2450.model.UserManagement;
import com.google.common.base.Preconditions;
/**
 * Utility class responsible for printing information about users
 **/
public class PrintUser {
    /**
     Prints the User info as a string
     */
    public static void userInfo(User user) {
        Preconditions.checkNotNull(user);
        System.out.println( "ID : " + user.getID() + " , Name :" + user.getUsername() + ", Email : " + user.getEmail() + ", Phone : " + user.getPhone());
    }

    /**
     Prints the User info of all users as a string
     */
    public static void printAllUsers() {
        Preconditions.checkNotNull(UserManagement.getUsers(), "UserDB is null");
        for (var user : UserManagement.getUsers()) {
            userInfo(user);
        }
    }
}