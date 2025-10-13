package COMP2450.model;

import com.google.common.base.Preconditions;

import java.util.Scanner;

public class RegisterUser {

    static int id = 0;

    public RegisterUser() {
        var input = new InputValidation();

        System.out.print("Welcome to the Registration process ");

        System.out.print("Enter Your Username :");
        String username = input.getStringInput();

        System.out.print("Enter Your Password :");
        String password = input.getStringInput();

        System.out.print("Enter Your Email Address :");
        String email = input.getStringInput();

        System.out.print("Enter Your Number :");
        String number = input.getStringInput();

        Preconditions.checkArgument(username != null && !username.isEmpty(), "Username cant be null");
        Preconditions.checkArgument(password != null && !password.isEmpty(), "Password cant be null");
        Preconditions.checkArgument(email != null && !email.isEmpty(), "Email cant be null");
        Preconditions.checkArgument(number != null && !number.isEmpty() && number.length() == 10, "Number must be greater than 0");



    }
}
