package COMP2450.model.UI;

import COMP2450.model.InputValidation;
import COMP2450.model.User;
import COMP2450.model.UserManagement;
import com.google.common.base.Preconditions;

public class RegisterUser {

    /*
     * Handles the user registration process by taking and validating input
     */
    public static User registerUser() {

        System.out.print("Welcome to the Registration process ");

        System.out.print("Enter Your Username :");
        String username = InputValidation.getStringInput();

        System.out.print("Enter Your Password :");
        String password = InputValidation.getStringInput();

        System.out.print("Enter Your Email Address :");
        String email = InputValidation.getStringInput();

        System.out.print("Enter Your Number :");
        int number = InputValidation.getIntInput();

        Preconditions.checkArgument(username != null && !username.isEmpty(), "Username cant be null");
        Preconditions.checkArgument(password != null && !password.isEmpty(), "Password cant be null");
        Preconditions.checkArgument(email != null && !email.isEmpty(), "Email cant be null");
        Preconditions.checkArgument((number > 0), "Number must be not null and 10 digits");

        User user = new User(username,password,UserManagement.nextID , email, number);

        if (UserManagement.addUser(user)) {
              System.out.println("User already exists");
        }
        return UserManagement.userExists(UserManagement.nextID);

    }
}
