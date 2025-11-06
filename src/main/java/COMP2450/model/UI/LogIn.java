package COMP2450.model.UI;

import COMP2450.model.InputValidation;
import COMP2450.model.User;
import COMP2450.model.UserManagement;
import com.google.common.base.Preconditions;

public class LogIn {
    public static User loginUser() {

        User userFound = null;
        boolean flag = true;

        System.out.println("Welcome to the Log In process.");


        while (flag && userFound == null) {
            System.out.print("Enter Your Username :");
            String username = InputValidation.getStringInput();

            System.out.print("Enter Your Password :");
            String password = InputValidation.getStringInput();

            Preconditions.checkArgument(username != null && !username.isEmpty(), "Username cant be null");
            Preconditions.checkArgument(password != null && !password.isEmpty(), "Password cant be null");

            for (var currUser : UserManagement.getUsers()) {
                if (((currUser.getUsername()).equals(username)) && ((currUser.getPassword()).equals(password))) {
                    userFound = currUser;
                }
            }

            if (userFound == null) {

                System.out.println("Invalid Username or password");
                System.out.println("Try again? [Y/N]");

                if (!InputValidation.getStringInput().equalsIgnoreCase("y")) {
                    flag = false;
                }
            }
        }

        return userFound;

    }
}
