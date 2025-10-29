package COMP2450.model;

public class RegisterUser {

    static int id = 1;

    public RegisterUser() {
        UserManagement.registerUser();
        id++;
    }
}
